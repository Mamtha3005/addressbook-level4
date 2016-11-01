package seedu.taskell.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.taskell.commons.exceptions.IllegalValueException;
import seedu.taskell.model.tag.Tag;
import seedu.taskell.model.tag.UniqueTagList;
import seedu.taskell.model.task.*;

import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private String taskType;
    @XmlElement(required = true)
    private String startDate;
    @XmlElement(required = true)
    private String endDate;
    @XmlElement(required = true)
    private String startTime;
    @XmlElement(required = true)
    private String endTime;
    @XmlElement(required = true)
    private String taskPriority;
    @XmlElement(required = true)
    private String recurringType;
    @XmlElement(required = true)
    private String taskStatus;
    
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        description = source.getDescription().description;
        taskType = source.getTaskType();
        startDate = source.getStartDate().taskDate;
        endDate = source.getEndDate().taskDate;
        startTime = source.getStartTime().taskTime;
        endTime = source.getEndTime().taskTime;
        taskPriority = source.getTaskPriority().taskPriority;
        recurringType = source.getRecurringType().recurringType;
        taskStatus = source.getTaskStatus().taskStatus;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Description description = new Description(this.description);
        final String taskType = this.taskType;
        final TaskDate startDate = new TaskDate(this.startDate);
        final TaskDate endDate = new TaskDate(this.endDate);
        final TaskTime startTime= new TaskTime(this.startTime);
        final TaskTime endTime = new TaskTime(this.endTime);
        final TaskPriority taskPriority = new TaskPriority(this.taskPriority);
        final RecurringType recurringType = new RecurringType(this.recurringType);
        final TaskStatus taskStatus = new TaskStatus(this.taskStatus);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(description, taskType, startDate, endDate, startTime, endTime, taskPriority, recurringType, taskStatus, tags);
    }
}
