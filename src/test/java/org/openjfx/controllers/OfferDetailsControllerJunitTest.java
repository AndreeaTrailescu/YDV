package org.openjfx.controllers;

import org.apache.commons.io.FileUtils;
import org.dizitart.no2.objects.ObjectRepository;
import org.junit.jupiter.api.*;
import org.openjfx.model.Offer;
import org.openjfx.services.FileSystemService;
import org.openjfx.services.OfferService;
import org.openjfx.services.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dizitart.no2.objects.filters.ObjectFilters.and;
import static org.dizitart.no2.objects.filters.ObjectFilters.eq;
import static org.junit.jupiter.api.Assertions.*;

class OfferDetailsControllerJunitTest {
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
    @DisplayName("The details of the selected offer from a list with one offer are displayed correctly")
    void testDetailsOfSelectedOfferAreDisplayedCorrectlyWithAListWithOneOffer() {
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

        OfferDetailsController.setSelectedAgency(AgenciesListController.getListOfAgencies().get(0));
        OfferDetailsController.setSelectedOffer(controller.getListOfOffers().get(0));
        ObjectRepository<Offer> OFFER_REPOSITORY = OfferService.getOfferRepository();
        Offer offerSelected = OFFER_REPOSITORY.find(and(eq("nameOfAgency", OfferDetailsController.getSelectedAgency()),eq("nameOfOffer",OfferDetailsController.getSelectedOffer()))).firstOrDefault();
        assertThat(offerSelected.getId()).isEqualTo("1");
        assertThat(offerSelected.getNameOfAgency()).isEqualTo(OfferDetailsController.getSelectedAgency());
        assertThat(offerSelected.getNameOfOffer()).isEqualTo(OfferDetailsController.getSelectedOffer());
        assertThat(offerSelected.getDestination()).isEqualTo("destination");
        assertThat(offerSelected.getHotelName()).isEqualTo("hotel");
        assertThat(offerSelected.getMeals()).isEqualTo("2");
        assertThat(offerSelected.getNights()).isEqualTo("5");
        assertThat(offerSelected.getNoOfClients()).isEqualTo("100");
        assertThat(offerSelected.getPrice()).isEqualTo("150");
    }

    @Test
    @DisplayName("The details of the first offer from the list are displayed correctly")
    void testDetailsOfTheFirstOfferAreDisplayedCorrectly() {
        UserService.addUser2("user1","1111","Travel Agent","user1","user1@gmail.com","0711111111","DreamVacation");
        AgenciesListController.getAllAgencies();
        assertThat(AgenciesListController.getListOfAgencies()).isNotNull();
        assertThat(AgenciesListController.getListOfAgencies()).size().isEqualTo(1);

        OfferService.addOffer("1","DreamVacation","offer3","destination","hotel","2","5","100","150");
        OfferService.addOffer("2","DreamVacation","offer2","destination","hotel","1","4","120","180");

        OffersPageController controller = new OffersPageController();
        OffersPageController.setSelectedAgency(AgenciesListController.getListOfAgencies().get(0));
        controller.getAllOffers();
        assertThat(controller.getListOfOffers()).isNotNull();
        assertThat(controller.getListOfOffers()).size().isEqualTo(2);

        OfferDetailsController.setSelectedAgency(AgenciesListController.getListOfAgencies().get(0));
        OfferDetailsController.setSelectedOffer(controller.getListOfOffers().get(0));
        ObjectRepository<Offer> OFFER_REPOSITORY = OfferService.getOfferRepository();
        Offer offerSelected = OFFER_REPOSITORY.find(and(eq("nameOfAgency", OfferDetailsController.getSelectedAgency()),eq("nameOfOffer",OfferDetailsController.getSelectedOffer()))).firstOrDefault();
        assertThat(offerSelected.getId()).isEqualTo("2");
        assertThat(offerSelected.getNameOfAgency()).isEqualTo(OfferDetailsController.getSelectedAgency());
        assertThat(offerSelected.getNameOfOffer()).isEqualTo(OfferDetailsController.getSelectedOffer());
        assertThat(offerSelected.getDestination()).isEqualTo("destination");
        assertThat(offerSelected.getHotelName()).isEqualTo("hotel");
        assertThat(offerSelected.getMeals()).isEqualTo("1");
        assertThat(offerSelected.getNights()).isEqualTo("4");
        assertThat(offerSelected.getNoOfClients()).isEqualTo("120");
        assertThat(offerSelected.getPrice()).isEqualTo("180");
    }

