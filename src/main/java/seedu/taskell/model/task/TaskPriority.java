package seedu.taskell.model.task;

import seedu.taskell.commons.exceptions.IllegalValueException;

//@@author A0139257X
/**
 * Represents a Task's priority in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class TaskPriority {
    
    public static final String MESSAGE_TASK_PRIORITY_CONSTRAINTS = "Task priority should range from 0-3"
            + "\nThere should not be more than 1 priority level for a task";
    public static final String TASK_PRIORITY_VALIDATION_REGEX = ".+";
    
    public static final String PREFIX = "p/";
    
    public static final String NO_PRIORITY = "0";
    public static final String LOW_PRIORITY = "1";
    public static final String MEDIUM_PRIORITY = "2";
    public static final String HIGH_PRIORITY = "3";
    
    public static final String DEFAULT_PRIORITY = NO_PRIORITY;
    
    public static final String LOW_PRIORITY_BACKGROUND = "-fx-background-color: #4CAF50";
    public static final String MEDIUM_PRIORITY_BACKGROUND = "-fx-background-color: #FFEB3B";
    public static final String HIGH_PRIORITY_BACKGROUND = "-fx-background-color: #F44336";

    public final String taskPriority;

    /**
     * Validates given priority.
     *
     * @throws IllegalValueException if given priority string is invalid.
     */
    public TaskPriority(String priority) throws IllegalValueException {
        assert priority != null;
        if (!isValidPriority(priority)) {
            throw new IllegalValueException(MESSAGE_TASK_PRIORITY_CONSTRAINTS);
        }
        this.taskPriority = priority;
    }

    /**
     * Returns true if a given string is a valid task taskPriority.
     */
    public static boolean isValidPriority(String priority) {
        return priority.equals(NO_PRIORITY)
                || priority.equals(LOW_PRIORITY) 
                || priority.equals(MEDIUM_PRIORITY)
                || priority.equals(HIGH_PRIORITY);
    }

    @Override
    public String toString() {
        return taskPriority;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskPriority // instanceof handles nulls
                && this.taskPriority.equals(((TaskPriority) other).taskPriority)); // state check
    }

    @Override
    public int hashCode() {
        return taskPriority.hashCode();
    }

}