package seedu.taskell.model.task;

import seedu.taskell.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the taskmanager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {
    Description getDescription();
    String getTaskType();
    TaskDate getStartDate();
    TaskDate getEndDate();
    TaskTime getStartTime();
    TaskTime getEndTime();
    TaskPriority getTaskPriority();
    RecurringType getRecurringType();
    TaskStatus getTaskStatus();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getDescription().equals(this.getDescription()) // state checks here onwards
                && other.getTaskType().equals(this.getTaskType())
                && other.getStartDate().equals(this.getStartDate())
                && other.getEndDate().equals(this.getEndDate())
                && other.getStartTime().equals(this.getStartTime())
                && other.getEndTime().equals(this.getEndTime())
                && other.getTaskPriority().equals(this.getTaskPriority())
                && other.getRecurringType().equals(this.getRecurringType())
                && other.getTaskStatus().equals(this.getTaskStatus()));
    }

    /**
     * Formats the task as text, showing all contact details.
     */
    default String getAsText() {
        if (getTaskType().equals(Task.FLOATING_TASK)) {
            return getAsTextFloatingTask();
        } else {
            return getAsTextEventTask();
        }
    }
    
    /**
     * Formats the floating task as text, showing all contact details.
     */
    default String getAsTextFloatingTask() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" TaskPriority: ")
                .append(getTaskPriority())
                .append(" TaskStatus: ")
                .append(getTaskStatus())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
    
    /**
     * Formats the event task as text, showing all contact details.
     */
    default String getAsTextEventTask() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" StartDate: ")
                .append(getStartDate())
                .append(" EndDate: ")
                .append(getEndDate())
                .append(" StartTime: ")
                .append(getStartTime())
                .append(" EndTime: ")
                .append(getEndTime())
                .append(" TaskPriority: ")
                .append(getTaskPriority())
                .append(" RecurringType: ")
                .append(getRecurringType())
                .append(" TaskStatus: ")
                .append(getTaskStatus())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Returns a string representation of this Task's tags
     */
    default String tagsString() {
        final StringBuffer buffer = new StringBuffer();
        final String separator = " ";
        getTags().forEach(tag -> buffer.append(tag).append(separator));
        if (buffer.length() == 0) {
            return "";
        } else {
            return buffer.substring(0, buffer.length() - separator.length());
        }
    }
    
    /**
     * @return a simple string representation of this Task's tags
     *          with each tag separated by a whitespace
     */
    default String tagsSimpleString() {
        final StringBuffer buffer = new StringBuffer();
        final String separator = " ";
        getTags().forEach(tag -> buffer.append(tag.toSimpleString()).append(separator));
        if (buffer.length() == 0) {
            return "";
        } else {
            return buffer.substring(0, buffer.length() - separator.length());
        }
    }

}
