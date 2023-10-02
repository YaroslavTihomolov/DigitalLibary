package ru.nsu.ccfit.tihomolov.digital_library.models.entity.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @CollectionTable(joinColumns = @JoinColumn(name = "username"))
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles = new HashSet<>();

}
