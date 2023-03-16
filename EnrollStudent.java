package com.example.project3fx;
/**
 * EnrollStudent class to keep track of enroll students
 * @author harsh_patel
 */
public class EnrollStudent {
    /**
     * student's profile instance
     */
    private Profile profile;
    /**
     * enroll student's credit enrolled
     */
    private int creditsEnrolled;

    /**
     * default constructor
     * sets profile to null and credits enrolled to 0
     */
    public EnrollStudent(){
        this.profile = null;
        this.creditsEnrolled = 0;
    }

    /**
     * this constructor sets student's profile and credits enrolled
     * @param fname student's first name
     * @param lname student's last name
     * @param date student's date of birth
     * @param creditsEnrolled student's creditEnrolled
     */
    public EnrollStudent(String fname, String lname, Date date, int creditsEnrolled){
        this.profile = new Profile(fname,lname,date);
        this.creditsEnrolled = creditsEnrolled;
    }

    /**
     * enroll student profile
     * @return student profile
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * enrolled students credit
     * @return creditsEnrolled
     */
    public int getCreditsEnrolled() {
        return creditsEnrolled;
    }

    /**
     * set new credits enrolled
     * @param creditsEnrolled student's creditenrolled
     */
    public void setCreditsEnrolled(int creditsEnrolled) {
        this.creditsEnrolled = creditsEnrolled;
    }

    /**
     * compare student profiles
     * @param obj object instance
     * @return true or false based on comparison
     */
    @Override
    public boolean equals(Object obj) {
        EnrollStudent es = (EnrollStudent) obj;
        return this.profile.equals(es.profile);
    }
    /**
     * string representation of student profile and credits enrolled
     * @return string representation of student profile and credits enrolled
     */
    public String print(){
        return this.profile.toString() + ": credits enrolled: " + this.creditsEnrolled;
    }
    /**
     * string representation of credits enrolled
     * @return string representation of credits enrolled
     */
    public String printCreditEnrolled(){
        return "enrolled " + this.creditsEnrolled + " credits:";
    }
    /**
     * string representation of student profile and credits enrolled
     * @return string representation of student profile and credits enrolled
     */
    @Override
    public String toString() {
        return this.profile.toString() + " enrolled " + this.creditsEnrolled + " credits";
    }
}
