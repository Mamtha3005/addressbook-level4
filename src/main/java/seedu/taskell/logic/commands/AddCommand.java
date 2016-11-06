package seedu.taskell.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.taskell.commons.exceptions.IllegalValueException;
import seedu.taskell.history.HistoryManager;
import seedu.taskell.model.tag.Tag;
import seedu.taskell.model.tag.UniqueTagList;
import seedu.taskell.model.task.*;

//@@author A0139257X
/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: DESCRIPTION by/on[DATE] from[START_TIME] to[END_TIME] [p/PRIORITY] [r/daily] [#TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " go for meeting on 1-1-2100 from 12.30AM to 12.45AM p/3 r/daily #work";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */

    public AddCommand(String description, String taskType, String startDate, String endDate, 
            String startTime, String endTime, String taskPriority, 
            String recurringType, Set<String> tags) throws IllegalValueException {
        
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        
        switch (taskType) {
        case Task.FLOATING_TASK: 
            this.toAdd = new FloatingTask(description, taskPriority, recurringType, TaskStatus.INCOMPLETE, new UniqueTagList(tagSet));
            break;
        case Task.EVENT_TASK:
            this.toAdd = new EventTask(description, startDate, endDate, startTime, endTime, taskPriority, recurringType, TaskStatus.INCOMPLETE, new UniqueTagList(tagSet));
            break;
        default:
            toAdd = null;
        }
    }

  //@@author A0139257X-reused
    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            HistoryManager.getInstance().addTask(toAdd);
            jumpToNewTaskIndex();
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            HistoryManager.getInstance().deleteLatestCommand();
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
    }
    
    private void jumpToNewTaskIndex() {
        int indexOfNewTask = model.getFilteredTaskList().size()-1;
        jumpToIndex(indexOfNewTask);
    }

}
