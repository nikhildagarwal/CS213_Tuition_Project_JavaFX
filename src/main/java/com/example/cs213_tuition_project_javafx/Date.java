/**
 * Contains all classes for student object and extended student objects
 */
package com.example.cs213_tuition_project_javafx;
import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * Implements our date object as a comparable object.
 * Consists of three integer components: year, month, day.
 * Overrides for equals, toString, and compareTo.
 * Implements methods to check if dates are valid, or year is a LeapYear.
 * @author Nikhil Agarwal, Hyeon Oh
 */
public class Date implements Comparable<Date>{

    /**
     * year of date (yyyy)
     */
    private int year;

    /**
     * month of date (mm)
     */
    private int month;

    /**
     * day of date (dd)
     */
    private int day;


    /**
     * Array that holds the number of days in each month (Starting from January @index 0)
     * index1 holds value for nonLeapYear february.
     */
    private static final int[] daysInMonth = {31,28,31,30,31,30,31,31,30,31,30,31};

    /**
     * every 4 years
     */
    private static final int QUADRENNIAL = 4;

    /**
     * every 100 years
     */
    private static final int CENTENNIAL = 100;

    /**
     * every 400 years
     */
    private static final int QUATERCENTENNIAL = 400;

    /**
     * indicates january is the first month
     */
    private static final int JANUARY = 1;

    /**
     * indicates february is the second month
     */
    private static final int FEBRUARY = 2;

    /**
     * indicates december is the twelfth month (last month)
     */
    private static final int DECEMBER = 12;

    /**
     * the index diff from actually position (java is 0-indexed, the months are 1-indexed)
     */
    private static final int INDEX_DIFF = 1;

    /**
     * first day of the month is always 1
     */
    private static final int FIRST_DAY = 1;

    /**
     * number of days in feb for a leap year
     */
    private static final int LEAP_YEAR_DAYS = 29;

    /**
     * minimum year a person can be born in to still be considered a student
     */
    private static final int MIN_YEAR = 1900;

    /**
     * minimum age for student to be added to roster
     */
    private static final int MIN_AGE = 16;


    /**
     * Default constructor for Date object.
     * Initializes object to current day.
     */
    public Date(){
        Calendar calendar = Calendar.getInstance();
        this.year = calendar.get(Calendar.YEAR);
        this.day = calendar.get(Calendar.DATE);
        this.month = calendar.get(Calendar.MONTH) + INDEX_DIFF;
    }

    /**
     * Secondary constructor for Date object that accepts string input of format: mm/dd/yyyy
     * @param date date in format mm/dd/yyyy
     */
    public Date(String date){
        StringTokenizer dateTokens = new StringTokenizer(date,"/");
        this.month = Integer.parseInt(dateTokens.nextToken());
        this.day = Integer.parseInt(dateTokens.nextToken());
        this.year = Integer.parseInt(dateTokens.nextToken());
    }

    /**
     * Override of equals method from object class in Java.
     * Compares each int value in Date.
     * @param obj object to be checked with.
     * @return true if the Date objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj){
        Date date = (Date) obj;
        return day == date.day && year == date.year && month == date.month;
    }

    /**
     * Override of toString method in object class in Java.
     * Prints each int value of Date, with slashes in between.
     * Method used by System.out.println to print our object.
     * @return date as string.
     */
    @Override
    public String toString(){
        String slash = "/";
        return month + slash + day + slash + year;
    }

    /**
     * Override of compareTo method in the Java comparable class.
     * @param date the object to be compared.
     * @return -1  if older date, 0 if same date, 1 if more recent date.
     */
    @Override
    public int compareTo(Date date){
        int compareYear = Integer.compare(year,date.year);
        if(compareYear == 0){
            int compareMonth = Integer.compare(month,date.month);
            if(compareMonth == 0){
                return Integer.compare(day,date.day);
            }else{
                return compareMonth;
            }
        }else{
            return compareYear;
        }
    }

    /**
     * Checks if date is a valid Calendar date.
     * @return true if date is a calendar date, false otherwise.
     */
    public boolean isValid(){
        boolean dayCheck = false;
        if(validMonth(month)){
            if(month == FEBRUARY){
                boolean leapYear = isLeapYear(year);
                if(leapYear){
                    dayCheck = validDay(day,LEAP_YEAR_DAYS);
                }else{
                    int daysInFeb = daysInMonth[FEBRUARY-INDEX_DIFF];
                    dayCheck = validDay(day,daysInFeb);
                }
            }else{
                int daysInCurrentMonth = daysInMonth[month - INDEX_DIFF];
                dayCheck = validDay(day,daysInCurrentMonth);
            }
        }else{
            return false;
        }
        return dayCheck && validYear(year) && validMonth(month);
    }

    /**
     * Checks to see if there is at least 16 years time between the date object and today.
     * @return true if there is at least 16 years difference, false otherwise.
     */
    public boolean isValidAge(){
        Date today = new Date();
        int yearDiff = today.year - year;
        if(yearDiff > MIN_AGE){
            return true;
        }else if(yearDiff < MIN_AGE){
            return false;
        }else{
            int monthDiff = today.month-month;
            if(monthDiff > 0){
                return true;
            }else if(monthDiff < 0){
                return false;
            }else{
                int dayDiff = today.day - day;
                if(dayDiff < 0){
                    return false;
                }else{
                    return true;
                }
            }
        }
    }

    /**
     * Checks if a day value is within the total number of days in a given month.
     * @param day day to be checked.
     * @param totalDays total number of days in the specific month of day given above.
     * @return true if the day is within the number of days in the month, false otherwise.
     */
    private boolean validDay(int day,int totalDays){
        if(day >= FIRST_DAY && day <= totalDays){
            return true;
        }
        return false;
    }

    /**
     * Checks if a year is a Leap year or not.
     * @param year year to be checked.
     * @return true if leap year, false otherwise.
     */
    private boolean isLeapYear(int year){
        if(year % QUADRENNIAL == 0){
            if(year % CENTENNIAL == 0){
                if(year % QUATERCENTENNIAL == 0){
                    return true;
                }
            }else{
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if given year is a valid year before today, and after 1900.
     * @param year year to be checked.
     * @return true if year is valid, false otherwise.
     */
    private boolean validYear(int year){
        Calendar calendar = Calendar.getInstance();
        int currYear = calendar.get(Calendar.YEAR);
        if(currYear >= year && year > MIN_YEAR){
            return true;
        }
        return false;
    }

    /**
     * Checks if given month is between January and December (inclusive).
     * @param month month to be checked.
     * @return return true if month is valid, false otherwise.
     */
    private boolean validMonth(int month){
        if(month >= JANUARY && month <= DECEMBER){
            return true;
        }
        return false;
    }
}