package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/** Kết quả thống kê doanh thu trong một khoảng thời gian. */
public class RevenueStatistics9 implements Serializable {
    private LocalDateTime periodStart;
    private LocalDateTime periodEnd;
    private int ticketCount;
    private BigDecimal totalRevenue;

    public RevenueStatistics9() {
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

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
