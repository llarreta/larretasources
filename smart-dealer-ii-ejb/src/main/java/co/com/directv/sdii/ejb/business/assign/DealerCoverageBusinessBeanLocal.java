package co.com.directv.sdii.ejb.business.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.collection.DealerCoverageResponse;
import co.com.directv.sdii.model.pojo.collection.PostalCodeResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.DealerCoverageVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.reports.dto.DealerCoverageDTO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad DealerCoverage.
 * 
 * Fecha de CreaciÃ³n: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerCoverageBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la informaciÃ³n de un objeto DealerCoverageVO
	 * @param obj objeto que encapsula la informaciÃ³n de un DealerCoverageVO
	 * @throws BusinessException en caso de error al ejecutar la operaciÃ³n
	 * @author
	 */
	public void createDealerCoverage(DealerCoverageVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la informaciÃ³n de un DealerCoverageVO
	 * @param obj objeto que encapsula la informaciÃ³n de un DealerCoverageVO
	 * @throws BusinessException en caso de error al ejecutar la operaciÃ³n
	 * @author
	 */
	public void updateDealerCoverage(DealerCoverageVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la informaciÃ³n de un DealerCoverageVO
	 * @param obj informaciÃ³n del DealerCoverageVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operaciÃ³n
	 * @author
	 */
	public void deleteDealerCoverage(DealerCoverageVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la informaciÃ³n de un DealerCoverageVO por su identificador
	 * @param id identificador del DealerCoverageVO a ser consultado
	 * @return objeto con la informaciÃ³n del DealerCoverageVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operaciÃ³n
	 * @author
	 */
	public DealerCoverageVO getDealerCoverageByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la informaciÃ³n de todos los DealerCoverageVO almacenados en la persistencia
	 * @return Lista con los DealerCoverageVO existentes, una lista vacia en caso que no existan DealerCoverageVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operaciÃ³n
	 * @author
	 */
	public List<DealerCoverageVO> getAllDealerCoverages() throws BusinessException;
	
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
	public List<DealerVO> getDealersInMicrozoneWithExMode(String executionMode,
			String postalCode, String countryIso2Code,Long dealerId)throws BusinessException;

	/**
	 * Metodo: Obtiene la cantidad de dealers que puedan atender en una zona, teniendo en cuenta la
	 * modalidad de ejecucion
	 * @param postalCode
	 * @param countryIso2Code
	 * @param dealerId
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	public boolean checkDealersInMicrozoneWithTypePerm(String postalCode, 
                                                    String countryIso2Code,
                                                    Long dealerId) throws BusinessException;

	/**
     * Obtiene todos los DealerCoverage del sistema que se encuentren activos en un cÃ³digo postal
     * 
     * @param postalCodeId
     * @param isSeller determina si es un dealer vendedor
     * @param isInstaller determina is es un dealer instalador
     * @return - List<Dealers>
     * @throws BusinessException
     */
	public List<DealerCoverageVO> getAllActiveByPostalCode(Long postalCodeId, String isSeller, String isInstaller) throws BusinessException;

	/**
	 * Metodo: persiste los cambios realizados sobre un grupo de DealerCoverageVO
	 * @param dealersCoverage
	 * @throws BusinessException en caso de error al ejecutar la operaciÃ³n
	 * @author wjimenez
	 */
	public void updateDealersCoverage(List<DealerCoverageVO> dealersCoverage) throws BusinessException;

	/**
	 * Req-0096 - Requerimiento Consolidado Asignador
	 * Metodo: persiste los cambios realizados sobre un grupo de DealerConfCoverageVO
	 * @param dealersCoverage
	 * @throws BusinessException en caso de error al ejecutar la operaciÃ³n
	 * @author ialessan
	 */
	public void updateDealerConfCoverageConfiguration(  List<DealerCoverageVO> dealersCoverages,
														Long  dealerId, 
														Long  customerCategoryId ,
														Long  businessAreaId,
														Long  userIdLastChange
														

			) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene todas las microzonas <code>PostalCode</code> en las que tiene
	 * cobertura el dealer especificado
	 * @param dealerId identificador del dealer por el que se realizarÃ¡ la bÃºsqueda
	 * @return List<PostalCode> listado de microzonas
	 * @throws BusinessException en caso de un error en la ejecuciÃ³n de la operaciÃ³n
	 * @author wjimenez
	 */
	public PostalCodeResponse getPostalCodesActiveByDealerId(Long dealerId, RequestCollectionInfo requestCollectionInfo) throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los DealerCoverage del sistema que se encuentren activos correspondientes
	 * a un dealer especÃ­fico
	 * @param dealerId identificador del Dealer
	 * @return DealerCoverageResponse listado de DealerCoverage activos
	 * @throws BusinessException en caso de un error en la ejecuciÃ³n de la operaciÃ³n
	 * @author wjimenez
	 */
	public DealerCoverageResponse getAllActiveByDealerId(Long dealerId, RequestCollectionInfo requestCollectionInfo) throws BusinessException;
	
	/**
	 * Metodo: Consulta la cobertura por id de dealer, y codigo postal que se encuentre activa
	 * @param dealerId identificador de dealer 
	 * @param postalCodeId id de codigo postal
	 * @return DealerCoverage cubrimiento del dealer
	 * @throws DAOServiceException en caso de un error en la ejecuciÃ³n de la operaciÃ³n
	 * @throws DAOSQLException en caso de un error en la ejecuciÃ³n de la operaciÃ³n
	 * @author wjimenez
	 */
	public DealerCoverageVO getDealerCoverageByDealerIdAndPostalCodeId(Long dealerId, Long postalCodeId) throws BusinessException;
	
	/**
	 * Metodo: Trae los cÃ³digos postales de una ciudad en los que un dealer no tiene covertura 
	 * @param dealerId identificador del dealer
	 * @param cityId identificador de la ciudad
	 * @param requestCollectionInfo informaciÃ³n de paginaciÃ³n
	 * @return PostalCodeResponse respuesta paginada
	 * @throws BusinessException en caso de un error en la ejecuciÃ³n de la operaciÃ³n
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
	
	/**
	 * Metodo: Permite consultar la configuracion de cobertura con los filtros dealerId, postalCodeId, countryId y statusActive
	 * @param dealerId
	 * @param postalCodeId
	 * @param countryId
	 * @param statusActive
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public List<DealerCoverageVO> getDealerCoverageByDealerIdPostalCodeIdCountryIdStatusActive(
			Long dealerId, Long postalCodeId, Long countryId,
			String statusActive) throws BusinessException;

}
