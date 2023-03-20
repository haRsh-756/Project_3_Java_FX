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

/**
 * controller class to handle tuition manager GUI
 * and all requests from user
 * @author harsh_patel, giancarlo_andretta
 */
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
    /**
     * first name String object instance to store firstName
     */
    private String fname;
    /**
     * last name String object instance to store lastName
     */
    private String lname;
    /**
     *dob String object instance to store date of birth
     */
    private String dob;
    /**
     * creditscompleted datatype to store creditsCompleted of students
     */
    private int creditsCompleted;
    /**
     * creditsenrolled datatype to store credits enrolled of students
     */
    private int creditsEnrolled;
    /**
     *schpAmount datatype to store scholarship amount given to student
     */
    private double schpAmnt;
    /**
     *major String object instance to store major of student
     */
    private String major;
    /**
     * isResident primitive datatype to check if student is resident or not
     */
    private boolean isResident = false;
    /**
     * TabPane object instance
     */
    @FXML private TabPane tabPane;
    /**
     * roster tab object instance
     */
    @FXML private Tab roster_tab;
    /**
     * enroll/drop tab object instance
     */
    @FXML private Tab Enroll_DropTab;
    /**
     * scholarship tab object instance
     */
    @FXML private Tab Scholarship_Tab;
    /**
     * print_tab object instance
     */
    @FXML private Tab Print_Tab;
    /**
     * add button object instance
     */
    @FXML private Button add;
    /**
     * drop button object instance
     */
    @FXML private Button drop;
    /**
     * enroll button object instance
     */
    @FXML private Button enroll;
    /**
     * change major button object instance
     */
    @FXML private Button changerMajor;
    /**
     * combo box object instance to select an item from a list
     */
    @FXML private ComboBox<String> comboBox;
    /**
     * textfield object instance to store student's creditsCompleted
     */
    @FXML private TextField roster_creditsCompleted;
    /**
     *  textfield object instance to store student's enrolled credits
     */
    @FXML private TextField enroll_Credits;
    /**
     * textfield object instance to store first Name of student
     */
    @FXML private TextField enroll_fname;
    /**
     *textfield object instance to store last Name of student
     */
    @FXML private TextField enroll_lname;
    /**
     *textfield object instance to store date of birth of student
     */
    @FXML private DatePicker enroll_dob;
    /**
     * menubar object instance to select a menuitem from menulist
     */
    @FXML private MenuBar menuBar;
    /**
     * menu object instance which will have menuitems
     */
    @FXML private Menu rosterMenu;
    /**
     * menuitem object instance byprofile
     */
    @FXML private MenuItem byProfile;
    /**
     * menuitem object instance bySchoolMajor
     */
    @FXML private MenuItem bySchoolMajor;
    /**
     * menuitem object instance byStanding
     */
    @FXML private MenuItem byStanding;
    /**
     * menu object instance which will have menuitems
     */
    @FXML private Menu school;
    /**
     * menuitem object instance school RBS
     */
    @FXML private MenuItem RBS;
    /**
     *menuitem object instance school SAS
     */
    @FXML private MenuItem SAS;
    /**
     * menuitem object instance school SC&I
     */
    @FXML private MenuItem SCandI;
    /**
     *menuitem object instance school SOE
     */
    @FXML private MenuItem SOE;
    /**
     * menu object instance which will have menuitems
     */
    @FXML private Menu enrollmentMenu;
    /**
     *menuitem object instance enrollStudents
     */
    @FXML private MenuItem byEnrollStudents;
    /**
     * menuitem object instance tuitionDue
     */
    @FXML private MenuItem byTuitionDue;
    /**
     * menuitem object instance semester end
     */
    @FXML private MenuItem bySemEnd;
    /**
     * radio button object instance for state CT
     */
    @FXML private RadioButton ct;
    /**
     * textfield object instance to store first Name of student
     */
    @FXML private TextField schp_fname;
    /**
     * textfield object instance to store last Name of student
     */
    @FXML private TextField schp_lname;
    /**
     * textfield object instance to store scholarship amount of student
     */
    @FXML private TextField schp_Amount;
    /**
     * textfield object instance to store first Name of student
     */
    @FXML private TextField roster_firstName;
    /**
     * textfield object instance to store date of birth of student
     */
    @FXML private DatePicker schp_dob;
    /**
     * button object instance fromFile
     */
    @FXML private Button fromFile;
    /**
     * button object instance to update schp amount
     */
    @FXML private Button updateSchpAmount;
    /**
     *radio button object instance for international
     */
    @FXML private RadioButton intl;
    /**
     * textfield object instance to store last Name of student
     */
    @FXML private TextField roster_lastName;
    /**
     * textArea object instance to output the message to gui
     */
    @FXML private TextArea messageArea;
    /**
     * toggle group object instance to switch between user selection student type
     */
    @FXML private ToggleGroup nonRes;
    /**
     * checkbox object instance to check and uncheck
     */
    @FXML private CheckBox nonResident;
    /**
     *radio button object instance for state NY
     */
    @FXML private RadioButton ny;
    /**
     * button object instance to remove
     */
    @FXML private Button remove;
    /**
     * datePicker object instance to select date
     */
    @FXML private DatePicker roster_dob;
    /**
     * toggle group object instance to switch between user selection state
     */
    @FXML private ToggleGroup state;
    /**
     *checkbox object instance to check and uncheck
     */
    @FXML private CheckBox studyAbroad;
    /**
     * radio button object instance for triState
     */
    @FXML private RadioButton triState;
    /**
     * errorMessage String object instance to print error messages in red color
     */
    private final String errorMessage = "-fx-text-fill: RED;";
    /**
     * successMessage String object instance to print success messages in green color
     */
    private final String successMessage = "-fx-text-fill: GREEN;";
    /**
     * checkdetails if the student is old enough or
     * not to be enrolled in college and
     * if they have any college credits
     * @param student object
     * @param date dob
     * @return true or false based on above condition
     */
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
    /**
     * this method checks the details of student adds to the roster
     * @param s object which is going to be added
     * @param fname student's first name
     * @param lname student's last name
     * @param dob   student's date of birth
     * @param state for tristate students
     * @param isFromFile if from file is true then no print messages
     */
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
    /**
     * method helps to process international student data
     * and also checks the details of student and adds to the roster
     */
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
    /**
     * method helps to process tri state student data
     * and also checks the details of student and adds to the roster
     */
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
    /**
     * method helps to process Resident student data
     * and also checks the details of student and adds to the roster
     */
    private void processAndInsert_RES_STUDENT(){
        try {
            messageArea.setStyle(errorMessage);
            if(!getMissingDataStatusForRoster()){
                if(!roster.checkAvailableMajor(major)){
                    messageArea.setText("Major code invalid: " + major);
                    return;
                }
                currentTabData();
                Student s = new Resident(fname, lname,new Date(dob),Major.valueOf(major), creditsCompleted);
                checkDetailsAndAdd(s, fname, lname,dob,null,false);
            }
            else{
                messageArea.setText("Missing data.");
            }
        }catch(NumberFormatException e) {
            messageArea.setText("Credits completed invalid: not an integer!");
        }catch (NullPointerException e1){
            messageArea.setText("Something went wrong");
            e1.printStackTrace();
        }
    }
    /**
     * method helps to process Non-Resident student data
     * and also checks the details of student and adds to the roster
     */
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
     * method to process and add students from given file
     */
    private void processAndInsert_FROM_FILE(){
        try{
            /*Stage stage = new Stage();
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File selectedFile = fileChooser.showOpenDialog(stage);
            if(selectedFile != null) {
                if (!selectedFile.getName().endsWith(".txt")) {
                    throw new IllegalArgumentException("Invalid file format. Only .txt files are allowed.");
                }
            }
            Scanner scnr = new Scanner(selectedFile);*/
            Scanner scnr = new Scanner(new File("studentList.txt"));
            while (scnr.hasNext()) {
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
                addStudentType_FROMFILE_HELPER(dataArr, studentType, fname, lname, dob, major);
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
    /**
     * updatemajor is a method that updates the major of a student in the roster
     * it takes string user input and splits it into an array of strings
     * if the length of the array is 4 and the roster is not empty then the code will do
     * first checks to see if the major code is valid or not if it's not
     * then it will generate an error message
     * then it will look up the students in the roster and update their major
     * then if the student is found in the roster list that person major
     * will be changed once it has been successfully changed a success message will be printed out
     * at the same time if that student wasn't found in the roster then an error message will be printed
     */
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
    /**
     * This method process the input and then remove student from the roster
     * @throws NullPointerException if any of the date is null
     * @throws IllegalArgumentException if the given data does not match the expected format (fname lname dob)
     */
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
            messageArea.setStyle(errorMessage);
            if(!getMissingDataStatusForSchp()) {
                currentTabData();
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
            messageArea.setText(enrollment.print());
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

    /**
     * this method checks if any input field is missing or not in roster tab
     * @return True if any field missing
     */
    public boolean getMissingDataStatusForRoster(){
        return roster_firstName.getText().isBlank() || roster_lastName.getText().isBlank()
                || roster_dob.getValue() == null || roster_creditsCompleted.getText().isBlank();
    }

    /**
     * this method checks if any input field is missing or not in enroll/drop tab
     * @return True if any field missing
     */
    public boolean getMissingDataStatusForEnroll(){
        return enroll_fname.getText().isBlank() || enroll_lname.getText().isBlank()
                || enroll_dob.getValue() == null || enroll_Credits.getText().isBlank();
    }

    /**
     * this method checks if any input field is missing or not in scholarship tab
     * @return True if any field missing
     */
    public boolean getMissingDataStatusForSchp(){
        return schp_fname.getText().isBlank() || schp_lname.getText().isBlank()
                || schp_dob.getValue() == null || schp_Amount.getText().isBlank();
    }

    /**
     * this method store field values to appropriate instances based
     * on current selected tab
     */
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

    /**
     * this method handles tristate radio button and sets its sub radio button to be active
     * @param event tristate radio button event
     */
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

    /**
     * this method fires tristate button to get preselected when any ny or ct button is clicked
     * @param event NY and CT button event
     */
    @FXML
    protected void handleNYandCTbtn(ActionEvent event){
        triState.fire();
    }

    /**
     * this method fires intl button to get preselected when studyAbroad button is clicked
     * @param event studyAbroad checkbox event
     */
    @FXML
    protected void handleStudyAbroadCheckBox(ActionEvent event){
        intl.fire();
    }

    /**
     *this method handles intl radio button and sets its sub radio button to be active
     * @param event intl radio button event
     */
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

    /**
     * this method sets sub buttons of nonresident to be active when its clicked
     * @param event non-resident check box event
     */
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

    /**
     * this method sets major value based on user selection
     * @param event combobox item selection event
     */
    @FXML
    protected void getMajor(ActionEvent event){
        major = comboBox.getSelectionModel().getSelectedItem().toUpperCase();
    }

    /**
     * this method loads the given txt file to be processed
     * @param event loadfrom file button event
     */
    @FXML
    protected void LoadFromFile(ActionEvent event) {
        processAndInsert_FROM_FILE();
    }
    /**
     * this method adds student type based on user selection
     * @param event add button event
     */
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

    /**
     * this method enrolls student based on field values and enrolls if student is in roster
     * @param event enroll button event
     */
    @FXML
    protected void enrollStudent(ActionEvent event){
        processAndEnrollStudent();
    }

    /**
     * this method drops student based on field values and if that student is currently enrolled
     * @param event drop button event
     */
    @FXML
    protected void dropStudent(ActionEvent event){
        checkAndDropStudent();
    }

    /**
     * this method removes student if student is in roster
     * @param event remove button event
     */
    @FXML
    protected void removeStudent(ActionEvent event) {
        processAndRemove();
    }

    /**
     * this method updates major if student is in roster and based on user major selection
     * @param event updateMajor button event
     */
    @FXML
    protected void updateMajor(ActionEvent event) {
        updateMajor();
    }
    /**
     * this method updates scholarship amount to total tuition amount if student is enrolled
     * and is a resident student
     * @param event update scholarship amount button event
     */
    @FXML
    protected void UpdateSchpAmount(ActionEvent event){
        ApplyScholarship();
    }
    /**
     * this method handles roster menuitems based on user selection and prints message appropriately
     * @param event roster menu items event
     */
    @FXML
    protected void handleRosterMenuItems(ActionEvent event){
        messageArea.setStyle(successMessage);
        messageArea.clear();
        byProfile.setOnAction(event1 -> messageArea.setText(roster.print()));
        bySchoolMajor.setOnAction(event12 -> messageArea.setText(roster.printBySchoolMajor()));
        byStanding.setOnAction(event13 -> messageArea.setText(roster.printByStanding()));
    }
    /**
     * this method handles school menuitems based on user selection and prints message appropriately
     * @param event school menu items event
     */
    @FXML
    protected void handleSchoolMenuItems(ActionEvent event){
        messageArea.setStyle(successMessage);
        messageArea.clear();
        RBS.setOnAction(event1 -> messageArea.setText(roster.printBySchool("RBS")));
        SAS.setOnAction(event12 -> messageArea.setText(roster.printBySchool("SAS")));
        SCandI.setOnAction(event13 -> messageArea.setText(roster.printBySchool("SC&I")));
        SOE.setOnAction(event14 -> messageArea.setText(roster.printBySchool("SOE")));
    }
    /**
     * this method handles enrollment menuitems based on user selection and prints message appropriately
     * @param event enrollment menu items event
     */
    @FXML
    protected void handleEnrollmentMenuItems(ActionEvent event){
        messageArea.setStyle(successMessage);
        messageArea.clear();
        byEnrollStudents.setOnAction(event1 -> enrollmentList());
        byTuitionDue.setOnAction(event12 -> printTuitionDue());
        bySemEnd.setOnAction(event13 -> updateCreditsCompletedAndPrint());
    }
    /**
     * this method initializes necessary values before user interacts with gui
     * this method will be performed when the controller is first invoked
     * @param url given url
     * @param resourceBundle given resourceBundle
     */
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