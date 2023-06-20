package com.flooringmastery.dao;

import java.util.List;

import com.flooringmastery.dto.Product;
import com.flooringmastery.exception.DataPersistenceException;

public interface ProductDao {
	Product getProduct(String productType) throws DataPersistenceException;
	List<Product> loadProducts() throws DataPersistenceException;
}
