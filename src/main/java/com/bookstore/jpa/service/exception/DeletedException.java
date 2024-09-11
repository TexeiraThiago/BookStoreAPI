package com.bookstore.jpa.service.exception;

public class DeletedException extends RuntimeException{

    public DeletedException() {
        super("Item already deleted");
    }
}
