package org.openjfx.services;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSystemService {
    private static final String APPLICATION_FOLDER = ".registration-database";
    private static final String OFFERS_FOLDER =".offers-database";
    private static final String BOOKINGS_FOLDER =".bookings-database";
    private static final String USER_FOLDER = System.getProperty("user.home");
    public static final Path APPLICATION_HOME_PATH = Paths.get(USER_FOLDER, APPLICATION_FOLDER);
    public static final Path OFFERS_HOME_PATH = Paths.get(USER_FOLDER, OFFERS_FOLDER);
    public static final Path BOOKINGS_HOME_PATH = Paths.get(USER_FOLDER, BOOKINGS_FOLDER);

    public static Path getPathToFile(String... path) {
        return APPLICATION_HOME_PATH.resolve(Paths.get(".", path));
    }
    public static Path getPathToOffer(String... path) {
        return OFFERS_HOME_PATH.resolve(Paths.get(".", path));
    }
    public static Path getPathToBooking(String... path) {
        return BOOKINGS_HOME_PATH.resolve(Paths.get(".", path));
    }
}