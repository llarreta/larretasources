package co.com.directv.sdii.facade.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DocumentTypeVO;


/**
 * 
 * Interfaz que define la Session Facade de las operaciones 
 * a realizar para el modulo de DocumentTypes
 * Solo operaciones de consulta.
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DocumentTypesFacadeBeanLocal {

	public DocumentTypeVO getDocumentTypesByID(Long id) throws BusinessException;

	/**
	 * Obtiene una lista regionalizada de los tipos de documentos de personas
	 * @param countryId identificador del país sobre el que se realizará el filtro
	 * @return Lista de tipos de documento de identificación de acuerdo al país
	 * @throws BusinessException en caso de error al tratar de obtener la lista
	 * @author jjimenezh Agregado por control de cambios 2010-04-23
	 */
	public List<DocumentTypeVO> getAllDocumentTypesByCountryId(Long countryId) throws BusinessException;
	
}
