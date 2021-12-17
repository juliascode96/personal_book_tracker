package com.jscode.myBooks.Service;

import com.jscode.myBooks.Entity.Quote;
import com.jscode.myBooks.Errors.ServiceError;
import com.jscode.myBooks.Repository.QuoteRepo;
import com.jscode.myBooks.Validations.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class QuoteSV {

    @Autowired
    QuoteRepo quoteRepo;

    Validation v = new Validation();

    @Transactional
    public Quote saveQuote(Quote c) throws ServiceError {
        try {
            v.validateString(c.getText());

        } catch (ServiceError e) {
            System.out.println(e);
        }

        return quoteRepo.save(c);
    }

    @Transactional
    public Quote updateQuote(Quote c) throws ServiceError{
        try {
            v.validateString(c.getText());
            Optional<Quote> resp = quoteRepo.findById(c.getId());
            if(resp.isPresent()) {
                Quote c1 = resp.get();
                return quoteRepo.save(c1);
            }
        } catch (ServiceError e) {
            System.out.println(e);
        }
        return c;
    }

    public List<Quote> showAll() {
        return (List<Quote>) quoteRepo.findAll();
    }

    public List<Quote> showInPageOrder(Long bookId) {
        return (List<Quote>) quoteRepo.showAllinPageOrder(bookId);
    }

    public List<Quote> showAllInBookOrder() {
        return (List<Quote>) quoteRepo.showAllinOrder();
    }

    public List<Quote> showByBook(Long bookId) {
        return quoteRepo.findByBookId(bookId);
    }


    public Optional<Quote> searchById(Long id) {
        try {
            v.validateNumber(id);
        } catch (ServiceError e) {
            System.out.println(e);
        }

        Optional<Quote> respuesta = quoteRepo.findById(id);
        return respuesta;
    }


    @Transactional
    public boolean deleteQuote(Long id){
        try {
            quoteRepo.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public List<Quote> findByTextContainingIgnoreCase(String search) {
        return quoteRepo.findByTextContainingIgnoreCase(search);
    }

}
