package org.openjfx.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.openjfx.model.Offer;

import static org.openjfx.services.FileSystemService.getPathToOffer;


public class OfferService {
    private static ObjectRepository<Offer> offerRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToOffer("offers-database.db").toFile())
                .openOrCreate("test", "test");

        offerRepository = database.getRepository(Offer.class);
    }

    public static void addOffer(String id, String nameOfAgency, String nameOfOffer, String destination, String hotelName, String meals, String nights, String noOfClients, String price) {
        offerRepository.insert(new Offer(id, nameOfAgency,nameOfOffer,destination,hotelName,meals,nights,noOfClients,price));
    }

    public static ObjectRepository<Offer> getOfferRepository() {
        return offerRepository;
    }
}