package org.example.app.services;

import java.util.List;

public interface ProjectRepository<T> {
    List<T> retreiveAll(Boolean filtered);

    void store(T book);

    boolean removeItemById(Integer bookIdToRemove);

    boolean removeItemByRegularExpression(String author, String title, String size);

    boolean filterItemByRegularExpression(String author, String title, String size);
}
