package com.example.project3fx;
/**
 * This is a profile class sets and create student's profile
 * This class has a persons first name , last name, and date of birth
 * The profile class implements the comparable interface to allow cimparisions between profiles
 * Variables:
 *  lname = the last name of the person
 *  fname = the first name of the person
 *  dob = the date of birth of the person
 *  @author samirroshan and harsh_patel
 */
public class Profile implements Comparable<Profile>{
    private String lname;
    private String fname;
    private Date dob; //use the Date class described in (f)

    /**
     * default constructor with 3 parameters
     * @param lname last name
     * @param fname first name
     * @param dob date of birth
     */
    public Profile(String fname, String lname, Date dob) {
        this.lname = lname;
        this.fname = fname;
        this.dob = dob;
    }
    /**
     * first name of that person
     * @return returns the first name of that person
     */
    public String getFname() {
        return fname;
    }

    /**
     * the date of birth object
     * @return returns the date of birth object
     */
    public Date getDob() {
        return dob;
    }

    /**
     * sets the date of birth
     * @param dob sets the date of birth of that person this will be using the date class
     */
    public void setDob(Date dob) {
        this.dob = dob;
    }

    /**
     * sets the first name
     * @param fname  sets the first name of that person
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * the last name of that person
     * @return returns the last name of that person
     */
    public String getLname() {
        return lname;
    }

    /**
     * sets the last name
     * @param lname  sets the last name of that person
     */
    public void setLname(String lname) {
        this.lname = lname;
    }

    /**
     * comparison between two profiles
     * @param obj object instance
     * @return Comparison of the current profile with another profile object to see if they are equal.
     */
    @Override
    public boolean equals(Object obj){
        if (obj instanceof Profile) {
            Profile profile = (Profile) obj;
            return this.lname.equalsIgnoreCase(profile.lname) &&
                    this.fname.equalsIgnoreCase(profile.fname) && this.dob.equals(profile.dob);
        }
        return false;
    }

    /**
     * a string representation of the profile in the format "fname lname dob"
     * @return a string representation of the profile in the format "fname lname dob".
     */
    @Override
    public String toString() {
        return fname + " " + lname + " "+ dob.toString();
    }
    /**
     * comparison between two students
     * @param o the object to be compared.
     * @return  The comparison is performed by first comparing the last names, then first names, and finally dates of birth.
     */
    @Override
    public int compareTo(Profile o) {
        int lnameDiff = this.lname.compareToIgnoreCase(o.lname);
        int fnameDiff = this.fname.compareToIgnoreCase(o.fname);
        int dobDiff = this.dob.compareTo(o.dob);
        if(lnameDiff != 0){
            return lnameDiff;
        }
        else if(fnameDiff != 0){
            return fnameDiff;
        }
        else{
            return dobDiff;
        }
    }
}

