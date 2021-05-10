package org.openjfx.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openjfx.services.BookingService;
import org.openjfx.services.FileSystemService;
import org.openjfx.services.OfferService;
import org.openjfx.services.UserService;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@ExtendWith(ApplicationExtension.class)
class HistoryBookingControllerTest {

    @AfterAll
    static void afterAll() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-registration-database";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();

        FileSystemService.OFFERS_FOLDER = ".test-offers-database";
        FileSystemService.initOffersDirectory();
        FileUtils.cleanDirectory(FileSystemService.getOffersHomeFolder().toFile());
        OfferService.initDatabase();

        FileSystemService.BOOKINGS_FOLDER = ".test-bookings-database";
        FileSystemService.initBookingDirectory();
        FileUtils.cleanDirectory(FileSystemService.getBookingsHomeFolder().toFile());
        BookingService.initDatabase();
        BookingService.addBooking("1","user1","agency1","offer1","3","600","10-05-2021", "10-05-2021","Accepted:deadline is 10-05-2021","0");
        BookingService.addBooking("2","user2","agency1","offer1","1","300","10-05-2021", "11-05-2021","Rejected:no more places available","0");
        BookingService.addBooking("3","user1","agency2","offer3","2","1000","10-05-2021", "10-05-2021","Rejected:no more places available","0");
        BookingService.addBooking("4","user1","agency1","offer2","1","800","10-05-2021", "11-05-2021","Your booking hasn't been approved/rejected yet.","0");
        HomePageController.setUsername("user1");
        HistoryBookingController.setUsername("user1");
    }

    @AfterEach
    void tearDown() throws IOException {
        BookingService.getDatabase().close();
        OfferService.getDatabase().close();
        UserService.getDatabase().close();
    }

    @Start
    void start(Stage stage) throws IOException {
        UserService.initDatabase();
        OfferService.initDatabase();
        BookingService.initDatabase();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("homePage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        BookingService.getDatabase().close();
        OfferService.getDatabase().close();
        UserService.getDatabase().close();
    }

    @Test
    void historyBookingAndRatingTest(FxRobot robot){
        robot.clickOn("#bookListButtonHome");
        robot.moveTo(700,250).doubleClickOn();
        robot.moveTo(700, 300).doubleClickOn();

        robot.clickOn("#rating");
        robot.write("6");
        robot.clickOn("#saveRatingButton");
    }
}