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
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.openjfx.model.Booking;
import org.openjfx.services.BookingService;

import java.io.IOException;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public class RejectBookingController {
    private static String nameOfAgency, username;
    private static Booking selectedBooking;
    private static ObjectRepository<Booking> BOOKING_REPOSITORY = BookingService.getBookingRepository();
    private static ObservableList<Booking> bookings ;

    @FXML
    private Button closeButton;
    @FXML
    private Button saveButton;
    @FXML
    private TextField rejectionReason;

    public static void setNameOfAgency(String nameOfAgency) {
        RejectBookingController.nameOfAgency = nameOfAgency;
    }

    public static void setUsername(String username) {
        RejectBookingController.username = username;
    }

    public static void setSelectedBooking(Booking selectedBooking) {
        RejectBookingController.selectedBooking = selectedBooking;
    }

    public static void setBookings(ObservableList<Booking> bookings) {
        RejectBookingController.bookings = bookings;
    }

    public static ObservableList<Booking> getBookings() {
        return bookings;
    }

    public static void getAllBookings(){
        BOOKING_REPOSITORY = BookingService.getBookingRepository();
        Cursor<Booking> cursor = BOOKING_REPOSITORY.find(eq("nameOfAgency",nameOfAgency));
        bookings = FXCollections.observableArrayList();
        for(Booking b : cursor) {
            if(b.getMessage().contains("hasn't"))
                bookings.add(b);
        }
    }

    @FXML
    public void handleClose(){
        getAllBookings();
        try {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("bookingDetailsPage.fxml"));
            Parent root = loader.load();
            BookingDetailsController controller = loader.getController();
            controller.setSelectedBooking(selectedBooking);
            controller.setNameOfAgency(nameOfAgency);
            controller.setUsername(username);
            controller.setBookings(bookings);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    @FXML
    public void handleSave(){
        selectedBooking.setMessage("Rejected. " + rejectionReason.getText());
        BOOKING_REPOSITORY.update(selectedBooking);
        getAllBookings();
        try {
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("bookingsPage.fxml"));
            Parent root = loader.load();
            RezervationsController controller = loader.getController();
            controller.setNameOfAgency(nameOfAgency);
            controller.setUsername(username);
            controller.setBookings(bookings);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }
}
