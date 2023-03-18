package com.example.cs213_tuition_project_javafx;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Bridge class between JavaFX GUI and backend Java files.
 * Controls all events on the GUI
 * Controls all exception handling for GUI errors
 * @author Nikhil Agarwal, Hyeon Oh
 */
public class TuitionManagerController {

    /**
     *Index of State String data in studentList.txt
     */
    public static final int STATE_INDEX = 6;

    /**
     *Index of Abroad String data in studentList.txt
     */
    public static final int ABROAD_INDEX = 6;

    /**
     *Index of code data in studentList.txt
     */
    public static final int CODE_INDEX = 0;

    /**
     *Index of firstName String data in studentList.txt
     */
    public static final int FIRSTNAME_INDEX = 1;

    /**
     *Index of lastName String data in studentList.txt
     */
    public static final int LASTNAME_INDEX = 2;

    /**
     *Index of Date String data in studentList.txt
     */
    public static final int DATE_INDEX = 3;

    /**
     *Index of Major String data in studentList.txt
     */
    public static final int MAJOR_INDEX = 4;

    /**
     *Index of creditsCompleted String data in studentList.txt
     */
    public static final int CREDITS_INDEX = 5;

    /**
     * Console TextArea for print Tab
     */
    @FXML
    private TextArea fourthTabText;

    /**
     * Console TextArea for scholarship Tab
     */
    @FXML
    private TextArea thirdTabText;

    /**
     * Console TextArea for Roster Tab
     */
    @FXML
    private TextArea firstTabText;

    /**
     * Console TextArea for Enroll Tab
     */
    @FXML
    private TextArea secondTabText;

    /**
     * Calendar interface for scholarship tab
     */
    @FXML
    private DatePicker SdateBox;

    /**
     * Calendar interface for roster tab
     */
    @FXML
    private DatePicker dateField;

    /**
     * Calendar interface for enroll tab
     */
    @FXML
    private DatePicker EdateBox;

    /**
     * Text box for File name
     */
    @FXML
    private TextField textFileTextField;

    /**
     * Text box for credits enrolled;
     */
    @FXML
    private TextField creditsEnrolledBox;

    /**
     * Text Box for first Name on Roster tab
     */
    @FXML
    private TextField firstNameTextField;

    /**
     * Text Box for last Name on Roster tab
     */
    @FXML
    private TextField lastNameTextField;

    /**
     * Text Box for credits completed on Roster tab
     */
    @FXML
    private TextField creditsTextField;

    /**
     * Text Box for first name on enroll tab
     */
    @FXML
    private TextField EfirstNameBox;

    /**
     * Text Box for last name on enroll tab
     */
    @FXML
    private TextField ElastNameBox;

    /**
     * Text Box for first name on scholarship tab
     */
    @FXML
    private TextField SfirstName;

    /**
     * Text Box for last name on scholarship tab
     */
    @FXML
    private TextField SlastName;

    /**
     * Text Box for scholarship amount
     */
    @FXML
    private TextField ScholarshipAmount;

    /**
     * Toggle buttons for majors EE, CS, BAIT, ITI, MATH
     */
    @FXML
    private ToggleGroup majorGroup;

    /**
     * Toggle buttons for Resident, Non-Resident
     */
    @FXML
    private ToggleGroup ResidentGroupMain;

    /**
     * Toggle buttons for TriState, International, Neither
     */
    @FXML
    private ToggleGroup ResidentGroupSecondary;

    /**
     * Toggle buttons for CT, NY
     */
    @FXML
    private ToggleGroup ResidentGroupTertiary;

    /**
     * Toggle button for isStudyAbroad
     */
    @FXML
    private ToggleGroup ResidentGroupQuad;

    /**
     * Enrollment Object for GUI project
     */
    private Enrollment enrollment = new Enrollment();

    /**
     * Roster Object for GUI project
     */
    private Roster roster = new Roster();

    /**
     * Creates instance of class (NOT USED);
     */
    public TuitionManagerController(){}


