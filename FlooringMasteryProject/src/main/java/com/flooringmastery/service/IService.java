package com.flooringmastery.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.flooringmastery.dto.Order;
import com.flooringmastery.exception.DataPersistenceException;
import com.flooringmastery.exception.InvalidOrderException;

public interface IService {
	
	Order addOrder(Order order) throws DataPersistenceException, IOException;
	Order editOrder(Order order) throws DataPersistenceException, IOException;
	void removeOrder(LocalDate date, int orderNumber) throws DataPersistenceException, IOException;
	
	Order getOrder(LocalDate date, int orderNumber) throws DataPersistenceException, InvalidOrderException;
	List<Order> getOrdersByDate(LocalDate date) throws DataPersistenceException, InvalidOrderException;
	Order calculateCost(Order order) throws DataPersistenceException;
	List<Order> exportData(LocalDate date, int orderNumber) throws DataPersistenceException, IOException;
	
}
