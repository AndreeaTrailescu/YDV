package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.dizitart.no2.SortOrder;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.openjfx.model.Offer;
import org.openjfx.services.OfferService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.dizitart.no2.FindOptions.sort;
import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public class OffersPageController implements Initializable {
    private static String selectedAgency;

    @FXML
    private Button agencyListButton;
    @FXML
    private Button bookListButton;
    @FXML
    private Button logoutButton;

    public static void setSelectedAgency(String selectedAgency) {
        OffersPageController.selectedAgency = selectedAgency;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObjectRepository<Offer> OFFER_REPOSITORY = OfferService.getOfferRepository();
        Cursor<Offer> offers = OFFER_REPOSITORY.find(eq("nameOfAgency",selectedAgency),sort("nameOfOffer", SortOrder.Ascending));
        System.out.println(selectedAgency);
        offers.toList();
        for(Offer offer : offers){
            System.out.println(offer.getNameOfOffer());
        }
    }

    @FXML
    public void handleAgencyList() {
        try{
            Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("travelAgenciesList.fxml"));
            Stage stage = (Stage) (agencyListButton.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    @FXML
    public void handleLogout() {
        try{
            Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
            Stage stage = (Stage) (logoutButton.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }
}