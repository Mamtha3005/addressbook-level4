package seedu.taskell.model.task;

import seedu.taskell.commons.exceptions.IllegalValueException;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

//@@author A0139257X
public class TaskDateTest {
    
    @Test
    public void assertValidFormatBehaviourForDate() {
        //Valid Day of the Week
        assertTrue(TaskDate.isValidDate("mon"));
        assertTrue(TaskDate.isValidDate("tue"));
        assertTrue(TaskDate.isValidDate("WED"));
        assertTrue(TaskDate.isValidDate("thurs"));
        assertTrue(TaskDate.isValidDate("fRi"));
        assertTrue(TaskDate.isValidDate("saturday"));
        assertTrue(TaskDate.isValidDate("sun"));
        
        //Valid Month
        assertTrue(TaskDate.isValidDate("jan"));
        assertTrue(TaskDate.isValidDate("fEb"));
        assertTrue(TaskDate.isValidDate("march"));
        assertTrue(TaskDate.isValidDate("APRIL"));
        assertTrue(TaskDate.isValidDate("mAy"));
        assertTrue(TaskDate.isValidDate("junE"));
        assertTrue(TaskDate.isValidDate("jul"));
        assertTrue(TaskDate.isValidDate("aug"));
        assertTrue(TaskDate.isValidDate("sept"));
        assertTrue(TaskDate.isValidDate("oct"));
        assertTrue(TaskDate.isValidDate("November"));
        assertTrue(TaskDate.isValidDate("December"));
        
        //Valid Month and Year
        assertTrue(TaskDate.isValidDate("may 2016"));
        assertTrue(TaskDate.isValidDate("may-2016"));
        assertTrue(TaskDate.isValidDate("may.2016"));
        assertTrue(TaskDate.isValidDate("may/2016"));
        
        //Valid Day and Month
        assertTrue(TaskDate.isValidDate("1 jan"));
        assertTrue(TaskDate.isValidDate("1-jan"));
        assertTrue(TaskDate.isValidDate("1.jan"));
        assertTrue(TaskDate.isValidDate("1/jan"));
        
        //Valid full Date
        assertTrue(TaskDate.isValidDate(TaskDate.DEFAULT_DATE));
        assertTrue(TaskDate.isValidDate("1 1 2016"));
        assertTrue(TaskDate.isValidDate("1 jan 2016"));
        assertTrue(TaskDate.isValidDate("1-1-2016"));
        assertTrue(TaskDate.isValidDate("1-jan-2016"));
        assertTrue(TaskDate.isValidDate("1.1.2016"));
        assertTrue(TaskDate.isValidDate("8.DeCeMbEr.2016"));
        assertTrue(TaskDate.isValidDate("8/8/2016"));
        assertTrue(TaskDate.isValidDate("8/jan/2016"));
        assertTrue(TaskDate.isValidDate("1-1/2016"));
        assertTrue(TaskDate.isValidDate("1-jan/2016"));
        assertTrue(TaskDate.isValidDate("1-1.2016"));
        assertTrue(TaskDate.isValidDate("1-jan.2016"));
        assertTrue(TaskDate.isValidDate("1/1-2016"));
        assertTrue(TaskDate.isValidDate("1/jan-2016"));
        assertTrue(TaskDate.isValidDate("1/1.2016"));
        assertTrue(TaskDate.isValidDate("1/jan.2016"));
        assertTrue(TaskDate.isValidDate("1.1/2016"));
        assertTrue(TaskDate.isValidDate("1.jan/2016"));
        assertTrue(TaskDate.isValidDate("1.1-2016"));
        assertTrue(TaskDate.isValidDate("1.jan-2016"));
        
        //Valid Today
        assertTrue(TaskDate.isValidDate("Today"));
        assertTrue(TaskDate.isValidDate("tdy"));
        
        //Valid Tomorrow
        assertTrue(TaskDate.isValidDate("Tomorrow"));
        assertTrue(TaskDate.isValidDate("tmr"));
        
    }
    
    @Test
    public void assertInvalidFormatBehaviourForDate() {
        assertFalse(TaskDate.isValidDate(""));
        assertFalse(TaskDate.isValidDate(null));
        assertFalse(TaskDate.isValidDate("1st January"));
        assertFalse(TaskDate.isValidDate("1/2"));
        assertFalse(TaskDate.isValidDate("01022016"));
        assertFalse(TaskDate.isValidDate("2016"));
        assertFalse(TaskDate.isValidDate("NotAValidDate"));
    }
    
    @Test
    public void assertNewTaskDateBehaviour() throws IllegalValueException {
        TaskDate today = TaskDate.getTodayDate();
        TaskDate validDayOfWeek = new TaskDate(today.getDayNameInWeek());
        assertEquals(today.getNextWeek(), validDayOfWeek);
        
        TaskDate validMonth = new TaskDate("september");
        assertEquals("1-9-2016", validMonth.toString());
        
        TaskDate validMonthAndYear = new TaskDate("dec-2016");
        assertEquals("1-12-2016", validMonthAndYear.toString());
        
        TaskDate validDayAndMonth = new TaskDate("1-jan");
        assertEquals("1-1-2016", validDayAndMonth.toString());
        
        TaskDate validFullDate = new TaskDate("1-1-2011");
        assertEquals("1-1-2011", validFullDate.toString());
        
        TaskDate validToday = new TaskDate("today");
        DateTimeFormatter standardFormat = DateTimeFormatter.ofPattern("d-MM-yyyy");
        assertEquals(LocalDate.now().format(standardFormat), validToday.toString());
        
        TaskDate validTomorrow = new TaskDate("tmr");
        standardFormat = DateTimeFormatter.ofPattern("d-MM-yyyy");
        assertEquals(LocalDate.now().plusDays(1).format(standardFormat), validTomorrow.toString());
        
        try {
            TaskDate invalidDate = new TaskDate("NOT-A-VALID-DATE");
        } catch (IllegalValueException ive) {
            assertEquals(TaskDate.MESSAGE_TASK_DATE_CONSTRAINTS, ive.getMessage());
        }
    }
    
