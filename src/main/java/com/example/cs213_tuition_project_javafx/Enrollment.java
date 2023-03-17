/**
 * structure package, contains Data Structure objects
 */
package com.example.cs213_tuition_project_javafx;




import java.text.DecimalFormat;


/**
 * Class to implement Enrollment object
 * Holds an array of EnrollStudent data type, size of the array
 * Contains methods to add, remove, print, getStudent , and check if a student is contained in the array
 * @author Nikhil Agarwal, Hyeon Oh
 */

public class Enrollment {

    /**
     * Data structure (array) to hold EnrollStudent Objects
     */
    private EnrollStudent[] enrollStudents;

    /**
     * Keeps track of number of elements in our array
     */
    private int size;

    /**
     * Initial size of array
     */
    public static final int INITIAL_SIZE = 4;

    /**
     * -1 to indicated element not found in search
     */
    public static final int NOT_FOUND = -1;

    /**
     * Array starts with no elements
     */
    public static final int INITIAL_NUMBER_OF_ELEMENTS = 0;

    /**
     * Constructor for the Enrollment object
     */
    public Enrollment(){
        this.enrollStudents = new EnrollStudent[INITIAL_SIZE];
        this.size = INITIAL_NUMBER_OF_ELEMENTS;
    }

    /**
     * adds a student of EnrollStudent type
     * @param enrollStudent which is the student to be added
     */
    public void add(EnrollStudent enrollStudent){
        if(size==enrollStudents.length){
            grow();
        }
        enrollStudents[size] = enrollStudent;
        size++;
    }

    /**
     * removes a student from the array
     * @param enrollStudent which is the student to be deleted
     */
    public void remove(EnrollStudent enrollStudent){
        int index = find(enrollStudent);
        if(index == NOT_FOUND){
            return;
        }
        EnrollStudent moveStudent = enrollStudents[size-1];
        enrollStudents[index] = moveStudent;
        size--;
        enrollStudents[size] = null;
    }

    /**
     * checks the EnrollStudent array to see if the student exists
     * @param enrollStudent student to be searched for in the array
     * @return true if the student is in the array, false otherwise
     */
    public boolean contains(EnrollStudent enrollStudent){
        for(int i = 0;i<size;i++){
            if(enrollStudent.equals(enrollStudents[i])){
                return true;
            }
        }
        return false;
    }

    /**
     * Searches through EnrollStudents array and returns the object if found.
     * @param profile matching profile of EnrollStudent
     * @return EnrollStudent that has the same profile as given
     */
    public EnrollStudent getEnrollStudentFromProfile(Profile profile){
        for(int i = 0;i<size;i++){
            if(enrollStudents[i].getProfile().equals(profile)){
                return enrollStudents[i];
            }
        }
        return null;
    }

    /**
     * fetches the student from the array
     * @param enrollStudent which is the student to be fetched
     * @return returns the student and null if the student is not found
     */
    public EnrollStudent getEnrollStudent(EnrollStudent enrollStudent){
        for(int i = 0;i<size;i++){
            if(enrollStudent.equals(enrollStudents[i])){
                return enrollStudents[i];
            }
        }
        return null;
    }

    /**
     * prints the EnrollStudent array
     */
    public void print(){
        for(int i =0;i<size;i++){
            System.out.println(enrollStudents[i]);
        }
    }

    /**
     * Prints the EnrollStudent Array and each student corresponding tuition due.
     * @param roster Roster object to search and get student data.
     */
    public void printTuition(Roster roster){
        for(int i =0;i<size;i++){
            Profile profile = enrollStudents[i].getProfile();
            String type = roster.getStudent(profile).getType();
            Student student = roster.getStudent(profile);
            int creditsEnrolled = enrollStudents[i].getCreditsEnrolled();
            System.out.println(profile+ " "+type+ " enrolled "+creditsEnrolled+" credits: tuition due: "+formatTuition(student.tuitionDue(creditsEnrolled)));
        }
    }

    /**
     * Change double to money format (EX: 23423.4 -> $23,423.40)
     * @param tuition The tuition value of a student (double)
     * @return String formatted as United States Money.
     */
    public String formatTuition(double tuition){
        DecimalFormat df = new DecimalFormat("0.00");
        String temp = "$"+df.format(tuition);
        int length = temp.length();
        String substringBack = temp.substring(length-6,length);
        String substringFront = temp.substring(0,length-6);
        return substringFront+","+substringBack;
    }


    /**
     * Increases the size of the EnrollStudent array when the number of elements in the array equals the length of the array
     */
    private void grow(){
        int prevLength = enrollStudents.length;
        EnrollStudent[] newEnrollment = new EnrollStudent[prevLength+INITIAL_SIZE];
        for(int i = 0; i < prevLength; i++){
            newEnrollment[i] = enrollStudents[i];
        }
        enrollStudents = newEnrollment;
    }

    /**
     * returns the size of the EnrollStudent array
     * @return size of the array
     */
    public int size(){
        return size;
    }

    /**
     * looks for the student in the EnrollStudent array
     * @param enrollStudent which is the student to be searched
     * @return returns the index of the student found in the array, returns NOT_FOUND otherwise
     */
    private int find(EnrollStudent enrollStudent){
        for(int i = 0;i<size;i++){
            if(enrollStudent.equals(enrollStudents[i])){
                return i;
            }
        }
        return NOT_FOUND;
    }
}

