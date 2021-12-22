package dk.kk.trackandtrace_004;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuApplication.class.getResource("mainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 450, 400);
        stage.setTitle("Track and Trace");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}