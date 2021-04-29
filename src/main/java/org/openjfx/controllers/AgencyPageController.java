package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AgencyPageController {
    private static String nameOfAgency;
    private static Stage stage = new Stage();

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
    public void handleAdd(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("addOfferPage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) (addButton.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    @FXML
    public void handleDelete(){
        try {
            Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("deleteOfferPage.fxml"));
            Stage stage = (Stage) (deleteButton.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    @FXML
    public void handleLogout() throws Exception{
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
    public void handleEdit() throws Exception{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("editOfferPage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
            Stage primaryStage = (Stage) editButton.getScene().getWindow();
            primaryStage.close();
        } catch (Exception e) {
            System.out.println("eroare");
        }
    }

    @FXML
    public void handleBookingList() throws Exception{
         try {
             FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("bookingsPage.fxml"));
             Parent root = loader.load();
             Stage bookList = (Stage) bookListButton.getScene().getWindow();
             bookList.close();
             Stage stage1 = new Stage();
             stage1.setScene(new Scene(root));
             stage1.show();
        } catch(Exception e) {
            System.out.println("eroare");
        }
    }

    public static void setNameOfAgency(String nameOfAgency) {
        AgencyPageController.nameOfAgency = nameOfAgency;
    }

    public static Stage getStage() {
        return stage;
    }

    public static String getNameOfAgency() {
        return nameOfAgency;
    }
}