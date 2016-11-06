package seedu.taskell.logic.parser;

import static seedu.taskell.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskell.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.taskell.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.taskell.commons.exceptions.IllegalValueException;
import seedu.taskell.commons.util.StringUtil;
import seedu.taskell.history.History;
import seedu.taskell.history.HistoryManager;
import seedu.taskell.logic.commands.*;
import seedu.taskell.logic.commands.EditCommand;
import seedu.taskell.logic.commands.list.ListAllCommand;
import seedu.taskell.logic.commands.list.ListCommand;
import seedu.taskell.logic.commands.list.ListDateCommand;
import seedu.taskell.logic.commands.list.ListDoneCommand;
import seedu.taskell.logic.commands.list.ListPriorityCommand;
import seedu.taskell.model.tag.Tag;
import seedu.taskell.model.task.Description;
import seedu.taskell.model.task.FloatingTask;
import seedu.taskell.model.task.RecurringType;
import seedu.taskell.model.task.Task;
import seedu.taskell.model.task.TaskDate;
import seedu.taskell.model.task.TaskPriority;
import seedu.taskell.model.task.TaskTime;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT = Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one

    private static final String BY = "by";
    private static final String ON = "on";
    private static final String AT = "at";
    private static final String FROM = "from";
    private static final String TO = "to";
<<<<<<< Updated upstream
    
    private static final String ST = "st:";
    private static final String ET = "et:";
    private static final String SD = "sd:";
    private static final String ED = "ed:";
    private static final String DESC = "desc:";
    private static final String P = "p:";
=======

    private static final int NUM_QUEUE = 6;
    private static final int DESCRIPTION_QUEUE = 0;
    private static final int BY_QUEUE = 1;
    private static final int ON_QUEUE = 2;
    private static final int AT_QUEUE = 3;
    private static final int FROM_QUEUE = 4;
    private static final int TO_QUEUE = 5;
>>>>>>> Stashed changes

    private static final String START_TIME = "st:";
    private static final String END_TIME = "et:";
    private static final String START_DATE = "sd:";
    private static final String END_DATE = "ed:";
    private static final String DESCRIPTION = "desc:";
    private static final String PRIORITY = "p:";

    private boolean lastCharChanged;

    private static History history;

    private ArrayList<Queue<String>> partitionQueue;
    private boolean[] hasTaskComponentArray;
    private String[] taskComponentArray;

    public Parser() {
        history = HistoryManager.getInstance();
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput
     *            full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        
        saveToHistory(userInput, commandWord);

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return prepareAdd(arguments);

        case SelectCommand.COMMAND_WORD:
            return prepareSelect(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);

        case EditCommand.COMMAND_WORD:
            return prepareEdit(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case FindTagCommand.COMMAND_WORD:
            return prepareFindByTag(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ListAllCommand.COMMAND_WORD:
            return new ListAllCommand();

        case ListDoneCommand.COMMAND_WORD:
            return new ListDoneCommand();

        case ListDateCommand.COMMAND_WORD:
            return prepareListDate(arguments);

        case ListPriorityCommand.COMMAND_WORD:
            return prepareListPriority(arguments);

        case UndoCommand.COMMAND_WORD:
            return prepareUndo(arguments);

        case SaveStorageLocationCommand.COMMAND_WORD:
            return prepareSaveStorageLocation(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case DoneCommand.COMMAND_WORD:
            return prepareDone(arguments);
        
        case UndoneCommand.COMMAND_WORD:
            return prepareUndone(arguments);
        
        case ViewHistoryCommand.COMMAND_WORD_1:
            return new ViewHistoryCommand();
            
        case ViewHistoryCommand.COMMAND_WORD_2:
            return new ViewHistoryCommand();

        case ViewCalendarCommand.COMMAND_WORD_1:
            return new ViewCalendarCommand();

        case ViewCalendarCommand.COMMAND_WORD_2:
            return new ViewCalendarCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /** @@author A0142130A **/
    
    /** if type of command is undoable, saves to history for undoing
     * */
    private void saveToHistory(String userInput, final String commandWord) {
        if (commandWord.equals(AddCommand.COMMAND_WORD) 
                || commandWord.equals(DeleteCommand.COMMAND_WORD)
                || commandWord.contains(UndoCommand.EDIT)) {
            IncorrectCommand.setIsUndoableCommand(true);
            history.addCommand(userInput, commandWord);
        } else {
            IncorrectCommand.setIsUndoableCommand(false);
        }
    }
    
    /** @@author **/

    // @@author A0142073R

    private Command prepareListDate(String arguments) {
        if (arguments.isEmpty()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListDateCommand.MESSAGE_USAGE));
        }
        StringTokenizer st = new StringTokenizer(arguments.trim(), " ");
        String date = st.nextToken();
        if (!TaskDate.isValidDate(date)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListDateCommand.MESSAGE_USAGE));
        } else {
            try {
                return new ListDateCommand(new TaskDate(date));
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());

            }
        }
    }

    private Command prepareListPriority(String args) {
        if (args.isEmpty()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListPriorityCommand.MESSAGE_USAGE));
        }
