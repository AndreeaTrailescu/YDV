package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.openjfx.model.Booking;
import org.openjfx.services.BookingService;
import org.openjfx.services.FileSystemService;
import org.openjfx.services.OfferService;
import org.openjfx.services.UserService;

import java.nio.file.Files;
import java.nio.file.Path;


public class App extends Application {


    public void start(Stage stage) throws Exception {
        UserService.initDatabase();
        OfferService.initDatabase();
        BookingService.initDatabase();
        Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}