    /**
     * Turns on the CT, NY toggle buttons when Tri-State button has been selected.
     */
    @FXML
    public void switchToTriState(){
        RadioButton CTButton = (RadioButton) ResidentGroupTertiary.getToggles().get(0);
        CTButton.setDisable(false);
        RadioButton NYButton = (RadioButton) ResidentGroupTertiary.getToggles().get(1);
        NYButton.setDisable(false);
        CTButton.setSelected(true);
        switchOffInternational();
    }

    /**
     * Turns off CT, NY toggle group and turns on isStudyAbroad button
     */
    @FXML
    public void switchToInternational(){
        switchOffTriState();
        RadioButton abroadButton = (RadioButton) ResidentGroupQuad.getToggles().get(0);
        abroadButton.setDisable(false);
    }

    /**
     * Turns off isStudyAbroad button when international button is deselected
     */
    @FXML
    public void switchOffInternational(){
        RadioButton abroadButton = (RadioButton) ResidentGroupQuad.getToggles().get(0);
        abroadButton.setDisable(true);
        abroadButton.setSelected(false);
    }

    /**
     * Turns off CT, NY toggle group
     */
    @FXML
    public void switchOffTriState(){
        RadioButton CTButton = (RadioButton) ResidentGroupTertiary.getToggles().get(0);
        CTButton.setDisable(true);
        RadioButton NYButton = (RadioButton) ResidentGroupTertiary.getToggles().get(1);
        NYButton.setDisable(true);
        CTButton.setSelected(false);
        NYButton.setSelected(false);
        switchOffInternational();
    }

    /**
     * Turns off all other buttons and deselected all other buttons except the Resident, NonResident toggle group
     */
    @FXML
    public void switchToResident(){
        RadioButton triStateButton = (RadioButton) ResidentGroupSecondary.getToggles().get(0);
        triStateButton.setDisable(true);
        RadioButton internationalButton = (RadioButton) ResidentGroupSecondary.getToggles().get(1);
        internationalButton.setDisable(true);
        RadioButton neitherButton = (RadioButton) ResidentGroupSecondary.getToggles().get(2);
        neitherButton.setDisable(true);
        RadioButton CTButton = (RadioButton) ResidentGroupTertiary.getToggles().get(0);
        CTButton.setDisable(true);
        RadioButton NYButton = (RadioButton) ResidentGroupTertiary.getToggles().get(1);
        NYButton.setDisable(true);
        CTButton.setSelected(false);
        NYButton.setSelected(false);
        triStateButton.setSelected(false);
        internationalButton.setSelected(false);
        neitherButton.setSelected(false);
        switchOffInternational();
    }

    /**
     * Turns on the TriState, International, and Neither toggle group
     */
    @FXML
    public void switchToNonResident(){
        RadioButton triStateButton = (RadioButton) ResidentGroupSecondary.getToggles().get(0);
        triStateButton.setDisable(false);
        RadioButton internationalButton = (RadioButton) ResidentGroupSecondary.getToggles().get(1);
        internationalButton.setDisable(false);
        RadioButton neitherButton = (RadioButton) ResidentGroupSecondary.getToggles().get(2);
        neitherButton.setDisable(false);
        neitherButton.setSelected(true);
    }

