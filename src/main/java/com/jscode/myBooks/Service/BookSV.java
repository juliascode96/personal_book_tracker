package com.jscode.myBooks.Service;

import com.jscode.myBooks.Entity.Book;
import com.jscode.myBooks.Entity.Comment;
import com.jscode.myBooks.Errors.ServiceError;
import com.jscode.myBooks.Repository.BookRepo;
import com.jscode.myBooks.Repository.CommentRepo;
import com.jscode.myBooks.Validations.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class BookSV {
    Validation v = new Validation();

    @Autowired
    BookRepo bookRepo;

    @Autowired
    CommentRepo commentRepo;

    @Transactional
    public Book saveBook(Book b) {
        try {
            validate(b.getTittle(), b.getAuthor(), (long)b.getPages());

        } catch (ServiceError e){
            System.out.println(e);
        }
        return bookRepo.save(b);
    }

    @Transactional
    public Book updateBook(Book b) {
        try {
            validate(b.getTittle(), b.getAuthor(), (long)b.getPages());
            v.validateNumber(b.getId());
            Optional<Book> resp = bookRepo.findById(b.getId());
            if(resp.isPresent()) {
                Book b1 = resp.get();
                return bookRepo.save(b1);
            }
        } catch (ServiceError e){
            System.out.println(e);
        }
        return b;
    }


    /*@Transactional
    public Book addComment(Comment c, Book b) throws ServiceError {
        try {
            v.validateNumber(b.getId());
            Optional<Book> resp = bookRepo.findById(b.getId());
            if(resp.isPresent()) {
                Book b1 = resp.get();
                b1.getComments().add(c);
                return bookRepo.save(b1);
            }

        } catch (ServiceError e) {
            System.out.println(e);
        }
        return b;
    }*/


    public List<Book> showAll() {
        return (List<Book>) bookRepo.findAll();
    }

    public List<Book> searchByTittle(String tittle) throws ServiceError{
        try {
            v.validateString(tittle);
        } catch (ServiceError e) {
            System.out.println(e);
        }
        return bookRepo.findByTittleContainingIgnoreCase(tittle);
    }

    public List<Book> searchByAuthor(String author) throws ServiceError{
        try {
            v.validateString(author);
        } catch (ServiceError e) {
            System.out.println(e);
        }
        return bookRepo.findByAuthorContainingIgnoreCase(author);
    }

    public List<Book> searchBySerie(String serie) throws ServiceError{
        try {
            v.validateString(serie);
        } catch (ServiceError e) {
            System.out.println(e);
        }
        return bookRepo.findBySerieContainingIgnoreCase(serie);
    }

    public Optional<Book> searchById(Long id) {
        try {
            v.validateNumber(id);
        } catch (ServiceError e) {
            System.out.println(e);
        }

        Optional<Book> respuesta = bookRepo.findById(id);
        return respuesta;
    }

    public List<Book> searchBar(String keyword) {
        HashSet books = new HashSet();
        books.addAll(bookRepo.findByTittleContainingIgnoreCase(keyword));
        books.addAll(bookRepo.findByAuthorContainingIgnoreCase(keyword));
        books.addAll(bookRepo.findBySerieContainingIgnoreCase(keyword));

        return (List<Book>) books;

    }

    @Transactional
    public boolean deleteBook(Long id){
        try {
            bookRepo.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public List<Comment> showAllComments(Book b) {
        return commentRepo.findByBookTittle(b.getTittle());
    }

    public void validate(String tittle, String author, Long pages) throws ServiceError {
        v.validateString(tittle);
        v.validateString(author);
        v.validateNumber(pages);

    }

}
