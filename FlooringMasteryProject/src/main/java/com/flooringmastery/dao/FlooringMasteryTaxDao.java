package com.flooringmastery.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.flooringmastery.dto.Product;
import com.flooringmastery.dto.Tax;
import com.flooringmastery.exception.DataPersistenceException;

@Component
public class FlooringMasteryTaxDao implements TaxDao, Serializable {
	
	private static final String TAX_FILE = "Taxes.txt";
	private static final String DELIMITER = ",";
	List<Tax> taxList = new ArrayList<>();
	BigDecimal taxRate = BigDecimal.ZERO;

	
//	public BigDecimal getTaxRate(String stateAbbreviation) throws DataPersistenceException {
//		List<Tax> taxList = loadTaxes();
//		
//		
//		if (taxList == null) {
//			return null;
//		} else {
//			Tax selectedStateTax = taxList.stream()
//					.filter(p-> p.getStateAbbreviation().equalsIgnoreCase(stateAbbreviation))
//					.findFirst().orElse(null);
//			taxRate = selectedStateTax.getTaxRate();
//			return taxRate;
//		}
//	
//	}
	
	@Override
	public List<Tax> loadTaxes() throws DataPersistenceException {
		
		Scanner scanner = null;

		try {
			scanner = new Scanner(
					new BufferedReader(
							new FileReader(TAX_FILE)));
		}
		catch (Exception e) {
			throw new DataPersistenceException("Failed to load tax data!");
		}
		
		String currentline;
		String[] currentTax;
		
		
		scanner.nextLine(); //Skips scanning document headers 
		while (scanner.hasNextLine()) {
			currentline = scanner.nextLine();
			
			currentTax = currentline.split(DELIMITER);
			if (currentTax.length == 3) {
				Tax tax = new Tax();
				
				tax.setStateAbbreviation(currentTax[0]);
				tax.setStateName(currentTax[1]);
				tax.setTaxRate(new BigDecimal(currentTax[2]));
				
				taxList.add(tax);
				
				} 
			}
		
		scanner.close();
		
	
		return taxList;
	
	}


}
