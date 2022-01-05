package com.jscode.myBooks.Repository;

import com.jscode.myBooks.Entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    public User findByUserName(String name);
}
