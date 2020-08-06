package com.haleem.ecommerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;

import com.haleem.ecommerce.entity.Product;
import com.haleem.ecommerce.entity.ProductCategory;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		
		HttpMethod[] theUnsupportedActions = { HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE };
		
		// Disable HTTP methods for Product: PUT, POST, DELETE
		config.getExposureConfiguration()
				.forDomainType(Product.class)
				.withItemExposure((metadate, httpMethods) -> httpMethods.disable(theUnsupportedActions))
				.withCollectionExposure((metadate, httpMethods) -> httpMethods.disable(theUnsupportedActions));
		
		config.getExposureConfiguration()
				.forDomainType(ProductCategory.class)
				.withItemExposure((metadate, httpMethods) -> httpMethods.disable(theUnsupportedActions))
				.withCollectionExposure((metadate, httpMethods) -> httpMethods.disable(theUnsupportedActions));
	}

	
}
