/** @@author A0142130A **/
package seedu.taskell.logic.commands;

import seedu.taskell.commons.core.EventsCenter;
import seedu.taskell.commons.events.ui.DisplayCalendarViewEvent;

/** Shows calendar view on right panel
 * */
public class ViewCalendarCommand extends Command {
    
    public static final String COMMAND_WORD_1 = "calendar";
    public static final String COMMAND_WORD_2 = "cal";
    
    public static final String MESSAGE_SUCCESS = "Displayed calendar.";

    @Override
    public CommandResult execute() {
        indicateDisplayCalendarView();
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
    
    private void indicateDisplayCalendarView() {
        EventsCenter.getInstance().post(new DisplayCalendarViewEvent());
    }

}
