package ru.nsu.ccfit.tihomolov.digital_library.services;

import java.util.Set;

public interface LibraryService<T> {
    void add(T typeTDto);

    T getInfoByTitle(String title);

    void edit(String oldTitle, T typeTDto);

    void delete(String title);

    Set<T> getPage(Integer numberPage);
}
