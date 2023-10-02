package ru.nsu.ccfit.tihomolov.digital_library.services;

import org.springframework.stereotype.Service;
import ru.nsu.ccfit.tihomolov.digital_library.models.dto.UserDto;
import ru.nsu.ccfit.tihomolov.digital_library.models.dto.UsernameDto;

import java.util.List;
import java.util.Set;

public interface UserService {
    Set<UsernameDto> userList();

    UserDto findByUserName(String username);

    UsernameDto create(UserDto userDto);

    void delete(String username);
}