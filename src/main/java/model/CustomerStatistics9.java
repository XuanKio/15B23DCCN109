package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** Kết quả thống kê theo khách hàng trong một khoảng thời gian. */
public class CustomerStatistics9 extends Customer9 {
    private LocalDateTime periodStart;
    private LocalDateTime periodEnd;
    private int ticketCount;
    private BigDecimal totalSpent;

    public CustomerStatistics9() {
        super();
    }

    public LocalDateTime getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(LocalDateTime periodStart) {
        this.periodStart = periodStart;
    }

    public LocalDateTime getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(LocalDateTime periodEnd) {
        this.periodEnd = periodEnd;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }

    public BigDecimal getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(BigDecimal totalSpent) {
        this.totalSpent = totalSpent;
    }
}
