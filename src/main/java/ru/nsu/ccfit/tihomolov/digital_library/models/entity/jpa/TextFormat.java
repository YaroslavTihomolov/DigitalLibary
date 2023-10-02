package ru.nsu.ccfit.tihomolov.digital_library.models.entity.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table
@Entity
public class TextFormat {
    @Id
    private String name;
}
