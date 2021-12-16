package com.jscode.myBooks.Validations;

import com.jscode.myBooks.Errors.ServiceError;

public class Validation {
    public void validateString(String s) throws ServiceError{
        if(s == null || s.isEmpty()){
            throw new ServiceError("Este campo no puede quedar vacío");
        }
    }

    public void validateNumber(Long n) throws ServiceError{
        if(n == null){
            throw new ServiceError("Este campo no puede quedar vacío");
        }
    }
}