    /**
     * Adds student to roster when add button on roster page has been clicked.
     * Takes in data from text fields on first tab
     */
    @FXML
    public void onAddButtonClick(){
        String fName = firstNameTextField.getText();
        if(fName.equals("")){
            firstTabText.setText("Data Missing: First Name is Empty!");
            return;
        }
        String lName = lastNameTextField.getText();
        if(lName.equals("")){
            firstTabText.setText("Data Missing: Last Name is Empty");
            return;
        }
        String date = "Data Missing: No date selected!";
        try{ date = getDateFromDatePicker(dateField); }catch (Exception e){
            firstTabText.setText(date);
            return;
        }
        String major = getTextFromToggleGroup(majorGroup);
        String creditsCompletedString = creditsTextField.getText();
        int creditsCompleted = 0;
        try{ creditsCompleted = Integer.parseInt(creditsCompletedString); }catch (Exception e){
            if(creditsCompletedString.equals("")){
                firstTabText.setText("Data Missing: Credits Completed is Empty!");
            }else{
                firstTabText.setText("Credits Invalid: "+creditsCompletedString+" is not an integer");
            }
            return;
        }
        String typeMain = getTextFromToggleGroup(ResidentGroupMain);
        String typeSecondary = getTextFromToggleGroup(ResidentGroupSecondary);
        String typeTertiary = getTextFromToggleGroup(ResidentGroupTertiary);
        String typeQuad = getTextFromToggleGroup(ResidentGroupQuad);
        boolean isStudyAbroad = true;
        if(typeQuad==null){
            isStudyAbroad = false;
        }
        Date studentDate = new Date(date);
        if(!studentDate.isValidAge()){
            firstTabText.setText("Invalid Date: Student is younger than 16 years old");
            return;
        }
        Profile profile = new Profile(lName,fName,new Date(date));
        Major myMajor =grabMajor(major);
        if(typeMain.equals("Resident")){
            Resident resident = new Resident(profile,myMajor,creditsCompleted);
            if(!resident.isValidStudent()){
                firstTabText.setText("Credits Invalid: "+creditsCompletedString+" is negative");
                return;
            }
            processAdd(resident);
        }else{
            switch(typeSecondary){
                case "Tri-State":
                    TriState triState = new TriState(profile,myMajor,creditsCompleted,typeTertiary);
                    if(!triState.isValidStudent()){
                        firstTabText.setText("Credits Invalid: "+creditsCompletedString+" is negative");
                        return;
                    }
                    processAdd(triState);
                    break;
                case "International":
                    International international = new International(profile,myMajor,creditsCompleted,isStudyAbroad);
                    if(!international.isValidStudent()){
                        firstTabText.setText("Credits Invalid: "+creditsCompletedString+" is negative");
                        return;
                    }
                    processAdd(international);
                    break;
                case "Neither":
                    NonResident nonResident = new NonResident(profile,myMajor,creditsCompleted);
                    if(!nonResident.isValidStudent()){
                        firstTabText.setText("Credits Invalid: "+creditsCompletedString+" is negative");
                        return;
                    }
                    processAdd(nonResident);
            }
        }
        clearFields();
    }

    /**
     * Deletes student from roster when add button on roster page has been clicked.
     * Takes in data from text fields on first tab
     */
    @FXML
    public void onDeleteButtonClick(){
        try{
            String fName = firstNameTextField.getText();
            if(fName.equals("")){
                firstTabText.setText("Data Missing: First Name is Empty!");
                return;
            }
            String lName = lastNameTextField.getText();
            if(lName.equals("")){
                firstTabText.setText("Data Missing: Last Name is Empty!");
                return;
            }
            String date = "Data Missing: No date selected!";
            try{
                date = getDateFromDatePicker(dateField);
            }catch (Exception e){
                firstTabText.setText(date);
                return;
            }
            Date studentDate = new Date(date);
            if(!studentDate.isValidAge()){
                firstTabText.setText("Invalid Date: Student is younger than 16 years old.");
                return;
            }
            Profile profile = new Profile(lName,fName,new Date(date));
            Resident resident = new Resident(profile,Major.CS,0);
            processRemove(resident,roster);
            clearFields();
        }catch (Exception e){
            firstTabText.setText("error");
        }
    }

    /**
     * Changes students Major to a given major when change button on roster page has been clicked.
     * Takes in data from text fields on first tab
     */
    @FXML
    public void onChangeButtonClick(){
        String fName = firstNameTextField.getText();
        if(fName.equals("")){
            firstTabText.setText("Data Missing: First Name is Empty!");
            return;
        }
        String lName = lastNameTextField.getText();
        if(lName.equals("")){
            firstTabText.setText("Data Missing: Last Name is Empty!");
            return;
        }
        String date = "Data Missing: No date selected!";
        try{
            date = getDateFromDatePicker(dateField);
        }catch (Exception e){
            firstTabText.setText(date);
            return;
        }
        Date studentDate = new Date(date);
        if(!studentDate.isValidAge()){
            firstTabText.setText("Invalid Date: Student is younger than 16 years old.");
            return;
        }
        String major = getTextFromToggleGroup(majorGroup);
        Major newMajor = grabMajor(major);
        Profile profile = new Profile(lName,fName,new Date(date));
        Resident resident = new Resident(profile,newMajor,0);
        processChange(resident,newMajor);
        clearFields();
    }

