package com.sooft.test.exception;

public class CompanyNotFoundException extends Exception {

    Long id;

    public CompanyNotFoundException (Long id) {
        super("Company not found for id: " + id);
        this.id = id;
    }
}
