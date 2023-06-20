package com.flooringmastery.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import org.springframework.stereotype.Component;

@Component
public class Order implements Serializable {
	Tax tax = new Tax();
	Product product = new Product();
	
	private int orderNumber;
	private String customerName;
	private String state = tax.getStateAbbreviation();
	private BigDecimal taxRate = tax.getTaxRate();
	private String productType = product.getProductType();
	private BigDecimal area;
	private BigDecimal costPerSquareFoot = product.getCostPerSquareFoot();
	private BigDecimal laborCostPerSquareFoot = product.getLaborCostPerSquareFoot();
	private BigDecimal materialCost;
	private BigDecimal laborCost ;
	private BigDecimal totalTax;
	private BigDecimal totalCost;
	private LocalDate timeStamp;
	
	
	public Order() {};
	
	public Order(String customerName, String state, BigDecimal taxRate, String productType,
			BigDecimal area, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot, BigDecimal materialCost,
			BigDecimal laborCost, BigDecimal totalTax, BigDecimal totalCost) {
		super();
		this.orderNumber = orderNumber;
		this.customerName = customerName;
		this.state = state;
		this.taxRate = taxRate;
		this.productType = productType;
		this.area = area;
		this.costPerSquareFoot = costPerSquareFoot;
		this.laborCostPerSquareFoot = laborCostPerSquareFoot;
		this.materialCost = materialCost;
		this.laborCost = laborCost;
		this.totalTax = totalTax;
		this.totalCost = totalCost;
		this.timeStamp = LocalDate.now();
	}
	
	public Order(int orderNumber, String customerName, String state, BigDecimal taxRate, String productType,
			BigDecimal area, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot, BigDecimal materialCost,
			BigDecimal laborCost, BigDecimal totalTax, BigDecimal totalCost) {
		super();
		this.orderNumber = orderNumber;
		this.customerName = customerName;
		this.state = state;
		this.taxRate = taxRate;
		this.productType = productType;
		this.area = area;
		this.costPerSquareFoot = costPerSquareFoot;
		this.laborCostPerSquareFoot = laborCostPerSquareFoot;
		this.materialCost = materialCost;
		this.laborCost = laborCost;
		this.totalTax = totalTax;
		this.totalCost = totalCost;
		this.timeStamp = LocalDate.now();
	}



	public Tax getTax() {
		return tax;
	}

	public void setTax(Tax tax) {
		this.tax = tax;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getOrderNumber() {
		return orderNumber;
	}
	
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public BigDecimal getTaxRate() {
		return taxRate;
	}


	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}


	public String getProductType() {
		return productType;
	}


	public void setProductType(String productType) {
		this.productType = productType;
	}


	public BigDecimal getArea() {
		return area;
	}


	public void setArea(BigDecimal area) {
		this.area = area;
	}


	public BigDecimal getCostPerSquareFoot() {
		return costPerSquareFoot;
	}


	public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
		this.costPerSquareFoot = costPerSquareFoot;
	}


	public BigDecimal getLaborCostPerSquareFoot() {
		return laborCostPerSquareFoot;
	}


	public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
		this.laborCostPerSquareFoot = laborCostPerSquareFoot;
	}


	public BigDecimal getMaterialCost() {
		return materialCost;
	}


	public void setMaterialCost(BigDecimal materialCost) {
		this.materialCost = materialCost;
	}


	public BigDecimal getLaborCost() {
		return laborCost;
	}


	public void setLaborCost(BigDecimal laborCost) {
		this.laborCost = laborCost;
	}


	public BigDecimal getTotalTax() {
		return totalTax;
	}


	public void setTotalTax(BigDecimal totalTax) {
		this.totalTax = totalTax;
	}


	public BigDecimal getTotalCost() {
		return totalCost;
	}


	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}


	public LocalDate getTimeStamp() {
		return timeStamp;
	}


	public void setTimeStamp(LocalDate timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Override
	public String toString() {
		return "Order [orderNumber=" + orderNumber + ", customerName=" + customerName + ", state=" + state
				+ ", taxRate=" + taxRate + ", productType=" + productType + ", area=" + area + ", costPerSquareFoot="
				+ costPerSquareFoot + ", laborCostPerSquareFoot=" + laborCostPerSquareFoot + ", materialCost="
				+ materialCost + ", laborCost=" + laborCost + ", totalTax=" + totalTax + ", totalCost=" + totalCost + ", timeStamp="
				+ timeStamp + "]";
	}
	
	
	 @Override
	    public int hashCode() {
	        int hash = 5;
	        hash = 53 * hash + Objects.hashCode(this.tax);
	        hash = 53 * hash + Objects.hashCode(this.product);
	        hash = 53 * hash + Objects.hashCode(this.orderNumber);
	        hash = 53 * hash + Objects.hashCode(this.customerName);
	        hash = 53 * hash + Objects.hashCode(this.state);
	        hash = 53 * hash + Objects.hashCode(this.taxRate);
	        hash = 53 * hash + Objects.hashCode(this.productType);
	        hash = 53 * hash + Objects.hashCode(this.area);
	        hash = 53 * hash + Objects.hashCode(this.costPerSquareFoot);
	        hash = 53 * hash + Objects.hashCode(this.laborCostPerSquareFoot);
	        hash = 53 * hash + Objects.hashCode(this.materialCost);
	        hash = 53 * hash + Objects.hashCode(this.laborCost);
	        hash = 53 * hash + Objects.hashCode(this.totalTax);
	        hash = 53 * hash + Objects.hashCode(this.totalCost);
	        hash = 53 * hash + Objects.hashCode(this.timeStamp);
	        return hash;
	    }

	    @Override
	    public boolean equals(Object obj) {
	        if (this == obj) {
	            return true;
	        }
	        if (obj == null) {
	            return false;
	        }
	        if (getClass() != obj.getClass()) {
	            return false;
	        }
	        final Order other = (Order) obj;
	        if (this.orderNumber != other.orderNumber) {
	            return false;
	        }
	        if (!Objects.equals(this.customerName, other.customerName)) {
	            return false;
	        }
	        if (!Objects.equals(this.state, other.state)) {
	            return false;
	        }
	        if (!Objects.equals(this.productType, other.productType)) {
	            return false;
	        }
	        if (!Objects.equals(this.tax, other.tax)) {
	            return false;
	        }
	        if (!Objects.equals(this.product, other.product)) {
	            return false;
	        }
	        if (!Objects.equals(this.taxRate, other.taxRate)) {
	            return false;
	        }
	        if (!Objects.equals(this.area, other.area)) {
	            return false;
	        }
	        if (!Objects.equals(this.costPerSquareFoot, other.costPerSquareFoot)) {
	            return false;
	        }
	        if (!Objects.equals(this.laborCostPerSquareFoot, other.laborCostPerSquareFoot)) {
	            return false;
	        }
	        if (!Objects.equals(this.materialCost, other.materialCost)) {
	            return false;
	        }
	        if (!Objects.equals(this.laborCost, other.laborCost)) {
	            return false;
	        }
	        if (!Objects.equals(this.totalTax, other.totalTax)) {
	            return false;
	        }
	        if (!Objects.equals(this.totalCost, other.totalCost)) {
	            return false;
	        }
	        if (!Objects.equals(this.timeStamp, other.timeStamp)) {
	            return false;
	        }
	        return true;
	    }
	
	
	
}
