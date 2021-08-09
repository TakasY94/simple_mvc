package org.example.web.dto;

import org.example.web.validators.AtLeastOneFieldNotNull;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@AtLeastOneFieldNotNull(fieldNames={"author","title", "size"})
public class BookToFilter {

    @Size(max=100)
    private String author;
    @Size(max=100)
    private String title;
    private Integer size;



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
}

