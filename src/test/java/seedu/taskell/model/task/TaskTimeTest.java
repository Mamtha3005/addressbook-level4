package seedu.taskell.model.task;

import seedu.taskell.commons.exceptions.IllegalValueException;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

//@@author A0139257X
public class TaskTimeTest {
    @Test
    public void assertValidFormatBehaviourForTime() {
        assertTrue(TaskTime.isValidTime(TaskTime.DEFAULT_START_TIME));
        assertTrue(TaskTime.isValidTime(TaskTime.DEFAULT_END_TIME));
        assertTrue(TaskTime.isValidTime("12am"));
        assertTrue(TaskTime.isValidTime("1.30pm"));
        assertTrue(TaskTime.isValidTime("1:40pm"));
        assertTrue(TaskTime.isValidTime("1-30am"));
        assertTrue(TaskTime.isValidTime("2:30Am"));
        assertTrue(TaskTime.isValidTime("noW"));
        
        //Valid Noon
        assertTrue(TaskTime.isValidTime("noon"));
        assertTrue(TaskTime.isValidTime("afterNoon"));
        assertTrue(TaskTime.isValidTime("12noon"));
        assertTrue(TaskTime.isValidTime("12-noon"));
        
        //Valid Midnight
        assertTrue(TaskTime.isValidTime("midnight"));
        assertTrue(TaskTime.isValidTime("Mid-Night"));
        assertTrue(TaskTime.isValidTime("12MidnIght"));
        assertTrue(TaskTime.isValidTime("12-miDnight"));
        assertTrue(TaskTime.isValidTime("12mid-night"));
        assertTrue(TaskTime.isValidTime("12-mid-nighT"));
    }

    @Test
    public void assertInvalidFormatBehaviourForTime() {
        assertFalse(TaskTime.isValidTime("1.3am"));
        assertFalse(TaskTime.isValidTime("2"));
        assertFalse(TaskTime.isValidTime("13pm"));
        assertFalse(TaskTime.isValidTime("2359"));
        assertFalse(TaskTime.isValidTime("NotAValidTime"));
        assertFalse(TaskTime.isValidTime(""));
    }
    
    @Test
    public void assertValidNewTaskTimeBehaviour() {
        try {
            TaskTime time = new TaskTime("now");
            TaskTime expected = new TaskTime(TaskTime.getTimeNow().toString());
            assertEquals(expected, time);
            
            time = new TaskTime("12Noon");
            expected = new TaskTime(TaskTime.NOON);
            assertEquals(expected, time);
            
            time = new TaskTime("midNiGht");
            expected = new TaskTime(TaskTime.MIDNIGHT);
            assertEquals(expected, time);
        } catch (IllegalValueException ive) {
            assert false;
        }
    }
    
    @Test
    public void assertInvalidNewTaskTimeBehaviour() {
        try {
            TaskTime time = new TaskTime("NOT A VALID TIME");
        } catch (IllegalValueException ive) {
            assertEquals(TaskTime.MESSAGE_TASK_TIME_CONSTRAINTS, ive.getMessage());
        }
    }
    
    @Test
    public void assertTimeIsBeforeBehaviour() throws IllegalValueException {
            TaskTime timeIs12Am = new TaskTime("12am");
            TaskTime timeIs12Pm = new TaskTime("12pm");
            TaskTime timeNot12Am = new TaskTime("3am");
            TaskTime timeNot12Pm = new TaskTime("3pm");
            TaskTime time = new TaskTime(TaskTime.DEFAULT_END_TIME);
            
            //Correct Behaviour
            assertTrue(timeIs12Am.isBefore(timeIs12Pm));
            assertTrue(timeIs12Am.isBefore(timeNot12Am));
            assertTrue(timeNot12Am.isBefore(timeIs12Pm));
            assertTrue(timeNot12Pm.isBefore(time));
            assertTrue(timeIs12Am.isBefore(time));
            
            //Incorrect Behaviour
            assertFalse(timeIs12Am.isBefore(timeIs12Am));
            assertFalse(timeIs12Pm.isBefore(timeIs12Am));
            assertFalse(timeIs12Pm.isBefore(timeNot12Am));
    }
    
    @Test
    public void assertTimeIsAfterBehaviour() throws IllegalValueException {
        TaskTime timeIs12Am = new TaskTime("12am");
        TaskTime timeIs12Pm = new TaskTime("12pm");
        TaskTime timeNot12Am = new TaskTime("3am");
        TaskTime timeNot12Pm = new TaskTime("3pm");
        TaskTime time = new TaskTime(TaskTime.DEFAULT_END_TIME);
        
        //Correct Behaviour
        assertTrue(timeIs12Pm.isAfter(timeIs12Am));
        assertTrue(timeIs12Pm.isAfter(timeNot12Am));
        
        //Incorrect Behaviour
        assertFalse(timeIs12Am.isAfter(timeIs12Am));
        assertFalse(timeIs12Am.isAfter(timeIs12Pm));
        assertFalse(timeIs12Am.isAfter(timeNot12Am));
        assertFalse(timeNot12Am.isAfter(timeIs12Pm));
        assertFalse(timeNot12Pm.isAfter(time));
    }
    
    @Test
    public void assertCorrectExtractionOfTime() throws IllegalValueException {
        TaskTime validTime = new TaskTime("4.35pm");
        
        assertEquals("4", validTime.getHour());
        assertEquals("35", validTime.getMinute());
        assertEquals("PM", validTime.getAntePost());
    }
    
    @Test
    public void assertCorrectCurrentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("h:mma");
        LocalTime currTime = LocalTime.now();
        assertEquals(LocalTime.of(currTime.getHour(), currTime.getMinute()).format(dtf), TaskTime.getTimeNow().toString());
    }
    
    @Test
    public void assertCorrectLocalTime() throws IllegalValueException {
        //Valid hour in the morning
        TaskTime time = new TaskTime("3am");
        LocalTime actual = time.getLocalTime();
        LocalTime expected = LocalTime.of(3, 0);
        assertEquals(expected, actual);
        
        //Valid time in the morning
        time = new TaskTime("5.23am");
        actual = time.getLocalTime();
        expected = LocalTime.of(5, 23);
        assertEquals(expected, actual);
        
        //Valid hour in the afternoon
        time = new TaskTime("3pm");
        actual = time.getLocalTime();
        expected = LocalTime.of(15, 0);
        assertEquals(expected, actual);
        
        //Valid time in the afternoon
        time = new TaskTime("5.23pm");
        actual = time.getLocalTime();
        expected = LocalTime.of(17, 23);
        assertEquals(expected, actual);
    }
    
    @Test 
    public void assertCorrectLocalDateTime() throws IllegalValueException {
        TaskDate givenDate = new TaskDate("1-1-2100");
        TaskTime time = new TaskTime("3am");

        LocalDateTime actual = time.toLocalDateTime(givenDate);
        LocalDateTime expected = LocalDateTime.of(2100, 1, 1, 3, 0);
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void assertCorrectToString() throws IllegalValueException {
        TaskTime time = new TaskTime("5pm");
        assertEquals("5:00PM", time.toString());
    }
    
    @Test
    public void assertEqualsBehaviour() throws IllegalValueException {
        TaskTime time = new TaskTime("12.30am");
        TaskTime sameTime= new TaskTime("12.30am");
        TaskTime differentTime= new TaskTime("3pm");
        
        assertEquals(time, time);
        assertEquals(time, sameTime);
        
        assertNotSame(time, differentTime);
        assertNotSame(time, "3am");
        assertNotSame(time, "NOT A TIME");
        assertNotSame(time, null);
    }

}
