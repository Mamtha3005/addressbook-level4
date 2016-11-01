/** @@author A0142130A **/
package seedu.taskell.commons.events.storage;

import seedu.taskell.commons.core.Config;
import seedu.taskell.commons.events.BaseEvent;

/**
 * Indicates storage location has changed.
 */
public class StorageLocationChangedEvent extends BaseEvent {
    
    private Config config;
    
    public StorageLocationChangedEvent(Config config) {
        this.config = config;
    }
    
    public Config getConfig() {
        return config;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
