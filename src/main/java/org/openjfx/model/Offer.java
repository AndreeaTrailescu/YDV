package org.openjfx.model;

public class Offer {
    private String nameOfAgency, nameOfOffer, destination, hotelName, meals, nights, noOfClients, price;

    public Offer(String nameOfAgency, String nameOfOffer, String destination, String hotelName, String meals, String nights, String noOfClients, String price) {
        this.nameOfAgency = nameOfAgency;
        this.nameOfOffer = nameOfOffer;
        this.destination = destination;
        this.hotelName = hotelName;
        this.meals = meals;
        this.nights = nights;
        this.noOfClients = noOfClients;
        this.price = price;
    }
}
