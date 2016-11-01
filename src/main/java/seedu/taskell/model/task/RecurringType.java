package seedu.taskell.model.task;

import seedu.taskell.commons.exceptions.IllegalValueException;

//@@author A0148004R
public class RecurringType {
    
    public static final String MESSAGE_TASK_RECURRING_CONSTRAINTS = "Task recurrence should be daily, weekly or monthly"
            + "\nThere should not be more than 1 recurring class for a task";
    
    public static final String MESSAGE_INVALID_RECURRING_DURATION = "Invalid duration!" 
            + "\nTask with recurring type as daily shoud not have a difference of more than a day"
            + "\nTask with recurring type as weekly should not have a difference of more than a week"
            + "\nTask with recurring type as monthly should not have a difference of more than a month";
    
    public static final String PREFIX = "r/";
    
    public static final String NO_RECURRING = "neverRecur";
    public static final String DAILY_RECURRING = "daily";
    public static final String WEEKLY_RECURRING = "weekly";
    public static final String MONTHLY_RECURRING = "monthly";
    
    public static final String DEFAULT_RECURRING = NO_RECURRING;
   
    public final String recurringType;

    /**
     * Validates given recurring.
     *
     * @throws IllegalValueException if given recurring string is invalid.
     */
    public RecurringType(String recurring) throws IllegalValueException {
        assert recurring != null;
        if (!isValidRecurring(recurring)) {
            throw new IllegalValueException(MESSAGE_TASK_RECURRING_CONSTRAINTS);
        }
        this.recurringType = recurring;
    }

    /**
     * Returns true if a given string is a valid task taskTime.
     */
    public static boolean isValidRecurring(String recurring) {
        return recurring.equals(NO_RECURRING)
                || recurring.equals(DAILY_RECURRING) 
                || recurring.equals(WEEKLY_RECURRING)
                || recurring.equals(MONTHLY_RECURRING);
    }

    @Override
    public String toString() {
        return recurringType;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecurringType // instanceof handles nulls
                && this.recurringType.equals(((RecurringType) other).recurringType)); // state check
    }

    @Override
    public int hashCode() {
        return recurringType.hashCode();
    }
}
