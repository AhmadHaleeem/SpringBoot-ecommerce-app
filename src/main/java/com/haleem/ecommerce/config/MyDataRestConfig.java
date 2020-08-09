package com.haleem.ecommerce.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;

import com.haleem.ecommerce.entity.Country;
import com.haleem.ecommerce.entity.Product;
import com.haleem.ecommerce.entity.ProductCategory;
import com.haleem.ecommerce.entity.State;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

	private EntityManager entityManager;
	
	@Autowired
	public MyDataRestConfig(EntityManager entityManager) {
		this.entityManager = entityManager;
	}



	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		
		HttpMethod[] theUnsupportedActions = { HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE };
		
		// Disable HTTP methods for Product: PUT, POST, DELETE
		disableHttpMethods(Product.class, config, theUnsupportedActions);
		
		disableHttpMethods(ProductCategory.class, config, theUnsupportedActions);
		
		disableHttpMethods(Country.class, config, theUnsupportedActions);
		
		disableHttpMethods(State.class, config, theUnsupportedActions);
	
		// call an internal helper method
		eposeIds(config);
	}



	private void disableHttpMethods(Class theCalss, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
		config.getExposureConfiguration()
				.forDomainType(theCalss)
				.withItemExposure((metadate, httpMethods) -> httpMethods.disable(theUnsupportedActions))
				.withCollectionExposure((metadate, httpMethods) -> httpMethods.disable(theUnsupportedActions));
	}



	private void eposeIds(RepositoryRestConfiguration config) {
		
		// expose entity ids
		//
		
		// get a list of all entity classes from the entity manager
		Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
		// create an array of the entity types
		List<Class> entityClasses = new ArrayList<>();
		// get the entity types for the entities
		for (EntityType tempEntityType : entities) {
			entityClasses.add(tempEntityType.getJavaType());
		}
		// expose the entity ids for the array of entity/domain types
		Class[] domainTypes = entityClasses.toArray(new Class[0]);
		config.exposeIdsFor(domainTypes);
	}

	
}
