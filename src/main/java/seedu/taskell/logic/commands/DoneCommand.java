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

public class DoneCommand extends Command {
    public static final String COMMAND_WORD = "done";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Done the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DONE_TASK_SUCCESS = "Done Task: %1$s";

    public final int targetIndex;
    
    
    public DoneCommand(int targetIndex) {
        this.targetIndex = targetIndex;
        
    }
    
    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        
        TaskStatus finsihedStatus = new TaskStatus(TaskStatus.FINISHED);
        
        ReadOnlyTask taskToBeDone = lastShownList.get(targetIndex - 1);
        Task newTask = null;
        
        try {        
            
            if (taskToBeDone.getRecurringType().recurringType.equals(RecurringType.DAILY_RECURRING)) {
                newTask = new Task(taskToBeDone.getDescription(), taskToBeDone.getTaskType(), taskToBeDone.getStartDate().getNextDay(), taskToBeDone.getEndDate().getNextDay(),                
                        taskToBeDone.getStartTime(), taskToBeDone.getEndTime(), taskToBeDone.getTaskPriority(), taskToBeDone.getRecurringType(), taskToBeDone.getTaskStatus(), taskToBeDone.getTags());
                
            } else if (taskToBeDone.getRecurringType().recurringType.equals(RecurringType.WEEKLY_RECURRING)) {
                newTask = new Task(taskToBeDone.getDescription(), taskToBeDone.getTaskType(), taskToBeDone.getStartDate().getNextWeek(), taskToBeDone.getEndDate().getNextWeek(),                
                        taskToBeDone.getStartTime(), taskToBeDone.getEndTime(), taskToBeDone.getTaskPriority(), taskToBeDone.getRecurringType(), taskToBeDone.getTaskStatus(), taskToBeDone.getTags());
                
            } else if (taskToBeDone.getRecurringType().recurringType.equals(RecurringType.MONTHLY_RECURRING)) {
                newTask = new Task(taskToBeDone.getDescription(), taskToBeDone.getTaskType(), taskToBeDone.getStartDate().getNextMonth(), taskToBeDone.getEndDate().getNextMonth(),                
                        taskToBeDone.getStartTime(), taskToBeDone.getEndTime(), taskToBeDone.getTaskPriority(), taskToBeDone.getRecurringType(), taskToBeDone.getTaskStatus(), taskToBeDone.getTags());
                
            } else if (taskToBeDone.getRecurringType().recurringType.equals(RecurringType.DEFAULT_RECURRING)) {
                newTask = new Task(taskToBeDone.getDescription(), taskToBeDone.getTaskType(), taskToBeDone.getStartDate(), taskToBeDone.getEndDate(),                
                        taskToBeDone.getStartTime(), taskToBeDone.getEndTime(), taskToBeDone.getTaskPriority(), taskToBeDone.getRecurringType(), finsihedStatus, taskToBeDone.getTags());
            }
        } catch (IllegalValueException ive) {
            return new CommandResult(TaskDate.MESSAGE_TASK_DATE_CONSTRAINTS);
        }   
        
        try {
            model.editTask(taskToBeDone, newTask);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(AddCommand.MESSAGE_DUPLICATE_TASK);
        } 

        return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, taskToBeDone));
    }
}
//@@author