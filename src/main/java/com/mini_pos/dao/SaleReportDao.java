package com.mini_pos.dao;

import java.time.LocalDate;
import java.util.List;

import com.mini_pos.dao.etinity.SaleReport;

public interface SaleReportDao {
	public List<SaleReport> getAllReport(  );
	public List<SaleReport> getCategoryReport( );
	public List<SaleReport> getSummaryReport( );
//	public List<SaleReport> getDailyReport(LocalDateTime date );
//	public List<SaleReport> getMonthlyReport(YearMonth month) ;
	public List<SaleReport> getAllItemsReportByInterval(LocalDate startDate, LocalDate endDate,String itemCode);
	public List<SaleReport> getCategoryReportByInterval(LocalDate startDate, LocalDate endDate);
	public List<SaleReport> getSummaryReportByInterval(LocalDate startDate, LocalDate endDate);
	public List<SaleReport> getItemReport(String itemcode );
}
