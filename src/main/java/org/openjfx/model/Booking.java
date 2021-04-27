package org.openjfx.model;

import org.dizitart.no2.objects.Id;

public class Booking {
    @Id
    private String id;
    private String clientUsername, nameOfAgency, nameOfOffer, numberOfPersons, totalPrice, checkInDate, checkOutDate, message;

    public Booking(String id, String clientUsername, String nameOfAgency, String nameOfOffer, String numberOfPersons, String totalPrice, String checkInDate, String checkOutDate, String message) {
        this.id = id;
        this.clientUsername = clientUsername;
        this.nameOfAgency = nameOfAgency;
        this.nameOfOffer = nameOfOffer;
        this.numberOfPersons = numberOfPersons;
        this.totalPrice = totalPrice;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.message = message;
    }

    public Booking(){

    }

    public String getNumberOfPersons() {
        return numberOfPersons;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public String getMessage() {
        return message;
    }

    public String getNameOfAgency() {
        return nameOfAgency;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public String getNameOfOffer() {
        return nameOfOffer;
    }

    public String getId() {
        return id;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
