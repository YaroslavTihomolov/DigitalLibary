package ru.nsu.ccfit.tihomolov.digital_library.services;

import ru.nsu.ccfit.tihomolov.digital_library.models.dto.AuthorDto;
import ru.nsu.ccfit.tihomolov.digital_library.models.dto.BookDto;
import ru.nsu.ccfit.tihomolov.digital_library.models.dto.PublisherDto;

import java.util.ArrayList;

public class Mock {
    public static BookDto getBookInfo() {
        return new BookDto();
    }

    public static boolean addBook(BookDto bookDto) {
        return true;
    }

    public static boolean addPublisher(PublisherDto publisherDto) {
        return true;
    }

    public static boolean addAuthor(AuthorDto authorDto) {
        return true;
    }

    public static ArrayList<BookDto> getBooksFromPage(Integer page) {
        return new ArrayList<BookDto>();
    }

    public static ArrayList<PublisherDto> getPublisherFromPage(Integer page) {
        return new ArrayList<PublisherDto>();
    }

    public static ArrayList<AuthorDto> getAuthorsFromPage(Integer page) {
        return new ArrayList<AuthorDto>();
    }


    public static PublisherDto getPublisherInfo() {
        return new PublisherDto();
    }

    public static AuthorDto getAuthorInfo() {
        return new AuthorDto();
    }

    public static boolean editPublisher(PublisherDto publisherDto) {
        return true;
    }

    public static boolean editBook(BookDto bookDto) {
        return true;
    }

    public static boolean editAuthor(AuthorDto AuthorDto) {
        return true;
    }

    public static boolean deletePublisher(String title) {
        return true;
    }

    public static boolean deleteBook(String title) {
        return true;
    }

    public static boolean deleteAuthor(String name) {
        return true;
    }


}
