package seedu.taskell.model.task;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.taskell.commons.exceptions.IllegalValueException;

import java.util.Locale;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;

//@@author A0139257X
/**
 * Represents a Task's taskDate in the task manager.
 * Guarantees: is valid as declared in {@link #isValidDate(String)}
 */
public class TaskDate {
    public static final int JANUARY = 1;
    public static final int FEBRUARY = 2;
    public static final int MARCH = 3;
    public static final int APRIL = 4;
    public static final int MAY = 5;
    public static final int JUNE = 6;
    public static final int JULY = 7;
    public static final int AUGUST = 8;
    public static final int SEPTEMBER = 9;
    public static final int OCTOBER = 10;
    public static final int NOVEMBER = 11;
    public static final int DECEMBER = 12;

    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;
    public static final int SUNDAY = 7;

    public static final int NUM_DAYS_PER_DAY = 1;
    public static final int NUM_DAYS_IN_A_WEEK = 7;
    public static final int NUM_DAYS_IN_A_MONTH = 30;
    public static final int NUM_MONTHS_IN_A_YEAR = 12;
    
    public static final int NOT_A_VALID_MONTH = 0;
    public static final int NOT_A_VALID_DAY_OF_THE_WEEK = 0;

    public static final int FIRST_DAY_OF_THE_MONTH = 1;
    
    public static final String DATE_DELIMITER = " .-/";
    
    public static final String DEFAULT_DATE = getDefaultDate();

    public static final Pattern TASK_DATE_ARGS_FORMAT = Pattern
            .compile("(?<day>(3[0-1]|2[0-9]|1[0-9]|[1-9]))" + "(-)(?<month>(1[0-2]|[1-9]))" + "(-)(?<year>([0-9]{4}))");
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy");
    private static final DateTimeFormatter standardFormat = DateTimeFormatter.ofPattern("d-MM-yyyy");
    SimpleDateFormat sdf = new SimpleDateFormat("d M yyyy");
    
    public static final String MESSAGE_TASK_DATE_CONSTRAINTS =
            "Task dates should be separated by '-' or '.' or '/'"
            + "\nSpelling of month should be in full or 3-letters"
            + "\nYear should only be 4-digits";

    public String taskDate;
    
    /**
     * Initialize the different fields given taskDate in the format of 
     * DAY-MONTH-YEAR, separated by DATE_DELIMITER
     * @throws IllegalValueException 
     */
    public TaskDate(String dateToAdd) throws IllegalValueException {
        if (isValidFullDate(dateToAdd)) {
            setDateGivenFullDate(dateToAdd);
        } else if (isValidDayAndMonth(dateToAdd)) {
            setDateGivenDayMonth(dateToAdd);
        } else if (isValidMonthAndYear(dateToAdd)) {
            setDateGivenMonthYear(dateToAdd);
        } else if (isValidMonth(dateToAdd)) {
            setDateGivenMonth(dateToAdd);
        } else if (isValidDayOfWeek(dateToAdd)) {
            setDateGivenDayNameOfWeek(dateToAdd);
        } else if (isValidToday(dateToAdd)) {
            setDateGivenToday(dateToAdd);
        } else if (isValidTomorrow(dateToAdd)) {
            setDateGivenTomorrow(dateToAdd);
        } else {
            throw new IllegalValueException(MESSAGE_TASK_DATE_CONSTRAINTS);
        }
    }
    
    /**
     * Extract the different fields from taskDate having the format of
     * DAY-MONTH-YEAR, separated by DATE_DELIMITER
     * @throws DateTimeException
     * @throws IllegalValueException 
     */
    private void setDateGivenFullDate(String dateToConvert) throws DateTimeException, IllegalValueException {
        StringTokenizer st = new StringTokenizer(dateToConvert, DATE_DELIMITER);
        String[] tokenArr = new String[3];
        int i = 0;
        while (st.hasMoreTokens()) {
            tokenArr[i] = st.nextToken();
            i++;
        }

        int day = Integer.valueOf(tokenArr[0]);
        String monthStr = tokenArr[1];
        int month;
        try {
            month = Integer.valueOf(tokenArr[1]);
        } catch (NumberFormatException nfe) {
            month = convertMonthIntoInteger(monthStr);
        }
        int year = Integer.valueOf(tokenArr[2]);

        try {
            setDate(day, month, year);
            getYear();
        } catch (IllegalValueException ive) {
            throw ive;
        }
    }
    
