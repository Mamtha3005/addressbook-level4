//@@author A0142073R
package seedu.taskell.logic.commands.list;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.taskell.logic.commands.Command;
import seedu.taskell.logic.commands.CommandResult;
import seedu.taskell.model.task.Task;
import seedu.taskell.model.task.TaskDate;

public class ListDateCommand extends Command {

    public static final String COMMAND_WORD = "list-date";

    public static final String MESSAGE_SUCCESS = "Listed all tasks on a given date";

<<<<<<< Updated upstream
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists tasks on a specific date.\n"
=======
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists tasks on 1 specific date only.\n"
>>>>>>> Stashed changes
            + "Parameters: DATE (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 8-8-2016 ";

    private Set<String> keywordSet;

    public ListDateCommand(TaskDate date) {
        keywordSet = new HashSet<>(Arrays.asList(date.taskDate, Task.EVENT_TASK));
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredtaskListDate(keywordSet);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }
}
// @@author