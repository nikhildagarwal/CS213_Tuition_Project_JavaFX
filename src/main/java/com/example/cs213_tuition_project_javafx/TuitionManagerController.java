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
    private TextField textFileTextField;
    @FXML
    private TextArea firstTabText;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private DatePicker dateField;
    @FXML
    private TextField creditsTextField;
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
        System.out.println("rosterPrint");
        System.out.println(roster.print());
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

    public void onLoadFileButtonClick(){
        String filename = textFileTextField.getText();
        if(filename.equals("")){
            firstTabText.setText("Data Missing: Filename is Empty!");
            return;
        }
        processLS(filename);
        clearFields();
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