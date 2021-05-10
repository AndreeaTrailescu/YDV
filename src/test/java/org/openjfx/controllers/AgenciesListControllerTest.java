package org.openjfx.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.assertj.core.api.Assertions;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class AgenciesListControllerTest {
    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-registration-database";
        UserService.initDatabase();
        FileSystemService.OFFERS_FOLDER = ".test-offers-database";
        FileSystemService.initOffersDirectory();
        FileUtils.cleanDirectory(FileSystemService.getOffersHomeFolder().toFile());
        OfferService.initDatabase();
        OfferService.addOffer("100","agency1","offer1","destination1","hotel1","1","5","50","100");
        OfferService.addOffer("200","agency1","offer2","destination2","hotel2","2","7","80","150");
        OfferService.addOffer("300","agency1","offer3","destination3","hotel3","3","6","60","180");

        FileSystemService.BOOKINGS_FOLDER = ".test-bookings-database";
        FileSystemService.initBookingDirectory();
        FileUtils.cleanDirectory(FileSystemService.getBookingsHomeFolder().toFile());
        BookingService.initDatabase();
    }

    @AfterEach
    void tearDown() {
        UserService.getDatabase().close();
        OfferService.getDatabase().close();
        BookingService.getDatabase().close();
    }

    @AfterAll
    static void afterAll() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    @Start
    void start(Stage stage) throws IOException {
        FileSystemService.APPLICATION_FOLDER = ".test-registration-database";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
        UserService.addUser1("client","password2","Client","user2","user2","1111111111");
        UserService.addUser2("agent1","user1","Travel Agent","user1","user1","0000000000","agency1");
        UserService.addUser2("agent2","user2","Travel Agent","user2","user2","1111111111","agency2");
        UserService.addUser2("agent3","user3","Travel Agent","user3","user3","2222222222","agency1");
        AgenciesListController.getAllAgencies();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("travelAgenciesList.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        UserService.getDatabase().close();
    }


    @Test
    void viewListWithAgenciesTest(FxRobot robot){
        robot.clickOn("#bookListButtonAgenciesList");
        robot.clickOn("#travelAgenciesButtonHistoryBooking");
        assertThat(AgenciesListController.getListOfAgencies().size()).isEqualTo(2);
        robot.clickOn("#agencyNameAgenciesList");
        robot.write("agency");
        robot.type(KeyCode.DOWN);
        robot.release(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);
        robot.release(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        robot.clickOn("#offersButtonAgenciesList");
        assertThat(OffersPageController.getSelectedAgency()).isEqualTo("agency1");
        robot.moveTo("offer1").doubleClickOn();
        robot.clickOn("#closeButtonOfferDetails");
        robot.clickOn("#agencyListButtonOffersPage");

        robot.clickOn("#agenciesListAgenciesList");
        robot.type(KeyCode.DOWN);
        robot.clickOn("#offersButtonAgenciesList");
        assertThat(OffersPageController.getSelectedAgency()).isEqualTo("agency2");
        assertThat(robot.lookup("#noOfferExistsOffersPage").queryText()).hasText("No offer has been added yet.");
        robot.clickOn("#agencyListButtonOffersPage");
        robot.clickOn("#agencyNameAgenciesList");
        robot.write("agency");
        robot.type(KeyCode.DOWN);
        robot.release(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);
        robot.release(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        robot.clickOn("#offersButtonAgenciesList");
        robot.clickOn("#bookListButtonOffersPage");
        robot.clickOn("#travelAgenciesButtonHistoryBooking");
        robot.clickOn("#logoutButtonAgenciesList");

        robot.clickOn("#username");
        robot.write("client");
        robot.clickOn("#password");
        robot.write("password2");
        robot.clickOn("#role");
        robot.type(KeyCode.ENTER);
        robot.clickOn("#loginButton");
        robot.clickOn("#agencyListButtonHome");
        robot.clickOn("#agencyNameAgenciesList");
        robot.write("agency");
        robot.type(KeyCode.DOWN);
        robot.release(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);
        robot.release(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        robot.clickOn("#offersButtonAgenciesList");
        robot.clickOn("#logoutButtonOffersPage");
    }
}