package ru.nsu.ccfit.tihomolov.digital_library.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.ccfit.tihomolov.digital_library.models.dto.BookDto;
import ru.nsu.ccfit.tihomolov.digital_library.services.BookServiceImpl;
import ru.nsu.ccfit.tihomolov.digital_library.util.ContextSpecialSymbols;
import ru.nsu.ccfit.tihomolov.digital_library.util.ContextValidation;

import java.util.Set;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/digital_library", produces = APPLICATION_JSON_VALUE)
@Slf4j
@Tag(name = "api.digital-library.tag.name", description = "api.digital-library.tag.description")
public class BookController {
    @Autowired
    BookServiceImpl bookService;

    @Operation(summary = "api.digital-library.book.info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "api-digital-library.return-code.ok",
                    description = "api-digital-library.return-code.ok.description")
    })
    @GetMapping("/book/{title}")
    public ResponseEntity<BookDto> getInfo(@PathVariable @Size(
            min = ContextValidation.MIN_TITLE_LENGTH,
            max = ContextValidation.MAX_TITLE_LENGTH) String title) {
        log.info("get book " + title + " info");
        return ResponseEntity.ok(bookService.getInfoByTitle(ContextSpecialSymbols.parseTitle(title)));
    }

    @Operation(summary = "api.digital-library.book.add")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "api-digital-library.return-code.ok",
                    description = "api-digital-library.return-code.ok.description")
    })
    @PostMapping("/manager/book/add")
    public ResponseEntity<Void> add(@Valid @RequestBody BookDto bookDto) {
        log.info("add new book " + bookDto);
        bookService.add(bookDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/manager/book/{title}")
    public ResponseEntity<Void> edit(@Valid @RequestBody BookDto bookDto, @PathVariable @Size(
            min = ContextValidation.MIN_TITLE_LENGTH,
            max = ContextValidation.MAX_TITLE_LENGTH) String title) {
        log.info("edit book " + bookDto);
        bookService.edit(ContextSpecialSymbols.parseTitle(title), bookDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/books/{page}")
    public ResponseEntity<Set<BookDto>> getBooksPage(@PathVariable @Min(ContextValidation.MIN_PAGE) Integer page) {
        log.info("get books from page " + page);
        return ResponseEntity.ok(bookService.getPage(page));
    }

    @DeleteMapping("/manager/book/{title}")
    public ResponseEntity<Void> delete(@PathVariable @Size(
            min = ContextValidation.MIN_TITLE_LENGTH,
            max = ContextValidation.MAX_TITLE_LENGTH) String title) {
        log.info("delete book " + title);
        bookService.delete(ContextSpecialSymbols.parseTitle(title));
        return ResponseEntity.ok().build();
    }
}
