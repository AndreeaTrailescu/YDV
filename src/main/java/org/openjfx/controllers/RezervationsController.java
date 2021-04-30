package org.openjfx.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.openjfx.model.Booking;
import org.openjfx.services.BookingService;

import java.io.IOException;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;


public class RezervationsController {
    private final ObjectRepository<Booking> BOOKING_REPOSITORY = BookingService.getBookingRepository();
    private static Stage stage = new Stage();
    private static String nameOfAgency,username;
    private static ObservableList<Booking> bookings;
    private static Booking selectedBooking;

    @FXML
    private Button logoutButton;
    @FXML
    private Button closeButton;
    @FXML
    public TableColumn<Booking, String> offerNameColumn;
    @FXML
    public TableColumn<Booking, String> usernameColumn;
    @FXML
    public TableView<Booking> bookingTableView;

    public void getAllBookings(){
        Cursor<Booking> cursor = BOOKING_REPOSITORY.find(eq("nameOfAgency",nameOfAgency));
        bookings = FXCollections.observableArrayList();
        for(Booking b : cursor) {
            if(!b.getMessage().contains("deadline") && b.getMessage().contains("hasn't"))
                bookings.add(b);
        }
    }
    @FXML
    private void initialize(){
        getAllBookings();
        Platform.runLater(()-> {
            offerNameColumn.setCellValueFactory(new PropertyValueFactory<>("nameOfOffer"));
            usernameColumn.setCellValueFactory(new PropertyValueFactory<>("clientUsername"));
            bookingTableView.setItems(bookings);

            bookingTableView.setRowFactory(e -> {
                TableRow<Booking> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && !row.isEmpty()) {
                        selectedBooking = bookingTableView.getSelectionModel().getSelectedItem();
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("bookingDetailsPage.fxml"));
                            Parent root = loader.load();
                            BookingDetailsController detailsController = loader.getController();
                            detailsController.setNameOfAgency(nameOfAgency);
                            detailsController.setUsername(username);
                            detailsController.setBookings(bookings);
                            Stage primaryStage = (Stage) row.getScene().getWindow();
                            primaryStage.close();
                            stage.setScene(new Scene(root));
                            stage.show();
                        } catch (Exception ee) {
                            System.out.println("eroareeeee");
                        }
                    }
                });
                return row;
            });
        });
    }

    @FXML
    public void handleClose() throws IOException {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("travelAgentPage.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
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

    public static void setBookings(ObservableList<Booking> bookings) {
        RezervationsController.bookings = FXCollections.observableArrayList(bookings);
    }

    public static void setNameOfAgency(String nameOfAgency) {
        RezervationsController.nameOfAgency = nameOfAgency;
    }

    public static void setUsername(String username) {
        RezervationsController.username = username;
    }

    public static Booking getSelectedBooking() {
        return selectedBooking;
    }

}