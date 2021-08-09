package org.example.app.services;

import org.example.web.dto.BookToFilter;
import org.example.web.dto.BookToRemove;

import java.util.List;

public interface ProjectRepository<T> {
    List<T> retreiveAll();

    void store(T book);

    boolean removeItemById(Integer bookIdToRemove);

    boolean removeItemByRegularExpression(BookToRemove bookToRemove);

    List<T> filterItemByRegularExpression(BookToFilter bookToFilter);
}
