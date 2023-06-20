package com.flooringmastery.dto;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class Tax {
	private String stateAbbreviation;
	private String stateName;
	private BigDecimal taxRate;
	
	public Tax() {};
	
	public Tax(String stateAbbreviation, String stateName, BigDecimal taxRate) {
		super();
		this.stateAbbreviation = stateAbbreviation;
		this.stateName = stateName;
		this.taxRate = taxRate;
	}


	public String getStateAbbreviation() {
		return stateAbbreviation;
	}
	
	public void setStateAbbreviation(String stateAbbreviation) {
		this.stateAbbreviation = stateAbbreviation;
	}
	
	public String getStateName() {
		return stateName;
	}
	
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
	public BigDecimal getTaxRate() {
		return taxRate;
	}
	
	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}
	
	
}
