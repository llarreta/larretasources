package co.com.directv.sdii.persistence.dao.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.DocumentType;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad DocumentTypes
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DocumentTypesDAOLocal {

	
	public DocumentType getDocumentTypesByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Obtiene una lista regionalizada de los tipos de documentos de personas
	 * @param countryId identificador del país sobre el que se realizará el filtro
	 * @return Lista de tipos de documento de identificación de acuerdo al país
	 * @throws BusinessException en caso de error al tratar de obtener la lista
	 * @author jjimenezh Agregado por control de cambios 2010-04-23
	 */
	public List<DocumentType> getAllDocumentTypesByCountryId(Long countryId) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene un tipo de identificación por el código y el país
	 * @param documentTypeCode código del tipo de documento
	 * @param countryId identificador del país
	 * @return tipo de documento, nulo en caso que no se encuentre
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jjimenezh
	 */
	public DocumentType getDocumentTypesByCodeAndCountryId(String documentTypeCode, Long countryId)throws DAOServiceException, DAOSQLException;

}