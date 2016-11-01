package seedu.taskell.testutil;

import seedu.taskell.model.tag.Tag;
import seedu.taskell.model.tag.UniqueTagList;
import seedu.taskell.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Description description;
    private String taskType;
    private TaskPriority taskPriority;
    private TaskTime startTime;
    private TaskTime endTime;
    private TaskDate startDate;
    private TaskDate endDate;
    private RecurringType recurringType;
    private TaskStatus taskStatus;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public TestTask(Description description, String taskType, TaskPriority taskPriority, TaskTime startTime,
            TaskTime endTime, TaskDate startDate, TaskDate endDate, RecurringType recurringType, TaskStatus taskStatus, UniqueTagList tags) {
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
        this.tags = new UniqueTagList(tags);
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public void setTaskPriority(TaskPriority taskPriority) {
        this.taskPriority = taskPriority;
    }

    public void setStartTime(TaskTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(TaskTime endTime) {
        this.endTime = endTime;
    }

    public void setStartDate(TaskDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(TaskDate endDate) {
        this.endDate = endDate;
    }
    
    


    public void setTaskComplete(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public void setRecurringType(RecurringType recurringType) {
        this.recurringType = recurringType;
    }
    
    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
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
        return startDate;
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
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getDescription().description + " ");
        sb.append("from " + this.getStartDate().taskDate + " ");
        sb.append("to " + this.getEndDate().taskDate + " ");
        sb.append("from " + this.getStartTime().taskTime + " ");
        sb.append("to " + this.getEndTime().taskTime + " ");
        sb.append(TaskPriority.PREFIX + this.getTaskPriority().taskPriority + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append(Tag.PREFIX + s.tagName + " "));
        return sb.toString();
    }

    public String getAddFloatingCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getDescription().description + " ");
        sb.append(TaskPriority.PREFIX + this.getTaskPriority().taskPriority + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append(Tag.PREFIX + s.tagName + " "));
        return sb.toString();
    }
}
