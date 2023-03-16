package com.example.project3fx;

/**
 * Resident class: subclass of Student
 * helps to create resident object based on student details
 * scholarship is available for only resident students
 * @author harsh_patel
 */
public class Resident extends Student{
    /**
     * scholarship value instance
     */
    private int scholarship;

    /**
     * default constructor
     * set scholarship value to 0
     */
    public Resident(){
        this.scholarship = 0;
    }

    /**
     * this constructor helps to create tri-state student object with the help of
     * student's profile, major, creditscompleted and eligible state
     * @param fname student's first name
     * @param lname student's last name
     * @param date student's date of birth
     * @param m student's major
     * @param creditsCompleted student's credit completed
     */
    public Resident(String fname, String lname, Date date, Major m, int creditsCompleted){
        super(fname,lname,date,m,creditsCompleted);
        this.scholarship = 0;
    }

    /**
     * sets scholarship value
     * @param scholarship  scholarship value
     */
    public void setScholarship(int scholarship) {
        this.scholarship = scholarship;
    }

    /**
     * checks scholarship value
     * max allowed is 10,000
     * @return true or false based on above condition
     */
    public boolean checkScholarship(){
        final int MAX_SCHOLARSHIP = 10000;
        return this.scholarship > 0 && this.scholarship <= MAX_SCHOLARSHIP;
    }

    /**
     * checks if resident student is partTime or fulltime
     * based on credits enrolled
     * @param creditsEnrolled student's creditenrolled
     * @return true or false based on above condition
     */
    public boolean isPartTime(int creditsEnrolled){
        final int FULL_TIME = 12;
        final int MIN_CREDITS = 3;
        return creditsEnrolled >= MIN_CREDITS && creditsEnrolled < FULL_TIME;
    }

    /**
     * scholarship value
     * @return scholarship value
     */
    public int getScholarship() {
        return scholarship;
    }

    /**
     * this method helps to check if credits enrolled is valid or not
     * based on resident student criteria
     * @param creditEnrolled student's credit enrolled
     * @return true or false based on above condition
     */
    @Override
    public boolean isValid(int creditEnrolled) {
        return super.isValid(creditEnrolled);
    }
    /**
     * abstract method from student helps to calculate student's tuition
     * based on creditsEnrolled and as per university's decided rate for resident student
     * @param creditsEnrolled student's credit enrolled
     * @return tuition due for resident student
     */
    @Override
    public double tuitionDue(int creditsEnrolled) {
        final int RESIDENT_FEE = 12536;
        final int UNIVERSITY_FEE = 3268;
        final int CREDIT_PER_HOUR_FEE = 404;
        final int CREDIT_THRESHOLD = 16;
        final int MAX_CREDITS = 24;
        final int FULL_TIME_RATE = 80;
        if(creditsEnrolled > CREDIT_THRESHOLD && creditsEnrolled <= MAX_CREDITS){ // >16
            double tuition = RESIDENT_FEE + UNIVERSITY_FEE + CREDIT_PER_HOUR_FEE * (creditsEnrolled - CREDIT_THRESHOLD);
            if(checkScholarship()){
                return tuition - getScholarship();
            }
            else {
                return tuition;
            }
        }
        else if(isPartTime(creditsEnrolled)){ //part-time
            return (creditsEnrolled * CREDIT_PER_HOUR_FEE) + ((UNIVERSITY_FEE/100.0) * FULL_TIME_RATE);
        }
        else{   //12 - 16 CREDITS
            return RESIDENT_FEE + UNIVERSITY_FEE;
        }
    }
    /**
     * resident status
     * @return true since this is a resident class
     */
    @Override
    public boolean isResident() {
        return true;
    }
    /**
     * string representation of resident student
     * @return string representation of resident student
     */
    public String print(){
        return " (Resident) ";
    }
    /**
     * string representation of resident student
     * @return string representation of resident student
     */
    @Override
    public String toString() {
        return super.toString() + " (resident)";
    }
}

