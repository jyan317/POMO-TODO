package model;

import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.*;

// Note: Any library in JDK 8 may be used to implement this class, however,
//     you must not use any third-party library in your implementation
// Hint: Explore https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html

// Represents the due date of a Task
public class DueDate {

    private Calendar calInstance;

    // MODIFIES: this
    // EFFECTS: creates a DueDate with deadline at end of day today (i.e., today at 11:59 PM)
    public DueDate() {
        calInstance = Calendar.getInstance();
        calInstance.set(HOUR_OF_DAY, 23);
        calInstance.set(MINUTE, 59);
        calInstance.set(SECOND, 0);
        calInstance.set(MILLISECOND, 0);
    }

    // REQUIRES: date != null
    // MODIFIES: this
    // EFFECTS: creates a DueDate with deadline of the given date
    public DueDate(Date date) {
        calInstance = Calendar.getInstance();
        calInstance.setTime(date);
    }

    // REQUIRES: date != null
    // MODIFIES: this
    // EFFECTS: changes the due date to the given date
    public void setDueDate(Date date) {
        calInstance.setTime(date);
    }

    // REQUIRES: 0 <= hh <= 23 && 0 <= mm <= 59
    // MODIFIES: this
    // EFFECTS: changes the due time to hh:mm leaving the month, day and year the same
    public void setDueTime(int hh, int mm) {
        calInstance.set(HOUR_OF_DAY, hh);
        calInstance.set(MINUTE, mm);
        calInstance.set(SECOND, 0);
        calInstance.set(MILLISECOND, 0);
    }

    // MODIFIES: this
    // EFFECTS: postpones the due date by one day (leaving the time the same as
    //     in the original due date) based on the rules of the Gregorian calendar.
    public void postponeOneDay() {
        calInstance.set(DAY_OF_YEAR, calInstance.get(DAY_OF_YEAR) + 1);
    }

    // MODIFIES: this
    // EFFECTS: postpones the due date by 7 days
    //     (leaving the time the same as in the original due date)
    //     based on the rules of the Gregorian calendar.
    public void postponeOneWeek() {
        calInstance.set(DAY_OF_YEAR, calInstance.get(DAY_OF_YEAR) + 7);
    }

    // EFFECTS: returns the due date
    public Date getDate() {
        return calInstance.getTime();
    }

    // EFFECTS: returns true if due date (and due time) is passed
    public boolean isOverdue() {
        Calendar now = Calendar.getInstance();
        return calInstance.before(now);
    }

    // EFFECTS: returns true if due date is at any time today, and false otherwise
    public boolean isDueToday() {
        Calendar now = Calendar.getInstance();
        int todayDay = now.get(DAY_OF_YEAR);
        int todayYear = now.get(YEAR);
        int dueDay = calInstance.get(DAY_OF_YEAR);
        int dueYear = calInstance.get(YEAR);
        return (todayYear == dueYear) && (todayDay == dueDay);
    }

    // EFFECTS: returns true if due date is at any time tomorrow, and false otherwise
    public boolean isDueTomorrow() {
        Calendar now = Calendar.getInstance();
        int tomorrowDay = now.get(DAY_OF_YEAR) + 1;
        int thisYear = now.get(YEAR);
        int dueDay = calInstance.get(DAY_OF_YEAR);
        int dueYear = calInstance.get(YEAR);
        return (thisYear == dueYear) && (tomorrowDay == dueDay);
    }

    // EFFECTS: returns true if due date is within the next seven days, irrespective of time of the day,
    // and false otherwise
    // Example, assume it is 8:00 AM on a Monday
    // then any task with due date between 00:00 AM today (Monday) and 11:59PM the following Sunday is due within a week
    public boolean isDueWithinAWeek() {
        Calendar now = Calendar.getInstance();
        int weekDay = now.get(DAY_OF_YEAR) + 6;
        int thisYear = now.get(YEAR);
        int dueDay = calInstance.get(DAY_OF_YEAR);
        int dueYear = calInstance.get(YEAR);
        return (thisYear == dueYear) && (0 <= (weekDay - dueDay)) && ((weekDay - dueDay) < 7);
    }

