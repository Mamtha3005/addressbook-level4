//@@author A0148004R
/**
 * Represents a Task's status in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidTaskComplete(String)}
 */
package seedu.taskell.model.task;

import java.util.Objects;

public class TaskStatus {
    public static final String FINISHED = "finished";
    public static final String INCOMPLETE = "incomplete";
    public final String taskStatus;
    
    public TaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
    
    public String taskStatus() {
        return taskStatus;
    }
    
    public static boolean isValidTaskComplete(String taskToValidate) {
        return taskToValidate.equals(FINISHED)  || taskToValidate.equals(INCOMPLETE);           
    }
    
    @Override
    public String toString() {
        return taskStatus;
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskStatus // instanceof handles nulls
                        && this.taskStatus.equals(((TaskStatus)other).taskStatus)); // state check
    }
    @Override
    public int hashCode() {
        return taskStatus.hashCode();
    }
}
//@@author
