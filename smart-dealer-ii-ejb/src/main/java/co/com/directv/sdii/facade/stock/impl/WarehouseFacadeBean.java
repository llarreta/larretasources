package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseElementStockBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.facade.stock.WarehouseFacadeBeanLocal;
import co.com.directv.sdii.model.dto.WarehouseInfoResponseDTO;
import co.com.directv.sdii.model.dto.WarehouseInfoResponseDetailDTO;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.WareHouseResponse;
import co.com.directv.sdii.model.vo.CrewVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.ElementVO;
import co.com.directv.sdii.model.vo.NotSerializedVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.model.vo.WarehouseTypeVO;
import co.com.directv.sdii.model.vo.WarehouseVO;
import co.com.directv.sdii.ws.model.dto.ResponseWareHouseIndicatorAlarmDTO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD Warehouse
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.WarehouseFacadeLocal
 */
@Stateless(name="WarehouseFacadeLocal",mappedName="ejb/WarehouseFacadeLocal")
public class WarehouseFacadeBean implements WarehouseFacadeBeanLocal {

		
    @EJB(name="WarehouseBusinessBeanLocal", beanInterface=WarehouseBusinessBeanLocal.class)
    private WarehouseBusinessBeanLocal businessWarehouse;

    @EJB(name="WarehouseElementStockBusinessBeanLocal",beanInterface=WarehouseElementStockBusinessBeanLocal.class)
    private WarehouseElementStockBusinessBeanLocal businessWareHouseElementStock;
   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.WarehouseFacadeLocal#getAllWarehouses()
     */
    public List<WarehouseVO> getAllWarehouses() throws BusinessException {
    	return businessWarehouse.getAllWarehouses();
    }
    
  
    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.stock.WarehouseFacadeBeanLocal#getAllWarehousesByCountryId(java.lang.Long, java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
     * 
 	 * ialessan
	 * junio 2014
	 * Modificacion de servicos de pantalla ubicaciones, cuatro nuevos parametros
	 * Se conserva este método ya que es utilizado por "ReportGeneratorWS.java / generateReport"
	 * para generación de reporte Excel análogo al resultado del botón "Consultar"
	 * y por ExcelGeneratorServlet.doGet
     * 
     */
    @Override
	public WarehouseInfoResponseDetailDTO getAllWarehousesByCountryId(	Long countryId,
																		String code,
																		RequestCollectionInfo requestCollInfo
	) throws BusinessException {
    	return businessWarehouse.getAllWarehousesByCountryId(countryId,code,requestCollInfo);
	}
        
    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.stock.WarehouseFacadeBeanLocal#getAllWarehousesByCountryId(java.lang.Long, java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
     * 
 	 * ialessan
	 * junio 2014
	 * Modificacion de servicos de pantalla ubicaciones, cuatro nuevos parametros
     * 
     */
    @Override
	public WarehouseInfoResponseDetailDTO getWhByWhTypeDealerBranchCrewIds(	 Long countryId, 
																				 String  warehouseTypeId, 
																				 String  dealerId, 
																				 String  branchId,
																				 String  crewId ,
																				 RequestCollectionInfo requestCollInfo
	) throws BusinessException {
    	return businessWarehouse.getWhByWhTypeDealerBranchCrewIds(	countryId, 
														    			warehouseTypeId, 
														    			dealerId, 
														    			branchId,
														    			crewId ,
														    			requestCollInfo);
	}	
    
    /* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseFacadeBeanLocal#getWarehousesByCountryId(java.lang.Long)
	 */
	public WareHouseResponse getWarehousesByCountryId(Long countryId, RequestCollectionInfo requestCollInfo) throws BusinessException {
		return businessWarehouse.getWarehousesByCountryId(countryId, requestCollInfo);
	}


    /* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseFacadeBeanLocal#getWarehousesByCountryIdAndDealerCondition(java.lang.Long)
	 */
	public WarehouseInfoResponseDTO getWarehousesByCountryIdAndDealerCondition(Long countryId, RequestCollectionInfo requestCollInfo,
				String isSeller, String isInstaller) throws BusinessException {
		return businessWarehouse.getWarehousesByCountryIdAndDealerCondition(countryId, requestCollInfo, isSeller, isInstaller);
	}

