package ru.nsu.ccfit.tihomolov.digital_library.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.nsu.ccfit.tihomolov.digital_library.models.dto.BookDto;
import ru.nsu.ccfit.tihomolov.digital_library.models.entity.jpa.Author;
import ru.nsu.ccfit.tihomolov.digital_library.models.entity.jpa.Book;
import ru.nsu.ccfit.tihomolov.digital_library.models.entity.jpa.Publisher;
import ru.nsu.ccfit.tihomolov.digital_library.models.exception.BookAlreadyExistException;
import ru.nsu.ccfit.tihomolov.digital_library.models.exception.BookNotFoundException;
import ru.nsu.ccfit.tihomolov.digital_library.models.mapper.MapperBook;
import ru.nsu.ccfit.tihomolov.digital_library.repository.jpa.AuthorRepository;
import ru.nsu.ccfit.tihomolov.digital_library.repository.jpa.BookRepository;
import ru.nsu.ccfit.tihomolov.digital_library.repository.jpa.PublisherRepository;
import ru.nsu.ccfit.tihomolov.digital_library.util.ContextValidation;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class BookServiceImpl implements LibraryService<BookDto> {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private PublisherRepository publisherRepository;

    private void addLinkPublisher(Book book, String publisherName) {
        book.setPublisher(publisherRepository.findByTitle(publisherName).orElse(new Publisher(publisherName)));
    }

    private<T extends RuntimeException> Book findBook(String title, T bookException) {
        return bookRepository.findBookByTitle(title).orElseThrow(() -> bookException);
    }

    private void addLinkAuthors(Book book, Set<String> authors) {
        book.setAuthors(authors.stream()
                .map(authorName -> {
                    Optional<Author> author = authorRepository.findAuthorByName(authorName);
                    return author.orElseGet(() -> new Author(authorName));
                }).
                collect(Collectors.toSet()));
    }


    public BookDto getInfoByTitle(String title) {
        Book book = findBook(title, new BookNotFoundException(title));

        return MapperBook.mapperToDto(book);
    }

    @Transactional
    @Override
    public void add(BookDto bookDto) {
        bookRepository.findBookByTitle(bookDto.getTitle()).ifPresent(
                (book) -> {throw new BookAlreadyExistException(bookDto.getTitle());});

        final Book newBook = MapperBook.mapperToEntity(bookDto);

        addLinkAuthors(newBook, bookDto.getAuthors());
        addLinkPublisher(newBook, bookDto.getPublisher());

        bookRepository.save(newBook);
    }

    @Transactional
    @Override
    public void edit(String title, BookDto bookDto) {
        Book book = bookRepository.findBookByTitle(title).orElseThrow(() -> new BookNotFoundException(title));
        MapperBook.editEntity(book, bookDto);
        addLinkPublisher(book, bookDto.getPublisher());
        addLinkAuthors(book, bookDto.getAuthors());
        bookRepository.save(book);
    }

    @Transactional
    @Override
    public void delete(String title) {
        Book book = findBook(title, new BookNotFoundException(title));
        bookRepository.delete(book);
    }

    @Transactional
    @Override
    public Set<BookDto> getPage(Integer page) {
        return bookRepository.findAll(PageRequest.of(page, ContextValidation.PAGE_SIZE)).stream()
                .map(MapperBook::mapperToDto).collect(Collectors.toSet());
    }
}