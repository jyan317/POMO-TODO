package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.YEAR;
import static org.junit.jupiter.api.Assertions.*;

public class TestDueDate {

    private DueDate testDueDate;
    private Date today;
    private Date Jan1st70GMT;
    private Date today2359;
    private Date yesterday2359;
    private Date todayIn2Years;
    private Date yesterdayIn2Years;
    private long mSecInDay;


    @BeforeEach
    public void setUp() {
        today = new Date();
        Jan1st70GMT = new Date(0);

        mSecInDay = (long) 8.64e7;
        long mSecTo2359 = (long) 8.634e7;
        long adjustPST = (long) 2.88e7;
        long mSecNow = today.getTime() - adjustPST;
        long numFullDays = mSecNow / mSecInDay;
        long mSecToday2359 = (mSecInDay * numFullDays) + mSecTo2359 + adjustPST;
        today2359 = new Date(mSecToday2359);

        DueDate today2359 = new DueDate();
        long mSecYesterday = today2359.getDate().getTime() - mSecInDay;
        yesterday2359 = new Date(mSecYesterday);

        Calendar rightNow = Calendar.getInstance();
        rightNow.set(YEAR, YEAR + 2);
        todayIn2Years = rightNow.getTime();
        long mSecYesterdayIn2Years = todayIn2Years.getTime() - mSecInDay;
        yesterdayIn2Years = new Date(mSecYesterdayIn2Years);
    }

    @Test
    public void testConstructorToday() {
        testDueDate = new DueDate();
        assertEquals(today2359, testDueDate.getDate());
    }

    @Test
    public void testConstructorSetDateToday() {
        testDueDate = new DueDate(today);
        assertEquals(today, testDueDate.getDate());
    }

    @Test
    public void testConstructorSetDateJan1st70() {
        testDueDate = new DueDate(Jan1st70GMT);
        assertEquals(Jan1st70GMT, testDueDate.getDate());
    }

    @Test
    public void testSetDueDate() {
        testDueDate = new DueDate(Jan1st70GMT);
        assertEquals(Jan1st70GMT, testDueDate.getDate());
        testDueDate.setDueDate(today);
        assertEquals(today, testDueDate.getDate());
    }

    @Test
    public void testSetDueTime() {
        testDueDate = new DueDate();
        testDueDate.setDueTime(12, 0);
        long mSecTo1200 = (long) 4.32e7;
        long PSTadjust = (long) 2.88e7;
        long mSecNow = today.getTime() - PSTadjust;
        long numFullDays = mSecNow / mSecInDay;
        long mSecToday1200 = (mSecInDay * numFullDays) + mSecTo1200 + PSTadjust;
        long expectedDueTime = testDueDate.getDate().getTime();
        assertEquals(mSecToday1200, expectedDueTime);
    }

    @Test
    public void testPostponeOneDay() {
        testDueDate = new DueDate();
        long mSecDueDate = testDueDate.getDate().getTime();
        testDueDate.postponeOneDay();
        long mSecNewDueDate = mSecDueDate + mSecInDay;
        long mSecExpNewDueDate = testDueDate.getDate().getTime();
        Date calcNewDueDate = new Date(mSecNewDueDate);
        Date expNewDueDate = new Date(mSecExpNewDueDate);
        assertEquals(calcNewDueDate, expNewDueDate);
    }

    @Test
    public void testPostponeOneWeek() {
        testDueDate = new DueDate();
        long mSecDueDate = testDueDate.getDate().getTime();
        testDueDate.postponeOneWeek();
        long mSecNewDueDate = mSecDueDate + (7 * mSecInDay);
        long mSecExpNewDueDate = testDueDate.getDate().getTime();
        Date calcNewDueDate = new Date(mSecNewDueDate);
        Date expNewDueDate = new Date(mSecExpNewDueDate);
        assertEquals(calcNewDueDate, expNewDueDate);
    }

    @Test
    public void testIsOverdueDatePassedTimePassed() {
        testDueDate = new DueDate(Jan1st70GMT);
        assertTrue(testDueDate.isOverdue());
    }

    @Test
    public void testIsOverdueDatePassedTimeValid() {
        testDueDate = new DueDate(yesterday2359);
        assertTrue(testDueDate.isOverdue());
    }

