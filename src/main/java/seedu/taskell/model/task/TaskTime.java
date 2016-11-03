package seedu.taskell.model.task;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.taskell.commons.exceptions.IllegalValueException;

//@@author A0139257X
/**
 * Represents a Task's taskTime in the task manager.
 */
public class TaskTime {

    public static final String ZERO_MINUTE = "00";
    public static final String NOON = "12:00PM";
    public static final String MIDNIGHT = "12:00AM";
    
    public static final String AM = "AM";
    public static final String PM = "PM";
    
    public static final int TIME_OFFSET = 12;
    
    public static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("h:mma");
    
    public static final String DEFAULT_START_TIME = MIDNIGHT;
    public static final String DEFAULT_END_TIME = "11:59PM";

    public static final Pattern TASK_TIME_ARGS_FORMAT = Pattern
            .compile("(?<hour>(1[0-2]|[1-9]))" + "(.|-|:)(?<minute>([0-5][0-9]))" + "(?<antePost>(am|pm|AM|PM|Am|Pm|aM|pM))");
    public static final Pattern TASK_TIME_HOUR_ONLY_FORMAT = Pattern
            .compile("(?<hour>(1[0-2]|[1-9]))" + "(?<antePost>(am|pm|AM|PM|Am|Pm|aM|pM))");
    final static String FULL_TIME_REGEX = "^(1[0-2]|[1-9])(.|-|:)([0-5][0-9])(am|pm|AM|PM|Am|Pm|aM|pM)$";
    final static String HOUR_ONLY_TIME_REGEX = "^(1[0-2]|[1-9])(am|pm|AM|PM|Am|Pm|aM|pM)$";
    
    public static final String MESSAGE_TASK_TIME_CONSTRAINTS =
            "Time should be in 12hour clock format."
            + "\nHour and Minute are separated by '.' or ':'"
            + "\nEg. 9.30am or 12:50pm";

    public String taskTime; //Standard format

    public TaskTime(String time) throws IllegalValueException {
        if (isValidTime(time)) {
            setTime(time);
        } else {
            throw new IllegalValueException(MESSAGE_TASK_TIME_CONSTRAINTS);
        }
    }

    /**
     * Returns true if given string is a valid 12-hour time in the real world
     */
    public static boolean isValidTime(String time) {
        assert time != null;
        if (time.isEmpty()) {
            return false;
        }

        if (time.matches(HOUR_ONLY_TIME_REGEX) || time.matches(FULL_TIME_REGEX)) {
            return true;
        } else if (isValidNow(time)) {
            return true;
        } else if (isValidNoon(time)) {
            return true;
        } else if (isValidMidnight(time)) {
            return true;
        } else {
            return false;
        }
    }
    
    private static boolean isValidNow(String time) {
        time = time.toLowerCase();
        return "now".equals(time);
    }
    
    private static boolean isValidNoon(String time) {
        time = time.toLowerCase();
        switch (time) {
        case "afternoon":
            //Fallthrough
        case "noon":
            //Fallthrough
        case "12noon":
            //Fallthrough
        case "12-noon":
            return true;
        default:
            return false;
        }
    }
    
    private static boolean isValidMidnight(String time) {
        time = time.toLowerCase();
        switch (time) {
        case "midnight":
            //Fallthrough
        case "mid-night":
            //Fallthrough
        case "12midnight":
            //Fallthrough
        case "12-midnight":
            //Fallthrough
        case "12mid-night":
            //Fallthrough
        case "12-mid-night":
            //Fallthrough
            return true;
        default:
            return false;
        }
    }
    
    /**
     * Checks if this time is before the specified time
     */
    public boolean isBefore(TaskTime time) {
        LocalTime timeToCompare = time.getLocalTime();
        LocalTime thisTimeLocalTime = this.getLocalTime();
        return thisTimeLocalTime.isBefore(timeToCompare);
    }

    /**
     * Checks if this time is after the specified time
     */
    public boolean isAfter(TaskTime time) {
        LocalTime timeToCompare = time.getLocalTime();
        LocalTime thisTimeLocalTime = this.getLocalTime();
        return thisTimeLocalTime.isAfter(timeToCompare);
    }
    
    public void setTime(String time) throws IllegalValueException {
        final Matcher matcherFullArg = TASK_TIME_ARGS_FORMAT.matcher(time.trim());
        final Matcher matcherHourOnly = TASK_TIME_HOUR_ONLY_FORMAT.matcher(time.trim());
        if (matcherFullArg.matches()) {
            this.taskTime = setTime(matcherFullArg.group("hour"), 
                    matcherFullArg.group("minute"), 
                    matcherFullArg.group("antePost"));
        } else if (matcherHourOnly.matches()) {
            this.taskTime = setTime(matcherHourOnly.group("hour"), 
                    ZERO_MINUTE, 
                    matcherHourOnly.group("antePost"));
        } else if (isValidNow(time)) {
            this.taskTime = getTimeNow().toString();
        } else if (isValidNoon(time)) {
            this.taskTime = NOON;
        } else if (isValidMidnight(time)) {
            this.taskTime = MIDNIGHT;
        } else {
            throw new IllegalValueException(MESSAGE_TASK_TIME_CONSTRAINTS);
        }
    }
    
    public String setTime(String hour, String minute, String antePost) {
        this.taskTime =  hour + ":" + minute + antePost.toUpperCase();
        return taskTime;
    }

    public static TaskTime getTimeNow() {
        LocalTime currTime = LocalTime.now();
        try {
            return new TaskTime(LocalTime.of(currTime.getHour(), currTime.getMinute()).format(dtf));
        } catch (IllegalValueException e) {
            return null;
        }
    }
    
    public String getHour() {
        assert taskTime != null;
        
        final Matcher matcherFullArg = TASK_TIME_ARGS_FORMAT.matcher(taskTime.trim());
        if (matcherFullArg.matches()) {
            return matcherFullArg.group("hour");
        }
        
        return "";
    }
    
    public String getMinute() {
        assert taskTime != null;
        
        final Matcher matcherFullArg = TASK_TIME_ARGS_FORMAT.matcher(taskTime.trim());
        if (matcherFullArg.matches()) {
            return matcherFullArg.group("minute");
        }
        
        return "";
    }
    
    public String getAntePost() {
        assert taskTime != null;
        
        final Matcher matcherFullArg = TASK_TIME_ARGS_FORMAT.matcher(taskTime.trim());
        if (matcherFullArg.matches()) {
            return matcherFullArg.group("antePost");
        }
        
        return "";
    }
    
    public LocalTime getLocalTime() {
        int timeHour = Integer.valueOf(this.getHour());
        if (this.getAntePost().equals(PM) && (timeHour != TIME_OFFSET)) {
            timeHour += TIME_OFFSET;
        } else if (this.getAntePost().equals(AM) && (timeHour == TIME_OFFSET)) {
            timeHour -= TIME_OFFSET;
        }
        return LocalTime.of(timeHour, Integer.valueOf(this.getMinute()));
    }
    
    public LocalDateTime toLocalDateTime(TaskDate date) {
        return LocalDateTime.of(date.getLocalDate(), this.getLocalTime());
    }
    
    @Override
    public String toString() {
        return taskTime;
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskTime // instanceof handles nulls
                && this.taskTime.equals(((TaskTime) other).taskTime)); // state check
    }
    
    @Override
    public int hashCode() {
        return (taskTime).hashCode();
    }
}
