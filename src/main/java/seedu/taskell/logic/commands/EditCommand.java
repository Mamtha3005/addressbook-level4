//@@author A0142073R

package seedu.taskell.logic.commands;

import seedu.taskell.commons.core.Messages;
import seedu.taskell.commons.core.UnmodifiableObservableList;
import seedu.taskell.commons.exceptions.IllegalValueException;
import seedu.taskell.history.HistoryManager;
import seedu.taskell.logic.commands.Command;
import seedu.taskell.logic.commands.CommandResult;
import seedu.taskell.model.task.Description;
import seedu.taskell.model.task.FloatingTask;
import seedu.taskell.model.task.ReadOnlyTask;
import seedu.taskell.model.task.Task;
import seedu.taskell.model.task.TaskDate;
import seedu.taskell.model.task.TaskPriority;
import seedu.taskell.model.task.TaskTime;
import seedu.taskell.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.taskell.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Edits a task identified using it's last displayed index from the task
 * manager. Supports to edit different parameters of a task including
 * description, time, date and priority of a task.
 */
public class EditCommand extends Command {
    
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the different parts of a task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer) RESPECTIVE TASK PARAMETERS\n" + "Example: " + COMMAND_WORD
            + " 1 desc: buy cake st: 7am et: 8am sd: 11-12-2016 ed: 12-12-2016 p: 2\n";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Original Task: %1$s \n\nUpdated Task: %2$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";
    public static final String TASK_NOT_FOUND = "The target task is missing";
    public static final String MESSAGE_TIME_CONSTRAINTS = "Start time must be before end time";
    public static final String MESSAGE_DATE_CONSTRAINTS = "Start date must be before end date";

    private final int targetIndex;

    private Description description;
    private TaskDate startDate;
    private TaskDate endDate;
    private TaskTime startTime;
    private TaskTime endTime;
    private TaskPriority taskPriority;

    private boolean hasChangedDescription;
    private boolean hasChangedStartDate;
    private boolean hasChangedEndDate;
    private boolean hasChangedStartTime;
    private boolean hasChangedEndTime;
    private boolean hasChangedPriority;

    public EditCommand(int targetIndex, Description newDescription, boolean hasChangedDescription,
            TaskDate newStartDate, boolean hasChangedStartDate, TaskDate newEndDate, boolean hasChangedEndDate,
            TaskTime newStartTime, boolean hasChangedStartTime, TaskTime newEndTime, boolean hasChangedEndTime,
            TaskPriority newPriority, boolean hasChangedPriority) throws IllegalValueException {

        this.targetIndex = targetIndex;

        description = newDescription;
        this.hasChangedDescription = hasChangedDescription;

        startTime = newStartTime;
        this.hasChangedStartTime = hasChangedStartTime;

        endTime = newEndTime;
        this.hasChangedEndTime = hasChangedEndTime;

        startDate = newStartDate;
        this.hasChangedStartDate = hasChangedStartDate;

        endDate = newEndDate;
        this.hasChangedEndDate = hasChangedEndDate;

        taskPriority = newPriority;
        this.hasChangedPriority = hasChangedPriority;
    }

    public void getEditInformation(ReadOnlyTask taskToEdit) {

        if (hasChangedDescription == false) {
            description = taskToEdit.getDescription();
        }
        if (hasChangedStartTime == false) {
            startTime = taskToEdit.getStartTime();
        }
        if (hasChangedEndTime == false) {
            endTime = taskToEdit.getEndTime();
        }
        if (hasChangedStartDate == false) {
            startDate = taskToEdit.getStartDate();
        }
        if (hasChangedEndDate == false) {
            endDate = taskToEdit.getEndDate();
        }
        if (hasChangedPriority == false) {
            taskPriority = taskToEdit.getTaskPriority();
        }
    }

    private boolean isValidDate(ReadOnlyTask taskToEdit) {
        if (taskToEdit.getTaskType().equals(Task.EVENT_TASK)) {
            if (endDate.isBefore(startDate)) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    private boolean isValidTime(ReadOnlyTask taskToEdit) {
        if (taskToEdit.getTaskType().equals(Task.EVENT_TASK)) {
            if (endDate.equals(startDate) && endTime.isBefore(startTime)) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
    
    private void jumpToNewTaskIndex() {
        jumpToIndex(targetIndex - 1);
    }
    
    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            HistoryManager.getInstance().deleteLatestCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);
        getEditInformation(taskToEdit);

        if (taskToEdit.getTaskType().equals(Task.FLOATING_TASK) && (hasChangedStartTime == true
                || hasChangedEndTime == true || hasChangedStartDate == true || hasChangedEndDate == true)) {
            return new CommandResult(FloatingTask.EDIT_FLOATING_NOT_ALLOWED);
        }

        if (!isValidTime(taskToEdit)) {
            return new CommandResult(MESSAGE_TIME_CONSTRAINTS);
        }

        if (!isValidDate(taskToEdit)) {
            return new CommandResult(MESSAGE_DATE_CONSTRAINTS);
        }

        Task newTask = new Task(description, taskToEdit.getTaskType(), startDate, endDate, startTime, endTime,
                taskPriority, taskToEdit.getRecurringType(), taskToEdit.getTaskStatus(), taskToEdit.getTags());

        try {
            model.editTask(taskToEdit, newTask);
            HistoryManager.getInstance().addTask(newTask);
            HistoryManager.getInstance().addOldTask((Task) taskToEdit);
            jumpToNewTaskIndex();
        } catch (DuplicateTaskException pnfe) {
            HistoryManager.getInstance().deleteLatestCommand();
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException pnfe) {
            HistoryManager.getInstance().deleteLatestCommand();
            return new CommandResult(TASK_NOT_FOUND);
        }

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit, newTask));
    }
}

// @@author