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
        model.addAttribute("comment", commentSV.searchById(id));
        return "edit_comment";
    }

    @PostMapping("/books/{bookId}/comments/{id}")
    public String editComment(@PathVariable Long bookId, @PathVariable Long id, @ModelAttribute("comment")Comment comment) {
        Optional<Comment> resp = commentSV.searchById(id);
        if(resp.isPresent()){
            Comment c = resp.get();
            c.setText(comment.getText());
            c.setPageNumber(comment.getPageNumber());
            try {
                commentSV.saveComment(c);
            } catch (ServiceError e) {
                System.out.println(e);
            }
        }
        return "redirect:/books/{bookId}/comments/{id}";
    }

    @DeleteMapping("/books/{bookId}/comment/{id}")
    public String deleteComment(@PathVariable Long bookId, @PathVariable Long id) {
        commentSV.deleteComment(id);
        return "redirect:/books/{bookId}/comments";
    }
}
