//@@author A0142073R
package guitests;

import org.junit.Test;

import static seedu.taskell.logic.commands.list.ListPriorityCommand.COMMAND_WORD;

import seedu.taskell.commons.exceptions.IllegalValueException;
import seedu.taskell.model.task.TaskPriority;
import seedu.taskell.model.task.TaskTime;
import seedu.taskell.testutil.TestTask;

import static org.junit.Assert.assertTrue;

public class ListPriorityCommandTest extends TaskManagerGuiTest {
    
    private static final String INVALID_PRIORITY = "777";

    @Test
    public void listPriority_valid_exceptionThrown() throws IllegalValueException {

        // list 2 tasks in the list
        TestTask[] currentList = td.getTypicalTasks();
        String priority = TaskPriority.LOW_PRIORITY;
        assertlistPrioritySuccess(currentList, priority, td.archivePastEmails, td.borrowBooks, td.collectParcel, td.discardBooks,td.editPowerpoint, td.fixbugs, td.getNewUniform);

         //no tasks to list
         priority = TaskPriority.NO_PRIORITY;
         assertlistPrioritySuccess(currentList, priority);
         
         priority = TaskPriority.HIGH_PRIORITY;
         assertlistPrioritySuccess(currentList, priority);
        
    }
    
    @Test
    public void listPriority_invalid_exceptionThrown() throws IllegalValueException {
        
         // invalid command format
         commandBox.runCommand(COMMAND_WORD);
         assertResultMessage("Invalid command format! \n" + COMMAND_WORD + ": List the task with the specified priority. "
                 + "Parameters: INDEX (must be between 0 and 3 inclusive).\n"
                 + "Example: list-priority 1");
         
         commandBox.runCommand(COMMAND_WORD + " p:" + INVALID_PRIORITY);
         assertResultMessage("Invalid command format! \n" + COMMAND_WORD + ": List the task with the specified priority. "
                 + "Parameters: INDEX (must be between 0 and 3 inclusive).\n"
                 + "Example: list-priority 1");
         
         commandBox.runCommand(COMMAND_WORD + " " + TaskTime.DEFAULT_START_TIME);
         assertResultMessage("Invalid command format! \n" + COMMAND_WORD + ": List the task with the specified priority. "
                 + "Parameters: INDEX (must be between 0 and 3 inclusive).\n"
                 + "Example: list-priority 1");
         
         //invalid priority value
         commandBox.runCommand(COMMAND_WORD + " " + INVALID_PRIORITY);
         assertResultMessage(TaskPriority.MESSAGE_TASK_PRIORITY_CONSTRAINTS);
         
    }

    /**
     * Runs the list priority command to list tasks with a specified
     * priority and confirms the result is correct.
     * 
     * @param priority
     *            e.g. to list tasks with priority 1, "1"
     *            should be given as the priority.
     * @param currentList
     *            A copy of the current list of tasks (before prioritizing).
     * @param 
     * @throws IllegalValueException
     */
    private void assertlistPrioritySuccess(final TestTask[] currentList, String priority, TestTask... values)
            throws IllegalValueException {

        commandBox.runCommand(COMMAND_WORD + " " + priority);

        assertListSize(values.length);
        // confirm the result message is correct
        assertResultMessage(values.length + " tasks listed!");
        // confirm the list now contains all previous tasks except the edited
        // task
        assertTrue(taskListPanel.isListMatching(values));
        
    }

}
// @@author