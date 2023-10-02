package ru.nsu.ccfit.tihomolov.digital_library.models.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.nsu.ccfit.tihomolov.digital_library.util.ContextValidation;

@Data
public class PublisherDto {

    @Size(min = ContextValidation.MIN_TITLE_LENGTH, max = ContextValidation.MAX_TITLE_LENGTH)
    @NotEmpty(message = "{validation.digital-library.title.not-empty}")
    private String title;

    @Min(ContextValidation.MIN_YEAR)
    @Max(ContextValidation.CURRENT_YEAR)
    @NotEmpty(message = "{validation.digital-library.past-date}")
    private Short year;

    @Size(min = ContextValidation.MIN_DESCRIPTION_LENGTH, max = ContextValidation.MAX_DESCRIPTION_LENGTH)
    @NotEmpty(message = "{validation.digital-library.description.not-empty}")
    private String description;
}
