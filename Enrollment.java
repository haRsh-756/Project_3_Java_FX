package com.example.project3fx;

/**
 * Enrollent class to enroll students
 * @author harsh_patel
 */
public class Enrollment {

    private EnrollStudent[] enrollStudents;
    private int size;

    /**
     * default constructor
     * create 4 enrollStudent objects
     */
    public Enrollment(){
        final int CAPACITY = 4;
        this.enrollStudents = new EnrollStudent[CAPACITY];
        this.size = 0;
    }
    /**
     * this method expands enroll student array by 4 elements
     */
    private void grow(){
        final int CAPACITY = 4;
        EnrollStudent [] nenrollStudents = new EnrollStudent[enrollStudents.length + CAPACITY];
        for(int i = 0; i < enrollStudents.length; i++){
            nenrollStudents[i] = enrollStudents[i];
        }
        enrollStudents = nenrollStudents;
    }
    /**
     * finds enroll student in the enrollment list
     * @param enrollStudent
     * @return -1, on success index of found student
     */
    private int find(EnrollStudent enrollStudent){
        final int NOT_FOUND = -1;
        for(int i =0; i < size; i++){
            if(enrollStudents[i]  != null && enrollStudents[i].equals(enrollStudent)){
                return i;
            }
        }
        return NOT_FOUND;
    }
    /**
     * checks and add enrollStudent object if not in the enrollment list
     * also updates the recently enrolled credit
     * @param enrollStudent
     */
    public void add(EnrollStudent enrollStudent){
        if(size == enrollStudents.length){
            grow();
        }
        if(!contains(enrollStudent)){
            enrollStudents[size++] = enrollStudent;
        }
        else{
            int index = find(enrollStudent);
            if(index >= 0) {    //updating credits enrolled recently
                enrollStudents[index].setCreditsEnrolled(enrollStudent.getCreditsEnrolled());
            }
        }
    } //add to the end of array
    /**
     * check if enrollment list is empty or not
     * @return true or false
     */
    public boolean isEmpty(){
        return this.size == 0;
    }

    /**
     * this method removes/drops enroll student
     * and maintains order after remove
     * @param enrollStudent
     */
    public void remove(EnrollStudent enrollStudent){
        final int NOT_FOUND = -1;
        int index = find(enrollStudent);
        if (index >= 0) {
            enrollStudents[index] = enrollStudents[size - 1];
            enrollStudents[size - 1] = null;
            size--;
        }
    }//move the last one in the array to replace the deleting index position
    /**
     * checks if student is in enrollment or not
     * @param enrollStudent
     * @return true or false
     */
    public boolean contains(EnrollStudent enrollStudent){
        final int NOT_FOUND = -1;
        return find(enrollStudent) != NOT_FOUND;
    }
    /**
     * helps to retrieve enrollment list
     * @return enrollment list
     */
    public EnrollStudent[] getEnrollStudents() {
        return enrollStudents;
    }
    /**
     * this is method checks if student is in enrollment or not
     * @param p profile of enrolled student
     * @return on success enroll student object, else null
     */
    public EnrollStudent lookupEnrollStudent(Profile p){
        for(int i =0; i < size; i++){
            if(enrollStudents[i].getProfile().equals(p)){
                return enrollStudents[i];
            }
        }
        return null;
    }
    /**
     * enrollment list size
     * @return enrollment list size
     */
    public int getSize() {
        return size;
    }
    /**
     * prints enroll students in the enrollment list
     */
    public void print() {
        for(int i = 0; i < size; i++){
            if(enrollStudents[i] != null) {
                System.out.println(enrollStudents[i].print());
            }
        }
    } //print the array as is without sorting
}
