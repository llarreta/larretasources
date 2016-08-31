package co.com.directv.sdii.facade.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.MediaContactTypeVO;

/**
 * 
 * Interfaz que define la Session Facade de las operaciones 
 * a realizar para el modulo de MediaContactTypes
 * Solo operaciones de consulta.
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface MediaContactTypesFacadeBeanLocal {

	/**
     * 
     * Metodo: Obtiene los tipos de contacto por identificador
     * @param id identificador de los tipos de contacto
     * @return información del tipo de contacto cuyo id coicide con el especificado
     * @throws BusinessException en caso de error al ejecutar la operación
     * @author jalopez
     */
	public MediaContactTypeVO getMediaContactTypesByID(Long id) throws BusinessException;
	
	/**
     * 
     * Metodo: Obtiene todos los medios de contacto registrados en el sistema
     * @return Lista con los medios de contacto registrados en el sistema
     * @throws BusinessException en caso de error al ejecutar la operación
     * @author jalopez
     */
	public List<MediaContactTypeVO> getAllMediaContactTypes() throws BusinessException;
}
