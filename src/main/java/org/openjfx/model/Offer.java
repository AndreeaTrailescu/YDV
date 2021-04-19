package org.openjfx.model;


import org.dizitart.no2.objects.Id;

public class Offer {
    @Id
    private String id;
    private String nameOfAgency, nameOfOffer, destination, hotelName, meals, nights, noOfClients, price;

    public Offer(String id, String nameOfAgency, String nameOfOffer, String destination, String hotelName, String meals, String nights, String noOfClients, String price) {
        this.id = id;
        this.nameOfAgency = nameOfAgency;
        this.nameOfOffer = nameOfOffer;
        this.destination = destination;
        this.hotelName = hotelName;
        this.meals = meals;
        this.nights = nights;
        this.noOfClients = noOfClients;
        this.price = price;
    }

    public Offer() {
    }

    public String getNameOfOffer() {
        return nameOfOffer;
    }

    public void setNameOfOffer(String nameOfOffer) {
        this.nameOfOffer = nameOfOffer;
    }

    public String getNameOfAgency() {
        return nameOfAgency;
    }

    public void setNameOfAgency(String nameOfAgency) {
        this.nameOfAgency = nameOfAgency;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getNoOfClients() {
        return noOfClients;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setNoOfClients(String noOfClients) {
        this.noOfClients = noOfClients;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getNights() {
        return nights;
    }

    public void setNights(String nights) {
        this.nights = nights;
    }

    public String getMeals() {
        return meals;
    }

    public void setMeals(String meals) {
        this.meals = meals;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}