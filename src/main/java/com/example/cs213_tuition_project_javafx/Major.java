/**
 * Contains all classes for student object and extended student objects
 */
package com.example.cs213_tuition_project_javafx;

import java.util.StringTokenizer;

/**
 * Major objects that hold major codes and the corresponding school @ Rutgers.
 * Using this class we can check if a certain school is a valid school @ Rutgers.
 * Compare two Majors to each other based on school (alphabetically) and then major code (numerically).
 * @author Nikhil Agarwal, Hyeon Oh
 */
public enum Major {
    /**
     * Enums of this class
     */
    CS("01:198","SAS"),
    MATH("01:640","SAS"),
    EE("14:332","SOE"),
    ITI("04:547","SC&I"),
    BAIT("33:136","RBS");

    /**
     * school and department code of each major
     */
    private final String majorCode;

    /**
     * school that each major is a part of
     */
    private final String school;

    /**
     * Constructor for Major object.
     * @param majorCode corresponding major code for a major. Ex: CS -> 01:198
     * @param school corresponding school for major. EX: CS -> SAS
     */
    Major (String majorCode,String school){
        this.majorCode = majorCode;
        this.school = school;
    }

    /**
     * return major code of the major object.
     * @return major code.
     */
    public String getMajorCode(){
        return majorCode;
    }

    /**
     * return school of the major object.
     * @return school.
     */
    public String getSchool(){
        return school;
    }

    /**
     * Checks to see if a string school, is a valid school @ Rutgers.
     * @param school school that we want to check.
     * @return true if school is one of the following (SAS, SOE, SC&I, RBS), false otherwise.
     */
    public boolean isValidSchool(String school){
        school.toUpperCase();
        boolean valid = false;
        if(school.equals(CS.school)){
            valid = true;
        }
        if(school.equals(EE.school)){
            valid = true;
        }
        if(school.equals(ITI.school)){
            valid = true;
        }
        if(school.equals(BAIT.school)){
            valid = true;
        }
        return valid;
    }

    /**
     * Compares two major objects by school, then by major code.
     * Syntax: major1.compareWith(major2);
     * @param major this input is major2, which we are comparing to major1 above.
     * @return positive int if major1>major2, 0 if equal, negative int otherwise.
     */
    public int compareWith(Major major){
        int compareSchool = school.compareTo(major.school);
        if(compareSchool == 0){
            int departmentCode1 = getDepartmentCode(majorCode);
            int departmentCode2 = getDepartmentCode(major.majorCode);
            return Integer.compare(departmentCode1,departmentCode2);
        }else{
            return compareSchool;
        }
    }

    /**
     * Parses a String majorcode into an integer.
     * Only returns the department section of the major code. Ex: "01:198" -> 198
     * @param majorCode majorCode of a major.
     * @return department code of the major.
     */
    private int getDepartmentCode(String majorCode){
        StringTokenizer codeTokens = new StringTokenizer(majorCode,":");
        codeTokens.nextToken();
        return Integer.parseInt(codeTokens.nextToken());
    }
}