package ru.nsu.ccfit.tihomolov.digital_library.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.ccfit.tihomolov.digital_library.models.dto.PublisherDto;
import ru.nsu.ccfit.tihomolov.digital_library.services.Mock;
import ru.nsu.ccfit.tihomolov.digital_library.services.PublisherServiceImpl;
import ru.nsu.ccfit.tihomolov.digital_library.util.ContextValidation;
import java.util.Set;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(value = "/digital_library", produces = APPLICATION_JSON_VALUE)
@Slf4j
public class PublisherController {
    @Autowired
    PublisherServiceImpl publisherService;

    @GetMapping("/publisher/{title}")
    public ResponseEntity<PublisherDto> getInfo(@PathVariable @Size(
            min = ContextValidation.MIN_TITLE_LENGTH,
            max = ContextValidation.MAX_TITLE_LENGTH) String title) {
        log.info("publisher " + title + " info");
        return ResponseEntity.ok(publisherService.getInfoByTitle(title));
    }

    @PostMapping("/manager/publisher/add")
    public ResponseEntity<Void> add(@Valid @RequestBody PublisherDto publisherDto) {
        log.info("add new publisher " + publisherDto);
        publisherService.add(publisherDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/manager/publisher/{title}")
    public ResponseEntity<Void> edit(@PathVariable @Size(
            min = ContextValidation.MIN_TITLE_LENGTH,
            max = ContextValidation.MAX_TITLE_LENGTH) String title, @Valid @RequestBody PublisherDto publisherDto) {
        log.info("edit publisher " + publisherDto);
        publisherService.edit(title, publisherDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/publishers/{page}")
    public ResponseEntity<Set<PublisherDto>> getAuthorPage(@PathVariable @Min(ContextValidation.MIN_PAGE) Integer page) {
        log.info("get publishers from page " + page);
        return ResponseEntity.ok(publisherService.getPage(page));
    }

    @DeleteMapping("/manager/publisher/{title}")
    public ResponseEntity<Void> delete(@PathVariable @Size(
            min = ContextValidation.MIN_TITLE_LENGTH,
            max = ContextValidation.MAX_TITLE_LENGTH) String title) {
        log.info("delete publisher " + title);
        publisherService.delete(title);
        return ResponseEntity.ok().build();
    }
}
