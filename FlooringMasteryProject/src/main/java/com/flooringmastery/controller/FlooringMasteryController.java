package com.flooringmastery.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import com.flooringmastery.dto.Order;
import com.flooringmastery.exception.DataPersistenceException;
import com.flooringmastery.exception.InvalidOrderException;
import com.flooringmastery.service.FlooringMasteryService;
import com.flooringmastery.service.IService;
import org.springframework.context.ConfigurableApplicationContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
// @ComponentScan(basePackages = "com.flooringmastery")
public class FlooringMasteryController {
	private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
	private static Scanner sc = new Scanner(System.in);
	private static ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	private static FlooringMasteryService service = context.getBean("service", FlooringMasteryService.class);

	
	//case 1 function
	// --------------------------------------------------------
	public static List<Order> displayOrders() {
		List<Order> orderList = new ArrayList<>();
	
		System.out.println("Please Enter Order Date (MMDDYYYY) Format only");
		String dateString = sc.next();
		LocalDate date = LocalDate.parse(dateString, formatter);
		
		try {
			orderList = service.getOrdersByDate(date);
		} catch (DataPersistenceException e) {
			System.out.println("Order cannot be found with matching date! " + e);
		} catch (InvalidOrderException e) {
			System.out.println("Order cannot be found with matching date! " + e);
		}
		System.out.println("Order List display as below: \n");
		System.out.println(orderList);
		return orderList;
		
	}
	
	
	//case 2 function
	// --------------------------------------------------------
	public static void addOrder() throws DataPersistenceException, IOException {
		System.out.println("=======add Order========");
		
		//validate customer name
		boolean validName = false;
		Pattern p = Pattern.compile("[^A-Za-z0-9]");
		String customerName = "";
		do {
			try {
				System.out.println("Please Enter Customer Name");
				customerName = sc.next();
					if (customerName.trim().isEmpty() || customerName == null) {
						System.out.println("Customer name is required.");
					} else {
						customerName = sc.next();
						validName = true;
					}
			} catch (Exception e) {
				System.out.println("customer name is not valid, please try again.");
			}
		} while (!validName);
		
		
		
		//validate state
		
		boolean valid = false;
		String stateResult = "";
		do 
		{
			System.out.println("Please enter the State of Where the Sale is Occuring?");
			System.out.println("State options are: TX, WA, KY, and CA ");
			String state = sc.next().toUpperCase();
			
			if (state.equals("TX") || state.equals("WA") || state.equals("KY") || state.equals("CA")) {
				stateResult = state;
				valid = true;
			} else {
				System.out.printf("You must select one of the options provided!! ");
			} 
			
		} while (!valid);
		
		//validate product
		boolean valid1 = false;
		String productResult = "";
		do {
		System.out.println("Please enter the Product Type: ");
		System.out.println("Product options are: Carpet, Laminate, Tile and wood. ");
		String product = sc.next().toLowerCase();
			if (product.equals("carpet") || product.equals("laminate") || product.equals("tile") || product.equals("wood")) {
				productResult = product;
				valid1 = true;
			} else {
				System.out.printf("You must select one of the options provided! ");
			} 
			
		} while (!valid1);
		
		//validate area
		boolean validArea = false;
		BigDecimal area = new BigDecimal(0);
		do {
			System.out.println("Please enter the Area in SqFeet that you want to Cover (please make sure the number over or equal to 100sqFeet) ");
			int areaInt = sc.nextInt();
			if (areaInt >=100) {
				area = BigDecimal.valueOf(areaInt);
				validArea = true;
			} else {
				System.out.printf("Area must over or equal to 100sqFeet  ");
			}
					
		} while (!validArea);
		
		LocalDate date = LocalDate.now();
		
		Order currentOrder = new Order();
		
		currentOrder.setOrderNumber(currentOrder.getOrderNumber());
		currentOrder.setCustomerName(customerName);
		currentOrder.setState(stateResult);
		currentOrder.setProductType(productResult);
		currentOrder.setArea(area);
		currentOrder.setTimeStamp(date);
	
		
		Order completeOrder = service.calculateCost(currentOrder);
		Order addedOrder = service.addOrder(completeOrder);
		System.out.printf("Order added succesfully!" + addedOrder);
		
	}
	
