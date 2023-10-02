package ru.nsu.ccfit.tihomolov.digital_library.models.entity.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate brithDate;

    @Column
    private String biography;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_info_id"))
    private Set<Book> books;

    public Author() {}

    public Author(String name) {
        this.name = name;
    }
}
