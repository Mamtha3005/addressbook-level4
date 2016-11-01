package seedu.taskell.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import seedu.taskell.model.task.ReadOnlyTask;
import seedu.taskell.model.task.RecurringType;
import seedu.taskell.model.task.Task;
import seedu.taskell.model.task.TaskPriority;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label description;
    @FXML
    private Label id;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private Label recurringType;
    @FXML
    private Label taskStatus;   
    @FXML
    private Label tags;
    @FXML
    private Pane priorityColour;

    private ReadOnlyTask task;
    private int displayedIndex;

    public TaskCard(){

    }

    public static TaskCard load(ReadOnlyTask task, int displayedIndex){
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

//@@author A0139257X
    @FXML
    public void initialize() {
        id.setText(displayedIndex + ". ");
        description.setText(task.getDescription().description);
        tags.setText(task.tagsString());
        recurringType.setText(task.getRecurringType().recurringType);
        taskStatus.setText(task.getTaskStatus().taskStatus);
        
        setDateTime();
        setDateTimeVisibility();
        setCardPaneBackground();
    }
    
    private void setDateTime() {
        startDate.setText(task.getStartDate().getDisplayDate());
        endDate.setText(task.getEndDate().getDisplayDate());
        startTime.setText(task.getStartTime().taskTime);
        endTime.setText(task.getEndTime().taskTime);
    }
    
    private void setDateTimeVisibility() {
        if (task.getTaskType().equals(Task.FLOATING_TASK)) {
            startDate.setVisible(false);
            endDate.setVisible(false);
            startTime.setVisible(false);
            endTime.setVisible(false);
        }
        
        if (task.getRecurringType().recurringType.equals(RecurringType.NO_RECURRING)) {
            recurringType.setVisible(false);
        }
    }
    
    private void setCardPaneBackground() {
        if (task.getTaskPriority().taskPriority.equals(TaskPriority.HIGH_PRIORITY)) {
            priorityColour.setStyle(TaskPriority.HIGH_PRIORITY_BACKGROUND);
        } else if (task.getTaskPriority().taskPriority.equals(TaskPriority.MEDIUM_PRIORITY)) {
            priorityColour.setStyle(TaskPriority.MEDIUM_PRIORITY_BACKGROUND);
        } else if (task.getTaskPriority().taskPriority.equals(TaskPriority.LOW_PRIORITY)) {
            priorityColour.setStyle(TaskPriority.LOW_PRIORITY_BACKGROUND);
        }
    }
//@@author
    
    public HBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) {
        cardPane = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
