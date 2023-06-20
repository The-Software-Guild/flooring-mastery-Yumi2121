package com.flooringmastery.dao;

import java.math.BigDecimal;
import java.util.List;

import com.flooringmastery.dto.Tax;
import com.flooringmastery.exception.DataPersistenceException;

public interface TaxDao {
	List<Tax> loadTaxes() throws DataPersistenceException;
}
