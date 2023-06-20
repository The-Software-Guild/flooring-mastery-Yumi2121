package com.flooringmastery.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.flooringmastery.dao.FlooringMasteryOrderDao;
import com.flooringmastery.dao.FlooringMasteryProductDao;
import com.flooringmastery.dao.FlooringMasteryTaxDao;
import com.flooringmastery.dao.OrderDao;
import com.flooringmastery.dao.ProductDao;
import com.flooringmastery.dao.TaxDao;
import com.flooringmastery.dto.Order;
import com.flooringmastery.dto.Product;
import com.flooringmastery.dto.Tax;
import com.flooringmastery.exception.DataPersistenceException;
import com.flooringmastery.exception.InvalidOrderException;

@Service
public class FlooringMasteryService implements IService {
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private TaxDao taxDao;

	
	@Autowired
	public FlooringMasteryService(FlooringMasteryOrderDao dao1, FlooringMasteryProductDao dao2, FlooringMasteryTaxDao dao3) {
		this.orderDao = dao1;
		this.productDao = dao2;
		this.taxDao = dao3;
	}
	

	@Override
	public Order addOrder(Order order) throws DataPersistenceException, IOException {
		orderDao.addOrder(order);
		return order;

	}

	@Override
	public Order editOrder(Order order) throws DataPersistenceException, IOException {
		orderDao.editOrder(order);
		return order;

	}

	@Override
	public void removeOrder(LocalDate date, int orderNumber) throws DataPersistenceException, IOException {
		orderDao.removeOrder(date, orderNumber); 

	}


	@Override
	public List<Order> getOrdersByDate(LocalDate date) throws DataPersistenceException, InvalidOrderException {
		List<Order> orders = orderDao.searchOrders(date);
		if (!orders.isEmpty()) {
			return orders;
		} else {
			throw new InvalidOrderException("Error: No orders exsit on this date!");
		}
	
	}


	@Override
	public Order getOrder(LocalDate date, int orderNumber) throws DataPersistenceException, InvalidOrderException {
		Order order = orderDao.getOrder(date, orderNumber);
		if (order != null) {
			return order;
		} else {
			throw new InvalidOrderException("Error: No order with given date and order number!");
		}
		
	}


	@Override
	public Order calculateCost(Order order) throws DataPersistenceException {
		BigDecimal area = order.getArea();
		BigDecimal taxRate = new BigDecimal(0);
		List<Tax> taxList = taxDao.loadTaxes();
		List<Product> productList = productDao.loadProducts();
		
		for (Tax t : taxList) {
			if (t.getStateAbbreviation().equals(order.getState())) {
				taxRate = t.getTaxRate();
			}
		}
		
		for (Product p : productList) {
			if (p.getProductType().equalsIgnoreCase(order.getProductType())) {
				
				BigDecimal costPerSquareFoot = p.getCostPerSquareFoot();
				BigDecimal laborCostPerSquareFoot = p.getLaborCostPerSquareFoot();
				
				BigDecimal materialCost = area.multiply(costPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
				BigDecimal laborCost = area.multiply(laborCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
				
				BigDecimal tax = (materialCost.add(laborCost)).multiply(taxRate.divide(new BigDecimal(100)));
				
				BigDecimal totalcost = materialCost.add(laborCost).add(tax);
				
				order.setTaxRate(taxRate);
				order.setCostPerSquareFoot(costPerSquareFoot);
				order.setLaborCostPerSquareFoot(laborCostPerSquareFoot);
				order.setMaterialCost(materialCost);
				order.setLaborCost(laborCost);
				order.setTotalTax(tax);
				order.setTotalCost(totalcost);		
				
			}
		}
		
		return order;	
		
	}
	
	public void validateOrder(Order o) throws InvalidOrderException, DataPersistenceException {
		LocalDate today = LocalDate.now();
		
		if (o.getCustomerName().trim().isEmpty() || o.getCustomerName() == null) {
			throw new InvalidOrderException("Customer name is required.");
		}
		
		if (o.getState().trim().isEmpty() || o.getState() == null || !taxDao.loadTaxes().contains(o.getState()) ) {
			throw new InvalidOrderException("State is required and it should covered with our bussiness area.");
		}
		if (o.getArea()== null || o.getArea().compareTo(new BigDecimal("100")) >= 0) {
			throw new InvalidOrderException("Area must be larger than 100!");
		}
		
		if (o.getTimeStamp().isBefore(today)) {
			throw new InvalidOrderException("Order date must be in the future.");
		}
	}
	
	public List<Order> exportData(LocalDate date, int orderNumber) throws DataPersistenceException, IOException {
		List<Order> orders = new ArrayList<>();
		orders = orderDao.dataExport(date, orderNumber);
			
		return orders;
	}

}
