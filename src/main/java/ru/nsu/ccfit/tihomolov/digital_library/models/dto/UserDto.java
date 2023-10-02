package ru.nsu.ccfit.tihomolov.digital_library.models.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.tihomolov.digital_library.models.roles.Roles;
import ru.nsu.ccfit.tihomolov.digital_library.util.ContextValidation;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    @NotEmpty
    @Size(min = ContextValidation.USERNAME_MIN_LENGTH, max = ContextValidation.USERNAME_MAX_LENGTH)
    private String username;

    @NotEmpty
    @Size(min = ContextValidation.PASSWORD_MIN_LENGTH, max = ContextValidation.PASSWORD_MAX_LENGTH)
    private String password;

    @NotEmpty
    private Set<String> roles;
}
