package ru.nsu.ccfit.tihomolov.digital_library.models.entity.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private Short year;

    @Column
    private String description;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Book> book = new HashSet<>();

    public Publisher() {}

    public Publisher(String title) {
        this.title = title;
    }
}
