//@@author A0148004R
package guitests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static seedu.taskell.logic.commands.DoneCommand.MESSAGE_DONE_TASK_SUCCESS;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.taskell.model.task.TaskStatus;
import seedu.taskell.testutil.TestTask;

public class DoneCommandTest extends TaskManagerGuiTest {
    
    @Test
    public void done() {
        
        //done the first item on the list
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        assertDoneSuccess(targetIndex, currentList);
        
        commandBox.runCommand("done " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");
    }
    
    /**
     * Runs the done command to finish the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to mark the first task in the list as finished, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before marking as ).
     */
    private void assertDoneSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        commandBox.runCommand("done " + targetIndexOneIndexed);
        TestTask taskToFinish = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        
        //confirm the new card contains the right data
        TaskCardHandle updatedCard = taskListPanel.navigateToTask(taskToFinish.getDescription().description);
        
        assertEquals(TaskStatus.FINISHED, updatedCard.getTaskComplete());
        
        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DONE_TASK_SUCCESS, taskToFinish));
    }

}
//@@author
