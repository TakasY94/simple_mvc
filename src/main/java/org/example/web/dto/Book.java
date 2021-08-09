package org.example.web.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Book {
    @NotBlank
    @Size(min=2)
    private String author;
    @NotBlank
    @Size(min=2)
    private String title;
    @Digits(integer = 4, fraction = 0)
    private Integer size;
    private Integer Id;

    public Book(String author, String title, Integer size, Integer id) {
        this.author = author;
        this.title = title;
        this.size = size;
        Id = id;
    }

    public Book() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    @Override
    public String toString() {
        return "Book{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", size=" + size +
                ", Id=" + Id +
                '}';
    }
}
