package model;

import java.io.Serializable;
import java.math.BigDecimal;

/** Loại ghế và phụ thu. */
public class SeatType9 implements Serializable {
    private long id;
    private String typeName;
    private BigDecimal surcharge;
    private String description;

    public SeatType9() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public BigDecimal getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(BigDecimal surcharge) {
        this.surcharge = surcharge;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
