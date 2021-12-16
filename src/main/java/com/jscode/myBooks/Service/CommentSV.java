package com.jscode.myBooks.Service;

import com.jscode.myBooks.Entity.Comment;
import com.jscode.myBooks.Errors.ServiceError;
import com.jscode.myBooks.Repository.CommentRepo;
import com.jscode.myBooks.Validations.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CommentSV {
    @Autowired
    CommentRepo commentRepo;

    Validation v = new Validation();

    @Transactional
    public Comment saveComment(Comment c) throws ServiceError {
        try {
            v.validateString(c.getText());

        } catch (ServiceError e) {
            System.out.println(e);
        }

        return commentRepo.save(c);
    }

    @Transactional
    public Comment updateComment(Comment c) throws ServiceError{
        try {
            v.validateString(c.getText());
            Optional<Comment> resp = commentRepo.findById(c.getId());
            if(resp.isPresent()) {
                Comment c1 = resp.get();
                return commentRepo.save(c1);
            }
        } catch (ServiceError e) {
            System.out.println(e);
        }
        return c;
    }

    public List<Comment> showAll() {
        return (List<Comment>) commentRepo.findAll();
    }

    public List<Comment> showInPageOrder(Long bookId) {
        return (List<Comment>) commentRepo.showAllinPageOrder(bookId);
    }

    public List<Comment> showAllInBookOrder() {
        return (List<Comment>) commentRepo.showAllinOrder();
    }

    public List<Comment> showByBook(Long bookId) {
        return commentRepo.findByBookId(bookId);
    }


    public Optional<Comment> searchById(Long id) {
        try {
            v.validateNumber(id);
        } catch (ServiceError e) {
            System.out.println(e);
        }

        Optional<Comment> respuesta = commentRepo.findById(id);
        return respuesta;
    }


    @Transactional
    public boolean deleteComment(Long id){
        try {
            commentRepo.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public List<Comment> findByTextContainingIgnoreCase(String search) {
        return commentRepo.findByTextContainingIgnoreCase(search);
    }

}
