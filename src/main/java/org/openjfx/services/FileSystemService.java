package org.openjfx.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSystemService {
    public static String APPLICATION_FOLDER = ".registration-database";
    private static final String OFFERS_FOLDER =".offers-database";
    private static final String BOOKINGS_FOLDER =".bookings-database";
    private static final String USER_FOLDER = System.getProperty("user.home");
    public static final Path OFFERS_HOME_PATH = Paths.get(USER_FOLDER, OFFERS_FOLDER);
    public static final Path BOOKINGS_HOME_PATH = Paths.get(USER_FOLDER, BOOKINGS_FOLDER);

    public static Path getPathToFile(String... path) {
        return getApplicationHomeFolder().resolve(Paths.get(".", path));
    }
    public static Path getApplicationHomeFolder() {
        return Paths.get(USER_FOLDER, APPLICATION_FOLDER);
    }

    static void initDirectory() {
        Path applicationHomePath = FileSystemService.getApplicationHomeFolder();
        if (!Files.exists(applicationHomePath))
            applicationHomePath.toFile().mkdirs();

        /*Path offersHomePath = FileSystemService.OFFERS_HOME_PATH;
        if (!Files.exists(offersHomePath))
            offersHomePath.toFile().mkdirs();

        Path bookingsHomePath = FileSystemService.BOOKINGS_HOME_PATH;
        if (!Files.exists(bookingsHomePath))
            bookingsHomePath.toFile().mkdirs();*/
    }

    public static Path getPathToOffer(String... path) {
        return OFFERS_HOME_PATH.resolve(Paths.get(".", path));
    }
    public static Path getPathToBooking(String... path) {
        return BOOKINGS_HOME_PATH.resolve(Paths.get(".", path));
    }
}