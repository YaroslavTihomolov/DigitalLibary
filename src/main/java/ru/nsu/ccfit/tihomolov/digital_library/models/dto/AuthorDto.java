package ru.nsu.ccfit.tihomolov.digital_library.models.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.nsu.ccfit.tihomolov.digital_library.util.ContextValidation;

import java.time.LocalDate;
import java.util.Set;

@Data
public class AuthorDto {

    @Size(min = ContextValidation.MIN_WORD_LENGTH, max = ContextValidation.MAX_WORD_LENGTH)
    @NotEmpty(message = "{validation.digital-library.title.not-empty}")
    private String name;

    private LocalDate brithDate;

    @Size(min = ContextValidation.MIN_DESCRIPTION_LENGTH, max = ContextValidation.MAX_DESCRIPTION_LENGTH)
    @NotEmpty(message = "{validation.digital-library.description.not-empty}")
    private String biography;

    private Set<BookDto> books;
}
