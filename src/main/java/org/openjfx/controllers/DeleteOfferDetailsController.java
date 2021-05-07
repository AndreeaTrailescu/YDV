package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.dizitart.no2.objects.ObjectRepository;
import org.openjfx.model.Offer;
import org.openjfx.services.OfferService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.dizitart.no2.objects.filters.ObjectFilters.and;
import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public class DeleteOfferDetailsController implements Initializable {
    private static final ObjectRepository<Offer> OFFER_REPOSITORY = OfferService.getOfferRepository();
    private static String nameOfAgency = DeleteOfferController.getNameOfAgency();
    private static String nameOfOffer ;
    private Offer offerSelected;

    @FXML
    private Button closeButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Label nameLabel;
    @FXML
    private Label destinationLabel;
    @FXML
    private Label hotelLabel;
    @FXML
    private Label mealsLabel;
    @FXML
    private Label nightsLabel;
    @FXML
    private Label clientsLabel;
    @FXML
    private Label priceLabel;
    @FXML

    public static void setNameOfOffer(String nameOfOffer) {
        DeleteOfferDetailsController.nameOfOffer = nameOfOffer;
    }

    public static void setNameOfAgency(String nameOfAgency) {
        DeleteOfferDetailsController.nameOfAgency = nameOfAgency;
    }

    public static String getNameOfAgency() {
        return nameOfAgency;
    }

    public static String getNameOfOffer() {
        return nameOfOffer;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showOfferDetails();
    }

    @FXML
    public void handleDelete(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the offer " +nameOfOffer+"?", ButtonType.YES, ButtonType.NO);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.NO){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("deleteOfferPage.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) closeButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
        if (alert.getResult() == ButtonType.YES){
            OFFER_REPOSITORY.remove(and(eq("nameOfAgency", nameOfAgency),eq("nameOfOffer",nameOfOffer)));
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("deleteOfferPage.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) closeButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }

    @FXML
    public void handleClose(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("deleteOfferPage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    @FXML
    public void handleLogout() {
        try {
            Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
            Stage stage = (Stage) (logoutButton.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public void showOfferDetails(){
        offerSelected = OFFER_REPOSITORY.find(and(eq("nameOfAgency", nameOfAgency),eq("nameOfOffer",nameOfOffer))).firstOrDefault();
        nameLabel.setText(offerSelected.getNameOfOffer());
        destinationLabel.setText(offerSelected.getDestination());
        hotelLabel.setText(offerSelected.getHotelName());
        mealsLabel.setText(offerSelected.getMeals());
        nightsLabel.setText(offerSelected.getNights());
        clientsLabel.setText(offerSelected.getNoOfClients());
        priceLabel.setText(offerSelected.getPrice());
    }
}
