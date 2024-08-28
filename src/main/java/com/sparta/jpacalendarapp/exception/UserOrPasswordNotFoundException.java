package com.sparta.jpacalendarapp.exception;

import java.util.NoSuchElementException;

public class UserOrPasswordNotFoundException extends NoSuchElementException {
    public UserOrPasswordNotFoundException(String msg) {
        super(msg);
    }
}
