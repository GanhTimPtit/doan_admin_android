package com.example.king.managebook.exception;

import java.io.IOException;

public class NoInternetConnectionException extends IOException {
    public NoInternetConnectionException() {
        super("No internet connection");
    }
}
