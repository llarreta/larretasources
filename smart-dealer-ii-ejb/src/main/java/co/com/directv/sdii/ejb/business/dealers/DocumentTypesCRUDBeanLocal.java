package co.com.directv.sdii.ejb.business.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DocumentTypeVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD (Read) de la
 * Entidad DocumentTypes.
 * Solo operaciones de consulta.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DocumentTypesCRUDBeanLocal {

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
