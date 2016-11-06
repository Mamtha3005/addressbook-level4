# A0139257Xreused
###### \java\seedu\taskell\logic\commands\AddCommand.java
``` java
    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            HistoryManager.getInstance().addTask(toAdd);
            jumpToNewTaskIndex();
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            HistoryManager.getInstance().deleteLatestCommand();
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
    }
    
    private void jumpToNewTaskIndex() {
        int indexOfNewTask = model.getFilteredTaskList().size()-1;
        jumpToIndex(indexOfNewTask);
    }

}
```
###### \java\seedu\taskell\logic\commands\Command.java
``` java
    /**
     * Raises an event to jump to the given index
     */
    protected void jumpToIndex(int index) {
        EventsCenter.getInstance().post(new JumpToListRequestEvent(index));
    }
```
