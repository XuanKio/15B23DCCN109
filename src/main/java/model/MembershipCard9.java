package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/** Thẻ thành viên của khách hàng. */
public class MembershipCard9 implements Serializable {
    private long id;
    private String cardNumber;
    private LocalDate issueDate;
    private String status;
    private BigDecimal discountRate;
    private Customer9 customer;
    private SalesEmployee9 issuedBy;

    public MembershipCard9() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    public Customer9 getCustomer() {
        return customer;
    }

    public void setCustomer(Customer9 customer) {
        this.customer = customer;
    }

    public SalesEmployee9 getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(SalesEmployee9 issuedBy) {
        this.issuedBy = issuedBy;
    }
}
