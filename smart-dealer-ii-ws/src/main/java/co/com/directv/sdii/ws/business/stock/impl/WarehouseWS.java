package co.com.directv.sdii.ws.business.stock.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.facade.stock.DocumentClassFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.EmailTypeFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.MovementTypeFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.NotSerPartialRetirementFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.RecordStatusFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.WarehouseElementStockFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.WarehouseFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.WarehouseTypeFacadeBeanLocal;
import co.com.directv.sdii.model.dto.MassiveMovementBetweenWareHouseDTO;
import co.com.directv.sdii.model.dto.MovementElementDTO;
import co.com.directv.sdii.model.dto.MovementTypeClassDTO;
import co.com.directv.sdii.model.dto.QuantityWarehouseElementsDTO;
import co.com.directv.sdii.model.dto.SendEmailDTO;
import co.com.directv.sdii.model.dto.WareHouseElementClientFilterRequestDTO;
import co.com.directv.sdii.model.dto.WareHouseRequestDTO;
import co.com.directv.sdii.model.dto.WarehouseInfoResponseDTO;
import co.com.directv.sdii.model.dto.WarehouseInfoResponseDetailDTO;
import co.com.directv.sdii.model.dto.collection.CustomerElementsResponse;
import co.com.directv.sdii.model.dto.collection.QuantityWarehouseElementResponse;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.dto.collection.WarehouseElementStockDTO;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.WarehouseElement;
import co.com.directv.sdii.model.pojo.WhElementSearchFilter;
import co.com.directv.sdii.model.pojo.collection.MovedElementSerializedResponse;
import co.com.directv.sdii.model.pojo.collection.MovementTypeResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SerializedWhElementsByCriteriaPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.WareHouseElementCustomerResponse;
import co.com.directv.sdii.model.pojo.collection.WareHouseElementHistoricalResponse;
import co.com.directv.sdii.model.pojo.collection.WareHouseElementResponse;
import co.com.directv.sdii.model.pojo.collection.WareHouseResponse;
import co.com.directv.sdii.model.pojo.collection.WareheouseTypeResponse;
import co.com.directv.sdii.model.vo.CrewVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.DocumentClassVO;
import co.com.directv.sdii.model.vo.ElementVO;
import co.com.directv.sdii.model.vo.MovementTypeVO;
import co.com.directv.sdii.model.vo.NotSerPartialRetirementVO;
import co.com.directv.sdii.model.vo.NotSerializedVO;
import co.com.directv.sdii.model.vo.RecordStatusVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.model.vo.UserVO;
import co.com.directv.sdii.model.vo.WHElementQtySummaryVO;
import co.com.directv.sdii.model.vo.WarehouseElementQuantityVO;
import co.com.directv.sdii.model.vo.WarehouseElementStockVO;
import co.com.directv.sdii.model.vo.WarehouseElementVO;
import co.com.directv.sdii.model.vo.WarehouseTypeVO;
import co.com.directv.sdii.model.vo.WarehouseVO;
import co.com.directv.sdii.model.vo.WhElementSearchFilterVO;
import co.com.directv.sdii.reports.dto.FilterSerializedElementDTO;
import co.com.directv.sdii.ws.business.stock.IWarehouseWS;
import co.com.directv.sdii.ws.model.dto.ResponseWareHouseIndicatorAlarmDTO;

/**
 * Servicio web que expone las operaciones relacionadas con Warehouse
 * 
 * Fecha de Creación: 28/06/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.WarehouseFacadeBeanLocal
 */
@MTOM(threshold=3072)
@WebService(serviceName="WarehouseService",
		endpointInterface="co.com.directv.sdii.ws.business.stock.IWarehouseWS",
		targetNamespace="http://stock.business.ws.sdii.directv.com.co/",
		portName="WarehousePort")
@Stateless()
public class WarehouseWS implements IWarehouseWS {
	
