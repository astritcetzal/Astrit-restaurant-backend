package mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.dto;

import java.math.BigDecimal;

public class SalesReportDto {

    private BigDecimal totalRevenue;
    private Long totalOrders;
    private Long totalClients;

    public SalesReportDto() {
    }

    public SalesReportDto(BigDecimal totalRevenue, Long totalOrders, Long totalClients) {
        this.totalRevenue = totalRevenue;
        this.totalOrders = totalOrders;
        this.totalClients = totalClients;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public Long getTotalClients() {
        return totalClients;
    }

    public void setTotalClients(Long totalClients) {
        this.totalClients = totalClients;
    }
}
