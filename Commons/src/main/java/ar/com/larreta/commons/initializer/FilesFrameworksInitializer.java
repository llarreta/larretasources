package ar.com.larreta.commons.initializer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import ar.com.larreta.commons.AppObject;
import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.commons.utils.iterators.IterateResourcesProperties;
import ar.com.larreta.commons.utils.iterators.PropertyAction;

public class FilesFrameworksInitializer extends GenericServlet {

	private static final String COPY_PROPERTIES = "copy.properties";
	private AppObject appObject = new AppObjectImpl(getClass());
	
	private PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		appObject.getLog().debug("Comenzando a copiar archivos de los diferentes frameworks de larreta");

		IterateResourcesProperties iterateResourcesProperties = new IterateResourcesProperties(COPY_PROPERTIES, new PropertyAction() {

			private Boolean createDir = Boolean.TRUE;
			
			@Override
			public void process(String key, String value) {
				String path = getServletContext().getRealPath(value);
				
				appObject.getLog().debug("Copiando:" + key + ", a la siguiente ruta:" + path);
				
				Path pathObject = Paths.get(path);
				try {
					Files.copy(
						resolver.getResources(key)[0].getInputStream(), 
						pathObject, 
						StandardCopyOption.REPLACE_EXISTING);
					createDir = Boolean.TRUE;
				} catch (IOException e) {
					if (createDir){
						String dirPath = path.substring(0, path.lastIndexOf(File.separator));
						File file = new File(dirPath);
						file.mkdirs();
						createDir = Boolean.FALSE;
						process(key, value);
					} else {
						appObject.getLog().error("Ocurrio un error copiando archivos", e);
					}
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