    /**
     * Loads students into the roster from data given in a text file.
     * Takes in data from file name text field on first tab
     */
    @FXML
    public void onLoadFileButtonClick(){
        String filename = textFileTextField.getText();
        if(filename.equals("")){
            firstTabText.setText("Data Missing: Filename is Empty!");
            return;
        }
        processLS(filename);
        clearFields();
    }

    /**
     * Adds EnrollStudent object to enrollment when enroll button on enroll page has been clicked.
     * First checks to see if the student is in the roster.
     * Takes data from text fields in enroll tab.
     */
    @FXML
    public void onEnrollButtonClick(){
        String fName = EfirstNameBox.getText();
        if(fName.equals("")){
            secondTabText.setText("Data Missing: First Name is Empty!");
            return;
        }
        String lName = ElastNameBox.getText();
        if(lName.equals("")){
            secondTabText.setText("Data Missing: Last Name is Empty!");
            return;
        }
        String date = "Data Missing: No date selected!";
        try{
            date = getDateFromDatePicker(EdateBox);
        }catch (Exception e){
            secondTabText.setText(date);
            return;
        }
        String creditsEnrolledString = creditsEnrolledBox.getText();
        int creditsEnrolled = 0;
        try{
            creditsEnrolled = Integer.parseInt(creditsEnrolledString);
        }catch (Exception e){
            if(creditsEnrolledString.equals("")){
                secondTabText.setText("Data Missing: Credits Completed is Empty!");
            }else{
                secondTabText.setText("Credits Enrolled is not an integer");
            }
            return;
        }
        Profile profile = new Profile(lName,fName,new Date(date));
        EnrollStudent enrollStudent = new EnrollStudent(profile,creditsEnrolled);
        Resident resident = new Resident(profile,Major.CS,0);
        processEnroll(enrollStudent,resident,creditsEnrolled);
        clearEnrollmentFields();
    }

    /**
     * Drops EnrollStudent object from enrollment when enroll button on enroll page has been clicked.
     * First checks to see if the student is in the enrollment.
     * Takes data from text fields in enroll tab.
     */
    @FXML
    public void onDropButtonClick(){
        String fName = EfirstNameBox.getText();
        if(fName.equals("")){
            secondTabText.setText("Data Missing: First Name is Empty!");
            return;
        }
        String lName = ElastNameBox.getText();
        if(lName.equals("")){
            secondTabText.setText("Data Missing: Last Name is Empty!");
            return;
        }
        String date = "Data Missing: No date selected!";
        try{
            date = getDateFromDatePicker(EdateBox);
        }catch (Exception e){
            secondTabText.setText(date);
            return;
        }
        Profile profile = new Profile(lName,fName,new Date(date));
        EnrollStudent enrollStudent = new EnrollStudent(profile,0);
        processDrop(enrollStudent);
        clearEnrollmentFields();
    }

    /**
     * Method to handle event that scholarship update button is clicked on scholarship tab.
     * Takes in data from text fields on scholarship tab.
     * Adds scholarship to resident objects (Checks to see if student object is a resident)
     */
    @FXML
    public void onUpdateScholarshipButtonClick(){
        String fName = SfirstName.getText();
        if(fName.equals("")){
            thirdTabText.setText("Data Missing: First Name is Empty!");
            return;
        }
        String lName = SlastName.getText();
        if(lName.equals("")){
            thirdTabText.setText("Data Missing: Last Name is Empty!");
            return;
        }
        String date = "Data Missing: No date selected!";
        try{
            date = getDateFromDatePicker(SdateBox);
        }catch (Exception e){
            thirdTabText.setText(date);
            return;
        }
        String scholarshipString = ScholarshipAmount.getText();
        int scholarship = 0;
        try{
            scholarship = Integer.parseInt(scholarshipString);
        }catch (Exception e){
            if(scholarshipString.equals("")){
                thirdTabText.setText("Data Missing: Scholarship amount is Empty!");
            }else{
                thirdTabText.setText(scholarshipString +": Scholarship Amount is not an integer");
            }
            return;
        }
        Profile profile = new Profile(lName,fName,new Date(date));
        EnrollStudent enrollStudent = new EnrollStudent(profile,0);
        Resident resident = new Resident(profile,Major.CS,0);
        processScholarship(scholarship,enrollStudent,resident);
    }

