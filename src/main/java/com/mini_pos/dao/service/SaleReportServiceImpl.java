package com.mini_pos.dao.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import com.mini_pos.dao.SaleReportDao;
import com.mini_pos.dao.etinity.SaleReport;
import com.mini_pos.dao.impl.SaleReportDaoImpl;
import com.mini_pos.helper_function.DaoException;

public class SaleReportServiceImpl implements SaleReportService {

	SaleReportDao saleRpDao = new SaleReportDaoImpl();
	
	@Override
	public List<SaleReport> getAllReport( ) throws DaoException {
		try {
		return saleRpDao.getAllReport();
		}
		catch(Exception e) {
			throw new DaoException( "Error happen in getAllReport from database", e);
		}
	}

	@Override
	public List<SaleReport> getCategoryReport( ) throws DaoException {
		try {
		return saleRpDao.getCategoryReport();
		}
		catch(Exception e) {
			throw new DaoException( "Error happen in getCategoryReport from database", e);
		}
	}

	@Override
	public List<SaleReport> getSummaryReport( ) throws DaoException {
		try {
		return saleRpDao.getSummaryReport();
		}
		catch(Exception e) {
			throw new DaoException( "Error happen in getSummaryReport from database", e);
		}
	}

	@Override
	public List<SaleReport> getMonthlyReport(YearMonth month) throws DaoException {
		try {
		return saleRpDao.getMonthlyReport(month	);
		}
		catch(Exception e) {
			throw new DaoException( "Error happen in getSummaryReport from database", e);
		}
	}

	@Override
	public List<SaleReport> getDailyReport(LocalDateTime date) throws DaoException {
		try {
			return saleRpDao.getDailyReport(date);
			}
			catch(Exception e) {
				throw new DaoException( "Error happen in getDailyReport from database", e);
			}
	}

	@Override
	public List<SaleReport> getReportByInterval(LocalDate startDate, LocalDate endDate) throws DaoException {
		try {
			return saleRpDao.getReportByInterval(startDate , endDate);
			}
			catch(Exception e) {
				throw new DaoException( "Error happen in getReport from database", e);
			}
	}
	
	

}
