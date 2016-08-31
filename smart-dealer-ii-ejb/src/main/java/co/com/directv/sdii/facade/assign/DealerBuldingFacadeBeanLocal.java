package co.com.directv.sdii.facade.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.collection.DealerBuildingResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.DealerBuldingVO;
import co.com.directv.sdii.reports.dto.DealerBuildingDTO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad DealerBulding.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerBuldingFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto DealerBulding
	 * @param obj - DealerBuldingVO  objeto que encapsula la información de un DealerBuldingVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createDealerBulding(DealerBuldingVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un DealerBulding
	 * @param obj - DealerBuldingVO  objeto que encapsula la información de un DealerBuldingVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateDealerBulding(DealerBuldingVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un DealerBulding
	 * @param obj - DealerBuldingVO  información del DealerBuldingVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteDealerBulding(DealerBuldingVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un DealerBulding por su identificador
	 * @param id - Long identificador del DealerBulding a ser consultado
	 * @return objeto con la información del DealerBuldingVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public DealerBuldingVO getDealerBuldingByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los DealerBulding almacenados en la persistencia
	 * @return List<DealerBuldingVO> Lista con los DealerBuldingVO existentes, una lista vacia en caso que no existan DealerBuldingVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
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
	public DealerBuildingResponse getDealerBuildingsByPostalCodeId(Long postalCodeId,
			RequestCollectionInfo requestCollectionInfo) throws BusinessException;

	/**
	 * Metodo: persiste los cambios realizados sobre un grupo de DealerCoverageVO
	 * @param dealersCoverage
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	public void updateDealerBuildings(List<DealerBuldingVO> dealerBuildings) throws BusinessException;
	
	/**
	 * Método: Permite consultar DealerBuildingDTO por país
	 * @param idCountry - Identificador del país
	 * @return List<DealerBuildingDTO>
	 * @throws BusinessException en caso de error consultando DealerBuildingDTO
	 * @author gfandino
	 */
	public List<DealerBuildingDTO> getDealerBuldingsByCountryAndActive(Long idCountry) throws BusinessException;

}
