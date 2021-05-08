package org.openjfx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.dizitart.no2.objects.ObjectRepository;
import org.openjfx.model.Booking;
import org.openjfx.model.Offer;
import org.openjfx.services.OfferService;

import java.io.IOException;

import static org.dizitart.no2.objects.filters.ObjectFilters.and;
import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public class BookingDetailsController {
    private Booking selectedBooking;
    private String nameOfAgency,username;
    private ObservableList<Booking> bookings;
    private final ObjectRepository<Offer> OFFER_REPOSITORY = OfferService.getOfferRepository();

    @FXML
    private Label clientUsername;
    @FXML
    private Label nameOfOffer;
    @FXML
    private Label totalPrice;
    @FXML
    private Label numberOfPersons;
    @FXML
    private Label checkinDate;
    @FXML
    private Label checkoutDate;
    @FXML
    private Label totalNumber;
    @FXML
    private Button rejectButton;
    @FXML
    private Button acceptButton;
    @FXML
    private Button closeButton;

    @FXML
    public void initialize() {
        selectedBooking = RezervationsController.getSelectedBooking();
        showOfferDetails(selectedBooking);
    }

    @FXML
    public void handleClose() throws IOException {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("bookingsPage.fxml"));
        Parent root = loader.load();
        RezervationsController controller = loader.getController();
        controller.setNameOfAgency(nameOfAgency);
        controller.setUsername(username);
        controller.setBookings(bookings);
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void handleAccept() throws IOException {
        Stage stage = (Stage) acceptButton.getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("acceptBooking.fxml"));
        Parent root = loader.load();
        AcceptController controller = loader.getController();
        controller.setSelectedBooking(selectedBooking);
        controller.setNameOfAgency(nameOfAgency);
        controller.setUsername(username);
        controller.setBookings(bookings);
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void handleReject() {
        try {
            Stage stage = (Stage) rejectButton.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("rejectBookingPage.fxml"));
            Parent root = loader.load();
            RejectBookingController.setNameOfAgency(nameOfAgency);
            RejectBookingController.setUsername(username);
            RejectBookingController.setSelectedBooking(selectedBooking);
            RejectBookingController.setBookings(bookings);
            stage.setScene(new Scene(root));
            stage.show();
        }catch (IOException e){
            System.out.println("Error");
        }
    }

    public void showOfferDetails(Booking booking) {
        Offer offer = OFFER_REPOSITORY.find(and(eq("nameOfOffer",booking.getNameOfOffer()),eq("nameOfAgency",booking.getNameOfAgency()))).firstOrDefault();
        clientUsername.setText(booking.getClientUsername());
        nameOfOffer.setText(booking.getNameOfOffer());
        totalPrice.setText(booking.getTotalPrice());
        numberOfPersons.setText(booking.getNumberOfPersons());
        checkinDate.setText(booking.getCheckInDate());
        checkoutDate.setText(booking.getCheckOutDate());
        totalNumber.setText(offer.getNoOfClients());
    }

    public void setNameOfAgency(String nameOfAgency) {
        this.nameOfAgency = nameOfAgency;
    }

    public void setSelectedBooking(Booking selectedBooking) {
        this.selectedBooking = selectedBooking;
    }

    public void setBookings(ObservableList<Booking> bookings) {
        this.bookings = FXCollections.observableArrayList(bookings);
    }

    public void setUsername(String username) {
        this.username = username;
    }
}