    @Test
    public void testIsOverdueDateValidTimePassed() {
        testDueDate = new DueDate();
        testDueDate.postponeOneDay();
        testDueDate.setDueTime(0, 1);
        assertFalse(testDueDate.isOverdue());
    }

    @Test
    public void testIsOverdueDateValidTimeValid() {
        testDueDate = new DueDate();
        testDueDate.postponeOneDay();
        assertFalse(testDueDate.isOverdue());
    }

    @Test
    public void testIsDueTodayTodayThisYear() {
        testDueDate = new DueDate();
        assertTrue(testDueDate.isDueToday());
    }

    @Test
    public void testIsDueTodayTomorrowThisYear() {
        testDueDate = new DueDate();
        testDueDate.postponeOneDay();
        assertFalse(testDueDate.isDueToday());
    }

    @Test
    public void testIsDueTodayYesterdayThisYear() {
        testDueDate = new DueDate(yesterday2359);
        assertFalse(testDueDate.isDueToday());
    }

    @Test
    public void testIsDueTodayTodayNotThisYear() {
        testDueDate = new DueDate(todayIn2Years);
        assertFalse(testDueDate.isDueToday());
    }

    @Test
    public void testIsDueTodayTomorrowNotThisYear() {
        testDueDate = new DueDate(todayIn2Years);
        testDueDate.postponeOneDay();
        assertFalse(testDueDate.isDueToday());
    }

    @Test
    public void testIsDueTodayYesterdayNotThisYear() {
        testDueDate = new DueDate(yesterdayIn2Years);
        assertFalse(testDueDate.isDueToday());
    }

    @Test
    public void testIsDueTomorrowTodayThisYear() {
        testDueDate = new DueDate();
        assertFalse(testDueDate.isDueTomorrow());
    }

    @Test
    public void testIsDueTomorrowTomorrowThisYear() {
        testDueDate = new DueDate();
        testDueDate.postponeOneDay();
        assertTrue(testDueDate.isDueTomorrow());
    }

    @Test
    public void testIsDueTomorrowYesterdayThisYear() {
        testDueDate = new DueDate(yesterday2359);
        assertFalse(testDueDate.isDueTomorrow());
    }

    @Test
    public void testIsDueTomorrow2DaysThisYear() {
        testDueDate = new DueDate();
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        assertFalse(testDueDate.isDueTomorrow());
    }

    @Test
    public void testIsDueTomorrowTodayNotThisYear() {
        testDueDate = new DueDate(todayIn2Years);
        assertFalse(testDueDate.isDueTomorrow());
    }

    @Test
    public void testIsDueTomorrowTomorrowNotThisYear() {
        testDueDate = new DueDate(todayIn2Years);
        testDueDate.postponeOneDay();
        assertFalse(testDueDate.isDueTomorrow());
    }

    @Test
    public void testIsDueTomorrowYesterdayNotThisYear() {
        testDueDate = new DueDate(yesterdayIn2Years);
        assertFalse(testDueDate.isDueTomorrow());
    }

    @Test
    public void testIsDueTomorrow2DaysNotThisYear() {
        testDueDate = new DueDate(todayIn2Years);
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        assertFalse(testDueDate.isDueTomorrow());
    }

    @Test
    public void testIsDueWithinAWeekTodayThisYear() {
        testDueDate = new DueDate();
        assertTrue(testDueDate.isDueWithinAWeek());
    }

    @Test
    public void testIsDueWithinAWeekTomorrowThisYear() {
        testDueDate = new DueDate();
        testDueDate.postponeOneDay();
        assertTrue(testDueDate.isDueWithinAWeek());
    }

    @Test
    public void testIsDueWithinAWeekYesterdayThisYear() {
        testDueDate = new DueDate(yesterday2359);
        assertFalse(testDueDate.isDueWithinAWeek());
    }

    @Test
    public void testIsDueWithinAWeek5DaysThisYear() {
        testDueDate = new DueDate();
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        assertTrue(testDueDate.isDueWithinAWeek());
    }

    @Test
    public void testIsDueWithinAWeek6DaysThisYear() {
        testDueDate = new DueDate();
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        assertTrue(testDueDate.isDueWithinAWeek());
    }

    @Test
    public void testIsDueWithinAWeek7DaysThisYear() {
        testDueDate = new DueDate();
        testDueDate.postponeOneWeek();
        assertFalse(testDueDate.isDueWithinAWeek());
    }

