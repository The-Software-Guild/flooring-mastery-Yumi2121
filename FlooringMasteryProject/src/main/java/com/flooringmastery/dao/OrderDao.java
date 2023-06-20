package com.flooringmastery.dao;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.flooringmastery.dto.Order;
import com.flooringmastery.exception.DataPersistenceException;

public interface OrderDao {
	public Order addOrder(Order order) throws DataPersistenceException, IOException;
	public Order editOrder(Order order) throws DataPersistenceException, IOException;
	public void removeOrder(LocalDate date, int orderNumber) throws DataPersistenceException, IOException;
	
	public Order getOrder(LocalDate date, int orderNumber) throws DataPersistenceException;
	public List<Order> searchOrders(LocalDate date) throws DataPersistenceException;
	public List<Order> dataExport(LocalDate date, int orderNumber) throws DataPersistenceException, IOException;
	
}