<<<<<<< Updated upstream
        StringTokenizer st = new StringTokenizer(args.trim(), " ");
        String intValue = st.nextToken();
        if (st.hasMoreTokens()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListPriorityCommand.MESSAGE_USAGE));
        }
        if (!isInt(intValue)) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListPriorityCommand.MESSAGE_USAGE));
        }
        int targetIdx = Integer.valueOf(intValue);
        if (targetIdx < 0 || targetIdx > 3) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_TASK_DISPLAYED_INDEX, ListPriorityCommand.MESSAGE_USAGE));
        } else
            return new ListPriorityCommand(intValue);
=======

        initialiseTaskComponentArray();
        StringTokenizer st = new StringTokenizer(args.trim(), " ");
        taskComponentArray[Task.TASK_PRIORITY] = st.nextToken();

        if (st.hasMoreTokens() || !isInt(taskComponentArray[Task.TASK_PRIORITY])) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListPriorityCommand.MESSAGE_USAGE));
        }

        int targetIdx = Integer.valueOf(taskComponentArray[Task.TASK_PRIORITY]);
        if (targetIdx < Integer.valueOf(TaskPriority.DEFAULT_PRIORITY)
                || targetIdx > Integer.valueOf(TaskPriority.HIGH_PRIORITY)) {
            return new IncorrectCommand(
                    String.format(TaskPriority.MESSAGE_TASK_PRIORITY_CONSTRAINTS, ListPriorityCommand.MESSAGE_USAGE));
        } else {
            return new ListPriorityCommand(taskComponentArray[Task.TASK_PRIORITY]);
        }
