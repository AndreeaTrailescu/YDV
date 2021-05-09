package org.openjfx.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openjfx.services.FileSystemService;
import org.openjfx.services.OfferService;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class EditTestFX {

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.OFFERS_FOLDER = ".test-offers-database";
        FileSystemService.initOffersDirectory();
        FileUtils.cleanDirectory(FileSystemService.getOffersHomeFolder().toFile());
        OfferService.initDatabase();
        OfferService.addOffer("1","agency1","offer","destination","hotel","2","5","100","150");
        EditOfferController.setNameOfAgency("agency1");
        AddOfferController.setNameOfAgency("agency1");
    }

    @AfterEach
    void tearDown() {
        OfferService.getDatabase().close();
    }

    @Start
    void start(Stage stage) throws IOException {
        OfferService.initDatabase();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("editOfferPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        OfferService.getDatabase().close();
    }

    @Test
    void editOfferTest(FxRobot robot) {
        robot.clickOn("#addFromEditButton");
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
        assertThat(OfferService.getAllOffers().size()).isEqualTo(2);
        robot.clickOn("#closeAddButton");
        robot.clickOn("#editButtonAgent");
        robot.clickOn("#offerTableFromEdit");
        robot.moveTo("offer").doubleClickOn();

        robot.clickOn("#nameOfferDialog");
        robot.write("offer2");
        robot.clickOn("#destinationDialog");
        robot.write("destination2");
        robot.clickOn("#hotelDialog");
        robot.write("hotel2");
        robot.clickOn("#mealsDialog");
        robot.write("3");
        robot.clickOn("#nightsDialog");
        robot.write("5");
        robot.clickOn("#clientsDialog");
        robot.write("20");
        robot.clickOn("#priceDialog");
        robot.write("300");
        robot.clickOn("#saveDialogButton");
    }
}
