package ru.nsu.ccfit.tihomolov.digital_library.models.exception;

import java.util.NoSuchElementException;

public class BookNotFoundException extends NoSuchElementException {
    public BookNotFoundException(String message) {
        super("api.digital-library.book-info.by-name.api.response.404 " + message);
    }
}
