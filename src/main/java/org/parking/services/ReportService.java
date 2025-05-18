package org.parking.services;

import org.parking.dao.ReportDAO;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

public class ReportService {

    private final ReportDAO dao = new ReportDAO();

    public Map<String, Double> getRevenueByType() throws IOException {
        return dao.revenueByType();
    }

    public Map<LocalDate, Double> getDailyRevenue(int days) throws IOException {
        return dao.dailyRevenue(days);
    }
}
