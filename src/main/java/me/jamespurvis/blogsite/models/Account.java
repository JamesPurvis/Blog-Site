package me.jamespurvis.blogsite.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    @OneToMany(mappedBy = "account")
    private List<Post> posts;
}
