package ru.nsu.ccfit.tihomolov.digital_library.models.mapper;

import ru.nsu.ccfit.tihomolov.digital_library.models.dto.AuthorDto;
import ru.nsu.ccfit.tihomolov.digital_library.models.dto.BookDto;
import ru.nsu.ccfit.tihomolov.digital_library.models.entity.jpa.Author;
import ru.nsu.ccfit.tihomolov.digital_library.models.entity.jpa.Book;
import ru.nsu.ccfit.tihomolov.digital_library.util.ContextSpecialSymbols;

import java.util.stream.Collectors;

public class MapperAuthor {
    public static void editEntity(Author author, AuthorDto authorDto) {
        author.setBiography(authorDto.getBiography());
        author.setBrithDate(authorDto.getBrithDate());
        author.setName(authorDto.getName());
    }

    public static Author mapperToEntity(AuthorDto authorDto) {
        Author author = new Author();
        author.setBiography(authorDto.getBiography());
        author.setBrithDate(authorDto.getBrithDate());
        author.setName(authorDto.getName());
        return author;
    }

    public static AuthorDto mapperToDto(Author author) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setBiography(author.getBiography());
        authorDto.setBooks(author.getBooks().stream().map(MapperBook::mapperToDto).collect(Collectors.toSet()));
        authorDto.setName(author.getName());
        authorDto.setBrithDate(author.getBrithDate());
        return authorDto;
    }
}
