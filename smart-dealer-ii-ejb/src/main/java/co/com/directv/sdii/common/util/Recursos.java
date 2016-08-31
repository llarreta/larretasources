package co.com.directv.sdii.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Clase estática que mantiene la referencia a los recursos de propiedades de la
 * aplicación SmartDealer II
 * 
 * @author Jimmy Vélez Muñoz
 */
public final class Recursos {

	private Recursos(){}
	
	// Recurso properties de la aplicacion (interno)
	private static ResourceBundle resourceApp;

	// Recurso properties de configuracion (externo)
	private static ResourceBundle resourceConf;

	static {
		resourceApp = ResourceBundle.getBundle(Constantes.RESOURCE_APP);
		try {
			resourceConf = new PropertyResourceBundle(new FileInputStream(
					new File(resourceApp.getString(Constantes.PROPERTIES_APP),
							Constantes.RESOURCE_CONFIGURATION)));
		} catch (Throwable e) {
			System.out.println("=== Error Leyendo Propiedades de SmartDealer II ===");
		}
	}

	/**
	 * Retorna el recurso de la aplicacion
	 * 
	 * @return Properties
	 */
	public static ResourceBundle getBundleAplicacion() {
		return resourceApp;
	}

	/**
	 * Retorna el recurso de configuracion
	 * 
	 * @return Properties
	 */
	public static ResourceBundle getBundleConfiguracion() {
		return resourceConf;
	}
}
