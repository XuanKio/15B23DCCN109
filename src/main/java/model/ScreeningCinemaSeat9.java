package model;

import java.io.Serializable;
import java.time.LocalDateTime;

/** Ghế theo suất chiếu (lớp liên kết Screening9 - CinemaSeat9). */
public class ScreeningCinemaSeat9 implements Serializable {
    public static final String EMPTY = "Trống";
    public static final String UNAVAILABLE = "Không khả dụng";

    private long id;
    private LocalDateTime holdExpireTime;
    private String status;
    private Screening9 screening;
    private CinemaSeat9 seat;

    public ScreeningCinemaSeat9() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getHoldExpireTime() {
        return holdExpireTime;
    }

    public void setHoldExpireTime(LocalDateTime holdExpireTime) {
        this.holdExpireTime = holdExpireTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Screening9 getScreening() {
        return screening;
    }

    public void setScreening(Screening9 screening) {
        this.screening = screening;
    }

    public CinemaSeat9 getSeat() {
        return seat;
    }

    public void setSeat(CinemaSeat9 seat) {
        this.seat = seat;
    }
}
