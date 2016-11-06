//@@author A0142073R
package guitests;

import org.junit.Test;

import static seedu.taskell.logic.commands.list.ListDateCommand.COMMAND_WORD;

import seedu.taskell.commons.exceptions.IllegalValueException;
import seedu.taskell.model.task.TaskDate;
import seedu.taskell.testutil.TestTask;

import static org.junit.Assert.assertTrue;

public class ListDateCommandTest extends TaskManagerGuiTest {

    private static final String INVALID_DATE = "123";

    @Test
    public void listDate_valid_thrownException() throws IllegalValueException {

        // list 2 tasks in the list
        TestTask[] currentList = td.getTypicalTasks();
        String date = "1-1-2100";
        assertListDateSuccess(currentList, date, td.archivePastEmails, td.borrowBooks, td.collectParcel,
                td.discardBooks, td.editPowerpoint, td.fixbugs, td.getNewUniform);

        // no tasks to list
        date = "1-1-2016";
        assertListDateSuccess(currentList, date);

    }
    
    @Test
    public void listDate_invalid_thrownException() throws IllegalValueException {

        // list 2 tasks in the list
        TestTask[] currentList = td.getTypicalTasks();
        String date = "1-1-2100";
        
        // no tasks to list
        date = "1-1-2016";
        assertListDateSuccess(currentList, date);

        // invalid command format
        commandBox.runCommand(COMMAND_WORD);
        assertResultMessage("Invalid command format! \n" + COMMAND_WORD + ": Lists tasks on 1 specific date only.\n"
                + "Parameters: DATE (must be a positive integer)\n" + "Example: " + "list-date" + " 8-8-2016 ");

        commandBox.runCommand(COMMAND_WORD + " " + INVALID_DATE);
        assertResultMessage("Invalid command format! \n" + COMMAND_WORD + ": Lists tasks on 1 specific date only.\n"
                + "Parameters: DATE (must be a positive integer)\n" + "Example: " + "list-date" + " 8-8-2016 ");

        commandBox.runCommand(COMMAND_WORD + " " + TaskDate.DEFAULT_DATE + " " + TaskDate.getTomorrowDate());
        assertResultMessage("Invalid command format! \n" + COMMAND_WORD + ": Lists tasks on 1 specific date only.\n"
                + "Parameters: DATE (must be a positive integer)\n" + "Example: " + "list-date" + " 8-8-2016 ");

    }

    /**
     * Runs the list date command to list tasks on a specified date and confirms
     * the result is correct.
     * 
     * @param date
     *            e.g. to list tasks on date "1-1-2016", "1-1-2016" should be
     *            given as the date.
     * @param currentList
     *            A copy of the current list of tasks (before listing).
     * @param
     * @throws IllegalValueException
     */
    private void assertListDateSuccess(final TestTask[] currentList, String date, TestTask... values)
            throws IllegalValueException {

        commandBox.runCommand(COMMAND_WORD + " " + date);

        assertListSize(values.length);
        // confirm the result message is correct
        assertResultMessage(values.length + " tasks listed!");
        // confirm the list now contains all previous tasks except the edited
        // task
        assertTrue(taskListPanel.isListMatching(values));
    }

}
// @@author