    /**
     * Prints roster sorted by Profile, and displays it in console
     * Event handler for (Roster --> Print By Profile) button
     */
    @FXML
    public void onNormalPrintButtonClick(){
        try{
            String printOutput = roster.print();
            printOutput = "** Student roster sorted by last name, first name, DOB **\n\n" + printOutput + "\n* End of Roster *";
            fourthTabText.setText(printOutput);
        }catch (Exception e){
            fourthTabText.setText("Roster is Empty!");
        }
    }

    /**
     * Prints roster sorted by School and Major, and displays it in console
     * Event handler for (Roster --> Print By School And Major) button
     */
    @FXML
    public void onMajorPrintButtonClick(){
        try{
            String printOutput = roster.printByMajor();
            printOutput = "** Student roster sorted by school, major **\n\n"+printOutput+"\n* End of Roster *";
            fourthTabText.setText(printOutput);
        }catch (Exception e){
            fourthTabText.setText("Roster is Empty!");
        }
    }

    /**
     * Prints roster sorted by Standing, and displays it in console
     * Event handler for (Roster --> Print By Standing) button
     */
    @FXML
    public void onStandingPrintButtonClick(){
        try{
            String printOutput = roster.printByStanding();
            printOutput = "** Student roster sorted by standing **\n\n"+printOutput+"\n* End of Roster *";
            fourthTabText.setText(printOutput);
        }catch (Exception e){
            fourthTabText.setText("Roster is Empty!");
        }
    }

    /**
     * Event handler function to print student currently in the roster,
     * that all attend RBS (Rutgers business school)
     * Displays output in console on print tab.
     */
    @FXML
    public void onRBSClick(){
        if(roster.isEmpty()){
            fourthTabText.setText("Roster is Empty! No students in any school.");
            return;
        }
        Roster schoolRoster = new Roster();
        roster.filterBySchool("RBS",schoolRoster);
        try{
            String printOutput = schoolRoster.print();
            printOutput = "** Students in RBS **\n\n" + printOutput + "\n* End of List *";
            fourthTabText.setText(printOutput);
        }catch (Exception e){
            fourthTabText.setText("No students in RBS!");
        }
    }

    /**
     * Event handler function to print student currently in the roster,
     * that all attend SOE (School of Engineering)
     * Displays output in console on print tab.
     */
    @FXML
    public void onSOEClick(){
        if(roster.isEmpty()){
            fourthTabText.setText("Roster is Empty! No students in any school.");
            return;
        }
        Roster schoolRoster = new Roster();
        roster.filterBySchool("SOE",schoolRoster);
        try{
            String printOutput = schoolRoster.print();
            printOutput = "** Students in SOE **\n\n" + printOutput + "\n* End of List *";
            fourthTabText.setText(printOutput);
        }catch (Exception e){
            fourthTabText.setText("No students in SOE!");
        }
    }

    /**
     * Event handler function to print student currently in the roster,
     * that all attend SAS (School of Ars and Sciences)
     * Displays output in console on print tab.
     */
    @FXML
    public void onSASCLick(){
        if(roster.isEmpty()){
            fourthTabText.setText("Roster is Empty! No students in any school.");
            return;
        }
        Roster schoolRoster = new Roster();
        roster.filterBySchool("SAS",schoolRoster);
        try{
            String printOutput = schoolRoster.print();
            printOutput = "** Students in SAS **\n\n" + printOutput + "\n* End of List *";
            fourthTabText.setText(printOutput);
        }catch (Exception e){
            fourthTabText.setText("No students in SAS!");
        }
    }

    /**
     * Event handler function to print student currently in the roster,
     * that all attend SC and I (School of Communication and Information)
     * Displays output in console on print tab.
     */
    @FXML
    public void onSCIClick(){
        if(roster.isEmpty()){
            fourthTabText.setText("Roster is Empty! No students in any school.");
            return;
        }
        Roster schoolRoster = new Roster();
        roster.filterBySchool("SC&I",schoolRoster);
        try{
            String printOutput = schoolRoster.print();
            printOutput = "** Students in SC&I **\n\n" + printOutput + "\n* End of List *";
            fourthTabText.setText(printOutput);
        }catch (Exception e){
            fourthTabText.setText("No students in SC&I!");
        }
    }

