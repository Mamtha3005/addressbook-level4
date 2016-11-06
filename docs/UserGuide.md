# User Guide
* [Introduction](#introduction)
* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)
* [Appendix A](#appendix-a)
* [Appendix B](#appendix-b)

<!--- @@author A0142073R ---> 

## Introduction
Are you having a hard time remembering all the work you have to do? Do you have trouble finding a task manager that suits your preference for keyboard input? Well, worry no more, Taskell is here for you! <br>
Taskell will be your personal secretary. It will keep track of your daily tasks and remind you of any important dates and deadlines. What distinguishes Taskell from other task managers is that Taskell only requires a single line of command for every task input. This means that you can record each one of your tasks with just a single statement. You will no longer have to use a mouse if you do not wish to. <br>
Ready to begin life anew with a more efficient task manager? Read on to find out more! 

<!--- @@author --> 

## Quick Start

Step 1: Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

  > Having any Java 8 version is not enough. <br>
    This application will not work with earlier versions of Java 8.

Step 2: Download the latest `taskell.jar` from <a href="https://github.com/CS2103AUG2016-W15-C3/main/releases">here</a>.<br>
	<br><img src="images/Icon.png" width="100"></br>
Step 3: Copy the file to the folder you want to use as the home folder for your Task Manager.<br>
Step 4: Double-click the file to start the application. The GUI should appear in a few seconds. <br>
 <br><p align="center"><img src="images/GUI.png" width="800"><br>
Diagram 1: A screenshot of the Graphical User Interface (GUI)<br>
</p>
<br>
Step 5:	Type the relevant command in the command box and press <kbd>Enter</kbd> to execute it.<br>
Step 6: Some example commands you can try:<br>
   * **`list`** : displays all contacts
   * **`add`** buy MA1101R textbook today : adds a task called buy MA1101R textbook to be done by today.
   * **`delete`** 3 : deletes the 3rd task shown in the current list
   * **`exit`** : exits the application <br>
Refer to the [Features](#features) section below for details of each command.<br>


## Features

This section shows the different commands that you can use in Taskell. Words that are in UPPER_CASE are parameters. These parameters have to be in the order stated below. Words that are in italics are used to identify the parameters while words enclosed in SQUARE_BRACKETS are optional. INDEX refers to the index number shown in the most recent listing.

#### Viewing list of commands : `help`

To open the help window<br>
 Format: `help`
 
<!--- @@author A0139257X --->

#### Adding a task: `add`
To add a floating task<br>
Format: 
- `add` TASK <br>
Example: `add` read Harry Potter Book <br>

To add a deadline task<br>
> Please refer to Appendix A and B for date and time format respectively that Taskell supports. <br>

Formats:
- `add` TASK <i>by</i> [DATE] <br>
Example: add buy textbook <i>by</i> today<br>
- `add` TASK <i>by</i> [TIME]<br>
Example: add visit Sandy at her house by the seaside <i>by</i>  3.35pm<br>
- `add` TASK <i>by</i>  [DATE] <i>by</i>  [TIME] <br>
Example: `add` do lab homework <i>by</i> Friday <i>by</i> 7pm

To have a greater flexibility in the command format, Taskell supports a few natural variation such as <i>on</i> and <i>at</i>.<br>

- `add ` TASK <i>on</i> [DATE]<br>
Example: `add ` go for meeting <i>on</i> monday <br>
- `add ` TASK <i>at</i> [TIME] <br>
Example: `add ` go for meeting <i>at</i> 3pm <br>
- `add ` TASK <i>on</i> [DATE] <i>at</i> [TIME] <br>
Example: `add ` go for meeting <i>on</i> Sunday <i>at</i> 3pm <br>
- `add ` TASK <i>on</i> [DATE] <i>by</i>[TIME] <br>
Example: `add ` go for meeting <i>on</i> 1-jan <i>by</i> 3pm <br>

To add an event task<br>
Formats:
- `add ` TASK <i>on</i> [DATE] <i>startat</i> [TIME] <i>endat</i> [TIME]<br>
Example: `add ` schedule meeting <i>on</i> Thursday <i>startat</i> 1pm <i>endat</i> 9pm<br>

- `add ` TASK <i>startat</i>  [TIME]<br>
Example: `add ` concert by 2am band <i>startat</i> 7pm<br>

- `add ` TASK <i>endat</i>  [TIME]<br>
Example: `add ` netball training <i>endat</i> 7pm<br>

<!--- @@author -->

<!--- @@author A0148004R --->

#### Listing tasks : `list`
Formats: 
- `list` <br>
Displays a list of uncompleted tasks.<br>
- `list-all` <br>
Displays a list of all tasks, both complete and incomplete.<br>
- `list-date` DATE <br> 
Displays a list of all the tasks due on the specific date.<br>
- `list-done` <br>
Displays a list of completed tasks.<br>
- `list-priority` PRIORITY<br>
Displays a list of tasks with given priority.<br>
<br><p align="center"><img src="images/ListToday.png" width="800"></br>
Diagram 2: Displays all the tasks to be done by today after typing list-date.

<!-- @@author -->
   
<!--- @@author A0142130A --->  

#### Finding tasks: `find`
You can use the find command to view tasks with specific keywords.<br>
Formats: <br>
-`find KEYWORD [MORE_KEYWORDS]`<br>
Displays a list of tasks with description or tags that match all the keywords.<br>
Example: `find banana milk essay`<br>
This returns all tasks with description or tags that match all keywords `banana`, `milk`, and `essay`. <br>

-`find-tag TAG [MORE_TAGS]`<br>
Displays list of tasks with the same tags.<br>
Example: `find homework essay cs2103`<br>
This returns any task with either tag `homework`, `essay`, or `cs2103`.<br>
 <br><p align="center"><img src="images/findReport.png" width="800"></br>
 <br><p align="center"><img src="images/findReportResult.png" width="800"></br>
 
 <!--- @@author ---> 

Diagrams 3 and 4: Keying in `find report` displays list of tasks with report as one of the keywords in task description

> Take Note! <br>
> * The order of the keywords does not matter. e.g. `chicken egg` will match `egg chicken`.
> * Full words will be matched e.g. `chicken` will match `chickens`.
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `chicken` will match `chicken duck`

<!--- @@author A0142130A ---> 

#### Showing history : `history` or `hist`
You can view the command history available for undo on the right panel to refer to when undoing previous commands.<br>
To save your time, Taskell has a short form command `hist`. <br>
Format: `history`<br>

> Take Note! <br>
> * Only commands available for undo are shown here. 
> * Refer to undo section for more information about what commands undo supports.

#### Reverting previous action : `undo`
If you wish to undo your most recent action, you can do so by using the undo command.<br>
You can also undo previous commands, <kbd>Enter</kbd> `hist` to see a list of 
previous commands that can be undone.<br>
Then use undo command with specified INDEX from the list of command history.<br>
Formats: <br>
-`undo`<br> 
Undo most recent command executed.<br>
-`undo INDEX`<br> 
Undo by index of command history.<br>
Example: `hist`, then `undo 3`, will undo third command in command history.<br>

> Take Note! <br>
> * Undo command only supports add, edit, delete, done, undone and undo commands.
> * Clear commands are irreversible!

<!--- @@author ---> 

#### Deleting a task : `delete`
To delete a task, use the delete command. This command deletes the task at a specified INDEX. The index refers to the index number shown in the most recent listing.<br>
Format: `delete INDEX`<br>
 <br><p align="center"><img src="images/delete1.png" width="800"></br>
Diagram 5: Entering `delete 1 will delete "Arrange meeting with XYZ company".`<br>

Example: `find violin, then delete 1<br>
This deletes the 1st task in the results of the find command.

Format: `delete` INDEX<br>
 <br><p align="center"><img src="images/delete1.png" width="800"></br>
Diagram 6: Deletes the first task in the list.<br>

<!--- @@author A0148004R --->

#### Marking a task as completed: `done`
Format: `done` INDEX<br>
Example: `done` 1<br>
This adds the 1st task as completed and moves it to the completed list.<br>

<!--- @@author --->

<!--- @@author A0142073R --->

#### Editing a task : `edit`
To edit a task<br>
Formats: 
- `edit ` INDEX `st: `[NEWSTARTTIME] `et: `[NEWENDTIME] `desc: `[NEWDESCRIPTION] `sd: `[NEWSTARTDATE] `ed: `[NEWENDDATE] `p: `[NEWPRIORITY]<br>
 <br><p align="center"><img src="images/editCmd.png" width="800"></br>
Diagram 7: Edits the 1st task on the list.<br>
Entering "edit 1 desc: send all emails sd: 11-11-2016 ed: 12-11-2016 st: 3pm et: 4pm p: 3", will update description to "send all emails", start date to 11-11-2016, end date to 12-11-2016, start time to 3pm end time to 4pm and priority to 3.<br>

<!--- @@author --->

<!--- @@author A0142130A ---> 

#### Showing calendar view : `calendar` or `cal`
You can view the calendar for the week on the right panel to refer to the dates and any events scheduled 
when adding tasks and scheduling events.<br>
By default, the calendar view will be shown on the right panel. As `history` displays command history on the right panel as well, this command is meant for your convenience if you want to view calendar again.<br>
Please note that entering other commands (i.e. `find`, `add`) will also revert the right panel back to calendar as it is the default view. <br>
To save your time, Taskell has a short form command `cal`. <br>
Format: `calendar`<br>

#### Saving the information in Taskell : `save`
You can specify the path of a folder to store Taskell's data file. Please note that you should have permissions to access the folder. <br>
To obtain the filepath, navigate to the required file in your File Explorer. Copy the path at the top of the
screen and paste into Taskell. Refer to diagram 8 for an example of a filepath. <br>

<p align="center"> <img src="images/filepath_screenshot.png" width="1000"> </br>
Diagram 8: Screenshot of File Explorer in Windows.  <br>

If the specified directory is valid but the file is missing, for example if command is `save C:\Users\Jim\Documents\chicken`,  and `C:\Users\Jim\Documents\chicken` is valid
but `chicken` file is not created, Taskell will create the file for you.<br>
Format: `save FOLDERPATH`<br>
Example: `save C:\Users\Jim\Documents`

> Take Note! <br>
> * If you only specify a folder name without directory, i.e. `save cat`, Taskell will create a file
named "cat" within Taskell's own directory. Whereas `save C:/Users/Jim/Documents/cat` will open a file named "cat" within your desktop's Documents folder.
> * Windows OS has reserved symbols that cannot be used in file names. The list is given below:
    * >
    * <
    * :
    * "
    * /
    * \
    * |
    * ?
    * *
> * Linux OS has reserved symbols that cannot be used in file names. The list is given below:
    * <
    * >
    * :
    * &
    * /
    * |

#### Clearing all entries : `clear`
Permanently clears **all** task data. <br>
Format: `clear`  <br>

> Take Note! <br>
> * Clear commands are irreversible!

<!--- @@author ---> 

#### Exiting the program : `exit`
Format: `exit`  

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the application in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Taskell folder.<br>

<!--- @@author A0142130A --->

**Q**: Do I have to save the data every time I enter new tasks? <br>
**A**: No, Taskell auto-saves your data every time you enter new tasks. Use `save` only when you want to
transfer your data to a new location on your computer. <br>

**Q**: Do I have to use `exit` command when I want to exit the application? <br>
**A**: There is no need to, as Taskell can be closed by the top right X button with the mouse as well. This command is for your convenience if you prefer to use the keyboard. <br>

<!--- @@author --->

<!--- @@author A0142073R ---> 
     
## Command Summary

Command | Format  
-------- | :-------- 
Add Floating Task | `add` TASK ITEM 
Add Event | `add` TASK ITEM <strong>by</strong> [DATE]
Add Event | `add` TASK ITEM <strong>by</strong> [TIME]
Add Event With Deadline | `add` TASK ITEM <strong>by</strong> [DATE][TIME]
Calendar View | `calendar` or `cal`
Clear | `clear`
Delete | `delete` INDEX
Edit | `edit` INDEX NEWTASK
Find Tasks | `find` KEYWORD [MORE_KEYWORDS]
Find Tasks by Tag | `find-tag` KEYWORD [MORE_KEYWORDS]
Help | `help`
History | `history` or `hist`
List Incomplete Tasks| `list`
List All Tasks | `list-all`
List by Given Date | `list-date` [DATE]
List Done Tasks | `list-done`
Mark Task Done | `done` INDEX
Mark Task Undone | `undone` INDEX
Undo | `undo` or `undo` INDEX

## Appendix A

Supported Date Format |   Example  
-------- | :-------- 
DD-MM-YY |1-1-16 
DD-MM-YY  | 1-1-2016 
DD-MM-YY  | 1-Jan-2016
DD-MM-YY  | 1-January-2016  
DD-MM-YY  | 1.Jan.2016
DD-MM-YY  | 1.January.2016  
MM-YY  | july-16
MM  | july
day  | today
day  | tdy
day  | tmr
day  | tomorrow
day  | thursday

## Appendix B

Supported Time Format |   Example  
-------- | :-------- 
12hour |1pm
12hour |12am
12hour |11.45pm

<!-- @@author -->
