/*
 * 
 */
package co.com.directv.sdii.common.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import co.com.directv.sdii.exceptions.PropertiesException;


/**
 * 
 * Clase encargada de centralizar la lectura de
 * archivos de propiedades
 * 
 * Fecha de Creación: Mar 1, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class PropertiesReader {

	/**
	 * Comment for <code>instance</code> Instancia creada mediante el patrón
	 * singleton.
	 */
	static private PropertiesReader instance = null;
	private static String APPLICATION_FILE_NAME = Constantes.RESOURCE_APP;
	
	private static Properties applicationData;

	/**
	 * Comment for <code>data</code> Objeto que encapsula las propiedades
	 * definidas en el archivo.
	 */
	private Properties data = new Properties();

	/**
	 * Constructor que recibe un nombre de archivo de propiedades como
	 * parámetro.
	 *
	 * @param propsFile
	 *            Nombre del archivo de propiedades
	 * @throws LogicException
	 */
	protected PropertiesReader() throws PropertiesException {
		
	}

	

	/**
	 * Obtiene la instancia singleton con el nobre de archivo por defecto
	 * definido en la interfaz PropertyConstants.
	 *
	 * @return
	 * @throws LogicException
	 */
	public static PropertiesReader getInstance() throws PropertiesException {
		if (instance == null) {
			instance = new PropertiesReader();
		}
		return instance;

	}
	
	/**
	 *
	 * Metodo: Retorna una instancia del PropertiesReader desde un directorio
	 * del sistema
	 * @param propsFile
	 * @return PropertiesReader
	 * @throws PropertiesException 
	 * @author jalopez
	 */
	public PropertiesReader getInstanceFileSystem(String propsFile)throws PropertiesException {
		PropertiesReader result = null;
		try {
			if(applicationData == null){
				loadConfiguration();
			}
			
			String propertiesFile = applicationData.getProperty(propsFile);
			result = new PropertiesReader();
			
			FileInputStream propertiesFileSystem = new FileInputStream(propertiesFile);
			result.data.load(propertiesFileSystem);
			propertiesFileSystem.close();
			propertiesFileSystem = null;
			
		} catch (Exception e) {
			throw new PropertiesException("Error obteniendo instancia PropertiesReader ["+propsFile+"]",e);
		}
		return result;
	}

	/**
	 * Obtiene el valor de ina propiedad definida en el archivo.
	 *
	 * @param key
	 *            Nombre de la propiedad definida en el archivo.
	 * @return
	 */
	public String getKey(String key) {
		String s = (String) data.get(key);
		return s;
	}
	
	
	public String getAppKey(String key) {
		String s = null;
		try {
			if(applicationData == null){
				loadConfiguration();
			}
			s = (String) applicationData.get(key);
		} catch (PropertiesException e) {
			e.printStackTrace();
		}
		return s;	}
	
	/**
	 * Metodo: Obtiene la lista de las llaves que se encuentran en el
	 * archivo de propiedades
	 * @return Lista de llaves del archivo de propiedades
	 * @author jjimenezh
	 */
	public List<String> getKeys(){
		Set<Object> keySet = data.keySet();
		List<String> result = new ArrayList<String>();
		for (Object object : keySet) {
			result.add(object.toString());
		}
		return result;
	}

	/**
	 * Carga las prpiedades definidas en el archivo de configuración
	 *
	 * @throws LogicException
	 */
	private void loadConfiguration() throws PropertiesException {
		try {
			applicationData = new Properties();
			applicationData.load(getClass().getResourceAsStream(APPLICATION_FILE_NAME));

		} catch (FileNotFoundException e) {
			throw new PropertiesException("Error abriendo archivo de propiedades ["+APPLICATION_FILE_NAME+"]",e);
		} catch (IOException e) {
			throw new PropertiesException("Error abriendo archivo de propiedades ["+APPLICATION_FILE_NAME+"]",e);
		}
	}

	/**
	 * Refresca la configuración definida
	 *
	 * @throws LogicException
	 */
	public void reloadConfiguration() throws PropertiesException {
		this.loadConfiguration();
	}
	
}
