package org.openjfx.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.openjfx.model.Booking;
import org.openjfx.model.Offer;

import java.util.List;

import static org.openjfx.services.FileSystemService.getPathToBooking;
import static org.openjfx.services.FileSystemService.getPathToOffer;

public class BookingService {
    private static ObjectRepository<Booking> bookingRepository;
    private static Nitrite database;

    public static void initDatabase() {
        FileSystemService.initBookingDirectory();
        database = Nitrite.builder()
                .filePath(getPathToBooking("bookings-database.db").toFile())
                .openOrCreate("test", "test");

        bookingRepository = database.getRepository(Booking.class);
    }

    public static void addBooking(String id, String clientUsername, String nameOfAgency, String nameOfOffer, String numberOfPersons, String totalPrice, String checkInDate, String checkOutDate, String message, String rating) {
        bookingRepository.insert(new Booking(id,clientUsername,nameOfAgency,nameOfOffer,numberOfPersons,totalPrice,checkInDate,checkOutDate,message,rating));
    }

    public static Nitrite getDatabase() {
        return database;
    }

    public static ObjectRepository<Booking> getBookingRepository() {
        return bookingRepository;
    }

    public static List<Booking> getAllBookings() {
        return bookingRepository.find().toList();
    }
}
