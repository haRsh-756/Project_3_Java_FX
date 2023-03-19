package com.example.project3fx;

import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Roster Test class with JUNIT test
 * testing add and remove method for international and tri-state objects
 * @author harsh_patel
 */
public class RosterTest {
    /**
     * roster object instance
     */
    Roster roster = new Roster();
    /**
     * this method test international student object using add method and remove method
     */
    @Test
    public void Intl_student_add() {
        Student s = new International("Harry","Potter",
                new Date("6/3/2002"),Major.CS,44,true);
        assertTrue(roster.add(s));  //adding
        assertFalse(roster.add(s)); // again adding same object
        assertTrue(roster.remove(s)); //removing
        assertFalse(roster.remove(s)); // again removing
    }

    /**
     * this method test tri-state student object using add method and remove method
     */
    @Test
    public void Tristate_student_add() {
        Student s = new TriState("Harry","Potter",
                new Date("6/3/2002"),Major.CS,45,"NY");
        assertTrue(roster.add(s));  //adding
        assertFalse(roster.add(s)); // again adding same object
        assertTrue(roster.remove(s)); //removing
        assertFalse(roster.remove(s)); // again removing
    }
}