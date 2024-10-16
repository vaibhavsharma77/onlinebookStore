package com.bnp.onlinebookstore.exception;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException(String userName) {
        super("user name is "+ userName+"Already Exists");
    }
}
