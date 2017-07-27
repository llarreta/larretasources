package ar.com.larreta.stepper.configs;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import ar.com.larreta.mystic.configs.PersistenceConfig;
import ar.com.larreta.tools.config.FormatConfig;

/**
 * Spring configuration entry point
 * Necesario para que se cargue la configuracion de spring dinamicamente sin necesidad de archivos de configuracion
 */
public class ServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { RestConfig.class, PersistenceConfig.class, FormatConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[0];
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/*" };
    }

    @Override
    public void onStartup(ServletContext context) throws ServletException {
        super.onStartup(context);

        System.setProperty("contextPath", context.getContextPath());
        System.setProperty("realPath", context.getRealPath("/"));
        
        String activeProfile = System.getProperty("spring.profiles.active");
        if (activeProfile == null) {
            activeProfile = "dev"; // or whatever you want the default to be
        }

        context.setInitParameter("spring.profiles.active", activeProfile);

    }


    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setMultipartConfig(getMultipartConfigElement());
        registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
    }

    private MultipartConfigElement getMultipartConfigElement() {
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement( LOCATION, MAX_FILE_SIZE, MAX_REQUEST_SIZE, FILE_SIZE_THRESHOLD);
        return multipartConfigElement;
    }

    private static final String LOCATION = "C:/tools/temp"; // Temporary location where files will be stored

    private static final long MAX_FILE_SIZE = 5242880; // 5MB : Max file size.
                                                        // Beyond that size spring will throw exception.
    private static final long MAX_REQUEST_SIZE = 20971520; // 20MB : Total request size containing Multi part.

    private static final int FILE_SIZE_THRESHOLD = 0; // Size threshold after which files will be written to disk


}
