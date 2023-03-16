package com.example.project3fx;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Date test class to test 5 valid and 2 invalid date objects
 * @author harsh_patel
 */
public class DateTest {
    /**
     *non leap year date test
     */
    @Test
    public void isValid_date_non_leapYear() {
        Date d = new Date("2/29/2011");
        assertFalse(d.isValid());
    }

    /**
     * leap year test
     */
    @Test
    public void isValid_date_leapYear() {
        Date d = new Date("2/29/2016");
        assertTrue(d.isValid());
    }

    /**
     * 30 days April , july , sep , nov months test
     */
    @Test
    public void isValid_MONTH_AJSN_day30() {
        Date d = new Date("4/31/2005");
        assertFalse(d.isValid());
    }

    /**
     * 31 days test months other than AJSN
     */
    @Test
    public void isValid_MONTH_not_AJSN_day31() {
        Date d = new Date("5/31/2004");
        assertTrue(d.isValid());
    }

    /**
     * negative month test
     */
    @Test
    public void isValid_negative_MONTH() {
        Date d = new Date("-7/31/2009");
        assertFalse(d.isValid());
    }

    /**
     * negative year test
     */
    @Test
    public void isValid_negative_YEAR() {
        Date d = new Date("4/30/-2011");
        assertFalse(d.isValid());
    }
}