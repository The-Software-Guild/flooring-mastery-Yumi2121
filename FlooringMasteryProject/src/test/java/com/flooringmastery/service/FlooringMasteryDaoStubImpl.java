package com.flooringmastery.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.ArrayList;

import com.flooringmastery.dao.OrderDao;
import com.flooringmastery.dto.Order;
import com.flooringmastery.exception.DataPersistenceException;

public class FlooringMasteryDaoStubImpl implements OrderDao {
	
	public Order currentOrder;
	List<Order> orderList = new ArrayList<>();
	
	public Order stubOrder() {
	
		
		LocalDate date = LocalDate.of(2023, Month.JUNE, 20);

	        BigDecimal rate = new BigDecimal(4.45);
	        BigDecimal area = new BigDecimal(110);
	        BigDecimal costSQ = new BigDecimal(2.25);
	        BigDecimal costLabSQ = new BigDecimal(2.10);
	        BigDecimal material = new BigDecimal(128.75);
	        BigDecimal labor = new BigDecimal(118.75);
	        BigDecimal tax = new BigDecimal(14.85);
	        BigDecimal total = new BigDecimal(262.35);

	        currentOrder.setOrderNumber(0);
	        currentOrder.setCustomerName("Anna Dell");
	        currentOrder.setState("TX");
	        currentOrder.setTaxRate(rate);
	        currentOrder.setProductType("Wood");
	        currentOrder.setArea(area);
	        currentOrder.setCostPerSquareFoot(costSQ);
	        currentOrder.setLaborCostPerSquareFoot(costLabSQ);
	        currentOrder.setMaterialCost(material);
	        currentOrder.setLaborCost(labor);
	        currentOrder.setTotalTax(tax);
	        currentOrder.setTotalCost(total);
	        currentOrder.setTimeStamp(date);

	      
	        orderList.add(currentOrder);
	        return currentOrder;
	    }
		

	@Override
	public Order addOrder(Order order) throws DataPersistenceException, IOException {
		
		return order;
	}

	@Override
	public Order editOrder(Order order) throws DataPersistenceException, IOException {
		return order;
	}

	@Override
	public void removeOrder(LocalDate date, int orderNumber) throws DataPersistenceException, IOException {
		

	}

	@Override
	public Order getOrder(LocalDate date, int orderNumber) throws DataPersistenceException {
		Order fetchOrder = new Order();
		for (Order order : orderList) {
			if(order.getOrderNumber()== orderNumber) {
				fetchOrder = order;
			}
		}
		return fetchOrder;
	}

	@Override
	public List<Order> searchOrders(LocalDate date) throws DataPersistenceException {
		
		return orderList;
	}

	@Override
	public List<Order> dataExport(LocalDate date, int orderNumber) throws DataPersistenceException, IOException {
		return null;
	}

}
