package org.openjfx.controllers;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openjfx.services.BookingService;
import org.openjfx.services.FileSystemService;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

class HistoryBookingControllerTest {

    @AfterEach
    void tearDown() {
        BookingService.getDatabase().close();
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
    }

    @Test
    @DisplayName("Bookings are added in database and then specific bookings for a client are saved in list")
    void testListWithBookingsForAClientIsCreated() {
        BookingService.addBooking("1","user1","agency1","offer1","8","1000","01-01-2000","01-02-2000","Accepted","10");
        BookingService.addBooking("2","user1","agency2","offer2","2","2000","01-01-2000","01-02-2000","Rejected","10");
        BookingService.addBooking("3","user2","agency1","offer1","8","1000","01-01-2000","01-02-2000","Accepted","10");
        BookingService.addBooking("4","user2","agency1","offer2","5","2000","01-01-2000","01-02-2000","Rejected","10");
        BookingService.addBooking("5","user1","agency3","offer3","2","5000","01-01-2000","01-02-2000","Your booking hasn't been approved/rejected yet.","10");

        HistoryBookingController.setUsername("user1");
        HistoryBookingController.getAllBookings();
        assertThat(HistoryBookingController.getBookings()).isNotNull();
        assertThat(HistoryBookingController.getBookings()).size().isEqualTo(3);
    }
}