package co.com.directv.sdii.common.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.EJBRemoteJNDINameEnum;
import co.com.directv.sdii.ejb.business.config.SystemParameterBusinessBeanRemote;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.SystemParameter;


/**
 * Fecha de Creacion: Dic 6, 2012
 * @author  Carlos Lopez.
 * @version 1.0
 * @see
 */

public class ResourceBundleReport {

	
	/**
	 * Comment for <code>instance</code> Instancia creada mediante el patrÃ³n
	 * singleton.
	 */
	private static  ResourceBundleReport instance = null;
	
	
	private static ResourceBundle applicationData;

	/**
	 * Constructor 
	 * @throws PropertiesException
	 */
	public ResourceBundleReport() throws PropertiesException {
		
	}


	/**
	 * Obtiene la instancia singleton con el nombre de archivo por defecto
	 * @return instance
	 * @throws PropertiesException
	 */
	public static ResourceBundleReport getInstance() throws PropertiesException {
		if (instance == null) {
			instance = new ResourceBundleReport();
			
		}
		return instance;

	}
	
	/**
	 * Metodo: Retorna una instancia del PropertiesReader desde un directorio
	 * del sistema
	 * @return ResourceBundle
	 * @throws PropertiesException 
	 */
	public ResourceBundle getResourceBundle() throws PropertiesException {

		if(applicationData == null){
				loadConfiguration();
			}
		return applicationData;
	}

	/**
	 * Obtiene el valor de ina propiedad definida en el archivo.
	 * @param key
	 *            Nombre de la propiedad definida en el archivo.
	 * @return
	 */
	public String getAppKey(String key) throws DAOServiceException {
		String s = null;
			if(applicationData == null){
				loadConfiguration();
			}
			s = (String) applicationData.getString(key);
		return s;
	}

	/**
	 * Carga las prpiedades definidas en el archivo de configuraciÃ³n
	 * @throws BusinessException 
	 * @throws LogicException
	 */
	private void loadConfiguration() {
		try {
			//carga variables para reportes
			InitialContext context=new InitialContext();
			String realEjbJndiName = EJBRemoteJNDINameEnum.SystemParameterBusinessBeanLocal.getCodeEntity()+"#"+SystemParameterBusinessBeanRemote.class.getName();
			SystemParameterBusinessBeanRemote systemParameterBusinessBean = (SystemParameterBusinessBeanRemote)context.lookup(realEjbJndiName);
			
			SystemParameter sysParamsLang = systemParameterBusinessBean.getSysParamsByCodeAndCountryIdNull(CodesBusinessEntityEnum.SYSTEM_PARAM_LANGUAGE.getCodeEntity());
			Locale[] locales = new Locale[] {new Locale(sysParamsLang.getValue())};

			String baseNameReport = new String(PropertiesReader.getInstance().getAppKey(Constantes.BASE_NAME_REPORT));
  
			String pathPropertiesi18n = new String(PropertiesReader.getInstance().getAppKey(Constantes.PROPERTIES_APP_I18N));
			File file = new File(pathPropertiesi18n);
			URL[] urls = {file.toURI().toURL()};  
			ClassLoader loader = new URLClassLoader(urls);
			
			applicationData = ResourceBundle.getBundle(baseNameReport, locales[0], loader);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (BusinessException ex) {
			ex.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (PropertiesException e) {
			e.printStackTrace();
		} 
		
	}

	/**
	 * Refresca la configuraciÃ³n definida
	 * @throws LogicException
	 */
	public void reloadConfiguration() throws PropertiesException {
		this.loadConfiguration();
	}
	
}
