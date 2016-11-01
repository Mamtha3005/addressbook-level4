package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import seedu.taskell.TestApp;

/**
 * A handler for the DisplayPanel of the UI
 */

public class DisplayPanelHandle extends GuiHandle {

    public static final String DISPLAY_PANEL_ID = "#displayPanel";
    
    public DisplayPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public String getText() {
        return getDisplayPanelAsTextArea().getText();
    }

    private TextArea getDisplayPanelAsTextArea() {
        return (TextArea) getNode(DISPLAY_PANEL_ID);
    }
    
}