    /**
     * Event Handler to display enrollment in console.
     * Event triggers when (Enrollment + Print Enrolled Student) button is clicked
     */
    @FXML
    public void onEnrollmentPrintClick(){
        try{
            String printOutput = enrollment.print();
            if(printOutput.equals("")){
                fourthTabText.setText("Enrollment is Empty!");
                return;
            }
            printOutput = "** Enrollment **\n\n" + printOutput + "\n* End of Enrollment *";
            fourthTabText.setText(printOutput);
        }catch (Exception e){
            fourthTabText.setText("Enrollment is Empty!");
        }
    }

    /**
     * Event Handler to display Tuition Due for student enrolled this semester, in the console.
     * Event triggers when (Enrollment + Print Tuition Dues) button is clicked
     */
    @FXML
    public void onTuitionDueClick(){
        try{
            String printOutput = enrollment.printTuition(roster);
            if(printOutput.equals("")){
                fourthTabText.setText("Enrollment is Empty!");
                return;
            }
            printOutput = "** Tuition Due **\n\n" + printOutput + "\n* End of Tuition Due *";
            fourthTabText.setText(printOutput);
        }catch (Exception e){
            fourthTabText.setText("Enrollment is Empty!");
        }
    }

    /**
     * Event Handler to run semester end functions, and display the results in the console.
     * Event triggers when (Enrollment + Semester End) button is clicked
     * The program does not terminate after this. Instead, enrollment is cleared and a new semester may begin.
     */
    @FXML
    public void onSemesterEndClick(){
        try{
            if(enrollment.isEmpty()){
                fourthTabText.setText("Enrollment is Empty!");
                return;
            }
            processSemesterEnd();
        }catch (Exception e){
            fourthTabText.setText("Enrollment is Empty!");
        }
    }

    /**
     * Adds creditsEnrolled to credits completed and then prints the students that are eligible to graduate.
     */
    private void processSemesterEnd(){
        String output = "";
        roster.updateCreditsCompleted(enrollment);
        output += "Credit completed has been updated.\n\n";
        output += "** list of students eligible for graduation **\n\n";
        String gradOutput = roster.printGraduated();
        if(gradOutput.equals("")){
            fourthTabText.setText("Credit completed has been updated.\n\nNo eligible students for graduation.");
        }
        output += gradOutput;
        output += "\n* end of list *";
        enrollment = new Enrollment();
        fourthTabText.setText(output);
    }

    /**
     * Clears text fields in scholarship tab after submission
     */
    private void clearFieldsScholarship(){
        SfirstName.setText("");
        SlastName.setText("");
        SdateBox.setValue(null);
        ScholarshipAmount.setText("");
    }

    /**
     * Adds scholarship amount to student if they are enrolled, and they are eligible for a scholarship.
     * @param scholarship amount of scholarship we want to add to student.
     * @param enrollStudentOriginal enrollStudent object that we want to search for in our enrollment.
     * @param student student object that we want ot add scholarship too. Student must be a resident.
     */
    private void processScholarship(int scholarship , EnrollStudent enrollStudentOriginal, Student student){
        if(roster.contains(student)){
            Student temp = roster.getStudent(enrollStudentOriginal.getProfile());
            EnrollStudent enrollStudent = enrollment.getEnrollStudent(enrollStudentOriginal);
            String type = temp.getType();
            if(!type.equals("(Resident)")){
                thirdTabText.setText(enrollStudent.getProfile()+" "+type+" is not eligible for the scholarship.");
                clearFieldsScholarship();
                return;
            }
            try{ student.isResident(); }catch(Exception e){}
            if(scholarship<=0 || scholarship>10000){
                thirdTabText.setText(scholarship+": invalid amount.");
                return;
            }
            if(enrollStudent.getCreditsEnrolled()<12){
                thirdTabText.setText(enrollStudent.getProfile()+" part time student is not eligible for the scholarship.");
                clearFieldsScholarship();
                return;
            }
            Resident ourStudent = (Resident) temp;
            ourStudent.setScholarship(scholarship);
            thirdTabText.setText(enrollStudent.getProfile()+": scholarship amount updated.");
            clearFieldsScholarship();
        }else{
            thirdTabText.setText(enrollStudentOriginal.getProfile()+" is not in the roster.");
            clearFieldsScholarship();
        }
    }

