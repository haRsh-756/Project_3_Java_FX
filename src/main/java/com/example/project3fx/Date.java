package com.example.project3fx;
import java.util.Calendar;
/**
 * This is the Date class to create and check date.
 * Here we are simply getting int type month date and year.
 * Then we are formating the date to be read as mm/dd/yyyy
 * Then we are assigning year to year date to date and month to month this is where we are creating an object with today’s date (see Calendar class)
 * Then we take “mm/dd/yyyy” and create a Date object
 * Then we check to see if the date are valid dates that are in the calendar or not for example 40 isn't a date in the Calendar but 20 is an date in the Calendar
 * Then we use the get method to get year month and date
 * Then we print the date in the format mmm/dd/yyyy
 * Then we make sure if the date, months and years are equal
 * Then in the last method we compare the months n years to see if the both month and year are equal if so then we return today's date minus the given date or we return this month minus given month
 * If the year isn't equal at all then we return this year minus given year and return that
 * @author harsh_patel and giancarlo_Andretta
 */
public class Date implements Comparable<Date>{
    /**
     *  We are also taking in account QUADRENNIAL which is the leap years that happens every 4 years in the calendar
     *  We are also taking in account of CENTENNIAL which is the 100 year old anniversary standpoint
     *  We are also taking in account of QUATERCENTENNIAL which is the 400 year old anniversary standpoint
     *  We are also implementing the 12 months in a year from january to December
     *  We are also implementing the number of max days in each month such as 1 to 28 days or 1 to 29 days or 1 - 30 days or 1 - 31 days
     *  We are also using the calendar class here
     *  we are also creating a variable year, month and day
     *  Creates a Calendar instance and assigns it to the calendar variable.
     *  @return A Calendar object representing the current date and time.
     */
    /**
     * year
     */
    private int year;
    /**
     * month
     */
    private int month;
    /**
     * day
     */
    private int day;
    /**
     * QUADRENNIAL
     */
    public static final int QUADRENNIAL = 4;
    /**
     * CENTENNIAL
     */
    public static final int CENTENNIAL = 100;
    /**
     * QUATERCENTENNIAL
     */
    public static final int QUATERCENTENNIAL = 400;
    /**
     * JANUARY
     */
    public static final int JANUARY = 1;
    /**
     * FEBRUARY
     */
    public static final int FEBRUARY = 2;
    /**
     * MARCH
     */
    public static final int MARCH = 3;
    /**
     * APRIL
     */
    public static final int APRIL = 4;
    /**
     * MAY
     */
    public static final int MAY = 5;
    /**
     * JUNE
     */
    public static final int JUNE = 6;
    /**
     * JULY
     */
    public static final int JULY = 7;
    /**
     * AUGUST
     */
    public static final int AUGUST = 8;
    /**
     * SEPTEMBER
     */
    public static final int SEPTEMBER = 9;
    /**
     * OCTOBER
     */
    public static final int OCTOBER = 10;
    /**
     * NOVEMBER
     */
    public static final int NOVEMBER = 11;
    /**
     * DECEMBER
     */
    public static final int DECEMBER = 12;
    /**
     * ZERO
     */
    public static final int ZERO = 0;
    /**
     * ONE
     */
    public static final int ONE = 1;
    /**
     * 28
     */
    public static final int TWENTYEIGHT = 28;
    /**
     * 29
     */
    public static final int TWENTYNINE = 29;
    /**
     * 30
     */
    public static final int THIRTY = 30;
    /**
     * 31
     */
    public static final int THIRTYONE = 31;
    /**
     * calendar object instance
     */
    Calendar calendar = Calendar.getInstance();
    /**
     * Constructs a Date object with the today's date as an value
     * The year, day and month data are of the date object which are
     * set to the current day, month and year and are returned by the calendar instance
     */
    public Date() { //create an object with today’s date (see Calendar class)
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH)+ ONE;
        this.day = calendar.get(Calendar.DATE);
    }
    /**
     * Constructs a Date object with the date values provided as an string variable
     * The date string variable will be in the formate of "mm/dd/yyy"
     * @param date A string is an depiction ia a date in the format of "mm/dd/yyyy"
     */
    public Date(String date) {
        if (date != null){
            String[] dateArr = date.split("/");
            try {
                this.month = Integer.parseInt(dateArr[0]);
                this.day = Integer.parseInt(dateArr[1]);
                this.year = Integer.parseInt(dateArr[2]);
            }catch (NumberFormatException e){
                System.out.println("Invalid date format");
                System.exit(ONE);
            }
        }
    } //take “mm/dd/yyyy” and create a Date object
    /**
     * This method looks to see if the date stored in the date object is valid or not
     *Then it will check if the day falls within the range of dates such as 1-30 or 1-28 or 1-31 or 1-29 depending on the month
     * Then depending on the month we can make sure the leap year is accounted for
     * @return True if the date in the Date object is valid if it's not then it's false
     */
    public boolean isValid() {
        boolean leapYear = false;
        if (this.year <= ZERO) {
            return false;
        }
        switch (this.month) {
            case JANUARY, MARCH, MAY, JULY, AUGUST, OCTOBER, DECEMBER -> {
                return this.day >= ONE && this.day <= THIRTYONE;
            }
            case APRIL, JUNE, SEPTEMBER, NOVEMBER -> {
                return this.day >= ONE && this.day <= THIRTY;
            }
            case FEBRUARY -> {
                if (this.year % QUADRENNIAL == ZERO) {
                    if (this.year % CENTENNIAL == ZERO) {
                        leapYear = this.year % QUATERCENTENNIAL == ZERO;
                    } else {
                        leapYear = true;
                    }
                }
                if (leapYear) {
                    return this.day >= ONE && this.day <= TWENTYNINE;
                } else {
                    return this.day >= ONE && this.day <= TWENTYEIGHT;
                }
            }
        }
        return false;
    }
    /**
     * Get the year of the date stored in the Date object
     * @return The year of the date stored in the Date obejct.
     */
    public int getYear() {
        return year;
    }
    /**
     * Get the month of the date stored in the Date object
     * @return the month of the date stored in the date object.
     * */
    public int getMonth() {
        return month;
    }
    /**
     * Get the date of the date stored in the date object
     * @return the day of the date stored in the date obejct.
     */
    public int getDay() {
        return day;
    }
    /**
     * Return string data type for the date stored in the date obejct
     * The date format is MM/DD/YYYY
     * @return a string representation of the date stored in the date object
     */
    public String printDate(){
        return month + "/" + day +"/"+year;
    }
    /**
     * This method returns a string representation of the date in the format "month/day/year"
     * @return string representation of the date
     */
    @Override
    public String toString() {
        return  month + "/"+day+"/"+ year;
    }
    /**
     * This method checks if the given object is equal to the current date object
     * The equal check is performed by comparing the year month and day of both dates.
     * @param obj the object to be compared with the current date object.
     * @return Boolean value indicating whether the two objects are equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Date ) {
            Date d = (Date) obj;
            return this.year == d.year &&
                    this.month == d.month && this.day == d.day;
        }
        return false;
    }
    /**
     * This is an compareTo method for the Date class
     * This compares current date with another date then returns an int value
     * First it compares the year then the month then both the dates
     * @param o the object to be compared to the current date
     * @return an int value which indicates the relative order of the two dates
     */
    @Override
    public int compareTo(Date o) {
        if(this.year == o.year) {
            if (this.month == o.month) {
                return this.day - o.day;
            } else {
                return this.month - o.month;
            }
        } else {
            return this.year - o.year;
        }
    }

    /**
     * test result method print the date, expected, and actual result
     * @param d date object
     * @param expOut expected out true or false
     * @param actualOut actual out true or false
     */
    public static void testResult(Date d, boolean expOut, boolean actualOut){
        System.out.println("   " + d.toString());
        System.out.println("   Expected: "+ expOut);
        System.out.println("   Actual: " + actualOut);
    }
    /**
     * testbed main
     * to test isValid with 8 different test cases
     * @param args cmd args
     */
    public static void main(String[] args) {
        System.out.println("Test cases for date class with isValid() method");
        Date date = new Date("2/29/2011");
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        System.out.println("**Test case #1: a date in a non-leap year has only 28 days");
        testResult(date,expectedOutput,actualOutput);

        date = new Date("2/29/2016");
        expectedOutput = true;
        actualOutput = date.isValid();
        System.out.println("**Test case #2: a date in a leap year has 29 days");
        testResult(date,expectedOutput,actualOutput);

        date = new Date("13/22/2019");
        expectedOutput = false;
        actualOutput = date.isValid();
        System.out.println("**Test case #3: range of month must be 1-12");
        testResult(date,expectedOutput,actualOutput);

        date = new Date("4/31/2005");
        expectedOutput = false;
        actualOutput = date.isValid();
        System.out.println("**Test case #4: valid day range for months like april,june,sep,nov: 1-30");
        testResult(date,expectedOutput,actualOutput);

        date = new Date("5/31/2004");
        expectedOutput = true;
        actualOutput = date.isValid();
        System.out.println("**Test case #5: valid day range for months other than april,june,sep,nov: 1-31");
        testResult(date,expectedOutput,actualOutput);

        date = new Date("-7/31/2009");
        expectedOutput = false;
        actualOutput = date.isValid();
        System.out.println("**Test case #6: with negative month");
        testResult(date,expectedOutput,actualOutput);

        date = new Date("4/30/-2011");
        expectedOutput = false;
        actualOutput = date.isValid();
        System.out.println("**Test case #7: with negative year");
        testResult(date,expectedOutput,actualOutput);

        date = new Date("6/-12/2012");
        expectedOutput = false;
        actualOutput = date.isValid();
        System.out.println("**Test case #8: with negative day");
        testResult(date,expectedOutput,actualOutput);
        System.out.println("--end of date class test cases");
    }
}

