package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.openjfx.services.UserService;

import java.io.IOException;

public class AgencyPageController {
    private String username;

    @FXML
    private Button logoutButton;
    @FXML
    private Button addButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button bookListButton;

    @FXML
    public void handleAdd() throws Exception{
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("addOfferPage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) (addButton.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();
            AddOfferController addController = loader.getController();
            addController.setUsername(username);
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    @FXML
    public void handleLogout() {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
    }

    public void setUsername(String username) {
        this.username = username;
    }
}