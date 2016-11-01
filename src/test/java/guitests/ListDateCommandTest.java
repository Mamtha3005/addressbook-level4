//@@author A0142073R
package guitests;

import org.junit.Test;

import seedu.taskell.commons.exceptions.IllegalValueException;
import seedu.taskell.testutil.TestTask;

import static org.junit.Assert.assertTrue;

public class ListDateCommandTest extends TaskManagerGuiTest {

    @Test
    public void listDate() throws IllegalValueException {

        // list 2 tasks in the list
        TestTask[] currentList = td.getTypicalTasks();
        String date = "1-1-2100";
        assertlistDateSuccess(currentList, date, td.archivePastEmails, td.borrowBooks, td.collectParcel,
                td.discardBooks, td.editPowerpoint, td.fixbugs, td.getNewUniform);

        // no tasks to list
        date = "1-1-2016";
        assertlistDateSuccess(currentList, date);

        // invalid command format
        commandBox.runCommand("list-date");
        assertResultMessage("Invalid command format! \n" + "list-date" + ": Lists tasks on a specific date.\n"
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
    private void assertlistDateSuccess(final TestTask[] currentList, String date, TestTask... values)
            throws IllegalValueException {

        commandBox.runCommand("list-date " + date);

        assertListSize(values.length);
        // confirm the result message is correct
        assertResultMessage(values.length + " tasks listed!");
        // confirm the list now contains all previous tasks except the edited
        // task
        assertTrue(taskListPanel.isListMatching(values));
    }

}
//@@author