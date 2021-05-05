package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.dizitart.no2.objects.ObjectRepository;
import org.openjfx.model.User;
import org.openjfx.services.UserService;

import java.awt.event.ActionEvent;
import java.io.IOException;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public class RegisterControllerAgent {
    private final ObjectRepository<User> REPOSITORY = UserService.getUserRepository();
    private String usernameField;
    private String passwordField;
    private String role;

    @FXML
    private TextField name;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField eMail;
    @FXML
    private TextField nameOfAgency;
    @FXML
    private Button saveButton;

    @FXML
    public void handleRegisterAgent() throws Exception{
        try {
            UserService.addUser2(usernameField, passwordField, role, name.getText(), eMail.getText(), phoneNumber.getText(), nameOfAgency.getText());
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("travelAgentPage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) (saveButton.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();
            User loggedInUser=REPOSITORY.find(eq("username",usernameField)).firstOrDefault();
            AgencyPageController.setNameOfAgency(loggedInUser.getNameOfAgency());
            AddOfferController.setUsername(usernameField);
            AddOfferController.setNameOfAgency(loggedInUser.getNameOfAgency());
            EditOfferController.setUsername(usernameField);
            EditOfferController.setNameOfAgency(loggedInUser.getNameOfAgency());
            DeleteOfferController.setNameOfAgency(loggedInUser.getNameOfAgency());
            RezervationsController.setUsername(usernameField);
            RezervationsController.setNameOfAgency(loggedInUser.getNameOfAgency());
        } catch (IOException e) {
            System.out.println("eroare");
        }
    }

    public void setUsernameField(TextField username) {
        this.usernameField = username.getText();
    }

    public void setPasswordField(PasswordField password) {
        this.passwordField = password.getText();
    }

    public void setRole(ChoiceBox<String> role) {
        this.role = role.getValue();
    }
}