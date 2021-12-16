package com.jscode.myBooks.Controller;

import com.jscode.myBooks.Entity.Book;
import com.jscode.myBooks.Service.BookSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class BookControl {
    @Autowired
    BookSV bookSV;

    @GetMapping("/books")
    public String listBooks(Model model, String keyword) {
        if(keyword != null) {
            model.addAttribute("books", bookSV.searchBar(keyword));
        } else {
            model.addAttribute("books", bookSV.showAll());
        }

        return "books";
    }

    @GetMapping("/books/{id}")
    public String getBookById(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookSV.searchById(id).get());
        return "book";
    }

    @GetMapping("/book/new")
    public String createForm(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        return "new_book";
    }

    @PostMapping("/books")
    public String saveBook(@ModelAttribute("book") Book book){
        bookSV.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/books/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookSV.searchById(id));
        return "edit_book";
    }

    @PostMapping("/books/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute("book") Book book, Model model) {
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
    }

    @DeleteMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookSV.deleteBook(id);
        return "redirect:/books";
    }

}