    @Test
    @DisplayName("The details of the last offer from the list are displayed correctly")
    void testDetailsOfTheLastOfferAreDisplayedCorrectly() {
        UserService.addUser2("user1","1111","Travel Agent","user1","user1@gmail.com","0711111111","DreamVacation");
        AgenciesListController.getAllAgencies();
        assertThat(AgenciesListController.getListOfAgencies()).isNotNull();
        assertThat(AgenciesListController.getListOfAgencies()).size().isEqualTo(1);

        OfferService.addOffer("1","DreamVacation","offer3","destination","hotel","2","5","100","150");
        OfferService.addOffer("2","DreamVacation","offer2","destination","hotel","1","4","120","180");
        OfferService.addOffer("3","DreamVacation","offer4","destination","hotel","3","7","80","119");
        OfferService.addOffer("4","DreamVacation","offer1","destination","hotel","1","4","120","180");

        OffersPageController controller = new OffersPageController();
        OffersPageController.setSelectedAgency(AgenciesListController.getListOfAgencies().get(0));
        controller.getAllOffers();
        assertThat(controller.getListOfOffers()).isNotNull();
        assertThat(controller.getListOfOffers()).size().isEqualTo(4);

        OfferDetailsController.setSelectedAgency(AgenciesListController.getListOfAgencies().get(0));
        OfferDetailsController.setSelectedOffer(controller.getListOfOffers().get(3));
        ObjectRepository<Offer> OFFER_REPOSITORY = OfferService.getOfferRepository();
        Offer offerSelected = OFFER_REPOSITORY.find(and(eq("nameOfAgency", OfferDetailsController.getSelectedAgency()),eq("nameOfOffer",OfferDetailsController.getSelectedOffer()))).firstOrDefault();
        assertThat(offerSelected.getId()).isEqualTo("3");
        assertThat(offerSelected.getNameOfAgency()).isEqualTo(OfferDetailsController.getSelectedAgency());
        assertThat(offerSelected.getNameOfOffer()).isEqualTo(OfferDetailsController.getSelectedOffer());
        assertThat(offerSelected.getDestination()).isEqualTo("destination");
        assertThat(offerSelected.getHotelName()).isEqualTo("hotel");
        assertThat(offerSelected.getMeals()).isEqualTo("3");
        assertThat(offerSelected.getNights()).isEqualTo("7");
        assertThat(offerSelected.getNoOfClients()).isEqualTo("80");
        assertThat(offerSelected.getPrice()).isEqualTo("119");
    }

    @Test
    @DisplayName("The details of the middle offer from the list are displayed correctly")
    void testDetailsOfTheMiddleOfferAreDisplayedCorrectly() {
        UserService.addUser2("user1","1111","Travel Agent","user1","user1@gmail.com","0711111111","DreamVacation");
        AgenciesListController.getAllAgencies();
        assertThat(AgenciesListController.getListOfAgencies()).isNotNull();
        assertThat(AgenciesListController.getListOfAgencies()).size().isEqualTo(1);

        OfferService.addOffer("1","DreamVacation","offer3","destination","hotel","2","5","100","150");
        OfferService.addOffer("2","DreamVacation","offer2","destination","hotel","2","6","150","200");
        OfferService.addOffer("3","DreamVacation","offer4","destination","hotel","3","7","80","119");
        OfferService.addOffer("4","DreamVacation","offer1","destination","hotel","1","4","120","180");
        OfferService.addOffer("5","DreamVacation","offer","destination","hotel","1","4","120","180");

        OffersPageController controller = new OffersPageController();
        OffersPageController.setSelectedAgency(AgenciesListController.getListOfAgencies().get(0));
        controller.getAllOffers();
        assertThat(controller.getListOfOffers()).isNotNull();
        assertThat(controller.getListOfOffers()).size().isEqualTo(5);

        OfferDetailsController.setSelectedAgency(AgenciesListController.getListOfAgencies().get(0));
        OfferDetailsController.setSelectedOffer(controller.getListOfOffers().get(2));
        ObjectRepository<Offer> OFFER_REPOSITORY = OfferService.getOfferRepository();
        Offer offerSelected = OFFER_REPOSITORY.find(and(eq("nameOfAgency", OfferDetailsController.getSelectedAgency()),eq("nameOfOffer",OfferDetailsController.getSelectedOffer()))).firstOrDefault();
        assertThat(offerSelected.getId()).isEqualTo("2");
        assertThat(offerSelected.getNameOfAgency()).isEqualTo(OfferDetailsController.getSelectedAgency());
        assertThat(offerSelected.getNameOfOffer()).isEqualTo(OfferDetailsController.getSelectedOffer());
        assertThat(offerSelected.getDestination()).isEqualTo("destination");
        assertThat(offerSelected.getHotelName()).isEqualTo("hotel");
        assertThat(offerSelected.getMeals()).isEqualTo("2");
        assertThat(offerSelected.getNights()).isEqualTo("6");
        assertThat(offerSelected.getNoOfClients()).isEqualTo("150");
        assertThat(offerSelected.getPrice()).isEqualTo("200");
    }
}