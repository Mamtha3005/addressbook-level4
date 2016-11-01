//@@author A0148004R
package seedu.taskell.logic.commands.list;


/**
 * Lists all tasks in the task manager to the user.
 */
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.taskell.logic.commands.Command;
import seedu.taskell.logic.commands.CommandResult;
import seedu.taskell.model.task.TaskStatus;

public class ListCommand extends Command {
    
    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks need to be done";

    private Set<String> keywordSet = new HashSet<>(Arrays.asList(TaskStatus.INCOMPLETE));

    public ListCommand() {
       
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredtaskListCompleted(keywordSet);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }
}
//@@author
