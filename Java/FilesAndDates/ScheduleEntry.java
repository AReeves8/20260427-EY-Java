import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ScheduleEntry {

    private String name;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public ScheduleEntry() {
    }

    public ScheduleEntry(String name, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    /**
     * DURATION
     *      - utility class that makes it easy to compare dates and times
     */
    Duration getDuration() {

        // calculates the time between the end and the start
        return Duration.between(startTime, endTime);    
    }

    LocalDateTime getStartDateTime() {
        return date.atTime(startTime);     
    }

    @Override
    public String toString() {
        return "ScheduleEntry [name=" + name + ", date=" + date + ", startTime=" + startTime + ", endTime=" + endTime
                + "]";
    }
}
