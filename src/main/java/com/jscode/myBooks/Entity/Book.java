package com.jscode.myBooks.Entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tittle;
    private String author;
    private String serie;
    private Integer pages;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;



    public Book(String tittle, String author, String serie, Integer pages, User user) {
        this.tittle = tittle;
        this.author = author;
        this.serie = serie;
        this.pages = pages;
        this.user = user;
    }
}
