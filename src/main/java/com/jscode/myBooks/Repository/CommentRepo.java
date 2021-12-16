package com.jscode.myBooks.Repository;

import com.jscode.myBooks.Entity.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends CrudRepository<Comment, Long> {
    public List<Comment> findByTextContainingIgnoreCase(String search);

    @Query("SELECT c FROM Comment c WHERE c.book.tittle = :tittle")
    public List<Comment> findByBookTittle(@Param("tittle") String tittle);

    @Query("SELECT c FROM Comment c ORDER BY c.book.tittle DESC")
    public List<Comment> showAllinOrder();

    @Query("SELECT c FROM Comment c ORDER BY c.pageNumber")
    public List<Comment> showAllinPageOrder();

    public List<Comment> findByBookId(Long bookId);

}
