package org.openjfx.controllers;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WritableDoubleValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.Rating;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.openjfx.model.Booking;
import org.openjfx.services.BookingService;
import org.w3c.dom.Text;

import javax.swing.event.ChangeListener;
import java.io.IOException;
import java.util.List;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public class HistoryBookingController {
    private static String username;
    private final ObjectRepository<Booking> BOOKING_REPOSITORY = BookingService.getBookingRepository();
    private static ObservableList<Booking> bookings;
    private Booking selectedBooking;

    @FXML
    public TableColumn<Booking, String> offerNameColumn;
    @FXML
    public TableColumn<Booking, String> agencyColumn;
    @FXML
    public TableColumn<Booking, String> message;
    @FXML
    public TableColumn<Booking, TextField> ratingTableColumn;
    @FXML
    public TableView<Booking> bookingTableView;
    @FXML
    private Button travelAgenciesButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button closeButton;


    @FXML
    public void initialize() {
        getAllBookings();

        Platform.runLater(() -> {
            bookingTableView.setEditable(true);
            offerNameColumn.setCellValueFactory(new PropertyValueFactory<>("nameOfOffer"));
            agencyColumn.setCellValueFactory(new PropertyValueFactory<>("nameOfAgency"));
            message.setCellValueFactory(new PropertyValueFactory<>("message"));
            ratingTableColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
            bookingTableView.setItems(bookings);

            bookingTableView.setRowFactory(e -> {
                TableRow<Booking> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && !row.isEmpty()) {
                        selectedBooking = bookingTableView.getSelectionModel().getSelectedItem();
                        try {

                        } catch (Exception ee) {
                            System.out.println("eroare");
                        }
                    }
                });
                return row;
            });
        });
    }

    @FXML
    public void handleClose() {

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

    public void getAllBookings(){
        Cursor<Booking> cursor = BOOKING_REPOSITORY.find(eq("clientUsername",username));
        bookings = FXCollections.observableArrayList();
        for(Booking b : cursor) {
            if(b.getMessage().contains("Accepted") || b.getMessage().contains("Rejected"))
                bookings.add(b);
        }
    }

    public static void setUsername(String username) {
        HistoryBookingController.username = username;
    }
}
