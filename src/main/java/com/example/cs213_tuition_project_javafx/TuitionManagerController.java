package com.example.cs213_tuition_project_javafx;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class TuitionManagerController {

    public static final int STATE_INDEX = 6;
    public static final int ABROAD_INDEX = 6;
    public static final int CODE_INDEX = 0;
    public static final int FIRSTNAME_INDEX = 1;
    public static final int LASTNAME_INDEX = 2;
    public static final int DATE_INDEX = 3;
    public static final int MAJOR_INDEX = 4;
    public static final int CREDITS_INDEX = 5;

    @FXML
    private TextArea thirdTabText;
    @FXML
    private TextArea firstTabText;
    @FXML
    private TextArea secondTabText;
    @FXML
    private DatePicker SdateBox;
    @FXML
    private DatePicker dateField;
    @FXML
    private DatePicker EdateBox;
    @FXML
    private TextField textFileTextField;
    @FXML
    private TextField creditsEnrolledBox;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField creditsTextField;
    @FXML
    private TextField EfirstNameBox;
    @FXML
    private TextField ElastNameBox;
    @FXML
    private TextField SfirstName;
    @FXML
    private TextField SlastName;
    @FXML
    private TextField ScholarshipAmount;
    @FXML
    private ToggleGroup majorGroup;
    @FXML
    private ToggleGroup ResidentGroupMain;
    @FXML
    private ToggleGroup ResidentGroupSecondary;
    @FXML
    private ToggleGroup ResidentGroupTertiary;
    @FXML
    private ToggleGroup ResidentGroupQuad;

    private Enrollment enrollment = new Enrollment();
    private Roster roster = new Roster();

    @FXML
    public void switchToTriState(){
        RadioButton CTButton = (RadioButton) ResidentGroupTertiary.getToggles().get(0);
        CTButton.setDisable(false);
        RadioButton NYButton = (RadioButton) ResidentGroupTertiary.getToggles().get(1);
        NYButton.setDisable(false);
        CTButton.setSelected(true);
        switchOffInternational();
    }

    @FXML
    public void switchToInternational(){
        switchOffTriState();
        RadioButton abroadButton = (RadioButton) ResidentGroupQuad.getToggles().get(0);
        abroadButton.setDisable(false);
    }

    @FXML
    public void switchOffInternational(){
        RadioButton abroadButton = (RadioButton) ResidentGroupQuad.getToggles().get(0);
        abroadButton.setDisable(true);
        abroadButton.setSelected(false);
    }

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
        try{
            date = getDateFromDatePicker(dateField);
        }catch (Exception e){
            firstTabText.setText(date);
            return;
        }
        String major = getTextFromToggleGroup(majorGroup);
        String creditsCompletedString = creditsTextField.getText();
        int creditsCompleted = 0;
        try{
            creditsCompleted = Integer.parseInt(creditsCompletedString);
        }catch (Exception e){
            if(creditsCompletedString.equals("")){
                firstTabText.setText("Data Missing: Credits Completed is Empty!");
            }else{
                firstTabText.setText("Credits Completed is not an integer");
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
            processAdd(resident);
        }else{
            switch(typeSecondary){
                case "Tri-State":
                    TriState triState = new TriState(profile,myMajor,creditsCompleted,typeTertiary);
                    processAdd(triState);
                    break;
                case "International":
                    International international = new International(profile,myMajor,creditsCompleted,isStudyAbroad);
                    processAdd(international);
                    break;
                case "Neither":
                    NonResident nonResident = new NonResident(profile,myMajor,creditsCompleted);
                    processAdd(nonResident);
            }
        }
        clearFields();
    }

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
        clearFieldsScholarship();
    }

    private void clearFieldsScholarship(){
        SfirstName.setText("");
        SlastName.setText("");
        SdateBox.setValue(null);
        ScholarshipAmount.setText("");
    }

    /**
     * Adds scholarship amount to student if they are enrolled, and they are eligible for a scholarship.
     * @param scholarship amount of scholarship we want to add to student.
     * @param enrollStudent enrollStudent object that we want to search for in our enrollment.
     * @param student student object that we want ot add scholarship too. Student must be a resident.
     */
    private void processScholarship(int scholarship , EnrollStudent enrollStudent, Student student){
        if(roster.contains(student)){
            Student temp = roster.getStudent(enrollStudent.getProfile());
            String type = temp.getType();
            if(!type.equals("(Resident)")){
                thirdTabText.setText(enrollStudent.getProfile()+" "+type+" is not eligible for the scholarship.");
                return;
            }
            if(scholarship<=0 || scholarship>10000){
                thirdTabText.setText(scholarship+": invalid amount.");
                return;
            }
            if(enrollStudent.getCreditsEnrolled()<12){
                thirdTabText.setText(enrollStudent.getProfile()+" part time student is not eligible for the scholarship.");
                return;
            }
            Resident ourStudent = (Resident) temp;
            ourStudent.setScholarship(scholarship);
            thirdTabText.setText(enrollStudent.getProfile()+": scholarship amount updated.");

        }else{
            thirdTabText.setText(enrollStudent.getProfile()+" is not in the roster.");
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

    private void processChange(Student student, Major newMajor){
        boolean changed = roster.change(student,newMajor);
        if(changed){
            firstTabText.setText(student.getProfile() + " major changed to " + newMajor);
        }else{
            firstTabText.setText(student.getProfile() + " is not in the roster.");
        }
    }

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

    private void processAdd(Student student){
        if(roster.contains(student)){
            firstTabText.setText(student.getProfile()+" is already in the roster.");
        }else{
            roster.add(student);
            firstTabText.setText(student.getProfile()+" added to the roster.");
        }
    }

    private void clearFields(){
        firstNameTextField.setText("");
        lastNameTextField.setText("");
        dateField.setValue(null);
        creditsTextField.setText("");
        textFileTextField.setText("");
    }

    private void clearEnrollmentFields(){
        EfirstNameBox.setText("");
        ElastNameBox.setText("");
        EdateBox.setValue(null);
        creditsEnrolledBox.setText("");
    }

    private String getDateFromDatePicker(DatePicker dateField){
        LocalDate date = dateField.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return date.format(formatter);
    }

    private String getTextFromToggleGroup(ToggleGroup toggleGroup){
        try{
            RadioButton radioButton = (RadioButton) toggleGroup.getSelectedToggle();
            return radioButton.getText();
        }catch (Exception e){
            return null;
        }
    }

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