package id.net.uninet.azwar_uninet.models;

import java.io.Serializable;

public class Author implements Serializable {
    private String name;
    private String username;
    private String avatar_path;
    private String rating;

    public Author() {

    }

    public Author(String name, String username, String avatar_path, String rating) {
        this.name = name;
        this.username = username;
        this.avatar_path = avatar_path;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar_path() {
        return avatar_path;
    }

    public void setAvatar_path(String avatar_path) {
        this.avatar_path = avatar_path;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
