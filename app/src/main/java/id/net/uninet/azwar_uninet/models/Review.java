package id.net.uninet.azwar_uninet.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Review implements Serializable {
    private String id;
    private String content;
    private String created_at;
    private String author;
    private String updated_at;
    private Author author_details;

    public Review() {

    }

    public Review(String id, String content, String created_at, String author, String updated_at, Author author_details) {
        this.id = id;
        this.content = content;
        this.created_at = created_at;
        this.author = author;
        this.updated_at = updated_at;
        this.author_details = author_details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Author getAuthor_details() {
        return author_details;
    }

    public void setAuthor_details(Author author_details) {
        this.author_details = author_details;
    }
}
