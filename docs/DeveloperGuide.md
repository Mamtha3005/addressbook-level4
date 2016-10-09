# Developer Guide 

* [Introduction](#introduction)
* [Setting Up](#setting-up)
* [Design](#design)
* [Implementation](#implementation)
* [Testing](#testing)
* [Dev Ops](#dev-ops)
* [Appendix A: User Stories](#appendix-a--user-stories)
* [Appendix B: Use Cases](#appendix-b--use-cases)
* [Appendix C: Non Functional Requirements](#appendix-c--non-functional-requirements)
* [Appendix D: Glossary](#appendix-d--glossary)
* [Appendix E : Product Survey](#appendix-e-product-survey)

##Introduction

Taskell is a simple software for users to keep track of their daily tasks. This software thus would help users to manage their busy schedule. 

This guide describes the design and implementation of Taskell. This will help you understand how Taskell works so that you can join our team in assisting for the development of this software.

## Setting up

#### Prerequisites

1. **JDK `1.8.0_60`**  or later<br>

    > Having any Java 8 version is not enough. <br>
    This app will not work with earlier versions of Java 8.
    
2. **Eclipse** IDE
3. **e(fx)clipse** plugin for Eclipse (Do the steps 2 onwards given in
   [this page](http://www.eclipse.org/efxclipse/install.html#for-the-ambitious))
4. **Buildship Gradle Integration** plugin from the Eclipse Marketplace


#### Importing The Project Into Eclipse

0. Fork this repository, and clone the fork to your computer
1. Open Eclipse (Note: Ensure you have installed the **e(fx)clipse** and **buildship** plugins as given in the prerequisites above)
2. Click `File` > `Import`
3. Click `Gradle` > `Gradle Project` > `Next` > `Next`
4. Click `Browse`, then locate the project's directory
5. Click `Finish`

  > * If you are asked whether to 'keep' or 'overwrite' config files, choose to 'keep'.
  > * Depending on your connection speed and server load, it can even take up to 30 minutes for the set up to finish
      (This is because Gradle downloads library files from servers during the project set up process)
  > * If Eclipse auto-changed any settings files during the import process, you can discard those changes.

## Design

### Architecture

<img src="images/Architecture.png" width="600"><br>

The Architecture Diagram given above explains the high-level design of the Application.
Given below is a quick overview of each component.<br>

`Main` has only one class called [`MainApp`](../src/main/java/seedu/taskell/MainApp.java). It is responsible for,
* At application launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup method where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.
Two of those classes play important roles at the architecture level.
* `EventsCentre` : Used by components to communicate with other components using events (i.e. a form of _Event Driven_ design)(written using [Google's Event Bus library](https://github.com/google/guava/wiki/EventBusExplained))

* `LogsCenter` : Used by many classes to write log messages to the Application's log file.

The rest of the Application consists four components.
* [**`UI`**](#ui-component) : UI of the Application.
* [**`Logic`**](#logic-component) : Command executor.
* [**`Model`**](#model-component) : Data Holder of the Application in-memory.
* [**`Storage`**](#storage-component) : Data read from, and written to the hard disk.

Each of the four components
* Defines its _API_ in an `interface` with the same name as the Component.
* Exposes its functionality using a `{Component Name}Manager` class.

<br><img src="images/LogicClassDiagram.png" width="800"><br>

The `Logic` component above defines it's API in the `Logic.java`
interface and exposes its functionality using the `LogicManager.java` class.<br>

<img src="images\SDforDeleteTask.png" width="800">

The Sequence Diagram above shows how the components interact for the scenario where the user issues the
command `delete 1`.

>Note how the `Model` simply raises a `TaskManagerChangedEvent` when the Task Manager data is changed,
 instead of asking the `Storage` to save the updates to the hard disk.

<br><img src="images\SDforDeleteTaskEventHandling.png" width="800">

The diagram above shows how the `EventsCenter` reacts to that event, which eventually results in the updates
being saved to the hard disk. The status bar of the UI is updated to reflect the 'Last Updated' time. <br>

> Note how the event is propagated through the `EventsCenter` to the `Storage` and `UI` without `Model` having
  to be coupled to either of them. This is an example of how this Event Driven approach helps us reduce direct 
  coupling between components.

The sections below give more details of each component.

### UI Component

<img src="images/UiClassDiagram.png" width="800"><br>

The picture above gives an overview of how the `UI`component is implemented.<br>

**API** : [`Ui.java`](../src/main/java/seedu/taskell/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `TaskListPanel`,
`StatusBarFooter`, `BrowserPanel` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class
and they can be loaded using the `UiPartLoader`.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files
 that are in the `src/main/resources/view` folder.<br>
 For example, the layout of the [`MainWindow`](../src/main/java/seedu/taskell/ui/MainWindow.java) is specified in
 [`MainWindow.fxml`](../src/main/resources/view/MainWindow.fxml)

The `UI` component,
* Executes user commands using the `Logic` component.
* Binds itself to some data in the `Model` so that the UI can auto-update when data in the `Model` change.
* Responds to events raised from various parts of the Application and updates the UI accordingly.

### Logic Component

<img src="images/LogicClassDiagram.png" width="800"><br>

The picture above gives an overview of how the `Logic`component is implemented.<br>
<br>**API** : [`Logic.java`](../src/main/java/seedu/taskell/logic/Logic.java)

The `Logic` component,
* Uses the `Parser` class to parse the user command: results in a `Command` object which is executed by the `LogicManager`.
* Affects the `Model` (e.g. adding a task) and/or raise events.
* Executes the necessary command and the result is encapsulated as a  `CommandResult` to be passed back to the `Ui`.

<br><img src="images/DeleteTaskSdForLogic.png" width="800"><br>

The picture above shows the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")`
 API call.<br>
 
### Model Component

<img src="images/ModelClassDiagram.png" width="800"><br>


The picture above gives an overview of how the `Model`component is implemented.<br>
<br>**API** : [`Model.java`](../src/main/java/seedu/taskell/model/Model.java)

The `Model` component,
* stores a `UserPref` object that represents the user's preferences.
* stores the Task Manager data.
* exposes a `UnmodifiableObservableList<ReadOnlyTask>` that can be 'observed' e.g. the UI can be bound to this list
  so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.

### Storage Component

<img src="images/StorageClassDiagram.png" width="800"><br>

The picture above gives an overview of how the `Storage`component is implemented.<br>
<br>**API** : [`Storage.java`](../src/main/java/seedu/taskell/storage/Storage.java)

The `Storage` component,
* can save `UserPref` objects in json format and read it back.
* can save the Task Manager data in xml format and read it back.

### Common Classes

Classes used by multiple components are in the `seedu.taskmanager.commons` package.

## Implementation

### Logging

We are using `java.util.logging` package for logging. The `LogsCenter` class is used to manage the logging levels
and logging destinations.

* The logging level can be controlled using the `logLevel` setting in the configuration file
  (See [Configuration](#configuration))
* The `Logger` for a class can be obtained using `LogsCenter.getLogger(Class)` which will log messages according to
  the specified logging level
* Currently log messages are output through: `Console` and to a `.log` file.

**Logging Levels**

* `SEVERE` : Critical problem detected which may possibly cause the termination of the application
* `WARNING` : Program can continue, but with caution
* `INFO` : Information showing the noteworthy actions by the Application
* `FINE` : Details that is not usually noteworthy but may be useful in debugging
  e.g. print the actual list instead of just its size

### Configuration

Certain properties of the application can be controlled (e.g Application name, logging level) through the configuration file 
(default: `config.json`):


## Testing

Tests can be found in the `./src/test/java` folder.

**In Eclipse**:
> If you are not using a recent Eclipse version (i.e. _Neon_ or later), enable assertions in JUnit tests
  as described [here](http://stackoverflow.com/questions/2522897/eclipse-junit-ea-vm-option).

* To run all tests, right-click on the `src/test/java` folder and choose
  `Run as` > `JUnit Test`
* To run a subset of tests, you can right-click on a test package, test class, or a test and choose
  to run as a JUnit test.

**Using Gradle**:
* See [UsingGradle.md](UsingGradle.md) for how to run tests using Gradle.

We have two types of tests:

1. **GUI Tests** - These are _System Tests_ that test the entire Application by simulating user actions on the GUI. 
   These are in the `guitests` package.
  
2. **Non-GUI Tests** - These are tests not involving the GUI. They include,
   1. _Unit tests_ targeting the lowest level methods/classes. <br>
      e.g. `seedu.taskell.commons.UrlUtilTest`
   2. _Integration tests_ that are checking the integration of multiple code units 
     (those code units are assumed to be working).<br>
      e.g. `seedu.taskell.storage.StorageManagerTest`
   3. Hybrids of unit and integration tests. These test are checking multiple code units as well as 
      how the are connected together.<br>
      e.g. `seedu.taskell.logic.LogicManagerTest`
  
**Headless GUI Testing** :
Thanks to the [TestFX](https://github.com/TestFX/TestFX) library we use,
 our GUI tests can be run in the _headless_ mode. 
 In the headless mode, GUI tests do not show up on the screen.
 That means the developer can do other things on the Computer while the tests are running.<br>
 See [UsingGradle.md](UsingGradle.md#running-tests) to learn how to run tests in headless mode.
  
## Dev Ops

### Build Automation

See [UsingGradle.md](UsingGradle.md) to learn how to use Gradle for build automation.

### Continuous Integration

We use [Travis CI](https://travis-ci.org/) to perform _Continuous Integration_ on our projects.
See [UsingTravis.md](UsingTravis.md) for more details.

### Making a Release

Here are the steps to create a new release.
 
 1. Generate a JAR file [using Gradle](UsingGradle.md#creating-the-jar-file).
 2. Tag the repository with the version number. e.g. `v0.1`
 2. [Create a new release using GitHub](https://help.github.com/articles/creating-releases/) 
    and upload the JAR file your created.
   
### Managing Dependencies

A project often depends on third-party libraries. For example, Taskell depends on the
[Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these _dependencies_
can be automated using Gradle. For example, Gradle can download the dependencies automatically, which
is better than these alternatives.<br>
a. Include those libraries in the repository (this bloats the repository size)<br>
b. Require developers to download those libraries manually (this creates extra work for developers)<br>

## Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`


Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | new user | see usage instructions | refer to instructions when I forget how to use the Application.
`* * *` | user |  add a task and set time and/or deadlines | take note of all my tasks.
`* * *` | user | delete a task | remove task that I no longer need.
`* * *` | user | find a task by its description | locate details of tasks without having to go through the entire list.
`* * *` | user | categorize my tasks | group and view tasks of similar type or tasks of same project.
`* * *` | user | view all the tasks, sorted by day, week, month | plan my schedule.
`* * *` | user | add task which has no time limit | can do it without specific times.
`* * *` | user | edit task | make changes to the task created.
`* * *` | user | have a start and end time for a task | save the duration of the task.
`* * *` | user | set deadlines for a task | know when the task is due.
`* * *` | user | undo the last previous task I wrote | I can recover from accidents.
`* * *` | user | mark a task as done | I can focus on uncompleted tasks.
`* * *` | user | have flexible command format | I do not have to waste time trying to remember and get the command format right.
`* * *` | user | specify a folder with cloud syncing service as the storage location | I can easily access my task planner from different computers.
`* * *` | user | check whether tasks are completed or yet to be done | I can track the tasks completion.
`* *` | user | set some of my task recursively | I need not have to manually key them everyday,week,month.
`* *` | user |  delete tasks based on a certain index | I can delete a few tasks in one go instead of deleting one task at a time.
`* *` | user | hide private contact details | minimize chance of someone else seeing them by accident.
`*` | user | be able to block multiple timeslots, and release the timeslots when timing is confirmed|  I can schedule in events which have uncertain timings more efficiently.
`*` | user with many tasks in Taskell | sort tasks by priority | view the most important tasks.
`*` | user | edit my notification time period | customise if I wanted to be reminded earlier or later.
`*` | user |  use the history command | I do not need to retype it and use it faster.
`*` | user |  view the task in either calendar form or list form |  I can see the task more directly.

## Appendix B : Use Cases

(For all use cases below, the **System** is the `TaskManager` and the **Actor** is the `user`, unless specified otherwise)

#### Use case: Add task

**MSS**

1. User requests to add tasks either with or without deadline
2. Taskell adds the task<br>

> Use case ends

**Extensions**

2a. The user did not follow the given format to add a task or deadline

> 2a1. Taskell shows the help message <br>
  Use case resumes at step 1

#### Use case: Delete task

**MSS**

1. User requests to list tasks
2. Taskell shows a list of uncompleted tasks
3. User requests to delete a specific task in the list
4. Taskell deletes the task <br>

> Use case ends

**Extensions**

2a. The list is empty

3a. The given index is invalid

> 3a1. Taskell shows an error message <br>
  Use case resumes at step 2

#### Use case: Help task

**MSS**

1. User requests to view the different command
2. User enters "help"
3. User displays a summary of all the different command.

> Use case ends

**Extensions**

2a. The user types "help" incorrectly

> 3a1. Taskell stil displays the help message <br>

#### Use case: Find task

**MSS**

1. User requests to list tasks
2. Taskell shows a list of uncompleted tasks
3. User requests to find tasks with specific keywords
4. Taskell displays the relevant task <br>

> Use case ends

**Extensions**

2a. The list is empty

3a. The given index is invalid

> 3a1. Taskell shows an error message <br>
  Use case resumes at step 2

#### Use case: Edit task

**MSS**

1. User requests to list tasks
2. Taskell shows a list of tasks
3. User requests to edit the task in the list
4. AddressBook edits the person's task, including its' deadline <br>

> Use case ends

**Extensions**

2a. The list is empty

3a. The given index is invalid
3b. The user did not key in the new task

> 3a1 and 3b1. Taskell shows an error message <br>
  Use case resumes at step 2

#### Use case: Undo task

**MSS**

1. User enters a wrong command
2. Taskell executed it
3. User requests to undo the wrong command
4. Taskell undo the previous command <br>

> Use case ends

**Extensions**

2a. The user did not enter any previous command

> 2a1. Taskell shows an error message <br>

#### Use case: List task

**MSS**

1. User requests to list tasks
2. Taskell shows a list of tasks

> Use case ends

**Extensions**

2a. The list is empty

> 2a1. Taskell shows an error message <br>
  Use case resumes at step 2
  
#### Use case: Store data in cloud syncing folder

**MSS**

1. User requests to save all tasks
2. Taskell saves all tasks in the requested folder

> Use case ends

**Extensions**

2a. The requested folder does not exist

> 2a1. Taskell shows an error message <br>

#### Use case: Clear task

**MSS**

1. User requests to clear all tasks
2. Taskell deletes all tasks

> Use case ends

**Extensions**

2a. The list is empty

> 2a1. Taskell shows an error message <br>

#### Use case: Exit task

**MSS**

1. User requests to exit Taskell
2. Taskell saves all the data and stops

> Use case ends

**Extensions**

NIL

## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to 1000 persons.
3. Should come with automated unit tests and open source code.
4. Should favor DOS style commands over Unix-style commands.
5. Each command executed under 5 seconds.

## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

##### Floating Tasks

> Tasks with no deadline

##### Private contact detail

> A contact detail that is not meant to be shared with others

## Appendix E : Product Survey
#### WunderList
**Pros:**<br>
1. Support for cross-platform operations<br>
2. Use tags for any tasks<br>
3. Share tasks with other people and manage the shared tasks<br> 
4. Categorize tasks into different categories<br>
5. Attach different types of file inside the task, such as photos, PDF and PowerPoint<br>
6. Use of short form command<br>

**Cons:**<br>
1. Only have two priority levels<br>

#### Remember the Milk
**Pros:**<br>
1. Use offline and be synced once internet connection is established<br>
2. Handle some natural language processing by saving deadlines from task information itself (e.g. Do math homework by tomorrow: Saves task with deadline set to date of tomorrow)<br>
3. Undo operation when marking tasks as done (recover from accidentally clicking done)<br>
4. Set priority with ordering<br>
5. Set recursive tasks<br>

**Cons:**<br>
1. Need to click frequently to enter a task (if using desktop, not application) <br>
2. Need to remember lots of shortcuts to remember, so user is less likely to use them<br>
3. Display of the interface is cluttered, not intuitive<br>
4. Does not support calendar view in-house, but there are plugins that display calendar views<br>

#### Google Calendar
**Pros:**<br>
1. Able to add in public holidays <br>
2. Can update in terms of time zone<br>
3. Can customize background picture<br>

**Cons:**<br>
1. Flexibility in the way to view the calendar is limited<br>
2. Information is too much in 1 page in application<br>

#### Any.do
**Pros:**<br>
1. Displays a reminder when the task is almost due (set in advance)<br>
2. Able to clear all tasks in one go<br>
3. Do a daily review at the start/end of day<br>
4. Arrange tasks by Today, Tomorrow, someday<br>


**Cons:**<br>
1. Require internet connection<br>