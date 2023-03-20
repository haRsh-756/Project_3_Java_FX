package com.example.project3fx;

/**
 * This class shows the roster of students where you can add, find, grow, size, lookup, check to see if it exists and remove students
 * @author harsh_patel and giancarlo_Andretta
 */
public class Roster {
    /**
     * student array instance
     */
    private Student[] roster;
    /**
     * size of the array
     */
    private int size;

    /**
     * default constructor
     * Creates a new roster object with an initial capacity of 4
     */
    public Roster() {
        final int CAPACITY = 4;
        this.roster = new Student[CAPACITY];
        this.size = 0;
    }

    /**
     * Searches the roster for the given student and
     * returns the index if found returns -1 if not found
     * @param student the student to search for in the roster
     * @return the index of the student in the roster or -1 if not found
     */
    private int find(Student student) {
        final int NOT_FOUND = -1;
        for (int i = 0; i < size; i++) {
            if (roster[i] != null  && roster[i].equals(student)) {
                return i;
            }
        }
        return NOT_FOUND;
    } //search the given student in roster

    /**
     * Increases the size/capacity of the roster array by 4
     * */
    private void grow() {
        final int CAPACITY = 4;
        Student[] nroster = new Student[roster.length + CAPACITY];
        for (int i = 0; i < size; i++) {
            nroster[i] = roster[i];
        }
        roster = nroster;
    } //increase the array capacity by 4

    /**
     * ADDS A STUDENT TO THE END OF THE ROSTER THEN RETURNS TRUE
     * IF ADDING PROCESS WAS DONE SUCCESSFULLY IF IT WASN'T IT WILL RETURN FALSE
     * @param student where the student is going to be added to the roster
     * @return true if the adding process was dont successfully if not it will return false
     */
    public boolean add(Student student){
        if(size == roster.length){
            grow();
        }
        if(!contains(student)){
            roster[size++] = student;
            return true;
        }
        return false;
    } //add student to end of array

    /**
     * the size of the roster or the length in this case since it is an array
     * @return the size of the roster or the length in this case since it is an array
     */
    public int getSize() {
        return size;
    }
    /**
     * true if the roster is empty else false
     * @return true if the roster is empty else false
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * Remove the given student from the roster then you have to
     * maintain the order of the roster even after removal of that student
     * return true if the removal was done if it wasn't done then return false
     * @param student object which will be removed from the roster
     * @return true if the removal process was done correctly if not then return false
     */
    public boolean remove(Student student){
        int index = find(student);
        if(index >=0){
            Student[] nroster = new Student[roster.length];
            int j =0;
            for(int i=0; i< roster.length; i++){
                if (i != index) {
                    nroster[j++] = roster[i];
                }
            }
            roster = nroster;
            size--;
            return true;
        }
        return false;
    }//maintain the order after remove

    /**
     *  contains method is used to check if a particular student object exists in the roster or not
     * @param student object which is going to be searched in the roster
     * @return A boolean value which is supposed to tell if the student exists in the roster or not returns true or false
     * */
    public boolean contains(Student student) {
        final int NOT_FOUND = -1;
        return find(student) != NOT_FOUND;
    } //if the student is in roster

    /**
     * Looks up and removes the student from the roster based on these variables
     * @param fname = the first name of the student
     * @param lname = the last name of the student
     * @param dob = the date of birth of the student in mm/dd/yyyy
     * @return return true if the student was found and removed if not return false
     */
    public boolean lookupAndRemove(String fname, String lname, String dob){
        for(int i=0; i<size; i++){
            if(roster[i] != null && roster[i].getProfile().getLname().equalsIgnoreCase(lname) &&
                    roster[i].getProfile().getFname().equalsIgnoreCase(fname) &&
                    roster[i].getProfile().getDob().printDate().equalsIgnoreCase(dob)){
                return remove(roster[i]);
            }
        }
        return false;
    }
    /**
     *lookup method overloading
     * This is used to look up student in the roster by their first name, last name, data of birth and major
     * @param fname = the first name of the student
     * @param lname = the last name of the student
     * @param dob = the date of birth of the student in mm/dd/yyyy
     * @param m = the major to be updated for the student
     * @return true if the student was found in the roster and their major was updated else return false
     */
    public boolean lookup(String fname, String lname, String dob, Major m){
        for(int i = 0; i < size; i++){
            if(roster[i] != null && roster[i].getProfile().getDob().printDate().equalsIgnoreCase(dob) &&
                    roster[i].getProfile().getFname().equalsIgnoreCase(fname) &&
                    roster[i].getProfile().getLname().equalsIgnoreCase(lname)){
                return changeMajor(i,m);
            }
        }
        return false;
    }

    /**
     * This checks to see if a particular major is available
     * @param major is a string representing the major to be checked
     * @return true if that particular major is available else return false
     * */
    public boolean checkAvailableMajor(String major){
        for(Major m: Major.values()){
            if(m.name().equalsIgnoreCase(major)){
                return true;
            }
        }
        return false;
    }

