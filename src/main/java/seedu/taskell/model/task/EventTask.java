package seedu.taskell.model.task;

import seedu.taskell.commons.exceptions.IllegalValueException;
import seedu.taskell.model.tag.UniqueTagList;

//@@author A0139257X
/**
 * Represents an Event task in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class EventTask extends Task {
    public static final String MESSAGE_EVENT_CONSTRAINTS = "Start date and time must be before end date and time"
            + "\nAll date and time should not before current time";
<<<<<<< Updated upstream

    public EventTask(String description, String startDate, String endDate, String startTime, String endTime, String taskPriority, String recurringType, String taskStatus, UniqueTagList tags) throws IllegalValueException {
        this(new Description(description),
                EVENT_TASK,
                new TaskDate(startDate),
                new TaskDate(endDate),
                new TaskTime(startTime),
                new TaskTime(endTime),
                new TaskPriority(taskPriority),
                new RecurringType(recurringType),
                new TaskStatus(taskStatus),              
                tags);
    }
    /**
     * Every field must be present and not null.
     * @throws IllegalValueException 
     */
    public EventTask(Description description, String taskType, TaskDate startDate, TaskDate endDate, TaskTime startTime, TaskTime endTime, TaskPriority taskPriority, RecurringType recurringType, TaskStatus taskStatus, UniqueTagList tags) throws IllegalValueException {
        endDate = autoAdjustEndDate(startDate, endDate, startTime, endTime);
=======
    
    private String[] taskComponentArray;
    private boolean[] hasTaskComponentArray;
    
    public EventTask(String[] taskComponentArray, boolean[] hasTaskComponentArray, UniqueTagList tags) throws IllegalValueException {
        
        this.taskComponentArray = taskComponentArray;
        this.hasTaskComponentArray = hasTaskComponentArray;
        this.tags = tags;
        
        setTaskComponents();
    }
    
    private void setTaskComponents() throws IllegalValueException {
        TaskDate eventStartDate = new TaskDate(this.taskComponentArray[START_DATE]);
        TaskTime eventEndTime = new TaskTime(this.taskComponentArray[END_TIME]);
        RecurringType eventRecurringType = new RecurringType(this.taskComponentArray[RECURRING_TYPE]);
        
        TaskTime eventStartTime = setStartTime(eventStartDate);
        eventStartDate = adjustStartDate(eventStartDate, eventStartTime);
        System.out.println(eventStartDate);
        
        TaskDate eventEndDate = setEndDate(eventStartDate, eventStartTime, eventEndTime);
>>>>>>> Stashed changes
        
        if (!isValidEventDuration(eventStartDate, eventEndDate, eventStartTime, eventEndTime)) {
            throw new IllegalValueException(MESSAGE_EVENT_CONSTRAINTS);
        }
<<<<<<< Updated upstream
        if(!isValidRecurringEvent(startDate, endDate, recurringType)){
            throw new IllegalValueException(RecurringType.MESSAGE_INVALID_RECURRING_DURATION);
        }
        this.description = description;
=======
        
        if(!isValidRecurringEvent(eventStartDate, eventEndDate, eventRecurringType)){
            throw new IllegalValueException(RecurringType.MESSAGE_INVALID_RECURRING_DURATION);
        }
        
        this.description = new Description(taskComponentArray[DESCRIPTION]);
>>>>>>> Stashed changes
        this.taskType = EVENT_TASK;
        this.startDate = eventStartDate;
        this.endDate = eventEndDate;
        this.startTime = eventStartTime;
        this.endTime = eventEndTime;
        this.taskPriority = new TaskPriority(taskComponentArray[TASK_PRIORITY]);
        this.recurringType = eventRecurringType;
        this.taskStatus = new TaskStatus(TaskStatus.INCOMPLETE);
    }
    
    private TaskDate adjustStartDate(TaskDate eventStartDate, TaskTime eventStartTime) throws IllegalValueException {
        if (TaskDate.isValidFullDate(taskComponentArray[START_DATE]) 
                && eventStartDate.isBefore(TaskDate.getTodayDate())) {
            throw new IllegalValueException(MESSAGE_EVENT_CONSTRAINTS);
        } else if (TaskDate.isValidMonthAndYear(taskComponentArray[START_DATE])
                && eventStartDate.isBefore(TaskDate.getTodayDate())) {
            throw new IllegalValueException(MESSAGE_EVENT_CONSTRAINTS);
        }
        
        if (!hasTaskComponentArray[START_DATE_COMPONENT] && eventStartTime.isBefore(TaskTime.getTimeNow())) {
            eventStartDate = eventStartDate.getNextDay();
        }
        
        return eventStartDate;
    }
    
    private TaskTime setStartTime(TaskDate eventStartDate) throws IllegalValueException {
        if (eventStartDate.equals(TaskDate.getTodayDate()) && !hasTaskComponentArray[Task.START_TIME_COMPONENT]) {
            return TaskTime.getTimeNow();
        } else {
            return new TaskTime(taskComponentArray[Task.START_TIME]);
        }
    }
    
    private TaskDate setEndDate(TaskDate eventStartDate, TaskTime eventStartTime, TaskTime eventEndTime) throws IllegalValueException {
        
        initialiseEndDate(eventStartDate);
        
        TaskDate eventEndDate = adjustEndDateDependingOnDateForm(eventStartDate, eventStartTime, eventEndTime);
        
        eventEndDate = adjustEndDateDependingOnEndTime(eventStartDate, eventEndDate, eventStartTime, eventEndTime);
        
        return eventEndDate;
    }
    
    /**
     * Adjust end date when start and end date is the same but end time is before start time.
     * Bring end date to the next day
     */
    private TaskDate adjustEndDateDependingOnEndTime(TaskDate eventStartDate, TaskDate eventEndDate, TaskTime eventStartTime, 
            TaskTime eventEndTime) throws IllegalValueException {
        if (!hasTaskComponentArray[END_DATE_COMPONENT] && eventStartDate.equals(eventEndDate) && eventEndTime.isBefore(eventStartTime)) {
            eventEndDate = eventEndDate.getNextDay();
        }
        return eventEndDate;
    }
    
    /**
     * Adjustment is made to end date to make sure end date is never before start date
     */
    private TaskDate adjustEndDateDependingOnDateForm(TaskDate eventStartDate, TaskTime eventStartTime, 
            TaskTime eventEndTime) throws IllegalValueException {
        
        TaskDate eventEndDate = new TaskDate(taskComponentArray[Task.END_DATE]);
        String userInputEndDate = taskComponentArray[END_DATE];
        
        if (TaskDate.isValidFullDate(userInputEndDate) && eventEndDate.isBefore(eventStartDate)
                || TaskDate.isValidMonthAndYear(userInputEndDate) && eventEndDate.isBefore(eventStartDate)) {
            throw new IllegalValueException(MESSAGE_EVENT_CONSTRAINTS);
        } else if (TaskDate.isValidDayOfWeek(userInputEndDate)) {
            eventEndDate = determineDayInWeekGivenName(taskComponentArray, eventStartDate, eventEndDate);
        } else if (TaskDate.isValidMonth(userInputEndDate)) {
            eventEndDate = determineDayGivenMonthOrYear(taskComponentArray, eventStartDate, eventEndDate);
        } else if (TaskDate.isValidDayAndMonth(userInputEndDate)) {
            eventEndDate = determineDayGivenMonthOrYear(taskComponentArray, eventStartDate, eventEndDate);
        } 
        
        return eventEndDate;
    }
    
    private void initialiseEndDate(TaskDate eventStartDate) {
        if (!hasTaskComponentArray[Task.END_DATE_COMPONENT]) {
            taskComponentArray[Task.END_DATE] = eventStartDate.toString();
        }
    }
    
    /**
     * Determine end date in the week with respect to start date
     */
    private TaskDate determineDayInWeekGivenName(String[] taskComponentArray, 
            TaskDate eventStartDate, TaskDate eventEndDate) {
        
        if (!eventEndDate.isAfter(eventStartDate) && hasTaskComponentArray[END_DATE_COMPONENT]) {
            try {
                eventEndDate = eventEndDate.getNextWeek();
            } catch (IllegalValueException e) {
                return null;
            }
        }
        return eventEndDate;
    }
    
    /**
     * Determine end date in the year with respect to start date
     */
    private TaskDate determineDayGivenMonthOrYear(String[] taskComponentArray, 
            TaskDate eventStartDate, TaskDate eventEndDate) {
        if (eventEndDate.isBefore(eventStartDate)) {
            try {
                eventEndDate = eventEndDate.getNextYear();
            } catch (IllegalValueException e) {
                return null;
            }
        }
        return eventEndDate;
    }
    
    //@@author
    
    //@@author A0148004R
    private boolean isValidRecurringEvent(TaskDate startDate, TaskDate endDate, RecurringType recurringType){
        
        if(recurringType.recurringType.equals(RecurringType.DAILY_RECURRING)){
            return Math.abs(TaskDate.between(startDate, endDate)) <= TaskDate.NUM_DAYS_PER_DAY;
        } else if(recurringType.recurringType.equals(RecurringType.WEEKLY_RECURRING)){
            return Math.abs(TaskDate.between(startDate, endDate)) <= TaskDate.NUM_DAYS_IN_A_WEEK;
        } else if(recurringType.recurringType.equals(RecurringType.MONTHLY_RECURRING)){
            return Math.abs(TaskDate.between(startDate, endDate)) <= TaskDate.NUM_DAYS_IN_A_MONTH;
        } else {
            return true;
        }
    }
    
    //@@author A0139257X
    private boolean isValidEventDuration(TaskDate startDate, TaskDate endDate, TaskTime startTime, TaskTime endTime) {
        TaskDate today = TaskDate.getTodayDate();
        TaskTime currentTime = TaskTime.getTimeNow();
        
        if (startDate.isBefore(today) || endDate.isBefore(today)) {
            return false;
        } else if (startDate.isAfter(endDate)) {
            return false;
        } else if (startDate.equals(today) && startTime.isBefore(currentTime)) { 
            return false;
        } else if (endDate.equals(today) && endTime.isBefore(currentTime)){
            return false;
        } else {
            return true;
        }
    }
    
<<<<<<< Updated upstream
    /**
     * Adjust the endDate such that it fits into the real-world context
     * @throws IllegalValueException
     */
    private TaskDate autoAdjustEndDate(TaskDate startDate, TaskDate endDate, TaskTime startTime, TaskTime endTime) throws IllegalValueException {
        TaskDate today = TaskDate.getTodayDate();
        if (startDate.equals(endDate) && startTime.isAfter(endTime)) {
            endDate = endDate.getNextDay();
        } else if (startDate.getDayNameInWeek().equals(today.getDayNameInWeek())
                && !endDate.getDayNameInWeek().equals(today.getDayNameInWeek())) {
            endDate = endDate.getNextWeek();
        }
        return endDate;
    }

=======
>>>>>>> Stashed changes
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }
}