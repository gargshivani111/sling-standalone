package com.ttnd.crud;

import java.util.Map;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrudOperations {
	private static final Logger LOGGER = LoggerFactory.getLogger(CrudOperations.class);

	public void create(ResourceResolver resourceResolver,String path, String nodeName,Map<String, Object> properties) {
		Resource myResource = resourceResolver.getResource(path);
		try {
			resourceResolver.create(myResource, nodeName, properties);
			resourceResolver.commit();
			LOGGER.info("Node Created {}", nodeName);
		} catch (PersistenceException e) {
			LOGGER.error("Persistence Exception : " + e);
		}
	}
	
	public void update(ResourceResolver resourceResolver, String updateNodePath,String property,String value) {
		Resource myResource = resourceResolver.getResource(updateNodePath);
		ModifiableValueMap properties = myResource.adaptTo(ModifiableValueMap.class);		
		properties.put(property,value);
		try {
			resourceResolver.commit();
			LOGGER.info("Content Updated @ {}",updateNodePath);
		} catch (PersistenceException e) {
			LOGGER.error("Persistence Exception : " + e);
		}
	}

	public boolean delete(ResourceResolver resourceResolver, String deletedNodePath) {
		Resource myResource = resourceResolver.getResource(deletedNodePath);
		if (myResource != null) {
			try {
				Node node = myResource.adaptTo(Node.class);
				node.remove();
				resourceResolver.commit();
				LOGGER.info("Node Deleted at {} ", deletedNodePath);
				return true;
			} catch (PersistenceException | RepositoryException e) {
				LOGGER.error("Persistence Exception : " + e);
			}
		}
		return false;
	}


	public void removeProperty(ResourceResolver resourceResolver, String updateNodePath) {
		Resource myResource = resourceResolver.getResource(updateNodePath);
		ModifiableValueMap properties = myResource.adaptTo(ModifiableValueMap.class);
		properties.remove("title");
		try {
			resourceResolver.commit();
			LOGGER.info("Node Property Updated");
		} catch (PersistenceException e) {
			LOGGER.error("Persistence Exception : " + e);
		}
	}

	public void copyResource(ResourceResolver resourceResolver, String sourceResPath, String destinationResPath) {
		Resource myResource = resourceResolver.getResource(sourceResPath);
		Map<String, Object> properties = myResource.adaptTo(ValueMap.class);
		try {
			resourceResolver.create(null, destinationResPath, properties);
			resourceResolver.commit();
			LOGGER.info("Resource Copied");
		} catch (PersistenceException e) {
			LOGGER.error("Persistence Exception : " + e);
		}
	}

	public void moveResource(ResourceResolver resourceResolver, String sourceResPath, String destinationResPath) {
		Resource myResource = resourceResolver.getResource(sourceResPath);
		Map<String, Object> properties = myResource.adaptTo(ValueMap.class);
		try {
			resourceResolver.create(null, destinationResPath, properties);
			resourceResolver.delete(myResource);
			resourceResolver.commit();
			LOGGER.info("Resource Moved");
		} catch (PersistenceException e) {
			LOGGER.error("Persistence Exception : " + e);
		}
	}
}
