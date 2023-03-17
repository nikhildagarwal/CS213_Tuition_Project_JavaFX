/**
 * Contains all classes for student object and extended student objects
 */
package com.example.cs213_tuition_project_javafx;

/**
 * Implementation of subclass TriState which extends to parent class NonResident
 * Contains instance variable state
 * Contains overrided methods such as toString
 * @author Nikhil Agarwal, Hyeon Oh
 */
public class TriState extends NonResident{

    /**
     * String state (Ex: NY)
     * data to add to Non-Resident object.
     */
    private String state;

    /**
     * Min credits to be considered full time student.
     */
    public static final int FULL_TIME = 12;

    /**
     * Discount for full-time NY students
     */
    public static final double NY_DISCOUNT = 4000;

    /**
     * Discount for full-time CT students
     */
    public static final double CT_DISCOUNT = 5000;

    /**
     * Constructor for the TriState subclass object
     * @param profile profile of the student
     * @param major major of the student
     * @param creditCompleted creditCompleted of the student
     * @param state state of the student
     */
    public TriState(Profile profile, Major major, int creditCompleted, String state){
        super(profile,major,creditCompleted);
        this.state = state;
    }

    /**
     * Overrides the toString method
     * @return invokes the parent class toString method and adds the information that the student is from a tri-state
     */
    @Override
    public String toString(){
        return super.toString()+"(tri-state:"+state+")";
    }

    /**
     * Overrides getType method in Student class.
     * Implements getType abstract method.
     * @return Type of student for Tri state student as String.
     */
    @Override
    public String getType(){
        String type = "";
        switch(state){
            case "NY":
                type = "(Tri-state NY)";
                break;
            case "CT":
                type = "(Tri-state CT)";
        }
        return type;
    }

    /**
     * Calculates the Tuition Due for Tri-State student depending on State
     * @param creditEnrolled of the NonResident student
     * @return  double Tuition Due for Tri-State
     */
    @Override
    public double tuitionDue(int creditEnrolled){
        if(creditEnrolled<FULL_TIME){
            return super.tuitionDue(creditEnrolled);
        }else{
            double temp = super.tuitionDue(creditEnrolled);
            switch(state){
                case "NY":
                    temp -= NY_DISCOUNT;
                    break;
                case "CT":
                    temp -= CT_DISCOUNT;

            }
            return temp;
        }
    }
}