	//case 3 function
	// --------------------------------------------------------
	public static void editOrder() {
		System.out.println("=======Edit Order========");
		System.out.println("Please enter the order date you want to edit? (MMDDYYYY) Format only");
		String dateString = sc.next();
		LocalDate date = LocalDate.parse(dateString, formatter);
		System.out.println("Please enter the order number you want to edit?");
		int orderNumber = sc.nextInt();
		
		Order editOrder = new Order();
		try {
			editOrder = service.getOrder(date, orderNumber);
		} catch (DataPersistenceException e) {
			System.out.println("Order cannot be found! " + e);
		} catch (InvalidOrderException e) {
			System.out.println("Order cannot be found! " + e);
		}
		
		System.out.println("Change customer name to: (if want to keep the original name, type n)");
		String customerName = sc.next();
		if (customerName.toLowerCase()== "n") {
			customerName = editOrder.getCustomerName();
		}
		
		

		//validate state
		boolean valid = false;
		String stateResult = "";
		do 
		{
			System.out.println("Change state to: (if want to keep the original state, type n)");
			System.out.println("State options are: TX, WA, KY, and CA ");
			String state = sc.next().toUpperCase();
			
			if (state.equals("TX") || state.equals("WA") || state.equals("KY") || state.equals("CA")) {
				stateResult = state;
				valid = true;
			} else if (state== "n") {
				stateResult = editOrder.getState();
				valid = true;
			} else {
				System.out.printf("You must select one of the options provided!! ");
			} 
			
		} while (!valid);
		
		
		//validate product
		boolean valid1 = false;
		String productResult = "";
		do {
		System.out.println("Change Product Type to: (if want to keep the original state, type n)");
		System.out.println("Product options are: Carpet, Laminate, Tile and wood. ");
		String product = sc.next().toLowerCase();
			if (product.equals("carpet") || product.equals("laminate") || product.equals("tile") || product.equals("wood")) {
				productResult = product;
				valid1 = true;
			} else if (product == "n") {
				productResult = editOrder.getProductType();
				valid1 = true;
				}
			else {
				System.out.printf("You must select one of the options provided! ");
			} 
			
		} while (!valid1);
		
		System.out.println("Cahnge Area in SqFeet to: (please make sure the number over or equal to 100sqFeet) ");
		int areaInt = sc.nextInt();
		BigDecimal area = BigDecimal.valueOf(areaInt);
		
		editOrder.setCustomerName(customerName);
		editOrder.setState(stateResult);
		editOrder.setProductType(productResult);
		editOrder.setArea(area);
		
		try {
			service.calculateCost(editOrder);
			service.editOrder(editOrder);
		} catch (DataPersistenceException e) {
			System.out.println("Order fail to edited! " + e);
		} catch (IOException e) {
			System.out.println("Order fail to edited! " + e);
		}
		
		System.out.printf("Order updated succesfully, the updated orfder: " + editOrder );
		
		
	}
	
	//case 4 function
	// --------------------------------------------------------
	public static void removeOrder() {
		System.out.println("=======remove Order========");

		System.out.println("Please enter the order date you want to remove? (MMDDYYYY) Format only");
		String dateString = sc.next();
		LocalDate date = LocalDate.parse(dateString, formatter);
		System.out.println("Please enter the order number you want to remove?");
		int orderNumber = sc.nextInt();
		
		try {
			Order removeOrder = service.getOrder(date, orderNumber);
			service.removeOrder(date, orderNumber);
		} catch (DataPersistenceException e) {
			System.out.println("Order Not Removed!" + e);
		} catch (IOException e) {
			System.out.println("Order Not Removed!" + e);
		} catch (InvalidOrderException e) {
			System.out.println("Order Not Removed!" + e);
		}
		
	}
	
