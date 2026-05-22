import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Set;

public class MoreDates {
    public static void main(String[] args) {
        //zonedDateTimes();
        //instants();
        //periodsAndDurations();
        formattingDates();


    }   


    static void zonedDateTimes() {

        /**
         * ZonedDateTime
         *      - accounts for time zones
         *          - does not track things like Daylight Savings Time
         *              - However, the IANA ZoneId will account for DST. 
         *              - if you do fixed offset value (like -5:00) this will be fixed and won't account for DST.
         *      
         */


        // "Local" - absent timezone info
        // "8:38 AM" - no timezone info
        LocalDateTime rightNow = LocalDateTime.now();
        System.out.println("Texas: " + rightNow);

        // IANA format for ZoneIds - needed to create a ZonedDateTime
        ZoneId londonZoneID = ZoneId.of("Europe/London");

        // LocalDateTime.atZone() just adds a time zone to the DateTime
        ZonedDateTime timeInLondon = rightNow.atZone(londonZoneID);
        System.out.println("London: " + timeInLondon);

        // There are hundreds of ZoneIds
        Set<String> allZoneIds = ZoneId.getAvailableZoneIds();
        System.out.println("--- ALL ZONE IDS ---");
        for(String zone : allZoneIds) {
            System.out.println(zone);
        }
        System.out.println("--------------------");


        ZoneId newYorkZoneId = ZoneId.of("America/New_York");
        ZoneId systemZoneId = ZoneId.systemDefault();       // grabs the time zone of the current system


        LocalDateTime certExam = LocalDateTime.of(2026, 6, 12, 9, 0);

        // withZoneSameInstant() - takes an exact instant of time, and converts it to different time zones
        ZonedDateTime myTime = certExam.atZone(systemZoneId);
        ZonedDateTime nyExamTime = myTime.withZoneSameInstant(newYorkZoneId);
        ZonedDateTime londonExamTime = myTime.withZoneSameInstant(londonZoneID); 

        System.out.println("--- withZoneSameInstant() ---");
        System.out.println("My Exam Time: " + myTime);
        System.out.println("New York Exam Time: " + nyExamTime);
        System.out.println("London Exam Time: " + londonExamTime);
        System.out.println("My Instant: " + myTime.toInstant());
        System.out.println("New York Instant: " + nyExamTime.toInstant());
        System.out.println("London Instant: " + londonExamTime.toInstant());


        // withZoneSameLocal() - return the same time, but a different instant
        ZonedDateTime nyLocalExamTime = myTime.withZoneSameLocal(newYorkZoneId);
        ZonedDateTime londonLocalExamTime = myTime.withZoneSameLocal(londonZoneID); 

        System.out.println("--- withZoneSameLocal() ---");
        System.out.println("My Exam Time: " + myTime);
        System.out.println("New Exam Time: " + nyLocalExamTime);
        System.out.println("London Exam Time: " + londonLocalExamTime);
        System.out.println("New York Instant: " + nyLocalExamTime.toInstant());
        System.out.println("London Instant: " + londonLocalExamTime.toInstant());


        // ZonedDateTime will automatically account for DST changes when using IANA ZoneIds
        ZonedDateTime sixMonthsAfterExamInNewYork = nyExamTime.plusMonths(6);
        System.out.println("--- after plusMonths() ---");
        System.out.println("New York Exam Time: " + nyExamTime);
        System.out.println("6 Months Later: " + sixMonthsAfterExamInNewYork);
    }


    static void instants() {

        /**
         * Instant
         *      - represent a single point in time since the Unix Epoch
         *          - Epoch: number of seconds since Jan 1, 1970, 12:00 AM UTC.
         *      - no timezone, no date, no time, only the number
         *          - the number is converted to UTC when you use it (almost like an implicit toString method)
         * 
         *      - standardized timestamp for applications
         */

        Instant now = Instant.now();                                                // current instant time
        Instant epochStart = Instant.ofEpochSecond(0);                  // instant at some given number of seconds
        Instant epochFuture = Instant.ofEpochSecond(999999999999L);     // instant at some given number of seconds in the future
        System.out.println("Right now: " + now);
        System.out.println("Past Epoch: " + epochStart);
        System.out.println("Future Epoch: " + epochFuture);

        // arithmetic is easy with instants
        Instant twoHoursFromNow = now.plusSeconds((60 * 60 * 2));
        Instant threeHoursFromNow = now.plus(Duration.ofHours(3));  // Duration utility methods make arithmetic even simpler
        System.out.println("Right now +2: " + twoHoursFromNow);
        System.out.println("Right now +3: " + threeHoursFromNow);


        ZoneId londonZoneID = ZoneId.of("Europe/London");
        Instant certExam = ZonedDateTime.of(2026, 6, 12, 9, 0, 0, 0, londonZoneID).toInstant();

        // isBefore and isAfter to easily compare Instants 
        System.out.println("Before exam day? " +  now.isBefore(certExam));

    }


