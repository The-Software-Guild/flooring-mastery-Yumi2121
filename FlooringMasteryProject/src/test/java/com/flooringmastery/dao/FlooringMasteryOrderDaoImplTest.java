package com.flooringmastery.dao;

import com.flooringmastery.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class FlooringMasteryOrderDaoImplTest {
	OrderDao dao = new FlooringMasteryOrderDao();
    private Order firstOrder;
    private Order secondOrder;
    private List<Order> orderList = new ArrayList<>();
    LocalDate date = LocalDate.of(2023, Month.JUNE, 20);
    
    @Before
    public void setUp() throws Exception {
    	List<Order> orders = dao.searchOrders(date);
    	
//    	for (Order order : orders) {
//    		dao.removeOrder(date, order.getOrderNumber());
//    	}
    }
    
    
    @Test
    public void testAddOrders() throws Exception {
    	firstOrder = order1();
    	secondOrder = order2();
    	
    	assertEquals(firstOrder, dao.getOrder(date, 1));
    	assertEquals(secondOrder, dao.getOrder(date, 2));
    	
    }
    
    @Test
    public void testSearchOrders() throws Exception {
    	firstOrder = order1();
    	secondOrder = order2();
    	
    	orderList = dao.searchOrders(date);
    	assertEquals(3, orderList.size());
    	
    }
    
    @Test
    public void testEditOrders() throws Exception {
    	Order order = order1();
    	order.setCustomerName("TestName");
    	
    	dao.editOrder(order);
    	assertEquals("TestName", order.getCustomerName());
    }
    
    @Test
    public void testRemoveOrders() throws Exception {
    	order1();
    	order2();
    	
    	orderList = dao.searchOrders(date);
    	
    	dao.removeOrder(date, 1);
    	assertEquals(2, orderList.size());
    	
    }
    
    
    public Order order1() throws Exception {

        Order currentOrder = new Order();

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

        dao.addOrder(currentOrder);
        orderList.add(currentOrder);
        return currentOrder;
    }

    public Order order2()throws Exception {

        Order otherOrder = new Order();

        BigDecimal rate = new BigDecimal(0.0675);
        BigDecimal area = new BigDecimal(25);
        BigDecimal costSQ = new BigDecimal(2.25);
        BigDecimal costLabSQ = new BigDecimal(2.10);
        BigDecimal material = new BigDecimal(56.25);
        BigDecimal labor = new BigDecimal(52.50);
        BigDecimal tax = new BigDecimal(7.34);
        BigDecimal total = new BigDecimal(126.09);

        otherOrder.setOrderNumber(1);
        otherOrder.setCustomerName("Bob Smith");
        otherOrder.setState("CA");
        otherOrder.setTaxRate(rate);
        otherOrder.setProductType("Carpet");
        otherOrder.setArea(area);
        otherOrder.setCostPerSquareFoot(costSQ);
        otherOrder.setLaborCostPerSquareFoot(costLabSQ);
        otherOrder.setMaterialCost(material);
        otherOrder.setLaborCost(labor);
        otherOrder.setTotalTax(tax);
        otherOrder.setTotalCost(total);
        otherOrder.setTimeStamp(date);

        dao.addOrder(otherOrder);
        orderList.add(otherOrder);
        return otherOrder;
    }
    
    
    
}
