package com.jscode.myBooks.Repository;

import com.jscode.myBooks.Entity.Quote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepo extends CrudRepository<Quote, Long> {

    public List<Quote> findByTextContainingIgnoreCase(String search);

    @Query("SELECT c FROM Comment c WHERE c.book.tittle = :tittle")
    public List<Quote> findByBookTittle(@Param("tittle") String tittle);

    @Query("SELECT c FROM Comment c ORDER BY c.book.tittle DESC")
    public List<Quote> showAllinOrder();

    @Query("SELECT c FROM Comment c WHERE c.book.id = :bookId ORDER BY c.pageNumber")
    public List<Quote> showAllinPageOrder(@Param("bookId") Long bookId);

    public List<Quote> findByBookId(Long bookId);

}
