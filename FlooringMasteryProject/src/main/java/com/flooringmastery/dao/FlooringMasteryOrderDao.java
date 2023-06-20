package com.flooringmastery.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.flooringmastery.dto.Order;
import com.flooringmastery.exception.DataPersistenceException;

@Component
public class FlooringMasteryOrderDao implements OrderDao, Serializable {
	
	public static final String DELIMITER = ",";
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
	
	public static final String HEADER = ("OrderNumber,CustomerName,State,TaxRate,"
            + "ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,"
			+ "MaterialCost,LaborCost,Tax,Total,CreateDate");
	
	List<Order> orderList = new ArrayList<>();
	

	@Override
	public Order addOrder(Order order) throws DataPersistenceException {

//		order.setOrderNumber(1);
	
		try {
			int maxOrderNumber = 0;
			orderList = readOrder(order.getTimeStamp());
			for (Order o : orderList) {
				if (o.getOrderNumber() > maxOrderNumber) {
					maxOrderNumber = o.getOrderNumber();
					
				}
			}
			order.setOrderNumber(maxOrderNumber + 1);
		
			
			LocalDate date = order.getTimeStamp();
			orderList.add(order);
		
			writeOrder(date, orderList);
		} catch (IOException e) {
			System.out.println("could not add order, please try again!");
		} catch (DataPersistenceException e) {
			throw new DataPersistenceException("could not add order, please try again!");
		}
		return order;

	}

	@Override
	public Order editOrder(Order order) throws DataPersistenceException, IOException {
		
		List<Order> selectedOrdersByDate = readOrder(order.getTimeStamp());
		
		Order selectedOrder = selectedOrdersByDate.stream()
				.filter( o -> o.getOrderNumber()== order.getOrderNumber())
				.findFirst().orElse(null);
		
		if (selectedOrder != null) {
			int index = selectedOrdersByDate.indexOf(selectedOrder);
			selectedOrdersByDate.set(index, order);
			writeOrder(selectedOrder.getTimeStamp(), selectedOrdersByDate);
			return selectedOrder;
		} else {
			return null;
		}
		

	}
	
	@Override
	public void removeOrder(LocalDate date, int orderNumber) throws DataPersistenceException, IOException {
		List<Order> selectedOrdersByDate = readOrder(date);
		
		Order selectedOrder = selectedOrdersByDate.stream()
				.filter( o -> o.getOrderNumber()== orderNumber)
				.findFirst().orElse(null);
		
		if (selectedOrder != null) {
			int index = selectedOrdersByDate.indexOf(selectedOrder);
			selectedOrdersByDate.remove(selectedOrder);
			writeOrder(selectedOrder.getTimeStamp(), selectedOrdersByDate);
//			return selectedOrder;
		} else {
			System.out.println("Order Not Removed!");
		}

	}
	
	
	@Override
	public Order getOrder(LocalDate date, int orderNumber) throws DataPersistenceException {
		List<Order> selectedOrdersByDate = readOrder(date);
		
		Order selectedOrder = selectedOrdersByDate.stream()
				.filter( o -> o.getOrderNumber()== orderNumber)
				.findFirst().orElse(null);
		
		if (selectedOrder != null) {
			return selectedOrder;
		} else {
			return null;
		}
	}


	@Override
	public List<Order> searchOrders(LocalDate date) throws DataPersistenceException {
		List<Order> orderList = readOrder(date);
		
		return orderList;
	}
	
	
	public List<Order> dataExport(LocalDate date, int orderNumber) throws DataPersistenceException, IOException {
		List<Order> orders = new ArrayList<>();
		Order order = getOrder(date, orderNumber);
		orders.add(order);
		
		writeOrderForExportData(date, orders);
		return orders;
	}
	
	
	// writeOrder
	//-----------------------------------------------------------------------------------------------
	private void writeOrder(LocalDate date, List<Order> orderlist) throws IOException, DataPersistenceException  {
		
		PrintWriter out;
		String filedate = date.format(formatter);
		
		File f = new File(String.format("Orders_%s.txt", filedate));
		
		try {
			out = new PrintWriter(new FileWriter(f, false));
		} 
		catch (FileNotFoundException e) {
			throw new DataPersistenceException(" Could not load order into memory");
			
		}
		
		out.print(HEADER + "\n");
		
		for (Order order : orderList) {
			out.println(+ order.getOrderNumber() + DELIMITER
						+ order.getCustomerName() + DELIMITER
						+ order.getState() + DELIMITER
						+ order.getTaxRate().setScale(2, RoundingMode.HALF_UP) + DELIMITER
						+ order.getProductType() + DELIMITER
						+ order.getArea().setScale(2, RoundingMode.HALF_UP) + DELIMITER
						+ order.getCostPerSquareFoot().setScale(2, RoundingMode.HALF_UP) + DELIMITER
						+ order.getLaborCostPerSquareFoot().setScale(2, RoundingMode.HALF_UP) + DELIMITER
						+ order.getMaterialCost().setScale(2, RoundingMode.HALF_UP) + DELIMITER
						+ order.getLaborCost().setScale(2, RoundingMode.HALF_UP) + DELIMITER
						+ order.getTotalTax().setScale(2, RoundingMode.HALF_UP) + DELIMITER
						+ order.getTotalCost().setScale(2, RoundingMode.HALF_UP) + DELIMITER
						+ order.getTimeStamp().format(formatter));
						
			
			//Force PrintWriter to write line to the file
			out.flush();
		}
		out.close();
	}
	
