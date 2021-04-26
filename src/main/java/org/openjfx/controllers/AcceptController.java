package org.openjfx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.openjfx.model.Booking;
import org.openjfx.model.Offer;
import org.openjfx.services.BookingService;
import org.openjfx.services.OfferService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.dizitart.no2.objects.filters.ObjectFilters.and;
import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public class AcceptController {
    private String date, nameOfAgency, username;
    private Booking selectedBooking;
    private final ObjectRepository<Booking> BOOKING_REPOSITORY = BookingService.getBookingRepository();
    private final ObjectRepository<Offer> OFFER_REPOSITORY = OfferService.getOfferRepository();
    private ObservableList<Booking> bookings ;

    @FXML
    private DatePicker deadlineDate;
    @FXML
    private Button closeButton;
    @FXML
    private Button saveButton;


    @FXML
    public void initialize() {
        deadlineDate.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
    }

    @FXML
    public void handleSave() throws IOException {
        date = deadlineDate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        selectedBooking.setMessage("Accepted deadline: " + date);
        String nameOfOffer = selectedBooking.getNameOfOffer();
        updateNumberOfClients(nameOfOffer);
        BOOKING_REPOSITORY.update(selectedBooking);

        Cursor<Booking> cursor = BOOKING_REPOSITORY.find(eq("nameOfAgency",nameOfAgency));
        bookings = FXCollections.observableArrayList();
        for(Booking b : cursor) {
            if(!b.getMessage().contains("deadline"))
                bookings.add(b);
        }

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
    }

    @FXML
    public void handleClose() throws IOException {
        Cursor<Booking> cursor = BOOKING_REPOSITORY.find(eq("nameOfAgency",nameOfAgency));
        bookings = FXCollections.observableArrayList();
        for(Booking b : cursor) {
            if(!b.getMessage().contains("deadline"))
                bookings.add(b);
        }

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
    }

    public void updateNumberOfClients(String nameOfOffer) {
        Offer offer = OFFER_REPOSITORY.find(and(eq("nameOfOffer",nameOfOffer),eq("nameOfAgency",nameOfAgency))).firstOrDefault();
        offer.setNoOfClients(String.valueOf(Integer.parseInt(offer.getNoOfClients()) - Integer.parseInt(selectedBooking.getNumberOfPersons())));
        OFFER_REPOSITORY.update(offer);
    }

    public void setSelectedBooking(Booking selectedBooking) {
        this.selectedBooking = selectedBooking;
    }

    public void setNameOfAgency(String nameOfAgency) {
        this.nameOfAgency = nameOfAgency;
    }

    public void setBookings(ObservableList<Booking> bookings) {
        this.bookings = FXCollections.observableArrayList(bookings);
    }

    public void setUsername(String username) {
        this.username = username;
    }
}