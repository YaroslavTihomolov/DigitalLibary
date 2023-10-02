package ru.nsu.ccfit.tihomolov.digital_library.models.mapper;

import ru.nsu.ccfit.tihomolov.digital_library.models.dto.BookDto;
import ru.nsu.ccfit.tihomolov.digital_library.models.entity.jpa.Author;
import ru.nsu.ccfit.tihomolov.digital_library.models.entity.jpa.Book;
import ru.nsu.ccfit.tihomolov.digital_library.util.ContextSpecialSymbols;

import java.util.stream.Collectors;

public class MapperBook {
    public static void editEntity(Book book, BookDto bookDto) {
        book.setYear(bookDto.getYear());
        book.setTitle(bookDto.getTitle());
        book.setDescription(bookDto.getDescription());
    }

    public static Book mapperToEntity(BookDto bookDto) {
        Book book = new Book();
        book.setYear(bookDto.getYear());
        book.setTitle(bookDto.getTitle());
        book.setDescription(bookDto.getDescription());
        return book;
    }

    public static BookDto mapperToDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setAuthors(book.getAuthors().stream()
                .map(Author::getName)
                .collect(Collectors.toSet()));
        bookDto.setYear(book.getYear());
        bookDto.setPublisher(book.getPublisher().getTitle());
        bookDto.setTitle(book.getTitle());
        bookDto.setDescription(book.getDescription());
        return bookDto;
    }
}
