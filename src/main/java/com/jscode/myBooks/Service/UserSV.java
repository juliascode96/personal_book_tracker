package com.jscode.myBooks.Service;

import com.jscode.myBooks.Entity.User;
import com.jscode.myBooks.Errors.ServiceError;
import com.jscode.myBooks.Repository.UserRepo;
import com.jscode.myBooks.Validations.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserSV {
    Validation v = new Validation();

    @Autowired
    UserRepo userRepo;

    @Transactional
    public User saveUser(User user) throws ServiceError{
        validate(user.getUserName(), user.getPassword());
        validatePassword(user.getPassword(), user.getPassword2());
        return userRepo.save(user);
    }


    public Optional<User> searchById(Long id) {
        try {
            v.validateNumber(id);
        } catch (ServiceError e) {
            System.out.println(e);
        }

        Optional<User> respuesta = userRepo.findById(id);
        return respuesta;
    }

    public User searchByName(String username) {
        if (username != null) {
            return userRepo.findByUserName(username);
        } else {
            User u = new User();
            u.setUserName("Prueba");
            return u;
        }

    }

    public void validatePassword(String password, String password2) throws ServiceError{
        if (!password.equals(password2)) {
            ServiceError e = new ServiceError("Las constraseñas deben ser iguales");
            throw e;
        }
    }

    public void validate(String userName, String password) throws ServiceError {
        v.validateString(userName);
        v.validateString(password);
    }
}
