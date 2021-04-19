package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class OffersPageController {
    private static String selectedAgency;

    @FXML
    private Label nameOfAgencyLabel;
    @FXML
    private Button agencyListButton;
    @FXML
    private Button bookListButton;
    @FXML
    private Button logoutButton;

    public static void setSelectedAgency(String selectedAgency) {
        OffersPageController.selectedAgency = selectedAgency;
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