    /**
     * Drops a student from enrollment.
     * @param enrollStudent enrollstudent object to remove student from enrollment.
     */
    private void processDrop(EnrollStudent enrollStudent){
        if(!enrollment.contains(enrollStudent)){
            secondTabText.setText(enrollStudent.getProfile()+" is not enrolled.");
            return;
        }
        enrollment.remove(enrollStudent);
        secondTabText.setText(enrollStudent.getProfile()+" dropped.");
    }

    /**
     * Enrolls a student if All the data that is provided in the input command line is valid.
     * Also checks to see if roster contains student we are trying to enroll. If not the method will terminate.
     * @param enrollStudent enrollStudent object
     * @param student Student object that corresponds to same student that is enrolling
     * @param creditsEnrolled number of credits student is going to take this semester
     */
    private void processEnroll(EnrollStudent enrollStudent, Student student, int creditsEnrolled){
        if(!roster.contains(student)){
            secondTabText.setText("Cannot enroll: "+ student.getProfile() +" is not in the roster.");
            return;
        }
        if(roster.getStudent(student.getProfile()).isValid(creditsEnrolled)){
            if(enrollment.contains(enrollStudent)){
                enrollment.getEnrollStudent(enrollStudent).changeCredits(creditsEnrolled);
                secondTabText.setText(student.getProfile() + " enrolled "+creditsEnrolled+" credits");
            }else{
                enrollment.add(enrollStudent);
                secondTabText.setText(student.getProfile() + " enrolled "+creditsEnrolled+" credits");
            }
        }else{
            secondTabText.setText(roster.getStudent(student.getProfile()).getType()+" "+creditsEnrolled+": invalid credit hours.");
        }
    }

    /**
     * Change the major of a student object
     * @param student Student object that we want to edit
     * @param newMajor the major object that we want to switch too in our student.
     */
    private void processChange(Student student, Major newMajor){
        boolean changed = roster.change(student,newMajor);
        if(changed){
            firstTabText.setText(student.getProfile() + " major changed to " + newMajor);
        }else{
            firstTabText.setText(student.getProfile() + " is not in the roster.");
        }
    }

    /**
     * Removes a student from the given roster, if the student is present in the roster.
     * First checks to see if roster contains the student.
     * @param student Student object that we want to remove
     * @param roster Roster that we want to remove the student from
     */
    private void processRemove(Student student, Roster roster){
        if(roster.isEmpty()){
            firstTabText.setText("No Students to remove in roster.");
            return;
        }
        boolean removed = roster.remove(student);
        if(removed){
            firstTabText.setText(student.getProfile() + " removed from the roster.");
            return;
        }
        firstTabText.setText(student.getProfile() + " is not in the roster.");
    }

    /**
     * Method to check a students validity before adding to the roster.
     * Process method for adding to roster.
     * @param student Student object we want to add to the roster.
     */
    private void processAdd(Student student){
        if(roster.contains(student)){
            firstTabText.setText(student.getProfile()+" is already in the roster.");
        }else{
            roster.add(student);
            firstTabText.setText(student.getProfile()+" added to the roster.");
        }
    }

    /**
     * Clear fields on the roster tab after submission
     */
    private void clearFields(){
        firstNameTextField.setText("");
        lastNameTextField.setText("");
        dateField.setValue(null);
        creditsTextField.setText("");
        textFileTextField.setText("");
    }

    /**
     * Clear fields on the enroll tab after submission
     */
    private void clearEnrollmentFields(){
        EfirstNameBox.setText("");
        ElastNameBox.setText("");
        EdateBox.setValue(null);
        creditsEnrolledBox.setText("");
    }

