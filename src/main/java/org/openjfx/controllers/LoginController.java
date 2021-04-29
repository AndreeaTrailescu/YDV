package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.dizitart.no2.objects.ObjectRepository;
import org.openjfx.exceptions.PasswordIncorrectException;
import org.openjfx.model.User;
import org.openjfx.services.UserService;

import java.io.IOException;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public class LoginController {
    private final ObjectRepository<User> REPOSITORY = UserService.getUserRepository();

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ChoiceBox<String> role;
    @FXML
    private Text message;
    @FXML
    private Button loginButton;

    @FXML
    public void initialize() {
        role.getItems().addAll("Client", "Travel Agent");
    }

    @FXML
    public void handleRegisterAction() {
        try {
            Stage stage = new Stage();

            boolean test = UserService.checkUserDoesAlreadyExist(usernameField.getText(), passwordField.getText());
            Stage primaryStage = (Stage) loginButton.getScene().getWindow();
            primaryStage.close();
            if(test) {
                if(((String) role.getValue()).equals("Client")) {
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("homePage.fxml"));
                    Parent root = (Parent) loader.load();
                    stage.setScene(new Scene(root));
                    stage.show();
                    OffersPageController.setClientUsername(usernameField.getText());
                } else if (((String) role.getValue()).equals("Travel Agent")) {
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("travelAgentPage.fxml"));
                    Parent root = (Parent) loader.load();
                    stage.setScene(new Scene(root));
                    stage.show();
                    User loggedInUser=REPOSITORY.find(eq("username",usernameField.getText())).firstOrDefault();
                    AgencyPageController.setNameOfAgency(loggedInUser.getNameOfAgency());
                    AddOfferController.setUsername(usernameField.getText());
                    AddOfferController.setNameOfAgency(loggedInUser.getNameOfAgency());
                    EditOfferController.setUsername(usernameField.getText());
                    EditOfferController.setNameOfAgency(loggedInUser.getNameOfAgency());
                    RezervationsController.setUsername(usernameField.getText());
                    RezervationsController.setNameOfAgency(loggedInUser.getNameOfAgency());
                }
            }

            if(!test) {
                if (((String) role.getValue()).equals("Client")) {
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("registerClient.fxml"));
                    Parent root = (Parent) loader.load();
                    stage.setScene(new Scene(root));
                    stage.show();
                    RegisterControllerClient reg = loader.getController();
                    reg.setUsernameField(usernameField);
                    reg.setPasswordField(passwordField);
                    reg.setRole(role);
                    OffersPageController.setClientUsername(usernameField.getText());
                } else if (((String) role.getValue()).equals("Travel Agent")) {
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("registerAgent.fxml"));
                    Parent root = (Parent) loader.load();
                    stage.setScene(new Scene(root));
                    stage.show();
                    RegisterControllerAgent reg = loader.getController();
                    reg.setUsernameField(usernameField);
                    reg.setPasswordField(passwordField);
                    reg.setRole(role);
                }
            }
        } catch (PasswordIncorrectException e) {
            message.setText(e.getMessage());
        } catch (IOException ee) {
            System.out.println("eroare");
        }
    }


}