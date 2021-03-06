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
import org.testfx.framework.junit5.Stop;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)
class AddOfferControllerTest {

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
        FileSystemService.BOOKINGS_FOLDER = ".test-bookings-database";
        FileSystemService.initBookingDirectory();
        FileUtils.cleanDirectory(FileSystemService.getBookingsHomeFolder().toFile());
        BookingService.initDatabase();
    }

    @AfterEach
    void tearDown() throws IOException {
        OfferService.getDatabase().close();
        BookingService.getDatabase().close();
    }

    @Start
    void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("addOfferPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void addOfferTest(FxRobot robot){
        AddOfferController.setNameOfAgency("agency1");
        robot.clickOn("#nameOfOfferAdd");
        robot.write("offer1");
        robot.clickOn("#destinationAdd");
        robot.write("destination1");
        robot.clickOn("#hotelNameAdd");
        robot.write("hotel1");
        robot.clickOn("#mealsAdd");
        robot.write("1");
        robot.clickOn("#nightsAdd");
        robot.write("5");
        robot.clickOn("#noOfClientsAdd");
        robot.write("50");
        robot.clickOn("#priceAdd");
        robot.write("100");
        robot.clickOn("#saveButtonAdd");
        assertThat(OfferService.getAllOffers().size()).isEqualTo(1);

        robot.clickOn("#bookListButtonAdd");
        robot.clickOn("#closeBookingButton");
        robot.clickOn("#addButton");
        robot.clickOn("#nameOfOfferAdd");
        robot.write("offer2");
        robot.clickOn("#destinationAdd");
        robot.write("destination2");
        robot.clickOn("#hotelNameAdd");
        robot.write("hotel2");
        robot.clickOn("#mealsAdd");
        robot.write("2");
        robot.clickOn("#nightsAdd");
        robot.write("7");
        robot.clickOn("#noOfClientsAdd");
        robot.write("80");
        robot.clickOn("#priceAdd");
        robot.write("150");
        robot.clickOn("#saveButtonAdd");
        assertThat(OfferService.getAllOffers().size()).isEqualTo(2);

        robot.clickOn("#closeButtonAdd");
        robot.clickOn("#addButton");
        robot.clickOn("#nameOfOfferAdd");
        robot.write("offer3");
        robot.clickOn("#destinationAdd");
        robot.write("destination3");
        robot.clickOn("#hotelNameAdd");
        robot.write("hotel3");
        robot.clickOn("#mealsAdd");
        robot.write("3");
        robot.clickOn("#nightsAdd");
        robot.write("6");
        robot.clickOn("#noOfClientsAdd");
        robot.write("60");
        robot.clickOn("#priceAdd");
        robot.write("180");
        robot.clickOn("#saveButtonAdd");
        assertThat(OfferService.getAllOffers().size()).isEqualTo(3);
        assertThat(OfferService.getAllOffers().get(0).getNameOfAgency()).isEqualTo("agency1");
        assertThat(OfferService.getAllOffers().get(1).getNameOfAgency()).isEqualTo("agency1");
        assertThat(OfferService.getAllOffers().get(2).getNameOfAgency()).isEqualTo("agency1");
        robot.clickOn("#logoutButtonAdd");
    }
}