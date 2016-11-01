/** @@author A0142130A **/
package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.taskell.logic.commands.UndoCommand;
import seedu.taskell.model.History;
import seedu.taskell.model.HistoryManager;
import seedu.taskell.testutil.TestTask;
import seedu.taskell.testutil.TestUtil;

public class UndoCommandTest extends TaskManagerGuiTest {
    
    private static final String UNDO = "undo";
    private History history = HistoryManager.getInstance();
    
    @Test
    public void undoAdd() {
        history.clear();
        
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.holdMeeting;
        
        //add one task
        commandBox.runCommand(taskToAdd.getAddCommand());
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        
        commandBox.runCommand(UNDO);
        assertDeleteSuccess(currentList.length, currentList);
        
        history.clear();
    }
    
    @Test
    public void undoDelete() {
        history.clear();
        
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToDelete = currentList[0];
        int target = 1;
        
        //delete a task
        commandBox.runCommand("delete " + target);
        assertDeleteSuccess(target, currentList);
        currentList = TestUtil.removeTaskFromList(currentList, 1);
        
        commandBox.runCommand(UNDO);
        assertAddSuccess(taskToDelete, currentList);
        
        history.clear();
    }
    
    @Test
    public void undoAndRedoAdd() {
        history.clear();
        
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.holdMeeting;
        
        //add one task
        commandBox.runCommand(taskToAdd.getAddCommand());    
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        
        commandBox.runCommand(UNDO);
        assertDeleteSuccess(currentList.length, currentList);
        currentList = TestUtil.removeTaskFromList(currentList, currentList.length);
        
        commandBox.runCommand(UNDO);
        assertAddSuccess(taskToAdd, currentList);
        
        history.clear();
    }
    
    @Test
    public void undoAndRedoDelete() {
        history.clear();
        
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToDelete = currentList[0];
        int target = 1;
        
        //delete a task
        commandBox.runCommand("delete " + target);
        assertDeleteSuccess(target, currentList);
        currentList = TestUtil.removeTaskFromList(currentList, 1);
        
        commandBox.runCommand(UNDO);
        assertAddSuccess(taskToDelete, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToDelete);
        
        target = currentList.length;
        commandBox.runCommand(UNDO);
        assertDeleteSuccess(target, currentList);
        
        history.clear();
    }
    
    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getDescription().description);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    
    /**
     * Confirms the undo add result is correct. Means task has been deleted.
     * @param targetIndexOneIndexed e.g. to delete the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToDelete = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);

        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isListMatching(expectedRemainder)); 
    }
}
