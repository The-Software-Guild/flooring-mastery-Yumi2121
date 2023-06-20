package com.flooringmastery.service;

import com.flooringmastery.dto.Order;
import com.flooringmastery.dao.OrderDao;
import com.flooringmastery.dao.ProductDao;
import com.flooringmastery.dao.TaxDao;
import com.flooringmastery.dao.FlooringMasteryOrderDao;
import com.flooringmastery.dao.FlooringMasteryProductDao;
import com.flooringmastery.dao.FlooringMasteryTaxDao;
import com.flooringmastery.service.FlooringMasteryService;
import com.flooringmastery.exception.InvalidOrderException;

import java.time.LocalDate;
import java.time.Month;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.math.BigDecimal;
import static org.junit.Assert.*;

public class FlooringMasteryServiceTest {
	
	private IService service;
	
	OrderDao dao1 = new FlooringMasteryDaoStubImpl();
	ProductDao dao2 = new FlooringMasteryProductDao();
	TaxDao dao3 = new FlooringMasteryTaxDao();
	
	service = new FlooringMasteryService (dao1, dao2, dao3); 
	
	LocalDate date = LocalDate.of(2023, Month.JUNE, 20);
	
	@Test 
	public void testGetOrdersByDate() {
		assertEquals(3, service.getOrdersByDate(date).size());
		
		try {
			List<Order> orders = service.getOrdersByDate(LocalDate.of(2013, Month.JUNE, 20));
			fail("Expected InvalidOrderNumberException was not thrown.");
		} catch (InvalidOrderException e) {
			
		}
		
	}
	
	@Test
	public void testCalculateCost() throws Exception {
		Order order1 = new Order();
		order1.setCustomerName("Anna");
		order1.setState("CA");
		order1.setProductType("Wood");
		order1.setArea(new BigDecimal("100"));
		
		Order order2 = new Order();
		order2.setCustomerName("Anna");
		order2.setState("CA");
		order2.setProductType("Wood");
		order2.setArea(new BigDecimal("100"));
		
		assertEquals(service.calculateCost(order1), service.calculateCost(order2));
		
		order1.setCustomerName("");

        try {
            service.calculateCost(order1);
            fail("Expected OrderValidationException was not thrown.");
        } catch (InvalidOrderException e) {
        	}
	        
	     
        order1.setCustomerName("Place LLC");
        order1.setState("");

        try {
            service.calculateCost(order1);
            fail("Expected OrderValidationException was not thrown.");
        } catch (InvalidOrderException e) {
        }
	}
	
	
}
