package co.com.directv.sdii.persistence.dao.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.DealerCoverage;
import co.com.directv.sdii.model.pojo.PostalCode;
import co.com.directv.sdii.model.pojo.collection.DealerCoverageResponse;
import co.com.directv.sdii.model.pojo.collection.PostalCodeResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.DealerVO;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad DealerCoverage
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerCoverageDAOLocal {

	/**
	 * Metodo:  persiste la información de un DealerCoverage
	 * @param obj objeto que encapsula la información de un DealerCoverage
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createDealerCoverage(DealerCoverage obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un DealerCoverage
	 * @param obj objeto que encapsula la información de un DealerCoverage
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateDealerCoverage(DealerCoverage obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un DealerCoverage
	 * @param obj información del DealerCoverage a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteDealerCoverage(DealerCoverage obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un DealerCoverage por su identificador
	 * @param id identificador del DealerCoverage a ser consultado
	 * @return objeto con la información del DealerCoverage dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public DealerCoverage getDealerCoverageByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los DealerCoverage almacenados en la persistencia
	 * @return Lista con los DealerCoverage existentes, una lista vacia en caso que no existan DealerCoverage en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<DealerCoverage> getAllDealerCoverages() throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los DealerCoverage almacenados en la persistencia por país y estado
	 * @return Lista con los DealerCoverage existentes, una lista vacia en caso que no existan DealerCoverage en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author gfandino
	 */
	public List<DealerCoverage> getAllDealerCoveragesByCountryAndStatus(Long country,String status) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Obtiene los dealers ordenados por prioridad en cubrimiento, por codigo postal y que se encuentre activo el cubrimiento
	 * @param dealerIds Lista de dealers de los cuales se desea obtener el cubrimiento
	 * @param postalCodeId Id del codigo postal donde se desea obtener el cubrimiento
	 * @param countryId Id del pais en donde se desea obtener le cubrimiento
	 * @return Lista de dealers ordenada por prioridad en cubrimiento
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<Dealer> getDealersByDealerIdOrderByDealerPriority(List<DealerVO> dealerIds,Long postalCodeId,Long countryId) throws DAOServiceException, DAOSQLException;
	
	
	
	/**
	 * 
	 * Metodo: Consulta la cobertura por codigo de dealer, pais, codigo postal que se encuentre activa
	 * @param dealerCode Codigo de dealer 
	 * @param postalCodeId id de codigo postal
	 * @param countryId id de pais
	 * @return DealerCoverage cubrimiento del dealer en el codigo postal y pais que se encuentra activo
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public DealerCoverage getActiveDealerCoverageByDealerCodePostalCodeAndCountry(Long dealerCode , Long postalCodeId , Long countryId) throws DAOServiceException, DAOSQLException; 

	/**
	 * Metodo: Obtiene los dealers que puedan atender en una zona, teniendo en cuenta la
	 * modalidad de ejecucion
	 * @param executionMode String
	 * @param postalCode String
	 * @param countryIso2Code String
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waltuzarra
	 */
	public List<Dealer> getDealersInMicrozoneWithExMode(String executionMode,
			String postalCode, String countryIso2Code, 
            Long dealerId)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene los dealers que puedan atender en una zona, teniendo en cuenta la modalidad de ejecucion
	 * @param executionMode String
	 * @param postalCode String
	 * @param countryIso2Code String
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	public Long countDealersInMicrozoneWithTypePerm(String postalCode, 
												    String countryIso2Code, 
												    Long dealerId) throws DAOServiceException, DAOSQLException;
	
	/**
     * Obtiene todos los DealerCoverage del sistema que se encuentren activos en un código postal
     * 
     * @param postalCode
     * @return - List<Dealers>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
	public List<DealerCoverage> getAllActiveByPostalCode(PostalCode postalCode) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene todas las microzonas <code>PostalCode</code> en las que tiene
	 * cobertura el dealer especificado
	 * @param dealerId identificador del dealer por el que se realizará la búsqueda
	 * @return List<PostalCode> listado de microzonas
	 * @throws DAOServiceException
	 * @throws DAOSQLException en caso de un error en la ejecución de la operación
	 * @author wjimenez
	 */
	public PostalCodeResponse getPostalCodesActiveByDealerId(Long dealerId, RequestCollectionInfo requestCollectionInfo) throws DAOServiceException, DAOSQLException;

	/**
     * Obtiene todos los DealerCoverage del sistema que se encuentren activos en un código postal
     * 
     * @param postalCode
     * @param isSeller determina is es un dealer vendedor
     * @param isInstaller determina is es un dealer instalador
     * @return - List<Dealers>
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
     */
	public List<DealerCoverage> getAllActiveByPostalCodeId(Long postalCodeId, String isSeller, String isInstaller) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene todos los DealerCoverage del sistema que se encuentren activos correspondientes
	 * a un dealer específico
	 * @param dealerId identificador del Dealer
	 * @return DealerCoverageResponse listado de DealerCoverage activos
	 * @throws DAOServiceException en caso de un error en la ejecución de la operación
	 * @throws DAOSQLException en caso de un error en la ejecución de la operación
	 * @author wjimenez
	 */
	public DealerCoverageResponse getAllActiveByDealerId(Long dealerId, RequestCollectionInfo requestCollectionInfo) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Consulta la cobertura por id de dealer, y codigo postal que se encuentre activa
	 * @param dealerId identificador de dealer 
	 * @param postalCodeId id de codigo postal
	 * @return DealerCoverage cubrimiento del dealer
	 * @throws DAOServiceException en caso de un error en la ejecución de la operación
	 * @throws DAOSQLException en caso de un error en la ejecución de la operación
	 * @author wjimenez
	 */
	public DealerCoverage getDealerCoverageByDealerIdAndPostalCodeId(Long dealerId, Long postalCodeId) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Trae los códigos postales de una ciudad en los que un dealer no tiene covertura 
	 * @param dealerId identificador del dealer
	 * @param cityId identificador de la ciudad
	 * @param requestCollectionInfo información de paginación
	 * @return PostalCodeResponse respuesta paginada
	 * @throws DAOServiceException en caso de un error en la ejecución de la operación
	 * @throws DAOSQLException en caso de un error en la ejecución de la operación
	 * @author wjimenez
	 */
	public PostalCodeResponse getPostalCodesWithoutCoverageByDealerIdAndCityId(Long dealerId, Long cityId,
			RequestCollectionInfo requestCollectionInfo) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Permite consultar todos los DealerCoverage segun el dealerId,postalCodeId,countryId,statusActive
	 * @param dealerId
	 * @param postalCodeId
	 * @param countryId
	 * @param statusActive
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author cduarte
	 */
	public List<DealerCoverage> getDealerCoverageByDealerIdPostalCodeIdCountryIdStatusActive(Long dealerId, Long postalCodeId, Long countryId,String statusActive)throws DAOServiceException, DAOSQLException;
	
}