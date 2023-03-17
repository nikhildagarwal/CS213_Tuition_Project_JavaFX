/**
 * package to hold manager class (all command line inputs are processed here)
 */
package com.example.cs213_tuition_project_javafx;



import java.util.Scanner;
import java.io.File;

/**
 * Implements the RosterManager class to take care of the roster and make changes to the roster.
 * Processes all console inputs and outputs a response to the console.
 * @author Nikhil Agarwal, Hyeon Oh
 */
public class TuitionManager {

    /**
     * Empty = 0
     */
    public static final int EMPTY = 0;

    /**
     * Index that holds scholarship value
     */
    public static final int SCHOLARSHIP_INDEX = 4;

    /**
     * Proper length of scholarship value input
     */
    public static final int LENGTH_SCHOLARSHIP_INPUT = 4;

    /**
     * Index that holds credits value
     */
    public static final int CREDIT_ENROLLED_INDEX = 4;

    /**
     * Length of input to enroll student
     */
    public static final int LENGTH_TO_ENROLL = 5;

    /**
     * Length of Resident input to have all data needed
     */
    public static final int LENGTH_RESIDENT_INPUT = 6;

    /**
     * Length of Resident input to have all data needed
     */
    public static final int LENGTH_INTERNATIONAL_INPUT = 6;

    /**
     * Length of Resident input to have all data needed
     */
    public static final int LENGTH_TRISTATE_INPUT = 7;

    /**
     * Length of Resident input to have all data needed
     */
    public static final int LENGTH_TRISTATE_INPUT_NO_STATE = 6;

    /**
     * Starting scholarship value
     */
    public static final int STARTING_SCHOLARSHIP = 0;

    /**
     * Index of input that holds state value
     */
    public static final int STATE_INDEX = 6;

    /**
     * Index to boolean value that determines abroad or not abroad
     */
    public static final int ABROAD_INDEX = 6;

    /**
     * minimum number of credits to be considered a student
     */
    public static final int MIN_CREDITS = 0;

    /**
     * index in array containing command code
     */
    public static final int CODE_INDEX = 0;

    /**
     * index in array containing first name
     */
    public static final int FIRSTNAME_INDEX = 1;

    /**
     * index in array containing last name
     */
    public static final int LASTNAME_INDEX = 2;

    /**
     * index in array containing date
     */
    public static final int DATE_INDEX = 3;

    /**
     * index in array containing major
     */
    public static final int MAJOR_INDEX = 4;

    /**
     * index in array containing number of credits
     */
    public static final int CREDITS_INDEX = 5;

    /**
     * null holder
     */
    public static final int ANY_NUMBER_OF_CREDITS = 0;

    /**
     * index in array containing school
     */
    public static final int SCHOOL_INDEX = 1;

    /**
     * indicates we are working with full roster list
     */
    public static final int FULL_ROSTER = 0;

    /**
     * indicates we are working with a school roster list
     */
    public static final int SCHOOL_ROSTER = 1;

    /**
     * null holder
     */
    public static final String ALL_SCHOOLS = "";

