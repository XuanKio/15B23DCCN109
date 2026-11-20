package model;

import java.io.Serializable;

/** Phòng chiếu. capacity là thuộc tính dẫn xuất, đếm từ ghế vật lí của phòng. */
public class ScreeningRoom9 implements Serializable {
    public static final String IN_USE = "Đang sử dụng";
    public static final String MAINTENANCE = "Bảo trì";

    private long id;
    private String roomName;
    private String usageStatus;
    private String description;
    private Cinema9 cinema;
    private int capacity;
    private int usableSeats;

    public ScreeningRoom9() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getUsageStatus() {
        return usageStatus;
    }

    public void setUsageStatus(String usageStatus) {
        this.usageStatus = usageStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Cinema9 getCinema() {
        return cinema;
    }

    public void setCinema(Cinema9 cinema) {
        this.cinema = cinema;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /** Số ghế vật lí đang dùng được (không lưu trong CSDL, đếm khi đọc). */
    public int getUsableSeats() {
        return usableSeats;
    }

    public void setUsableSeats(int usableSeats) {
        this.usableSeats = usableSeats;
    }
}
