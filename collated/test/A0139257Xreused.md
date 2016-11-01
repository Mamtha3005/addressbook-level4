# A0139257Xreused
###### \java\guitests\AddCommandTest.java
``` java
public class AddCommandTest extends TaskManagerGuiTest {

    @Test
    public void add() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.holdMeeting;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.inspectWarehouse;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(td.holdMeeting.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.archivePastEmails);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        
       //add valid floating task
        commandBox.runCommand("clear");
        assertAddFloatingSuccess(td.floatingTask_Valid);
        
        commandBox.runCommand("clear");
        assertAddFloatingSuccess(td.floatingTask_NonIntuitiveDescription);
        
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getDescription().description);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    
    private void assertAddFloatingSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddFloatingCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getDescription().description);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
```
###### \java\seedu\taskell\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_add_invalidTaskData() throws Exception {
        assertCommandBehavior(
                "add #descriptionIsEmpty", Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Description with invalid startDate format by 1-jan-16", TaskDate.MESSAGE_TASK_DATE_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Description with start date before today's date on 1-jan-2000", EventTask.MESSAGE_EVENT_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Description with end date before today's date by 1-jan-2000", EventTask.MESSAGE_EVENT_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Description with startDate after endDate from 1-jan-2200 to 1-jan-2100", EventTask.MESSAGE_EVENT_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Description with startTime before Today's current time at 2am", EventTask.MESSAGE_EVENT_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Description p/invalidPriority ", TaskPriority.MESSAGE_TASK_PRIORITY_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Description p/0 p/1 ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        assertCommandBehavior(
                "add Valid Description #invalid_-[.tag", Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.askBoon();
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());
    }
    
```
###### \java\seedu\taskell\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.askBoon();
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);

        // setup starting state
        model.addTask(toBeAdded); // task already in internal task manager

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                AddCommand.MESSAGE_DUPLICATE_TASK,
                expectedAB,
                expectedAB.getTaskList());

    }
```
