package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import org.dizitart.no2.SortOrder;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.openjfx.model.Offer;
import org.openjfx.services.OfferService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import static org.dizitart.no2.FindOptions.sort;
import static org.dizitart.no2.objects.filters.ObjectFilters.eq;


public class DeleteOfferController implements Initializable {
    private static ObjectRepository<Offer> REPOSITORY = OfferService.getOfferRepository();
    private static String nameOfAgency;
    private static ArrayList<String> listOfOffers = new ArrayList<String>();
    private Stage stage = new Stage();

    @FXML
    private TextField offerName;
    @FXML
    private Button closeButton;
    @FXML
    private Button addButton;
    @FXML
    private Button editButton;
    @FXML
    private Button bookListButton;
    @FXML
    private Button logoutButton;

    public static void setNameOfAgency(String nameOfAgency) {
        DeleteOfferController.nameOfAgency = nameOfAgency;
    }

    public static ArrayList<String> getListOfOffers() {
        return listOfOffers;
    }

    public static void getAllOffers(){
        REPOSITORY = OfferService.getOfferRepository();
        Cursor<Offer> offers = REPOSITORY.find(eq("nameOfAgency",nameOfAgency),sort("nameOfOffer", SortOrder.Ascending));
        listOfOffers.clear();
        for(Offer offer : offers){
            listOfOffers.add(offer.getNameOfOffer());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAllOffers();
        TextFields.bindAutoCompletion(offerName,listOfOffers);
        offerName.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)) {
                if(!offerName.getText().equals("") && listOfOffers.contains(offerName.getText())){
                    try{
                        DeleteOfferDetailsController.setNameOfOffer(offerName.getText());
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("deleteOfferDetailsPage.fxml"));
                        Parent root = loader.load();
                        Stage stage = (Stage) addButton.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException e) {
                        System.out.println("Error");
                    }
                }
            }
        });
    }

    @FXML
    public void handleAdd(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("addOfferPage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    @FXML
    public void handleEdit() throws Exception{
        Stage primaryStage = (Stage) editButton.getScene().getWindow();
        primaryStage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("editOfferPage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void handleClose(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("travelAgentPage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    @FXML
    public void handleBookingList(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("bookingsPage.fxml"));
            Parent root = loader.load();
            Stage bookList = (Stage) bookListButton.getScene().getWindow();
            bookList.close();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root));
            stage1.show();
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
