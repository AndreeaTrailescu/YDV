package org.openjfx.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
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
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.TimeoutException;


@ExtendWith(ApplicationExtension.class)
class RezervationsControllerTest {

    @AfterAll
    static void afterAll() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.OFFERS_FOLDER = ".test-offers-database";
        FileSystemService.initOffersDirectory();
        FileUtils.cleanDirectory(FileSystemService.getOffersHomeFolder().toFile());
        OfferService.initDatabase();
        OfferService.addOffer("20","agency1","offer1","destination","hotel","2","5","100","150");
        OfferService.addOffer("13","agency1","offer2","destination","hotel","2","5","100","150");

        FileSystemService.BOOKINGS_FOLDER = ".test-bookings-database";
        FileSystemService.initBookingDirectory();
        FileUtils.cleanDirectory(FileSystemService.getBookingsHomeFolder().toFile());
        BookingService.initDatabase();
        BookingService.addBooking("1","user1","agency1","offer1","3","600","10-05-2021", "10-05-2021","Accepted:deadline is 10-05-2021","0");
        BookingService.addBooking("2","user2","agency1","offer1","1","300","10-05-2021", "11-05-2021","Your booking hasn't been approved/rejected yet.","0");
        BookingService.addBooking("3","user1","agency2","offer3","2","1000","10-05-2021", "10-05-2021","Rejected","0");
        BookingService.addBooking("4","user1","agency1","offer2","1","800","10-05-2021", "11-05-2021","Your booking hasn't been approved/rejected yet.","0");

    }

    @AfterEach
    void tearDown() throws IOException {
        BookingService.getDatabase().close();
        OfferService.getDatabase().close();
    }

    @Start
    void start(Stage stage) throws IOException {
        BookingService.initDatabase();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("travelAgentPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        BookingService.getDatabase().close();
    }

    @Test
    void listOfBookingsTest(FxRobot robot) {
        RezervationsController.setNameOfAgency("agency1");
        robot.clickOn("#bookListButtonAgent");

        robot.clickOn("#tableOfBookings");
        robot.type(KeyCode.UP);
        robot.type(KeyCode.ENTER);
        robot.moveTo("offer1").doubleClickOn("offer1");

        robot.clickOn("#acceptButton");
        robot.moveTo(900,310).clickOn();
        robot.moveTo(750,500).clickOn();
        robot.clickOn("#saveAcceptButton");

        RezervationsController.setNameOfAgency("agency1");
        robot.clickOn("#tableOfBookings");
        robot.type(KeyCode.DOWN);
        robot.moveTo(640,260).doubleClickOn().doubleClickOn();

        robot.clickOn("#acceptButton");
        robot.clickOn("#closeAcceptButton");
        robot.clickOn("#closeBookDetailsButton");
        robot.clickOn("#logoutButtonBooking");
    }
}