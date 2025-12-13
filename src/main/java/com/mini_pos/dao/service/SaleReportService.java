package com.mini_pos.dao.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import com.mini_pos.dao.etinity.SaleReport;
import com.mini_pos.helper_function.DaoException;

public interface SaleReportService {
	public List<SaleReport> getAllReport(  ) throws DaoException;
	public List<SaleReport> getCategoryReport( ) throws DaoException;
	public List<SaleReport> getSummaryReport( ) throws DaoException;
	public List<SaleReport> getMonthlyReport(YearMonth month) throws DaoException;
	public List<SaleReport> getDailyReport(LocalDateTime date) throws DaoException;
	public List<SaleReport> getReportByInterval(LocalDate startDate, LocalDate endDate) throws DaoException;
}
