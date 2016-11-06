/** @@author A0142130A **/
package seedu.taskell.logic.commands;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import seedu.taskell.commons.core.Config;
import seedu.taskell.commons.core.EventsCenter;
import seedu.taskell.commons.core.LogsCenter;
import seedu.taskell.commons.events.storage.StorageLocationChangedEvent;
import seedu.taskell.commons.exceptions.DataConversionException;
import seedu.taskell.model.ReadOnlyTaskManager;
import seedu.taskell.storage.JsonConfigStorage;
import seedu.taskell.storage.Storage;

/** Saves current data file to new filepath.
 * */

public class SaveStorageLocationCommand extends Command {
    
    private Logger logger = LogsCenter.getLogger(SaveStorageLocationCommand.class.getName());
    
    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Saves data file to new location specified. "
            + "New files can be auto-created as long as given directory is valid.\n"
            + "Parameters: FILEPATH (must be valid)\n"
            + "Example: " + COMMAND_WORD + " C:\\Users\\chicken\\Desktop\\cat";
    
    private static final String MESSAGE_SUCCESS = "Data successfully saved to new location.";
    private static final String MESSAGE_INVALID_PATH = "Filepath given is invalid. Filepath will be reset to old path.";
    
    private static Config config;
    private String newStorageFilePath, oldStorageFilePath;
    private ReadOnlyTaskManager taskManager;
    private static JsonConfigStorage jsonConfigStorage;
    private static Storage storage;
    
    public SaveStorageLocationCommand(String newStorageFilePath) {
        this.oldStorageFilePath = config.getTaskManagerFilePath();
        logger.info("Old file path: " + oldStorageFilePath);
        
        this.newStorageFilePath = newStorageFilePath.trim().replace("\\", "/") + "/taskmanager.xml";
        logger.info("New file path: " + this.newStorageFilePath);
        jsonConfigStorage = new JsonConfigStorage(Config.DEFAULT_CONFIG_FILE);
    }
    
    public static void setConfig(Config c) {
        config = c;
    }
    
    public static void setStorage(Storage s) {
        storage = s;
    }

    @Override
    public CommandResult execute() {
        assert config != null;
        assert jsonConfigStorage != null;

        taskManager = model.getTaskManager();
        
        config.setTaskManagerFilePath(newStorageFilePath);
        indicateStorageLocationChanged();
        try {
            storage.saveTaskManager(taskManager, newStorageFilePath);
            storage.readTaskManager();
        } catch (IOException e) {
            handleInvalidFilePathException();
            return new CommandResult(MESSAGE_INVALID_PATH);
        } catch (DataConversionException e) {
            handleInvalidFilePathException();
            return new CommandResult(MESSAGE_INVALID_PATH);
        }
        
        saveToConfigJson();
        
        deleteOldFile();
        
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /** deletes xml file in previous storage location
     * */
    private void deleteOldFile() {
        File file = new File(oldStorageFilePath);
        file.delete();
    }

    private void indicateStorageLocationChanged() {
        assert config != null;
        EventsCenter.getInstance().post(new StorageLocationChangedEvent(config));
    }
    
    /** occurs when there is error writing to given filepath
     *  i.e. invalid name, invalid directory
     *  this method will transfer data file back to previous location and 
     *  configure internal variables to point back to correct location
     * */
    private void handleInvalidFilePathException() {
        logger.info("Error writing to filepath. Handling data save exception.");
        assert config != null;
        
        config.setTaskManagerFilePath(oldStorageFilePath);  //set back to old filepath
        indicateStorageLocationChanged();
        
        try {
            storage.saveTaskManager(taskManager, newStorageFilePath);
        } catch (IOException e) {
            logger.severe("Error saving task manager");
        }
        
        saveToConfigJson();
    }
    
    private void saveToConfigJson() {
        try {
            jsonConfigStorage.saveConfigFile(config);
            jsonConfigStorage.readConfigFile();
        } catch (IOException e) {
            logger.severe("save to config json error");
        } catch (DataConversionException e) {
            logger.severe("read back config json error");
        }
    }

}
