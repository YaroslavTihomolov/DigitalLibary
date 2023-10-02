package ru.nsu.ccfit.tihomolov.digital_library.services;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.nsu.ccfit.tihomolov.digital_library.models.dto.AuthorDto;
import ru.nsu.ccfit.tihomolov.digital_library.models.dto.BookDto;
import ru.nsu.ccfit.tihomolov.digital_library.models.entity.jpa.Author;
import ru.nsu.ccfit.tihomolov.digital_library.models.entity.jpa.Book;
import ru.nsu.ccfit.tihomolov.digital_library.models.exception.AuthorNotFoundException;
import ru.nsu.ccfit.tihomolov.digital_library.models.exception.BookAlreadyExistException;
import ru.nsu.ccfit.tihomolov.digital_library.models.mapper.MapperAuthor;
import ru.nsu.ccfit.tihomolov.digital_library.models.mapper.MapperBook;
import ru.nsu.ccfit.tihomolov.digital_library.repository.jpa.AuthorRepository;
import ru.nsu.ccfit.tihomolov.digital_library.repository.jpa.BookRepository;
import ru.nsu.ccfit.tihomolov.digital_library.repository.jpa.PublisherRepository;
import ru.nsu.ccfit.tihomolov.digital_library.util.ContextValidation;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthorServiceImpl implements LibraryService<AuthorDto> {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private PublisherRepository publisherRepository;

    private<T extends RuntimeException> Author findAuthor(String name, T authorException) {
        return authorRepository.findAuthorByName(name).orElseThrow(() -> authorException);
    }

    private void addLinkBooks(Author author, Set<BookDto> books) {
        author.setBooks(books.stream()
                .map(book -> {
                    Optional<Book> newBook = bookRepository.findBookByTitle(book.getTitle());
                    return newBook.orElseGet(() -> MapperBook.mapperToEntity(book));
                })
                .collect(Collectors.toSet()));
    }

    @Transactional
    @Override
    public void add(AuthorDto authorDto) {
        log.info("add " + authorDto);
        authorRepository.findAuthorByName(authorDto.getName()).ifPresent(
                (book) -> {throw new BookAlreadyExistException(authorDto.getName());});

        final Author newAuthor = MapperAuthor.mapperToEntity(authorDto);
        addLinkBooks(newAuthor, authorDto.getBooks());

        authorRepository.save(newAuthor);
    }

    @Override
    public AuthorDto getInfoByTitle(String name) {
        Author author = findAuthor(name, new AuthorNotFoundException(name));
        return MapperAuthor.mapperToDto(author);
    }

    @Override
    public void edit(String name, AuthorDto authorDto) {
        Author author = authorRepository.findAuthorByName(name).orElseThrow(() -> new AuthorNotFoundException(name));
        MapperAuthor.editEntity(author, authorDto);
        addLinkBooks(author, authorDto.getBooks());
        authorRepository.save(author);
    }

    @Override
    public void delete(String name) {
        Author author = findAuthor(name, new AuthorNotFoundException(name));
        authorRepository.delete(author);

    }

    @Override
    public Set<AuthorDto> getPage(Integer page) {
        return authorRepository.findAll(PageRequest.of(page, ContextValidation.PAGE_SIZE)).stream()
                .map(MapperAuthor::mapperToDto).collect(Collectors.toSet());
    }
}
