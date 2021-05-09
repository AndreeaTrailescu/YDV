package org.openjfx.controllers;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.dizitart.no2.objects.ObjectRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openjfx.model.Offer;
import org.openjfx.services.BookingService;
import org.openjfx.services.FileSystemService;
import org.openjfx.services.OfferService;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

import java.io.IOException;
import java.lang.management.PlatformLoggingMXBean;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class DeleteOfferControllerTest {

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
        OfferService.addOffer("100","agency1","offer1","destination1","hotel1","1","5","50","100");
        OfferService.addOffer("200","agency1","offer2","destination2","hotel2","2","7","80","150");
        OfferService.addOffer("300","agency1","offer3","destination3","hotel3","3","6","60","180");
        DeleteOfferController.setNameOfAgency("agency1");
        FileSystemService.BOOKINGS_FOLDER = ".test-bookings-database";
        FileSystemService.initBookingDirectory();
        FileUtils.cleanDirectory(FileSystemService.getBookingsHomeFolder().toFile());
        BookingService.initDatabase();
    }

    @AfterEach
    void tearDown() {
        OfferService.getDatabase().close();
        BookingService.getDatabase().close();
    }

    @Start
    void start(Stage stage) throws IOException {
        FileSystemService.OFFERS_FOLDER = ".test-offers-database";
        OfferService.initDatabase();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("deleteOfferPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        OfferService.getDatabase().close();
    }
    @Test
    void deleteOfferTest(FxRobot robot){
        robot.clickOn("#bookListButtonDelete");
        robot.clickOn("#closeBookingButton");
        robot.clickOn("#deleteButton");

        robot.clickOn("#offerNameDelete");
        assertThat(OfferService.getAllOffers().size()).isEqualTo(3);
        robot.write("offer");
        robot.type(KeyCode.DOWN);
        robot.release(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);
        robot.release(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        robot.type(KeyCode.ENTER);
        robot.clickOn("#deleteButtonDeleteDetails");
        robot.clickOn("YES");
        assertThat(OfferService.getAllOffers().size()).isEqualTo(2);

        robot.clickOn("#addButtonDelete");
        robot.clickOn("#deleteButtonAdd");
        robot.clickOn("#offerNameDelete");
        robot.write("offer");
        robot.type(KeyCode.DOWN);
        robot.release(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);
        robot.release(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        robot.type(KeyCode.ENTER);
        robot.clickOn("#closeButtonDeleteDetails");
        assertThat(OfferService.getAllOffers().size()).isEqualTo(2);
        robot.clickOn("#offerNameDelete");
        robot.write("offer");
        robot.type(KeyCode.DOWN);
        robot.release(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);
        robot.release(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        robot.type(KeyCode.ENTER);
        robot.clickOn("#deleteButtonDeleteDetails");
        robot.clickOn("NO");
        assertThat(OfferService.getAllOffers().size()).isEqualTo(2);
        robot.clickOn("#offerNameDelete");
        robot.write("offer");
        robot.type(KeyCode.DOWN);
        robot.clickOn("#logoutButtonDelete");
    }
}