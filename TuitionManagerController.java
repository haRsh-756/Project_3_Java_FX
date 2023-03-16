package com.example.project3fx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class TuitionManagerController implements Initializable {

    private String firstName;
    private String lastName;
    private String dob;
    private int credits;
    private String major;
    private boolean isResident = false;
    @FXML
    private Button add;

    @FXML
    private Button changerMajor;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextField roster_creditsCompleted;

    @FXML
    private RadioButton ct;

    @FXML
    private TextField roster_firstName;

    @FXML
    private Button fromFile;

    @FXML
    private RadioButton intl;

    @FXML
    private TextField roster_lastName;

    @FXML
    private TextArea messageArea;

    @FXML
    private ToggleGroup nonRes;

    @FXML
    private CheckBox nonResident;

    @FXML
    private RadioButton ny;

    @FXML
    private Button remove;

    @FXML
    private DatePicker roster_dob;

    @FXML
    private ToggleGroup state;

    @FXML
    private CheckBox studyAbroad;

    @FXML
    private RadioButton triState;

    @FXML
    protected void getFirstName(ActionEvent event){
        firstName = roster_firstName.getText();
    }
    @FXML
    protected void getLastName(ActionEvent event){
        lastName = roster_lastName.getText();
    }
    @FXML
    protected void getDob(ActionEvent event){
        LocalDate date = roster_dob.getValue();
        dob = date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }
    @FXML
    protected void getCreditsCompleted(ActionEvent event){
        try {
            credits = Integer.parseInt(roster_creditsCompleted.getText());
        }catch(NumberFormatException e){
            messageArea.setText("Not a integer");
        }catch (Exception e){
            messageArea.setText("Something went wrong");
        }
    }
    @FXML
    protected void handleTriState(ActionEvent event){
        triState.setDisable(false);
        ny.setDisable(false);
        ct.setDisable(false);
        intl.setDisable(false);
        studyAbroad.setDisable(false);
        if(triState.isSelected()){
            studyAbroad.setDisable(true);
        }
    }
    @FXML
    protected void handleIntl(ActionEvent event){
        triState.setDisable(false);
        ny.setDisable(false);
        ct.setDisable(false);
        intl.setDisable(false);
        studyAbroad.setDisable(false);
        if(intl.isSelected()){
            ny.setDisable(true); ct.setDisable(true);
        }
    }
    @FXML
    protected void isNonResident(ActionEvent event){
        if(nonResident.isSelected()){
            isResident = false;
            triState.setDisable(false);
            ny.setDisable(false);
            ct.setDisable(false);
            intl.setDisable(false);
            studyAbroad.setDisable(false);
        }
        else{
            isResident = true;
            triState.setDisable(true);
            ny.setDisable(true);
            ct.setDisable(true);
            intl.setDisable(true);
            studyAbroad.setDisable(true);
        }
    }
    @FXML
    protected void select(ActionEvent event){
        major = comboBox.getSelectionModel().getSelectedItem();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Roster roster = new Roster();
        Enrollment enrollment = new Enrollment();
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        Major.CS.name(),Major.EE.name(),Major.ITI.name(),Major.BAIT.name()
                );
        comboBox.setItems(options);
        nonRes = new ToggleGroup();
        state = new ToggleGroup();
        triState.setToggleGroup(nonRes);
        intl.setToggleGroup(nonRes);
        ct.setToggleGroup(state);
        ny.setToggleGroup(state);
        isResident = true;
        triState.setDisable(true);
        ny.setDisable(true);
        ct.setDisable(true);
        intl.setDisable(true);
        studyAbroad.setDisable(true);
    }
    @FXML
    protected void LoadFromFile(ActionEvent event) {

    }

    @FXML
    protected void addStudent(ActionEvent event) {
        messageArea.setText("in progress");
        /*EventHandler<ActionEvent> event = e -> {

        };
        add.setOnAction(event);*/
    }

    @FXML
    protected void removeStudent(ActionEvent event) {

    }

    @FXML
    protected void updateMajor(ActionEvent event) {

    }



    /*@FXML
    protected void onHelloButtonClick() {
        myLabel.setText("Welcome to JavaFX Application!");
    }
    @FXML
    protected void getDate(ActionEvent event){
        LocalDate date = roster_dob.getValue();

    }*/
}