package com.jscode.myBooks.Controller;

import com.jscode.myBooks.Entity.Book;
import com.jscode.myBooks.Entity.Comment;
import com.jscode.myBooks.Errors.ServiceError;
import com.jscode.myBooks.Service.BookSV;
import com.jscode.myBooks.Service.CommentSV;
import com.jscode.myBooks.Service.UserSV;
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

    @Autowired
    UserSV userSV;

    @GetMapping("/{userId}/books/{bookId}/comments")
    public String showCommentsByBook(@PathVariable Long bookId, @PathVariable Long userId, Model model) {
        model.addAttribute("user", userSV.searchById(userId).get());
        model.addAttribute("comments", commentSV.showByBook(bookId));
        model.addAttribute("book", bookSV.searchById(bookId).get());
        return "comments";
    }

    @GetMapping("/{userId}/books/{bookId}/new_comment")
    public String addCommentForm(@PathVariable Long bookId, @PathVariable Long userId, Model model) {
        Comment comment = new Comment();
        Book book = bookSV.searchById(bookId).get();
        model.addAttribute("user", book.getUser());
        model.addAttribute("book", book);
        model.addAttribute("comment", comment);
        return "new_comment";
    }

    @PostMapping("/{userId}/books/{bookId}/comments")
    public String createNewComment(@PathVariable Long bookId, @PathVariable Long userId, @ModelAttribute("comment") Comment comment) {
            Book book = bookSV.searchById(bookId).get();
            comment.setBook(book);
            try {
                commentSV.saveComment(comment);
            } catch (ServiceError e) {
                System.out.println(e);
            }
        return "redirect:/{userId}/books/{bookId}/comments";
    }

    @GetMapping("/{userId}/books/{bookId}/edit_comment/{id}")
    public String editCommentForm(@PathVariable Long bookId, @PathVariable Long id, @PathVariable Long userId, Model model){
        Comment comment = commentSV.searchById(id).get();
        Book book = bookSV.searchById(bookId).get();
        comment.setBook(book);
        model.addAttribute("comment", comment);
        model.addAttribute("book", book);
        model.addAttribute("user", userSV.searchById(userId).get());
        return "edit_comment";
    }

    @PostMapping("/{userId}/books/{bookId}/comments/{id}")
    public String editComment(@PathVariable Long bookId, @PathVariable Long id, @PathVariable Long userId, @ModelAttribute("comment")Comment comment) {
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
        return "redirect:/{userId}/books/{bookId}/comments";
    }

    @GetMapping("/{userId}/books/{bookId}/comment/{id}")
    public String deleteComment(@PathVariable Long bookId, @PathVariable Long id, @PathVariable Long userId, Model model) {
        Book book = bookSV.searchById(bookId).get();
        model.addAttribute("book", book);
        model.addAttribute("comment", commentSV.searchById(id).get());
        model.addAttribute("user", userSV.searchById(userId).get());
        commentSV.deleteComment(id);
        return "redirect:/{userId}/books/{bookId}/comments";
    }
}
