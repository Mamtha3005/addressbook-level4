/** @@author A0142130A **/
package seedu.taskell.logic.commands;

import seedu.taskell.commons.core.EventsCenter;
import seedu.taskell.commons.events.ui.ClearCommandInputEvent;
import seedu.taskell.model.TaskManager;

/**
 * Clears the task manager. Will show confirm dialog.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Clear command executed.";
    private static ClearCommand self;
    private static boolean isUnderTesting;  //for testing ONLY

    public ClearCommand() {}

    public static ClearCommand getInstance() {
        if (self == null) {
            self = new ClearCommand();
        }
        
        return self;
    }
    
    /** if set to true, ClearCommand bypasses confirm dialog
     *  for running test cases ONLY
     * */
    public static void setIsUnderTesting(boolean value) {
        isUnderTesting = value;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        
        if (isUnderTesting) {
            executeClear();
        } else {
            raiseClearCommandInputEvent();
        }
        
        return new CommandResult(MESSAGE_SUCCESS);
    }
    
    /** executes reset data
     *  will be called from UiManager after confirmation of clear
     * */
    public void executeClear() {
        model.resetData(TaskManager.getEmptyTaskManager());
    }

    private void raiseClearCommandInputEvent() {
        EventsCenter.getInstance().post(new ClearCommandInputEvent());      
    }
    
    /** @@author **/
}
