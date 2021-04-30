package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.openjfx.services.UserService;

import java.io.IOException;

public class HomePageController {

    @FXML
    private Button logoutButton;
    @FXML
    private Button agencyListButton;
    @FXML
    private Button bookListButton;

    @FXML
    public void handleAgenciesList() throws Exception{
        try {
            AgenciesListController.getAllAgencies();
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
        try {
            Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
            Stage stage = (Stage) (logoutButton.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    @FXML
    public  void handleHistory() throws Exception{
        try {
            Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("historyBooking.fxml"));
            Stage stage = (Stage) (agencyListButton.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

}