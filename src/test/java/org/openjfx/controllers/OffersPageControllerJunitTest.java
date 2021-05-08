package org.openjfx.controllers;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openjfx.services.FileSystemService;
import org.openjfx.services.OfferService;
import org.openjfx.services.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OffersPageControllerJunitTest {

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-registration-database";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
        FileSystemService.OFFERS_FOLDER = ".test-offers-database";
        FileSystemService.initOffersDirectory();
        FileUtils.cleanDirectory(FileSystemService.getOffersHomeFolder().toFile());
        OfferService.initDatabase();
    }

    @AfterEach
    void tearDown() {
        UserService.getDatabase().close();
        OfferService.getDatabase().close();
    }

    @Test
    @DisplayName("The offers of the selected agency from a list with one agency are displayed correctly")
    void testOffersOfSelectedAgencyAreDisplayedCorrectlyWithAListWithOneAgency() {
        UserService.addUser2("user1","1111","Travel Agent","user1","user1@gmail.com","0711111111","DreamVacation");
        AgenciesListController.getAllAgencies();
        assertThat(AgenciesListController.getListOfAgencies()).isNotNull();
        assertThat(AgenciesListController.getListOfAgencies()).size().isEqualTo(1);

        OffersPageController controller = new OffersPageController();
        OffersPageController.setSelectedAgency(AgenciesListController.getListOfAgencies().get(0));
        controller.getAllOffers();
        assertThat(controller.getListOfOffers()).isNotNull();
        assertThat(controller.getListOfOffers()).size().isEqualTo(0);

        OfferService.addOffer("1","DreamVacation","offer","destination","hotel","2","5","100","150");
        controller.getAllOffers();
        assertThat(controller.getListOfOffers()).isNotNull();
        assertThat(controller.getListOfOffers()).size().isEqualTo(1);
        assertThat(controller.getListOfOffers().get(0)).isEqualTo("offer");
    }
    @Test
    @DisplayName("The offers of the first agency from the list are displayed correctly")
    void testOffersOfTheFirstAgencyAreDisplayedCorrectly() {
        UserService.addUser2("user1","1111","Travel Agent","user1","user1@gmail.com","0711111111","BestBooking");
        UserService.addUser2("user2","2222","Travel Agent","user2","user2@gmail.com","0722222222","AuthenticVacation");
        AgenciesListController.getAllAgencies();
        assertThat(AgenciesListController.getListOfAgencies()).isNotNull();
        assertThat(AgenciesListController.getListOfAgencies()).size().isEqualTo(2);

        OfferService.addOffer("1","BestBooking","offer","destination","hotel","2","5","100","150");
        OfferService.addOffer("2","AuthenticVacation","offer2","destination","hotel","2","5","100","150");
        OfferService.addOffer("3","AuthenticVacation","offer1","destination","hotel","2","5","100","150");

        OffersPageController controller = new OffersPageController();
        OffersPageController.setSelectedAgency(AgenciesListController.getListOfAgencies().get(0));
        controller.getAllOffers();
        assertThat(controller.getListOfOffers()).isNotNull();
        assertThat(controller.getListOfOffers()).size().isEqualTo(2);
        assertThat(controller.getListOfOffers().get(0)).isEqualTo("offer1");
        assertThat(controller.getListOfOffers().get(1)).isEqualTo("offer2");
    }

    @Test
    @DisplayName("The offers of the last agency from the list are displayed correctly")
    void testOffersOfTheLastAgencyAreDisplayedCorrectly() {
        UserService.addUser2("user1","1111","Travel Agent","user1","user1@gmail.com","0711111111","DreamVacation");
        UserService.addUser2("user2","2222","Travel Agent","user2","user2@gmail.com","0722222222","BestBooking");
        UserService.addUser2("user3","3333","Travel Agent","user3","user3@gmail.com","0733333333","AuthenticVacation");
        AgenciesListController.getAllAgencies();
        assertThat(AgenciesListController.getListOfAgencies()).isNotNull();
        assertThat(AgenciesListController.getListOfAgencies()).size().isEqualTo(3);

        OfferService.addOffer("1","DreamVacation","offer3","destination","hotel","2","5","100","150");
        OfferService.addOffer("2","DreamVacation","offer2","destination","hotel","2","5","100","150");
        OfferService.addOffer("3","DreamVacation","offer1","destination","hotel","2","5","100","150");

        OffersPageController controller = new OffersPageController();
        OffersPageController.setSelectedAgency(AgenciesListController.getListOfAgencies().get(2));
        controller.getAllOffers();
        assertThat(controller.getListOfOffers()).isNotNull();
        assertThat(controller.getListOfOffers()).size().isEqualTo(3);
        assertThat(controller.getListOfOffers().get(0)).isEqualTo("offer1");
        assertThat(controller.getListOfOffers().get(1)).isEqualTo("offer2");
        assertThat(controller.getListOfOffers().get(2)).isEqualTo("offer3");
    }

    @Test
    @DisplayName("The offers of the middle agency from the list are displayed correctly")
    void testOffersOfTheMiddleAgencyAreDisplayedCorrectly() {
        UserService.addUser2("user1","1111","Travel Agent","user1","user1@gmail.com","0711111111","DreamVacation");
        UserService.addUser2("user2","2222","Travel Agent","user2","user2@gmail.com","0722222222","BestBooking");
        UserService.addUser2("user3","3333","Travel Agent","user3","user3@gmail.com","0733333333","AuthenticVacation");
        UserService.addUser2("user4","4444","Travel Agent","user4","user4@gmail.com","0744444444","CoolVacation");
        UserService.addUser2("user5","5555","Travel Agent","user5","user5@gmail.com","0755555555","SupremeVacation");
        AgenciesListController.getAllAgencies();
        assertThat(AgenciesListController.getListOfAgencies()).isNotNull();
        assertThat(AgenciesListController.getListOfAgencies()).size().isEqualTo(5);

        OffersPageController controller = new OffersPageController();
        OffersPageController.setSelectedAgency(AgenciesListController.getListOfAgencies().get(2));
        controller.getAllOffers();
        assertThat(controller.getListOfOffers()).isNotNull();
        assertThat(controller.getListOfOffers()).size().isEqualTo(0);

        OfferService.addOffer("1","CoolVacation","offer","destination","hotel","2","5","100","150");
        controller.getAllOffers();
        assertThat(controller.getListOfOffers()).isNotNull();
        assertThat(controller.getListOfOffers()).size().isEqualTo(1);
        assertThat(controller.getListOfOffers().get(0)).isEqualTo("offer");
    }
}