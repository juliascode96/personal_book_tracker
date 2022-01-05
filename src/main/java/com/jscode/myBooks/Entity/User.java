package com.jscode.myBooks.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String password;
    private String password2;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany
    private Set<Book> books;


    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

}
