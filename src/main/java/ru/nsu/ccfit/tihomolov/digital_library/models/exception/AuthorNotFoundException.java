package ru.nsu.ccfit.tihomolov.digital_library.models.exception;

import java.util.NoSuchElementException;

public class AuthorNotFoundException extends NoSuchElementException {
    public AuthorNotFoundException(String message) {
        super("api.digital-library.book-info.by-name.api.response.404 " + message);
    }
}
