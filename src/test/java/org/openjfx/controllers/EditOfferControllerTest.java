package org.openjfx.controllers;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openjfx.services.FileSystemService;
import org.openjfx.services.OfferService;

import static org.assertj.core.api.Assertions.assertThat;


class EditOfferControllerTest {

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
    @DisplayName("Offers are added in database and then specific offers of an agency are saved in list")
    void testListWithOffersOfAnAgencyIsCreated() {
        OfferService.addOffer("1","agency1","offer1","destination","hotel","2","5","100","150");
        OfferService.addOffer("2","agency2","offer2","destination","hotel","2","5","100","150");
        OfferService.addOffer("3","agency1","offer3","destination","hotel","2","5","100","150");
        EditOfferController.setNameOfAgency("agency1");
        EditOfferController.getAllOffers();
        assertThat(EditOfferController.getOffers()).isNotNull();
        assertThat(EditOfferController.getOffers()).size().isEqualTo(2);
    }
}