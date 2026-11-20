package model;

import java.io.Serializable;

/** Ghế vật lí trong phòng chiếu. */
public class CinemaSeat9 implements Serializable {
    public static final String USABLE = "Dùng được";
    public static final String MAINTENANCE = "Bảo trì";

    private long id;
    private String seatCode;
    private String rowLabel;
    private int seatNumber;
    private String status;
    private ScreeningRoom9 room;
    private SeatType9 seatType;

    public CinemaSeat9() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public String getRowLabel() {
        return rowLabel;
    }

    public void setRowLabel(String rowLabel) {
        this.rowLabel = rowLabel;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ScreeningRoom9 getRoom() {
        return room;
    }

    public void setRoom(ScreeningRoom9 room) {
        this.room = room;
    }

    public SeatType9 getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType9 seatType) {
        this.seatType = seatType;
    }
}
