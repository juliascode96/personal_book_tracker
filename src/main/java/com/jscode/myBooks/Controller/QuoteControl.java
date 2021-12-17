package com.jscode.myBooks.Controller;

import com.jscode.myBooks.Entity.Book;
import com.jscode.myBooks.Entity.Quote;
import com.jscode.myBooks.Errors.ServiceError;
import com.jscode.myBooks.Service.BookSV;
import com.jscode.myBooks.Service.QuoteSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class QuoteControl {


    @Autowired
    QuoteSV quoteSV;

    @Autowired
    BookSV bookSV;

    @GetMapping("/books/{bookId}/quotes")
    public String showCommentsByBook(@PathVariable Long bookId, Model model) {
        model.addAttribute("quotes", quoteSV.showByBook(bookId));
        model.addAttribute("book", bookSV.searchById(bookId).get());
        return "quotes";
    }

    @GetMapping("/books/{bookId}/new_quote")
    public String addCommentForm(@PathVariable Long bookId, Model model) {
        Quote quote = new Quote();
        Book book = bookSV.searchById(bookId).get();
        model.addAttribute("book", book);
        model.addAttribute("quote", quote);
        return "new_quote";
    }

    @PostMapping("/books/{bookId}/quotes")
    public String createNewComment(@PathVariable Long bookId, @ModelAttribute("quote") Quote quote) {
            Book book = bookSV.searchById(bookId).get();
            quote.setBook(book);
            try {
                quoteSV.saveQuote(quote);
            } catch (ServiceError e) {
                System.out.println(e);
            }
        return "redirect:/books/{bookId}/quotes";
    }

    @GetMapping("/books/{bookId}/edit_quote/{id}")
    public String editCommentForm(@PathVariable Long bookId, @PathVariable Long id, Model model){
        Quote quote = quoteSV.searchById(id).get();
        Book book = bookSV.searchById(bookId).get();
        quote.setBook(book);
        model.addAttribute("quote", quote);
        model.addAttribute("book", book);
        return "edit_quote";
    }

    @PostMapping("/books/{bookId}/quote/{id}")
    public String editComment(@PathVariable Long bookId, @PathVariable Long id, @ModelAttribute("quote")Quote quote) {
        quote.setBook(bookSV.searchById(bookId).get());
        Optional<Quote> resp = quoteSV.searchById(id);
        if(resp.isPresent()){
            Quote c = resp.get();
            c.setId(quote.getId());
            c.setBook(quote.getBook());
            c.setText(quote.getText());
            c.setPageNumber(quote.getPageNumber());
            try {
                quoteSV.saveQuote(c);
            } catch (ServiceError e) {
                System.out.println(e);
            }
        }
        return "redirect:/books/{bookId}/quotes";
    }

    @GetMapping("/books/{bookId}/quote/{id}")
    public String deleteComment(@PathVariable Long bookId, @PathVariable Long id, Model model) {
        Book book = bookSV.searchById(bookId).get();
        model.addAttribute("book", book);
        model.addAttribute("quote", quoteSV.searchById(id).get());
        quoteSV.deleteQuote(id);
        return "redirect:/books/{bookId}/quotes";
    }

}