    /**
     * Method to get String date in format (MM/DD/YYYY) from Calendar interface
     * @param dateField FXML calendar interface from GUI
     * @return String date of format MM/DD/YYYY
     */
    private String getDateFromDatePicker(DatePicker dateField){
        LocalDate date = dateField.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return date.format(formatter);
    }

    /**
     * Method to get the text value of a toggle button.
     * @param toggleGroup The group of buttons that are active currently in single category (Ex: Resident, NonResident)
     * @return String text value of button (Ex: Resident button selected --> "Resident")
     */
    private String getTextFromToggleGroup(ToggleGroup toggleGroup){
        try{
            RadioButton radioButton = (RadioButton) toggleGroup.getSelectedToggle();
            return radioButton.getText();
        }catch (Exception e){
            return null;
        }
    }

    /**
     * Method to get a major object form a string
     * @param major String major
     * @return A major object that corresponds to the String provided
     *         (Ex: "CS" --> Major.CS)
     */
    private Major grabMajor(String major){
        switch(major){
            case "BAIT": return Major.BAIT;
            case "CS": return Major.CS;
            case "MATH": return Major.MATH;
            case "ITI": return Major.ITI;
            case "EE": return Major.EE;
        }
        return null;
    }

    /**
     * This method processes a txt. File of student objects and adds them to our roster.
     * @param filename file that holds all student data.
     */
    private void processLS(String filename){
        try{
            Scanner scanner = new Scanner(new File(filename));
            while(scanner.hasNextLine()){
                String[] tokens = processLineLS(scanner.nextLine());
                switch(tokens[CODE_INDEX]){
                    case "R":
                        Resident resident = makeResident(tokens[FIRSTNAME_INDEX],tokens[LASTNAME_INDEX],tokens[DATE_INDEX],tokens[MAJOR_INDEX].toUpperCase(),tokens[CREDITS_INDEX]);
                        roster.add(resident);
                        break;
                    case "I":
                        String abroad = "";
                        International international = null;
                        try{
                            abroad = tokens[ABROAD_INDEX];
                            international = makeInternational(tokens[FIRSTNAME_INDEX],tokens[LASTNAME_INDEX],tokens[DATE_INDEX],tokens[MAJOR_INDEX].toUpperCase(),tokens[CREDITS_INDEX],abroad);
                        }catch (Exception e){
                            international = makeInternational(tokens[FIRSTNAME_INDEX],tokens[LASTNAME_INDEX],tokens[DATE_INDEX],tokens[MAJOR_INDEX].toUpperCase(),tokens[CREDITS_INDEX]);
                        }
                        roster.add(international);
                        break;
                    case "T":
                        TriState triState = makeTriState(tokens[FIRSTNAME_INDEX],tokens[LASTNAME_INDEX],tokens[DATE_INDEX],tokens[MAJOR_INDEX].toUpperCase(),tokens[CREDITS_INDEX],tokens[STATE_INDEX]);
                        roster.add(triState);
                        break;
                    case "N":
                        NonResident nonResident = makeNonResident(tokens[FIRSTNAME_INDEX],tokens[LASTNAME_INDEX],tokens[DATE_INDEX],tokens[MAJOR_INDEX].toUpperCase(),tokens[CREDITS_INDEX]);
                        roster.add(nonResident);
                }
            }
            firstTabText.setText("Students loaded to the roster.");
        }catch (Exception e){
            firstTabText.setText("Error: File not Found\nPlease enter valid textfile (Ex: studentList.txt)");
        }
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
     * Makes a Non-Resident student object.
     * @param firstName String first name
     * @param lastName String last name
     * @param date Date object
     * @param major Major enum
     * @param creditCompleted integer credits student has completed
     * @return Non-Resident object that we have created.
     */
    private NonResident makeNonResident(String firstName, String lastName, String date, String major, String creditCompleted){
        Major nonResMajor = grabMajor(major);
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
        Major triMajor = grabMajor(major);
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
        Major resMajor = grabMajor(major);
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
        Major iMajor = grabMajor(major);
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
        Major iMajor = grabMajor(major);
        International international = new International(new Profile(lastName,firstName,new Date(date)),iMajor,Integer.parseInt(creditCompleted));
        return international;
    }


}