package model;

import java.io.Serializable;
import java.math.BigDecimal;

/** Vé cho một ghế theo suất. */
public class Ticket9 implements Serializable {
    private long id;
    private String ticketNumber;
    private BigDecimal price;
    private ScreeningCinemaSeat9 screeningCinemaSeat;
    private Invoice9 invoice;
    private MembershipCard9 membershipCard;

    public Ticket9() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ScreeningCinemaSeat9 getScreeningCinemaSeat() {
        return screeningCinemaSeat;
    }

    public void setScreeningCinemaSeat(ScreeningCinemaSeat9 screeningCinemaSeat) {
        this.screeningCinemaSeat = screeningCinemaSeat;
    }

    public Invoice9 getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice9 invoice) {
        this.invoice = invoice;
    }

    public MembershipCard9 getMembershipCard() {
        return membershipCard;
    }

    public void setMembershipCard(MembershipCard9 membershipCard) {
        this.membershipCard = membershipCard;
    }
}
