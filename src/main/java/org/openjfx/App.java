package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.openjfx.services.FileSystemService;
import org.openjfx.services.OfferService;
import org.openjfx.services.UserService;

import java.nio.file.Files;
import java.nio.file.Path;


public class App extends Application {


    public void start(Stage stage) throws Exception {
        initDirectory();
        UserService.initDatabase();
        OfferService.initDatabase();
        Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void initDirectory() {
        Path applicationHomePath = FileSystemService.APPLICATION_HOME_PATH;
        if (!Files.exists(applicationHomePath))
            applicationHomePath.toFile().mkdirs();

        Path offersHomePath = FileSystemService.OFFERS_HOME_PATH;
        if (!Files.exists(offersHomePath))
            offersHomePath.toFile().mkdirs();
    }

    public static void main(String[] args) {
        launch(args);
    }
}