    /**
     * check if the given school name exists in the major enum class
     * @param school the name of the school to be checked
     * @return true if the school exists, false otherwise
     */
    public boolean checkAvailableSchool(String school){
        for(Major m: Major.values()){
            if(m.getSchool().equalsIgnoreCase(school)){
                return true;
            }
        }
        return false;
    }

    /**
     * Changes the major of a student in the roster
     * @param index the student whose major needs changing
     * @param newMajor the new major for that student
     * @return true if the majoe change was successful, else return false
     */
    private boolean changeMajor(int index, Major newMajor){
        if(index >= 0){
            roster[index].setMajor(newMajor);
            return true;
        }
        return false;
    }

    /**
     * lookup method checks student based on profile
     * returns the status if roster contains the requested student
     * @param fname student's first name
     * @param lname student's last name
     * @param dob student's date of birth
     * @return true or false
     */
    public boolean lookup(String fname, String lname, String dob){
        for(int i = 0; i < size; i++){
            if(roster[i] != null && roster[i].getProfile().getFname().equalsIgnoreCase(fname)
                    && roster[i].getProfile().getLname().equalsIgnoreCase(lname)
                    && roster[i].getProfile().getDob().printDate().equalsIgnoreCase(dob)){
                return true;
            }
        }
        return false;
    }

    /**
     * this methods retrieve student object found in the array
     * @param p profile of the student which needs to be retrieved
     * @return on success student object else null
     */
    public Student getStudent(Profile p){
        for(int i = 0; i < size; i++){
            if(roster[i]!= null && roster[i].getProfile().equals(p)){
                return roster[i];
            }
        }
        return null;
    }
    /**
     * prints the student in the roster who belong in a particular school
     * @param school the name of the school whose student should be printed
     */
    public String printBySchool(String school){
        String text = "";
        for(int i =0; i < size; i++){
            for(int j = i+1; j< size; j++){
                if(roster[i]!=null && roster[j]!=null) {
                    if(roster[i].getMajor().getSchool().equalsIgnoreCase(school)
                            && roster[j].getMajor().getSchool().equalsIgnoreCase(school)){
                        if(roster[i].getProfile().compareTo(roster[j].getProfile())>0){
                            Student s = roster[i];
                            roster[i] = roster[j];
                            roster[j] = s;
                        }
                    }
                }
            }
        }
        for(int i=0; i<size; i++){
            if(roster[i] != null && roster[i].getMajor().getSchool().equalsIgnoreCase(school)) {
                text += roster[i].toString() + "\n";
            }
        }
        return text;
    }
    /**
     * Prints the students information in the roster sorted by their last names
     * if two students have the same last name then they are stored by their first name
     * if they have the same first name then they are sorted by their date of birth
     */
    public String print() {
        String text = "";
        for(int i =0; i < size; i++){
            for(int j = i+1; j< size; j++){
                if(roster[i]!=null && roster[j]!=null) {
                    if (roster[i].getProfile().compareTo(roster[j].getProfile()) > 0) {
                        Student s = roster[i];
                        roster[i] = roster[j];
                        roster[j] = s;
                    }
                }
            }
        }

        for(int i=0; i<size; i++){
            if(roster[i] != null) {
                 text += roster[i].toString() + "\n";
            }
        }
        return text;
    }
    /**
     * This method prints the roster of student sorted by school major
     * The roster is sorted by school then the by the major within each school
     */
    public String printBySchoolMajor() {
        String text = "";
        for(int i=0; i<size; i++){
            for(int j=i+1; j<size; j++){
                if(roster[i]!=null && roster[j]!=null){
                    int res = roster[i].getMajor().getSchool().compareToIgnoreCase(roster[j].getMajor().getSchool());
                    if( res > 0) {
                        Student s = roster[i];
                        roster[i] = roster[j];
                        roster[j] = s;
                    }
                    else if(res ==0){
                        if(roster[i].getMajor().name().compareToIgnoreCase(roster[j].getMajor().name())>0){
                            Student s = roster[i];
                            roster[i] = roster[j];
                            roster[j] = s;
                        }
                    }
                }
            }
        }
        for(int i =0; i<size; i++){
            if(roster[i]!=null) {
                text += roster[i].toString() + "\n";
            }
        }
        return text;
    } //print roster sorted by school major

    /**
     * this method return student array
     * @return array of students
     */
    public Student[] getRoster() {
        return roster;
    }

    /**
     * This method sorted and prints the roster of student based on their standing
     * the standing is represented by enum
     * the roster is sorted in sorted by name from a to z by enum type
     * the sorted roster of students is then printed by System.out.print
     */
    public String printByStanding() {
        String text = "";
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (roster[i] != null && roster[j] != null) {
                    if (roster[i].getStanding().name().compareToIgnoreCase(roster[j].getStanding().name()) > 0) {
                        Student s = roster[i];
                        roster[i] = roster[j];
                        roster[j] = s;
                    }
                }
            }
        }
        for (int i = 0; i < size; i++) {
            if (roster[i] != null) {
                text += roster[i].toString() + "\n";
            }
        }
        return text;
    }
}