    /**
     * Runs the entire Project form this method.
     * Processes all line commands
     */
    public void run(){
        Roster roster = new Roster();
        Enrollment enrollment = new Enrollment();
        System.out.println("Tuition Manager running...");
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()){
            String[] tokens = processLine(scanner.nextLine());
            try{
                switch(tokens[CODE_INDEX]){
                    case "LS": processLS(roster); break;
                    case "R": processRemove(tokens,roster); break;
                    case "P": processPrint(roster,FULL_ROSTER,ALL_SCHOOLS); break;
                    case "PS": processPrintStanding(roster); break;
                    case "PC": processPrintMajor(roster); break;
                    case "C": processChange(tokens,roster); break;
                    case "Q": System.out.println("Tuition Manager terminated."); return;
                    case "AR": processAddResident(tokens,roster); break;
                    case "AT": processAddTriState(tokens,roster); break;
                    case "AI": processAddInternational(tokens,roster); break;
                    case "AN": processAddNonResident(tokens,roster); break;
                    case "PE": processPrintEnrollment(enrollment); break;
                    case "E": processEnroll(tokens,enrollment,roster); break;
                    case "D": processDrop(tokens,enrollment); break;
                    case "PT": processPrintTuition(enrollment, roster); break;
                    case "S": processScholarship(tokens,roster,enrollment); break;
                    case "SE": processSemesterEnd(roster,enrollment); break;
                    case "L": processPrintSchool(tokens,roster); break;
                    default: System.out.println(tokens[0] + " is an invalid command!");
                }
            }catch (Exception e){}
        }
    }

    public void processPrintSchool(String[] tokens, Roster roster){
        if(roster.isEmpty()){
            System.out.println("School List is Empty");
            return;
        }

        String school = tokens[SCHOOL_INDEX].toUpperCase();
        Roster myRoster = new Roster();
        myRoster = roster.addFromSchool(school);
        if(myRoster.isEmpty()){
            System.out.println("School List is Empty");
            return;
        }
        System.out.println("** School List (Last Name, First Name, DoB) **");
        myRoster.print();
        System.out.println("* end of School list *");
    }

    /**
     * Adds creditsEnrolled to credits completed and then prints the students that are eligible to graduate.
     * @param roster roster of students
     * @param enrollment list of students that are enrolled
     */
    private void processSemesterEnd(Roster roster, Enrollment enrollment){
        roster.updateCreditsCompleted(enrollment);
        System.out.println("Credit completed has been updated.");
        System.out.println("** list of students eligible for graduation **");
        roster.printGraduated();
    }

    /**
     * Adds scholarships to Residents student objects
     * @param tokens String[] of tokens from input arguments.
     * @param roster Roster of students
     * @param enrollment list of students that are enrolled
     */
    private void processScholarship(String[] tokens, Roster roster, Enrollment enrollment){
        if(tokens.length<LENGTH_SCHOLARSHIP_INPUT){
            System.out.println("Missing data in line command.");
            return;
        }
        Profile profile = new Profile(tokens[LASTNAME_INDEX],tokens[FIRSTNAME_INDEX],new Date(tokens[DATE_INDEX]));
        Resident tempRes = new Resident(profile, Major.CS,0,0);
        EnrollStudent enrollStudent = enrollment.getEnrollStudent(new EnrollStudent(profile,0));

        if(roster.contains(tempRes)){
            Student temp = roster.getStudent(profile);
            String type = temp.getType();
            if(!type.equals("(Resident)")){
                System.out.println(profile+" "+type+" is not eligible for the scholarship.");
                return;
            }
            int scholarship = 0;
            try{
                scholarship = Integer.parseInt(tokens[SCHOLARSHIP_INDEX]);
            }catch (Exception e){
                System.out.println("Amount is not an integer.");
                return;
            }
            if(scholarship<=0 || scholarship>10000){
                System.out.println(scholarship+": invalid amount.");
                return;
            }
            if(enrollStudent.getCreditsEnrolled()<12){
                System.out.println(profile+" part time student is not eligible for the scholarship.");
                return;
            }
            Resident ourStudent = (Resident) temp;
            ourStudent.setScholarship(scholarship);
            System.out.println(profile+": scholarship amount updated.");

        }else{
            System.out.println(profile+" is not in the roster.");
        }
    }

    /**
     * Checks to see if there are students enrolled,
     * if there are students that are enrolled, it will print the list of enrolled students and tuition that each student owes.
     * @param enrollment list of students that are enrolled
     * @param roster roster of students
     */
    private void processPrintTuition(Enrollment enrollment, Roster roster){
        if(enrollment.size()>0){
            System.out.println("** Tuition due **");
            enrollment.printTuition(roster);
            System.out.println("* end of tuition due *");
        }else{
            System.out.println("Student roster is empty!");
        }
    }

    /**
     * Drops a student from enrollment.
     * @param tokens String[] of tokens from input arguments.
     * @param enrollment
     */
    private void processDrop(String[] tokens, Enrollment enrollment){
        Profile profile = new Profile(tokens[LASTNAME_INDEX],tokens[FIRSTNAME_INDEX],new Date(tokens[DATE_INDEX]));
        EnrollStudent enrollStudent = new EnrollStudent(profile,0);
        if(!enrollment.contains(enrollStudent)){
            System.out.println(profile+" is not enrolled.");
            return;
        }
        enrollment.remove(enrollStudent);
        System.out.println(profile+" dropped.");

    }

    /**
     * Enrolls a student if All the data that is provided in the input command line is valid.
     * Also checks to see if roster contains student we are trying to enroll. If not the method will terminate.
     * @param tokens String[] of tokens from input arguments.
     * @param enrollment list of students that are enrolled this semester
     * @param roster roster of students.
     */
    private void processEnroll(String[] tokens, Enrollment enrollment, Roster roster){
        if(tokens.length<LENGTH_TO_ENROLL){
            System.out.println("Missing data in line command.");
            return;
        }
        int creditsEnrolled = 0;
        try{
            creditsEnrolled = Integer.parseInt(tokens[CREDIT_ENROLLED_INDEX]);
        }catch (Exception e){
            System.out.println("Credits enrolled is not an integer.");
            return;
        }
        Profile profile = new Profile(tokens[LASTNAME_INDEX],tokens[FIRSTNAME_INDEX],new Date(tokens[DATE_INDEX]));
        EnrollStudent enrollStudent = new EnrollStudent(profile,creditsEnrolled);
        Resident student = new Resident(profile,Major.CS,0,0);
        if(!roster.contains(student)){
            System.out.println("Cannot enroll: "+ profile +" is not in the roster.");
            return;
        }
        if(roster.getStudent(profile).isValid(creditsEnrolled)){
            if(enrollment.contains(enrollStudent)){
                enrollment.getEnrollStudent(enrollStudent).changeCredits(creditsEnrolled);
                System.out.println(profile + " enrolled "+creditsEnrolled+" credits");
            }else{
                enrollment.add(enrollStudent);
                System.out.println(profile + " enrolled "+creditsEnrolled+" credits");
            }
        }else{
            System.out.println(roster.getStudent(profile).getType()+" "+creditsEnrolled+": invalid credit hours.");
        }


    }

    /**
     * Prints the enrollment list.
     * @param enrollment enrollment DS to be printed.
     */
    private void processPrintEnrollment(Enrollment enrollment){
        if(enrollment.size()==EMPTY){
            System.out.println("Enrollment is empty!");
            return;
        }
        System.out.println("** Enrollment **");
        enrollment.print();
        System.out.println("* end of enrollment *");
    }

    /**
     * Used to remove a student from the roster
     * @param tokens input taken from the user at the command line
     * @param roster roster object that the student object will be removed from.
     */
    private void processRemove(String[] tokens,Roster roster){
        Resident studentToRemove = new Resident(new Profile(tokens[LASTNAME_INDEX],tokens[FIRSTNAME_INDEX],new Date(tokens[DATE_INDEX])),Major.CS,0,0);
        boolean removed = roster.remove(studentToRemove);
        if(removed){
            System.out.println(studentToRemove.getProfile() + " removed from the roster.");
            return;
        }
        System.out.println(studentToRemove.getProfile() + " is not in the roster.");
    }

    /**
     * Adds a nonResident to our enrollment DS
     * Checks to see if roster has the student.
     * If not student will not be added to the enrollment list.
     * @param tokens String[] of tokens from input arguments.
     * @param roster roster of students.
     */
    private void processAddNonResident(String[] tokens, Roster roster){
        if(tokens.length<LENGTH_RESIDENT_INPUT){
            System.out.println("Missing data in line command.");
            return;
        }
        if(studentChecker(tokens)){
            Profile profile = new Profile(tokens[LASTNAME_INDEX],tokens[FIRSTNAME_INDEX],new Date(tokens[DATE_INDEX]));
            NonResident nonResident = new NonResident(profile,grabMajorString(tokens[MAJOR_INDEX]),Integer.parseInt(tokens[CREDITS_INDEX]));
            if(nonResident.isValidStudent()){
                if(roster.contains(nonResident)){
                    System.out.println(nonResident.getProfile()+" is already in the roster.");
                }else{
                    roster.add(nonResident);
                    System.out.println(nonResident.getProfile()+" added to the roster.");
                }
            }
        }
    }

    /**
     * Changes a student's major if the student is found the roster and the major they want to change too is valid.
     * @param tokens string array that is taken from the command line from the user
     * @param roster the roster object that will be searched to find a specific student.
     */
    private void processChange(String[] tokens, Roster roster){
        Major major = grabMajor(tokens);
        if(major==null){
            return;
        }
        Profile profile = new Profile(tokens[LASTNAME_INDEX],tokens[FIRSTNAME_INDEX],new Date(tokens[DATE_INDEX]));
        Resident changedStudent = new Resident(profile,major,ANY_NUMBER_OF_CREDITS,0);
        boolean changed = roster.change(changedStudent,major);
        if(changed){
            System.out.println(profile + " major changed to " + major);
        }else{
            System.out.println(profile + " is not in the roster.");
        }
    }

    /**
     * Adds an International to our enrollment DS
     * Checks to see if roster has the student.
     * If not student will not be added to the enrollment list.
     * @param tokens String[] of tokens from input arguments.
     * @param roster roster of students.
     */
    private void processAddInternational(String[] tokens, Roster roster){
        if(tokens.length<LENGTH_INTERNATIONAL_INPUT){
            System.out.println("Missing data in line command.");
            return;
        }
        if(studentChecker(tokens)){
            Profile profile = new Profile(tokens[LASTNAME_INDEX],tokens[FIRSTNAME_INDEX],new Date(tokens[DATE_INDEX]));
            International international = new International(profile,grabMajorString(tokens[MAJOR_INDEX]),Integer.parseInt(tokens[CREDITS_INDEX]));
            try{
                boolean temp = false;
                if(tokens[ABROAD_INDEX].equals("true")){
                    temp = true;
                }
                international = new International(profile,grabMajorString(tokens[MAJOR_INDEX]),Integer.parseInt(tokens[CREDITS_INDEX]),temp);
            }catch (Exception e){

            }
            if(international.isValidStudent()){
                if(roster.contains(international)){
                    System.out.println(international.getProfile()+" is already in the roster.");
                }else{
                    roster.add(international);
                    System.out.println(international.getProfile()+" added to the roster.");
                }
            }
        }
    }

    /**
     * Adds a Tri-State to our enrollment DS
     * Checks to see if roster has the student.
     * If not student will not be added to the enrollment list.
     * @param tokens String[] of tokens from input arguments.
     * @param roster roster of students.
     */
    private void processAddTriState(String[] tokens, Roster roster){
        if(tokens.length==LENGTH_TRISTATE_INPUT_NO_STATE){
            System.out.println("Missing the state code.");
            return;
        }
        if(tokens.length<LENGTH_TRISTATE_INPUT){
            System.out.println("Missing data in line command.");
            return;
        }
        String upperCaseState = tokens[STATE_INDEX].toUpperCase();
        if(!upperCaseState.equals("NY") && !upperCaseState.equals("CT")){
            System.out.println(tokens[STATE_INDEX]+": Invalid state code.");
            return;
        }
        if(studentChecker(tokens)){
            Profile profile = new Profile(tokens[LASTNAME_INDEX],tokens[FIRSTNAME_INDEX],new Date(tokens[DATE_INDEX]));
            TriState triState = new TriState(profile,grabMajorString(tokens[MAJOR_INDEX]),Integer.parseInt(tokens[CREDITS_INDEX]),tokens[STATE_INDEX].toUpperCase());
            if(triState.isValidStudent()){
                if(roster.contains(triState)){
                    System.out.println(triState.getProfile()+" is already in the roster.");
                }else{
                    roster.add(triState);
                    System.out.println(triState.getProfile()+" added to the roster.");
                }
            }
        }

    }

    /**
     * Adds a Resident to our enrollment DS
     * Checks to see if roster has the student.
     * If not student will not be added to the enrollment list.
     * @param tokens String[] of tokens from input arguments.
     * @param roster roster of students.
     */
    private void processAddResident(String[] tokens, Roster roster){
        if(tokens.length<LENGTH_RESIDENT_INPUT){
            System.out.println("Missing data in line command.");
            return;
        }
        if(studentChecker(tokens)){
            Profile profile = new Profile(tokens[LASTNAME_INDEX],tokens[FIRSTNAME_INDEX],new Date(tokens[DATE_INDEX]));
            Resident resident = new Resident(profile,grabMajorString(tokens[MAJOR_INDEX]),Integer.parseInt(tokens[CREDITS_INDEX]),STARTING_SCHOLARSHIP);
            if(resident.isResident()){
                if(resident.isValidStudent()){
                    if(roster.contains(resident)){
                        System.out.println(resident.getProfile()+" is already in the roster.");
                    }else{
                        roster.add(resident);
                        System.out.println(resident.getProfile()+" added to the roster.");
                    }
                }
            }
        }
    }

    /**
     * Displays the roster sorted by standing by calling printByStanding method from the roster class
     * If the roster is empty, it will display to the user that it is empty
     * @param roster the argument that is to be displayed
     */
    private void processPrintStanding(Roster roster){
        if(roster.isEmpty()){
            System.out.println("Student roster is empty!");
            return;
        }
        System.out.println("** Student roster sorted by standing **");
        roster.printByStanding();
        System.out.println("* end of roster *");
    }

    /**
     * Displays the roster sorted by school, major
     * @param roster the argument that is to be displayed
     */
    private void processPrintMajor(Roster roster){
        if(roster.isEmpty()){
            System.out.println("Student roster is empty!");
            return;
        }
        System.out.println("** Student roster sorted by school, major **");
        roster.printByMajor();
        System.out.println("* end of roster *");
    }

    /**
     * Displays to the user whether the roster or list is empty, and displays the roster
     * @param roster argument used to determine if the roster is empty and also used to call print method from roster class
     * @param typeOfRoster type of roster (Full student roster, school specific roster).
     * @param school if the type of roster is a school list, the school of the list will be input, otherwise this parameter is an empty string.
     */
    private void processPrint(Roster roster,int typeOfRoster,String school){
        if(roster.isEmpty()){
            if(typeOfRoster == FULL_ROSTER){
                System.out.println("Student roster is empty!");
                return;
            }
            if(typeOfRoster == SCHOOL_ROSTER){
                System.out.println("School list is empty!");
                return;
            }
        }
        if(typeOfRoster == FULL_ROSTER){
            System.out.println("** Student roster sorted by last name, first name, DOB **");
        }
        if(typeOfRoster == SCHOOL_ROSTER){
            System.out.println("** Students in "+ school+" **");
        }
        roster.print();
        if(typeOfRoster == FULL_ROSTER){
            System.out.println("* end of roster *");
        }
        if(typeOfRoster == SCHOOL_ROSTER){
            System.out.println("* end of list *");
        }

    }

    /**
     * uses string methods such as split to take inputs separately
     * @param command the command which is taken to be split
     * @return newLine which is a string array that returns the commands
     */
    private String[] processLine(String command){
        String[] line = command.split("\\s");
        int counter = 0;
        String[] newLine = new String[line.length];
        for(String token:line){
            if(!token.equals("")){
                newLine[counter] = token;
                counter++;
            }
        }
        return newLine;
    }

    /**
     * uses string methods such as split to take inputs separately
     * This method is parses lines from txt file that is given.
     * @param command the command which is taken to be split
     * @return newLine which is a string array that returns the commands
     */
    private String[] processLineLS(String command){
        String[] line = command.split(",");
        int counter = 0;
        String[] newLine = new String[line.length];
        for(String token:line){
            if(!token.equals("")){
                newLine[counter] = token;
                counter++;
            }
        }
        return newLine;
    }

    /**
     * Method to check is student is valid, and gives the go ahead to add student object to the roster.
     * @param tokens String[] of tokens from input arguments.
     * @return true if student is good to be added, false if any data point is bad.
     */
    private boolean studentChecker(String[] tokens){
        Date dob = new Date(tokens[DATE_INDEX]);
        if(!dob.isValid()){
            System.out.println("DOB invalid: "+dob+" not a valid calendar date!");
            return false;
        }
        if(!dob.isValidAge()){
            System.out.println("DOB invalid: "+dob+" younger than 16 years old.");
            return false;
        }
        Major major = grabMajor(tokens);
        if(major == null){
            return false;
        }
        try{
            if(Integer.parseInt(tokens[CREDITS_INDEX]) < MIN_CREDITS){
                System.out.println("Credits completed invalid: cannot be negative!");
                return false;
            }
        }catch(Exception e){
            System.out.println("Credits completed invalid: not an integer!");
            return false;
        }
        return true;
    }

    /**
     * This method processes a txt. File of student objects and adds them to our roster.
     * @param roster roster DS object that we want to add our students too.
     */
    private void processLS(Roster roster){
        try{
            Scanner scanner = new Scanner(new File("studentList.txt"));
            while(scanner.hasNextLine()){
                String[] tokens = processLineLS(scanner.nextLine());
                switch(tokens[CODE_INDEX]){
                    case "R":
                        Resident resident = makeResident(tokens[FIRSTNAME_INDEX],tokens[LASTNAME_INDEX],tokens[DATE_INDEX],tokens[MAJOR_INDEX],tokens[CREDITS_INDEX]);
                        roster.add(resident);
                        break;
                    case "I":
                        String abroad = "";
                        International international = null;
                        try{
                            abroad = tokens[ABROAD_INDEX];
                            international = makeInternational(tokens[FIRSTNAME_INDEX],tokens[LASTNAME_INDEX],tokens[DATE_INDEX],tokens[MAJOR_INDEX],tokens[CREDITS_INDEX],abroad);
                        }catch (Exception e){
                            international = makeInternational(tokens[FIRSTNAME_INDEX],tokens[LASTNAME_INDEX],tokens[DATE_INDEX],tokens[MAJOR_INDEX],tokens[CREDITS_INDEX]);
                        }
                        roster.add(international);
                        break;
                    case "T":
                        TriState triState = makeTriState(tokens[FIRSTNAME_INDEX],tokens[LASTNAME_INDEX],tokens[DATE_INDEX],tokens[MAJOR_INDEX],tokens[CREDITS_INDEX],tokens[STATE_INDEX]);
                        roster.add(triState);
                        break;
                    case "N":
                        NonResident nonResident = makeNonResident(tokens[FIRSTNAME_INDEX],tokens[LASTNAME_INDEX],tokens[DATE_INDEX],tokens[MAJOR_INDEX],tokens[CREDITS_INDEX]);
                        roster.add(nonResident);
                }
            }
            System.out.println("Students loaded to the roster.");
        }catch (Exception e){

        }
    }

    /**
     * Makes a Non-Resident student object.
     * @param firstName String first name
     * @param lastName String last name
     * @param date Date object
     * @param major Major enum
     * @param creditCompleted integer credits student has completed
     * @return Non-Resident object that we have created.
     */
    private NonResident makeNonResident(String firstName, String lastName, String date, String major, String creditCompleted){
        Major nonResMajor = grabMajorString(major);
        NonResident nonResident  = new NonResident(new Profile(lastName,firstName,new Date(date)),nonResMajor,Integer.parseInt(creditCompleted));
        return nonResident;
    }

    /**
     * Makes a Tri-State student object.
     * @param firstName String first name
     * @param lastName String last name
     * @param date Date object
     * @param major Major enum
     * @param creditCompleted integer credits student has completed
     * @param state String State (EX: NY)
     * @return Tri-State object that we have created.
     */
    private TriState makeTriState(String firstName, String lastName, String date, String major, String creditCompleted, String state){
        Major triMajor = grabMajorString(major);
        TriState triState = new TriState(new Profile(lastName,firstName,new Date(date)),triMajor,Integer.parseInt(creditCompleted),state);
        return triState;
    }

    /**
     * Makes a Resident student object.
     * @param firstName String first name
     * @param lastName String last name
     * @param date Date object
     * @param major Major enum
     * @param creditCompleted integer credits student has completed
     * @return Resident object that we have created.
     */
    private Resident makeResident(String firstName, String lastName, String date, String major, String creditCompleted){
        Major resMajor = grabMajorString(major);
        Resident resident  = new Resident(new Profile(lastName,firstName,new Date(date)),resMajor,Integer.parseInt(creditCompleted),0);
        return resident;
    }

    /**
     * Makes an International student object.
     * @param firstName String first name
     * @param lastName String last name
     * @param date Date object
     * @param major Major enum
     * @param creditCompleted integer credits student has completed
     * @param abroad boolean Abroad var to determine if International student is studying aboard.
     * @return International object that we have created.
     */
    private International makeInternational(String firstName, String lastName, String date, String major, String creditCompleted,String abroad){
        Major iMajor = grabMajorString(major);
        boolean isAbroad = false;
        if(abroad.equals("true")){
            isAbroad = true;
        }
        International international = new International(new Profile(lastName,firstName,new Date(date)),iMajor,Integer.parseInt(creditCompleted),isAbroad);
        return international;
    }

    /**
     * Makes an International student object.
     * This sets the abroad data in our International student object to default false.
     * @param firstName String first name
     * @param lastName String last name
     * @param date Date object
     * @param major Major enum
     * @param creditCompleted integer credits student has completed
     * @return International object that we have created.
     */
    private International makeInternational(String firstName, String lastName, String date, String major, String creditCompleted){
        Major iMajor = grabMajorString(major);
        International international = new International(new Profile(lastName,firstName,new Date(date)),iMajor,Integer.parseInt(creditCompleted));
        return international;
    }

    /**
     * Checks to see if a given console input matches a valid major in enum class Major.
     * @param tokens string array which is taken from the command line from the user
     * @return major returns the major, null if major is not valid.
     */
    private Major grabMajor(String[] tokens){
        Major major = null;
        switch(tokens[MAJOR_INDEX].toUpperCase()){
            case "EE":
                major = Major.EE;
                break;
            case "CS":
                major = Major.CS;
                break;
            case "BAIT":
                major = Major.BAIT;
                break;
            case "MATH":
                major = Major.MATH;
                break;
            case "ITI":
                major = Major.ITI;
                break;
            default:
                System.out.println("Major code invalid: "+tokens[MAJOR_INDEX]);
        }
        return major;
    }

    /**
     * Checks to see if a given console input matches a valid major in enum class Major.
     * Does not take token input, instead uses a string directly.
     * @return major returns the major, null if major is not valid.
     */
    private Major grabMajorString(String majorString){
        Major major = null;
        switch(majorString.toUpperCase()){
            case "EE":
                major = Major.EE;
                break;
            case "CS":
                major = Major.CS;
                break;
            case "BAIT":
                major = Major.BAIT;
                break;
            case "MATH":
                major = Major.MATH;
                break;
            case "ITI":
                major = Major.ITI;
                break;
            default:
                System.out.println("Major code invalid: "+majorString);
        }
        return major;
    }
}