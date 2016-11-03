package seedu.taskell.logic;

import com.google.common.eventbus.Subscribe;

import seedu.taskell.commons.core.EventsCenter;
import seedu.taskell.commons.events.model.TaskManagerChangedEvent;
import seedu.taskell.commons.events.ui.JumpToListRequestEvent;
import seedu.taskell.commons.events.ui.ShowHelpRequestEvent;
import seedu.taskell.commons.exceptions.IllegalValueException;
import seedu.taskell.logic.Logic;
import seedu.taskell.logic.LogicManager;
import seedu.taskell.logic.commands.*;
import seedu.taskell.logic.commands.list.ListAllCommand;
import seedu.taskell.model.TaskManager;
import seedu.taskell.model.Model;
import seedu.taskell.model.ModelManager;
import seedu.taskell.model.ReadOnlyTaskManager;
import seedu.taskell.model.tag.Tag;
import seedu.taskell.model.tag.UniqueTagList;
import seedu.taskell.model.task.*;
import seedu.taskell.storage.StorageManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.taskell.commons.core.Messages.*;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    // These are for checking the correctness of the events raised
    private ReadOnlyTaskManager latestSavedTaskManager;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(TaskManagerChangedEvent abce) {
        latestSavedTaskManager = new TaskManager(abce.data);
    }

    @Subscribe
    private void handleShowHelpRequestEvent(ShowHelpRequestEvent she) {
        helpShown = true;
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent je) {
        targetedJumpIndex = je.targetIndex;
    }

    @Before
    public void setup() {
        model = new ModelManager();
        String tempTaskManagerFile = saveFolder.getRoot().getPath() + "TempTaskManager.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempTaskManagerFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedTaskManager = new TaskManager(model.getTaskManager()); // last
                                                                          // saved
                                                                          // assumed
                                                                          // to
                                                                          // be
                                                                          // up
                                                                          // to
                                                                          // startDate
                                                                          // before.
        helpShown = false;
        targetedJumpIndex = -1; // non yet

        ClearCommand.setIsUnderTesting(true);
    }

    @After
    public void teardown() {
        EventsCenter.clearSubscribers();
    }

    @Test
    public void execute_invalid() throws Exception {
        String invalidCommand = "       ";
        assertCommandBehavior(invalidCommand, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command and confirms that the result message is correct.
     * Both the 'task manager' and the 'last shown list' are expected to be
     * empty.
     * 
     * @see #assertCommandBehavior(String, String, ReadOnlyTaskManager, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new TaskManager(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's
     * state are as expected:<br>
     * - the internal task manager data are same as those in the
     * {@code expectedTaskManager} <br>
     * - the backing list shown by UI matches the {@code shownList} <br>
     * - {@code expectedTaskManager} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage,
            ReadOnlyTaskManager expectedTaskManager, List<? extends ReadOnlyTask> expectedShownList) throws Exception {

        // Execute the command
        CommandResult result = logic.execute(inputCommand);

        // Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredTaskList());

        // Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskManager, model.getTaskManager());
        assertEquals(expectedTaskManager, latestSavedTaskManager);
    }

    @Test
    public void execute_unknownCommandWord() throws Exception {
        String unknownCommand = "uicfhmowqewca";
        assertCommandBehavior(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_help() throws Exception {
        assertCommandBehavior("help", HelpCommand.SHOWING_HELP_MESSAGE);
        assertTrue(helpShown);
    }

    @Test
    public void execute_exit() throws Exception {
        assertCommandBehavior("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

    @Test
    public void execute_clear() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        model.addTask(helper.generateTask(1));
        model.addTask(helper.generateTask(2));
        model.addTask(helper.generateTask(3));

        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new TaskManager(), Collections.emptyList());
    }

    @Test
    public void execute_add_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandBehavior("add", expectedMessage);
    }

    // @@author A0139257X-reused
    @Test
    public void execute_add_invalidTaskData() throws Exception {
        assertCommandBehavior("add #descriptionIsEmpty", Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
        assertCommandBehavior("add Valid Description with invalid startDate format by 1-jan-16",
                TaskDate.MESSAGE_TASK_DATE_CONSTRAINTS);
        assertCommandBehavior("add Valid Description with start date before today's date on 1-jan-2000",
                EventTask.MESSAGE_EVENT_CONSTRAINTS);
        assertCommandBehavior("add Valid Description with end date before today's date by 1-jan-2000",
                EventTask.MESSAGE_EVENT_CONSTRAINTS);
        assertCommandBehavior("add Valid Description with startDate after endDate from 1-jan-2200 to 1-jan-2100",
                EventTask.MESSAGE_EVENT_CONSTRAINTS);
        assertCommandBehavior("add Valid Description with startTime before Today's current time at 12am",
                EventTask.MESSAGE_EVENT_CONSTRAINTS);
        assertCommandBehavior("add Valid Description p/invalidPriority ",
                TaskPriority.MESSAGE_TASK_PRIORITY_CONSTRAINTS);
        assertCommandBehavior("add Valid Description p/0 p/1 ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        assertCommandBehavior("add Valid Description #invalid_-[.tag", Tag.MESSAGE_TAG_CONSTRAINTS);
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
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB, expectedAB.getTaskList());
    }

    // @@author A0139257X
    @Test
    public void execute_add_ValidFloatingTaskWithKeywords_success() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.generateFloatingTask("on by on at from to", "0", "neverRecur");
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddFloatingTaskCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB, expectedAB.getTaskList());
    }

    @Test
    public void execute_add_ValidFloatingTaskWithKeywordsButNoValidDateTime_success() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.generateFloatingTask("sleep by the seaside", "0", "neverRecur");
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddFloatingTaskCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB, expectedAB.getTaskList());
    }

    @Test
    public void execute_add_ValidTaskWithImproperUsageOfAt_success() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.generateFloatingTask("go shopping at monday", "0", "neverRecur");
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddFloatingTaskCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB, expectedAB.getTaskList());
    }

    @Test
    public void execute_add_ValidTaskWithValidDateButNoPreFix_success() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.generateFloatingTask("go shopping today", "0", "neverRecur");
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddFloatingTaskCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB, expectedAB.getTaskList());
    }

    @Test
    public void execute_add_ValidTaskWithValidTimeButNoPreFix_success() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.generateFloatingTask("go shopping 7pm", "0", "neverRecur");
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddFloatingTaskCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB, expectedAB.getTaskList());
    }

    @Test
    public void execute_add_ValidTaskWithImproperUsageOfOn_success() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.generateFloatingTask("go shopping on 7pm", "0", "neverRecur");
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddFloatingTaskCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB, expectedAB.getTaskList());
    }

    @Test
    public void execute_add_ValidTaskWithMultipleOn_success() throws Exception {
        String description = "add go shopping on 2-2-2222 on 3-3-3333";
        Task toBeAdded = new EventTask("go shopping on 3-3-3333", "2-2-2222", "2-2-2222", TaskTime.DEFAULT_START_TIME,
                TaskTime.DEFAULT_END_TIME, TaskPriority.DEFAULT_PRIORITY, RecurringType.NO_RECURRING,
                TaskStatus.INCOMPLETE, new UniqueTagList());
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);
        // execute command and verify result
        assertCommandBehavior(description, String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_add_ValidTaskWithMultipleAt_success() throws Exception {
        String description = "add go shopping at 11.58pm at 11.59pm";
        Task toBeAdded = new EventTask("go shopping at 11.59pm", TaskDate.DEFAULT_DATE, TaskDate.DEFAULT_DATE,
                "11.58pm", TaskTime.DEFAULT_END_TIME, TaskPriority.DEFAULT_PRIORITY, RecurringType.NO_RECURRING,
                TaskStatus.INCOMPLETE, new UniqueTagList());
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);
        // execute command and verify result
        assertCommandBehavior(description, String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_add_ValidTaskWithMultipleByDate_success() throws Exception {
        String description = "add go shopping by 2-2-2222 by 3-3-3333";
        Task toBeAdded = new EventTask("go shopping by 3-3-3333", TaskDate.DEFAULT_DATE, "2-2-2222",
                TaskTime.getTimeNow().toString(), TaskTime.DEFAULT_END_TIME, TaskPriority.DEFAULT_PRIORITY,
                RecurringType.NO_RECURRING, TaskStatus.INCOMPLETE, new UniqueTagList());
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);
        // execute command and verify result
        assertCommandBehavior(description, String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_add_ValidTaskWithMultipleByTime_success() throws Exception {
        String description = "add go shopping by 11.58pm by 11.59pm";
        Task toBeAdded = new EventTask("go shopping by 11.59pm", TaskDate.DEFAULT_DATE, TaskDate.DEFAULT_DATE,
                TaskTime.getTimeNow().toString(), "11.58pm", TaskPriority.DEFAULT_PRIORITY, RecurringType.NO_RECURRING,
                TaskStatus.INCOMPLETE, new UniqueTagList());
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);
        // execute command and verify result
        assertCommandBehavior(description, String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_add_ValidTaskWithMultipleFromDate_success() throws Exception {
        String description = "add go shopping from 2-2-2222 from 3-3-3333";
        Task toBeAdded = new EventTask("go shopping from 3-3-3333", "2-2-2222", "2-2-2222", TaskTime.DEFAULT_START_TIME,
                TaskTime.DEFAULT_END_TIME, TaskPriority.DEFAULT_PRIORITY, RecurringType.NO_RECURRING,
                TaskStatus.INCOMPLETE, new UniqueTagList());
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);
        // execute command and verify result
        assertCommandBehavior(description, String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_add_ValidTaskWithMultipleFromTime_success() throws Exception {
        String description = "add go shopping from 11.58pm from 11.59pm";
        Task toBeAdded = new EventTask("go shopping from 11.59pm", TaskDate.DEFAULT_DATE, TaskDate.DEFAULT_DATE,
                "11.58pm", TaskTime.DEFAULT_END_TIME, TaskPriority.DEFAULT_PRIORITY, RecurringType.NO_RECURRING,
                TaskStatus.INCOMPLETE, new UniqueTagList());
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);
        // execute command and verify result
        assertCommandBehavior(description, String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_add_ValidTaskWithMultipleToDate_success() throws Exception {
        String description = "add go shopping to 2-2-2222 to 3-3-3333";
        Task toBeAdded = new EventTask("go shopping to 3-3-3333", TaskDate.DEFAULT_DATE, "2-2-2222",
                TaskTime.getTimeNow().toString(), TaskTime.DEFAULT_END_TIME, TaskPriority.DEFAULT_PRIORITY,
                RecurringType.NO_RECURRING, TaskStatus.INCOMPLETE, new UniqueTagList());
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);
        // execute command and verify result
        assertCommandBehavior(description, String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_add_ValidTaskWithMultipleToTime_success() throws Exception {
        String description = "add go shopping to 11.58pm to 11.59pm";
        Task toBeAdded = new EventTask("go shopping to 11.59pm", TaskDate.DEFAULT_DATE, TaskDate.DEFAULT_DATE,
                TaskTime.getTimeNow().toString(), "11.58pm", TaskPriority.DEFAULT_PRIORITY, RecurringType.NO_RECURRING,
                TaskStatus.INCOMPLETE, new UniqueTagList());
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);
        // execute command and verify result
        assertCommandBehavior(description, String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_add_ValidEventWithByTime_success() throws Exception {
        String description = "add go shopping by 11:59pm";
        Task toBeAdded = new EventTask("go shopping", TaskDate.getTodayDate().toString(),
                TaskDate.getTodayDate().toString(), TaskTime.getTimeNow().toString(), "11:59pm",
                TaskPriority.DEFAULT_PRIORITY, RecurringType.NO_RECURRING, TaskStatus.INCOMPLETE, new UniqueTagList());
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);
        // execute command and verify result
        assertCommandBehavior(description, String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_add_ValidEventWithAtTime_success() throws Exception {
        String description = "add go shopping at 11:59pm";
        Task toBeAdded = new EventTask("go shopping", TaskDate.getTodayDate().toString(),
                TaskDate.getTodayDate().toString(), "11:59pm", TaskTime.DEFAULT_END_TIME, TaskPriority.DEFAULT_PRIORITY,
                RecurringType.NO_RECURRING, TaskStatus.INCOMPLETE, new UniqueTagList());
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);
        // execute command and verify result
        assertCommandBehavior(description, String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_add_ValidEventWithTagNotAtTheEnd_success() throws Exception {
        String description = "add go to #girlfriend Mavis's house at 11:58pm";
        Task toBeAdded = new EventTask("go to Mavis's house", TaskDate.getTodayDate().toString(),
                TaskDate.getTodayDate().toString(), "11:58pm", TaskTime.DEFAULT_END_TIME, TaskPriority.DEFAULT_PRIORITY,
                RecurringType.NO_RECURRING, TaskStatus.INCOMPLETE, new UniqueTagList(new Tag("girlfriend")));
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);
        // execute command and verify result
        assertCommandBehavior(description, String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_add_ValidEventWithEndTimeBeforeStartTime_success() throws Exception {
        String description = "add stayover at Juliet's house from 11.58pm to 10am";
        Task toBeAdded = new EventTask("stayover at Juliet's house", TaskDate.getTodayDate().toString(),
                TaskDate.getTodayDate().toString(), "11:58pm", "10:00am", TaskPriority.DEFAULT_PRIORITY,
                RecurringType.NO_RECURRING, TaskStatus.INCOMPLETE, new UniqueTagList());
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);
        // execute command and verify result
        assertCommandBehavior(description, String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_add_ValidEventWithSameDayNameInWeekAsToday_success() throws Exception {
        TaskDate today = new TaskDate(TaskDate.getTodayDate().toString());
        TaskDate tomorrow = new TaskDate(TaskDate.getTomorrowDate().toString());
        String todayNameInWeek = today.getDayNameInWeek();
        String tomorrowNameInWeek = tomorrow.getDayNameInWeek();
        String description = "add go school from " + todayNameInWeek + " to " + tomorrowNameInWeek;
        Task toBeAdded = new EventTask("go school", today.getNextWeek().toString(), tomorrow.toString(),
                TaskTime.DEFAULT_START_TIME, TaskTime.DEFAULT_END_TIME, TaskPriority.DEFAULT_PRIORITY,
                RecurringType.NO_RECURRING, TaskStatus.INCOMPLETE, new UniqueTagList());
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);
        // execute command and verify result
        assertCommandBehavior(description, String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_add_ValidEventDuration_successful_success() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.validEventDuration();
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB, expectedAB.getTaskList());
    }

    // @@author A0139257X-reused
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
        assertCommandBehavior(helper.generateAddCommand(toBeAdded), AddCommand.MESSAGE_DUPLICATE_TASK, expectedAB,
                expectedAB.getTaskList());

    }
    // @@author

    // @@author A0142073R-reused

    @Test
    public void execute_edit_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);
        assertCommandBehavior(EditCommand.COMMAND_WORD, expectedMessage);
        assertCommandBehavior(EditCommand.COMMAND_WORD + " 1 " + EditCommand.COMMAND_WORD, expectedMessage);
    }

    // @@author

    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TaskManager expectedAB = helper.generateTaskManager(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare task manager state
        helper.addToModel(model, 2);

        assertCommandBehavior("list-all", ListAllCommand.MESSAGE_SUCCESS, expectedAB, expectedList);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given
     * command targeting a single task in the shown list, using visible index.
     * 
     * @param commandWord
     *            to test assuming it targets a single task in the last shown
     *            list based on visible index.
     */
    private void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage)
            throws Exception {
        assertCommandBehavior(commandWord, expectedMessage); // index missing
        assertCommandBehavior(commandWord + " +1", expectedMessage); // index
                                                                     // should
                                                                     // be
                                                                     // unsigned
        assertCommandBehavior(commandWord + " -1", expectedMessage); // index
                                                                     // should
                                                                     // be
                                                                     // unsigned
        assertCommandBehavior(commandWord + " 0", expectedMessage); // index
                                                                    // cannot be
                                                                    // 0
        assertCommandBehavior(commandWord + " not_a_number", expectedMessage);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given
     * command targeting a single task in the shown list, using visible index.
     * 
     * @param commandWord
     *            to test assuming it targets a single task in the last shown
     *            list based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

        // set AB state to 2 tasks
        model.resetData(new TaskManager());
        for (Task p : taskList) {
            model.addTask(p);
        }

        assertCommandBehavior(commandWord + " 3", expectedMessage, model.getTaskManager(), taskList);
    }

    @Test
    public void execute_selectInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("select", expectedMessage);
    }

    @Test
    public void execute_selectIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("select");
    }

    @Test
    public void execute_select_jumpsToCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("select 2", String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2), expectedAB,
                expectedAB.getTaskList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredTaskList().get(1), threeTasks.get(1));
    }

    @Test
    public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
    }

    @Test
    public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("delete");
    }

    @Test
    public void execute_delete_removesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        expectedAB.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("delete 2", String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
                expectedAB, expectedAB.getTaskList());
    }

    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p1 = helper.generateTaskWithName("KE Y");
        Task p2 = helper.generateTaskWithName("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        TaskManager expectedAB = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find KEY", Command.getMessageForTaskListShownSummary(expectedList.size()), expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithName("bla bla KEY bla");
        Task p2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p3 = helper.generateTaskWithName("key key");
        Task p4 = helper.generateTaskWithName("KEy sduauo");

        List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        TaskManager expectedAB = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = fourTasks;
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find KEY", Command.getMessageForTaskListShownSummary(expectedList.size()), expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_matchesIfAllKeywordsPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();

        Task pTarget = helper.generateTaskWithName("bla KEY rAnDoM bla bceofeia");
        Task p1 = helper.generateTaskWithName("sduauo");
        Task p2 = helper.generateTaskWithName("bla bla KEY bla");
        Task p3 = helper.generateTaskWithName("key key");

        List<Task> oneTask = helper.generateTaskList(pTarget);
        TaskManager expectedAB = helper.generateTaskManager(oneTask);
        List<Task> expectedList = helper.generateTaskList(pTarget);
        helper.addToModel(model, oneTask);

        assertCommandBehavior("find key rAnDoM", Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB, expectedList);
    }

    /**
     * A utility class to generate test data.
     */
    class TestDataHelper {

        Task askBoon() throws Exception {
            Description description = new Description("Ask boon for tax rebate");
            String taskType = Task.EVENT_TASK;
            TaskDate startDate = new TaskDate("1-1-2100");
            TaskDate endDate = new TaskDate("1-12-2100");
            TaskTime startTime = new TaskTime("12:30AM");
            TaskTime endTime = new TaskTime("12:45AM");
            TaskPriority privatetaskPriority = new TaskPriority("0");
            RecurringType recurringType = new RecurringType(RecurringType.NO_RECURRING);
            TaskStatus initialStatus = new TaskStatus(TaskStatus.INCOMPLETE);
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("tag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(description, taskType, startDate, endDate, startTime, endTime, privatetaskPriority,
                    recurringType, initialStatus, tags);
        }

        Task validEventDuration() throws Exception {
            Description description = new Description("StartDate is before EndDate but startTime is afterEndTime");
            String taskType = Task.EVENT_TASK;
            TaskDate startDate = new TaskDate("1-1-2100");
            TaskDate endDate = new TaskDate("1-12-2100");
            TaskTime startTime = new TaskTime("2:30pm");
            TaskTime endTime = new TaskTime("3:45AM");
            TaskPriority privatetaskPriority = new TaskPriority("0");
            RecurringType recurringType = new RecurringType(RecurringType.NO_RECURRING);
            TaskStatus initialStatus = new TaskStatus(TaskStatus.INCOMPLETE);
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("tag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(description, taskType, startDate, endDate, startTime, endTime, privatetaskPriority,
                    recurringType, initialStatus, tags);
        }

        /**
         * Generates a valid task using the given seed. Running this function
         * with the same parameter values guarantees the returned task will have
         * the same state. Each unique seed will generate a unique Task object.
         *
         * @param seed
         *            used to generate the task data field values
         */
        Task generateTask(int seed) throws Exception {
            return new Task(new Description("Task " + seed), Task.EVENT_TASK, new TaskDate("1-1-2100"),
                    new TaskDate("1-12-2100"), new TaskTime("12:30AM"), new TaskTime("12:45AM"),
                    new TaskPriority((seed % 4) + ""), new RecurringType(RecurringType.NO_RECURRING),
                    new TaskStatus(TaskStatus.INCOMPLETE),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1))));
        }

        /**
         * Generate event task with the given parameters
         */
        Task generateEventTask(String description, String startDate, String endDate, String startTime, String endTime,
                String taskPriority, String recurringType, String taskStatus) throws Exception {
            return new Task(new Description(description), Task.EVENT_TASK, new TaskDate(startDate),
                    new TaskDate(endDate), new TaskTime(startTime), new TaskTime(endTime),
                    new TaskPriority(taskPriority), new RecurringType(recurringType), new TaskStatus(taskStatus),
                    new UniqueTagList(new Tag("tag" + Math.abs(1)), new Tag("tag" + Math.abs(2))));
        }

        /**
         * Generate floating task with the given parameters
         */
        Task generateFloatingTask(String description, String taskPriority, String recurringType) throws Exception {
            return new Task(new Description(description), Task.FLOATING_TASK, new TaskDate(TaskDate.DEFAULT_DATE),
                    new TaskDate(TaskDate.DEFAULT_DATE), new TaskTime(TaskTime.DEFAULT_START_TIME),
                    new TaskTime(TaskTime.DEFAULT_END_TIME), new TaskPriority(taskPriority),
                    new RecurringType(recurringType), new TaskStatus(TaskStatus.INCOMPLETE),
                    new UniqueTagList(new Tag("tag" + Math.abs(1)), new Tag("tag" + Math.abs(2))));
        }

        /** Generates the correct add command based on the task given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");
            cmd.append(p.getDescription().toString());
            cmd.append(" from ").append(p.getStartDate());
            cmd.append(" to ").append(p.getEndDate());
            cmd.append(" from ").append(p.getStartTime());
            cmd.append(" to ").append(p.getEndTime());
            cmd.append(" " + TaskPriority.PREFIX).append(p.getTaskPriority());
            cmd.append(" " + RecurringType.PREFIX).append(p.getRecurringType());

            UniqueTagList tags = p.getTags();
            for (Tag t : tags) {
                cmd.append(" " + Tag.PREFIX).append(t.tagName);
            }

            return cmd.toString();
        }

        /**
         * Generates the correct add command based on the floating task given
         */
        String generateAddFloatingTaskCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");
            cmd.append(p.getDescription().toString());
            cmd.append(" " + TaskPriority.PREFIX).append(p.getTaskPriority());

            UniqueTagList tags = p.getTags();
            for (Tag t : tags) {
                cmd.append(" " + Tag.PREFIX).append(t.tagName);
            }

            return cmd.toString();
        }

        /**
         * Generates an TaskManager with auto-generated tasks.
         */
        TaskManager generateTaskManager(int numGenerated) throws Exception {
            TaskManager taskManager = new TaskManager();
            addToTaskManager(taskManager, numGenerated);
            return taskManager;
        }

        /**
         * Generates an TaskManager based on the list of Tasks given.
         */
        TaskManager generateTaskManager(List<Task> tasks) throws Exception {
            TaskManager taskManager = new TaskManager();
            addToTaskManager(taskManager, tasks);
            return taskManager;
        }

        /**
         * Adds auto-generated Task objects to the given TaskManager
         * 
         * @param taskManager
         *            The TaskManager to which the Tasks will be added
         */
        void addToTaskManager(TaskManager taskManager, int numGenerated) throws Exception {
            addToTaskManager(taskManager, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given TaskManager
         */
        void addToTaskManager(TaskManager taskManager, List<Task> tasksToAdd) throws Exception {
            for (Task p : tasksToAdd) {
                taskManager.addTask(p);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * 
         * @param model
         *            The model to which the Tasks will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception {
            addToModel(model, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given model
         */
        void addToModel(Model model, List<Task> tasksToAdd) throws Exception {
            for (Task p : tasksToAdd) {
                model.addTask(p);
            }
        }

        /**
         * Generates a list of Tasks based on the flags.
         */
        List<Task> generateTaskList(int numGenerated) throws Exception {
            List<Task> tasks = new ArrayList<>();
            for (int i = 1; i <= numGenerated; i++) {
                tasks.add(generateTask(i));
            }
            return tasks;
        }

        List<Task> generateTaskList(Task... tasks) {
            return Arrays.asList(tasks);
        }

        /**
         * Generates a Task object with given description. Other fields will
         * have some dummy values.
         */

        Task generateTaskWithName(String description) throws Exception {
            return new Task(new Description(description), Task.EVENT_TASK, new TaskDate("1-1-2100"),
                    new TaskDate("1-12-2100"), new TaskTime("12:30AM"), new TaskTime("12:45AM"),
                    new TaskPriority(TaskPriority.NO_PRIORITY), new RecurringType(RecurringType.NO_RECURRING),
                    new TaskStatus(TaskStatus.INCOMPLETE), new UniqueTagList(new Tag("tag")));
        }

    }
}
