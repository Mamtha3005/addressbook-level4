//@@author A0142073R
package guitests;

import org.junit.Test;

import seedu.taskell.commons.core.Messages;
import seedu.taskell.commons.exceptions.IllegalValueException;
import seedu.taskell.model.task.Description;
import seedu.taskell.model.task.TaskDate;
import seedu.taskell.model.task.TaskPriority;
import seedu.taskell.model.task.TaskTime;
import seedu.taskell.testutil.TestTask;
import seedu.taskell.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.taskell.logic.commands.EditCommand.MESSAGE_EDIT_TASK_SUCCESS;
import static seedu.taskell.logic.commands.EditCommand.MESSAGE_USAGE;
import static seedu.taskell.logic.commands.EditCommand.COMMAND_WORD;
import static seedu.taskell.logic.commands.EditCommand.MESSAGE_DATE_CONSTRAINTS;
import static seedu.taskell.logic.commands.EditCommand.MESSAGE_TIME_CONSTRAINTS;

public class EditCommandTest extends TaskManagerGuiTest {

    public static final String INVALID_PRIORITY = "4";
    public static final String INVALID_END_TIME = "12am";
    public static final String INVALID_START_TIME = "1159pm";
    public static final String INVALID_TIME = "1400";
    public static final String VALID_DATE = "30-10-2100";
    public static final String VALID_START_DATE = "1-12-2100";
    public static final String VALID_END_DATE = "10-12-2100";
    public static final String INVALID_DATE = "30Nov";

    @Test
    public void edit_desc_exceptionThrown() throws IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;

        // edit the description of first task in the list
        TestTask oldTask = currentList[targetIndex - 1];
        TestTask newTask = new TestTask(new Description("make tea"), oldTask.getTaskType(), oldTask.getTaskPriority(),
                oldTask.getStartTime(), oldTask.getEndTime(), oldTask.getStartDate(), oldTask.getEndDate(),
                oldTask.getRecurringType(), oldTask.getTaskStatus(), oldTask.getTags());

        commandBox.runCommand("edit " + targetIndex + " desc: make tea");
        currentList[0] = newTask;
        assertEditSuccess(targetIndex, currentList, oldTask, newTask);

        // edit the description of last task in the list
        targetIndex = currentList.length;
        oldTask = currentList[targetIndex - 1];
        newTask = new TestTask(new Description("finish software demo"), oldTask.getTaskType(),
                oldTask.getTaskPriority(), oldTask.getStartTime(), oldTask.getEndTime(), oldTask.getStartDate(),
                oldTask.getEndDate(), oldTask.getRecurringType(), oldTask.getTaskStatus(), oldTask.getTags());

        commandBox.runCommand(COMMAND_WORD + " " + targetIndex + " desc: finish software demo");
        assertEditSuccess(targetIndex, currentList, oldTask, newTask);

        // invalid index
        commandBox.runCommand(COMMAND_WORD + " " + currentList.length + 1 + " desc: go shopping");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // invalid command
        commandBox.runCommand(COMMAND_WORD);
        assertResultMessage("Invalid command format! \n" + MESSAGE_USAGE);

