package org.openjfx.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSystemService {
    public static String APPLICATION_FOLDER = ".registration-database";
    public static String OFFERS_FOLDER =".offers-database";
    public static String BOOKINGS_FOLDER =".bookings-database";
    private static final String USER_FOLDER = System.getProperty("user.home");

    public static Path getPathToFile(String... path) {
        return getApplicationHomeFolder().resolve(Paths.get(".", path));
    }

    public static Path getPathToOffer(String... path) {
        return getOffersHomeFolder().resolve(Paths.get(".", path));
    }

    public static Path getApplicationHomeFolder() {
        return Paths.get(USER_FOLDER, APPLICATION_FOLDER);
    }

    public static Path getOffersHomeFolder(){
        return Paths.get(USER_FOLDER,OFFERS_FOLDER);
    }

    public static Path getBookingsHomeFolder() { return Paths.get(USER_FOLDER,BOOKINGS_FOLDER); }

    public static void initDirectory() {
        Path applicationHomePath = FileSystemService.getApplicationHomeFolder();
        if (!Files.exists(applicationHomePath))
            applicationHomePath.toFile().mkdirs();
    }

    public static void initBookingDirectory() {
        Path bookingsHomePath = FileSystemService.getBookingsHomeFolder();
        if (!Files.exists(bookingsHomePath))
            bookingsHomePath.toFile().mkdirs();
    }

    public static void initOffersDirectory(){
        Path offersHomePath = FileSystemService.getOffersHomeFolder();
        if (!Files.exists(offersHomePath))
            offersHomePath.toFile().mkdirs();
    }

    public static Path getPathToBooking(String... path) {
        return getBookingsHomeFolder().resolve(Paths.get(".", path));
    }
}