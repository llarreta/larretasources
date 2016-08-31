package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.dto.collection.TransportCompanyDTO;
import co.com.directv.sdii.model.vo.TransportCompanyVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad TransportCompany.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface TransportCompanyFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto TransportCompany
	 * @param obj - TransportCompanyVO  objeto que encapsula la información de un TransportCompanyVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void createTransportCompany(TransportCompanyVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un TransportCompany
	 * @param obj - TransportCompanyVO  objeto que encapsula la información de un TransportCompanyVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void updateTransportCompany(TransportCompanyVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un TransportCompany
	 * @param obj - TransportCompanyVO  información del TransportCompanyVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void deleteTransportCompany(TransportCompanyVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un TransportCompany por su identificador
	 * @param id - Long identificador del TransportCompany a ser consultado
	 * @return objeto con la información del TransportCompanyVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public TransportCompanyVO getTransportCompanyByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los TransportCompany almacenados en la persistencia
	 * @return List<TransportCompanyVO> Lista con los TransportCompanyVO existentes, una lista vacia en caso que no existan TransportCompanyVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public List<TransportCompanyVO> getTransportCompaniesByCountryId(Long countryId) throws BusinessException;

	/**
	 * Metodo: Permite consultar la información de todos los TransportCompany almacenados en la persistencia
	 * @param RequestCollectionInfoDTO requestCollInfo, parametros para paginar.
	 * @return TransportCompanyDTO, List<TransportCompanyVO> Lista con los TransportCompanyVO existentes, una lista vacia en caso que no existan TransportCompanyVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public TransportCompanyDTO getActiveTransportCompaniesByCountryId(Long countryId, RequestCollectionInfoDTO requestCollInfo) throws BusinessException;

	/**
	 * Metodo: Permite consultar la información de todos los TransportCompany almacenados en la persistencia
	 * @param RequestCollectionInfoDTO requestCollInfo, parametros para paginar.
	 * @return TransportCompanyDTO, List<TransportCompanyVO> Lista con los TransportCompanyVO existentes, una lista vacia en caso que no existan TransportCompanyVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author waguilera
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
