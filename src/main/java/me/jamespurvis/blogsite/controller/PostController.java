package me.jamespurvis.blogsite.controller;

import me.jamespurvis.blogsite.models.Account;
import me.jamespurvis.blogsite.models.Post;
import me.jamespurvis.blogsite.service.AccountService;
import me.jamespurvis.blogsite.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable Long id, Model model) {
        Optional<Post> optionalPost = postService.getById(id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            model.addAttribute("post", post);
            return "post";
        } else {
            return "404";
        }
    }

    @GetMapping("/posts/new")
    public String createNewPost(Model model, Principal principal) {

        String userName = "AnonymousUser";

        if (principal != null) {
            userName = principal.getName();
        }

        Optional<Account> optionalAccount = accountService.findByEmail(userName);

        if (optionalAccount.isPresent()) {
            Post post = new Post();
            model.addAttribute("post", post);
            post.setAccount(optionalAccount.get());
            return "post_new";
        } else {
            return "404";
        }
    }

    @PostMapping("posts/new")
    public String saveNewPost(@ModelAttribute Post post) {
        postService.save(post);
        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/posts/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    public String getPostForEdit(@PathVariable Long id, Model model, Principal principal) {
        Optional<Post> optionalPost = postService.getById(id);
        String emailAddress = principal.getName();
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin || !optionalPost.get().getAccount().getEmail().equals(emailAddress)) {
            return "404";
        }

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            model.addAttribute("post", post);
            return "post_edit";
        } else {
            return "404";
        }
    }

    @PostMapping("/posts/{id}")
    @PreAuthorize("isAuthenticated()")
    public String upatePost(@PathVariable Long id, Post post) {
        Optional<Post> optionalPost = postService.getById(id);

        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get();

            existingPost.setTitle(post.getTitle());
            existingPost.setBody(post.getBody());

            postService.save(existingPost);
        }

        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/posts/{id}/delete")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deletePost(@PathVariable Long id, Principal principal) {
        Optional<Post> optionalPost = postService.getById(id);
        String emailAddress = principal.getName();
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin || !optionalPost.get().getAccount().getEmail().equals(emailAddress)) {
            return "404";
        }

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            postService.delete(post);
            return "redirect:/";
        } else {
            return "404";
        }
    }


}
