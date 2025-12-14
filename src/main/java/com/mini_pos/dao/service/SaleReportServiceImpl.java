package com.mini_pos.dao.service;

import java.time.LocalDate;
import java.util.List;

import com.mini_pos.dao.SaleReportDao;
import com.mini_pos.dao.etinity.SaleReport;
import com.mini_pos.dao.impl.SaleReportDaoImpl;
import com.mini_pos.helper_function.DaoException;
import com.mini_pos.helper_function.ValidationException;

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

//	@Override
//	public List<SaleReport> getMonthlyReport(YearMonth month) throws DaoException {
//		try {
//		return saleRpDao.getMonthlyReport(month	);
//		}
//		catch(Exception e) {
//			throw new DaoException( "Error happen in getSummaryReport from database", e);
//		}
//	}
//
//	@Override
//	public List<SaleReport> getDailyReport(LocalDateTime date) throws DaoException {
//		try {
//			return saleRpDao.getDailyReport(date);
//			}
//			catch(Exception e) {
//				throw new DaoException( "Error happen in getDailyReport from database", e);
//			}
//	}

	@Override
	public List<SaleReport> getAllItemsReportByInterval(LocalDate startDate, LocalDate endDate,String itemCode) throws DaoException, ValidationException {
	    if (startDate == null) {
	        throw new ValidationException("Start date is required!");
	    }
	    if (endDate == null) {
	        throw new ValidationException("End date is required!");
	    }
	    if (endDate.isBefore(startDate)) {
	        throw new ValidationException("End date cannot be before start date!");
	    }	
		try {
			return saleRpDao.getAllItemsReportByInterval(startDate , endDate,itemCode);
			}
			catch(Exception e) {
				throw new DaoException( "Error happen in All Items getReport from database", e);
			}
	}

	@Override
	public List<SaleReport> getCategoryReportByInterval(LocalDate startDate, LocalDate endDate)throws DaoException, ValidationException {
			
	    if (startDate == null) {
	        throw new ValidationException("Start date is required!");
	    }
	    if (endDate == null) {
	        throw new ValidationException("End date is required!");
	    }
	    if (endDate.isBefore(startDate)) {
	        throw new ValidationException("End date cannot be before start date!");
	    }	
		try {
			return saleRpDao.getCategoryReportByInterval(startDate , endDate);
			}
			catch(Exception e) {
				throw new DaoException( "Error happen in Category getReport from database", e);
			}
	}

	@Override
	public List<SaleReport> getSummaryReportByInterval(LocalDate startDate, LocalDate endDate)throws DaoException, ValidationException {
			
	    if (startDate == null) {
	        throw new ValidationException("Start date is required!");
	    }
	    if (endDate == null) {
	        throw new ValidationException("End date is required!");
	    }
	    if (endDate.isBefore(startDate)) {
	        throw new ValidationException("End date cannot be before start date!");
	    }		
		try {
			return saleRpDao.getSummaryReportByInterval(startDate , endDate);
			}
			catch(Exception e) {
				throw new DaoException( "Error happen Summary getReport from database", e);
			}
	}

	@Override
	public List<SaleReport> getItemReport(String itemcode) throws DaoException, ValidationException {
		if (itemcode == null || itemcode.trim().isEmpty()) {
	        throw new ValidationException("ItemCode required!");
	    }
		
		try {
			return saleRpDao.getItemReport(itemcode);
			}
			catch(Exception e) {
				throw new DaoException( "Error happen in getAllReport from database", e);
			}
		}
	}

	
