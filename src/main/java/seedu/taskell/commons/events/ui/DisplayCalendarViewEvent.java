/** @@author A0142130A **/
package seedu.taskell.commons.events.ui;

import seedu.taskell.commons.events.BaseEvent;

/** Indicates display panel needs to show calendar **/

public class DisplayCalendarViewEvent extends BaseEvent {
   
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
