package com.jscode.myBooks.Controller;

import com.jscode.myBooks.Entity.Book;
import com.jscode.myBooks.Entity.Comment;
import com.jscode.myBooks.Entity.User;
import com.jscode.myBooks.Errors.ServiceError;
import com.jscode.myBooks.Service.BookSV;
import com.jscode.myBooks.Service.CommentSV;
import com.jscode.myBooks.Service.UserSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class BookControl {
    @Autowired
    BookSV bookSV;

    @Autowired
    CommentSV commentSV;

    @Autowired
    UserSV userSV;

    @GetMapping("/user/{userId}/books")
    public String listBooks(@PathVariable Long userId, Model model, String keyword) {
        model.addAttribute("user", userSV.searchById(userId).get());
        model.addAttribute("books", bookSV.showByUser(userId));
        return "books";
    }

    @GetMapping("/user/{userId}/books/{id}")
    public String getBookById(@PathVariable Long id, @PathVariable Long userId, Model model) {
        model.addAttribute("book", bookSV.searchById(id).get());
        return "book";
    }

    @GetMapping("/user/{userId}/book/new")
    public String createForm(@PathVariable Long userId, Model model) {
        Book book = new Book();
        User user = userSV.searchById(userId).get();
        model.addAttribute("user", user);
        model.addAttribute("book", book);
        return "new_book";
    }

    @PostMapping("/{userId}/books")
    public String saveBook(@PathVariable Long userId, @ModelAttribute("book") Book book) {
        User user = userSV.searchById(userId).get();
        book.setUser(user);
        try {
            bookSV.saveBook(book);
            return "redirect:/{userId}/books";
        } catch (ServiceError e) {
           return "error";
        }
    }

    @GetMapping("/user/{userId}/books/edit/{id}")
    public String editForm(@PathVariable Long id, @PathVariable Long userId, Model model) {
        Book book = bookSV.searchById(id).get();
        User user = userSV.searchById(userId).get();
        book.setUser(user);
        model.addAttribute("book", book);
        model.addAttribute("user", user);
        return "edit_book";
    }


    @PostMapping("/{userId}/books/{id}")
    public String updateBook(@PathVariable Long id, @PathVariable Long userId, @ModelAttribute("book") Book book) {
        book.setUser(userSV.searchById(userId).get());
        try {
            Optional<Book> resp = bookSV.searchById(id);
            if(resp.isPresent()){
                Book b1 = resp.get();
                b1.setId(id);
                b1.setTittle(book.getTittle());
                b1.setAuthor(book.getAuthor());
                b1.setSerie(book.getSerie());
                b1.setPages(book.getPages());

                bookSV.saveBook(b1);
            }

            return "redirect:/books";
        } catch (ServiceError e) {
            return "error";
        }

    }

    @GetMapping("/{userId}/books/delete/{id}")
    public String deleteBook(@PathVariable Long id, @PathVariable Long userId, Model model) {
        List<Comment> comments = commentSV.showByBook(id);
        for (Comment c: comments) {
            commentSV.deleteComment(c.getId());
        }
        model.addAttribute("user", userSV.searchById(userId).get());
        bookSV.deleteBook(id);
        return "redirect:/books";
    }

}
