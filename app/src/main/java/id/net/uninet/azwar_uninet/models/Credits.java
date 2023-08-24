package id.net.uninet.azwar_uninet.models;

import java.io.Serializable;

public class Credits implements Serializable {
    private String id;
    private String adult;
    private String gender;
    private String known_for_department;
    private String name;
    private String original_name;
    private String popularity;
    private String profile_path;
    private String cast_id;
    private String character;
    private String credit_id;
    private String order;

    public Credits() {

    }

    public Credits(String id, String adult, String gender, String known_for_department, String name, String original_name, String popularity, String profile_path, String cast_id, String character, String credit_id, String order) {
        this.id = id;
        this.adult = adult;
        this.gender = gender;
        this.known_for_department = known_for_department;
        this.name = name;
        this.original_name = original_name;
        this.popularity = popularity;
        this.profile_path = profile_path;
        this.cast_id = cast_id;
        this.character = character;
        this.credit_id = credit_id;
        this.order = order;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getKnown_for_department() {
        return known_for_department;
    }

    public void setKnown_for_department(String known_for_department) {
        this.known_for_department = known_for_department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public String getCast_id() {
        return cast_id;
    }

    public void setCast_id(String cast_id) {
        this.cast_id = cast_id;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getCredit_id() {
        return credit_id;
    }

    public void setCredit_id(String credit_id) {
        this.credit_id = credit_id;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Details{" +
                "id='" + id + '\'' +
                ", adult='" + adult + '\'' +
                ", gender='" + gender + '\'' +
                ", known_for_department='" + known_for_department + '\'' +
                ", name='" + name + '\'' +
                ", original_name='" + original_name + '\'' +
                ", popularity='" + popularity + '\'' +
                ", profile_path='" + profile_path + '\'' +
                ", cast_id='" + cast_id + '\'' +
                ", character='" + character + '\'' +
                ", credit_id='" + credit_id + '\'' +
                ", order='" + order + '\'' +
                '}';
    }
}