>>>>>>> Stashed changes
    }

    /**
     * Parses arguments in the context of the edit command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareEdit(String args) {
<<<<<<< Updated upstream
        String description = "default";
        String startDate = TaskDate.DEFAULT_DATE;
        String endDate = TaskDate.DEFAULT_DATE;
        String startTime = TaskTime.DEFAULT_START_TIME;
        String endTime = TaskTime.DEFAULT_END_TIME;
        String taskPriority = TaskPriority.DEFAULT_PRIORITY;
        
=======
        initialiseTaskComponentArray();
        initialiseHasTaskComponentArray();
        taskComponentArray[Task.DESCRIPTION] = "default";
        ArrayList<String> argsList = tokenizeArguments(args);
>>>>>>> Stashed changes

        if (argsList.isEmpty()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
<<<<<<< Updated upstream
        StringTokenizer st = new StringTokenizer(args.trim(), " ");
        String intValue = st.nextToken();
        if (!isInt(intValue)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_TASK_DISPLAYED_INDEX, EditCommand.MESSAGE_USAGE));
        }
        int targetIdx = Integer.valueOf(intValue);
        hasChangedDescription = false;
        hasChangedStartDate = false;
        hasChangedEndDate = false;
        hasChangedStartTime = false;
        hasChangedEndTime = false;
        hasChangedPriority = false;
        boolean lastChar = false;
        if (!st.hasMoreTokens()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        while (st.hasMoreTokens()) {
            String parts = st.nextToken();
            // System.out.println("Parts is " + parts);
            if (parts.equals(DESC)) {
                if (hasChangedDescription == true) {
                    return new IncorrectCommand(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
                }
                // System.out.println("I am inside desc");
                String desc = " ";
                while (!(parts.equals(ST) || parts.equals(ET) || parts.equals(SD)
                        || parts.equals("ed") | parts.equals(P)) && st.hasMoreTokens()) {
                    desc += (parts + " ");
                    parts = st.nextToken();
                    hasChangedDescription = true;
                }
                if (!(parts.equals(ST) || parts.equals(ET) || parts.equals(SD)
                        || parts.equals("ed") | parts.equals(P))) {
                    // System.out.println("I am here to add the last is
                    // "+parts);
                    desc += parts;
                    lastChar = true;
                }
                desc = desc.trim();
                if (Description.isValidDescription(desc)) {
                    System.out.println("The new desc is valid");
                    description = desc.substring(5);
                    hasChangedDescription = true;
                }
                // System.out.println("Description End: " + desc);
            }
            if (parts.equals(ST)) {
                if (hasChangedStartTime == true) {
                    return new IncorrectCommand(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
                }
                // System.out.println("I am inside start time");
                if (st.hasMoreTokens()) {
                    String startT = st.nextToken();
                    if (TaskTime.isValidTime(startT)) {
                        startTime = startT.trim();
                        hasChangedStartTime = true;
                    } else {
                        return new IncorrectCommand(
                                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskTime.MESSAGE_TASK_TIME_CONSTRAINTS));
                    }
=======

        String index = argsList.remove(0);
        if (!isInt(index)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_TASK_DISPLAYED_INDEX, EditCommand.MESSAGE_USAGE));
        }

        if (argsList.isEmpty()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        int targetIdx = Integer.valueOf(index);
        return splitInputWithGivenNewParameters(targetIdx, argsList);
    }

    private Command splitInputWithGivenNewParameters(int targetIdx, ArrayList<String> argsList) {
        while (!argsList.isEmpty()) {
            switch (argsList.get(0)) {
            case DESCRIPTION:
                if (hasTaskComponentArray[Task.DESCRIPTION_COMPONENT] == true) {
                    return new IncorrectCommand(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
                }
                updateDescription(argsList);
                break;

            case START_DATE:
                argsList.remove(0);

                if (hasTaskComponentArray[Task.START_DATE_COMPONENT] == true || argsList.isEmpty()) {
                    return new IncorrectCommand(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
                }
                taskComponentArray[Task.START_DATE] = argsList.remove(0);
                if (TaskDate.isValidDate(taskComponentArray[Task.START_DATE])) {
                    hasTaskComponentArray[Task.START_DATE_COMPONENT] = true;
>>>>>>> Stashed changes
                } else {
                    return new IncorrectCommand(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskDate.MESSAGE_TASK_DATE_CONSTRAINTS));
                }
<<<<<<< Updated upstream
                // System.out.println("Start time is " + startTime);
            }
            if (parts.equals(ET)) {
                if (hasChangedEndTime == true) {
=======
                break;
            case END_DATE:
                argsList.remove(0);
                if (hasTaskComponentArray[Task.END_DATE_COMPONENT] == true || argsList.isEmpty()) {
>>>>>>> Stashed changes
                    return new IncorrectCommand(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
                }
                taskComponentArray[Task.END_DATE] = argsList.remove(0);
                if (TaskDate.isValidDate(taskComponentArray[Task.END_DATE])) {
                    hasTaskComponentArray[Task.END_DATE_COMPONENT] = true;
                } else {
                    return new IncorrectCommand(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskDate.MESSAGE_TASK_DATE_CONSTRAINTS));
                }
<<<<<<< Updated upstream
                // System.out.println("End time is " + endTime);
            }
            if (parts.equals(SD)) {
                if (hasChangedStartDate == true) {
=======
                break;
            case START_TIME:
                argsList.remove(0);
                if (hasTaskComponentArray[Task.START_TIME_COMPONENT] == true || argsList.isEmpty()) {
>>>>>>> Stashed changes
                    return new IncorrectCommand(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
                }
                taskComponentArray[Task.START_TIME] = argsList.remove(0);
                if (TaskTime.isValidTime(taskComponentArray[Task.START_TIME])) {
                    hasTaskComponentArray[Task.START_TIME_COMPONENT] = true;
                } else {
                    return new IncorrectCommand(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskTime.MESSAGE_TASK_TIME_CONSTRAINTS));
                }
<<<<<<< Updated upstream
                // System.out.println("Start Date is " + startDate);
            }
            if (parts.equals(ED)) {
                if (hasChangedEndDate == true) {
=======
                break;
            case END_TIME:
                argsList.remove(0);
                if (hasTaskComponentArray[Task.END_TIME_COMPONENT] == true || argsList.isEmpty()) {
>>>>>>> Stashed changes
                    return new IncorrectCommand(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
                }
                taskComponentArray[Task.END_TIME] = argsList.remove(0);
                if (TaskTime.isValidTime(taskComponentArray[Task.END_TIME])) {
                    hasTaskComponentArray[Task.END_TIME_COMPONENT] = true;
                } else {
                    return new IncorrectCommand(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskTime.MESSAGE_TASK_TIME_CONSTRAINTS));
                }
<<<<<<< Updated upstream
                // System.out.println("End Date is " + endDate);
            }
            if (parts.equals(P)) {
                if (hasChangedPriority == true) {
=======
                break;
            case PRIORITY:
                argsList.remove(0);
                if (hasTaskComponentArray[Task.PRIORITY_COMPONENT] == true || argsList.isEmpty()) {
>>>>>>> Stashed changes
                    return new IncorrectCommand(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
                }
                taskComponentArray[Task.TASK_PRIORITY] = argsList.remove(0);
                if (TaskPriority.isValidPriority(taskComponentArray[Task.TASK_PRIORITY])) {
                    hasTaskComponentArray[Task.PRIORITY_COMPONENT] = true;
                } else {
                    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            TaskPriority.MESSAGE_TASK_PRIORITY_CONSTRAINTS));
                }
<<<<<<< Updated upstream
                // System.out.println("Priority is " + taskPriority);
            }
            if (!(parts.equals(DESC) || parts.equals(ST) || parts.equals(ET) || parts.equals(SD)
                    || parts.equals("ed") || parts.equals(P)) && lastChar == false) {
                // System.out.println("I am here as incrct because of parts
                // "+parts);
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
            }
        }
        // System.out
        // .println("Desc: " + hasChangedDescription + " st: " +
        // hasChangedStartTime + " et: " + hasChangedEndTime
        // + " sd: " + hasChangedStartDate + " ed: " + hasChangedEndDate + " p:
        // " + hasChangedPriority);
        try {
            // System.out.println("I am here to exectue edit command");
            return new EditCommand(targetIdx, new Description(description), hasChangedDescription,
                    new TaskDate(startDate), hasChangedStartDate, new TaskDate(endDate), hasChangedEndDate,
                    new TaskTime(startTime), hasChangedStartTime, new TaskTime(endTime), hasChangedEndTime,
                    new TaskPriority(taskPriority), hasChangedPriority);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
=======
                break;
            default:
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
            }
        }
        try {
            return new EditCommand(targetIdx, new Description(taskComponentArray[Task.DESCRIPTION]),
                    new TaskDate(taskComponentArray[Task.START_DATE]), new TaskDate(taskComponentArray[Task.END_DATE]),
                    new TaskTime(taskComponentArray[Task.START_TIME]), new TaskTime(taskComponentArray[Task.END_TIME]),
                    new TaskPriority(taskComponentArray[Task.TASK_PRIORITY]), hasTaskComponentArray);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    private void updateDescription(ArrayList<String> argsList) {
        argsList.remove(0);
        String desc = " ";
        while (!argsList.isEmpty() && !(argsList.get(0).equals(START_TIME) || argsList.get(0).equals(END_TIME)
                || argsList.get(0).equals(START_DATE) || argsList.get(0).equals(END_DATE)
                || argsList.get(0).equals(PRIORITY))) {
            desc += (argsList.remove(0) + " ");
            hasTaskComponentArray[Task.DESCRIPTION_COMPONENT] = true;
        }
        hasTaskComponentArray[Task.DESCRIPTION_COMPONENT] = true;
        taskComponentArray[Task.DESCRIPTION] = desc.trim();
>>>>>>> Stashed changes
    }

    // @@author

    // @@author A0139257X
    /**
     * Parses arguments in the context of the add task command.
     *
     * @param full
     *            command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args) {
        if (args.isEmpty()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        ArrayList<String> argsList = tokenizeArguments(args);
        Queue<String> initialQueue = initialiseArgQueue(argsList);

        initialisePartitionQueue();
        initialiseHasTaskComponentArray();
        initialiseTaskComponentArray();

        try {
            splitInputIntoComponents(initialQueue);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        addReservedWordToDescription(); // Add trailing reserved word to
                                        // description

        extractDescriptionComponent();
        // adjustEndDate();
        adjustStartTime();

        return addTaskAccordingToType();
    }

    /**
     * Returns a new AddCommand object according to task type if successful else
     * return IncorrectCommand
     */
    private Command addTaskAccordingToType() {
        if (isEventTask()) {
            try {
                return new AddCommand(Task.EVENT_TASK, taskComponentArray, hasTaskComponentArray,
                        getTagsFromArgs(taskComponentArray[Task.TAG]));
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        } else {
            try {
                return new AddCommand(Task.FLOATING_TASK, taskComponentArray, hasTaskComponentArray,
                        getTagsFromArgs(taskComponentArray[Task.TAG]));
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        }
    }

    /**
     * Separates the content in the initialQueue into its different task
     * components
     * 
     * @throws IllegalValueException
     */
    private void splitInputIntoComponents(Queue<String> initialQueue) throws IllegalValueException {
        String token = "";
        int priorityCount = 0;
        int recurrenceCount = 0;

        while (!initialQueue.isEmpty()) {
            token = initialQueue.poll().trim();
            if (!isReservedWord(token)) {
                offerTokenToQueue(DESCRIPTION_QUEUE, token);
                continue;
            } else if (token.equals(BY)) {
                offerTokenToQueue(BY_QUEUE, token);
                continue;
            } else if (token.equals(ON)) {
                offerTokenToQueue(ON_QUEUE, token);
                continue;
            } else if (token.equals(AT)) {
                offerTokenToQueue(AT_QUEUE, token);
                continue;
            } else if (token.equals(FROM)) {
                offerTokenToQueue(FROM_QUEUE, token);
                continue;
            } else if (token.equals(TO)) {
                offerTokenToQueue(TO_QUEUE, token);
                continue;
            } else if (token.startsWith(Tag.PREFIX)) {
                addReservedWordToDescription();
                taskComponentArray[Task.TAG] += " " + token;
                continue;
            } else if (token.startsWith(TaskPriority.PREFIX)) {
                addReservedWordToDescription();
                if (priorityCount > 0) {
                    throw new IllegalValueException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                } else {
                    taskComponentArray[Task.TASK_PRIORITY] = token.substring(token.indexOf(TaskPriority.PREFIX) + 2);
                    priorityCount++;
                }
                continue;
<<<<<<< Updated upstream
            } else if(token.startsWith(RecurringType.PREFIX)){
                tempToken = flushQueue(byQueue, onQueue, atQueue, fromQueue, toQueue);
                if (!tempToken.isEmpty()) {
                    descriptionQueue.offer(tempToken);
                }
=======
            } else if (token.startsWith(RecurringType.PREFIX)) {
                addReservedWordToDescription();
>>>>>>> Stashed changes
                if (recurrenceCount > 0) {
                    throw new IllegalValueException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                } else {
<<<<<<< Updated upstream
                    
                    recurringType = token.substring(token.indexOf(RecurringType.PREFIX) + 2);
                    hasRecurring = true;
                    recurrenceCount++;
                }
                
=======
                    taskComponentArray[Task.RECURRING_TYPE] = token.substring(token.indexOf(RecurringType.PREFIX) + 2);
                    hasTaskComponentArray[Task.RECURRING_COMPONENT] = true;
                    recurrenceCount++;
                }
>>>>>>> Stashed changes
            } else if (TaskDate.isValidDate(token)) {
                determineDateisActualDateOrDescription(token);
            } else if (TaskTime.isValidTime(token)) {
                determineTimeIsActualTimeOrDescription(token);
            }
        }
    }

    /**
     * Determine if the given token is an intended date or is part of a
     * description Token is actual date if it is preceded by a Date-Time prefix
     */
    private void determineDateisActualDateOrDescription(String token) {
        if (!isPrecededByDateTimePrefix(partitionQueue)) {
            partitionQueue.get(DESCRIPTION_QUEUE).offer(token);
        } else if (isPrecededByPrefixInQueue(ON_QUEUE)) {
            if (!hasTaskComponentArray[Task.START_DATE_COMPONENT]) {
                extractDateTimeWhenPrecededByPrefix(token, ON_QUEUE, Task.START_DATE, Task.START_DATE_COMPONENT);
            } else {
                offerTokenToQueue(DESCRIPTION_QUEUE, token);
            }
        } else if (isPrecededByPrefixInQueue(BY_QUEUE)) {
            if (!hasTaskComponentArray[Task.END_DATE_COMPONENT]) {
                extractDateTimeWhenPrecededByPrefix(token, BY_QUEUE, Task.END_DATE, Task.END_DATE_COMPONENT);
            } else {
                offerTokenToQueue(DESCRIPTION_QUEUE, token);
            }
        } else if (isPrecededByPrefixInQueue(AT_QUEUE)) {
            addReservedWordToDescription();
            partitionQueue.get(DESCRIPTION_QUEUE).offer(token);
        } else if (isPrecededByPrefixInQueue(FROM_QUEUE)) {
            if (!hasTaskComponentArray[Task.START_DATE_COMPONENT]) {
                extractDateTimeWhenPrecededByPrefix(token, FROM_QUEUE, Task.START_DATE, Task.START_DATE_COMPONENT);
            } else {
                offerTokenToQueue(DESCRIPTION_QUEUE, token);
            }
        } else if (isPrecededByPrefixInQueue(TO_QUEUE)) {
            if (!hasTaskComponentArray[Task.END_DATE_COMPONENT]) {
                extractDateTimeWhenPrecededByPrefix(token, TO_QUEUE, Task.END_DATE, Task.END_DATE_COMPONENT);
            } else {
                offerTokenToQueue(DESCRIPTION_QUEUE, token);
            }
        }
    }

    /**
     * Determine if the given token is an intended time or is part of a
     * description Token is actual time if it is preceded by a Date-Time prefix
     */
    private void determineTimeIsActualTimeOrDescription(String token) {
        if (!isPrecededByDateTimePrefix(partitionQueue)) {
            partitionQueue.get(DESCRIPTION_QUEUE).offer(token);
        } else if (isPrecededByPrefixInQueue(BY_QUEUE)) {
            if (!hasTaskComponentArray[Task.END_TIME_COMPONENT]) {
                extractDateTimeWhenPrecededByPrefix(token, BY_QUEUE, Task.END_TIME, Task.END_TIME_COMPONENT);
            } else {
                offerTokenToQueue(DESCRIPTION_QUEUE, token);
            }
        } else if (isPrecededByPrefixInQueue(AT_QUEUE)) {
            if (!hasTaskComponentArray[Task.START_TIME_COMPONENT]) {
                extractDateTimeWhenPrecededByPrefix(token, AT_QUEUE, Task.START_TIME, Task.START_TIME_COMPONENT);
            } else {
                offerTokenToQueue(DESCRIPTION_QUEUE, token);
            }
        } else if (isPrecededByPrefixInQueue(FROM_QUEUE)) {
            if (!hasTaskComponentArray[Task.START_TIME_COMPONENT]) {
                extractDateTimeWhenPrecededByPrefix(token, FROM_QUEUE, Task.START_TIME, Task.START_TIME_COMPONENT);
            } else {
                offerTokenToQueue(DESCRIPTION_QUEUE, token);
            }
        } else if (isPrecededByPrefixInQueue(TO_QUEUE)) {
            if (!hasTaskComponentArray[Task.END_TIME_COMPONENT]) {
                extractDateTimeWhenPrecededByPrefix(token, TO_QUEUE, Task.END_TIME, Task.END_TIME_COMPONENT);
            } else {
                offerTokenToQueue(DESCRIPTION_QUEUE, token);
            }
        } else if (isPrecededByPrefixInQueue(ON_QUEUE)) {
            offerTokenToQueue(DESCRIPTION_QUEUE, token);
        }
    }

    /**
     * Flush outstanding Date-Time prefix into description queue Add Token to
     * respective queue
     */
    private void offerTokenToQueue(int queueType, String token) {
        addReservedWordToDescription();
        partitionQueue.get(queueType).offer(token);
    }

    private boolean isEventTask() {
        return hasTaskComponentArray[Task.START_DATE_COMPONENT] || hasTaskComponentArray[Task.END_DATE_COMPONENT]
                || hasTaskComponentArray[Task.START_TIME_COMPONENT] || hasTaskComponentArray[Task.END_TIME_COMPONENT];
    }

    private void adjustStartTime() {
        if ((TaskDate.isValidToday(taskComponentArray[Task.START_DATE])
                && !hasTaskComponentArray[Task.START_TIME_COMPONENT])
                || taskComponentArray[Task.START_DATE].equals(TaskDate.DEFAULT_DATE)
                        && !hasTaskComponentArray[Task.START_TIME_COMPONENT]) {

            taskComponentArray[Task.START_TIME] = TaskTime.getTimeNow().toString();
        }
    }

    private void extractDescriptionComponent() {
        while (!partitionQueue.get(DESCRIPTION_QUEUE).isEmpty()) {
            taskComponentArray[Task.DESCRIPTION] += partitionQueue.get(DESCRIPTION_QUEUE).poll() + " ";
        }
        taskComponentArray[Task.DESCRIPTION].trim();
    }

    private void extractDateTimeWhenPrecededByPrefix(String token, int queueType, int taskComponent,
            int taskComponentBoolean) {
        partitionQueue.get(queueType).poll();
        taskComponentArray[taskComponent] = token;
        hasTaskComponentArray[taskComponentBoolean] = true;
    }

<<<<<<< Updated upstream
            if(hasRecurring){
                return new IncorrectCommand(FloatingTask.RECURRING_TYPE_NOT_ALLOWED);
            }else{
                try {
                    return new AddCommand(description, Task.FLOATING_TASK, startDate, endDate, startTime, endTime,
                            taskPriority, recurringType, getTagsFromArgs(tagString));
                } catch (IllegalValueException ive) {                    
                    return new IncorrectCommand(ive.getMessage());
                }
            }
=======
    private boolean isPrecededByDateTimePrefix(ArrayList<Queue<String>> partitionQueue) {
        return !partitionQueue.get(BY_QUEUE).isEmpty() || !partitionQueue.get(ON_QUEUE).isEmpty()
                || !partitionQueue.get(AT_QUEUE).isEmpty() || !partitionQueue.get(FROM_QUEUE).isEmpty()
                || !partitionQueue.get(TO_QUEUE).isEmpty();
    }

    private boolean isPrecededByPrefixInQueue(int queueType) {
        return !partitionQueue.get(queueType).isEmpty();
    }

    private boolean isReservedWord(String token) {
        return token.equals(BY) || token.equals(ON) || token.equals(AT) || token.equals(FROM) || token.equals(TO)
                || TaskDate.isValidDate(token) || TaskTime.isValidTime(token) || token.startsWith(Tag.PREFIX)
                || token.startsWith(TaskPriority.PREFIX) || token.startsWith(RecurringType.PREFIX);
    }

    private void addReservedWordToDescription() {
        String tempToken = flushQueue(partitionQueue);
        if (!tempToken.isEmpty()) {
            partitionQueue.get(DESCRIPTION_QUEUE).offer(tempToken);
>>>>>>> Stashed changes
        }
    }

    private String flushQueue(ArrayList<Queue<String>> partitionQueue) {
        String token = "";

        if (!partitionQueue.get(BY_QUEUE).isEmpty()) {
            token = partitionQueue.get(BY_QUEUE).poll();
        } else if (!partitionQueue.get(ON_QUEUE).isEmpty()) {
            token = partitionQueue.get(ON_QUEUE).poll();
        } else if (!partitionQueue.get(AT_QUEUE).isEmpty()) {
            token = partitionQueue.get(AT_QUEUE).poll();
        } else if (!partitionQueue.get(FROM_QUEUE).isEmpty()) {
            token = partitionQueue.get(FROM_QUEUE).poll();
        } else if (!partitionQueue.get(TO_QUEUE).isEmpty()) {
            token = partitionQueue.get(TO_QUEUE).poll();
        }

        return token;
    }

    private Queue<String> initialiseArgQueue(ArrayList<String> argsList) {
        Queue<String> argsQueue = new LinkedList<String>();
        for (String arg : argsList) {
            argsQueue.offer(arg);
        }
        return argsQueue;
    }

    private void initialisePartitionQueue() {
        partitionQueue = new ArrayList<Queue<String>>();
        for (int i = 0; i < NUM_QUEUE; i++) {
            partitionQueue.add(new LinkedList<String>());
        }
    }

    private void initialiseHasTaskComponentArray() {
        hasTaskComponentArray = new boolean[Task.NUM_BOOLEAN_TASK_COMPONENT];
    }

    private void initialiseTaskComponentArray() {
        taskComponentArray = new String[Task.NUM_TASK_COMPONENT];
        taskComponentArray[Task.DESCRIPTION] = " ";
        taskComponentArray[Task.START_DATE] = TaskDate.DEFAULT_DATE;
        taskComponentArray[Task.END_DATE] = taskComponentArray[Task.START_DATE];
        taskComponentArray[Task.START_TIME] = TaskTime.DEFAULT_START_TIME;
        taskComponentArray[Task.END_TIME] = TaskTime.DEFAULT_END_TIME;
        taskComponentArray[Task.TASK_PRIORITY] = TaskPriority.DEFAULT_PRIORITY;
        taskComponentArray[Task.RECURRING_TYPE] = RecurringType.DEFAULT_RECURRING;
        taskComponentArray[Task.TAG] = "";
    }

    private ArrayList<String> tokenizeArguments(String args) {
        ArrayList<String> argsList = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(args, " ");
        while (st.hasMoreTokens()) {
            argsList.add(st.nextToken());
        }
        return argsList;
    }
    // @@author

    /**
     * Extracts the new task's tags from the add command's tag arguments string.
     * Merges duplicate tag strings.
     */
    private static Set<String> getTagsFromArgs(String tagArguments) throws IllegalValueException {
        // no tags
        if (tagArguments.isEmpty()) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays
                .asList(tagArguments.replaceFirst(" " + Tag.PREFIX, "").split(" " + Tag.PREFIX));
        return new HashSet<>(tagStrings);
    }

    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {

        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return new DeleteCommand(index.get());
    }

    /**
     * Parses arguments in the context of the select task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareSelect(String args) {
        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        return new SelectCommand(index.get());
    }

    /**
     * Returns the specified index in the {@code command} IF a positive unsigned
     * integer is given as the index. Returns an {@code Optional.empty()}
     * otherwise.
     */
    private Optional<Integer> parseIndex(String command) {
        final Matcher matcher = TASK_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if (!StringUtil.isUnsignedInteger(index)) {
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

    /**
     * Parses arguments in the context of the find task command.
     *
     * @param args
     *            string
     * @return the prepared command
     */
    private Command prepareFind(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywordSet);
    }

    /** @@author A0142130A **/

    /**
     * Parses arguments in the context of undo command.
     * 
     */
    private Command prepareUndo(String args) {
        if (args.isEmpty()) {
            return new UndoCommand();
        }
        
        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE));
        } 
        return new UndoCommand(index.get());
    }

    /**
     * Parses arguments in the context of the find task by tags command.
     * 
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareFindByTag(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindTagCommand(keywordSet);

    }

    /**
     * Parses arguments in the context of the save storage location command.
     * 
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareSaveStorageLocation(String args) {
        if (args.isEmpty()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveStorageLocationCommand.MESSAGE_USAGE));
        }
        return new SaveStorageLocationCommand(args);
    }
    /** @@author **/

    // @@author A0148004R
    /**
     * Parses arguments in the context of the done task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareDone(String args) {
        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }

        return new DoneCommand(index.get());
    }
   
    /**
     * Parses arguments in the context of the Undone task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareUndone(String args) {
        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoneCommand.MESSAGE_USAGE));
        } 
        
        return new UndoneCommand(index.get());
    }
<<<<<<< Updated upstream
    //@@author
    
    //@@author A0142073R
=======
    // @@author

    // @@author A0142073R

>>>>>>> Stashed changes
    private static boolean isInt(String s) {
        try {
            int i = Integer.parseInt(s);
            return true;
        }

        catch (NumberFormatException er) {
            return false;
        }
    }

    // @@author
}