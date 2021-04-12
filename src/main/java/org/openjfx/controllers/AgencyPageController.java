package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AgencyPageController {

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
    public void handleLogout() {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
    }
}