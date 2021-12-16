package com.jscode.myBooks.Entity;

import lombok.*;
import javax.persistence.*;
import java.util.Set;

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


    public Book(String tittle, String author, String serie, Integer pages) {
        this.tittle = tittle;
        this.author = author;
        this.serie = serie;
        this.pages = pages;
    }
}
