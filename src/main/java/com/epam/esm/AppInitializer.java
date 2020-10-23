package com.epam.esm;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * Class for DispatcherServlet init
 * @author Marianna Patrusova
 * @version 1.0
 */
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{AppMvcConfig.class, DataSourceConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{AppMvcConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(AppMvcConfig.class);

        ServletRegistration.Dynamic servlet =
                servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
        servlet.addMapping("/");
        servlet.setLoadOnStartup(1);

        servlet.setInitParameter("contextClass", ctx.getClass().getName());
        servletContext.addListener(new ContextLoaderListener(ctx));

        //character encoding filter
        FilterRegistration.Dynamic filterRegistration =
                servletContext.addFilter("characterEncodingFilter", new CharacterEncodingFilter());
        filterRegistration.setInitParameter("encoding", "UTF-8");
        filterRegistration.setInitParameter("forceEncoding", "true");
        filterRegistration.addMappingForUrlPatterns(null, true, "/*");
    }

}