    static void periodsAndDurations() {

        /**
         * PERIOD vs DURATION
         * 
         *      Period - calendar based (years/months/days)
         *          - works with LocalDate
         *          - getYears(), getDays(), etc.
         * 
         * 
         *      Duration - clock based (hours/minutes/seconds)
         *          - works with LocalTime, LocalDateTime, ZonedDateTime, Instant
         *          - toHours(), toMinutes(), etc.
         */

        LocalDate may22 = LocalDate.of(2026, 5, 22);
        LocalDate june12 = LocalDate.of(2026, 6, 12);
        LocalDate august11 = LocalDate.of(2026, 8, 11);

        // .between() to find time between two dates
        Period daysUntilExam = Period.between(may22, june12);
        System.out.println("Until Exam: " + daysUntilExam);                     // P21D
        System.out.println("Days Until Exam: " + daysUntilExam.getDays());      // 21

        long daysTilExam = ChronoUnit.DAYS.between(may22, june12);
        System.out.println("Chrono Days Until Exam: " + daysTilExam);           // 21

        // Period.getDays() returns number of days AFTER years and months have been counted. 
        Period daysUntilAug11 = Period.between(may22, august11);
        System.out.println("Until August 11: " + daysUntilAug11);                           // P2M20D
        System.out.println("Months Until August 11: " + daysUntilAug11.getMonths());        // 2
        System.out.println("Days Until August 11: " + daysUntilAug11.getDays());            // 20

        // ChronoUnit is more consistent for one specific unit of time
        long daysTilAug11 = ChronoUnit.DAYS.between(may22, august11);           
        System.out.println("Chrono Days Until August 11: " + daysTilAug11);                 // 81


        LocalDateTime may22NineAM = LocalDateTime.of(2026, 5, 22, 9, 0);
        LocalDateTime june12Ten30AM = LocalDateTime.of(2026, 6, 12, 10, 30);

        // Duration.to*() - returns TOTAL time
        Duration timeUntilExam = Duration.between(may22NineAM, june12Ten30AM);
        System.out.println("Time Until Exam: " + timeUntilExam);                            // PT505H30M
        System.out.println("Days Until Exam: " + timeUntilExam.toDays());                   // 21
        System.out.println("Hours Until Exam: " + timeUntilExam.toHours());                 // 505
        System.out.println("Minutes Until Exam: " + timeUntilExam.toMinutes());             // 30330

        // Duration.to*Part() - gives calculated values similar to Period
        System.out.println("Days Until Exam: " + timeUntilExam.toDaysPart());                   // 21
        System.out.println("Hours Until Exam: " + timeUntilExam.toHoursPart());                 // 1
        System.out.println("Minutes Until Exam: " + timeUntilExam.toMinutesPart());             // 30

        // Duration throws DateTimeException when working with LocalDate - use Period instead
        // Here UnsupportedTemporalTypeException is thrown because LocalDate.now() doesn't have time to work with
        Duration durationUntilAug11 = Duration.between(may22, august11);
        System.out.println("Time Until Exam: " + durationUntilAug11);  

    }


    static void formattingDates() {
        /**
         * Built in formatters from DateTimeFormatter for ISO 8601 standard
         * 
         *      ISO_LOCAL_DATE              LocalDate
         *      ISO_LOCAL_DATE_TIME         LocalDateTime
         *      ISO_LOCAL_TIME              LocalTime
         *      ISO_ZONED_DATE_TIME         ZonedDateTime
         *      ISO_INSTANT                 Instant, ZonedDateTime 
         *      RFC_1123_DATE_TIME          ZonedDateTime (HTTP date headers)
         */
        LocalDateTime rightNow = LocalDateTime.now();
        ZoneId tokyoZoneId = ZoneId.of("Asia/Tokyo");
        ZonedDateTime rightNowTokyo = rightNow.atZone(ZoneId.systemDefault());
        ZonedDateTime tokyotime = rightNowTokyo.withZoneSameInstant(tokyoZoneId);


        // yyyy-mm-ddThh:mm:ss.millis+offset[ZoneId]        2026-05-22T10:36:49.8740304+09:00[Asia/Tokyo]
        System.out.println(tokyotime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));

        // yyyy-mm-ddThh:mm:ss.millis in UTC                2026-05-22T01:36:49.874030400Z
        System.out.println(tokyotime.format(DateTimeFormatter.ISO_INSTANT));

        // DoW, dd Mon Year HH:MM:SS +offset                Fri, 22 May 2026 10:36:49 +0900
        System.out.println(tokyotime.format(DateTimeFormatter.RFC_1123_DATE_TIME));

        /**
         * FormatStyle for localized date formatting
         */
        LocalDate today = LocalDate.of(2026, 11, 5);
        System.out.println("Full Date: " + today.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));      // Thursday, November 5, 2026
        System.out.println("Long Date: " + today.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));      // November 5, 2026
        System.out.println("Med Date: " + today.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));     // Nov 5, 2026
        System.out.println("Short Date: " + today.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));    // 11/5/26

    }
}
