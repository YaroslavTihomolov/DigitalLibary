package ru.nsu.ccfit.tihomolov.digital_library.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nsu.ccfit.tihomolov.digital_library.models.dto.UserDto;
import ru.nsu.ccfit.tihomolov.digital_library.models.dto.UsernameDto;
import ru.nsu.ccfit.tihomolov.digital_library.models.entity.jpa.Users;
import ru.nsu.ccfit.tihomolov.digital_library.models.exception.UserAlreadyExistException;
import ru.nsu.ccfit.tihomolov.digital_library.repository.jpa.UserRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Set<UsernameDto> userList() {
        log.info("get users");
        return userRepository.findAll().stream()
                .map(users -> new UsernameDto(users.getUsername()))
                .collect(Collectors.toSet());
    }

    @Override
    public UserDto findByUserName(String username) {
        log.info("find user " + username);
        return userRepository.findById(username)
                .map(users -> new UserDto(users.getUsername(), users.getPassword(), users.getRoles()))
                .orElseThrow(() -> new UsernameNotFoundException(username + " user not found"));
    }

    @Override
    public UsernameDto create(UserDto userDto) {
        log.info("create new user " + userDto);
        userRepository.findById(userDto.getUsername())
                .ifPresent((users) -> { throw new UserAlreadyExistException(userDto.getUsername());});
        Users users = new Users(userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()), userDto.getRoles());
        log.info("user create successfully");
        userRepository.save(users);
        return new UsernameDto(userDto.getUsername());
    }

    @Override
    public void delete(String username) {
        Users users = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " user not found"));
        userRepository.delete(users);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = findByUserName(username);
        return org.springframework.security.core.userdetails.User.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .roles(userDto.getRoles().toArray(String[]::new))
                .build();
    }
}
