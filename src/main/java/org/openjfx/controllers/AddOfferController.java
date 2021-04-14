package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddOfferController {
    private String nameOfAgency;

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
            Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("addOfferPage.fxml"));
            Stage stage = (Stage) (saveButton.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }
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
}
