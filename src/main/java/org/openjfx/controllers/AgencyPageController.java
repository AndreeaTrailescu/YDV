package org.openjfx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.dizitart.no2.FindOptions;
import org.dizitart.no2.SortOrder;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.openjfx.model.Booking;
import org.openjfx.model.Offer;
import org.openjfx.services.BookingService;
import org.openjfx.services.OfferService;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public class AgencyPageController {
    private static ObservableList<Offer> offers;
    private String username, nameOfAgency;
    private final ObjectRepository<Offer> REPOSITORY = OfferService.getOfferRepository();
    private final ObjectRepository<Booking> BOOKING_REPOSITORY = BookingService.getBookingRepository();
    private static ObservableList<Booking> bookings;
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
    public void handleAdd() throws Exception {
        ObservableList<Offer> newList = FXCollections.observableArrayList();
        Cursor<Offer> cursor = REPOSITORY.find(FindOptions.sort("nameOfOffer",SortOrder.Ascending));
        for(Offer offer:cursor) {
            if(Objects.equals(nameOfAgency,offer.getNameOfAgency())) {
                newList.add(offer);
            }
        }
        offers = newList;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("addOfferPage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) (addButton.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();
            AddOfferController addController = loader.getController();
            addController.setUsername(username);
            addController.setNameOfAgency(nameOfAgency);
            addController.setOffers(offers);
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
        ObservableList<Offer> newList = FXCollections.observableArrayList();
        Cursor<Offer> cursor = REPOSITORY.find(FindOptions.sort("nameOfOffer",SortOrder.Ascending));
        for(Offer offer:cursor) {
            if(Objects.equals(nameOfAgency,offer.getNameOfAgency())) {
                newList.add(offer);
            }
        }
        offers = newList;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("editOfferPage.fxml"));
            Parent root = loader.load();
            EditOfferController controller = loader.getController();
            controller.setUsername(username);
            controller.setNameOfAgency(nameOfAgency);
            controller.setOffers(offers);
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
        Cursor<Booking> cursor = BOOKING_REPOSITORY.find(eq("nameOfAgency",nameOfAgency));
        bookings = FXCollections.observableArrayList();
        for(Booking b : cursor) {
            if(!b.getMessage().contains("deadline"))
                bookings.add(b);
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("bookingsPage.fxml"));
            Parent root = loader.load();
            RezervationsController controller = loader.getController();
            controller.setNameOfAgency(nameOfAgency);
            controller.setBookings(bookings);
            Stage bookList = (Stage) bookListButton.getScene().getWindow();
            bookList.close();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root));
            stage1.show();
        } catch(Exception e) {
            System.out.println("eroare");
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNameOfAgency(String name) {
        this.nameOfAgency = name;
    }

    public static ObservableList<Offer> getOffers() {
        return offers;
    }

    public static Stage getStage() {
        return stage;
    }

    public static ObservableList<Booking> getBookings() {
        return bookings;
    }
}