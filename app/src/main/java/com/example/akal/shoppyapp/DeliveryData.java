package com.example.akal.shoppyapp;

/**
 * Created by Gursimran on 11-11-2017.
 */

public class DeliveryData {
    private String Username, Email, Street, City, State, Postal, Country;

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public DeliveryData(String username, String email, String street, String city, String state, String postal, String country) {
        Username = username;

        Email = email;
        Street = street;
        City = city;
        State = state;
        Postal = postal;
        Country = country;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getPostal() {
        return Postal;
    }

    public void setPostal(String postal) {
        Postal = postal;
    }
    @Override
    public String toString() {
        return "DeliveryData{" +
                "Street='" + Street + '\'' +
                ", City='" + City + '\'' +
                ", State='" + State + '\'' +
                ", Postal='" + Postal + '\'' +
                ", Country='" + Country + '\'' +
                ", Email='" + Email + '\'' +
                ", Username='" + Username + '\'' +
                '}';
    }
}
