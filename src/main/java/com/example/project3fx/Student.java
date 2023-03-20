package com.example.project3fx;

/**
 * This class has the students profile, major and number of credits completed
 * This class has the comparable interface
 * so that the students can be sorted and compared by last and
 * first name also their date of birth and by their major and credits completed.
 *  @author harsh_patel and giancarlo_Andretta
 */
public abstract class Student implements Comparable<Student>{
    /**
     * student profile object instance
     */
    private Profile profile;
    /**
     * student's major object instance
     */
    private Major major; //Major is an enum type
    /**
     * student's creditCompleted
     */
    private int creditCompleted;

    /**
     * default student constructor
     * which set profile and major to null
     * credits completed to 0;
     */
    public Student(){
        this.profile = null;
        this.major = null;
        this.creditCompleted =0;
    }
    /**
     *Constructor for the student class
     *@param lname the last name of the student
     *@param fname the first name of the student
     *@param dob the date of birth of the student
     *@param major the major of the student
     *@param creditCompleted the number of credits completed by the student
     * this code will create a new profile object using these parameters and assign it
     * to the profile and assigns major and credit completed to the
     * correct part in the student class
     */
    public Student(String fname, String lname, Date dob, Major major, int creditCompleted){
        this.profile = new Profile(fname,lname,dob);
        this.major = major;
        this.creditCompleted = creditCompleted;
    }

    /**
     * abstract method which will be used in subclasses
     * @param creditsEnrolled enrolled credits
     * @return tuition due based on student criteria
     */
    public abstract double tuitionDue(int creditsEnrolled);

    /**
     * method checks the creditsEnrolled is in range or not
     * @param creditEnrolled enrolled credits
     * @return true or false based on creditsEnrolled
     */
    public boolean isValid(int creditEnrolled){
        final int MIN_CREDITS = 3;
        final int MAX_CREDITS = 24;
        return creditEnrolled >= MIN_CREDITS && creditEnrolled <= MAX_CREDITS;
    }

    /**
     * abstract method which will be used by subclasses
     * @return true or false based on resident or not
     */
    public abstract boolean isResident(); //polymorphism;

    /**
     * This method checks to see if the date of birth stored in the profile is valid
     * @return true if the date of birth is valid else return false
     */
    public boolean checkDate(){
        return this.profile.getDob().isValid();
    }
    /**
     * Method to check if the student is at least 16 years old.
     * @return true if the student is at least 16 years old, false otherwise.
     */
    public boolean ageLimit(){
        Date d = new Date();
        return d.getYear() - this.profile.getDob().getYear() >= 16;
    }
    /**
     *  Setter method to set the number of credits completed by the student.
     * @param creditCompleted the number of credits completed by the student.
     */
    public void setCreditCompleted(int creditCompleted) {
        this.creditCompleted = creditCompleted;
    }
    /**
     *Getter method to retrieve the number of credits completed by the student.
     * @return the number of credits completed by the student.
     */
    public int getCreditCompleted() {
        return creditCompleted;
    }
    /**
     * Getter method to retrieve the student's profile information.
     * @return the student's profile information, including their name and date of birth.
     */
    public Profile getProfile() {
        return profile;
    }
    /**
     * Enumerated type to represent the academic standing of a student.
     */
    public enum Standing {
        Freshman, Sophomore, Junior, Senior
    }
    /**
     * Calculates and returns the student's academic standing
     * based on the number of credits they have completed.
     * @return the student's academic standing, represented
     * as a value from the Standing enumerated type.
     */
    public Standing getStanding(){
        Standing standing;
        if(this.creditCompleted < 30){
            standing = Standing.Freshman;
        }
        else if(this.creditCompleted < 60){
            standing = Standing.Sophomore;
        }
        else if(this.creditCompleted < 90){
            standing = Standing.Junior;
        }
        else {
            standing = Standing.Senior;
        }
        return standing;
    }
    /**
     * Sets the student's major to the specified value.
     * @param major the new major to set for the student.
     */
    public void setMajor(Major major) {
        this.major = major;
    }
    /**
     * Returns the student's major.
     * @return the student's current major.
     */
    public Major getMajor() {
        return major;
    }
    /**
     * Compares this student object to the specified object.
     * The result is `true` if and only if the argument is not `null` and is a `Student` object that has the same
     * profile, creditCompleted, and major as this object.
     * @param obj the object to compare this student against.
     * @return `true` if the given object represents a `Student` equivalent to this student, `false` otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Student) {
            Student student = (Student) obj;
            return this.profile.equals(student.profile) && this.creditCompleted == student.creditCompleted
                    && this.major == student.major;
        }
        return false;
    }
    /**
     * Returns a string representation of the student.
     * The string representation consists of the profile, major, credit completed and the standing of the student.
     * @return a string representation of the student.
     */
    @Override
    public String toString() {
        return this.profile.toString() + " " + major.toString() + " credits completed: " + this.creditCompleted + " (" + getStanding().name() + ")";
    }
    /**
     * Compares two students based on their profile, major, and credit completed.
     * The comparison first considers the profile, then the major, and finally the credit completed.
     * @param o the other student to compare to.
     * @return a negative integer, zero, or a positive integer
     * as this student is less than, equal to, or greater than the specified student.
     */
    @Override
    public int compareTo(Student o) {
        int p = this.profile.compareTo(o.profile);
        int m = this.major.compareTo(o.major);
        int c = Integer.compare(this.creditCompleted,o.creditCompleted);
        if(p != 0){
            return p;
        }
        else if(m != 0){
            return m;
        }
        else{
            return c;
        }
    }
    /**
     * printing based on expected and actual comparison value
     * @param expected
     * @param actual
     */
    /*public static void testResult(int expected, int actual){
        System.out.println("expected:"+expected);
        System.out.println("actual:" +actual);
        if(expected > 0 && actual > 0){
            System.out.println("   s1 > s2");
        }else if(expected < 0  && actual < 0){
            System.out.println("   s1 < s2");
        }
        else{
            System.out.println("   s1 == s2");
        }
    }*/

    /**
     * testbed main for student class using compareTo method
     * @param args
     */
    /*public static void main(String[] args) {
        System.out.println("Test cases for students using compareTo method");
        Student s1 = new Student("morris","John",new Date("2/22/2002"),Major.EE,32);
        Student s2 = new Student("doe","John",new Date("7/6/2002"),Major.CS,33);
        Student s3 = new Student("Brooks","Joy",new Date("8/8/1999"),Major.BAIT,77);
        Student s4 = new Student("Brooks","Lindsey",new Date(),Major.CS,44);
        Student s5 = new Student("Kate","Lindsey",new Date("7/16/2002"),Major.ITI,55);
        Student s6 = new Student("Kate", "Lindsey",new Date("7/16/2002"),Major.ITI,55);
        int expected = 9;
        int actual = s1.compareTo(s2);
        System.out.println("** Test case #1: comparing s1 with s2: should be s1 > s2 because first comparison made with lname");
        testResult(expected,actual);

        expected = -2;
        actual = s3.compareTo(s4);
        System.out.println("** Test case #2: comparing s3 with s4: should be  s3 < s4 because lname same " +
                "and second comparison made with fname");
        testResult(expected,actual);

        expected = 0;
        actual = s5.compareTo(s6);
        System.out.println("** Test case #3: comparing s5 with s6: should be  s5 == s6 because same objects");
        testResult(expected,actual);
        System.out.println("--end of student testbed main test cases");
    }*/
}
