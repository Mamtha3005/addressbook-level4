package seedu.taskell.testutil;

import seedu.taskell.commons.exceptions.IllegalValueException;
import seedu.taskell.model.tag.Tag;
import seedu.taskell.model.task.*;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    public TaskBuilder withDescription(String name) throws IllegalValueException {
        this.task.setDescription(new Description(name));
        return this;
    }
    
    public TaskBuilder withTaskType(String taskType) throws IllegalValueException {
        this.task.setTaskType(taskType);
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withTaskPriority(String taskPriority) throws IllegalValueException {
        this.task.setTaskPriority(new TaskPriority(taskPriority));
        return this;
    }

    public TaskBuilder withStartDate(String startDate) throws IllegalValueException {
        this.task.setStartDate(new TaskDate(startDate));
        return this;
    }
    
    public TaskBuilder withEndDate(String endDate) throws IllegalValueException {
        this.task.setEndDate(new TaskDate(endDate));
        return this;
    }

    public TaskBuilder withStartTime(String startTime) throws IllegalValueException {
        this.task.setStartTime(new TaskTime(startTime));
        return this;
    }
    
    public TaskBuilder withEndTime(String endTime) throws IllegalValueException {
        this.task.setEndTime(new TaskTime(endTime));
        return this;
    }
    
    public TaskBuilder withRecurringType(String recurringType) throws IllegalValueException {
        this.task.setRecurringType(new RecurringType(recurringType));
        return this;
    }
    
    public TaskBuilder withTaskComplete(String taskStatus) throws IllegalValueException {
        this.task.setTaskComplete(new TaskStatus(taskStatus));
        return this;
    }
    
    public TestTask build() {
        return this.task;
    }

}
