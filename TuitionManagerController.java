package com.example.project3fx;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TuitionManagerController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextArea messageArea;
    @FXML
    private DatePicker roster_dob;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}