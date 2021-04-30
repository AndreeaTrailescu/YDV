package org.openjfx.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import org.dizitart.no2.objects.ObjectRepository;
import org.openjfx.model.Booking;
import org.openjfx.services.BookingService;

import java.io.IOException;

public class RatingController {
    private final ObjectRepository<Booking> BOOKING_REPOSITORY = BookingService.getBookingRepository();
    private static Booking selectedBooking = HistoryBookingController.getSelectedBooking();
    private Stage anotherStage;

    @FXML
    private TextField rating;
    @FXML
    private Button saveButton;

    @FXML
    public void initialize() {
        rating.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("([0-9]|10)")) {
                        saveButton.setDisable(true);
                } else {
                    saveButton.setDisable(false);
                }
            }
        });
    }

    @FXML
    public void handleSave() throws IOException {
        selectedBooking.setRating(rating.getText());

        BOOKING_REPOSITORY.update(selectedBooking);
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("historyBooking.fxml"));
        Parent root = loader.load();
        HistoryBookingController controller = loader.getController();
        controller.setStage(anotherStage);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void setAnotherStage(Stage anotherStage) {
        this.anotherStage = anotherStage;
    }
}
