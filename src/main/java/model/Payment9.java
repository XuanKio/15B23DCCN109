package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/** Khoản thanh toán của hóa đơn. */
public class Payment9 implements Serializable {
    private long id;
    private LocalDateTime paymentTime;
    private BigDecimal amount;
    private String paymentMethod;
    private String status;
    private String transactionReference;
    private Invoice9 invoice;

    public Payment9() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(LocalDateTime paymentTime) {
        this.paymentTime = paymentTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public Invoice9 getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice9 invoice) {
        this.invoice = invoice;
    }
}
