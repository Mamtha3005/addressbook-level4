# A0139257Xreused
###### \java\seedu\taskell\logic\commands\AddCommand.java
``` java
    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            HistoryManager.getInstance().addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            HistoryManager.getInstance().deleteLatestCommand();
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

}
```
