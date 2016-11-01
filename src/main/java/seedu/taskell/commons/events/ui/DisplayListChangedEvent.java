/** @@author A0142130A **/
package seedu.taskell.commons.events.ui;

import java.util.ArrayList;

import seedu.taskell.commons.events.BaseEvent;

/** Indicates a list needs to be displayed on Display Panel
 * */
public class DisplayListChangedEvent extends BaseEvent {
    
    private ArrayList<String> list;
    
    public DisplayListChangedEvent(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    
    public ArrayList<String> getList() {
        return list;
    }

}
