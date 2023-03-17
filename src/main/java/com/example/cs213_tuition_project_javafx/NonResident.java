/**
 * Contains all classes for student object and extended student objects
 */
package com.example.cs213_tuition_project_javafx;

/**
 * Implementation of subclass NonResident that extends to Student superclass
 * Contains override methods such as toString
 * Contains methods to calculate tuition, type of nonresident student, checking to see whether or not student is a resident
 * @author Nikhil Agarwal, Hyeon Oh
 */
public class NonResident extends Student {

    /**
     * Min credits need to be full time student
     */
    public static final int FULL_TIME = 12;

    /**
     * Any credit hours over this value and tution will increase
     */
    public static final int EXTRA_FULL_TIME = 16;

    /**
     * Price of each credit hour for Non-Residents
     */
    public static final double NONRESIDENT_CREDIT_PRICE = 966;

    /**
     * University Fee for students
     */
    public static final double UNIVERSITY_FEE = 3268;

    /**
     * University Fee for part-time students
     */
    public static final double PART_TIME_UNIVERSITY_FEE = UNIVERSITY_FEE * 0.8;

    /**
     * Non-Resident flat tuition fee
     */
    public static final double NONRESIDENT_TUITION = 29737;

    /**
     * Constructor for the NonResident object
     * @param profile profile of the NonResident student
     * @param major major of the NonResident student
     * @param creditCompleted creditCompleted of the NonResident student
     */
    public NonResident(Profile profile, Major major, int creditCompleted){
        super(profile,major,creditCompleted);
    }

    /**
     * Overrides the toString method
     * @return invokes the superclass toString and adds non-resident information
     */
    @Override
    public String toString(){
        return super.toString() + "(non-resident)";
    }

    /**
     * checks to see if the student is a resident
     * @return returns false
     */
    @Override
    public boolean isResident(){
        return false;
    }

    /**
     * gets the type of the student
     * @return returns a String "(Non-Resident)"
     */
    @Override
    public String getType(){
        return "(Non-Resident)";
    }

    /**
     * Calculates the tuition due for a NonResident student
     * @param creditsEnrolled of the NonResident student
     * @return tuition which is the amount due assigned to the NonResident student
     */
    @Override
    public double tuitionDue(int creditsEnrolled) {
        double tuition = 0;
        if(creditsEnrolled<FULL_TIME){
            tuition = creditsEnrolled * NONRESIDENT_CREDIT_PRICE;
            tuition += PART_TIME_UNIVERSITY_FEE;
        }else if(creditsEnrolled>EXTRA_FULL_TIME){
            tuition += NONRESIDENT_TUITION+UNIVERSITY_FEE;
            tuition += (creditsEnrolled-EXTRA_FULL_TIME) * NONRESIDENT_CREDIT_PRICE;
        }else{
            tuition += NONRESIDENT_TUITION+UNIVERSITY_FEE;
        }
        return tuition;
    }
}
