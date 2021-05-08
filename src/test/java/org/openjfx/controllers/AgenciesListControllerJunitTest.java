package org.openjfx.controllers;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openjfx.services.FileSystemService;
import org.openjfx.services.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AgenciesListControllerJunitTest {

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-registration-database";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
    }

    @AfterEach
    void tearDown() {
        UserService.getDatabase().close();
    }

    @Test
    @DisplayName("The list of agencies is correctly retrieved from the database")
    void testAgenciesAreRetrievedCorrectlyFromTheDatabase() {
        UserService.addUser2("user1","1111","Travel Agent","user1","user1@gmail.com","0711111111","DreamVacation");
        UserService.addUser2("user2","2222","Travel Agent","user2","user2@gmail.com","0722222222","BestBooking");
        UserService.addUser2("user3","3333","Travel Agent","user3","user3@gmail.com","0733333333","AuthenticVacation");
        UserService.addUser2("user4","4444","Travel Agent","user4","user4@gmail.com","0744444444","DreamVacation");
        UserService.addUser2("user5","5555","Travel Agent","user5","user5@gmail.com","0755555555","SupremeVacation");
        UserService.addUser2("user6","6666","Travel Agent","user6","user6@gmail.com","0766666666","SupremeVacation");
        AgenciesListController.getAllAgencies();
        assertThat(AgenciesListController.getListOfAgencies()).isNotNull();
        assertThat(AgenciesListController.getListOfAgencies()).size().isEqualTo(4);
        assertThat(AgenciesListController.getListOfAgencies().get(0)).isEqualTo("AuthenticVacation");
        assertThat(AgenciesListController.getListOfAgencies().get(1)).isEqualTo("BestBooking");
        assertThat(AgenciesListController.getListOfAgencies().get(2)).isEqualTo("DreamVacation");
        assertThat(AgenciesListController.getListOfAgencies().get(3)).isEqualTo("SupremeVacation");
    }
}