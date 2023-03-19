package com.example.project3fx;
/**
 * NonResident class: subclass of Student
 * helps to create non-resident object based on student details
 * @author harsh_patel
 */
public class NonResident extends Student{

    /**
     * this constructor helps to create Non-resident student object with the help of
     * student's profile, major, creditscompleted
     * @param fname student's first name
     * @param lname student's last name
     * @param date student's date of birth
     * @param m student's major
     * @param creditsCompleted enrolled credits
     */
    public NonResident(String fname, String lname, Date date, Major m, int creditsCompleted){
        super(fname,lname,date,m,creditsCompleted);
    }

    /**
     * default constructor
     */
    public NonResident() {}

    /**
     * abstract method from student helps to calculate student's tuition
     * based on creditsEnrolled and as per university's decided rate for non-resident student
     * @param creditsEnrolled enrolled credits
     * @return tuition due for non-resident student
     */
    @Override
    public double tuitionDue(int creditsEnrolled) {
        final int NON_RESIDENT_FEE = 29737;
        final int UNIVERSITY_FEE = 3268;
        final int CREDIT_PER_HOUR_FEE = 966;
        final int CREDIT_THRESHOLD = 16;
        final int MAX_CREDITS = 24;
        final int FULL_TIME_STATUS = 12;
        final int MIN_CREDITS = 3;
        final int FULL_TIME_RATE = 80;
        if(creditsEnrolled > CREDIT_THRESHOLD && creditsEnrolled <= MAX_CREDITS) { //17-24
            return NON_RESIDENT_FEE + UNIVERSITY_FEE + CREDIT_PER_HOUR_FEE * (creditsEnrolled - CREDIT_THRESHOLD);
        }
        else if(creditsEnrolled >= MIN_CREDITS && creditsEnrolled < FULL_TIME_STATUS){ //3-11
            return (creditsEnrolled * CREDIT_PER_HOUR_FEE) + (UNIVERSITY_FEE/100.0) * FULL_TIME_RATE;
        }
        else {  //12-16
            return NON_RESIDENT_FEE + UNIVERSITY_FEE;
        }
    }
    /**
     * this method helps to check if credits enrolled is valid or not
     * based on non-resident student criteria
     * @param creditEnrolled enrolled credits
     * @return true or false based on above condition
     * */
    @Override
    public boolean isValid(int creditEnrolled) {
        return super.isValid(creditEnrolled);
    }
    /**
     * non-resident status
     * @return false since this is Non-resident student
     */
    @Override
    public boolean isResident() {
        return false;
    }
    /**
     * string representation of non-resident student
     * @return string representation of non-resident student
     */
    public String print(){
        return " (Non-Resident) ";
    }
    /**
     * string representation of non-resident student
     * @return string representation of non-resident student
     */
    @Override
    public String toString() {
        return super.toString() + " (non-resident)";
    }
}
