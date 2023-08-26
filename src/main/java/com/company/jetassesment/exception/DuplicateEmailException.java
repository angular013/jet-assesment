package com.company.jetassesment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;



@ResponseStatus(HttpStatus.NOT_FOUND)
public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String email) {
        super("Employee with email " + email + " not found");
    }
}
