package co.com.directv.sdii.persistence.dao.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ContractType;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad ContractTypes
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ContractTypesDAOLocal {

	/**
	 * 
	 * @param code
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public ContractType getContractTypesByCode(String code) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public ContractType getContractTypesByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<ContractType> getAllContractTypes() throws DAOServiceException, DAOSQLException;

	/**
	 * Obtiene un listado de tipos de contrato regionalizado
	 * @param countryId identificador del país
	 * @return lista de los tipos de contrato por el país especificado
	 * @throws DAOServiceException en caso de error al tratar de obtener la lista de los países
	 * @throws DAOSQLException en caso de error al tratar de obtener la lista de los países
	 * @author jjimenezh Agergado por control de cambios 2010-04-23
	 */
	public List<ContractType> getAllContractTypesByCountryId(Long countryId) throws DAOServiceException, DAOSQLException;

}