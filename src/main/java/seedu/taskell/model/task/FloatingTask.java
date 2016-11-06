package seedu.taskell.model.task;

import java.util.Objects;

import seedu.taskell.model.task.Description;
import seedu.taskell.commons.exceptions.IllegalValueException;
import seedu.taskell.commons.util.CollectionUtil;
import seedu.taskell.logic.commands.IncorrectCommand;
import seedu.taskell.model.tag.UniqueTagList;

//@@author A0139257X
/**
 * Represents a Floating Task in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class FloatingTask extends Task {
    
    public static final String RECURRING_TYPE_NOT_ALLOWED = "Floating task cannot be recurring";
    
<<<<<<< Updated upstream
    public FloatingTask(String description, String taskPriority, String recurringType, String taskStatus, UniqueTagList tags) throws IllegalValueException {
        this(new Description(description),
=======
    
    public FloatingTask(String[] taskComponentArray, boolean[] hasTaskComponentArray, UniqueTagList tags) throws IllegalValueException {

        this(new Description(taskComponentArray[DESCRIPTION]),
>>>>>>> Stashed changes
                FLOATING_TASK,
                new TaskDate(TaskDate.DEFAULT_DATE),
                new TaskDate(TaskDate.DEFAULT_DATE),
                new TaskTime(TaskTime.DEFAULT_START_TIME),
                new TaskTime(TaskTime.DEFAULT_END_TIME),
                new TaskPriority(taskComponentArray[TASK_PRIORITY]),
                new RecurringType(taskComponentArray[RECURRING_TYPE]),
                new TaskStatus(TaskStatus.INCOMPLETE),
                tags);
        
        if (hasTaskComponentArray[Task.RECURRING_COMPONENT]) {
            throw new IllegalValueException(FloatingTask.RECURRING_TYPE_NOT_ALLOWED);
        }
    }
    
<<<<<<< Updated upstream
    public FloatingTask(Description description, String taskType, TaskDate startDate, TaskDate endDate, TaskTime startTime, TaskTime endTime, TaskPriority taskPriority, RecurringType recurringType, TaskStatus taskStatus, UniqueTagList tags) {
        super(description, taskType, startDate, endDate, startTime, endTime, taskPriority, recurringType, taskStatus, tags);
=======
    public FloatingTask(Description description, String taskType, TaskDate startDate, 
            TaskDate endDate, TaskTime startTime, TaskTime endTime, TaskPriority taskPriority, 
            RecurringType recurringType, TaskStatus taskStatus, UniqueTagList tags) {
        
        super(description, FLOATING_TASK, 
                startDate, endDate, 
                startTime, endTime, 
                taskPriority, recurringType, 
                taskStatus, tags);
>>>>>>> Stashed changes
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

}
