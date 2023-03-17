/**
 * Contains all classes for student object and extended student objects
 */
package com.example.cs213_tuition_project_javafx;

/**
 * Implementation of the subclass Resident that extends superclass Student
 * Contains override methods toString, getType, isResident, tuitionDue
 * @author Nikhil Agarwal, Hyeon Oh
 */
public class Resident extends Student {

    /**
     * Min credits needed to be full time student
     */
    public static final int FULL_TIME = 12;

    /**
     * Min credits to not be charged extra
     */
    public static final int EXTRA_FULL_TIME = 16;

    /**
     * Price per credit hour for Residents
     */
    public static final double RESIDENT_CREDIT_PRICE = 404;

    /**
     * University fee for students
     */
    public static final double UNIVERSITY_FEE = 3268;

    /**
     * Part-time University fee
     */
    public static final double PART_TIME_UNIVERSITY_FEE = UNIVERSITY_FEE*0.8;

    /**
     * Resident flat tuition fee
     */
    public static final double RESIDENT_TUITION = 12536;

    /**
     * int scholarship data for resident student.
     */
    private int scholarship;

    /**
     * Constructor of Resident class
     * @param profile profile of the student
     * @param major major of the student
     * @param creditCompleted creditsCompleted by the student
     * @param scholarship scholarship amount of the student
     */
    public Resident(Profile profile, Major major, int creditCompleted, int scholarship){
        super(profile,major,creditCompleted);
        this.scholarship = scholarship;
    }

    /**
     * Constructor of Resident Class without scholarship, overloading
     * @param profile profile of the student
     * @param major major of the student
     * @param creditCompleted creditsCompleted by the student
     */
    public Resident(Profile profile, Major major, int creditCompleted){
        super(profile,major,creditCompleted);
        this.scholarship = 0;
    }

    /**
     * Overrides toString
     * @return invokes the superclass toString method and adds resident
     */
    @Override
    public String toString(){
        return super.toString() + "(resident)";
    }

    /**
     * Used to check if the student is a resident
     * @return true if student is a resident, false otherwise
     */
    @Override
    public boolean isResident(){
        return true;
    }

    /**
     * gets the type of student
     * @return returns "(Resident)" which when getType() is invoked
     */
    @Override
    public String getType(){
        return "(Resident)";
    }

    /**
     * Calculates the student's tuition
     * @param creditsEnrolled credits enrolled by the student
     * @return returns tuition of the student
     */
    @Override
    public double tuitionDue(int creditsEnrolled){
        double tuition = 0;
        if(creditsEnrolled<FULL_TIME){
            tuition = creditsEnrolled * RESIDENT_CREDIT_PRICE;
            tuition += PART_TIME_UNIVERSITY_FEE;
        }else if(creditsEnrolled>EXTRA_FULL_TIME){
            tuition += RESIDENT_TUITION+UNIVERSITY_FEE;
            tuition += (creditsEnrolled-EXTRA_FULL_TIME) * RESIDENT_CREDIT_PRICE;
        }else{
            tuition += RESIDENT_TUITION+UNIVERSITY_FEE;
        }
        return tuition-scholarship;
    }

    /**
     * Sets the scholarship data of Resident object to a new specified value.
     * @param newScholarship value of new scholarship.
     */
    public void setScholarship(int newScholarship){
        scholarship = newScholarship;
    }

}