    /* (non-Javadoc)
     * @see #{$business_package_name$}.WarehouseFacadeLocal#getWarehousesByID(java.lang.Long)
     */
    public WarehouseVO getWarehouseByID(Long id) throws BusinessException {
    	return businessWarehouse.getWarehouseByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.WarehouseFacadeLocal#createWarehouse(co.com.directv.sdii.model.vo.WarehouseVO)
	 */
	public void createWarehouse(WarehouseVO obj) throws BusinessException {
		businessWarehouse.createWarehouse(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.WarehouseFacadeLocal#updateWarehouse(co.com.directv.sdii.model.vo.WarehouseVO)
	 */
	public void updateWarehouse(WarehouseVO obj) throws BusinessException {
		businessWarehouse.updateWarehouse(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.WarehouseFacadeLocal#deleteWarehouse(co.com.directv.sdii.model.vo.WarehouseVO)
	 */
	public void deleteWarehouse(WarehouseVO obj) throws BusinessException {
		businessWarehouse.deleteWarehouse(obj);
	}


	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseFacadeBeanLocal#getWarehouseByCodeAndByCountry(java.lang.String, java.lang.Long)
	 */
	public WarehouseVO getWarehouseByCodeAndByCountry(String code,Long country) throws BusinessException {
		return businessWarehouse.getWarehouseByCodeAndByCountry(code,country);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseFacadeBeanLocal#getWhByCrewAndWhType(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<WarehouseVO> getWhByCrewAndWhType(Long crewId, Long whTypeId)
			throws BusinessException {
		return businessWarehouse.getWhByCrewAndWhType(crewId, whTypeId);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseFacadeBeanLocal#validateSerializedInventory(java.lang.Long, java.util.List)
	 */
	@Override
	public List<SerializedVO> validateSerializedInventory(Long whID,List<SerializedVO> serializadosFisicos,Long countryId) throws BusinessException {
		return businessWarehouse.validateSerializedInventory(whID,serializadosFisicos,countryId);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseFacadeBeanLocal#validateNotSerializedInventory(java.lang.Long, java.util.List)
	 */
	@Override
	public List<NotSerializedVO> validateNotSerializedInventory(Long whID,List<NotSerializedVO> noSerializadosFisicos,Long countryId)
			throws BusinessException {
		return businessWarehouse.validateNotSerializedInventory(whID,noSerializadosFisicos,countryId);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseFacadeBeanLocal#getWarehousesByDealerId(java.lang.Long)
	 */
	@Override
	public List<WarehouseVO> getWarehousesByDealerId(Long dealerId)
			throws BusinessException {
		return businessWarehouse.getWarehousesByDealerId(dealerId);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseFacadeBeanLocal#getWarehousesByDealerIdOwnAndRelated(java.lang.Long, java.lang.Long)
	 */
	public List<WarehouseVO> getWarehousesByDealerIdOwnAndRelated(
			Long dealerId, Long warehouseTypeId) throws BusinessException {
		return businessWarehouse.getWarehousesByDealerIdOwnAndRelated(dealerId, warehouseTypeId);
	}
	
	/**
	 * Metodo: Obtiene las bodegas filtradas por codigo de bodega,codigo de compa�ia,codigo
	 * de sucursal, codigo de cuadrilla y codigo para tipo de bodega
	 * @param wareHouseId identificador de la bodega
	 * @param companyId identificador de la compa�ia
	 * @param branchId identificador de la sucursal
	 * @param crewId identificador de la cuadrilla
	 * @param wareHouseTypeId identificador del tipo de bodega
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @return WareHouseResponse, Lista de bodegas producto filtro
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta Warehouse por cuadrilla y tipo bodega
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta Warehouse por cuadrilla y tipo bodega
	 * @author garciniegas
	 */
	public WareHouseResponse getWarehousesByComplexFilter( WarehouseVO wareHouseId,DealerVO companyId,DealerVO branchId,CrewVO crewId,
			   WarehouseTypeVO wareHouseTypeId, RequestCollectionInfo requestCollInfo)throws BusinessException{
	  return businessWarehouse.getWarehousesByComplexFilter(wareHouseId, companyId, branchId, crewId, wareHouseTypeId, requestCollInfo);	
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseFacadeBeanLocal#getWarehousesByDealerIdAndCrewId(java.lang.Long, java.lang.Long)
	 */
	public List<WarehouseVO> getWarehousesByDealerIdAndCrewId(Long dealerId, Long crewId)	throws BusinessException {
		return businessWarehouse.getWarehousesByDealerId(dealerId);
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseFacadeBeanLocal#getWarehousesByAdjustNotSerElemCriteria(java.lang.Long, java.lang.Long,  java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	public WareHouseResponse getWarehousesByAdjustNotSerElemCriteria(
			Long wareHouseId, Long dealerId, Long branchId, Long crewId,
			Long wareHouseTypeId, RequestCollectionInfo requestCollInfo) throws BusinessException {
		return businessWarehouse.getWarehousesByAdjustNotSerElemCriteria(wareHouseId, dealerId, branchId, crewId, wareHouseTypeId, requestCollInfo);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseFacadeBeanLocal#getSerializedByElementId(co.com.directv.sdii.model.vo.ElementVO)
	 */
	public SerializedVO getSerializedByElementId( ElementVO elementId )throws BusinessException
	{
		return businessWarehouse.getSerializedByElementId( elementId );
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseFacadeBeanLocal#checkShortcomingInWarehouse(java.lang.Long, java.lang.Long)
	 */
	public boolean checkShortcomingInWarehouse( Long warehouse,Long user )throws BusinessException
	{
	  return businessWareHouseElementStock.checkShortcomingInWarehouse( warehouse, user);	
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseFacadeBeanLocal#checkWarehouseIndicatorAlarmByDealerIdWarehouseTypeAndUser(java.lang.Long, java.lang.String, java.lang.Long)
	 */
	public List<ResponseWareHouseIndicatorAlarmDTO> checkWarehouseIndicatorAlarmByDealerIdWarehouseTypeAndUser( Long dealerId,String warehouseType,Long user )throws BusinessException
	{
	  return businessWareHouseElementStock.checkWarehouseIndicatorAlarmByDealerIdWarehouseTypeAndUser( dealerId, warehouseType, user);	
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseFacadeBeanLocal#getWarehousesByCountryIdAndActive(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<WarehouseVO> getWarehousesByCountryIdAndActive(Long countryId, Long dealerId)
			throws BusinessException {
		return businessWarehouse.getWarehousesByCountryIdAndActive(countryId,dealerId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseFacadeBeanLocal#getWarehousesByCountryIdAndActive(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public WareHouseResponse getWarehousesByCountryIdAndActive(Long countryId,RequestCollectionInfo requestCollInfo)
		throws BusinessException {
		return businessWarehouse.getWarehousesByCountryIdAndActive(countryId,requestCollInfo);
	}


	@Override
	public WarehouseVO createWarehouseTransit(WarehouseVO objWarehouse)
			throws BusinessException {
		
		return businessWarehouse.createWarehouseTransit(objWarehouse);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseFacadeBeanLocal#getWhByCrewAndDealerAndWhType(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<WarehouseVO> getWhByCrewAndDealerAndWhType(Long dealerId, Long crewId, Long whTypeId) throws BusinessException {
		return businessWarehouse.getWhByCrewAndDealerAndWhTypeNotVirtual(dealerId, crewId, whTypeId);
	}


	@Override
	public WarehouseVO getWarehouseTypeByDealerId(Long dealerId,
			String warehouseType) throws BusinessException {
		return businessWarehouse.getWarehouseTypeByDealerId(dealerId, warehouseType);
	}


	@Override
	public WarehouseVO getWarehouseByDealerIDIBSCodeDocumentCustomer(
			Long warehouseId, String codeIBS, String documentCustomer)
			throws BusinessException {
		return businessWarehouse.getWarehouseByDealerIDIBSCodeDocumentCustomer(warehouseId, codeIBS, documentCustomer);
	}
	

	@Override
	public List<WarehouseVO> getWarehouseByCountryIdAndOptionalDealerId(
			Long dealerID, Long countryID, Long userId, boolean searchSourceWh)
			throws BusinessException {
		return businessWarehouse.getWarehouseByCountryIdAndOptionalDealerId(dealerID, countryID, userId, searchSourceWh);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseFacadeBeanLocal#getWarehousesByDealerIdAndWhTypeCode(java.lang.Long, java.lang.String)
	 */
	public List<WarehouseVO> getWarehousesByDealerIdAndWhTypeCodeWithBranch(Long dealerId,String whTypeCode,boolean withBranch)  throws BusinessException{
		return businessWarehouse.getWarehousesByDealerIdAndWhTypeCodeWithBranch(dealerId, whTypeCode,withBranch);
	}
	
}
