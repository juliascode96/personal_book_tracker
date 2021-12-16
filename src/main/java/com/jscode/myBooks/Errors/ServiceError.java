package com.jscode.myBooks.Errors;

public class ServiceError extends Exception{

    public ServiceError(String msn) {
        super(msn);
    }
}
