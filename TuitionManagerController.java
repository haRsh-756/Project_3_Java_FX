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
import java.text.DecimalFormat;
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
    private int creditsCompleted;
    private int creditsEnrolled;
    private double schpAmnt;
    private String major;
    private boolean isResident = false;
    @FXML private TabPane tabPane;
    @FXML private Tab roster_tab;
    @FXML private Tab Enroll_DropTab;
    @FXML private Tab Scholarship_Tab;
    @FXML private Tab Print_Tab;
    @FXML private Button add;
    @FXML private Button drop;
    @FXML private Button enroll;
    @FXML private Button changerMajor;

    @FXML private ComboBox<String> comboBox;

    @FXML private TextField roster_creditsCompleted;
    @FXML private TextField enroll_Credits;
    @FXML private TextField enroll_fname;
    @FXML private TextField enroll_lname;
    @FXML private DatePicker enroll_dob;
    @FXML private MenuBar menuBar;
    @FXML private Menu rosterMenu;
    @FXML private MenuItem byProfile;
    @FXML private MenuItem bySchoolMajor;
    @FXML private MenuItem byStanding;
    @FXML private Menu school;
    @FXML private MenuItem RBS;
    @FXML private MenuItem SAS;
    @FXML private MenuItem SCandI;
    @FXML private MenuItem SOE;
    @FXML private Menu enrollmentMenu;
    @FXML private MenuItem byEnrollStudents;
    @FXML private MenuItem byTuitionDue;
    @FXML private MenuItem bySemEnd;
    @FXML private RadioButton ct;
    @FXML private TextField schp_fname;
    @FXML private TextField schp_lname;
    @FXML private TextField schp_Amount;
    @FXML private TextField roster_firstName;
    @FXML private DatePicker schp_dob;
    @FXML private Button fromFile;
    @FXML private Button updateSchpAmount;
    @FXML private RadioButton intl;
    @FXML private TextField roster_lastName;
    @FXML private TextArea messageArea;
    @FXML private ToggleGroup nonRes;
    @FXML private CheckBox nonResident;
    @FXML private RadioButton ny;
    @FXML private Button remove;
    @FXML private DatePicker roster_dob;
    @FXML private ToggleGroup state;
    @FXML private CheckBox studyAbroad;
    @FXML private RadioButton triState;
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
                currentTabData();
                boolean isStudyABROAD = false;
                if(!roster.checkAvailableMajor(major)){
                    messageArea.setText("Major code invalid: " + major);
                    return;
                }
                if(studyAbroad.isSelected()){
                    isStudyABROAD = true;
                }
                Student s = new International(fname,lname,new Date(dob),Major.valueOf(major), creditsCompleted,isStudyABROAD);
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
                currentTabData();
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
                Student s = new TriState(fname, lname, new Date(dob), Major.valueOf(major), creditsCompleted, state);
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
                currentTabData();
                Student s = new Resident(fname, lname,new Date(dob),Major.valueOf(major), creditsCompleted);
                checkDetailsAndAdd(s, fname, lname,dob,null,false);
            }
            else{
                messageArea.setStyle(errorMessage);
                messageArea.setText("Missing data.");
            }
        }catch(NumberFormatException e) {
            messageArea.setStyle(errorMessage);
            messageArea.setText("Credits completed invalid: not an integer!");
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
                currentTabData();
                if(!roster.checkAvailableMajor(major)){
                    messageArea.setText("Major code invalid: " + major);
                    return;
                }
                Student s = new NonResident(fname,lname,new Date(dob),Major.valueOf(major), creditsCompleted);
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
            //Scanner scnr = new Scanner(new File("studentList.txt"));
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
    /**
     * method checks the details of enroll student with the help of credits enrolled
     * @param student student object
     * @param creditsEnrolled enrolled credits of student
     * @return -1 if invalid credits, on success returns 0
     */
    private int checkDetailsOfEnrollStudent(Student student, int creditsEnrolled){
        messageArea.setStyle(errorMessage);
        if(student instanceof Resident r){
            if(!r.isValid(creditsEnrolled)){
                messageArea.setText("(Resident) " + creditsEnrolled + ": invalid credit hours.");
                return -1;
            }
        }else if(student instanceof NonResident){
            if(student instanceof TriState t){
                if(!t.isValid(creditsEnrolled)){
                    messageArea.setText("(Non-Resident) " + creditsEnrolled + ": invalid credit hours.");
                    return -1;
                }
            }else if(student instanceof International intl){
                if(!intl.isValid(creditsEnrolled)){
                    if(intl.isStudyAbroad()){
                        messageArea.setText("(International studentstudy abroad) " + creditsEnrolled + ": invalid credit hours.");
                        return -1;
                    }
                    else {
                        messageArea.setText("(International student) " + creditsEnrolled + ": invalid credit hours.");
                        return -1;
                    }
                }
            }else{
                NonResident nR = (NonResident) student;
                if(!nR.isValid(creditsEnrolled)){
                    messageArea.setText("(Non-Resident) " + creditsEnrolled + ": invalid credit hours.");
                    return -1;
                }
            }
        }
        return 0;
    }

    /**
     * method checks if student is in roster or not and adds to enrollment on success
     * @param fname student's first name
     * @param lname student's last name
     * @param dob   student's date of birth
     * @param creditsEnrolled student's credit enrolled
     */
    private void checkAndEnrollStudent(String fname, String lname, String dob, int creditsEnrolled){
        messageArea.setStyle(errorMessage);
        if(roster.lookup(fname,lname,dob)) {
            Student student = roster.getStudent(new Profile(fname,lname,new Date(dob)));
            if (checkDetailsOfEnrollStudent(student, creditsEnrolled) != 0) {
                return;
            }
            EnrollStudent es = new EnrollStudent(fname, lname, new Date(dob), creditsEnrolled);
            enrollment.add(es);
            messageArea.setStyle(successMessage);
            messageArea.setText(es.toString());
        }
        else{
            messageArea.setText("Cannot enroll: " + fname + " " + lname
                    + " " + dob + " is not in the roster.");
        }
    }
    /**
     * methods helps to process student data and checks if student
     * is eligible for enrollment or not
     */
    private void processAndEnrollStudent(){
        try {
            messageArea.setStyle(errorMessage);
            if(!getMissingDataStatusForEnroll()){
                currentTabData();
                checkAndEnrollStudent(fname,lname,dob,creditsEnrolled);
            }
            else {
                messageArea.setText("Missing data.");
            }
        }catch (NumberFormatException e){
            messageArea.setText("Credits enrolled is not an integer.");
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
    private void updateMajor(){
        if(!getMissingDataStatusForRoster() && !roster.isEmpty()){
            currentTabData();
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
                currentTabData();
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
    /**
     * this method prints student's tuition based on
     * credits enrolled
     */
    private void printTuitionDue(){
        messageArea.clear();
        messageArea.setStyle(successMessage);
        if(enrollment.isEmpty()){
            messageArea.setText("Enrollment is empty!");
            return;
        }
        messageArea.appendText("** Tuition due **" + "\n");
        EnrollStudent [] enrollStudents = enrollment.getEnrollStudents();
        DecimalFormat df = new DecimalFormat("##,###.00");
        for(EnrollStudent enrollStudent: enrollStudents){
            if(enrollStudent != null) {
                int creditEnrolled = enrollStudent.getCreditsEnrolled();
                Student student = roster.getStudent(enrollStudent.getProfile());
                if (student instanceof Resident resident){
                    messageArea.appendText(enrollStudent.getProfile().toString() + resident.print()
                            + enrollStudent.printCreditEnrolled() + " tuition due: $" + df.format(resident.tuitionDue(creditEnrolled)) +"\n");
                }
                else if (student instanceof NonResident nonResident) {
                    if (student instanceof TriState triState) {
                        messageArea.appendText(enrollStudent.getProfile().toString() + triState.printState()
                                + enrollStudent.printCreditEnrolled() + " tuition due: $" + df.format(triState.tuitionDue(creditEnrolled)) +"\n");
                    }
                    else if (student instanceof International intl) {
                        messageArea.appendText(enrollStudent.getProfile().toString() + intl.print()
                                + enrollStudent.printCreditEnrolled() + " tuition due: $" + df.format(intl.tuitionDue(creditEnrolled)) + "\n");
                    }
                    else {
                        messageArea.appendText(enrollStudent.getProfile().toString() + nonResident.print()
                                + enrollStudent.printCreditEnrolled() + " tuition due: $" + df.format(nonResident.tuitionDue(creditEnrolled))+"\n");
                    }
                }
            }
        }
        messageArea.appendText("* end of tuition due *" + "\n");
    }

    /**
     * this method process student data and helps to
     * apply scholarship based on student type
     */
    private void ApplyScholarship(){
        try {
            if(!getMissingDataStatusForSchp()) {
                currentTabData();
                messageArea.setStyle(errorMessage);
                Student student = roster.getStudent(new Profile(fname,lname,new Date(dob)));
                EnrollStudent enrollStudent = enrollment.lookupEnrollStudent(new Profile(fname,lname,new Date(dob)));
                if(student == null){
                    messageArea.setText(successMessage);
                    messageArea.setText(fname + " " + lname + " " + dob + " is not in the roster");
                    return;
                }
                if(student instanceof Resident resident){
                    resident.setScholarship(schpAmnt);
                    if(!resident.checkScholarship()){
                        messageArea.setText(schpAmnt + ": invalid amount.");
                    } else if (enrollStudent != null && resident.isPartTime(enrollStudent.getCreditsEnrolled())){
                        messageArea.setText(student.getProfile().toString() + " part time student is not eligible for the scholarship.");
                    } else{
                        messageArea.setStyle(successMessage);
                        messageArea.setText(student.getProfile().toString() + ": scholarship amount updated.");
                    }
                } else if(student instanceof NonResident nR){
                    messageArea.setText(student.getProfile().toString() + nR.print() + "is not eligible for the scholarship.");
                }
            }else{
                messageArea.setText("Missing data.");
            }
        }catch (NumberFormatException e) {
            messageArea.setText("Amount is not an integer.");
        }
    }

    /**
     * this method list students who are eligible for graduation
     */
    private void printStudentsEligibleForGraduation(){
        messageArea.appendText("** list of students eligible for graduation **" + "\n");
        Student [] students = roster.getRoster();
        final int GRADUATION_CREDITS = 120;
        for(int i = 0; i < roster.getSize(); i++){
            if(students[i] != null && students[i].getCreditCompleted() >= GRADUATION_CREDITS){
                messageArea.appendText(students[i].toString() + "\n");
            }
        }
    }

    /**
     * this method updates total credits of student based on current semester's enrollment
     */
    private void updateCreditsCompletedAndPrint(){
        EnrollStudent [] enrollStudents = enrollment.getEnrollStudents();
        for(int i = 0; i < enrollment.getSize(); i++){
            if(enrollStudents[i] != null) {
                Student student = roster.getStudent(enrollStudents[i].getProfile());
                if (student == null) {
                    return;
                }
                int creditsEnrolled = enrollStudents[i].getCreditsEnrolled();
                student.setCreditCompleted(student.getCreditCompleted() + creditsEnrolled);
            }
        }
        messageArea.clear();
        messageArea.setStyle(successMessage);
        messageArea.appendText("Credit completed has been updated." + "\n");
        printStudentsEligibleForGraduation();
    }

    /**
     * this methods print list of enroll students this semester
     */
    private void enrollmentList(){
        messageArea.setStyle(successMessage);
        if(!enrollment.isEmpty()){
            enrollment.print(messageArea);
        }
        else{
            messageArea.setText("Enrollment is empty!");
        }
    }
    /**
     * this method process student data and check if the student exist in enrollment
     * on success it removes/drop the student from the enrollment
     */
    private void checkAndDropStudent(){
        try {
            messageArea.setStyle(errorMessage);
            if(!getMissingDataStatusForEnroll()) {
                currentTabData();
                messageArea.setStyle(successMessage);
                EnrollStudent es = new EnrollStudent(fname, lname, new Date(dob), -1);
                if (enrollment.contains(es)) {
                    enrollment.remove(es);
                    messageArea.setText(fname + " " + lname + " " + dob + " dropped.");
                } else {
                    messageArea.setText(fname + " " + lname + " " + dob + " is not enrolled.");
                }
            }
            else{
                messageArea.setText("Missing data.");
            }
        }catch(NullPointerException e){
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
    protected void currentTabData(){
        if(roster_tab.isSelected()){
            fname = roster_firstName.getText().trim();
            lname = roster_lastName.getText().trim();
            if(roster_dob.getValue() != null) {
                LocalDate date = roster_dob.getValue();
                dob = date.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
            }
            creditsCompleted = Integer.parseInt(roster_creditsCompleted.getText().trim());
        }
        else if(Enroll_DropTab.isSelected()){
            fname = enroll_fname.getText().trim();
            lname = enroll_lname.getText().trim();
            if(enroll_dob.getValue() != null){
                LocalDate date = enroll_dob.getValue();
                dob = date.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
            }
            creditsEnrolled = Integer.parseInt(enroll_Credits.getText().trim());
        }
        else if(Scholarship_Tab.isSelected()){
            fname = schp_fname.getText().trim();
            lname = schp_lname.getText().trim();
            if(schp_dob.getValue() != null){
                LocalDate date = schp_dob.getValue();
                dob = date.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
            }
            schpAmnt = Double.parseDouble(schp_Amount.getText().trim());
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
    protected void handleNYandCTbtn(ActionEvent event){
        triState.fire();
    }
    @FXML
    protected void handleStudyAbroadCheckBox(ActionEvent event){
        intl.fire();
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
            triState.setSelected(false);
            ny.setSelected(false);
            ct.setSelected(false);
            intl.setSelected(false);
            studyAbroad.setSelected(false);
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
    protected void enrollStudent(ActionEvent event){
        processAndEnrollStudent();
    }
    @FXML
    protected void dropStudent(ActionEvent event){
        checkAndDropStudent();
    }
    @FXML
    protected void removeStudent(ActionEvent event) {
        processAndRemove();
    }
    @FXML
    protected void updateMajor(ActionEvent event) {
        updateMajor();
    }
    @FXML
    protected void UpdateSchpAmount(ActionEvent event){
        ApplyScholarship();
    }
    @FXML
    protected void handleRosterMenuItems(ActionEvent event){
        messageArea.setStyle(successMessage);
        byProfile.setOnAction(event1 -> roster.print(messageArea));
        bySchoolMajor.setOnAction(event12 -> roster.printBySchoolMajor(messageArea));
        byStanding.setOnAction(event13 -> roster.printByStanding(messageArea));
    }
    @FXML
    protected void handleSchoolMenuItems(ActionEvent event){
        messageArea.setStyle(successMessage);
        RBS.setOnAction(event1 -> roster.printBySchool("RBS",messageArea));
        SAS.setOnAction(event12 -> roster.printBySchool("SAS",messageArea));
        SCandI.setOnAction(event13 -> roster.printBySchool("SC&I",messageArea));
        SOE.setOnAction(event14 -> roster.printBySchool("SOE",messageArea));
    }
    @FXML
    protected void handleEnrollmentMenuItems(ActionEvent event){
        messageArea.setStyle(successMessage);
        byEnrollStudents.setOnAction(event1 -> enrollmentList());
        byTuitionDue.setOnAction(event12 -> printTuitionDue());
        bySemEnd.setOnAction(event13 -> updateCreditsCompletedAndPrint());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roster = new Roster();
        enrollment = new Enrollment();
        ObservableList<String> options = FXCollections.observableArrayList(
                        Major.CS.name(), Major.EE.name(), Major.ITI.name(), Major.BAIT.name()
        );
        comboBox.setItems(options);
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
}
/*add.setOnAction(this::addStudent);
        remove.setOnAction(this::removeStudent);
        changerMajor.setOnAction(this::updateMajor);
        fromFile.setOnAction(this::LoadFromFile);
        enroll.setOnAction(this::enrollStudent);
        drop.setOnAction(this::dropStudent);
        updateSchpAmount.setOnAction(this::UpdateSchpAmount);*/
/*@FXML
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
        creditsCompleted = Integer.parseInt(roster_creditsCompleted.getText());
    }
    @FXML
    protected void getCreditsEnrolled(){
        creditsEnrolled = Integer.parseInt(enroll_Credits.getText());
    }
    @FXML
    protected void getSchpAmount(){
        schpAmnt = Double.parseDouble(schp_Amount.getText());
    }*/