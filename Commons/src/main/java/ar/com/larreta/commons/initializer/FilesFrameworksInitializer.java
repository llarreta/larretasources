package ar.com.larreta.commons.initializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import ar.com.larreta.commons.AppObject;
import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.commons.utils.iterators.IterateResourcesProperties;
import ar.com.larreta.commons.utils.iterators.PropertyAction;

public class FilesFrameworksInitializer extends GenericServlet {

	private static final String COPY_PROPERTIES = "copy.properties";
	private AppObject appObject = new AppObjectImpl(getClass());
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		appObject.getLog().debug("Comenzando a copiar archivos de los diferentes frameworks de larreta");

		IterateResourcesProperties iterateResourcesProperties = new IterateResourcesProperties(COPY_PROPERTIES, new PropertyAction() {
			
			@Override
			public void process(String key, String value) {
				String path = getServletContext().getRealPath(value);
				
				appObject.getLog().debug("Copiando:" + key + ", a la siguiente ruta:" + path);
				
				try {
					Files.copy(
						getClass().getClassLoader().getResourceAsStream(key), 
						Paths.get(path), 
						StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					appObject.getLog().error("Ocurrio un error copiando archivos", e);
				}
				
			}
		});
		
		iterateResourcesProperties.start();
	}


	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		// No hacemos nada
	}

}
