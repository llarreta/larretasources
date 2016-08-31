package co.com.directv.sdii.ejb.business.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.collection.DealerBuildingResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.DealerBuldingVO;
import co.com.directv.sdii.reports.dto.DealerBuildingDTO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad DealerBulding.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerBuldingBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto DealerBuldingVO
	 * @param obj objeto que encapsula la información de un DealerBuldingVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createDealerBulding(DealerBuldingVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un DealerBuldingVO
	 * @param obj objeto que encapsula la información de un DealerBuldingVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateDealerBulding(DealerBuldingVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un DealerBuldingVO
	 * @param obj información del DealerBuldingVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteDealerBulding(DealerBuldingVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un DealerBuldingVO por su identificador
	 * @param id identificador del DealerBuldingVO a ser consultado
	 * @return objeto con la información del DealerBuldingVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public DealerBuldingVO getDealerBuldingByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los DealerBuldingVO almacenados en la persistencia
	 * @return Lista con los DealerBuldingVO existentes, una lista vacia en caso que no existan DealerBuldingVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<DealerBuldingVO> getAllDealerBuldings() throws BusinessException;

	/**
	 * Metodo: Obtiene todos los edificios para un código postal, y si el edificio ya
	 * tiene un dealer asociado para que lo atienda, se carga la información de este
	 * dealer
	 * @param postalCodeId
	 * @return DealerBuildingResponse
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	DealerBuildingResponse getDealerBuildingsByPostalCodeId(Long postalCodeId,
			RequestCollectionInfo requestCollectionInfo) throws BusinessException;

	/**
	 * Metodo: Actualiza un grupo de edificios de cobertura de dealers
	 * @param dealerBuildings registros a actualizar
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	void updateDealerBuildings(List<DealerBuldingVO> dealerBuildings) throws BusinessException;
	
	/**
	 * Método: Permite consultar DealerBuildingDTO por país
	 * @param idCountry - Identificador del país
	 * @return List<DealerBuildingDTO>
	 * @throws BusinessException en caso de error consultando DealerBuildingDTO
	 * @author gfandino
	 */
	public List<DealerBuildingDTO> getDealerBuldingsByCountryAndActive(Long idCountry) throws BusinessException;

	/**
	 * Metodo: Obtiene la información de los DealerBuldingVO almacenados en la persistencia
	 * que cumplan con los filtros especificados
	 * @param dealerCoverageId
	 * @param buildingId
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author wjimenez
	 */
	public DealerBuldingVO getDealerBuildingByDealerCoverageIdAndBuildingId(
			Long dealerCoverageId, Long buildingId) throws BusinessException;

}
