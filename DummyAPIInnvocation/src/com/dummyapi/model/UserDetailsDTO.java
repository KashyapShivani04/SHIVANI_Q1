package com.dummyapi.model;

public class UserDetailsDTO {

    public String id;
    public String title;
    public String firstName;
    public String lastName;
    public String picture;

    @Override
    public String toString() {
        return "UserDetailsDTO{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
