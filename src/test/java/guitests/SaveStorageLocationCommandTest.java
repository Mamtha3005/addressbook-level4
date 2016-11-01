/** @@author A0142130A **/
package guitests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

import seedu.taskell.TestApp;
import seedu.taskell.commons.core.Config;
import seedu.taskell.commons.core.LogsCenter;
import seedu.taskell.commons.exceptions.DataConversionException;
import seedu.taskell.logic.commands.SaveStorageLocationCommand;
import seedu.taskell.model.ReadOnlyTaskManager;
import seedu.taskell.model.TaskManager;
import seedu.taskell.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.taskell.storage.JsonConfigStorage;
import seedu.taskell.testutil.TestTask;
import seedu.taskell.testutil.TestUtil;

public class SaveStorageLocationCommandTest extends TaskManagerGuiTest {
    
    private static final Logger logger = LogsCenter.getLogger(SaveStorageLocationCommandTest.class);
    
    private static final String CONFIG_JSON = "config.json";
    private static final String CONFIG_LOCATION = "./src/test/data/SaveLocationCommandTest";
    
    private static final String DEFAULT_SAVE_LOCATION = TestApp.SAVE_LOCATION_FOR_TESTING;

    @Test
    public void saveToValidFilePath_success() throws DataConversionException, IOException, DuplicateTaskException {
        String testFilePath = "./src/test/data/SaveLocationCommandTest/newStorageLocation/";
        commandBox.runCommand("save " + testFilePath);
        assertWriteToJsonSuccess();
        assertWriteToXmlSuccess();
    }
    
    /** @@author A0142130A-unused **/
    //This test is not run because it has assertion error on Travis build
    //@Test
    public void saveToInvalidFilePath() throws DataConversionException {
        JsonConfigStorage jsonConfigStorage = new JsonConfigStorage(CONFIG_LOCATION);

        commandBox.runCommand("save E:");   
        
        Optional<Config> newConfig = jsonConfigStorage.readConfig(CONFIG_JSON);
        String newFilePath = newConfig.get().getTaskManagerFilePath();
        logger.info("New path: " + newFilePath);
        
        assert(newFilePath.equals(DEFAULT_SAVE_LOCATION));
    }
    
    /** @@author A0142130A **/
    
    /** NOTE: because of the way SaveStorageLocationCommand works, after running this command
     *          config.json in Taskell saves the test data so this method is necessary to reset
     *          config.json to default data
     * */
    @Test
    public void resetConfigFile() throws IOException {
        Config config = new Config();
        config.setAppTitle("Taskell");
        config.setLogLevel(Level.INFO);
        config.setUserPrefsFilePath("preferences.json");
        config.setTaskManagerFilePath("data/taskmanager.xml");
        config.setTaskManagerName("MyTaskManager");
        SaveStorageLocationCommand.setConfig(config);
        
        JsonConfigStorage jsonConfigStorage = new JsonConfigStorage(CONFIG_JSON);
        jsonConfigStorage.saveConfigFile(config);
    }
    
    private void assertWriteToJsonSuccess() throws DataConversionException {
        JsonConfigStorage jsonConfigStorage = new JsonConfigStorage(CONFIG_LOCATION);
        Optional<Config> config = jsonConfigStorage.readConfig(CONFIG_JSON);
        assert(config.isPresent());
    } 
    
    private void assertWriteToXmlSuccess() {
        TestTask[] currentList = td.getTypicalTasks();
        assertTrue(taskListPanel.isListMatching(currentList));
    }

}
