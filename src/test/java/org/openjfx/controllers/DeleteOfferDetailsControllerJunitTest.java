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

class DeleteOfferDetailsControllerJunitTest {
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
        UserService.addUser2("user1", "1111", "Travel Agent", "user1", "user1@gmail.com", "0711111111", "DreamVacation");
        AgenciesListController.getAllAgencies();
        assertThat(AgenciesListController.getListOfAgencies()).isNotNull();
        assertThat(AgenciesListController.getListOfAgencies()).size().isEqualTo(1);
    }

    @AfterEach
    void tearDown() {
        UserService.getDatabase().close();
        OfferService.getDatabase().close();
    }

    @Test
    @DisplayName("The offer from a list with one offer is deleted")
    void testOfferFromAListWithOneOfferIsDeleted() {
        DeleteOfferController.setNameOfAgency(AgenciesListController.getListOfAgencies().get(0));
        DeleteOfferController.getAllOffers();
        assertThat(DeleteOfferController.getListOfOffers()).isNotNull();
        assertThat(DeleteOfferController.getListOfOffers()).size().isEqualTo(0);

        OfferService.addOffer("1", "DreamVacation", "offer", "destination", "hotel", "2", "5", "100", "150");
        DeleteOfferController.getAllOffers();
        assertThat(DeleteOfferController.getListOfOffers()).isNotNull();
        assertThat(DeleteOfferController.getListOfOffers()).size().isEqualTo(1);

        DeleteOfferDetailsController.setNameOfAgency(AgenciesListController.getListOfAgencies().get(0));
        DeleteOfferDetailsController.setNameOfOffer(DeleteOfferController.getListOfOffers().get(0));
        ObjectRepository<Offer> OFFER_REPOSITORY = OfferService.getOfferRepository();
        OFFER_REPOSITORY.remove(and(eq("nameOfAgency", DeleteOfferDetailsController.getNameOfAgency()), eq("nameOfOffer", DeleteOfferDetailsController.getNameOfOffer())));
        DeleteOfferController.getAllOffers();
        assertThat(DeleteOfferController.getListOfOffers()).isNotNull();
        assertThat(DeleteOfferController.getListOfOffers()).size().isEqualTo(0);
    }

    @Test
    @DisplayName("The first offer from the list of offers is deleted")
    void testFirstOfferFromAListOfOffersIsDeleted() {
        OfferService.addOffer("1","DreamVacation","offer3","destination","hotel","2","5","100","150");
        OfferService.addOffer("2","DreamVacation","offer2","destination","hotel","1","4","120","180");

        DeleteOfferController.setNameOfAgency(AgenciesListController.getListOfAgencies().get(0));
        DeleteOfferController.getAllOffers();
        assertThat(DeleteOfferController.getListOfOffers()).isNotNull();
        assertThat(DeleteOfferController.getListOfOffers()).size().isEqualTo(2);

        DeleteOfferDetailsController.setNameOfAgency(AgenciesListController.getListOfAgencies().get(0));
        DeleteOfferDetailsController.setNameOfOffer(DeleteOfferController.getListOfOffers().get(0));
        ObjectRepository<Offer> OFFER_REPOSITORY = OfferService.getOfferRepository();
        OFFER_REPOSITORY.remove(and(eq("nameOfAgency", DeleteOfferDetailsController.getNameOfAgency()), eq("nameOfOffer", DeleteOfferDetailsController.getNameOfOffer())));
        DeleteOfferController.getAllOffers();
        assertThat(DeleteOfferController.getListOfOffers()).isNotNull();
        assertThat(DeleteOfferController.getListOfOffers()).size().isEqualTo(1);
        assertThat(DeleteOfferController.getListOfOffers().get(0)).isEqualTo("offer3");
    }

    @Test
    @DisplayName("The last offer from the list of offers is deleted")
    void testLastOfferFromAListOfOffersIsDeleted() {
        OfferService.addOffer("1","DreamVacation","offer3","destination","hotel","2","5","100","150");
        OfferService.addOffer("2","DreamVacation","offer2","destination","hotel","1","4","120","180");
        OfferService.addOffer("3","DreamVacation","offer4","destination","hotel","3","7","80","119");
        OfferService.addOffer("4","DreamVacation","offer1","destination","hotel","1","4","120","180");


        DeleteOfferController.setNameOfAgency(AgenciesListController.getListOfAgencies().get(0));
        DeleteOfferController.getAllOffers();
        assertThat(DeleteOfferController.getListOfOffers()).isNotNull();
        assertThat(DeleteOfferController.getListOfOffers()).size().isEqualTo(4);

        DeleteOfferDetailsController.setNameOfAgency(AgenciesListController.getListOfAgencies().get(0));
        DeleteOfferDetailsController.setNameOfOffer(DeleteOfferController.getListOfOffers().get(3));
        ObjectRepository<Offer> OFFER_REPOSITORY = OfferService.getOfferRepository();
        OFFER_REPOSITORY.remove(and(eq("nameOfAgency", DeleteOfferDetailsController.getNameOfAgency()), eq("nameOfOffer", DeleteOfferDetailsController.getNameOfOffer())));
        DeleteOfferController.getAllOffers();
        assertThat(DeleteOfferController.getListOfOffers()).isNotNull();
        assertThat(DeleteOfferController.getListOfOffers()).size().isEqualTo(3);
        assertThat(DeleteOfferController.getListOfOffers().get(0)).isEqualTo("offer1");
        assertThat(DeleteOfferController.getListOfOffers().get(1)).isEqualTo("offer2");
        assertThat(DeleteOfferController.getListOfOffers().get(2)).isEqualTo("offer3");
    }

    @Test
    @DisplayName("The middle offer from the list of offers is deleted")
    void testMiddleOfferFromAListOfOffersIsDeleted() {
        OfferService.addOffer("1","DreamVacation","offer3","destination","hotel","2","5","100","150");
        OfferService.addOffer("2","DreamVacation","offer2","destination","hotel","2","6","150","200");
        OfferService.addOffer("3","DreamVacation","offer4","destination","hotel","3","7","80","119");
        OfferService.addOffer("4","DreamVacation","offer1","destination","hotel","1","4","120","180");
        OfferService.addOffer("5","DreamVacation","offer","destination","hotel","1","4","120","180");

        DeleteOfferController.setNameOfAgency(AgenciesListController.getListOfAgencies().get(0));
        DeleteOfferController.getAllOffers();
        assertThat(DeleteOfferController.getListOfOffers()).isNotNull();
        assertThat(DeleteOfferController.getListOfOffers()).size().isEqualTo(5);

        DeleteOfferDetailsController.setNameOfAgency(AgenciesListController.getListOfAgencies().get(0));
        DeleteOfferDetailsController.setNameOfOffer(DeleteOfferController.getListOfOffers().get(2));
        ObjectRepository<Offer> OFFER_REPOSITORY = OfferService.getOfferRepository();
        OFFER_REPOSITORY.remove(and(eq("nameOfAgency", DeleteOfferDetailsController.getNameOfAgency()), eq("nameOfOffer", DeleteOfferDetailsController.getNameOfOffer())));
        DeleteOfferController.getAllOffers();
        assertThat(DeleteOfferController.getListOfOffers()).isNotNull();
        assertThat(DeleteOfferController.getListOfOffers()).size().isEqualTo(4);
        assertThat(DeleteOfferController.getListOfOffers().get(0)).isEqualTo("offer");
        assertThat(DeleteOfferController.getListOfOffers().get(1)).isEqualTo("offer1");
        assertThat(DeleteOfferController.getListOfOffers().get(2)).isEqualTo("offer3");
        assertThat(DeleteOfferController.getListOfOffers().get(3)).isEqualTo("offer4");
    }
}