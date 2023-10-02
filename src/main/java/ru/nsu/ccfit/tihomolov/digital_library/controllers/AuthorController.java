package ru.nsu.ccfit.tihomolov.digital_library.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.ccfit.tihomolov.digital_library.models.dto.AuthorDto;
import ru.nsu.ccfit.tihomolov.digital_library.services.Mock;
import ru.nsu.ccfit.tihomolov.digital_library.util.ContextValidation;
import java.util.List;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/digital_library", produces = APPLICATION_JSON_VALUE)
@Slf4j
public class AuthorController {
    @GetMapping("/author/{name}")
    public ResponseEntity<AuthorDto> getInfo(@PathVariable @Size(
            min = ContextValidation.MIN_TITLE_LENGTH,
            max = ContextValidation.MAX_TITLE_LENGTH) String name) {
        log.info("author " + name + " info");
        return ResponseEntity.ok(Mock.getAuthorInfo());
    }

    @PostMapping("/manager/author/add")
    public ResponseEntity<Void> add(@Valid @RequestBody AuthorDto authorDto) {
        log.info("add new author " + authorDto);
        Mock.addAuthor(authorDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/manager/author/{name}")
    public ResponseEntity<Void> edit(@Valid @RequestBody AuthorDto authorDto) {
        log.info("edit author " + authorDto);
        Mock.editAuthor(authorDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/authors/{page}")
    public ResponseEntity<List<AuthorDto>> getAuthorPage(@PathVariable @Min(ContextValidation.MIN_PAGE) Integer page) {
        log.info("get authors from page " + page);
        return ResponseEntity.ok(Mock.getAuthorsFromPage(page));
    }

    @DeleteMapping("/manager/author/{name}")
    public ResponseEntity<Void> delete(@PathVariable @Size(
            min = ContextValidation.MIN_TITLE_LENGTH,
            max = ContextValidation.MAX_TITLE_LENGTH) String name) {
        log.info("delete author " + name);
        Mock.deleteAuthor(name);
        return ResponseEntity.ok().build();
    }
}