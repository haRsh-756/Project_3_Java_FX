package com.example.project3fx;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * International Test to test isStudyAbroad and tuitionDue
 * @author harsh_patel
 */
public class InternationalTest {
    /**
     * this method tests tuition due of
     * international student study abroad
     */
    @Test
    public void Study_Abroad_tuitionDue() {
        International intl = new International();
        intl.setStudyAbroad(true);
        assertEquals(5918.0,intl.tuitionDue(11),0);
    }
    /**
     * this method tests tuition due of
     * international student not study abroad
     */
    @Test
    public void Study_not_Abroad_tuitionDue() {
        International intl = new International();
        assertEquals(35655.0,intl.tuitionDue(12),0);
    }
}