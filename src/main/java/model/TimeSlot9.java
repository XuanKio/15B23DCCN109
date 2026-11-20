package model;

import java.io.Serializable;
import java.time.LocalTime;

/** Khung giờ chiếu. */
public class TimeSlot9 implements Serializable {
    private long id;
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;

    public TimeSlot9() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    /** Số phút của khung giờ, dùng để kiểm tra thời lượng phim. */
    public long getDurationMinutes() {
        return java.time.Duration.between(startTime, endTime).toMinutes();
    }

    /** Nhãn hiển thị dạng 18:00–20:00. */
    public String getLabel() {
        return startTime + "–" + endTime;
    }
}
