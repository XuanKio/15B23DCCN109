package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** Kết quả thống kê theo phim trong một khoảng thời gian. */
public class MovieStatistics9 extends Movie9 {
    private LocalDateTime periodStart;
    private LocalDateTime periodEnd;
    private int ticketCount;
    private BigDecimal revenue;

    public MovieStatistics9() {
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

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }
}
