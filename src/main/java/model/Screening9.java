package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/** Suất chiếu (lịch chiếu). ticketPrice là giá cơ sở chốt khi lập lịch. */
public class Screening9 implements Serializable {
    public static final String SCHEDULED = "Đã lập lịch";

    private long id;
    private LocalDate screeningDate;
    private BigDecimal ticketPrice;
    private String status;
    private Movie9 movie;
    private ScreeningRoom9 room;
    private TimeSlot9 timeSlot;
    private Manager9 createdBy;

    public Screening9() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getScreeningDate() {
        return screeningDate;
    }

    public void setScreeningDate(LocalDate screeningDate) {
        this.screeningDate = screeningDate;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Movie9 getMovie() {
        return movie;
    }

    public void setMovie(Movie9 movie) {
        this.movie = movie;
    }

    public ScreeningRoom9 getRoom() {
        return room;
    }

    public void setRoom(ScreeningRoom9 room) {
        this.room = room;
    }

    public TimeSlot9 getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot9 timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Manager9 getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Manager9 createdBy) {
        this.createdBy = createdBy;
    }
}
