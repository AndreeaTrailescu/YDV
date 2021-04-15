package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.dizitart.no2.SortOrder;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.openjfx.model.User;
import org.openjfx.services.UserService;

import java.io.IOException;
import java.util.ArrayList;

import static org.dizitart.no2.FindOptions.sort;
import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public class AgenciesListController {
    private static final ObjectRepository<User> REPOSITORY = UserService.getUserRepository();
    private static Cursor<User> agents = REPOSITORY.find(eq("role","Travel Agent"),sort("nameOfAgency", SortOrder.Ascending));
    private static ArrayList<String> agenciesList = new ArrayList<String>();

    @FXML
    private Button bookListButton;
    @FXML
    private Button logoutButton;


    public static void getAllAgencies(){
        for(User agent : agents){
            String agencyName= agent.getNameOfAgency();
            if(!agenciesList.contains(agencyName)){
                agenciesList.add(agencyName);
            }
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
