package ru.nsu.ccfit.tihomolov.digital_library.models.entity.monodb;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Accessors(chain = true)
@TypeAlias("BookData")
@Document(collection = "dataset")
public class AuthorData {
    @Id
    @Column(nullable = false)
    private String name;

    @Column
    private String imagePath;
}
