package org.openjfx.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.openjfx.model.Booking;
import org.openjfx.services.BookingService;
import org.openjfx.services.UserService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public class HomePageController {
    private static String username;
    private Stage anotherStage;
    private final ObjectRepository<Booking> BOOKING_REPOSITORY = BookingService.getBookingRepository();

    @FXML
    private Button logoutButton;
    @FXML
    private Button agencyListButton;
    @FXML
    private Button bookListButton;
    @FXML
    private Text messageText;

    @FXML
    public void initialize() {
        Platform.runLater(()->{
            if(findBookings() == 1) messageText.setText("Rating became available!");
        });
    }

    @FXML
    public void handleAgenciesList() throws Exception{
        try {
            AgenciesListController.getAllAgencies();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("travelAgenciesList.fxml"));
            Parent root = loader.load();
            anotherStage = (Stage) (bookListButton.getScene().getWindow());
            AgenciesListController controller = loader.getController();
            controller.setAnotherStage(anotherStage);
            Stage stage = (Stage) (agencyListButton.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();
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

    @FXML
    public  void handleHistory() throws Exception{
        try {
            AgenciesListController.getAllAgencies();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("historyBooking.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) (bookListButton.getScene().getWindow());
            anotherStage = (Stage) (bookListButton.getScene().getWindow());
            HistoryBookingController controller = loader.getController();
            controller.setStage(anotherStage);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public int findBookings() {
        int ok = 0;
        try {
            Cursor<Booking> cursor = BOOKING_REPOSITORY.find(eq("clientUsername", username));
            for (Booking b : cursor) {
                if (b.getMessage().contains("Accepted") || b.getMessage().contains("Rejected")) {
                    Date d1,d2;
                    LocalDate now = LocalDate.now();
                    String date1 = now.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                    d1 = formatter.parse(date1);
                    d2 = formatter.parse(b.getCheckOutDate());
                    if(d2.compareTo(d1) > 0) {
                        ok = 1;
                    }
                }

            }

        } catch (ParseException parseException) {
            parseException.printStackTrace();
        }
        return ok;

    }

    public static void setUsername(String username) {
        HomePageController.username = username;
    }
}