    @Test
    public void testIsDueWithinAWeek8DaysThisYear() {
        testDueDate = new DueDate();
        testDueDate.postponeOneWeek();
        testDueDate.postponeOneDay();
        assertFalse(testDueDate.isDueWithinAWeek());
    }

    @Test
    public void testIsDueWithinAWeekTodayNotThisYear() {
        testDueDate = new DueDate(todayIn2Years);
        assertFalse(testDueDate.isDueWithinAWeek());
    }

    @Test
    public void testIsDueWithinAWeekTomorrowNotThisYear() {
        testDueDate = new DueDate(todayIn2Years);
        testDueDate.postponeOneDay();
        assertFalse(testDueDate.isDueWithinAWeek());
    }

    @Test
    public void testIsDueWithinAWeekYesterdayNotThisYear() {
        testDueDate = new DueDate(yesterdayIn2Years);
        assertFalse(testDueDate.isDueWithinAWeek());
    }

    @Test
    public void testIsDueWithinAWeek5DaysNotThisYear() {
        testDueDate = new DueDate(todayIn2Years);
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        assertFalse(testDueDate.isDueWithinAWeek());
    }

    @Test
    public void testIsDueWithinAWeek6DaysNotThisYear() {
        testDueDate = new DueDate(todayIn2Years);
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        assertFalse(testDueDate.isDueWithinAWeek());
    }

    @Test
    public void testIsDueWithinAWeek7DaysNotThisYear() {
        testDueDate = new DueDate(todayIn2Years);
        testDueDate.postponeOneWeek();
        assertFalse(testDueDate.isDueWithinAWeek());
    }

    @Test
    public void testIsDueWithinAWeek8DaysNotThisYear() {
        testDueDate = new DueDate(todayIn2Years);
        testDueDate.postponeOneWeek();
        testDueDate.postponeOneDay();
        assertFalse(testDueDate.isDueWithinAWeek());
    }

    @Test
    public void testToStringWedDecPM() {
        testDueDate = new DueDate(Jan1st70GMT);
        assertEquals("Wed Dec 31 1969 4:00 PM", testDueDate.toString());
    }

    @Test
    public void testToStringThursJanPM() {
        testDueDate = new DueDate(Jan1st70GMT);
        testDueDate.postponeOneDay();
        assertEquals("Thurs Jan 1 1970 4:00 PM", testDueDate.toString());
    }

    @Test
    public void testToStringFriJanPM() {
        testDueDate = new DueDate(Jan1st70GMT);
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        assertEquals("Fri Jan 2 1970 4:00 PM", testDueDate.toString());
    }

    @Test
    public void testToStringSatJanPM() {
        testDueDate = new DueDate(Jan1st70GMT);
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        assertEquals("Sat Jan 3 1970 4:00 PM", testDueDate.toString());
    }

    @Test
    public void testToStringSunJanPM() {
        testDueDate = new DueDate(Jan1st70GMT);
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        assertEquals("Sun Jan 4 1970 4:00 PM", testDueDate.toString());
    }

    @Test
    public void testToStringMonJanPM() {
        testDueDate = new DueDate(Jan1st70GMT);
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        assertEquals("Mon Jan 5 1970 4:00 PM", testDueDate.toString());
    }

    @Test
    public void testToStringTuesJanPM() {
        testDueDate = new DueDate(Jan1st70GMT);
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        testDueDate.postponeOneDay();
        assertEquals("Tues Jan 6 1970 4:00 PM", testDueDate.toString());
    }

    @Test
    public void testToStringWedFebPM() {
        testDueDate = new DueDate(Jan1st70GMT);
        for (int i = 0; i < 5; i++) {
            testDueDate.postponeOneWeek();
        }
        assertEquals("Wed Feb 4 1970 4:00 PM", testDueDate.toString());
    }

    @Test
    public void testToStringWedMarPM() {
        testDueDate = new DueDate(Jan1st70GMT);
        for (int i = 0; i < 9; i++) {
            testDueDate.postponeOneWeek();
        }
        assertEquals("Wed Mar 4 1970 4:00 PM", testDueDate.toString());
    }

    @Test
    public void testToStringWedAprPM() {
        testDueDate = new DueDate(Jan1st70GMT);
        for (int i = 0; i < 13; i++) {
            testDueDate.postponeOneWeek();
        }
        assertEquals("Wed Apr 1 1970 4:00 PM", testDueDate.toString());
    }

