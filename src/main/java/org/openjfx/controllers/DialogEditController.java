package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.dizitart.no2.objects.ObjectRepository;
import org.openjfx.model.Offer;
import org.openjfx.services.OfferService;

import java.io.IOException;

public class DialogEditController {
    private Offer offer;
    private boolean okClicked = false;
    private final ObjectRepository<Offer> REPOSITORY = OfferService.getOfferRepository();
    private String id,username, nameOfAgency;
    private static Stage secondStage = new Stage();

    @FXML
    private TextField nameOfOffer;
    @FXML
    private TextField destination;
    @FXML
    private TextField hotelName;
    @FXML
    private TextField meals;
    @FXML
    private TextField nights;
    @FXML
    private TextField noOfClients;
    @FXML
    private TextField price;
    @FXML
    private Button saveButton;
    @FXML
    private Button closeButton;

    @FXML
    public void handleSave() throws IOException {
        offer.setNameOfOffer(nameOfOffer.getText());
        offer.setDestination(destination.getText());
        offer.setHotelName(hotelName.getText());
        offer.setMeals(meals.getText());
        offer.setNights(nights.getText());
        offer.setNoOfClients(noOfClients.getText());
        offer.setPrice(price.getText());

        okClicked = true;
        offer.setId(id);
        REPOSITORY.update(offer);
        System.out.println("dialog " + username + " " + nameOfAgency);
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("editOfferPage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        secondStage.setScene(scene);
        secondStage.show();
        EditOfferController controller = loader.getController();
        controller.setUsername(username);
        controller.setNameOfAgency(nameOfAgency);
    }

    @FXML
    public void handleClose() throws IOException {
        System.out.println(username + "nume agentie " + nameOfAgency);
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("editOfferPage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        secondStage.setScene(scene);
        secondStage.show();
        EditOfferController controller = loader.getController();
        controller.setUsername(username);
        controller.setNameOfAgency(nameOfAgency);
    }

    public void setOffer(Offer offer) {
        this.offer = offer;

        this.offer.setId(id);
        nameOfOffer.setText(nameOfOffer.getText());
        nights.setText(nights.getText());
        hotelName.setText(hotelName.getText());
        destination.setText(destination.getText());
        meals.setText(meals.getText());
        noOfClients.setText(noOfClients.getText());
        price.setText(price.getText());
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static Stage getSecondStage() {
        return secondStage;
    }

    public void setNameOfAgency(String nameOfAgency) {
        this.nameOfAgency = nameOfAgency;
    }
}