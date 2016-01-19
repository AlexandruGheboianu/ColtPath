package com.ghb.coltpath.rest;

/**
 * Created by Ghebo on 1/17/2016.
 */
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}