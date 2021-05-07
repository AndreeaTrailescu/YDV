package org.openjfx.controllers;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openjfx.model.Booking;
import org.openjfx.services.BookingService;
import org.openjfx.services.FileSystemService;
import org.openjfx.services.OfferService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AcceptControllerTest {

    @AfterEach
    void tearDown() {
        BookingService.getDatabase().close();
        OfferService.getDatabase().close();
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before All");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After All");
    }

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.BOOKINGS_FOLDER = ".test-bookings-database";
        FileSystemService.initBookingDirectory();
        FileUtils.cleanDirectory(FileSystemService.getBookingsHomeFolder().toFile());
        BookingService.initDatabase();
        FileSystemService.OFFERS_FOLDER = ".test-offers-database";
        FileSystemService.initOffersDirectory();
        FileUtils.cleanDirectory(FileSystemService.getOffersHomeFolder().toFile());
        OfferService.initDatabase();
    }

    @Test
    @DisplayName("Number of places available for an offer is update in database")
    void testNumberOfClientsIsUpdate() {
        OfferService.addOffer("1","agency1","offer1","destination","hotel","2","5","100","150");
        OfferService.addOffer("2","agency2","offer2","destination","hotel","2","5","100","150");
        OfferService.addOffer("3","agency1","offer3","destination","hotel","2","5","100","150");
        AcceptController.setNameOfAgency("agency1");
        AcceptController.setSelectedBooking(new Booking("1","user1","agency1","offer3","2","400","01-01-2000","01-02-2000","Accepted:deadline is 10-01-2000","10"));
        AcceptController.updateNumberOfClients("offer3");
        assertThat(OfferService.getAllOffers()).isNotNull();
        assertThat(OfferService.getAllOffers().get(2)).isNotNull();
        assertThat(OfferService.getAllOffers().get(2).getNoOfClients()).isEqualTo("98");
    }

    @Test
    @DisplayName("Bookings are added in database and then specific bookings for a client are saved in list")
    void testListWithBookingsForAClientIsCreated() {
        BookingService.addBooking("1","user1","agency1","offer1","8","1000","01-01-2000","01-02-2000","Accepted","10");
        BookingService.addBooking("2","user1","agency3","offer2","2","2000","01-01-2000","01-02-2000","Rejected","10");
        BookingService.addBooking("3","user2","agency1","offer1","8","1000","01-01-2000","01-02-2000","Accepted","10");
        BookingService.addBooking("4","user2","agency1","offer2","5","2000","01-01-2000","01-02-2000","Rejected","10");
        BookingService.addBooking("5","user1","agency3","offer3","2","5000","01-01-2000","01-02-2000","Your booking hasn't been approved/rejected yet.","10");
        BookingService.addBooking("6","user2","agency3","offer3","2","5000","01-01-2000","01-02-2000","Accepted:deadline is 10-01-2000","10");

        AcceptController.setNameOfAgency("agency3");
        AcceptController.getAllBookings();
        assertThat(AcceptController.getBookings()).isNotNull();
        assertThat(AcceptController.getBookings()).size().isEqualTo(1);
    }
}