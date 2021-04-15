package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.dizitart.no2.objects.ObjectRepository;
import org.openjfx.model.User;
import org.openjfx.services.OfferService;
import org.openjfx.services.UserService;

import java.io.IOException;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public class AddOfferController {
    private static String username;
    private final ObjectRepository<User> REPOSITORY =UserService.getUserRepository();

    @FXML
    private Button saveButton;
    @FXML
    private Button closeButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button bookListButton;
    @FXML
    private Button logoutButton;
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
    public void handleSaveOffer() throws Exception{
        try {
            User loggedInUser=REPOSITORY.find(eq("username",username)).firstOrDefault();
            String nameOfAgency=loggedInUser.getNameOfAgency();
            OfferService.addOffer(nameOfAgency,nameOfOffer.getText(),destination.getText(),hotelName.getText(),meals.getText(),nights.getText(),noOfClients.getText(),price.getText());
            Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("addOfferPage.fxml"));
            Stage stage = (Stage) (saveButton.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    @FXML
    public void handleClose() throws Exception{
        try {
            Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("travelAgentPage.fxml"));
            Stage stage = (Stage) (closeButton.getScene().getWindow());
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
    public void setUsername(String username) {
        this.username = username;
    }
}
