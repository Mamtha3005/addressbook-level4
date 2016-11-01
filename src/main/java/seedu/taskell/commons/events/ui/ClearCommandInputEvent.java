/** @@author A0142130A **/
package seedu.taskell.commons.events.ui;

import seedu.taskell.commons.events.BaseEvent;

/** indicates ClearCommand is being executed
 * */

public class ClearCommandInputEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