    private void setDateGivenDayMonth(String dateToConvert) throws IllegalValueException {
        StringTokenizer st = new StringTokenizer(dateToConvert, DATE_DELIMITER);
        String[] tokenArr = new String[3];
        int i = 0;
        while (st.hasMoreTokens()) {
            tokenArr[i] = st.nextToken();
            i++;
        }

        int day = Integer.valueOf(tokenArr[0]);
        String monthStr = tokenArr[1];
        int month;
        try {
            month = Integer.valueOf(tokenArr[1]);
        } catch (NumberFormatException nfe) {
            month = convertMonthIntoInteger(monthStr);
        }
        int year = Integer.valueOf(getThisYear());

        try {
            setDate(day, month, year);
            getYear();
        } catch (IllegalValueException ive) {
            throw ive;
        }
    }

    private void setDateGivenMonthYear(String dateToConvert) throws IllegalValueException {
        StringTokenizer st = new StringTokenizer(dateToConvert, DATE_DELIMITER);
        String[] tokenArr = new String[3];
        int i = 0;
        while (st.hasMoreTokens()) {
            tokenArr[i] = st.nextToken();
            i++;
        }

        int day = FIRST_DAY_OF_THE_MONTH;
        String monthStr = tokenArr[0];
        int month;
        try {
            month = Integer.valueOf(tokenArr[0]);
        } catch (NumberFormatException nfe) {
            month = convertMonthIntoInteger(monthStr);
        }
        int year = Integer.valueOf(tokenArr[1]);

        try {
            setDate(day, month, year);
            getYear();
        } catch (IllegalValueException ive) {
            throw ive;
        }
    }
    
    private void setDateGivenMonth(String monthToConvert) {
        int day = FIRST_DAY_OF_THE_MONTH;
        int month = convertMonthIntoInteger(monthToConvert);
        int year = Integer.valueOf(getThisYear());

        try {
            setDate(day, month, year);
        } catch (DateTimeException dte) {
            throw dte;
        }
    }
    
    private void setDateGivenDayNameOfWeek(String dayName) {
        int day = convertDayOfWeekIntoInteger(dayName);
        LocalDate today = LocalDate.now();
        String todayDayNameInWeek = today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
        int todayDayInWeek = convertDayOfWeekIntoInteger(todayDayNameInWeek);
        int daysToAdd = day - todayDayInWeek;
        if (daysToAdd <= 0) {
            daysToAdd += NUM_DAYS_IN_A_WEEK;
        }
        LocalDate finalDate = today.plusDays(daysToAdd);
        setDate(finalDate.getDayOfMonth(), finalDate.getMonthValue(), finalDate.getYear());
    }
    
    private void setDateGivenToday(String taskDate) {
        LocalDate today = LocalDate.now();
        setDate(today.getDayOfMonth(), today.getMonthValue(), today.getYear());
    }
    
    private void setDateGivenTomorrow(String taskDate) {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        setDate(tomorrow.getDayOfMonth(), tomorrow.getMonthValue(), tomorrow.getYear());
    }
    
    /**
     * Extract the different fields of a given valid taskDate
     * @throws DateTimeException
     */
    public void setDate(int day, int month, int year) {
        this.taskDate = convertToStandardFormat(day, month, year);
    }
    
    /**
     * Convert this TaskDate to the format of
     * DAY_MONTH-YEAR
     */
    public static String convertToStandardFormat(int day, int month, int year) {
        return day + "-" + month + "-" + year;
    }

