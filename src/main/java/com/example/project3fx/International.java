package com.example.project3fx;
/**
 * International class: subclass of Nonresident
 * helps to create non-resident International object based on student details
 * @author harsh_patel and giancarlo_Andretta
 */
public class International extends NonResident{
    /**
     * isStudyAbroad instance
     */
    private boolean isStudyAbroad;

    /**
     * default constructor
     * sets isStudyAbroad to false
     */
    public International(){
        this.isStudyAbroad = false;
    }
    /**
     * this constructor helps to International student object with the help of
     * student's profile, major, creditscompleted and is StudyAbroad or not
     * @param fname student's first name
     * @param lname student's last name
     * @param date student's date of birth
     * @param m student's major
     * @param credits student's completed credit
     * @param isStudyAbroad student's studyAbroad status
     */
    public International(String fname, String lname, Date date, Major m, int credits, boolean isStudyAbroad) {
        super(fname,lname,date,m,credits);
        this.isStudyAbroad = isStudyAbroad;
    }
    /**
     * sets studyAbroad or not
     * @param studyAbroad true or false
     */
    public void setStudyAbroad(boolean studyAbroad) {
        isStudyAbroad = studyAbroad;
    }
    /**
     * study abroad status
     * @return isStudyAbraod
     */
    public boolean isStudyAbroad() {
        return isStudyAbroad;
    }
    /**
     * abstract method from student helps to calculate student's tuition
     * based on creditsEnrolled and as per university's decided rate for international student
     * @param creditsEnrolled student's creditenrolled
     * @return tuition due for international student
     */
    @Override
    public double tuitionDue(int creditsEnrolled) {
        final int UNIVERSITY_FEE = 3268;
        final int HEALTH_INSURANCE_FEE = 2650;
        if(isStudyAbroad() && isValid(creditsEnrolled)){
            return UNIVERSITY_FEE + HEALTH_INSURANCE_FEE;
        }
        else {
            return super.tuitionDue(creditsEnrolled) + HEALTH_INSURANCE_FEE;
        }
    }
    /**
     * this method helps to check if credits enrolled is valid or not
     * based on international student criteria
     * @param creditEnrolled student's creditenrolled
     * @return true or false based on above condition
     */
    @Override
    public boolean isValid(int creditEnrolled) {
        final int MIN_CREDITS_STUDY_ABROAD = 3;
        final int MAX_CREDITS_STUDY_ABROAD = 12;
        final int MIN_CREDITS = 12;
        final int MAX_CREDITS = 24;
        if(isStudyAbroad()){
            return creditEnrolled >= MIN_CREDITS_STUDY_ABROAD && creditEnrolled <= MAX_CREDITS_STUDY_ABROAD;
        }
        else{
            return creditEnrolled >= MIN_CREDITS && creditEnrolled <= MAX_CREDITS;
        }
    }
    /**
     * Non-resident status
     * @return false since this class is subclass of Non-resident
     */
    @Override
    public boolean isResident() {
        return super.isResident();
    }
    /**
     * string representation of international student type
     * @return string representation of international student type
     */
    @Override
    public String toString() {
        if(isStudyAbroad()){
            return super.toString() + " (international:study abroad)";
        }
        else {
            return super.toString() + " (international)";
        }
    }
    /**
     * string representation of international student type
     * @return string representation of international student type
     */
    public String print(){
        if(isStudyAbroad()){
            return " (International studentstudy abroad) ";
        }
        else {
            return " (International student) ";
        }
    }
}
