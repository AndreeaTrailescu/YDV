package org.openjfx.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openjfx.model.Offer;

import static org.assertj.core.api.Assertions.assertThat;

class OfferServiceTest {
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
    @DisplayName("Database is initialized, and there are no offers")
    void testDatabaseIsInitializedAndNoOfferIsPersisted() {
        assertThat(OfferService.getAllOffers()).isNotNull();
        assertThat(OfferService.getAllOffers()).isEmpty();

    }

    @Test
    @DisplayName("Offer is successfully persisted to Database")
    void testOfferIsAddedToDatabase(){
        OfferService.addOffer("1","agency","offer","destination","hotel","2","5","100","150");
        assertThat(OfferService.getAllOffers()).isNotEmpty();
        assertThat(OfferService.getAllOffers()).size().isEqualTo(1);
        Offer offer = OfferService.getAllOffers().get(0);
        assertThat(offer).isNotNull();
        assertThat(offer.getId()).isEqualTo("1");
        assertThat(offer.getNameOfAgency()).isEqualTo("agency");
        assertThat(offer.getNameOfOffer()).isEqualTo("offer");
        assertThat(offer.getDestination()).isEqualTo("destination");
        assertThat(offer.getHotelName()).isEqualTo("hotel");
        assertThat(offer.getMeals()).isEqualTo("2");
        assertThat(offer.getNights()).isEqualTo("5");
        assertThat(offer.getNoOfClients()).isEqualTo("100");
        assertThat(offer.getPrice()).isEqualTo("150");
    }
}