package com.jscode.myBooks.Repository;

import com.jscode.myBooks.Entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepo extends CrudRepository<Book, Long> {
    public List<Book> findByAuthorContainingIgnoreCase(String name);
    public List<Book> findByTittleContainingIgnoreCase(String tittle);
    public List<Book> findBySerieContainingIgnoreCase(String tittle);
    public List<Book> findByUserId(Long userId);

}
