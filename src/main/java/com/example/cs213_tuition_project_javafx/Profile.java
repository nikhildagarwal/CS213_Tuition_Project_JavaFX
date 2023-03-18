/**
 * Contains all classes for student object and extended student objects
 */
package com.example.cs213_tuition_project_javafx;
/**
 * Class to implement Profile Object.
 * Holds LastName, FirstName, and DoB as parameters.
 * Contains methods to fetch profile values from outside this class,
 * as well as methods that override equals, toString, and compareTo.
 * @author Nikhil Agarwal, Hyeon Oh
 */
public class Profile implements Comparable<Profile>{

    /**
     * Last name
     */
    private String lname;

    /**
     * First name
     */
    private String fname;

    /**
     * Date of birth (mm/dd/yyyy)
     */
    private Date dob;

    /**
     * Constructor for Profile Object.
     * @param lname Last Name of profile.
     * @param fname First Name of profile.
     * @param dob DoB of profile.
     */
    public Profile(String lname, String fname, Date dob){
        this.lname = lname;
        this.fname = fname;
        this.dob = dob;
    }

    /**
     * Getter Method for last Name of profile object
     * @return String lastName
     */
    public String getLastName(){
        return lname;
    }

    /**
     * Getter Method for firstName of profile object
     * @return String firstName
     */
    public String getFirstName(){
        return fname;
    }

    /**
     * Getter Method for date of profile object
     * @return Date object
     */
    public Date getDate(){
        return dob;
    }

    /**
     * Overrides toString method from Java Object class.
     * @return Profile as string printed as: firstName lastName doB
     */
    @Override
    public String toString(){
        return fname + " " + lname + " " + dob.toString();
    }

    /**
     * Overrides equals method from Java Object class.
     * @param obj object to be checked.
     * @return true if object equals Profile, false if otherwise.
     */
    @Override
    public boolean equals(Object obj){
        Profile profile = (Profile) obj;
        if(profile.fname.equalsIgnoreCase(fname)){
            if(profile.lname.equalsIgnoreCase(lname)){
                if(dob.equals(profile.dob)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Overrides compareTo method from Java Comparable class.
     * Syntax: profile1.compareTo(profile2)
     * @param profile the object to be compared.
     * @return positive if profile1 is lexgraphically greater than profile2, 0 if equal, negative otherwise.
     */
    @Override
    public int compareTo(Profile profile){
        int compareLastName = lname.compareTo(profile.lname);
        if(compareLastName == 0){
            int compareFirstName = fname.compareTo(profile.fname);
            if(compareFirstName == 0){
                return dob.compareTo(profile.dob);
            }else{
                return compareFirstName;
            }
        }else{
            return compareLastName;
        }
    }
}
