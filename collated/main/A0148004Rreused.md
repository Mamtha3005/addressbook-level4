# A0148004Rreused
###### \java\seedu\taskell\model\task\TaskDate.java
``` java
    public TaskDate getNextMonth() throws IllegalValueException {
        try {
            LocalDate localDate = LocalDate.of(Integer.valueOf(getYear()), Integer.valueOf(getMonth()), Integer.valueOf(getDay()));
            LocalDate nextMonth = localDate.plusMonths(1);
            return new TaskDate(nextMonth.format(standardFormat));
        } catch (IllegalValueException e) {
            throw new IllegalValueException(MESSAGE_TASK_DATE_CONSTRAINTS);
        }
    }
```