    /**
     * Returns true if a given string is a valid task taskDate.
     */
    public static boolean isValidDate(String dateToValidate) {
        if (dateToValidate == null || dateToValidate.isEmpty()) {
            return false;
        }
        
        return isValidFullDate(dateToValidate) || isValidMonthAndYear(dateToValidate)
                || isValidDayAndMonth(dateToValidate) || isValidMonth(dateToValidate) || isValidToday(dateToValidate)
                || isValidTomorrow(dateToValidate) || isValidDayOfWeek(dateToValidate);
    }

    public static boolean isValidDayOfWeek(String dateToValidate) {
        if (convertDayOfWeekIntoInteger(dateToValidate) == NOT_A_VALID_DAY_OF_THE_WEEK) {
            return false;
        }
        return true;
    }

    public static boolean isValidMonthAndYear(String dateToValidate) {
        if (isValidFormat(dateToValidate, "MMM yyyy") || isValidFormat(dateToValidate, "MMM-yyyy")
                || isValidFormat(dateToValidate, "MMM.yyyy")
                || isValidFormat(dateToValidate, "MMM/yyyy")) {
            return true;
        }
        return false;
    }

    public static boolean isValidDayAndMonth(String dateToValidate) {
        if (isValidFormat(dateToValidate, "d MMM") || isValidFormat(dateToValidate, "d-MMM")
                || isValidFormat(dateToValidate, "d.MMM")
                || isValidFormat(dateToValidate, "d/MMM")) {
            return true;
        }
        return false;
    }

    public static boolean isValidFullDate(String dateToValidate) {
        if (isValidFormat(dateToValidate, "d M yyyy") || isValidFormat(dateToValidate, "d MMM yyyy")
                || isValidFormat(dateToValidate, "d-M-yyyy") || isValidFormat(dateToValidate, "d-MMM-yyyy")
                || isValidFormat(dateToValidate, "d.M.yyyy") || isValidFormat(dateToValidate, "d.MMM.yyyy")
                || isValidFormat(dateToValidate, "d.M-yyyy") || isValidFormat(dateToValidate, "d.MMM-yyyy")
                || isValidFormat(dateToValidate, "d-M.yyyy") || isValidFormat(dateToValidate, "d-MMM.yyyy")
                || isValidFormat(dateToValidate, "d/M/yyyy") || isValidFormat(dateToValidate, "d/MMM/yyyy")
                || isValidFormat(dateToValidate, "d-M/yyyy") || isValidFormat(dateToValidate, "d-MMM/yyyy")
                || isValidFormat(dateToValidate, "d/M-yyyy") || isValidFormat(dateToValidate, "d/MMM-yyyy")
                || isValidFormat(dateToValidate, "d.M/yyyy") || isValidFormat(dateToValidate, "d.MMM/yyyy")
                || isValidFormat(dateToValidate, "d/M.yyyy") || isValidFormat(dateToValidate, "d/MMM.yyyy")) {
            return true;
        }
        return false;
    }

    /**
     * Returns true if a given string has a valid format supported by SimpleDateFormat.
     */
    public static boolean isValidFormat(String dateToValidate, String acceptedFormat) {
        if (dateToValidate == null) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(acceptedFormat);
        sdf.setLenient(false);

        try {
            // if not valid, it will throw ParseException
            Date taskDate = sdf.parse(dateToValidate);
        } catch (ParseException e) {
            return false;
        }

        return true;
    }

    public static boolean isValidToday(String dateToValidate) {
        assert (dateToValidate != null);
        dateToValidate = dateToValidate.toLowerCase();
        
        switch (dateToValidate) {
        case "today":
            // Fallthrough
        case "tdy":
            return true;
        default:
            return false;
        }
    }

    public static boolean isValidTomorrow(String dateToValidate) {
        assert (dateToValidate != null);
        dateToValidate = dateToValidate.toLowerCase();
        
        switch (dateToValidate) {
        case "tomorrow":
            // Fallthrough
        case "tmr":
            return true;
        default:
            return false;
        }
    }

