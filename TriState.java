package com.example.project3fx;

/**
 * Tristate class: subclass of Non-resident
 * helps to create non-resident tri-state object based on student details
 * @author harsh_patel
 */
public class TriState extends NonResident{
    /**
     * tri-state student's state instance
     */
    private String state;

    /**
     * this constructor helps to create tri-state student object with the help of
     * student's profile, major, creditscompleted and eligible state
     * @param fname student's first name
     * @param lname student's last name
     * @param date student's date of birth
     * @param m student's major
     * @param creditsCompleted student's credit completed
     * @param state tri state student state
     */
    public TriState(String fname, String lname, Date date, Major m, int creditsCompleted, String state){
        super(fname, lname, date, m, creditsCompleted);
        this.state = state;
    }

    /**
     * sets the state
     * @param state student's state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * tri-state student state
     * @return state
     */
    public String getState() {
        return state;
    }
    /**
     * abstract method from student helps to calculate student's tuition
     * based on creditsEnrolled and as per university's decided rate for non-resident tri-state student
     * @param creditsEnrolled student's credit enrolled
     * @return tuition due for tri-state student
     */
    @Override
    public double tuitionDue(int creditsEnrolled) {
        final int NY_DISCOUNT = 4000;
        final int CT_DISCOUNT = 5000;
        final int FULL_TIME_STATUS = 12;
        final int MAX_CREDITS = 24;

        if(this.state.equalsIgnoreCase("ny") && creditsEnrolled >= FULL_TIME_STATUS) {  //ny and full time
            return super.tuitionDue(creditsEnrolled) - NY_DISCOUNT;
        }
        else if(this.state.equalsIgnoreCase("ct") && creditsEnrolled >= FULL_TIME_STATUS){ //ct and full time
            return super.tuitionDue(creditsEnrolled) - CT_DISCOUNT;
        }
        else{
            return super.tuitionDue(creditsEnrolled);
        }
    }

    /**
     * this method helps to check if credits enrolled is valid or not
     * based on tristate student criteria
     * @param creditEnrolled student's creditenrolled
     * @return true or false based on above condition
     */
    @Override
    public boolean isValid(int creditEnrolled) {
        return super.isValid(creditEnrolled);
    }
    /**
     * non-resident status
     * @return false since this class is subclass of Non-resident
     */
    @Override
    public boolean isResident() {
        return super.isResident();
    }
    /**
     * string representation of non-resident tri-state student
     * @return string representation of non-resident tri-state student
     */
    @Override
    public String toString() {
        return super.toString() + " (triState:" + state.toUpperCase() + ")";
    }
    /**
     * string representation of non-resident tri-state student
     * @return string representation of non-resident tri-state student
     */
    public String printState(){
        return " (Tri-state " + this.state + ") ";
    }
}
