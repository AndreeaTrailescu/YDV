package org.openjfx.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openjfx.model.Booking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BookingServiceTest {
    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.BOOKINGS_FOLDER = ".test-bookings-database";
        FileSystemService.initBookingDirectory();
        FileUtils.cleanDirectory(FileSystemService.getBookingsHomeFolder().toFile());
        BookingService.initDatabase();
    }

    @AfterEach
    void tearDown() {
        BookingService.getDatabase().close();
    }

    @Test
    @DisplayName("Database is initialized, and there are no bookings")
    void testDatabaseIsInitializedAndNoBookingIsPersisted() {
        assertThat(BookingService.getAllBookings()).isNotNull();
        assertThat(BookingService.getAllBookings()).isEmpty();
    }

    @Test
    @DisplayName("Booking is successfully persisted to Database")
    void testBookingIsAddedToDatabase(){
        BookingService.addBooking("1","user","agency","offer","2","500","05-07-2021","10-07-2021","Your booking hasn't been approved/rejected yet.","0");
        assertThat(BookingService.getAllBookings()).isNotNull();
        assertThat(BookingService.getAllBookings()).size().isEqualTo(1);
        Booking booking = BookingService.getAllBookings().get(0);
        assertThat(booking).isNotNull();
        assertThat(booking.getId()).isEqualTo("1");
        assertThat(booking.getClientUsername()).isEqualTo("user");
        assertThat(booking.getNameOfAgency()).isEqualTo("agency");
        assertThat(booking.getNameOfOffer()).isEqualTo("offer");
        assertThat(booking.getNumberOfPersons()).isEqualTo("2");
        assertThat(booking.getTotalPrice()).isEqualTo("500");
        assertThat(booking.getCheckInDate()).isEqualTo("05-07-2021");
        assertThat(booking.getCheckOutDate()).isEqualTo("10-07-2021");
        assertThat(booking.getMessage()).isEqualTo("Your booking hasn't been approved/rejected yet.");
        assertThat(booking.getRating()).isEqualTo("0");
    }
}