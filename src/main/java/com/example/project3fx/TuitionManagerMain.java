package com.example.project3fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

/**
 * main class to run tuitionManager gui
 * @author harsh_patel and giancarlo_Andretta
 */
public class TuitionManagerMain extends Application {
    /**
     * method sets the necessary stage setting, style css, and fxml
     * @param stage stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TuitionManagerMain.class.getResource("TuitionManagerView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 800);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm());
        stage.setTitle("TuitionManager!");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * main method to launch the gui
     * @param args cmd line args
     */
    public static void main(String[] args) {
        launch();
    }
}