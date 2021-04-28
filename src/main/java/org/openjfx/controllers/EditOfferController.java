package org.openjfx.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.openjfx.model.Offer;

import java.io.IOException;


public class EditOfferController {
    private static ObservableList<Offer> offers;
    private Stage primaryStage = AgencyPageController.getStage();
    private Stage secondStage = DialogEditController.getSecondStage();
    private Stage thirdStage = AddOfferController.getStage();
    private static String id,username,nameOfAgency;

    @FXML
    public TableView<Offer> offerTable;
    @FXML
    public TableColumn<Offer, String> offerNameColumn;

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
    private Button logoutButton;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField searchTextField;


    @FXML
    public void initialize() {
        Platform.runLater(() -> {

            offerNameColumn.setCellValueFactory(new PropertyValueFactory<>("nameOfOffer"));
            offerTable.setItems(offers);

            showOfferDetails(null);

            offerTable.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> showOfferDetails(newValue));

            FilteredList<Offer> filteredData = new FilteredList<>(offers, p -> true);

            searchTextField.setOnKeyReleased(e -> {
                searchTextField.textProperty().addListener(
                        (observable, oldValue, newValue) -> {
                            filteredData.setPredicate(offer -> {
                                if (newValue == null || newValue.isEmpty()) {
                                    return true;
                                }

                                String lowerCaseFilter = newValue.toLowerCase();
                                if (offer.getNameOfOffer().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                                    return true;
                                }
                                return false;

                            });
                        });

                SortedList<Offer> sortedList = new SortedList<>(filteredData);
                sortedList.comparatorProperty().bind(offerTable.comparatorProperty());
                offerTable.setItems(sortedList);
            });

            offerTable.setRowFactory(offer -> {
                TableRow<Offer> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && !row.isEmpty()) {
                        try {
                            Offer selectedOffer = offerTable.getSelectionModel().getSelectedItem();
                            if (selectedOffer != null) {
                                id = selectedOffer.getId();
                                primaryStage.close();
                                secondStage.close();
                                thirdStage.close();
                                if (showDialogEditPage(selectedOffer)) {
                                    showOfferDetails(selectedOffer);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                return row;
            });

        });

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
    public void handleAdd(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("addOfferPage.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            Stage addStage = (Stage) addButton.getScene().getWindow();
            addStage.close();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    @FXML
    public void handleDelete() {
        try {
            primaryStage.close();
            Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("deleteOfferPage.fxml"));
            Stage stage = (Stage) (deleteButton.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public boolean showDialogEditPage(Offer offer) throws Exception {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("dialogEdit.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
            DialogEditController controller = loader.getController();
            controller.setId(id);
            controller.setUsername(username);
            System.out.println(username);
            controller.setNameOfAgency(nameOfAgency);
            controller.setOffer(offer);

            return controller.isOkClicked();
        } catch (Exception e) {
            System.out.println("eroare");
            return false;
        }
    }

    private void showOfferDetails(Offer offer) {
        if(offer != null) {
            destinationLabel.setText(offer.getDestination());
            hotelLabel.setText(offer.getHotelName());
            nightsLabel.setText(offer.getNights());
            mealsLabel.setText(offer.getMeals());
            clientsLabel.setText(offer.getNoOfClients());
            priceLabel.setText(offer.getPrice());
        } else {
            destinationLabel.setText("");
            hotelLabel.setText("");
            nightsLabel.setText("");
            mealsLabel.setText("");
            clientsLabel.setText("");
            priceLabel.setText("");
        }
    }

    public static void setUsername(String username) {
        EditOfferController.username = username;
    }

    public static void setNameOfAgency(String nameOfAgency) {
        EditOfferController.nameOfAgency = nameOfAgency;
    }

    public static void setOffers(ObservableList<Offer> offers) {
        EditOfferController.offers = offers;
    }
}