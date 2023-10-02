package ru.nsu.ccfit.tihomolov.digital_library.models.exception;

public class PublisherNotFoundException extends RuntimeException {
    public PublisherNotFoundException(String message) {
        super("api.digital-library.book-info.by-name.api.response.404 " + message);
    }
}