	@EJB
    private WarehouseFacadeBeanLocal ejbRef;
	@EJB
    private WarehouseTypeFacadeBeanLocal ejbRefType;
	@EJB
    private WarehouseElementFacadeBeanLocal ejbRefElement;
	@EJB
    private WarehouseElementStockFacadeBeanLocal ejbRefStock;
	@EJB
    private NotSerPartialRetirementFacadeBeanLocal ejbRefPartial;
	@EJB
    private MovementTypeFacadeBeanLocal ejbRefMovType;
	@EJB
    private RecordStatusFacadeBeanLocal ejbRefRec;
	@EJB
    private EmailTypeFacadeBeanLocal ejbRefEmailType;
	@EJB
    private DocumentClassFacadeBeanLocal ejbDocumentClass;

	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#createWarehouseType(co.com.directv.sdii.model.vo.WarehouseTypeVO)
	 */
	@Override
	public void createWarehouseType(WarehouseTypeVO objWarehouseType) throws BusinessException{
		ejbRefType.createWarehouseType(objWarehouseType);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#updateWarehouseType(co.com.directv.sdii.model.vo.WarehouseTypeVO)
	 */
	@Override
	public void updateWarehouseType(WarehouseTypeVO objWarehouseType) throws BusinessException{
		ejbRefType.updateWarehouseType(objWarehouseType);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getWarehouseTypeByID(java.lang.Long)
	 */
	@Override
	public WarehouseTypeVO getWarehouseTypeByID(Long id) throws BusinessException{
		return ejbRefType.getWarehouseTypeByID(id);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getAllWarehouseTypes()
	 */
	@Override
	public List<WarehouseTypeVO> getAllWarehouseTypes() throws BusinessException{
		return ejbRefType.getAllWarehouseTypes();
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getWarehouseTypeByCode(java.lang.String)
	 */
	@Override
	public WarehouseTypeVO getWarehouseTypeByCode(String code)
			throws BusinessException {
		return ejbRefType.getWarehouseTypeByCode(code);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#createWarehouse(co.com.directv.sdii.model.vo.WarehouseVO)
	 */
	@Override
	public void createWarehouse(WarehouseVO objWarehouse) throws BusinessException{
		ejbRef.createWarehouse(objWarehouse);
	}
	
	@Override
	public WarehouseVO createWarehouseTransit(WarehouseVO objWarehouse) throws BusinessException{
		return ejbRef.createWarehouseTransit(objWarehouse);
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#updateWarehouse(co.com.directv.sdii.model.vo.WarehouseVO)
	 */
	@Override
	public void updateWarehouse(WarehouseVO objWarehouse) throws BusinessException{
		ejbRef.updateWarehouse(objWarehouse);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getWarehouseByID(java.lang.Long)
	 */
	@Override
	public WarehouseVO getWarehouseByID(Long id) throws BusinessException{
		return ejbRef.getWarehouseByID(id);
	}
	
	@Override
	public WarehouseVO getWarehouseTypeByDealerId(Long dealerId,String warehouseType) throws BusinessException{
		return ejbRef.getWarehouseTypeByDealerId(dealerId, warehouseType);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getAllWarehouses()
	 */
	@Override
	public List<WarehouseVO> getAllWarehouses() throws BusinessException{
		return ejbRef.getAllWarehouses();
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getAllWarehousesByCountryId(java.lang.Long, java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public WarehouseInfoResponseDetailDTO  getAllWarehousesByCountryId(Long countryId, String code,RequestCollectionInfo requestCollInfo) throws BusinessException{
		return ejbRef.getAllWarehousesByCountryId(countryId,code,requestCollInfo);
	}
	
	/* 
	 * 
	 * ialessan
	 * junio 2014
	 * Modificacion de servicos de pantalla ubicaciones, cuatro nuevos parametros.
	 * 
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getAllWarehousesByCountryId(java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public WarehouseInfoResponseDetailDTO  getWhByWhTypeDealerBranchCrewIds(	Long 	countryId, 
																					String  warehouseTypeId, 
																					String  dealerId, 
																					String  branchId,
																					String  crewId ,
																					RequestCollectionInfo requestCollInfo
	) throws BusinessException{
		return ejbRef.getWhByWhTypeDealerBranchCrewIds( countryId, 
															warehouseTypeId, 
															dealerId, 
															branchId,
															crewId ,
															requestCollInfo
															);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getWarehousesByCountryId(java.lang.Long)
	 */
	public WareHouseResponse getWarehousesByCountryId(Long countryId, RequestCollectionInfoDTO requestCollInfo)	throws BusinessException {
		return ejbRef.getWarehousesByCountryId(countryId, requestCollInfo);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getWarehousesByCountryIdAndDealerCondition(java.lang.Long)
	 */
	public WarehouseInfoResponseDTO getWarehousesByCountryIdAndDealerCondition(Long countryId, RequestCollectionInfoDTO requestCollInfo,
				String isSeller, String isInstaller)	throws BusinessException {
		return ejbRef.getWarehousesByCountryIdAndDealerCondition(countryId, requestCollInfo, isSeller, isInstaller);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getWarehousesByDealerIdOwnAndRelated(java.lang.Long, java.lang.Long)
	 */
	public List<WarehouseVO> getWarehousesByDealerIdOwnAndRelated(
			Long dealerId, Long warehouseTypeId) throws BusinessException {
		return ejbRef.getWarehousesByDealerIdOwnAndRelated(dealerId, warehouseTypeId);
	}

	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getWarehousesByDealerId(java.lang.Long)
	 */
	@Override
	public List<WarehouseVO> getWarehousesByDealerId(Long dealerId)throws BusinessException {
		return ejbRef.getWarehousesByDealerId(dealerId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#createWarehouseElement(co.com.directv.sdii.model.vo.WarehouseElementVO)
	 */
	@Override
	public void createWarehouseElement(WarehouseElementVO objWarehouseElement) throws BusinessException{
		ejbRefElement.createWarehouseElement(objWarehouseElement);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#updateWarehouseElement(co.com.directv.sdii.model.vo.WarehouseElementVO)
	 */
	@Override
	public void updateWarehouseElement(WarehouseElementVO objWarehouseElement) throws BusinessException{
		ejbRefElement.updateWarehouseElement(objWarehouseElement);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#deleteWarehouseElement(co.com.directv.sdii.model.vo.WarehouseElementVO)
	 */
	@Override
	public void deleteWarehouseElement(WarehouseElementVO objWarehouseElement) throws BusinessException{
		ejbRefElement.deleteWarehouseElement(objWarehouseElement);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getWarehouseElementByID(java.lang.Long)
	 */
	@Override
	public WarehouseElementVO getWarehouseElementByID(Long id) throws BusinessException{
		return ejbRefElement.getWarehouseElementByID(id);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getAllWarehouseElements()
	 */
	@Override
	public List<WarehouseElementVO> getAllWarehouseElements() throws BusinessException{
		return ejbRefElement.getAllWarehouseElements();
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getWarehouseElementsByFilters(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public WareHouseElementResponse getWarehouseElementsByFilters(Long wareHouseId, Long dealerId, Long branchDealerId, Long crewId, Long warehouseTypeId, String elementTypeCode, Long elementModelId, RequestCollectionInfo requestCollInfo) throws BusinessException{
		return ejbRefElement.getWarehouseElementsByFilters(wareHouseId, dealerId, branchDealerId, crewId, warehouseTypeId, elementTypeCode, elementModelId, requestCollInfo);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getWarehouseElementsBySearchFilter(co.com.directv.sdii.model.vo.WhElementSearchFilterVO)
	 */
	@Override
	public WareHouseElementResponse getWarehouseElementsBySearchFilter( WhElementSearchFilterVO whElementSearchFilterVO, RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		return ejbRefElement.getWarehouseElementsBySearchFilter(whElementSearchFilterVO, requestCollInfo);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getQualityControlWhNotSerElementsByDealerId(java.lang.Long)
	 */
	public List<WarehouseElementVO> getQualityControlWhNotSerElementsByDealerId(
			Long dealerId) throws BusinessException {
		return ejbRefElement.getQualityControlWhNotSerElementsByDealerId(dealerId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getCurrentQuantityInWarehouseByElementType(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	public WarehouseElementQuantityVO getCurrentQuantityInWarehouseByElementType(
			Long warehouseId, Long elementTypeId, Long elementModelId)
			throws BusinessException {
		return ejbRefElement.getCurrentQuantityInWarehouseByElementType(warehouseId, elementTypeId, elementModelId);
	}
	
	@Override
	public void moveElementsFromWHCrewToWHCompany(List<DealerVO> companies, UserVO user) throws BusinessException {
		ejbRefElement.moveElementsFromWHCrewToWHCompany(companies, user);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#moveSerializedElementsToWareHouse(java.lang.Long, java.lang.Long, java.util.List, java.lang.String, java.lang.String)
	 */
	@Override
	public void moveSerializedElementsToWareHouse(Long sourceWhId, Long targetWsId, List<SerializedVO> serializedList, String movTypeCodeE, String movTypeCodeS) throws BusinessException {
		ejbRefElement.moveSerializedElementsToWareHouse(sourceWhId, targetWsId, serializedList, movTypeCodeE, movTypeCodeS);
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#createWarehouseElementStock(co.com.directv.sdii.model.vo.WarehouseElementStockVO)
	 */
	@Override
	public void createWarehouseElementStock(WarehouseElementStockVO objWarehouseElementStock) throws BusinessException{
		ejbRefStock.createWarehouseElementStock(objWarehouseElementStock);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#updateWarehouseElementStock(co.com.directv.sdii.model.vo.WarehouseElementStockVO)
	 */
	@Override
	public void updateWarehouseElementStock(WarehouseElementStockVO objWarehouseElementStock) throws BusinessException{
		ejbRefStock.updateWarehouseElementStock(objWarehouseElementStock);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#deleteWarehouseElementStock(co.com.directv.sdii.model.vo.WarehouseElementStockVO)
	 */
	@Override
	public void deleteWarehouseElementStock(WarehouseElementStockVO objWarehouseElementStock) throws BusinessException{
		ejbRefStock.deleteWarehouseElementStock(objWarehouseElementStock);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getWarehouseElementStockByID(java.lang.Long)
	 */
	@Override
	public WarehouseElementStockVO getWarehouseElementStockByID(Long id) throws BusinessException{
		return ejbRefStock.getWarehouseElementStockByID(id);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getAllWarehouseElementStocks()
	 */
	@Override
	public List<WarehouseElementStockVO> getAllWarehouseElementStocks() throws BusinessException{
		return ejbRefStock.getAllWarehouseElementStocks();
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getWarehElemStockByElemTypeCodeOrDealerCodeOrWhCode(java.lang.String, java.lang.String, java.lang.String, java.lang.Long, co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO)
	 */
	@Override
	public WarehouseElementStockDTO getWarehElemStockByElemTypeCodeOrDealerCodeOrWhCode(
			String elementTypeCode, Long dealerId, String warehouseCode, Long countryId, RequestCollectionInfoDTO requestCollInfo, Long dealerBranchId)
			throws BusinessException {
		return ejbRefStock.getWarehElemStockByElemTypeCodeOrDealerCodeOrWhCode(elementTypeCode, dealerId, warehouseCode, countryId, requestCollInfo, dealerBranchId);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#createNotSerPartialRetirement(co.com.directv.sdii.model.vo.NotSerPartialRetirementVO)
	 */
	@Override
	public void createNotSerPartialRetirement(NotSerPartialRetirementVO objNotSerPartialRetirement) throws BusinessException{
		ejbRefPartial.createNotSerPartialRetirement(objNotSerPartialRetirement);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#updateNotSerPartialRetirement(co.com.directv.sdii.model.vo.NotSerPartialRetirementVO)
	 */
	@Override
	public void updateNotSerPartialRetirement(NotSerPartialRetirementVO objNotSerPartialRetirement) throws BusinessException{
		ejbRefPartial.updateNotSerPartialRetirement(objNotSerPartialRetirement);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#deleteNotSerPartialRetirement(co.com.directv.sdii.model.vo.NotSerPartialRetirementVO)
	 */
	@Override
	public void deleteNotSerPartialRetirement(NotSerPartialRetirementVO objNotSerPartialRetirement) throws BusinessException{
		ejbRefPartial.deleteNotSerPartialRetirement(objNotSerPartialRetirement);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getNotSerPartialRetirementByID(java.lang.Long)
	 */
	@Override
	public NotSerPartialRetirementVO getNotSerPartialRetirementByID(Long id) throws BusinessException{
		return ejbRefPartial.getNotSerPartialRetirementByID(id);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getAllNotSerPartialRetirements()
	 */
	@Override
	public List<NotSerPartialRetirementVO> getAllNotSerPartialRetirements() throws BusinessException{
		return ejbRefPartial.getAllNotSerPartialRetirements();
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#createMovementType(co.com.directv.sdii.model.vo.MovementTypeVO)
	 */
	@Override
	public void createMovementType(MovementTypeVO objMovementType) throws BusinessException{
		ejbRefMovType.createMovementType(objMovementType);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#updateMovementType(co.com.directv.sdii.model.vo.MovementTypeVO)
	 */
	@Override
	public void updateMovementType(MovementTypeVO objMovementType) throws BusinessException{
		ejbRefMovType.updateMovementType(objMovementType);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#deleteMovementType(co.com.directv.sdii.model.vo.MovementTypeVO)
	 */
	@Override
	public void deleteMovementType(MovementTypeVO objMovementType) throws BusinessException{
		ejbRefMovType.deleteMovementType(objMovementType);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getMovementTypeByID(java.lang.Long)
	 */
	@Override
	public MovementTypeVO getMovementTypeByID(Long id) throws BusinessException{
		return ejbRefMovType.getMovementTypeByID(id);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getAllMovementTypes()
	 */
	@Override
	public List<MovementTypeVO> getAllMovementTypes() throws BusinessException{
		return ejbRefMovType.getAllMovementTypes();
	}
	
	public List<MovementTypeClassDTO> getAllMovementTypesClass() throws BusinessException{
		return ejbRefMovType.getAllMovementTypesClass();
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getMovementTypeByCode(java.lang.String)
	 */
	@Override
	public MovementTypeVO getMovementTypeByCode(String code)
			throws BusinessException {
		return ejbRefMovType.getMovementTypeByCode(code);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#createRecordStatus(co.com.directv.sdii.model.vo.RecordStatusVO)
	 */
	@Override
	public void createRecordStatus(RecordStatusVO objRecordStatus) throws BusinessException{
		ejbRefRec.createRecordStatus(objRecordStatus);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#updateRecordStatus(co.com.directv.sdii.model.vo.RecordStatusVO)
	 */
	@Override
	public void updateRecordStatus(RecordStatusVO objRecordStatus) throws BusinessException{
		ejbRefRec.updateRecordStatus(objRecordStatus);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#deleteRecordStatus(co.com.directv.sdii.model.vo.RecordStatusVO)
	 */
	@Override
	public void deleteRecordStatus(RecordStatusVO objRecordStatus) throws BusinessException{
		ejbRefRec.deleteRecordStatus(objRecordStatus);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getRecordStatusByID(java.lang.Long)
	 */
	@Override
	public RecordStatusVO getRecordStatusByID(Long id) throws BusinessException{
		return ejbRefRec.getRecordStatusByID(id);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getAllRecordStatuss()
	 */
	@Override
	public List<RecordStatusVO> getAllRecordStatuss() throws BusinessException{
		return ejbRefRec.getAllRecordStatuss();
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getWarehouseElementsByCustomerIBSCodeDocTypeIdAndDocNumber(java.lang.String, java.lang.Long, java.lang.String, java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public QuantityWarehouseElementResponse getWarehouseElementsByWarehouse(WhElementSearchFilter whElementSearchFilter,RequestCollectionInfo requestCollInfo) throws BusinessException{
		return ejbRefElement.getWarehouseElementsByWarehouse(whElementSearchFilter, requestCollInfo);
	}
	
	@Override
	public WareHouseElementCustomerResponse getWarehouseElementsByWarehouseCustomerActual(WareHouseElementClientFilterRequestDTO request,RequestCollectionInfo requestCollInfo) throws BusinessException{
		return ejbRefElement.getWarehouseElementsByWarehouseCustomerActual(request, requestCollInfo);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getHistoryElementsByCustomerIBSCodeDocTypeIdAndDocNumber(java.lang.String, java.lang.Long, java.lang.String, java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public CustomerElementsResponse getElementsByCustomerIBSCodeDocTypeIdAndDocNumber(
			String customerIbsCode, Long documentTypeId,
			String customerDocumentNumber, Long countryId,
			RequestCollectionInfo requestCollInfo) throws BusinessException{
		return ejbRefElement.getElementsByCustomerIBSCodeDocTypeIdAndDocNumber(customerIbsCode, documentTypeId, customerDocumentNumber, countryId, requestCollInfo);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getWhByCrewAndWhType(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<WarehouseVO> getWhByCrewAndWhType(Long crewId, Long whTypeId)
			throws BusinessException {
		return ejbRef.getWhByCrewAndWhType(crewId, whTypeId);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#validateSerializedInventory(java.lang.Long, java.util.List, java.lang.Long)
	 */
	@Override
	public List<SerializedVO> validateSerializedInventory(Long whID,List<SerializedVO> serializadosFisicos,Long countryId) throws BusinessException {
		return ejbRef.validateSerializedInventory(whID,serializadosFisicos,countryId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#validateNotSerializedInventory(java.lang.Long, java.util.List, java.lang.Long)
	 */
	@Override
	public List<NotSerializedVO> validateNotSerializedInventory(Long whID, List<NotSerializedVO> noSerializadosFisicos,Long countryId)
			throws BusinessException {
		return ejbRef.validateNotSerializedInventory(whID,noSerializadosFisicos,countryId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getNotSerializedsByWareHouseId(co.com.directv.sdii.model.vo.WarehouseVO)
	 */
	@Override
	public List<NotSerializedVO> getNotSerializedsByWareHouseId(WarehouseVO warehouseId ) throws BusinessException
	{
		return ejbRefElement.getNotSerializedsByWareHouseId( warehouseId );
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getWarehouseQANOTSerializedElements(Long)
	 */
	@Override
	public WareHouseElementResponse getWarehouseQANOTSerializedElements( Long dealerId, RequestCollectionInfo requestCollInfo ) throws BusinessException
	{
		return ejbRefElement.getWarehouseQANOTSerializedElements(dealerId, requestCollInfo);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getWarehouseQANOTSerializedElements(Long)
	 */
	@Override
	public WareHouseElementResponse getWarehouseQASerializedElements(Dealer dealer, String elementType, String serialCode, RequestCollectionInfo requestCollInfo ) throws BusinessException
	{
		return ejbRefElement.getWarehouseQASerializedElements(dealer, elementType, serialCode, requestCollInfo);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getWarehousesByComplexFilter(co.com.directv.sdii.model.vo.WarehouseVO, co.com.directv.sdii.model.vo.DealerVO, co.com.directv.sdii.model.vo.DealerVO, co.com.directv.sdii.model.vo.CrewVO, co.com.directv.sdii.model.vo.WarehouseTypeVO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public WareHouseResponse getWarehousesByComplexFilter( WarehouseVO wareHouseId,
			  DealerVO companyId,DealerVO branchId,CrewVO crewId,
			  WarehouseTypeVO wareHouseTypeId, RequestCollectionInfo requestCollInfo )throws BusinessException
	{
        return ejbRef.getWarehousesByComplexFilter(wareHouseId, companyId, branchId, crewId, wareHouseTypeId, requestCollInfo);
    }
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#sendEmailByEmailTypeCode(co.com.directv.sdii.model.dto.SendEmailDTO, java.lang.Long)
	 */
	@Override
	public void sendEmailByEmailTypeCode(SendEmailDTO sendEmailDTO, Long countryId)
			throws BusinessException {
		ejbRefEmailType.sendEmailByEmailTypeCode(sendEmailDTO, countryId);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getWarehousesByAdjustNotSerElemCriteria(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public WareHouseResponse getWarehousesByAdjustNotSerElemCriteria(Long warehouseId,
			Long dealerId, Long branchId, Long crewId, Long wareHouseTypeId, RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		return ejbRef.getWarehousesByAdjustNotSerElemCriteria(warehouseId, dealerId, branchId, crewId, wareHouseTypeId, requestCollInfo);
		
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getWhElementsByCriteria(java.lang.Long, java.lang.String, java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public WareHouseElementResponse getWhElementsByCriteria(Long warehouseId,
			String isSerialized, Long elementId, RequestCollectionInfo requestCollInfo) throws BusinessException {
		return ejbRefElement.getWhElementsByCriteria(warehouseId, isSerialized, elementId, requestCollInfo);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#massiveMovementOfNotSerializedElementsBetweenWareHouse(co.com.directv.sdii.model.vo.WarehouseVO, co.com.directv.sdii.model.vo.WarehouseVO, java.util.List)
	 */
	@Override
	public void massiveMovementOfNotSerializedElementsBetweenWareHouse( WarehouseVO wareHouseSource,WarehouseVO wareHouseTarget,List<ElementVO> listObjectToMove, Long userId )throws BusinessException
	{
		MassiveMovementBetweenWareHouseDTO moveObject = new MassiveMovementBetweenWareHouseDTO();
		moveObject.setListObjectToMove( listObjectToMove );
		moveObject.setWareHouseSource( wareHouseSource );
		moveObject.setWareHouseTarget(wareHouseTarget);
		moveObject.setUserId(userId);
		
		ejbRefElement.massiveMovementOfNotSerializedElementsBetweenWareHouse( moveObject );	
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getSerializedsByWareHouseId(co.com.directv.sdii.model.vo.WarehouseVO)
	 */
	@Override
	public List<SerializedVO> getSerializedsByWareHouseId( WarehouseVO warehouseId ) throws BusinessException
	{
		return ejbRefElement.getSerializedsByWareHouseId( warehouseId );
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getWhElementsByWhIdAndElementTypeCodeAndSerials(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.String)
	 */
	@Override
	public List<WarehouseElementVO> getWhElementsByWhIdAndElementTypeCodeAndSerials(
			Long warehouseId, Long elementTypeId1, Long elementTypeId2,
			String serialEl1, String serialEl2) throws BusinessException {
		return ejbRefElement.getWhElementsByWhIdAndElementTypeCodeAndSerials(warehouseId,
				elementTypeId1, elementTypeId2, serialEl1, serialEl2);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#separateLinkedSerializedElementsInWh(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	public void separateLinkedSerializedElementsInWh(Long warehouseId,
			Long whElementMasterId, Long whElementLinkedId)
			throws BusinessException {
		ejbRefElement.separateLinkedSerializedElementsInWh(warehouseId, whElementMasterId, whElementLinkedId);
	}

	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#massiveMovementOfSerializedElementsBetweenWareHouse(co.com.directv.sdii.model.vo.WarehouseVO, co.com.directv.sdii.model.vo.WarehouseVO, java.util.List)
	 */
	@Override
	public void massiveMovementOfSerializedAndLinkedElementsBetweenWareHouse(WarehouseVO wareHouseSource, WarehouseVO wareHouseTarget, List<ElementVO> listObjectToMove, Long userId)throws BusinessException
	{
		MassiveMovementBetweenWareHouseDTO moveObject = new MassiveMovementBetweenWareHouseDTO();
		moveObject.setListObjectToMove( listObjectToMove );
		moveObject.setWareHouseSource( wareHouseSource );
		moveObject.setWareHouseTarget(wareHouseTarget);
		ejbRefElement.massiveMovementOfSerializedAndLinkedElementsBetweenWareHouse(moveObject, userId);	
	}
	

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#separateLinkedSerializedElementsInWh(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	public SerializedVO getSerializedByElementId(ElementVO elementId)
			throws BusinessException {
		return ejbRef.getSerializedByElementId( elementId );
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getWhElementQuantitySummariesByFilters(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.util.Date, java.util.Date)
	 */
	@Override
	public List<WHElementQtySummaryVO> getWhElementQuantitySummariesByFilters(
			Long dealerId, Long warehouseId, Long crewId, Long warehouseTypeId,
			Long elementTypeId, Long elementModelId, Date initialDate,
			Date finalDate) throws BusinessException {
		return ejbRefElement.getWhElementQuantitySummariesByFilters(dealerId, warehouseId, crewId, warehouseTypeId, elementTypeId, elementModelId, initialDate, finalDate);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getQuantityWarehouseElementsSummariesByFilters(co.com.directv.sdii.model.dto.QuantityWarehouseElementsDTO, co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO)
	 */
	public QuantityWarehouseElementResponse getQuantityWarehouseElementsSummariesByFilters(QuantityWarehouseElementsDTO quantityWarehouseElementsDTO,RequestCollectionInfoDTO requestCollInfo)throws BusinessException{
		return ejbRefElement.getQuantityWarehouseElementsSummariesByFilters(quantityWarehouseElementsDTO,requestCollInfo);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getQuantityWarehouseElementsDetailsByFilters(co.com.directv.sdii.model.dto.QuantityWarehouseElementsDTO, co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO)
	 */
	public QuantityWarehouseElementResponse getQuantityWarehouseElementsDetailsByFilters(QuantityWarehouseElementsDTO quantityWarehouseElementsDTO,RequestCollectionInfoDTO requestCollInfo)throws BusinessException{
		return ejbRefElement.getQuantityWarehouseElementsDetailsByFilters(quantityWarehouseElementsDTO,requestCollInfo);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getMovementTypesActiveStatus()
	 */
	@Override
	public List<MovementTypeVO> getMovementTypesActiveStatus()
			throws BusinessException {
		return ejbRefMovType.getMovementTypesActiveStatus();
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getMovementTypesActiveStatusPage(co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public MovementTypeResponse getMovementTypesActiveStatusPage(RequestCollectionInfo requestCollInfo) throws BusinessException{
		return ejbRefMovType.getMovementTypesActiveStatus(requestCollInfo); 
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getMovementTypesAllStatusPage(java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public MovementTypeResponse getMovementTypesAllStatusPage(String code,RequestCollectionInfo requestCollInfo) throws BusinessException{
		return ejbRefMovType.getMovementTypesAllStatusPage(code,requestCollInfo); 
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getMovementTypesInActiveStatus()
	 */
	@Override
	public List<MovementTypeVO> getMovementTypesInActiveStatus() throws BusinessException {
		return ejbRefMovType.getMovementTypesInActiveStatus();
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getWareHouseElementHistoricalForSerializedElement(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public WareHouseElementHistoricalResponse getWareHouseElementHistoricalForSerializedElement( Long serializedId, RequestCollectionInfo requestCollInfo )throws BusinessException
	{
		return ejbRefElement.getWareHouseElementHistoricalForSerializedElement( serializedId, requestCollInfo );
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getMovedWareHouseElementSerializedByLinkedOrSerialCode(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public MovedElementSerializedResponse getMovedWareHouseElementSerializedByLinkedOrSerialCode(
			String linkedOrSerialCode,Long countryId, RequestCollectionInfo requestCollInfo)
			throws BusinessException{
		return ejbRefElement.getMovedWareHouseElementSerializedByLinkedOrSerialCode( linkedOrSerialCode ,countryId, requestCollInfo);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getSerializedWhElementsByCriteria(java.lang.Long, java.lang.Long, java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public SerializedWhElementsByCriteriaPaginationResponse getSerializedWhElementsByCriteria(Long warehouseId ,Long typeId, Long modelId, RequestCollectionInfo requestCollectionInfo)throws BusinessException{
		return ejbRefElement.getSerializedWhElementsByCriteria(warehouseId, typeId, modelId, requestCollectionInfo);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getSerializedWhElementsByCriteria(java.lang.Long,java.lang.Long,java.lang.Long,java.lang.Long,java.lang.Double)
	 */
	@Override
	public boolean checkShortcomingInWarehouse( Long warehouse,Long user )throws BusinessException
	{
		return ejbRef.checkShortcomingInWarehouse( warehouse, user);
	}
	
	@Override
	public List<ResponseWareHouseIndicatorAlarmDTO> checkWarehouseIndicatorAlarmByDealerIdWarehouseTypeAndUser( Long dealerId,String warehouseType,Long user )throws BusinessException
	{
		return ejbRef.checkWarehouseIndicatorAlarmByDealerIdWarehouseTypeAndUser(dealerId,warehouseType,user);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getWhouseSerializedElementsByElementTypeAndQAWh(co.com.directv.sdii.model.vo.DealerVO, co.com.directv.sdii.model.vo.ElementTypeVO, java.lang.String)
	 */
	@Override
	public List<WarehouseElementVO> getWhouseSerializedElementsByElementTypeAndQAWh(
			DealerVO dealer, co.com.directv.sdii.model.vo.ElementTypeVO elementType, String serialCode)
			throws BusinessException {
		return ejbRefElement.getWhouseSerializedElementsByElementTypeAndQAWh(dealer, elementType, serialCode);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getSerializedsAndWhEntryDateByWareHouseId(co.com.directv.sdii.model.vo.WarehouseVO)
	 */
	@Override
	public List<NotSerPartialRetirementVO> getNotSerPartialRetirementByElementId(Long elementId)  throws BusinessException
	{
	   return ejbRefElement.getNotSerPartialRetirementByElementId( elementId );	
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getWarehouseTypeActive()
	 */
	@Override
	public List<WarehouseTypeVO> getWarehouseTypeActive()
			throws BusinessException {
		return ejbRefType.getWarehouseTypeActive();
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getWarehouseTypeActivePage(co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public WareheouseTypeResponse getWarehouseTypeActivePage(RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		return ejbRefType.getWarehouseTypeActive(requestCollInfo);
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getWarehouseTypePage(java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public WareheouseTypeResponse getWarehouseTypePage(String code,RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		return ejbRefType.getAllWarehouseTypes(code,requestCollInfo);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getWarehousesByCountryIdAndActive(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<WarehouseVO> getWarehousesByCountryIdAndActive(Long countryId,Long dealerId)
			throws BusinessException {
		return ejbRef.getWarehousesByCountryIdAndActive(countryId,dealerId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getWarehousesByCountryIdAndActivePage(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public WareHouseResponse getWarehousesByCountryIdAndActivePage(Long countryId,RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		return ejbRef.getWarehousesByCountryIdAndActive(countryId,requestCollInfo);
	}
	

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#moveSerializedElementsBetweenWareHouses(co.com.directv.sdii.model.dto.ElementMovementDTO)
	 */
	@Override
	public void moveSerializedElementsBetweenWareHouses(MovementElementDTO dto)
			throws BusinessException {
		ejbRefElement.moveSerializedElementBetweenWareHouses(dto);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#moveNotSerializedElementBetweenWareHouses(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.String, java.lang.Double)
	 */
	@Override
	public void moveNotSerializedElementBetweenWareHouses(MovementElementDTO dto) throws BusinessException {
		ejbRefElement.moveNotSerializedElementBetweenWareHouses(dto);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#moveNotSerializedElementBetweenWareHouseQuality(java.util.List, java.util.List, co.com.directv.sdii.model.pojo.Dealer)
	 */
	@Override
	public void moveNotSerializedElementBetweenWareHouseQuality(List<ElementVO> elementsAvailables, List<ElementVO> elementsReturns, Dealer dealer, UserVO user)
		throws BusinessException {
		ejbRefElement.moveNotSerializedElementBetweenWareHouseQuality(elementsAvailables, elementsReturns, dealer, user);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#moveNotSerializedElementBetweenWareHouses(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.String, java.lang.Double)
	 */
	@Override
	public void moveNotSerializedElementBetweenWareHouseQualityAvailable(List<ElementVO> elementsVO, Dealer dealer)
			throws BusinessException {
		ejbRefElement.moveNotSerializedElementBetweenWareHouseQualityAvailable(elementsVO, dealer);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#moveNotSerializedElementBetweenWareHouses(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.String, java.lang.Double)
	 */
	@Override
	public void moveNotSerializedElementBetweenWareHouseQualityReturns(List<ElementVO> elementsVO, Dealer dealer)
			throws BusinessException {
		ejbRefElement.moveNotSerializedElementBetweenWareHouseQualityReturns(elementsVO, dealer);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#moveNotSerializedElementBetweenWareHouses(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.String, java.lang.Double)
	 */
	@Override
	public void moveSerializedElementBetweenWareHouseQualityAvailable(List<ElementVO> elementsVO, Dealer dealer, Long userId)
			throws BusinessException {
		ejbRefElement.moveSerializedElementBetweenWareHouseQualityAvailable(elementsVO, dealer, userId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#moveNotSerializedElementBetweenWareHouses(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.String, java.lang.Double)
	 */
	@Override
	public void moveSerializedElementBetweenWareHouseQualityReturn(List<ElementVO> elementsVO, Dealer dealer, Long userId)
			throws BusinessException {
		ejbRefElement.moveSerializedElementBetweenWareHouseQualityReturn(elementsVO, dealer, userId);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getWhByCrewAndDealerAndWhType(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<WarehouseVO> getWhByCrewAndDealerAndWhType(Long dealerId,Long crewId, Long whTypeId) throws BusinessException {
		return ejbRef.getWhByCrewAndDealerAndWhType(dealerId, crewId, whTypeId);
	}

	@Override
	public WarehouseElementVO getWarehouseElementBySerialOrElementType(
			Long warehouseId, String serial, Long elementTypeId, Double quantity)
			throws BusinessException {
		return ejbRefElement.getWarehouseElementBySerialOrElementType(warehouseId, serial, elementTypeId, quantity);
	}
	
	@Override
	public List<MovementTypeVO> getMovementTypesAtiveByClass(String moveClass)
			throws BusinessException {
		return ejbRefMovType.getMovementTypesAtiveByClass(moveClass);
	}
	
	@Override
	public List<WarehouseTypeVO> getAllWarehouseTypesActive()
			throws BusinessException {
		return ejbRefType.getAllWarehouseTypesActive();
	}


	@Override
	public WarehouseVO getWarehouseByDealerIDIBSCodeDocumentCustomer(
			Long warehouseId, String codeIBS, String documentCustomer)
			throws BusinessException {
		return ejbRef.getWarehouseByDealerIDIBSCodeDocumentCustomer(warehouseId, codeIBS, documentCustomer);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.customer.ICustomerWS#getWarehouseElementsByCustomerIdSerialAndDatesRange(java.lang.Long, java.lang.String, java.util.Date, java.util.Date)
	 */
	@Override
	public CustomerElementsResponse getWarehouseElementsByCustomerIdSerialAndDatesRange(WareHouseElementClientFilterRequestDTO request, RequestCollectionInfo reqCollInfo)
			throws BusinessException {
		return ejbRefElement.getWarehouseElementsByCustomerIdSerialAndDatesRange(request, reqCollInfo);
	}
	
	@Override
	public List<WarehouseElement> getWhElementByElementIdAndNotWhType(Long elementId,
			String whType) throws DAOServiceException, DAOSQLException, BusinessException{
		return ejbRefElement.getWhElementByElementIdAndNotWhType(elementId, whType);
		
	}
	
	@Override
	public List<WarehouseTypeVO> getAllWarehouseTypesByDealerID(Long dealerId)
			throws BusinessException {
		return ejbRefType.getAllWarehouseTypesByDealerID(dealerId);
	}
	
	@Override
	public List<WarehouseVO> getWarehouseByDealerIdCountryIdAndUserId(
			Long dealerID, Long countryID, Long userId, boolean searchSourceWh)
			throws BusinessException {
		return ejbRef.getWarehouseByCountryIdAndOptionalDealerId(dealerID, countryID, userId, searchSourceWh);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getWarehousesByDealerIdAndWhTypeCode(java.lang.Long, java.lang.String)
	 */
	public List<WarehouseVO> getWarehousesByDealerIdAndWhTypeCodeWithBranch(Long dealerId,String whTypeCode,boolean withBranch)  throws BusinessException{
		return ejbRef.getWarehousesByDealerIdAndWhTypeCodeWithBranch(dealerId, whTypeCode,withBranch);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getWarehouseElementValidatingExistenceInMoreElementsRefInc(java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Double)
	 */
	@Override
	public WarehouseElementVO getWarehouseElementValidatingExistenceInMoreElementsRefInc(
			Long refId, Long warehouseId, String serial, Long elementTypeId,
			Double quantity, Long countryId) throws BusinessException {
		return ejbRefElement.getWarehouseElementValidatingExistenceInMoreElementsRefInc(
				refId, warehouseId, serial, elementTypeId, quantity, countryId);
	}


	@Override
	public WareHouseElementResponse getSerializedElementsLastByCriteria(
			WareHouseRequestDTO wareHouseRequestDTO) throws BusinessException {
		return ejbRefElement.getSerializedElementsLastByCriteria(wareHouseRequestDTO);
	}

	@Override
	public WareHouseElementResponse getNotSerializedElementsLastByCriteria(
			WareHouseRequestDTO wareHouseRequestDTO) throws BusinessException {
		
		return ejbRefElement.getNotSerializedElementsLastByCriteria(wareHouseRequestDTO);
	}
	
	
	
	@Override
	public List<DocumentClassVO> getAllDocumentClass() throws BusinessException {
		return ejbDocumentClass.getAllDocumentClass();
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IWarehouseWS#getAllWarehouseTypesActiveAndNotVirtual()
	 */
	@Override
	public List<WarehouseTypeVO> getAllWarehouseTypesActiveAndNotVirtual()
			throws BusinessException {
		return ejbRefType.getAllWarehouseTypesActiveAndNotVirtual();
	}


	@Override
	public WareHouseElementResponse getWarehouseElementsByFiltersAndIsSerializedLast(
			FilterSerializedElementDTO filterSerializedElement,
			RequestCollectionInfo requestCollInfo) throws BusinessException {
		return ejbRefElement.getWarehouseElementsByFiltersAndIsSerializedLast(filterSerializedElement, requestCollInfo);
	}
	
}
