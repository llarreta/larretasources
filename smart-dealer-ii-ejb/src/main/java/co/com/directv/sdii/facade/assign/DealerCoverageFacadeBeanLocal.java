package co.com.directv.sdii.facade.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.collection.DealerCoverageResponse;
import co.com.directv.sdii.model.pojo.collection.PostalCodeResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.DealerCoverageVO;
import co.com.directv.sdii.reports.dto.DealerCoverageDTO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad DealerCoverage.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerCoverageFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto DealerCoverage
	 * @param obj - DealerCoverageVO  objeto que encapsula la información de un DealerCoverageVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createDealerCoverage(DealerCoverageVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un DealerCoverage
	 * @param obj - DealerCoverageVO  objeto que encapsula la información de un DealerCoverageVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateDealerCoverage(DealerCoverageVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un DealerCoverage
	 * @param obj - DealerCoverageVO  información del DealerCoverageVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteDealerCoverage(DealerCoverageVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un DealerCoverage por su identificador
	 * @param id - Long identificador del DealerCoverage a ser consultado
	 * @return objeto con la información del DealerCoverageVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public DealerCoverageVO getDealerCoverageByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los DealerCoverage almacenados en la persistencia
	 * @return List<DealerCoverageVO> Lista con los DealerCoverageVO existentes, una lista vacia en caso que no existan DealerCoverageVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<DealerCoverageVO> getAllDealerCoverages() throws BusinessException;

	/**
     * Obtiene todos los DealerCoverage del sistema que se encuentren activos en un código postal
     * 
     * @param postalCodeId
     * @param isSeller determina si es un dealer vendedor
     * @param isInstaller determina si es un dealer instalador
     * @return - List<Dealers>
     * @throws BusinessException
     */
	public List<DealerCoverageVO> getAllActiveByPostalCode(Long postalCodeId, String isSeller, String isInstaller) throws BusinessException;

	/**
	 * Metodo: persiste los cambios realizados sobre un grupo de DealerCoverageVO
	 * @param dealersCoverage
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	public void updateDealersCoverage(List<DealerCoverageVO> dealersCoverage) throws BusinessException;
	
	/**
	 * Metodo: Obtiene todas las microzonas <code>PostalCode</code> en las que tiene
	 * cobertura el dealer especificado
	 * @param dealerId identificador del dealer por el que se realizará la búsqueda
	 * @return PostalCodeResponse listado de microzonas paginado
	 * @throws BusinessException en caso de un error en la ejecución de la operación
	 * @author wjimenez
	 */
	public PostalCodeResponse getPostalCodesActiveByDealerId(Long dealerId, RequestCollectionInfo requestCollectionInfo) throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los DealerCoverage del sistema que se encuentren activos correspondientes
	 * a un dealer específico
	 * @param dealerId identificador del Dealer
	 * @return DealerCoverageResponse listado de DealerCoverage activos
	 * @throws BusinessException en caso de un error en la ejecución de la operación
	 * @author wjimenez
	 */
	public DealerCoverageResponse getAllActiveByDealerId(Long dealerId, RequestCollectionInfo requestCollectionInfo) throws BusinessException;
	
	/**
	 * Metodo: Consulta la cobertura por id de dealer, y codigo postal que se encuentre activa
	 * @param dealerId identificador de dealer 
	 * @param postalCodeId id de codigo postal
	 * @return DealerCoverage cubrimiento del dealer
	 * @throws DAOServiceException en caso de un error en la ejecución de la operación
	 * @throws DAOSQLException en caso de un error en la ejecución de la operación
	 * @author wjimenez
	 */
	public DealerCoverageVO getDealerCoverageByDealerIdAndPostalCodeId(Long dealerId, Long postalCodeId) throws BusinessException;
	
	/**
	 * Metodo: Trae los códigos postales de una ciudad en los que un dealer no tiene covertura 
	 * @param dealerId identificador del dealer
	 * @param cityId identificador de la ciudad
	 * @param requestCollectionInfo información de paginación
	 * @return PostalCodeResponse respuesta paginada
	 * @throws BusinessException en caso de un error en la ejecución de la operación
	 * @author wjimenez
	 */
	public PostalCodeResponse getPostalCodesWithoutCoverageByDealerIdAndCityId(Long dealerId, Long cityId,
			RequestCollectionInfo requestCollectionInfo) throws BusinessException;
	
	/**
	 * Método: Consulta los DealerCoverage que se encuentran en estado activo para el país determinado
	 * @return List<DealerCoverageVO> con los DealerCoverage activos
	 * @throws BusinessException en caso de error en la consulta
	 * @author gfandino
	 */
	public  List<DealerCoverageDTO> getAllDealerCoverageByCountryAndActiveStatus(Long countryID)throws BusinessException;

}