    @Test
    public void assertCorrectTodayDate() {
        DateTimeFormatter standardFormat = DateTimeFormatter.ofPattern("d-MM-yyyy");
        assertEquals(LocalDate.now().format(standardFormat), TaskDate.getTodayDate().toString());
    }
    
    @Test
    public void assertCorrectTomorrowDate() {
        DateTimeFormatter standardFormat = DateTimeFormatter.ofPattern("d-MM-yyyy");
        assertEquals(LocalDate.now().plusDays(1).format(standardFormat), TaskDate.getTomorrowDate().toString());
    }
    
    @Test
    public void assertCorrectThisYear() {
        assertEquals(LocalDate.now().getYear() + "", TaskDate.getThisYear());
    }
    
    @Test
    public void assertCorrectGetNextDay() throws IllegalValueException {
        TaskDate today = new TaskDate("1-1-2016");
        TaskDate nextDay = new TaskDate("2-1-2016");
        assertEquals(nextDay, today.getNextDay());
    }
    
    @Test
    public void assertCorrectGetNextWeek() throws IllegalValueException {
        TaskDate today = new TaskDate("1-1-2016");
        TaskDate nextWeek = new TaskDate("8-1-2016");
        assertEquals(nextWeek, today.getNextWeek());
    }
    
    @Test
    public void assertCorrectLocalDate() throws IllegalValueException {
        TaskDate date = new TaskDate("1-1-2100");
        LocalDate actual = date.getLocalDate();
        LocalDate expected = LocalDate.of(2100, 1, 1);
        assertEquals(expected, actual);
    }
    
    @Test
    public void assertDateisBeforeBehaviour() throws IllegalValueException {
            TaskDate startDate = new TaskDate("1-1-2100");
            TaskDate endDateDiffDaySameMonthSameYear = new TaskDate("10-1-2100");
            TaskDate endDateSameDayDiffMonthSameYear = new TaskDate("1-2-2100");
            TaskDate endDateSameDaySameMonthDiffYear = new TaskDate("1-1-2200");
            
            assertTrue(startDate.isBefore(endDateDiffDaySameMonthSameYear));
            assertTrue(startDate.isBefore(endDateSameDayDiffMonthSameYear));
            assertTrue(startDate.isBefore(endDateSameDaySameMonthDiffYear));
            
            assertFalse(endDateDiffDaySameMonthSameYear.isBefore(startDate));
            assertFalse(endDateSameDayDiffMonthSameYear.isBefore(startDate));
            assertFalse(endDateSameDaySameMonthDiffYear.isBefore(startDate));
    }
    
    @Test
    public void assertDateisAfterBehaviour() throws IllegalValueException {
            TaskDate startDate = new TaskDate("1-1-2100");
            TaskDate endDateDiffDaySameMonthSameYear = new TaskDate("10-1-2100");
            TaskDate endDateSameDayDiffMonthSameYear = new TaskDate("1-2-2100");
            TaskDate endDateSameDaySameMonthDiffYear = new TaskDate("1-1-2200");
            
            assertTrue(endDateDiffDaySameMonthSameYear.isAfter(startDate));
            assertTrue(endDateSameDayDiffMonthSameYear.isAfter(startDate));
            assertTrue(endDateSameDaySameMonthDiffYear.isAfter(startDate));
            
            assertFalse(startDate.isAfter(endDateDiffDaySameMonthSameYear));
            assertFalse(startDate.isAfter(endDateSameDayDiffMonthSameYear));
            assertFalse(startDate.isAfter(endDateSameDaySameMonthDiffYear));
    }
    
    @Test
    public void assertNumberOfDays() throws IllegalValueException {
        TaskDate first = new TaskDate("1-11-2016");
        TaskDate second = new TaskDate("20-11-2016");
        
        //Positive difference between given dates
        long positiveDayDifference = TaskDate.between(first, second);
        assertEquals(19, positiveDayDifference);
        
        //Negative difference between given dates
        long negativeDayDifference = TaskDate.between(second, first);
        assertEquals(-19, negativeDayDifference);
    }
    
    @Test
    public void assertCorrectDisplayDate() throws IllegalValueException {
        TaskDate date = new TaskDate("22-10-2016");
        assertEquals("Saturday, 22 October 2016", date.getDisplayDate());
    }
    
    @Test
    public void assertCorrectLocalDateTime() throws IllegalValueException {
        TaskTime givenTime = new TaskTime("3am");
        TaskDate date = new TaskDate("1-1-2100");

        LocalDateTime actual = date.toLocalDateTime(givenTime);
        LocalDateTime expected = LocalDateTime.of(2100, 1, 1, 3, 0);
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void assertCorrectToString() throws IllegalValueException {
        TaskDate date = new TaskDate("1-1-2015");
        assertEquals("1-1-2015", date.toString());
    }
    
    @Test
    public void assertEqualsBehaviour() throws IllegalValueException {
        TaskDate date = new TaskDate("1-1-2015");
        TaskDate sameDate = new TaskDate("1-1-2015");
        TaskDate differentDate = new TaskDate("2-2-2016");
        
        assertEquals(date, date);
        assertEquals(date, sameDate);
        
        assertNotSame(date, differentDate);
        assertNotSame(date, "1-1-2015");
        assertNotSame(date, "NOT A DATE");
        assertNotSame(date, null);
    }
}
