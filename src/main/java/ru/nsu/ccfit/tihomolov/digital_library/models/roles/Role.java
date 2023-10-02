package ru.nsu.ccfit.tihomolov.digital_library.models.roles;

public interface Role {
    boolean includes(Role role);
}