    @Test
    public void testToStringWedMayPM() {
        testDueDate = new DueDate(Jan1st70GMT);
        for (int i = 0; i < 18; i++) {
            testDueDate.postponeOneWeek();
        }
        assertEquals("Wed May 6 1970 4:00 PM", testDueDate.toString());
    }

    @Test
    public void testToStringWedJunePM() {
        testDueDate = new DueDate(Jan1st70GMT);
        for (int i = 0; i < 22; i++) {
            testDueDate.postponeOneWeek();
        }
        assertEquals("Wed June 3 1970 4:00 PM", testDueDate.toString());
    }

    @Test
    public void testToStringWedJulyPM() {
        testDueDate = new DueDate(Jan1st70GMT);
        for (int i = 0; i < 26; i++) {
            testDueDate.postponeOneWeek();
        }
        assertEquals("Wed July 1 1970 4:00 PM", testDueDate.toString());
    }

    @Test
    public void testToStringWedAugPM() {
        testDueDate = new DueDate(Jan1st70GMT);
        for (int i = 0; i < 31; i++) {
            testDueDate.postponeOneWeek();
        }
        assertEquals("Wed Aug 5 1970 4:00 PM", testDueDate.toString());
    }

    @Test
    public void testToStringWedSeptPM() {
        testDueDate = new DueDate(Jan1st70GMT);
        for (int i = 0; i < 35; i++) {
            testDueDate.postponeOneWeek();
        }
        assertEquals("Wed Sept 2 1970 4:00 PM", testDueDate.toString());
    }

    @Test
    public void testToStringWedOctPM() {
        testDueDate = new DueDate(Jan1st70GMT);
        for (int i = 0; i < 40; i++) {
            testDueDate.postponeOneWeek();
        }
        assertEquals("Wed Oct 7 1970 4:00 PM", testDueDate.toString());
    }

    @Test
    public void testToStringWedNovPM() {
        testDueDate = new DueDate(Jan1st70GMT);
        for (int i = 0; i < 45; i++) {
            testDueDate.postponeOneWeek();
        }
        assertEquals("Wed Nov 11 1970 4:00 PM", testDueDate.toString());
    }

    @Test
    public void testToStringThursDecPM() {
        testDueDate = new DueDate(Jan1st70GMT);
        for (int i = 0; i < 50; i++) {
            testDueDate.postponeOneWeek();
        }
        testDueDate.postponeOneDay();
        assertEquals("Thurs Dec 17 1970 4:00 PM", testDueDate.toString());
    }

    @Test
    public void testToStringDoubleDigitMinHourAM() {
        testDueDate = new DueDate(Jan1st70GMT);
        testDueDate.setDueTime(10, 30);
        assertEquals("Wed Dec 31 1969 10:30 AM", testDueDate.toString());
    }

    @Test
    public void testToString1109AM() {
        testDueDate = new DueDate(Jan1st70GMT);
        testDueDate.setDueTime(11, 9);
        assertEquals("Wed Dec 31 1969 11:09 AM", testDueDate.toString());
    }

    @Test
    public void testToString1210PM() {
        testDueDate = new DueDate(Jan1st70GMT);
        testDueDate.setDueTime(12, 10);
        assertEquals("Wed Dec 31 1969 12:10 PM", testDueDate.toString());
    }

    @Test
    public void testToString1359() {
        testDueDate = new DueDate(Jan1st70GMT);
        testDueDate.setDueTime(13, 1);
        assertEquals("Wed Dec 31 1969 1:01 PM", testDueDate.toString());
    }

    @Test
    public void testToString2359() {
        testDueDate = new DueDate(Jan1st70GMT);
        testDueDate.setDueTime(23, 59);
        assertEquals("Wed Dec 31 1969 11:59 PM", testDueDate.toString());
    }

    @Test
    public void testToString0011() {
        testDueDate = new DueDate(Jan1st70GMT);
        testDueDate.setDueTime(0, 11);
        assertEquals("Wed Dec 31 1969 12:11 AM", testDueDate.toString());
    }

    @Test
    public void testToString0116() {
        testDueDate = new DueDate(Jan1st70GMT);
        testDueDate.setDueTime(1, 16);
        assertEquals("Wed Dec 31 1969 1:16 AM", testDueDate.toString());
    }
}