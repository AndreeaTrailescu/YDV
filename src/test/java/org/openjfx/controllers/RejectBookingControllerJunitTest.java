package org.openjfx.controllers;

import org.apache.commons.io.FileUtils;
import org.dizitart.no2.objects.ObjectRepository;
import org.junit.jupiter.api.*;
import org.openjfx.model.Booking;
import org.openjfx.services.BookingService;
import org.openjfx.services.FileSystemService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dizitart.no2.objects.filters.ObjectFilters.and;
import static org.dizitart.no2.objects.filters.ObjectFilters.eq;
import static org.junit.jupiter.api.Assertions.*;

class RejectBookingControllerJunitTest {
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
    @DisplayName("The message of the selected booking from a list with one booking is changed")
    void testMessageOfTheBookingFromTheListWithOneBookingIsChanged() {
        BookingService.addBooking("1","user","agency","offer","2","500","05-07-2021","10-07-2021","Your booking hasn't been approved/rejected yet.","0");
        RezervationsController.setNameOfAgency("agency");
        RezervationsController.getAllBookings();
        assertThat(RezervationsController.getBookings()).isNotNull();
        assertThat(RezervationsController.getBookings()).size().isEqualTo(1);
        ObjectRepository<Booking> BOOKING_REPOSITORY = BookingService.getBookingRepository();

        Booking booking = RezervationsController.getBookings().get(0);
        assertThat(booking.getMessage()).isEqualTo("Your booking hasn't been approved/rejected yet.");
        booking.setMessage("Rejected. " );
        BOOKING_REPOSITORY.update(booking);
        assertThat(booking.getMessage()).isEqualTo("Rejected. ");
    }

    @Test
    @DisplayName("The message of the first booking of the list is changed")
    void testMessageOfTheFirstBookingOfTheListIsChanged() {
        BookingService.addBooking("1","user","agency","offer","2","500","05-07-2021","10-07-2021","Your booking hasn't been approved/rejected yet.","0");
        BookingService.addBooking("2","user","agency","offer2","3","600","05-08-2021","10-08-2021","Your booking hasn't been approved/rejected yet.","0");
        RezervationsController.setNameOfAgency("agency");
        RezervationsController.getAllBookings();
        assertThat(RezervationsController.getBookings()).isNotNull();
        assertThat(RezervationsController.getBookings()).size().isEqualTo(2);
        ObjectRepository<Booking> BOOKING_REPOSITORY = BookingService.getBookingRepository();

        Booking booking = RezervationsController.getBookings().get(0);
        assertThat(booking.getMessage()).isEqualTo("Your booking hasn't been approved/rejected yet.");
        booking.setMessage("Rejected. There are not enough vacancies left." );
        BOOKING_REPOSITORY.update(booking);
        assertThat(booking.getMessage()).isEqualTo("Rejected. There are not enough vacancies left.");
    }

    @Test
    @DisplayName("The message of the last booking of the list is changed")
    void testMessageOfTheLastBookingOfTheListIsChanged() {
        BookingService.addBooking("1","user","agency","offer1","2","500","05-07-2021","10-07-2021","Your booking hasn't been approved/rejected yet.","0");
        BookingService.addBooking("2","user","agency","offer2","3","600","05-08-2021","10-08-2021","Your booking hasn't been approved/rejected yet.","0");
        BookingService.addBooking("3","user","agency","offer3","2","500","05-07-2021","10-07-2021","Your booking hasn't been approved/rejected yet.","0");
        BookingService.addBooking("4","user","agency","offer4","3","600","05-08-2021","10-08-2021","Your booking hasn't been approved/rejected yet.","0");
        RezervationsController.setNameOfAgency("agency");
        RezervationsController.getAllBookings();
        assertThat(RezervationsController.getBookings()).isNotNull();
        assertThat(RezervationsController.getBookings()).size().isEqualTo(4);
        ObjectRepository<Booking> BOOKING_REPOSITORY = BookingService.getBookingRepository();

        Booking booking = RezervationsController.getBookings().get(3);
        assertThat(booking.getMessage()).isEqualTo("Your booking hasn't been approved/rejected yet.");
        booking.setMessage("Rejected." );
        BOOKING_REPOSITORY.update(booking);
        assertThat(booking.getMessage()).isEqualTo("Rejected.");
    }

    @Test
    @DisplayName("The message of the middle booking of the list is changed")
    void testMessageOfTheMiddleBookingOfTheListIsChanged() {
        BookingService.addBooking("1","user","agency","offer1","2","500","05-07-2021","10-07-2021","Your booking hasn't been approved/rejected yet.","0");
        BookingService.addBooking("2","user","agency","offer2","3","600","05-08-2021","10-08-2021","Your booking hasn't been approved/rejected yet.","0");
        BookingService.addBooking("3","user","agency","offer3","2","500","05-07-2021","10-07-2021","Your booking hasn't been approved/rejected yet.","0");
        BookingService.addBooking("4","user","agency","offer4","3","600","05-08-2021","10-08-2021","Your booking hasn't been approved/rejected yet.","0");
        BookingService.addBooking("5","user","agency","offer5","3","600","05-08-2021","10-08-2021","Your booking hasn't been approved/rejected yet.","0");
        RezervationsController.setNameOfAgency("agency");
        RezervationsController.getAllBookings();
        assertThat(RezervationsController.getBookings()).isNotNull();
        assertThat(RezervationsController.getBookings()).size().isEqualTo(5);
        ObjectRepository<Booking> BOOKING_REPOSITORY = BookingService.getBookingRepository();

        Booking booking = RezervationsController.getBookings().get(2);
        assertThat(booking.getMessage()).isEqualTo("Your booking hasn't been approved/rejected yet.");
        booking.setMessage("Rejected." );
        BOOKING_REPOSITORY.update(booking);
        assertThat(booking.getMessage()).isEqualTo("Rejected.");
        assertThat(booking.getNameOfOffer()).isEqualTo("offer3");
    }
}