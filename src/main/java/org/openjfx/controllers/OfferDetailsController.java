package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.dizitart.no2.SortOrder;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.openjfx.model.Offer;
import org.openjfx.services.OfferService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static org.dizitart.no2.FindOptions.sort;
import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public class OfferDetailsController implements Initializable {
    private static final ObjectRepository<Offer> OFFER_REPOSITORY = OfferService.getOfferRepository();
    private static String selectedAgency;
    private static String selectedOffer;

    @FXML
    private Button agencyListButton;
    @FXML
    private Button bookListButton;
    @FXML
    private Button makeBookingButton;
    @FXML
    private Button closeButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Label nameLabel;
    @FXML
    private Label destinationLabel;
    @FXML
    private Label hotelLabel;
    @FXML
    private Label mealsLabel;
    @FXML
    private Label nightsLabel;
    @FXML
    private Label clientsLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label totalPriceLabel;
    @FXML
    private TextField numberOfPersons;
    @FXML
    private DatePicker checkInDate;
    @FXML
    private DatePicker checkOutDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedAgency = OffersPageController.getSelectedAgency();
        selectedOffer = OffersPageController.getSelectedOffer();
        showOfferDetails(selectedOffer);
        numberOfPersons.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)) {
                if(!numberOfPersons.getText().equals("")){
                    double price = Integer.parseInt(numberOfPersons.getText())*Double.parseDouble(priceLabel.getText());
                    int intPrice=(int) price;
                    if(price==intPrice){
                        totalPriceLabel.setText(String.valueOf(intPrice));
                    }
                    else{
                        totalPriceLabel.setText(String.valueOf(price));
                    }
                }
            }
        });
        checkInDate.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
        checkInDate.valueProperty().addListener((ov, oldValue, newValue) -> {
            checkOutDate.setValue(newValue.plusDays(Integer.parseInt(nightsLabel.getText())));
        });
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
    public void handleClose(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("offersPage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) closeButton.getScene().getWindow();
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
    public void showOfferDetails(String offerName){
        Offer offerSelected = new Offer();
        Cursor<Offer> offers = OFFER_REPOSITORY.find(eq("nameOfAgency",selectedAgency),sort("nameOfOffer", SortOrder.Ascending));
        for(Offer offer : offers){
            if(offer.getNameOfOffer().equals(offerName)){
                offerSelected=offer;
                break;
            }
        }
        nameLabel.setText(offerSelected.getNameOfOffer());
        destinationLabel.setText(offerSelected.getDestination());
        hotelLabel.setText(offerSelected.getHotelName());
        mealsLabel.setText(offerSelected.getMeals());
        nightsLabel.setText(offerSelected.getNights());
        clientsLabel.setText(offerSelected.getNoOfClients());
        priceLabel.setText(offerSelected.getPrice());
    }
}

