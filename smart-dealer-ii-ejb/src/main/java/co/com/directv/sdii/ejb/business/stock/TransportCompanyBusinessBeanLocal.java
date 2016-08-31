package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.dto.collection.TransportCompanyDTO;
import co.com.directv.sdii.model.vo.TransportCompanyVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad TransportCompany.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface TransportCompanyBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto TransportCompanyVO
	 * @param obj objeto que encapsula la información de un TransportCompanyVO
	 * @throws BusinessException En caso de error al ejecutar la operación
	 * @author
	 */
	public void createTransportCompany(TransportCompanyVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un TransportCompanyVO
	 * @param obj objeto que encapsula la información de un TransportCompanyVO
	 * @throws BusinessException En caso de error al ejecutar la operación
	 * @author
	 */
	public void updateTransportCompany(TransportCompanyVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un TransportCompanyVO
	 * @param obj información del TransportCompanyVO a ser borrado
	 * @throws BusinessException En caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteTransportCompany(TransportCompanyVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un TransportCompanyVO por su identificador
	 * @param id identificador del TransportCompanyVO a ser consultado
	 * @return objeto con la información del TransportCompanyVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException En caso de error al ejecutar la operación
	 * @author
	 */
	public TransportCompanyVO getTransportCompanyByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los TransportCompanyVO almacenados en la persistencia
	 * @return Lista con los TransportCompanyVO existentes, una lista vacia en caso que no existan TransportCompanyVO en el sistema
	 * @throws BusinessException En caso de error al ejecutar la operación
	 * @author jalopez
	 * @param countryId 
	 */
	public List<TransportCompanyVO> getTransportCompaniesByCountryId(Long countryId) throws BusinessException;

	/**
	 * Metodo: Obtiene la información de todos los TransportCompanyVO almacenados en la persistencia
	 * @param RequestCollectionInfoDTO requestCollInfo, parametros para paginar
	 * @return TransportCompanyDTO, Lista con los TransportCompanyVO existentes, una lista vacia en caso que no existan TransportCompanyVO en el sistema
	 * @throws BusinessException En caso de error al ejecutar la operación
	 * @author jalopez
	 * @param countryId 
	 */
	public TransportCompanyDTO getActiveTransportCompaniesByCountryId(Long countryId, RequestCollectionInfoDTO requestCollInfo) throws BusinessException;
	

	/**
	 * Metodo: Obtiene la información de todos los TransportCompanyVO almacenados en la persistencia
	 * @param RequestCollectionInfoDTO requestCollInfo, parametros para paginar
	 * @return TransportCompanyDTO, Lista con los TransportCompanyVO existentes, una lista vacia en caso que no existan TransportCompanyVO en el sistema
	 * @throws BusinessException En caso de error al ejecutar la operación
	 * @author waguilera
	 * @param countryId 
	 */
	public TransportCompanyDTO getAllTransportCompaniesByCountryId(Long countryId, RequestCollectionInfoDTO requestCollInfo) throws BusinessException;

	
	/**
	 * Metodo: Obtiene la información de una compañía transportadora
	 * dado el identificador de la misma
	 * @param code código de la compañía transportadora
	 * @return compañía transportadora con el identificador especificado
	 * @throws BusinessException En caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public TransportCompanyVO getTransportCompanyByCode(String code)throws BusinessException;

	/**
	 * Metodo: Obtiene la compañía trasnportadora por código y por país
	 * @param code código de la compañía transportadora
	 * @param countryId identificador del país
	 * @return compañía transportadora
	 * @throws BusinessException En caso de error al realizar la operación
	 * @author jjimenezh
	 */
	public TransportCompanyVO getTransportCompanyByCodeAndCountryId(String code, Long countryId) throws BusinessException;

	
}