    // EFFECTS: returns a string representation of due date in the following format
    //     day-of-week month day year hour:minute
    //  example: Sun Jan 25 2019 10:30 AM
    @Override
    public String toString() {
        return (dayOfWeekDisplay(calInstance) + " " + monthDisplay(calInstance) + " "
                + calInstance.get(DAY_OF_MONTH) + " " + calInstance.get(YEAR) + " "
                + hourDisplay(calInstance) + ":" + minuteDisplay(calInstance) + " "
                + amPmDisplay(calInstance));
    }


    // EFFECTS: returns short string representation of days of the week
    private String dayOfWeekDisplay(Calendar c) {
        String day;
        if (c.get(DAY_OF_WEEK) == SUNDAY) {
            day = "Sun";
        } else if (c.get(DAY_OF_WEEK) == MONDAY) {
            day = "Mon";
        } else if (c.get(DAY_OF_WEEK) == TUESDAY) {
            day = "Tues";
        } else if (c.get(DAY_OF_WEEK) == WEDNESDAY) {
            day = "Wed";
        } else if (c.get(DAY_OF_WEEK) == THURSDAY) {
            day = "Thurs";
        } else if (c.get(DAY_OF_WEEK) == FRIDAY) {
            day = "Fri";
        } else {
            day = "Sat";
        }
        return day;
    }

    // EFFECTS: returns short string representation of months of the year
    private String monthDisplay(Calendar c) {
        String month;
        if (c.get(MONTH) == JANUARY) {
            month = "Jan";
        } else if (c.get(MONTH) == FEBRUARY) {
            month = "Feb";
        } else if (c.get(MONTH) == MARCH) {
            month = "Mar";
        } else if (c.get(MONTH) == APRIL) {
            month = "Apr";
        } else if (c.get(MONTH) == MAY) {
            month = "May";
        } else if (c.get(MONTH) == JUNE) {
            month = "June";
        } else {
            month = monthDisplayPartTwo(c);
        }
        return month;
    }

    // EFFECTS: returns short string representation of months July to Dec
    private String monthDisplayPartTwo(Calendar c) {
        String month2;
        if (c.get(MONTH) == JULY) {
            month2 = "July";
        } else if (c.get(MONTH) == AUGUST) {
            month2 = "Aug";
        } else if (c.get(MONTH) == SEPTEMBER) {
            month2 = "Sept";
        } else if (c.get(MONTH) == OCTOBER) {
            month2 = "Oct";
        } else if (c.get(MONTH) == NOVEMBER) {
            month2 = "Nov";
        } else {
            month2 = "Dec";
        }
        return month2;
    }

    // EFFECTS: returns string representation of hour in 12 hour format
    private String hourDisplay(Calendar c) {
        String stringHour = Integer.toString(c.get(HOUR_OF_DAY));
        if (c.get(HOUR_OF_DAY) == 0) {
            stringHour = "12";
        } else if (c.get(HOUR_OF_DAY) > 12) {
            stringHour = Integer.toString(c.get(HOUR_OF_DAY) - 12);
        }
        return stringHour;
    }

    // EFFECTS: returns string representation of minutes with leading 0 if single digit
    private String minuteDisplay(Calendar c) {
        String stringMin = Integer.toString(c.get(MINUTE));
        if (c.get(MINUTE) < 10) {
            stringMin = "0" + stringMin;
        }
        return stringMin;
    }

    // EFFECTS: return AM or PM as appropriate for 12 hour time format
    private String amPmDisplay(Calendar c) {
        if (c.get(HOUR_OF_DAY) < 12) {
            return "AM";
        } else {
            return "PM";
        }
    }
}