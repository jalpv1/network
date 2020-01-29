package com.example.demo.services.exeption;

public class IdNotFoundException extends Exception {
    public IdNotFoundException() {
        super("Wrong id ");
    }
}
