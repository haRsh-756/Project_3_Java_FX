package com.example.project3fx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Scanner;

public class TuitionManagerController implements Initializable {

    /**
     * scanner object instance
     */
    Scanner scnr;
    /**
     * Roster object instance
     */
    Roster roster;
    /**
     * enrollment object instance
     */
    Enrollment enrollment;
    private String fname;
    private String lname;
    private String dob;
    private int credits;
    private double schpAmnt;
    private String major;
    private boolean isResident = false;
    @FXML
    private Button add;
    @FXML
    private Button drop;
    @FXML
    private Button enroll;
    @FXML
    private Button changerMajor;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextField roster_creditsCompleted;
    @FXML
    private TextField enroll_Credits;
    @FXML
    private TextField enroll_fname;
    @FXML
    private TextField enroll_lname;
    @FXML
    private DatePicker enroll_dob;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu rosterMenu;
    @FXML
    private MenuItem byProfile;
    @FXML
    private MenuItem bySchoolMajor;
    @FXML
    private MenuItem byStanding;
    @FXML
    private Menu school;
    @FXML
    private MenuItem RBS;
    @FXML
    private MenuItem SAS;
    @FXML
    private MenuItem SCandI;
    @FXML
    private MenuItem SOE;
    @FXML
    private Menu enrollmentMenu;
    @FXML
    private MenuItem byEnrollStudents;
    @FXML
    private MenuItem byTuitionDue;
    @FXML
    private MenuItem bySemEnd;
    @FXML
    private RadioButton ct;
    @FXML
    private TextField schp_fname;
    @FXML
    private TextField schp_lname;
    @FXML
    private TextField schp_Amount;
    @FXML
    private TextField roster_firstName;
    @FXML
    private DatePicker schp_dob;
    @FXML
    private Button fromFile;
    @FXML
    private Button updateSchpAmount;
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
    private final String errorMessage = "-fx-text-fill: RED;";
    private final String successMessage = "-fx-text-fill: GREEN;";

