package me.jamespurvis.blogsite.repository;

import me.jamespurvis.blogsite.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public  interface PostRepository  extends JpaRepository<Post, Long> {
}
