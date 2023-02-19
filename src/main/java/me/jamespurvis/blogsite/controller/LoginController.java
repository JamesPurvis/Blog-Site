package me.jamespurvis.blogsite.controller;

import me.jamespurvis.blogsite.models.Account;
import me.jamespurvis.blogsite.repository.AccountRepository;
import me.jamespurvis.blogsite.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

@Controller
public class LoginController {


    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

}
