package ru.nsu.ccfit.tihomolov.digital_library.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.nsu.ccfit.tihomolov.digital_library.models.dto.UserDto;
import ru.nsu.ccfit.tihomolov.digital_library.models.dto.UsernameDto;
import ru.nsu.ccfit.tihomolov.digital_library.services.UserService;
import ru.nsu.ccfit.tihomolov.digital_library.util.ContextValidation;

import java.util.Set;

@RestController
@RequestMapping("/digital_library/admin")
@Validated
@Slf4j
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("users")
    public ResponseEntity<Set<UsernameDto>> users() {
        log.info("get users");
        return ResponseEntity.ok(userService.userList());
    }

    @PostMapping("create")
    public ResponseEntity<UsernameDto> createUser(@RequestBody @Valid UserDto userDto) {
        log.info("create user " + userDto);
        return ResponseEntity.ok(userService.create(userDto));
    }

    @DeleteMapping("delete/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable @Size(
            min = ContextValidation.USERNAME_MIN_LENGTH,
            max = ContextValidation.USERNAME_MAX_LENGTH) String username) {
        log.info("delete user " + username);
        userService.delete(username);
        return ResponseEntity.ok().build();
    }
}
