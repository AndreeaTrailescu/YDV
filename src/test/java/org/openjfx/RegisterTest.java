package org.openjfx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openjfx.services.BookingService;
import org.openjfx.services.FileSystemService;
import org.openjfx.services.UserService;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class RegisterTest {

    @AfterEach
    void tearDown() {
        UserService.getDatabase().close();
        BookingService.getDatabase().close();
    }

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-registration-database";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
        FileSystemService.BOOKINGS_FOLDER = ".test-bookings-database";
        FileSystemService.initBookingDirectory();
        FileUtils.cleanDirectory(FileSystemService.getBookingsHomeFolder().toFile());
        BookingService.initDatabase();
    }

    @Start
    void start(Stage stage) throws IOException {
        Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void loginTest(FxRobot robot) {

        robot.clickOn("#username");
        robot.write("agent");
        robot.clickOn("#password");
        robot.write("password1");
        robot.clickOn("#role");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);

        robot.clickOn("#loginButton");

        robot.clickOn("#name");
        robot.write("user1");
        robot.clickOn("#eMail");
        robot.write("user1");
        robot.clickOn("#phoneNumber");
        robot.write("0000000000");
        robot.clickOn("#nameOfAgency");
        robot.write("agency1");

        robot.clickOn("#saveButton");
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        robot.clickOn("#logoutButton");

        robot.clickOn("#username");
        robot.write("client");
        robot.clickOn("#password");
        robot.write("password2");
        robot.clickOn("#role");
        robot.type(KeyCode.ENTER);
        robot.clickOn("#loginButton");

        robot.clickOn("#name");
        robot.write("user2");
        robot.clickOn("#eMail");
        robot.write("user2");
        robot.clickOn("#phoneNumber");
        robot.write("1111111111");

        robot.clickOn("#saveButton");
        assertThat(UserService.getAllUsers()).size().isEqualTo(2);
        robot.clickOn("#logoutButton");

        robot.clickOn("#username");
        robot.write("agent");
        robot.clickOn("#password");
        robot.write("password1");
        robot.clickOn("#role");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.clickOn("#loginButton");
        robot.clickOn("#logoutButton");

        robot.clickOn("#username");
        robot.write("client");
        robot.clickOn("#password");
        robot.write("password2");
        robot.clickOn("#role");
        robot.type(KeyCode.ENTER);
        robot.clickOn("#loginButton");
        robot.clickOn("#logoutButton");

        robot.clickOn("#username");
        robot.write("agent");
        robot.clickOn("#password");
        robot.write("password2");
        robot.clickOn("#role");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);

        robot.clickOn("#loginButton");
        assertThat(robot.lookup("#message").queryText()).hasText("Username already exists or the password entered is incorrect!");

    }

}