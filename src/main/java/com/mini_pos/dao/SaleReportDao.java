package com.mini_pos.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import com.mini_pos.dao.etinity.SaleReport;

public interface SaleReportDao {
	public List<SaleReport> getAllReport(  );
	public List<SaleReport> getCategoryReport( );
	public List<SaleReport> getSummaryReport( );
	public List<SaleReport> getDailyReport(LocalDateTime date );
	public List<SaleReport> getMonthlyReport(YearMonth month) ;
	public List<SaleReport> getReportByInterval(LocalDate startDate, LocalDate endDate);
}
