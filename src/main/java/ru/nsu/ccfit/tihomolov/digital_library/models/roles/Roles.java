package ru.nsu.ccfit.tihomolov.digital_library.models.roles;

import java.util.HashSet;
import java.util.Set;

public enum Roles implements Role {
    GUEST,
    USER,
    ADMIN;

    private final Set<Role> children = new HashSet<>();

    static {
        ADMIN.children.add(USER);
        USER.children.add(GUEST);
    }

    @Override
    public boolean includes(Role role) {
        return this.equals(role) || children.stream().anyMatch(r -> r.includes(role));
    }
}
