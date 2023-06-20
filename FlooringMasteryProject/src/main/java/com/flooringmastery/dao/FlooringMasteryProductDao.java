 package com.flooringmastery.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.flooringmastery.dto.Product;
import com.flooringmastery.exception.DataPersistenceException;

@Component
public class FlooringMasteryProductDao implements ProductDao, Serializable {
	
	private static final String PRODTCT_FILE = "Products.txt";
	private static final String DELIMITER = ",";
	List<Product> productList = new ArrayList<>();

	@Override
	public Product getProduct(String productType) throws DataPersistenceException {
		List<Product> productList = loadProducts();
		if (productList == null) {
			return null;
		} else {
			Product selectProduct = productList.stream()
					.filter(p-> p.getProductType().equalsIgnoreCase(productType))
					.findFirst().orElse(null);
			return selectProduct;
		}
		
	}
	
	@Override
	public List<Product> loadProducts() throws DataPersistenceException {
	
		Scanner scanner = null;

		try {
			scanner = new Scanner(
					new BufferedReader(
							new FileReader(PRODTCT_FILE)));
		}
		catch (Exception e) {
			throw new DataPersistenceException("Failed to load product data!");
		}
		
		String currentline;
		String[] currentProduct;
		
		
		scanner.nextLine(); //Skips scanning document headers 
		while (scanner.hasNextLine()) {
			currentline = scanner.nextLine();
			
			currentProduct = currentline.split(DELIMITER);
			if (currentProduct.length == 3) {
				Product product = new Product();
				
				product.setProductType(currentProduct[0]);
				product.setCostPerSquareFoot(new BigDecimal(currentProduct[1]));
				product.setLaborCostPerSquareFoot(new BigDecimal(currentProduct[2]));
				
				productList.add(product);
				
				} 
			}
		
		scanner.close();
		
	
		return productList;
		
	
	}

}
