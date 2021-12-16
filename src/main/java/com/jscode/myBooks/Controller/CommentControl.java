package com.jscode.myBooks.Controller;

import com.jscode.myBooks.Entity.Book;
import com.jscode.myBooks.Entity.Comment;
import com.jscode.myBooks.Errors.ServiceError;
import com.jscode.myBooks.Service.BookSV;
import com.jscode.myBooks.Service.CommentSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class CommentControl {

    @Autowired
    CommentSV commentSV;

    @Autowired
    BookSV bookSV;

    @GetMapping("/books/{bookId}/comments")
    public String showCommentsByBook(@PathVariable Long bookId, Model model) {
        model.addAttribute("comments", commentSV.showByBook(bookId));
        return "comments";
    }

    @GetMapping("/books/{bookId}/new_comment")
    public String addCommentForm(@PathVariable Long bookId, Model model) {
        Comment comment = new Comment();
        Book book = bookSV.searchById(bookId).get();
        model.addAttribute("book", book);
        model.addAttribute("comment", comment);
        return "new_comment";
    }

    @PostMapping("/books/{bookId}/comments")
    public String createNewComment(@PathVariable Long bookId, @ModelAttribute("comment") Comment comment) {
            Book book = bookSV.searchById(bookId).get();
            comment.setBook(book);
            try {
                commentSV.saveComment(comment);
            } catch (ServiceError e) {
                System.out.println(e);
            }
        return "redirect:/books";
    }

    @GetMapping("/books/{bookId}/edit_comment/{id}")
    public String editCommentForm(@PathVariable Long bookId, @PathVariable Long id, Model model){
        Comment comment = commentSV.searchById(id).get();
        Book book = bookSV.searchById(bookId).get();
        comment.setBook(book);
        model.addAttribute("comment", comment);
        model.addAttribute("book", book);
        return "edit_comment";
    }

    @PostMapping("/books/{bookId}/comments/{id}")
    public String editComment(@PathVariable Long bookId, @PathVariable Long id, @ModelAttribute("comment")Comment comment) {
        comment.setBook(bookSV.searchById(bookId).get());
        Optional<Comment> resp = commentSV.searchById(id);
        if(resp.isPresent()){
            Comment c = resp.get();
            c.setId(comment.getId());
            c.setBook(comment.getBook());
            c.setText(comment.getText());
            c.setPageNumber(comment.getPageNumber());
            try {
                commentSV.saveComment(c);
            } catch (ServiceError e) {
                System.out.println(e);
            }
        }
        return "redirect:/books/{bookId}/comments";
    }

    @GetMapping("/books/{bookId}/comment/{id}")
    public String deleteComment(@PathVariable Long bookId, @PathVariable Long id, Model model) {
        Book book = bookSV.searchById(bookId).get();
        model.addAttribute("book", book);
        model.addAttribute("comment", commentSV.searchById(id).get());
        commentSV.deleteComment(id);
        return "redirect:/books/{bookId}/comments";
    }
}
