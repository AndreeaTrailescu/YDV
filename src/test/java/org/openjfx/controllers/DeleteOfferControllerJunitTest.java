package org.openjfx.controllers;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openjfx.model.Offer;
import org.openjfx.services.FileSystemService;
import org.openjfx.services.OfferService;
import org.openjfx.services.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DeleteOfferControllerJunitTest {
    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.OFFERS_FOLDER = ".test-offers-database";
        FileSystemService.initOffersDirectory();
        FileUtils.cleanDirectory(FileSystemService.getOffersHomeFolder().toFile());
        OfferService.initDatabase();
    }

    @AfterEach
    void tearDown() {
        OfferService.getDatabase().close();
    }

    @Test
    @DisplayName("The list of offers is correctly retrieved from the database")
    void testOffersAreRetrievedCorrectlyFromTheDatabase(){
        OfferService.addOffer("1","agency","offer","destination","hotel","2","5","100","150");
        OfferService.addOffer("2","agency2","offer2","destination","hotel","3","4","120","180");
        OfferService.addOffer("3","agency2","offer1","destination","hotel","1","6","90","110");
        assertThat(OfferService.getAllOffers()).isNotEmpty();
        assertThat(OfferService.getAllOffers()).size().isEqualTo(3);

        DeleteOfferController.setNameOfAgency("agency");
        DeleteOfferController.getAllOffers();
        assertThat(DeleteOfferController.getListOfOffers()).isNotNull();
        assertThat(DeleteOfferController.getListOfOffers()).size().isEqualTo(1);
        assertThat(DeleteOfferController.getListOfOffers().get(0)).isEqualTo("offer");

        DeleteOfferController.setNameOfAgency("agency2");
        DeleteOfferController.getAllOffers();
        assertThat(DeleteOfferController.getListOfOffers()).isNotNull();
        assertThat(DeleteOfferController.getListOfOffers()).size().isEqualTo(2);
        assertThat(DeleteOfferController.getListOfOffers().get(0)).isEqualTo("offer1");
        assertThat(DeleteOfferController.getListOfOffers().get(1)).isEqualTo("offer2");
    }
}