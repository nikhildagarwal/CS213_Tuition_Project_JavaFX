/**
 * package for enroll object
 */
package com.example.cs213_tuition_project_javafx;


/**
 * Class to implement EnrollStudent object
 * Holds profile and creditsEnrolled as parameters
 * Contains methods to fetch information about enrolled students' credits and change credits
 * Contains override methods toString, equals
 * @author Nikhil Agarwal, Hyeon Oh
 */
public class EnrollStudent {

    /**
     * Profile Object
     */
    private Profile profile;

    /**
     * credits that the student wants to take in the current semester.
     */
    private int creditsEnrolled;

    /**
     * Constructor for EnrollStudent object
     * @param profile profile of the student
     * @param creditsEnrolled credits enrolled of the specified student
     */
    public EnrollStudent(Profile profile, int creditsEnrolled){
        this.profile = profile;
        this.creditsEnrolled = creditsEnrolled;
    }

    /**
     * Overrides toString
     * @return returns the student's profile along with the credits enrolled
     */
    @Override
    public String toString(){
        return profile.toString()+": credits enrolled: "+creditsEnrolled;
    }

    /**
     * this method overrides the equals method of the Object class
     * @param obj which is an instance of the Object class
     * @return true if the profiles are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj){
        EnrollStudent enrollStudent = (EnrollStudent) obj;
        if(profile.equals(enrollStudent.profile)){
            return true;
        }
        return false;
    }

    /**
     * Getter method to return Profile data of EnrollStudent object.
     * @return Profile Object
     */
    public Profile getProfile(){
        return profile;
    }

    /**
     * Getter method to return creditEnrolled data of EnrollStudent object.
     * @return integer creditEnrolled
     */
    public int getCreditsEnrolled(){
        return creditsEnrolled;
    }

    /**
     * Changes the EnrollStudent object creditsEnrolled to the desired value.
     * @param newCredits which are the credits to be changed
     */
    public void changeCredits(int newCredits){
        creditsEnrolled = newCredits;
    }
}