    private boolean checkDetails(Student student, String date){
        if(date == null) {
            return false;
        }
        else{
            messageArea.setStyle(errorMessage);
            if (!student.checkDate()) {
                messageArea.setText("DOB invalid: " + date + " not a valid calendar date!");
                return false;
            } else if (!student.ageLimit()) {
                messageArea.setText("DOB invalid: " + date + " younger than 16 years old.");
                return false;
            } else if (student.getCreditCompleted() < 0) {
                messageArea.setText("Credits completed invalid: cannot be negative!");
                return false;
            }
        }
        return true;
    }
    private void checkDetailsAndAdd(Student s, String fname, String lname, String dob, String state, boolean isFromFile){

        if(state != null && !state.equalsIgnoreCase("NY") && !state.equalsIgnoreCase("CT")){
            messageArea.setStyle(errorMessage);
            messageArea.setText(state +": Invalid state code.");
            return;
        }
        if(checkDetails(s, dob) && !isFromFile) {
            messageArea.setStyle(successMessage);
            if (roster.add(s)) {
                messageArea.setText(fname + " " + lname + " " + dob + " added to the roster.");
            } else {
                messageArea.setText(fname + " " + lname + " " + dob + " already in the roster.");
            }
        }
        else if(isFromFile){
            if(checkDetails(s,dob)){
                roster.add(s); //no printing
            }
        }
    }
    private void processAndInsert_INTL_STUDENT(){
        try {
            messageArea.setStyle(errorMessage);
            if(!getMissingDataStatusForRoster()) {
                getFirstName();
                getLastName();
                getCreditsCompleted();
                boolean isStudyABROAD = false;
                if(!roster.checkAvailableMajor(major)){
                    messageArea.setText("Major code invalid: " + major);
                    return;
                }
                if(studyAbroad.isSelected()){
                    isStudyABROAD = true;
                }
                Student s = new International(fname,lname,new Date(dob),Major.valueOf(major),credits,isStudyABROAD);
                checkDetailsAndAdd(s,fname,lname,dob,null,false);
            } else {
                messageArea.setText("Missing data.");
            }
        }catch (NumberFormatException e){
            messageArea.setText("Credits completed invalid: not an integer!");
        }catch (NullPointerException e1){
            e1.printStackTrace();
        }
    }
    private void processAndInsert_TRI_STATE_STUDENT(){
        try {
            messageArea.setStyle(errorMessage);
            if (!getMissingDataStatusForRoster()) {
                getFirstName();
                getLastName();
                getCreditsCompleted();
                String state = "";
                if (!roster.checkAvailableMajor(major)) {
                    messageArea.setText("Major code invalid: " + major);
                    return;
                }
                if (!ny.isSelected() && !ct.isSelected()) {
                    messageArea.setText("Missing the state code.");
                    return;
                } else if (ny.isSelected()) {
                    state = "ny";
                } else if (ct.isSelected()) {
                    state = "ct";
                }
                Student s = new TriState(fname, lname, new Date(dob), Major.valueOf(major), credits, state);
                checkDetailsAndAdd(s, fname, lname, dob, state, false);
            }
            else {
                messageArea.setText("Missing data.");
            }
        }catch (NumberFormatException e){
            messageArea.setText("Credits completed invalid: not an integer!");
        }catch (NullPointerException e1){
            e1.printStackTrace();
        }
    }
    private void processAndInsert_RES_STUDENT(){
        try {
            if(!getMissingDataStatusForRoster()){
                if(!roster.checkAvailableMajor(major)){
                    messageArea.setStyle(errorMessage);
                    messageArea.setText("Major code invalid: " + major);
                    return;
                }
                getFirstName();
                getLastName();
                getCreditsCompleted();
                Student s = new Resident(fname, lname,new Date(dob),Major.valueOf(major),credits);
                checkDetailsAndAdd(s, fname, lname,dob,null,false);
            }
            else{
                messageArea.setStyle(errorMessage);
                messageArea.setText("Missing data.");
            }
        }catch(NumberFormatException e) {
            messageArea.setStyle(errorMessage);
            messageArea.setText("Not an integer");
        }catch (NullPointerException e1){
            messageArea.setStyle(errorMessage);
            messageArea.setText("Something went wrong");
            e1.printStackTrace();
        }
    }
    private void processAndInsert_NON_RES_STUDENT(){
        try {
            messageArea.setStyle(errorMessage);
            if (!getMissingDataStatusForRoster()) {
                getFirstName();
                getLastName();
                getCreditsCompleted();
                if(!roster.checkAvailableMajor(major)){
                    messageArea.setText("Major code invalid: " + major);
                    return;
                }
                Student s = new NonResident(fname,lname,new Date(dob),Major.valueOf(major),credits);
                checkDetailsAndAdd(s,fname,lname,dob,null,false);
            } else {
                messageArea.setText("Missing data.");
            }
        }catch (NumberFormatException e){
            messageArea.setText("Credits completed invalid: not an integer!");
        }catch (NullPointerException e1){
            e1.printStackTrace();
        }
    }
    /**
     * Helper method to add studentType data from file
     * @param dataArr student data
     * @param studentType process and add resident, non-resident, tri-state, International
     * @param fname student's first name
     * @param lname student's last name
     * @param dob student's date of birth
     * @param major student's major
     */
    private void addStudentType_FROMFILE_HELPER(String [] dataArr, String studentType, String fname, String lname, String dob, String major){
        switch (studentType) {
            case "R" -> {
                int credits = Integer.parseInt(dataArr[5]);
                Student s = new Resident(fname, lname, new Date(dob), Major.valueOf(major), credits);
                checkDetailsAndAdd(s, fname, lname, dob, null,true);
            }
            case "N" -> {
                int credits = Integer.parseInt(dataArr[5]);
                Student s = new NonResident(fname, lname, new Date(dob), Major.valueOf(major), credits);
                checkDetailsAndAdd(s, fname, lname, dob, null,true);
            }
            case "I" -> {
                int credits = Integer.parseInt(dataArr[5]);
                boolean isStudyAbroad = Boolean.parseBoolean(dataArr[6]);
                Student s = new International(fname, lname, new Date(dob), Major.valueOf(major), credits, isStudyAbroad);
                checkDetailsAndAdd(s, fname, lname, dob, null,true);
            }
            case "T" -> {
                int credits = Integer.parseInt(dataArr[5]);
                String state = dataArr[6];
                Student s = new TriState(fname, lname, new Date(dob), Major.valueOf(major), credits, state);
                checkDetailsAndAdd(s, fname, lname, dob, state,true);
            }
        }
    }
    /**
     * method to process and add students from file
     */
    private void processAndInsert_FROM_FILE(){
        try{
            Stage stage = new Stage();
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File selectedFile = fileChooser.showOpenDialog(stage);

            if(selectedFile != null)
                if(!selectedFile.getName().endsWith(".txt")) {
                    throw new IllegalArgumentException("Invalid file format. Only .txt files are allowed.");
                }

            Scanner scnr = new Scanner(selectedFile);
            while(scnr.hasNext()) {
                String[] dataArr = scnr.nextLine().trim().split("[\\s+,]");
                String studentType = dataArr[0];
                String fname = dataArr[1];
                String lname = dataArr[2];
                String dob = dataArr[3];
                String major = dataArr[4].toUpperCase();
                if (!roster.checkAvailableMajor(major)) {
                    //System.out.println("Major code invalid: " + major);
                    return;
                }
                addStudentType_FROMFILE_HELPER(dataArr,studentType,fname,lname,dob,major);
            }
            messageArea.setStyle(successMessage);
            messageArea.setText("Students loaded to the roster.");
            scnr.close();
        }catch (NumberFormatException e){
            //System.out.println("Credits completed invalid: not an integer!");
        }catch (NullPointerException e1){
            e1.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void updateMajor(){
        if(!getMissingDataStatusForRoster() && !roster.isEmpty()){
            getFirstName();
            getLastName();
            messageArea.setStyle(successMessage);
            if(!roster.checkAvailableMajor(major)){
                messageArea.setStyle(errorMessage);
                messageArea.setText("Major code invalid: " + major);
                return;
            }
            if(roster.lookup(fname, lname,dob,Major.valueOf(major))){
                messageArea.setText(fname + " " + lname + " " + dob + " major changed to "
                        + Major.valueOf(major).name());
            }
            else{
                messageArea.setText(fname + " " + lname + " " + dob + " is not in the roster.");
            }
        }
    }
    private void processAndRemove(){
        try {
            if (!getMissingDataStatusForRoster() && !roster.isEmpty()) {
                getFirstName();
                getLastName();
                messageArea.setStyle(successMessage);
                if (roster.lookupAndRemove(fname, lname, dob)) {
                    messageArea.setText(fname + " " + lname + " " + dob + " removed from the roster.");
                } else {
                    messageArea.setText(fname + " " + lname + " " + dob + " is not in the roster.");
                }
            }
            else if (!getMissingDataStatusForRoster() && roster.isEmpty()) {
                messageArea.setStyle(errorMessage);
                messageArea.setText("Student roster is empty!");
            }
            else{
                messageArea.setStyle(errorMessage);
                messageArea.setText("Missing data.");
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
    public boolean getMissingDataStatusForRoster(){
        return roster_firstName.getText().isBlank() || roster_lastName.getText().isBlank()
                || roster_dob.getValue() == null || roster_creditsCompleted.getText().isBlank();
    }
    public boolean getMissingDataStatusForEnroll(){
        return enroll_fname.getText().isBlank() || enroll_lname.getText().isBlank()
                || enroll_dob.getValue() == null || enroll_Credits.getText().isBlank();
    }
    public boolean getMissingDataStatusForSchp(){
        return schp_fname.getText().isBlank() || schp_lname.getText().isBlank()
                || schp_dob.getValue() == null || schp_Amount.getText().isBlank();
    }
    @FXML
    protected void getFirstName(){
        fname = roster_firstName.getText();
        fname = enroll_fname.getText();
        fname = schp_fname.getText();
    }
    @FXML
    protected void getLastName(){
        lname = roster_lastName.getText();
        lname = enroll_lname.getText();
        lname = schp_lname.getText();
    }
    @FXML
    protected void getDob(ActionEvent event){
        if(roster_dob.getValue() != null) {
            LocalDate date = roster_dob.getValue();
            dob = date.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
        }
        else if(enroll_dob.getValue() != null){
            LocalDate date = enroll_dob.getValue();
            dob = date.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
        }
        else if(schp_dob.getValue() != null){
            LocalDate date = schp_dob.getValue();
            dob = date.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
        }
    }
    @FXML
    protected void getCreditsCompleted(){
        if(!getMissingDataStatusForRoster()) {
            credits = Integer.parseInt(roster_creditsCompleted.getText());
        }
        else if(!getMissingDataStatusForEnroll()) {
            credits = Integer.parseInt(enroll_Credits.getText());
        }
    }
    @FXML
    protected void getSchpAmount(){
        schpAmnt = Double.parseDouble(schp_Amount.getText());
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
    protected void getMajor(ActionEvent event){
        major = comboBox.getSelectionModel().getSelectedItem().toUpperCase();
    }

    @FXML
    protected void LoadFromFile(ActionEvent event) {
        processAndInsert_FROM_FILE();
    }

    @FXML
    protected void addStudent(ActionEvent event) {
        if(nonResident.isSelected()){
            if(triState.isSelected()){
                processAndInsert_TRI_STATE_STUDENT();
            }
            else if(intl.isSelected()){
                processAndInsert_INTL_STUDENT();
            }
            else{
                processAndInsert_NON_RES_STUDENT();
            }
        }
        else{
            processAndInsert_RES_STUDENT();
        }
    }
    @FXML
    protected void removeStudent(ActionEvent event) {
        processAndRemove();
    }
    @FXML
    protected void updateMajor(ActionEvent event) {
        updateMajor();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roster = new Roster();
        enrollment = new Enrollment();
        ObservableList<String> options = FXCollections.observableArrayList(
                Major.CS.name(), Major.EE.name(), Major.ITI.name(), Major.BAIT.name()
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
        add.setOnAction(this::addStudent);
        remove.setOnAction(this::removeStudent);
        changerMajor.setOnAction(this::updateMajor);
        fromFile.setOnAction(this::LoadFromFile);
        //enroll.setOnAction();
    }
}
