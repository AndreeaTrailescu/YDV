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
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.SortOrder;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.openjfx.model.Booking;
import org.openjfx.model.Offer;
import org.openjfx.model.User;
import org.openjfx.services.BookingService;
import org.openjfx.services.OfferService;
import org.openjfx.services.UserService;

import java.io.IOException;
import java.util.Objects;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public class AddOfferController {
    private static String username,id;
    private static String nameOfAgency;
    private final ObjectRepository<User> REPOSITORY =UserService.getUserRepository();
    private final ObjectRepository<Booking> BOOKING_REPOSITORY = BookingService.getBookingRepository();
    private ObservableList<Offer> offers ;
    private ObservableList<Booking> bookings;
    private  static Stage stage = new Stage();

    @FXML
    private Button saveButton;
    @FXML
    private Button closeButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button bookListButton;
    @FXML
    private Button logoutButton;
    @FXML
    private TextField nameOfOffer;
    @FXML
    private TextField destination;
    @FXML
    private TextField hotelName;
    @FXML
    private TextField meals;
    @FXML
    private TextField nights;
    @FXML
    private TextField noOfClients;
    @FXML
    private TextField price;

    @FXML
    public void handleSaveOffer(){
        try {
            User loggedInUser=REPOSITORY.find(eq("username",username)).firstOrDefault();
            nameOfAgency=loggedInUser.getNameOfAgency();
            String id = NitriteId.newId().toString();
            OfferService.addOffer(id,nameOfAgency,nameOfOffer.getText(),destination.getText(),hotelName.getText(),meals.getText(),nights.getText(),noOfClients.getText(),price.getText());
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("addOfferPage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) (saveButton.getScene().getWindow());
            AddOfferController controller = loader.getController();
            controller.setOffers(offers);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    @FXML
    public void handleClose(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("travelAgentPage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            AgencyPageController controller = loader.getController();
            controller.setUsername(username);
            controller.setNameOfAgency(nameOfAgency);
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
    public void handleEdit() throws Exception{
        ObjectRepository<Offer> repo = OfferService.getOfferRepository();
        ObservableList<Offer> newList = FXCollections.observableArrayList();
        Cursor<Offer> cursor = repo.find(FindOptions.sort("nameOfOffer", SortOrder.Ascending));
        for(Offer offer:cursor) {
            if(Objects.equals(nameOfAgency,offer.getNameOfAgency())) {
                newList.add(offer);
            }
        }
        offers = newList;
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("editOfferPage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        EditOfferController controller = loader.getController();
        controller.setUsername(username);
        controller.setNameOfAgency(nameOfAgency);
        controller.setOffers(offers);
        Stage primaryStage = (Stage) editButton.getScene().getWindow();
        primaryStage.close();
    }

    @FXML
    public void handleBookingList() throws IOException {
        Cursor<Booking> cursor = BOOKING_REPOSITORY.find(eq("nameOfAgency",nameOfAgency));
        bookings = FXCollections.observableArrayList();
        for(Booking b : cursor) {
            if(!b.getMessage().contains("deadline"))
                bookings.add(b);
        }

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("bookingsPage.fxml"));
            Parent root = loader.load();
            RezervationsController controller = loader.getController();
            controller.setNameOfAgency(nameOfAgency);
            controller.setBookings(bookings);
            controller.setUsername(username);
            Stage bookList = (Stage) bookListButton.getScene().getWindow();
            bookList.close();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root));
            stage1.show();

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(String id) {
        AddOfferController.id = id;
    }

    public void setNameOfAgency(String nameOfAgency) {
        this.nameOfAgency = nameOfAgency;
    }

    public static Stage getStage() {
        return stage;
    }

    public void setOffers(ObservableList<Offer> offers) {
        this.offers = offers;
    }
}
