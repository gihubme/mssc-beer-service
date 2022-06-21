package org.nnn4eu.mssc.msscbeerservice.web.controller;

public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = -6798981475833483401L;
    private static String defaultMessage = "Not found";

    public NotFoundException() {
        super(defaultMessage);
    }

    public NotFoundException(String message) {
        super(message);
    }
}
