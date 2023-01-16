package com.asherl.springbootlibrary.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.asherl.springbootlibrary.entity.Book;

@Configuration
public class MyDataRestConfigClass implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod[] theUnsupportedActions = {
                HttpMethod.POST,
                HttpMethod.PATCH,
                HttpMethod.DELETE };
        config.exposeIdsFor(Book.class);
        disableHttpMethods(Book.class, config, theUnsupportedActions);


        /*Configure CORS Mapping */
        String theAllowedOrigins = "http://localhost:3000";
        cors.addMapping(config.getBasePath()+ "/**")
                .allowedOrigins(theAllowedOrigins);
    }

    private void disableHttpMethods(Class<Book> theClass, RepositoryRestConfiguration config,
            HttpMethod[] theUnsupportedActions) {
            config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }

}


