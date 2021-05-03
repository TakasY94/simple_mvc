package org.example.web.dto;

public class Book {

    private String author;
    private String title;
    private String size;
    private Integer Id;

    public Book(String author, String title, String size, Integer id) {
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
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
