# User Guide
* [Introduction](#introduction)
* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Glossary](#glossary)
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

Step 2: Download the latest `Taskell.jar` from <a href="https://github.com/CS2103AUG2016-W15-C3/main/releases">here</a>.<br>
	<br> &nbsp; &nbsp; &nbsp; &nbsp; <img src="images/Icon.png" width="100"></br>
	
Step 3: Copy the file to the folder you want to use as the home folder for your Task Manager.<br>
Step 4: Double-click the file to start the application. The GUI should appear in a few seconds.
 <br><p align="center"><img src="images/screenshots/screen_calendar.PNG" width="800"><br>
Diagram 1: A screenshot of the Graphical User Interface (GUI)<br>
</p>

Step 5:	Type the relevant command in the command box and press <kbd>Enter</kbd> to execute it.<br>
Step 6: Some example commands you can try:<br>
   * `list` : displays all tasks
   * `add` buy MA1101R textbook today : adds a task called buy MA1101R textbook to be done by today.
   * `delete` 3 : deletes the 3rd task shown in the current list
   * `exit` : exits the application <br>
Refer to the [Features](#features) section below for details of each command.<br>

<!--- @@author A0139257X --->
## Features

This section shows the different commands that you can use in Taskell. Words that are in UPPER_CASE are parameters. The parameters are listed below.
- TASK: Indicates the content of a work
- DATE: Indicates a date

> Please refer to Appendix A for date format that Taskell supports. <br> 
> Default start date has been set to today's date. <br>
> Default end date has been set to be the same as the start date. <br>

- TIME: Indicates a time

> Please refer to Appendix B for time format that Taskell supports. <br>
> Default start time has been set to 12:00AM <br>
> Default end time has been set to 11:59PM <br>
> If the start date is today and no start time is provided, the default start time will be set to be the current time.

- PRIORITY: Indicates the level of importance of a task ranging from level 0 to 3. Level 0, 1, 3 indicates default, low and high priority respectively. 

> In the GUI, level 1,2 and 3 tasks are marked as green, yellow and red respectively. Tasks with default priority level are not marked with any colors.

- RECURRING: Indicates the repetitive nature of a task. A task can be repeated daily, weekly or monthly.
- TAG: Indicates the category a task belongs to

Words that are in italics are used to identify the parameters while words enclosed in SQUARE_BRACKETS are optional. <br>
INDEX refers to the index number shown in the most recent listing.
<!--- @@author --->

#### Viewing list of commands : `help`

To open the help window<br>
 Format: `help`
 
<!--- @@author A0139257X --->

#### Adding a task: **`add`**
You can use the add command to add different tasks.<br>

To add a floating task<br>
Format: 
`add` TASK <br>
Example: `add` Read Harry Potter book <br>

To add a task with priority<br>
Format:
`add` TASK <i>p/</i>PRIORITY <br>
Example: `add` Complete math assignment <i>p/</i>3<br>

To add a task with tag(s)<br>
Formats:
- `add` TASK <i>#</i>TAG <br>
Example: `add` Meet Alice in Bugis <i>#</i>friends<br>
- `add` TASK <i>#</i>TAG [<i>#</i>MORE_TAGS] <br>
Example: `add` Swimming with Jane <i>#</i>friends <i>#</i>leisure <br>

To add a recurring task <br>
Format:
`add` TASK <i>r/</i>RECURRING <br>
Example: `add` Read newspaper <i>on</i> mon <i>r/</i>daily
> Floating tasks are not allowed to have recurring status since there have no element of date or time

To add a task with date and time<br>
Formats:
- `add` TASK <i>from</i> START_DATE <i>to</i> END_DATE<br>
Example: `add` Go camping at Kota Tinggi <i>from</i> 3-jun-2016 <i>to</i> 7-jun-2016
- `add` TASK <i>from</i> START_TIME <i>to</i> END_TIME <br>
Example: `add` Watch Dr Strange <i>from</i> 7.30pm <i>to</i> 9.25pm


To allow greater flexibility in the command format, Taskell supports a few natural variation such as <i>by</i>, <i>on</i> and <i>at</i>.
<br>

The <i>by</i> keyword indicates that the task is a deadline task. Any date or time preceded by this keyword will be stored as an end date and end time respectively.
<br>
Formats:
- `add` TASK <i>by</i> DATE <br>
Example: `add` Buy textbook <i>by</i> tuesday
- `add` TASK <i>by</i> TIME<br>
Example: `add` visit Sandy at her house by the seaside <i>by</i> 3.35pm
- `add` TASK <i>by</i>  DATE <i>by</i>  TIME <br>
Example: `add` Do lab homework <i>by</i> Friday <i>by</i> 7pm
<br>

The <i>on</i> keyword indicates that the task has to be done on the given date. Any date preceded by this keyword will be stored as a start date.
<br>


Format:
`add` TASK <i>on</i> DATE<br>
Example: `add` Go for meeting <i>on</i> mon <br>


The <i>at</i> keyword indicates that the task has to be done at the given time. Any time preceded by this keyword will be stored as a start time.
<br>

Format:
`add` TASK <i>at</i> TIME <br>
Example: `add` Go for meeting <i>at</i> 3pm <br>


Having understood the aforementioned behaviors of the <i>by</i>, <i>on</i>, <i>at</i>, <i>from</i> and <i>to</i> keyword, you can fuse them together to form more complex tasks.
<br>
Formats:
- `add` TASK <i>on</i> DATE <i>at</i> TIME <br>
Example: `add` Go for meeting <i>on</i> Sunday <i>at</i> 3pm <br>
- `add` TASK <i>on</i> DATE <i>by</i> TIME <br>
Example: `add` Go for meeting <i>by</i> 3pm <i>on</i> 1-jan
- `add` TASK <i>from</i> DATE <br>
Example:`add` Go out with friends <i>from</i> 9am
- `add` TASK <i>on</i> DATE <i>from</i> TIME <i>to</i> TIME <br>
Example: `add` Watch webcast <i>on</i> sat <i>from</i> 4.45pm <i>to</i> 7pm
- `add` TASK <i>from</i> DATE <i>to</i> DATE <i>from</i> TIME <i>to</i> TIME [<i>#</i>TAG] [<i>p/</i>PRIORITY]  [<i>r/</i>RECURRING] <br>
Example: `add` Holiday in San Francisco at Ocean Beach by the sea <i>from</i> may <i>to</i> aug <i>from</i> 9am <i>to</i> 11pm <i>#</i>holiday <i>#</i>leisure <i>p/</i>3 <i>r/</i>monthly

Moreover, Taskell is able to make automatic adjustments to the date and time so that the task entered remains relevant. <br> 
Examples: 
- `add` Create powerpoint slides for project <i>from</i> 11pm <i>to</i> 3am <br>
This task will be added as a valid task that starts today at 11pm and ends  tomorrow at 3pm.
- `add` Staycation with friends <i>from</i> sunday <i>to</i> tues <br>
If today is a Saturday, this task will be added as a valid task that starts from tomorrow and end on this coming Tuesday. <br>
If today is a Sunday, this task will be added as a valid task that starts from next Sunday and end on the following Tuesday.



<!--- @@author -->

<!--- @@author A0148004R --->

#### Listing tasks : `list`
You can use the list command to display a certain type of tasks.<br>

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
Displays a list of tasks with given priority.
<br><p align="center"><img src="images/screenshots/list_date.PNG" width="800"></br><br>
Diagram 2: Displays all the tasks to be done by today after typing list-date today.

<!-- @@author -->
   
<!--- @@author A0142130A --->  

#### Finding tasks: `find`
You can use the find command to view tasks with specific keywords.<br> 
Formats: <br>
- `find KEYWORD [MORE_KEYWORDS]`<br>
Displays a list of tasks with description or tags that match all the keywords.<br>
Example: `find banana milk essay`<br>
This returns all tasks with description or tags that match all keywords `banana`, `milk`, and `essay`. <br>

> Tasks with words that match the keyword include those that contain the keyword. For example, searching for "book" will match with "book", "textbook", "storybook" etc.

- `find-tag TAG [MORE_TAGS]`<br>
Displays list of tasks with the same tags.<br>
Example: `find homework essay cs2103`<br>
This returns any task with either tag `homework`, `essay`, or `cs2103`.
 <br><p align="center"><img src="images/screenshots/find_cs2010_results.PNG" width="800"></br> <br>
Diagram 3: Keying in `find cs2010` displays list of tasks with "cs2010" as one of the keywords in task description.

> Take Note! <br>
> * The order of the keywords does not matter. e.g. `chicken egg` will match `egg chicken`.
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `chicken` will match `chicken duck`
    
<!--- @@author --->

<!--- @@author A0142130A ---> 

#### Showing history : `history` or `hist`
You can view the command history available for undo on the right panel to refer to when undoing previous commands.<br>

Format: `history` or `hist` 

 <p align="center"><img src="images/screenshots/history_02.PNG" width="800"></br> <br>
 Diagram 4: Keying in `hist` will show list of command history on the right panel

> Take Note! <br>
> * Only commands available for undo are shown here. 
> * Refer to undo section below for more information about what commands undo supports.

#### Reverting previous action : `undo`
If you wish to undo your most recent action, you can do so by using the undo command.<br>
To undo previous commands, <kbd>Enter</kbd> `hist` to see a list of 
previous commands that can be undone.<br>
Then use undo command with specified INDEX from the list of command history.
Formats: <br>
-`undo`<br> 
Undo most recent command executed.<br>
-`undo INDEX`<br> 
Undo by index of command history.<br>
Example: `hist`, then `undo 3`, will undo third command in command history.

 <p align="center"><img src="images/screenshots/undo_01.PNG" width="800"></br>
 <br><p align="center"><img src="images/screenshots/undo_02.PNG" width="800"></br> <br>
Diagrams 5 and 6: `undo 2` will undo 2nd command in history

> Take Note! <br>
> * Undo command only supports add, edit, delete, done, undone and undo commands.
> * Clear commands are irreversible!

<!--- @@author ---> 

#### Deleting a task : `delete`
You can use the delete command to delete a task at a specified INDEX. The index refers to the index number shown in the most recent listing.<br>
Format: `delete INDEX`<br>
Example: `find violin`, then `delete 1`<br>
This deletes the 1st task in the results of the find command.

<!--- @@author A0148004R --->

#### Marking a task as completed: `done`
You can use the done command to mark an uncompleted task as completed.
Format: `done` INDEX<br>
Example: `done` 1<br>
This marks the first task as finished and moves it to the completed list.<br>

#### Marking a task as incomplete: `undone`
You can use the undone command to mark a completed task as uncompleted.
Format: `undone` INDEX<br>
Example: `undone` 1<br>
This marks the 1st task as incomplete and moves it to the uncompleted list.<br>

<!--- @@author --->

<!--- @@author A0142073R --->

#### Editing a task : `edit`
To edit a task<br>
Formats: 
- `edit` INDEX <i>st:</i> NEWSTARTTIME <i>et:</i> NEWENDTIME <i>desc:</i> NEWDESCRIPTION <i>sd:</i> NEWSTARTDATE <i>ed:</i> NEWENDDATE <i>p:</i> NEWPRIORITY <br>
 <br><p align="center"><img src="images/editCmd.png" width="800"></br>
Diagram 9: Edits the 1st task on the list.<br>

Entering "edit 1 desc: send all emails sd: 11-11-2016 ed: 12-11-2016 st: 3pm et: 4pm p: 3", will update description to "send all emails", start date to 11-11-2016, end date to 12-11-2016, start time to 3pm end time to 4pm and priority to 3.<br>

> Take Note! <br>
> * You only need to key in the necessary parameters of the task you would like to change. Not all parameters are required.
> * Order of the parameters are not important.
    
<!--- @@author --->

<!--- @@author A0142130A ---> 

#### Showing calendar view : `calendar` or `cal`
You can view the calendar for the week on the right panel to refer to the dates and any events scheduled 
when adding tasks and scheduling events. There is also a single red line displayed to show you the current time for your ease in checking your schedule.<br>
<br><p align="center"><img src="images/screenshots/calendar_circle.PNG" width="800"></br>
Diagram 10: An example of how the current time marker looks like.<br>

Calendar view depends on the list of tasks on the left panel, with each section marked with the index corresponding to the task, meaning a block marked "4" would correspond with the 4th task.<br>
By default, the calendar view will be shown on the right panel. As `history` displays command history on the right panel as well, this command is meant for your convenience if you want to view calendar again.<br>
Please note that entering other commands (i.e. `find`, `add`) will also revert the right panel back to calendar as it is the default view. <br>
Format: `calendar` or `cal` <br>

#### Saving the information in Taskell : `save`
You can specify the path of a folder to store Taskell's data file. Please note that you should have permissions to access the folder. <br>
To obtain the filepath, navigate to the required file in your File Explorer. Copy the path at the top of the
screen and paste into Taskell. Refer to diagram 12 for an example of a filepath. <br>

<p align="center"> <img src="images/filepath_screenshot.png" width="1000"> </br>
Diagram 11: Screenshot of File Explorer in Windows.  <br>

If the specified directory is valid but the file is missing, for example if command is `save C:\Users\Jim\Documents\chicken`,  and `C:\Users\Jim\Documents\chicken` is valid
but `chicken` file is not created, Taskell will create the file for you.<br>
Format: `save FOLDERPATH`<br>
Example: `save C:\Users\Jim\Documents`

> Take Note! <br>
> * If you only specify a folder name without directory, i.e. `save cat`, Taskell will create a file
named "cat" within Taskell's own directory. Whereas `save C:/Users/Jim/Documents/cat` will open a file named "cat" within your desktop's Documents folder.
> * Both Windows and Linux OS have restricted symbols not allowed for filenames. Please be aware of the symbols
shown in the table below.
    
Windows |  Linux 
-------- | :-------- 
    * > | * <
    * < | * >
    * : | * :
    * " | * &
    * / | * /
    * \ | * |
    * | | 
    * ? |
    * * |

#### Clearing all entries : `clear`
Permanently clears **all** task data. <br>
Format: `clear`  

<p align="center"><img src="images/screenshots/clear.PNG" width="800"></br><br>
Diagram 12: Typing `clear` will prompt a confirm pop-up window <br>

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

<!--- @@author A0139257X ---> 

## Glossary

1. GUI: Graphical User Interface
2. Floating task: A task without date and time

<!--- @@author --->

<!--- @@author A0142073R ---> 
     
## Command Summary

Command | Format  
-------- | :-------- 
Add Floating Task | `add` TASK <i>p/</i>[PRIORITY] <i>#</i>[TAG]
Add Event | `add` TASK <i>by</i> DATE <br> `add` TASK <i>by</i> TIME <br> `add` TASK <i>by</i> DATE <i>by</i> TIME <br> `add` TASK <i>at</i> TIME <br> `add` TASK <i>on</i> DATE <br> `add` TASK <i>on</i> DATE <i>by</i> TIME <br> `add` Task <i>on</i> DATE <i>at</i> TIME <br> `add` TASK <i>from</i> DATE <i>to</i> DATE <br> `add` TASK <i>from</i> TIME <i>to</i> TIME <br> `add` TASK <i>on</i> DATE <i>from</i> TIME <i>to</i> TIME <i>p/</i>[PRIORITY] <i>#</i>[TAG] <i>r/</i>[RECURRING]
Calendar View | `calendar` or `cal`
Clear | `clear`
Delete | `delete` INDEX
Edit | `edit` INDEX `desc:` NEW_DESCRIPTION 
`st:` NEW_START_TIME `et:` NEW_END_TIME `ed:` NEW_END_DATE `sd:` NEW_START_DATE `p:` NEW_PRIORITY
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

<!-- @@author -->

<!--- @@author A0139257X --->
## Appendix A

Supported Date Format |   Example  
-------- | :-------- 
DD-MM-YYYY |1-3-2016 <br> 1/5/2016<br> 1-jan-2016 <br> 1-April-2016 <br> 1.Jan.2016 <br> 1.May.2016
MM-YYYY  | jul-2016 <br> july-2016
MM  | mar <br> sept <br> December
day  | today <br> tdy <br> tomorrow <br> tmr <br> thursday <br> thurs <br> thu

## Appendix B

Supported Time Format |   Example  
-------- | :-------- 
In 12-hour format | 12am <br> 5:30am<br> 1pm <br> 11.45pm <br> 10-35pm
In words | now <br> midnight <br> afternoon <br> noon

<!-- @@author -->

