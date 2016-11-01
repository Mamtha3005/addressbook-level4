//@@author A0148004R
package seedu.taskell.logic.commands;

import seedu.taskell.commons.core.Messages;
import seedu.taskell.commons.core.UnmodifiableObservableList;
import seedu.taskell.commons.exceptions.IllegalValueException;
import seedu.taskell.model.task.ReadOnlyTask;
import seedu.taskell.model.task.RecurringType;
import seedu.taskell.model.task.Task;
import seedu.taskell.model.task.TaskDate;
import seedu.taskell.model.task.TaskStatus;
import seedu.taskell.model.task.UniqueTaskList;
import seedu.taskell.model.task.UniqueTaskList.TaskNotFoundException;

public class UndoneCommand extends Command {
    public static final String COMMAND_WORD = "undone";
    public static final String MESSAGE_UNDONE_UNSUCCESSFUL = "The task status is already incomplete";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undone the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNDONE_TASK_SUCCESS = "Undone Task: %1$s";

    public final int targetIndex;
    
    
    public UndoneCommand(int targetIndex) {
        this.targetIndex = targetIndex;
        
    }
    
    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        
        TaskStatus incompleteStatus = new TaskStatus(TaskStatus.INCOMPLETE);
        
        ReadOnlyTask taskToBeUndone = lastShownList.get(targetIndex - 1);
        Task newTask = null;
        if(taskToBeUndone.getTaskStatus().taskStatus().equals(TaskStatus.INCOMPLETE)){
            return new CommandResult(MESSAGE_UNDONE_UNSUCCESSFUL);
        } else {
            newTask = new Task(taskToBeUndone.getDescription(), taskToBeUndone.getTaskType(), taskToBeUndone.getStartDate(), taskToBeUndone.getEndDate(),                
                    taskToBeUndone.getStartTime(), taskToBeUndone.getEndTime(), taskToBeUndone.getTaskPriority(), taskToBeUndone.getRecurringType(), incompleteStatus, taskToBeUndone.getTags());
        }
        
        try {
            model.editTask(taskToBeUndone, newTask);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(AddCommand.MESSAGE_DUPLICATE_TASK);
        } 

        return new CommandResult(String.format(MESSAGE_UNDONE_TASK_SUCCESS, taskToBeUndone));
    }
}
//@@author