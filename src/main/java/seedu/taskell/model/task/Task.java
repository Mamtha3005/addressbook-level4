package seedu.taskell.model.task;

import java.util.Objects;

import seedu.taskell.commons.exceptions.IllegalValueException;
import seedu.taskell.commons.util.CollectionUtil;
import seedu.taskell.model.tag.UniqueTagList;

/**
 * Represents a Task in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {
    public static final String FLOATING_TASK = "FLOATING";
    public static final String EVENT_TASK = "EVENT";

    protected Description description;
    protected String taskType;
    protected TaskDate startDate;
    protected TaskDate endDate;
    protected TaskTime startTime;
    protected TaskTime endTime;
    protected TaskPriority taskPriority;
    protected RecurringType recurringType;
    protected TaskStatus taskStatus;
    
    protected UniqueTagList tags;
    
    public Task() { 
        //Not applicable
    }

    /**
     * Every field must be present and not null.
     */
    public Task(Description description, String taskType, TaskDate startDate, TaskDate endDate, 
            TaskTime startTime, TaskTime endTime, TaskPriority taskPriority, 
            RecurringType recurringType, TaskStatus taskStatus, UniqueTagList tags) {
        
        assert !CollectionUtil.isAnyNull(description, taskType, startDate, startTime, 
                endTime, taskPriority, recurringType, taskStatus, tags);
        
        this.description = description;
        this.taskType = taskType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.taskPriority = taskPriority;
        this.recurringType = recurringType;
        this.taskStatus = taskStatus;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    public Task(String description) throws IllegalValueException{
        this.description = new Description(description);
    }
    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getDescription(), source.getTaskType(), source.getStartDate(), 
                source.getEndDate(), source.getStartTime(), source.getEndTime(), 
                source.getTaskPriority(), source.getRecurringType(), source.getTaskStatus(), 
                source.getTags());
    }

    @Override
    public Description getDescription() {
        return description;
    }
    
    @Override
    public String getTaskType() {
        return taskType;
    }

    @Override
    public TaskDate getStartDate() {
        return startDate;
    }
    
    @Override
    public TaskDate getEndDate() {
        return endDate;
    }

    @Override
    public TaskTime getStartTime() {
        return startTime;
    }
    
    @Override
    public TaskTime getEndTime() {
        return endTime;
    }

    @Override
    public TaskPriority getTaskPriority() {
        return taskPriority;
    }
    
    @Override
    public RecurringType getRecurringType() {
        return recurringType;
    }
    
    @Override
    public TaskStatus getTaskStatus() {
        return taskStatus;
    }
    
    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }
   
    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, startDate, startTime, endTime, 
                taskPriority, recurringType, taskStatus, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
