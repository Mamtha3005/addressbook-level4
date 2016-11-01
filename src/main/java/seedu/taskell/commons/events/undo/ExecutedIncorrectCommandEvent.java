/** @@author A0142130A **/
package seedu.taskell.commons.events.undo;

import seedu.taskell.commons.events.BaseEvent;

/** Indicates a Command has been entered incorrectly, to feedback to History
 *  NOTE: this is different from IncorrectCommandAttemptedEvent (which is meant for UI)
 */

public class ExecutedIncorrectCommandEvent extends BaseEvent {
    
    private boolean isUndoableCommand;
    
    public ExecutedIncorrectCommandEvent(boolean isUndoableCommand) {
        this.isUndoableCommand = isUndoableCommand;
    }
    
    public boolean isUndoableCommand() {
        return isUndoableCommand;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