    public static boolean isValidMonth(String month) {
        if (convertMonthIntoInteger(month) == NOT_A_VALID_MONTH) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns an integer representing the day in a week
     */
    private static int convertDayOfWeekIntoInteger(String day) {
        assert (day != null);
        day = day.toLowerCase();

        switch (day) {
        case "mon":
            // Fallthrough
        case "monday":
            return MONDAY;
        case "tue":
            // Fallthrough
        case "tues":
            // Fallthrough
        case "tuesday":
            return TUESDAY;
        case "wed":
            // Fallthrough
        case "wednesday":
            return WEDNESDAY;
        case "thu":
            // Fallthrough
        case "thur":
            // Fallthrough
        case "thurs":
            // Fallthrough
        case "thursday":
            return THURSDAY;
        case "fri":
            // Fallthrough
        case "friday":
            return FRIDAY;
        case "sat":
            // Fallthrough
        case "saturday":
            return SATURDAY;
        case "sun":
            // Fallthrough
        case "sunday":
            return SUNDAY;
        default:
            return NOT_A_VALID_DAY_OF_THE_WEEK;
        }
    }

    /**
     * Returns an integer representing the month of a year.
     */
    private static int convertMonthIntoInteger(String month) {
        assert (month!= null);
        if (Character.isLetter(month.charAt(0))) {
            month = month.toLowerCase();
        }

        switch (month) {
        case "jan":
            // Fallthrough
        case "january":
            return JANUARY;
        case "feb":
            // Fallthrough
        case "february":
            return FEBRUARY;
        case "mar":
            // Fallthrough
        case "march":
            return MARCH;
        case "apr":
            // Fallthrough
        case "april":
            return APRIL;
        case "may":
            return MAY;
        case "jun":
            // Fallthrough
        case "june":
            return JUNE;
        case "jul":
            // Fallthrough
        case "july":
            return JULY;
        case "aug":
            // Fallthrough
        case "august":
            return AUGUST;
        case "sep":
            // Fallthrough
        case "sept":
            // Fallthrough
        case "september":
            return SEPTEMBER;
        case "oct":
            // Fallthrough
        case "october":
            return OCTOBER;
        case "nov":
            // Fallthrough
        case "november":
            return NOVEMBER;
        case "dec":
            // Fallthrough
        case "december":
            return DECEMBER;
        default:
            return NOT_A_VALID_MONTH;
        }
    }

    /**
     * Get today's taskDate in the format of
     * DAY-MONTH-YEAR
     */
    public static TaskDate getTodayDate() {
        try {
            return new TaskDate(LocalDate.now().format(standardFormat));
        } catch (IllegalValueException e) {
            return null;
        }
    }

    /**
     * Get tomorrow's taskDate in the format of
     * DAY-MONTH-YEAR
     */
    public static TaskDate getTomorrowDate() {
        try {
            return new TaskDate(LocalDate.now().plusDays(1).format(standardFormat));
        } catch (IllegalValueException e) {
            return null;
        }
    }
    
    public TaskDate getNextDay() throws IllegalValueException {
        try {
            LocalDate localDate = this.getLocalDate();
            LocalDate nextDay = localDate.plusDays(1);
            return new TaskDate(nextDay.format(standardFormat));
        } catch (IllegalValueException e) {
            throw new IllegalValueException(MESSAGE_TASK_DATE_CONSTRAINTS);
        }
    }
    
    public TaskDate getNextWeek() throws IllegalValueException {
        try {
            LocalDate localDate = this.getLocalDate();
            LocalDate nextWeek = localDate.plusWeeks(1);
            return new TaskDate(nextWeek.format(standardFormat));
        } catch (IllegalValueException e) {
            throw new IllegalValueException(MESSAGE_TASK_DATE_CONSTRAINTS);
        }
    }
    
    //@@author A0148004R-reused
    public TaskDate getNextMonth() throws IllegalValueException {
        try {
            LocalDate localDate = LocalDate.of(Integer.valueOf(getYear()), Integer.valueOf(getMonth()), Integer.valueOf(getDay()));
            LocalDate nextMonth = localDate.plusMonths(1);
            return new TaskDate(nextMonth.format(standardFormat));
        } catch (IllegalValueException e) {
            throw new IllegalValueException(MESSAGE_TASK_DATE_CONSTRAINTS);
        }
    }
    //@@author
    
    //@@author A0139257X
    /**
     * Returns a string representing the integer value of this year
     */
    public static String getThisYear() {
        return LocalDate.now().getYear() + "";
    }
    
    public String getDay() throws IllegalValueException {
        assert taskDate != null;
        
        final Matcher matcherFullArg = TASK_DATE_ARGS_FORMAT.matcher(taskDate.trim());
        if (matcherFullArg.matches()) {
            return matcherFullArg.group("day");
        } else {
            throw new IllegalValueException(MESSAGE_TASK_DATE_CONSTRAINTS);
        }
    }
    
    public String getMonth() throws IllegalValueException {
        assert taskDate != null;
        
        final Matcher matcherFullArg = TASK_DATE_ARGS_FORMAT.matcher(taskDate.trim());
        if (matcherFullArg.matches()) {
            return matcherFullArg.group("month");
        } else {
            throw new IllegalValueException(MESSAGE_TASK_DATE_CONSTRAINTS);
        }
    }
    
    public String getYear() throws IllegalValueException {
        assert taskDate != null;
        
        final Matcher matcherFullArg = TASK_DATE_ARGS_FORMAT.matcher(taskDate.trim());
        if (matcherFullArg.matches()) {
            return matcherFullArg.group("year");
        } else {
            throw new IllegalValueException(MESSAGE_TASK_DATE_CONSTRAINTS);
        }
    }
    
    public String getDayNameInWeek() throws IllegalValueException {
        LocalDate localDate = this.getLocalDate();
        String dayNameInWeek = localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
        return dayNameInWeek;
    }
    
    public String getMonthName() {
        LocalDate localDate = this.getLocalDate();
        String month = localDate.getMonth().getDisplayName(TextStyle.FULL, Locale.US);
        return month;
    }
    
    public String getDisplayDate() {
        try {
            return getDayNameInWeek() + ", " + getDay() + " " + getMonthName() + " " + getYear();
        } catch (IllegalValueException e) {
            return "";
        }
    }
    
    public LocalDate getLocalDate() {
        try {
            return LocalDate.of(Integer.valueOf(getYear()), Integer.valueOf(getMonth()), Integer.valueOf(getDay()));
        } catch (NumberFormatException | IllegalValueException e) {
            return null;
        }
    }

    public static String getDefaultDate() {
        int day = LocalDate.now().getDayOfMonth();
        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();
        return convertToStandardFormat(day, month, year);
    }
    
    public boolean isBefore(TaskDate date) {
        try {
            LocalDate thisDate = this.getLocalDate();
            LocalDate dateToComapare = date.getLocalDate();
            return thisDate.isBefore(dateToComapare);
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isAfter(TaskDate date) {
        try {
            LocalDate thisDate = this.getLocalDate();
            LocalDate dateToComapare = date.getLocalDate();
            return thisDate.isAfter(dateToComapare);
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Calculates the number of days from the first date to the second date
     */
    public static long between(TaskDate first, TaskDate second) {
        return ChronoUnit.DAYS.between(first.getLocalDate(), second.getLocalDate());
    }
    
    public LocalDateTime toLocalDateTime(TaskTime time) {
        return LocalDateTime.of(this.getLocalDate(), time.getLocalTime());
    }
    
    /**
     * Returns a string with the format of
     * DAY-MONTH-YEAR
     */
    @Override
    public String toString() {
        return taskDate;
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDate // instanceof handles nulls
                && this.taskDate.equals(((TaskDate)other).taskDate));
    }

    @Override
    public int hashCode() {
        return taskDate.hashCode();
    }

}
