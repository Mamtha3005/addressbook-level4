//@@author A0142073R-reused

package seedu.taskell.model.task;

import seedu.taskell.commons.exceptions.IllegalValueException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TaskPriorityTest {

    @Test
    public void isValidPriority_returnTrue() {
        assertTrue(TaskPriority.isValidPriority("0"));
        assertTrue(TaskPriority.isValidPriority("1"));
        assertTrue(TaskPriority.isValidPriority("2"));
        assertTrue(TaskPriority.isValidPriority("3"));
    }
    
    public void isInvalidPriority_returnFalse() {
        try {
            TaskPriority invalidPriority = new TaskPriority("100");
        } catch (IllegalValueException ive) {
            assertEquals(TaskPriority.MESSAGE_TASK_PRIORITY_CONSTRAINTS, ive.getMessage());
        }
    }

}