	//case 5 function
	// --------------------------------------------------------
	public static void exportData() {
		List<Order> orders = new ArrayList<>();
		
		System.out.println("Let's get the order information that you want to export.");
		System.out.println("Please Enter Order Date of the order: (MMDDYYYY) Format only");
		String dateString = sc.next();
		LocalDate date = LocalDate.parse(dateString, formatter);
		System.out.println("Please enter the order number: ");
		int orderNumber = sc.nextInt();
		
		try {
			orders = service.exportData(date, orderNumber);
		} catch (DataPersistenceException e) {
			System.out.println("Order could not found!" + e);
		} catch (IOException e) {
			System.out.println("Order could not found!" + e);
		}
		System.out.println("Data Export succesfully!");	
		System.out.println(orders);	
		
	}
	

	public static void main(String[] args) throws DataPersistenceException, IOException {
		Scanner sc = new Scanner(System.in);
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		FlooringMasteryService service = context.getBean("service", FlooringMasteryService.class);
		
		List<Order> orderList = new ArrayList<>();
		
//		Order o1 = new Order(1,"Ada Lovelace","CA",new BigDecimal(25.00),"Tile",new BigDecimal(249.00),new BigDecimal(3.50),new BigDecimal(4.15),new BigDecimal(871.50),new BigDecimal(1033.35),new BigDecimal(476.21),new BigDecimal(2381.06));
//		Order o2 = new Order("Doctor Who","WA",new BigDecimal(9.25),"Wood",new BigDecimal(243.00),new BigDecimal(5.15),new BigDecimal(4.75),new BigDecimal(1251.45),new BigDecimal(1154.25),new BigDecimal(216.51),new BigDecimal(2622.21));
//		
//		
//		service.addOrder(o1);
//		service.addOrder(o2);
		
		

		
		//edit order
//		Order editOrder = new Order(2, "Doctor Whommmm","WA",new BigDecimal(9.25),"Tile",new BigDecimal(243.55),new BigDecimal(5.15),new BigDecimal(4.75),new BigDecimal(1251.45),new BigDecimal(1154.25),new BigDecimal(216.51),new BigDecimal(2622.21));
//		service.editOrder(editOrder);
		
		// get order
//		LocalDate date = LocalDate.of(2023,06,17);
//		Order selectedOrder = new Order();
//		
//		try {
//			selectedOrder = service.getOrder(date, 2);
//		} catch (DataPersistenceException e) {
//			e.printStackTrace();
//		} catch (InvalidOrderException e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println(selectedOrder);
		
		//getordersByDate
//		LocalDate date = LocalDate.of(2023,06,17);
//
//		
//		try {
//			orderList = service.getOrdersByDate(date);
//		} catch (DataPersistenceException e) {
//		
//			e.printStackTrace();
//		} catch (InvalidOrderException e) {
//			
//			e.printStackTrace();
//		}
//		System.out.println(orderList);
		
		//remove order
//		service.removeOrder(date, 1);
//		try {
//			orderList = service.getOrdersByDate(date);
//		} catch (DataPersistenceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvalidOrderException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(orderList);
		
		int choice;
		do {
			System.out.println("\n   * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
			System.out.println("* <<Flooring Program>>");
			System.out.println("* 1. Display Orders");
			System.out.println("* 2. Add an Order");
			System.out.println("* 3. Edit an Order");
			System.out.println("* 4. Remove an Order");
			System.out.println("* 5. Export All Data");
			System.out.println("* 6. Quit");
			System.out.println("\n   * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
			
			choice = sc.nextInt();
			switch (choice) {
				case 1:
					displayOrders();
					break;
				case 2:
					addOrder();
					break;
				case 3:
					editOrder();
					break;
				case 4:
					removeOrder();
					break;
				case 5:
					exportData();
					break;
				case 6:
					break;
				default:
					System.out.println("Invalid Option! Please enter again");
					break;
			}
		} while (choice !=6);
		
		System.out.println("Thank you for using Flooring Program!");
	}	

}
