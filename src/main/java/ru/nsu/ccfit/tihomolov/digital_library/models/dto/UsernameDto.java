package ru.nsu.ccfit.tihomolov.digital_library.models.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.tihomolov.digital_library.util.ContextValidation;

@AllArgsConstructor
@Getter
@Setter
public class UsernameDto {
    @NotEmpty
    @Size(min = ContextValidation.USERNAME_MIN_LENGTH, max = ContextValidation.USERNAME_MAX_LENGTH)
    private String username;
}
