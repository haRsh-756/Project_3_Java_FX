package com.example.project3fx;

/**
 * major and it's codes and schools that offers it
 * @author harsh_patel and giancarlo_Andretta
 */
public enum Major {
    /**
     * Computer Science department
     * with code "01:198" and school "SAS".
     */
    CS ("01:198","SAS"),
    /**
     * Mathematics department
     * with code "01:640" and school "SAS".
     */
    MATH("01:640","SAS"),
    /**
     * Electrical Engineering department
     * with code "14:332" and school "SOE".
     */
    EE("14:332","SOE"),
    /**
     * Information Technology and Informatics department
     * with code "04:547" and school "SC&I".
     */
    ITI("04:547","SC&I"),
    /**
     * Business and Information Technology department
     * with code "33:136" and school "RBS".
     */
    BAIT("33:136","RBS");
    /**
     * majorCode instance
     */
    private final String majorCode;
    /**
     * school instance
     */
    private final String school;

    /**
     * @param majorCode = the code for the major
     * @param School = the school that offers that major
     */
    Major(String majorCode, String School){
        this.majorCode = majorCode;
        this.school = School;
    }
    /**
     * the code of the major
     *@returns the code of the major
     */
    public String getMajorCode() {
        return majorCode;
    }
    /**
     * the school that offers that major
     *@returns the school that offers that major
     */
    public String getSchool() {
        return school;
    }
    /**
     * in the formate of "majorcode + majorname + schoolthatoffersthatmajor"
     *@returns a string representation of the major
     */
    @Override
    public String toString() {
        return "(" + getMajorCode() + " "+ this.name() + " " + getSchool() + ")";
    }
}