        commandBox.runCommand(COMMAND_WORD + " " + COMMAND_WORD);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        commandBox.runCommand(
                COMMAND_WORD + " " + targetIndex + " desc: finish homework " + " st: 8am " + " desc: submit homework");
        assertResultMessage("Invalid command format! \n" + MESSAGE_USAGE);

    }

    @Test
    public void edit_priority_exceptionThrown() throws IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;

        // edit the priority of first task in the list
        targetIndex = 1;
        TestTask oldTask = currentList[targetIndex - 1];
        TestTask newTask = new TestTask(oldTask.getDescription(), oldTask.getTaskType(),
                new TaskPriority(TaskPriority.DEFAULT_PRIORITY), oldTask.getStartTime(), oldTask.getEndTime(),
                oldTask.getStartDate(), oldTask.getEndDate(), oldTask.getRecurringType(), oldTask.getTaskStatus(),
                oldTask.getTags());

        commandBox.runCommand("edit " + targetIndex + " p: " + TaskPriority.DEFAULT_PRIORITY);
        currentList[0] = newTask;
        assertEditSuccess(targetIndex, currentList, oldTask, newTask);

        // edit the priority of last task in the list
        targetIndex = currentList.length;
        oldTask = currentList[targetIndex - 1];
        newTask = new TestTask(oldTask.getDescription(), oldTask.getTaskType(),
                new TaskPriority(TaskPriority.HIGH_PRIORITY), oldTask.getStartTime(), oldTask.getEndTime(),
                oldTask.getStartDate(), oldTask.getEndDate(), oldTask.getRecurringType(), oldTask.getTaskStatus(),
                oldTask.getTags());

        commandBox.runCommand("edit " + targetIndex + " p: " + TaskPriority.HIGH_PRIORITY);
        assertEditSuccess(targetIndex, currentList, oldTask, newTask);

        // invalid index
        commandBox.runCommand(COMMAND_WORD + " " + currentList.length + 1 + " p: " + TaskPriority.DEFAULT_PRIORITY);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // invalid command
        commandBox.runCommand(COMMAND_WORD + " " + targetIndex + " p:");
        assertResultMessage("Invalid command format! \n" + MESSAGE_USAGE);
        
        commandBox.runCommand(COMMAND_WORD + " " + targetIndex + " p: " + TaskPriority.DEFAULT_PRIORITY + " "+ TaskPriority.HIGH_PRIORITY);
        
        assertResultMessage("Invalid command format! \n" + MESSAGE_USAGE); 

        commandBox.runCommand(COMMAND_WORD + " " + targetIndex + " p: " + INVALID_PRIORITY);
        assertResultMessage("Invalid command format! \n" + TaskPriority.MESSAGE_TASK_PRIORITY_CONSTRAINTS);

        commandBox.runCommand(COMMAND_WORD + " " + targetIndex + " p: " + TaskPriority.DEFAULT_PRIORITY + " st: 8am"
                + " p: " + TaskPriority.HIGH_PRIORITY);
        assertResultMessage("Invalid command format! \n" + MESSAGE_USAGE);

    }

    @Test
    public void edit_startTime_exceptionThrown() throws IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;

        // edit the start time of first task in the list
        targetIndex = 1;
        TestTask oldTask = currentList[targetIndex - 1];
        TestTask newTask = new TestTask(oldTask.getDescription(), oldTask.getTaskType(), oldTask.getTaskPriority(),
                new TaskTime(TaskTime.MIDNIGHT), oldTask.getEndTime(), oldTask.getStartDate(), oldTask.getEndDate(),
                oldTask.getRecurringType(), oldTask.getTaskStatus(), oldTask.getTags());

        commandBox.runCommand("edit " + targetIndex + " st: " + TaskTime.MIDNIGHT);
        currentList[0] = newTask;
        assertEditSuccess(targetIndex, currentList, oldTask, newTask);

        // edit the start time of last task in the list
        targetIndex = currentList.length;
        oldTask = currentList[targetIndex - 1];
        newTask = new TestTask(oldTask.getDescription(), oldTask.getTaskType(), oldTask.getTaskPriority(),
                new TaskTime(TaskTime.DEFAULT_START_TIME), oldTask.getEndTime(), oldTask.getStartDate(),
                oldTask.getEndDate(), oldTask.getRecurringType(), oldTask.getTaskStatus(), oldTask.getTags());

        commandBox.runCommand("edit " + targetIndex + " st: " + TaskTime.DEFAULT_START_TIME);
        assertEditSuccess(targetIndex, currentList, oldTask, newTask);

        // invalid index
        commandBox.runCommand(COMMAND_WORD + " " + currentList.length + 1 + " st: " + TaskTime.DEFAULT_START_TIME);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // invalid command
        commandBox.runCommand(COMMAND_WORD + " " + targetIndex + " st:");
        assertResultMessage("Invalid command format! \n" + MESSAGE_USAGE);
        
        commandBox.runCommand(COMMAND_WORD + " " + targetIndex + " st: " + TaskTime.DEFAULT_START_TIME + " " + TaskTime.DEFAULT_END_TIME);
        assertResultMessage("Invalid command format! \n" + MESSAGE_USAGE); 

        commandBox.runCommand(COMMAND_WORD + " " + targetIndex + " st: " + INVALID_TIME);
        assertResultMessage("Invalid command format! \n" + TaskTime.MESSAGE_TASK_TIME_CONSTRAINTS);

        commandBox.runCommand(COMMAND_WORD + " " + targetIndex + " st: " + TaskTime.DEFAULT_START_TIME + " p: "
                + TaskPriority.DEFAULT_PRIORITY + " st: " + TaskTime.DEFAULT_START_TIME);
        assertResultMessage("Invalid command format! \n" + MESSAGE_USAGE);

    }

    @Test
    public void edit_endTime_exceptionThrown() throws IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;

        // edit the end time of first task in the list
        targetIndex = 1;
        TestTask oldTask = currentList[targetIndex - 1];
        TestTask newTask = new TestTask(oldTask.getDescription(), oldTask.getTaskType(), oldTask.getTaskPriority(),
                oldTask.getStartTime(), new TaskTime(TaskTime.DEFAULT_END_TIME), oldTask.getStartDate(),
                oldTask.getEndDate(), oldTask.getRecurringType(), oldTask.getTaskStatus(), oldTask.getTags());

        commandBox.runCommand("edit " + targetIndex + " et: " + TaskTime.DEFAULT_END_TIME);
        currentList[0] = newTask;
        assertEditSuccess(targetIndex, currentList, oldTask, newTask);

        // edit the end time of last task in the list
        targetIndex = currentList.length;
        oldTask = currentList[targetIndex - 1];
        newTask = new TestTask(oldTask.getDescription(), oldTask.getTaskType(), oldTask.getTaskPriority(),
                oldTask.getStartTime(), new TaskTime(TaskTime.DEFAULT_END_TIME), oldTask.getStartDate(),
                oldTask.getEndDate(), oldTask.getRecurringType(), oldTask.getTaskStatus(), oldTask.getTags());

        commandBox.runCommand("edit " + targetIndex + " et: " + TaskTime.DEFAULT_END_TIME);
        assertEditSuccess(targetIndex, currentList, oldTask, newTask);

        // invalid index
        commandBox.runCommand(COMMAND_WORD + " " + currentList.length + 1 + " et: 3pm");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // invalid command
        commandBox.runCommand(COMMAND_WORD + " " + targetIndex + " et:");
        assertResultMessage("Invalid command format! \n" + MESSAGE_USAGE);
        
        commandBox.runCommand(COMMAND_WORD + " " + targetIndex + " et: " + TaskTime.DEFAULT_END_TIME + " " + TaskTime.DEFAULT_START_TIME);
        assertResultMessage("Invalid command format! \n" + MESSAGE_USAGE); 

        commandBox.runCommand(COMMAND_WORD + " " + targetIndex + " et: " + INVALID_TIME);
        assertResultMessage("Invalid command format! \n" + TaskTime.MESSAGE_TASK_TIME_CONSTRAINTS);

        commandBox.runCommand(COMMAND_WORD + " " + targetIndex + " et: " + TaskTime.DEFAULT_START_TIME + " p: "
                + TaskPriority.DEFAULT_PRIORITY + " et: " + TaskTime.DEFAULT_START_TIME);
        assertResultMessage("Invalid command format! \n" + MESSAGE_USAGE);

    }

    @Test
    public void edit_endDate_exceptionThrown() throws IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;

        // edit the end date of first task in the list
        targetIndex = 1;
        TestTask oldTask = currentList[targetIndex - 1];
        TestTask newTask = new TestTask(oldTask.getDescription(), oldTask.getTaskType(), oldTask.getTaskPriority(),
                oldTask.getStartTime(), oldTask.getEndTime(), oldTask.getStartDate(), new TaskDate(VALID_END_DATE),
                oldTask.getRecurringType(), oldTask.getTaskStatus(), oldTask.getTags());

        commandBox.runCommand("edit " + targetIndex + " ed: " + VALID_END_DATE);
        currentList[0] = newTask;
        assertEditSuccess(targetIndex, currentList, oldTask, newTask);

        // invalid index
        commandBox.runCommand(COMMAND_WORD + " " + currentList.length + 1 + " ed: " + VALID_END_DATE);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // invalid command
        commandBox.runCommand(COMMAND_WORD + " " + targetIndex + " ed:");
        assertResultMessage("Invalid command format! \n" + MESSAGE_USAGE);
        
        commandBox.runCommand(COMMAND_WORD + " " + targetIndex + " ed: " + VALID_END_DATE + " " + VALID_END_DATE);
        assertResultMessage("Invalid command format! \n" + MESSAGE_USAGE); 

        commandBox.runCommand(COMMAND_WORD + " " + targetIndex + " ed: " + INVALID_DATE);
        assertResultMessage("Invalid command format! \n" + TaskDate.MESSAGE_TASK_DATE_CONSTRAINTS);

        commandBox.runCommand(COMMAND_WORD + " " + targetIndex + " ed: " + VALID_END_DATE + " st: "
                + TaskTime.DEFAULT_START_TIME + " ed: " + VALID_END_DATE);
        assertResultMessage("Invalid command format! \n" + MESSAGE_USAGE);

    }

    @Test
    public void edit_startDate_exceptionThrown() throws IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;

        // edit the start date of first task in the list
        targetIndex = 1;
        TestTask oldTask = currentList[targetIndex - 1];
        TestTask newTask = new TestTask(oldTask.getDescription(), oldTask.getTaskType(), oldTask.getTaskPriority(),
                oldTask.getStartTime(), oldTask.getEndTime(), new TaskDate(VALID_DATE), oldTask.getEndDate(),
                oldTask.getRecurringType(), oldTask.getTaskStatus(), oldTask.getTags());

        commandBox.runCommand("edit " + targetIndex + " sd: " + VALID_DATE);
        currentList[0] = newTask;
        assertEditSuccess(targetIndex, currentList, oldTask, newTask);

        // edit the start date of last task in the list
        targetIndex = currentList.length;
        oldTask = currentList[targetIndex - 1];
        newTask = new TestTask(oldTask.getDescription(), oldTask.getTaskType(), oldTask.getTaskPriority(),
                oldTask.getStartTime(), oldTask.getEndTime(), new TaskDate(VALID_DATE), oldTask.getEndDate(),
                oldTask.getRecurringType(), oldTask.getTaskStatus(), oldTask.getTags());

        commandBox.runCommand("edit " + targetIndex + " sd: " + VALID_DATE);
        assertEditSuccess(targetIndex, currentList, oldTask, newTask);

        // invalid index
        commandBox.runCommand(COMMAND_WORD + " " + currentList.length + 1 + " sd: " + VALID_DATE);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // invalid command
        commandBox.runCommand(COMMAND_WORD + " " + targetIndex + " sd:");
        assertResultMessage("Invalid command format! \n" + MESSAGE_USAGE);
        
        commandBox.runCommand(COMMAND_WORD + " " + targetIndex + " sd: " + VALID_START_DATE + " " + VALID_END_DATE);
        assertResultMessage("Invalid command format! \n" + MESSAGE_USAGE); 

        commandBox.runCommand(COMMAND_WORD + " " + targetIndex + " sd: " + INVALID_DATE);
        assertResultMessage("Invalid command format! \n" + TaskDate.MESSAGE_TASK_DATE_CONSTRAINTS);

        commandBox.runCommand(COMMAND_WORD + " " + targetIndex + " sd: " + VALID_DATE + " st: "
                + TaskTime.DEFAULT_START_TIME + " sd: " + VALID_DATE);
        assertResultMessage("Invalid command format! \n" + MESSAGE_USAGE);

    }

    @Test
    public void edit_fewPart_exceptionThrown() throws IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;

        // edit the description, start time and priority of first task in the
        // list
        TestTask oldTask = currentList[targetIndex - 1];
        TestTask newTask = new TestTask(new Description("send emails"), oldTask.getTaskType(),
                new TaskPriority(TaskPriority.DEFAULT_PRIORITY), new TaskTime("12am"), oldTask.getEndTime(),
                oldTask.getStartDate(), oldTask.getEndDate(), oldTask.getRecurringType(), oldTask.getTaskStatus(),
                oldTask.getTags());

        commandBox.runCommand("edit " + targetIndex + " desc: send emails st: " + TaskTime.DEFAULT_START_TIME + " p: "
                + TaskPriority.DEFAULT_PRIORITY);
        currentList[0] = newTask;
        assertEditSuccess(targetIndex, currentList, oldTask, newTask);

        // edit start time and end time of first task in the
        // list
        oldTask = currentList[targetIndex - 1];
        newTask = new TestTask(oldTask.getDescription(), oldTask.getTaskType(), oldTask.getTaskPriority(),
                new TaskTime(TaskTime.DEFAULT_START_TIME), new TaskTime(TaskTime.DEFAULT_END_TIME),
                oldTask.getStartDate(), oldTask.getEndDate(), oldTask.getRecurringType(), oldTask.getTaskStatus(),
                oldTask.getTags());

        commandBox.runCommand(
                "edit " + targetIndex + " st: " + TaskTime.DEFAULT_START_TIME + " et: " + TaskTime.DEFAULT_END_TIME);
        currentList[0] = newTask;
        assertEditSuccess(targetIndex, currentList, oldTask, newTask);

        // edit start date and end date of first task in the
        // list
        oldTask = currentList[targetIndex - 1];
        newTask = new TestTask(oldTask.getDescription(), oldTask.getTaskType(), oldTask.getTaskPriority(),
                oldTask.getStartTime(), oldTask.getEndTime(), new TaskDate(VALID_START_DATE),
                new TaskDate(VALID_END_DATE), oldTask.getRecurringType(), oldTask.getTaskStatus(), oldTask.getTags());

        commandBox.runCommand("edit " + targetIndex + " sd: " + VALID_START_DATE + " ed: " + VALID_END_DATE);
        currentList[0] = newTask;
        assertEditSuccess(targetIndex, currentList, oldTask, newTask);

        // invalid command
        commandBox.runCommand("edit " + targetIndex + " st: st: st: ");
        currentList[0] = newTask;
        assertResultMessage("Invalid command format! \n" + TaskTime.MESSAGE_TASK_TIME_CONSTRAINTS);

        commandBox.runCommand("edit " + targetIndex + " st: " + TaskTime.DEFAULT_START_TIME + " st: "
                + TaskTime.DEFAULT_START_TIME + " st: " + TaskTime.DEFAULT_START_TIME);
        currentList[0] = newTask;
        assertResultMessage("Invalid command format! \n" + MESSAGE_USAGE);

        commandBox.runCommand(COMMAND_WORD + " " + targetIndex + " sd: " + VALID_DATE + " st: "
                + TaskTime.DEFAULT_START_TIME + " sd: " + VALID_DATE);
        assertResultMessage("Invalid command format! \n" + MESSAGE_USAGE);
    }

    /**
     * Runs the edit command to edit the description of a task at specified
     * index and confirms the result is correct.
     * 
     * @param targetIndexOneIndexed
     *            e.g. to edit the first task in the list, 1 should be given as
     *            the target index.
     * @param currentList
     *            A copy of the current list of tasks (before deletion).
     * @param oldTask
     *            A copy of the old task which is not updated
     * @param newTask
     *            A copy of the updated task
     * @throws IllegalValueException
     */
    private void assertEditSuccess(int targetIndexOneIndexed, final TestTask[] currentList, TestTask oldTask,
            TestTask newTask) throws IllegalValueException {

        TestTask[] current = TestUtil.replaceTaskFromList(currentList, newTask, targetIndexOneIndexed - 1);
        // confirm the list now contains all previous tasks except the edited
        // task
        assertTrue(taskListPanel.isListMatching(current));

        // confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_EDIT_TASK_SUCCESS, oldTask, newTask));
    }

}

// @@author