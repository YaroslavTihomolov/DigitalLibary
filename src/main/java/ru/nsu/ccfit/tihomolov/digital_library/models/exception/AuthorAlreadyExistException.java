package ru.nsu.ccfit.tihomolov.digital_library.models.exception;

public class AuthorAlreadyExistException extends RuntimeException {
    public AuthorAlreadyExistException(String message) {
        super("api.digital-library.book-info.add.api.response.404 " + message);
    }
}
