package co.com.directv.sdii.facade.test;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;

/**
 * 
 * Interfaz del facade con las operaciones para probar los Web services externos.
 * 
 * Fecha de CreaciÃ³n: feb 02, 2015
 * @author fsanabria <a href="mailto:facundo.sanabria@everis.com">e-mail</a>
 * @version 5.2.2
 * 
 * @see
 */
@Local
public interface ExternalServiceTestFacadeBeanLocal {
	
	/**
	 * Metodo: Obtiene todos los port de los services externos.
	 * @return List<String> 
	 * @throws BusinessException en caso de error al ejecutar la operaciÃ³n
	 * @author
	 */
	public List<String> getAllServicesPort() throws BusinessException;

}
