package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    private static String nameOfAgency = AgencyPageController.getNameOfAgency();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showOfferDetails(nameOfOffer);
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

    public void showOfferDetails(String offerName){
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