	// readOrder
	//---------------------------------------------------------
	private List<Order> readOrder(LocalDate date) {
		
		String filedate = date.format(formatter);
		File f = new File(String.format("Orders_%s.txt", filedate));
		
		Scanner scanner = null;

		try {
			scanner = new Scanner(
					new BufferedReader(
							new FileReader(f)));
		}
		catch (Exception e) {
			List<Order> orderList = new ArrayList<>();
			return orderList;
			
		}
		
		
		
		String currentline;
		String[] currentOrder;
		
		orderList.clear();
		
		scanner.nextLine(); //Skips scanning document headers 
		while (scanner.hasNextLine()) {
			currentline = scanner.nextLine();
			
			currentOrder = currentline.split(DELIMITER);
			if (currentOrder.length == 13) {
				Order order = new Order();
				
				Integer orderNumber = Integer.parseInt(currentOrder[0]);
				String customerName = currentOrder[1];
				String state = currentOrder[2];
				BigDecimal taxRate = new BigDecimal(currentOrder[3]);
				String productType = currentOrder[4];
				BigDecimal area = new BigDecimal(currentOrder[5]);
				BigDecimal costPerSquareFoot = new BigDecimal(currentOrder[6]);
				BigDecimal laborCostPerSquareFoot = new BigDecimal(currentOrder[7]);
				BigDecimal materialCost = new BigDecimal(currentOrder[8]);
				BigDecimal laborCost = new BigDecimal(currentOrder[9]);
				BigDecimal totalTax = new BigDecimal(currentOrder[10]);
				BigDecimal totalCost = new BigDecimal(currentOrder[11]);
				date = LocalDate.parse(currentOrder[12], formatter);
				
			
				order.setOrderNumber(orderNumber);
				order.setCustomerName(customerName);
				order.setState(state);
				order.setTaxRate(taxRate);
				order.setProductType(productType);
				order.setArea(area);
				order.setCostPerSquareFoot(costPerSquareFoot);
				order.setLaborCostPerSquareFoot(laborCostPerSquareFoot);
				order.setMaterialCost(materialCost);
				order.setLaborCost(laborCost);
				order.setTotalTax(totalTax);
				order.setTotalCost(totalCost);
				order.setTimeStamp(date);
				
				orderList.add(order);
			}
				
		}
		
		scanner.close();
		
		return orderList;
	
	}
	
	// writeOrder for Export data
	//-----------------------------------------------------------------------------------------------
	private void writeOrderForExportData(LocalDate date, List<Order> orders) throws IOException, DataPersistenceException  {
		
		PrintWriter out;
		String file = "DataExport";
		
		File f = new File(file);
		
		try {
			out = new PrintWriter(new FileWriter(f));
		} 
		catch (FileNotFoundException e) {
			throw new DataPersistenceException(" Could not load order into memory");
			
		}
		
		out.print(HEADER + "\n");
		
		for (Order order : orders) {
			out.println(+ order.getOrderNumber() + DELIMITER
						+ order.getCustomerName() + DELIMITER
						+ order.getState() + DELIMITER
						+ order.getTaxRate().setScale(2, RoundingMode.HALF_UP) + DELIMITER
						+ order.getProductType() + DELIMITER
						+ order.getArea().setScale(2, RoundingMode.HALF_UP) + DELIMITER
						+ order.getCostPerSquareFoot().setScale(2, RoundingMode.HALF_UP) + DELIMITER
						+ order.getLaborCostPerSquareFoot().setScale(2, RoundingMode.HALF_UP) + DELIMITER
						+ order.getMaterialCost().setScale(2, RoundingMode.HALF_UP) + DELIMITER
						+ order.getLaborCost().setScale(2, RoundingMode.HALF_UP) + DELIMITER
						+ order.getTotalTax().setScale(2, RoundingMode.HALF_UP) + DELIMITER
						+ order.getTotalCost().setScale(2, RoundingMode.HALF_UP) + DELIMITER
						+ order.getTimeStamp().format(formatter));
						
			
			//Force PrintWriter to write line to the file
			out.flush();
		}
		out.close();
	}

	
}
