package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/** Hóa đơn bán vé. */
public class Invoice9 implements Serializable {
    private long id;
    private String invoiceNumber;
    private LocalDateTime saleTime;
    private String saleChannel;
    private Customer9 customer;
    private SalesEmployee9 seller;
    private MembershipCard9 membershipCard;
    private List<Ticket9> tickets;
    private List<Payment9> payments;

    public Invoice9() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public LocalDateTime getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(LocalDateTime saleTime) {
        this.saleTime = saleTime;
    }

    public String getSaleChannel() {
        return saleChannel;
    }

    public void setSaleChannel(String saleChannel) {
        this.saleChannel = saleChannel;
    }

    public Customer9 getCustomer() {
        return customer;
    }

    public void setCustomer(Customer9 customer) {
        this.customer = customer;
    }

    public SalesEmployee9 getSeller() {
        return seller;
    }

    public void setSeller(SalesEmployee9 seller) {
        this.seller = seller;
    }

    public MembershipCard9 getMembershipCard() {
        return membershipCard;
    }

    public void setMembershipCard(MembershipCard9 membershipCard) {
        this.membershipCard = membershipCard;
    }

    public List<Ticket9> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket9> tickets) {
        this.tickets = tickets;
    }

    public List<Payment9> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment9> payments) {
        this.payments = payments;
    }
}
