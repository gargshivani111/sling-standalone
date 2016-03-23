package com.ttnd.crud;

import java.io.IOException;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NewsUpdateServlet is invoked on update operation of content and also when a news page is deleted.
 *
 */
@SlingServlet(paths = { "/services/newsupdate" }, generateComponent = false)
@Component(metatype = false)
public class NewsUpdateDeleteServlet extends SlingSafeMethodsServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(NewsUpdateDeleteServlet.class);

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {
		updateDeleteContent(request, response);
	}
	
	public void updateDeleteContent(SlingHttpServletRequest request, SlingHttpServletResponse response) {
		CrudOperations crudOperations = new CrudOperations();
		String title = request.getParameter("title") != null ? Jsoup.parse(request.getParameter("title")).text() : null;		
		String desc = request.getParameter("desc") !=null ? Jsoup.parse(request.getParameter("desc")).text() : null;
		String[] path = request.getParameter("path").split(".html");
		ResourceResolver resourceResolver = request.getResourceResolver();
		
		if(title != null){
			crudOperations.update(resourceResolver, path[0],"jcr:title",title);
			LOGGER.info("Updated Title -- {}",title);
		}
		else if(desc != null){ 
			crudOperations.update(resourceResolver, path[0],"jcr:description",desc);
			LOGGER.info("Updated Desc -- {}",desc);
		}else{
			if(crudOperations.delete(resourceResolver, path[0])){
				try {
					response.getWriter().write("success");
				} catch (IOException e) {
					LOGGER.error("IO Exception");
				}
			}
		}
	}
}


