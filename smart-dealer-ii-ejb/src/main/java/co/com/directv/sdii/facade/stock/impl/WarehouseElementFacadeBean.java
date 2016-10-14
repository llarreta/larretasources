package co.com.directv.sdii.facade.stock.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import co.com.directv.sdii.ejb.business.stock.MovementElementBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal;
import co.com.directv.sdii.model.dto.MassiveMovementBetweenWareHouseDTO;
import co.com.directv.sdii.model.dto.MovementElementDTO;
import co.com.directv.sdii.model.dto.QuantityWarehouseElementsDTO;
import co.com.directv.sdii.model.dto.WareHouseElementClientFilterRequestDTO;
import co.com.directv.sdii.model.dto.WareHouseRequestDTO;
import co.com.directv.sdii.model.dto.collection.CustomerElementsResponse;
import co.com.directv.sdii.model.dto.collection.QuantityWarehouseElementResponse;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.WarehouseElement;
import co.com.directv.sdii.model.pojo.WhElementSearchFilter;
import co.com.directv.sdii.model.pojo.collection.MovedElementSerializedResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SerializedWhElementsByCriteriaPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.WareHouseElementCustomerResponse;
import co.com.directv.sdii.model.pojo.collection.WareHouseElementHistoricalResponse;
import co.com.directv.sdii.model.pojo.collection.WareHouseElementResponse;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.ElementTypeVO;
import co.com.directv.sdii.model.vo.ElementVO;
import co.com.directv.sdii.model.vo.MassiveMovementVO;
import co.com.directv.sdii.model.vo.NotSerPartialRetirementVO;
import co.com.directv.sdii.model.vo.NotSerializedVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.model.vo.UserVO;
import co.com.directv.sdii.model.vo.WHElementQtySummaryVO;
import co.com.directv.sdii.model.vo.WarehouseElementQuantityVO;
import co.com.directv.sdii.model.vo.WarehouseElementVO;
import co.com.directv.sdii.model.vo.WarehouseVO;
import co.com.directv.sdii.model.vo.WhElementSearchFilterVO;
import co.com.directv.sdii.reports.dto.FilterSerializedElementDTO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD WarehouseElement
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.WarehouseElementFacadeLocal
 */
@Stateless(name="WarehouseElementFacadeLocal",mappedName="ejb/WarehouseElementFacadeLocal")
public class WarehouseElementFacadeBean implements WarehouseElementFacadeBeanLocal {

		
    @EJB(name="WarehouseElementBusinessBeanLocal", beanInterface=WarehouseElementBusinessBeanLocal.class)
    private WarehouseElementBusinessBeanLocal businessWarehouseElement;

    @EJB(name = "MovementElementBusinessBeanLocal", beanInterface = MovementElementBusinessBeanLocal.class)
	private MovementElementBusinessBeanLocal businessMovementElement;
	
    /* (non-Javadoc)
     * @see #{$business_package_name$}.WarehouseElementFacadeLocal#getAllWarehouseElements()
     */
    public List<WarehouseElementVO> getAllWarehouseElements() throws BusinessException {
    	return businessWarehouseElement.getAllWarehouseElements();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.WarehouseElementFacadeLocal#getWarehouseElementsByID(java.lang.Long)
     */
    public WarehouseElementVO getWarehouseElementByID(Long id) throws BusinessException {
    	return businessWarehouseElement.getWarehouseElementByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.WarehouseElementFacadeLocal#createWarehouseElement(co.com.directv.sdii.model.vo.WarehouseElementVO)
	 */
	public void createWarehouseElement(WarehouseElementVO obj) throws BusinessException {
		businessWarehouseElement.createWarehouseElement(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.WarehouseElementFacadeLocal#updateWarehouseElement(co.com.directv.sdii.model.vo.WarehouseElementVO)
	 */
	public void updateWarehouseElement(WarehouseElementVO obj) throws BusinessException {
		businessWarehouseElement.updateWarehouseElement(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.WarehouseElementFacadeLocal#deleteWarehouseElement(co.com.directv.sdii.model.vo.WarehouseElementVO)
	 */
	public void deleteWarehouseElement(WarehouseElementVO obj) throws BusinessException {
		businessWarehouseElement.deleteWarehouseElement(obj);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal#moveSerializedElementToWareHouse(co.com.directv.sdii.model.dto.ElementMovementDTO)
	 */
	public void moveSerializedElementToWareHouse(MovementElementDTO dto)
			throws BusinessException {
		businessMovementElement.moveSerializedElementToWarehouse(dto);
	}
	
	public MassiveMovementVO moveElementsFromWHCrewToWHCompany(List<DealerVO> companies, UserVO user) throws BusinessException {
		return businessWarehouseElement.moveElementsFromWHCrewToWHCompany(companies, user);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal#moveSerializedElementsToWareHouse(java.lang.Long, java.lang.Long, java.util.List, java.lang.String, java.lang.String)
	 */
	public void moveSerializedElementsToWareHouse(Long sourceWhId, Long targetWsId, List<SerializedVO> serializedList, String movTypeCodeE, String movTypeCodeS) throws BusinessException {
		//businessWarehouseElement.moveSerializedElementsToWareHouse(sourceWhId, targetWsId, serializedList, movTypeCodeE, movTypeCodeS);
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal#getWarehouseElementsByFilters(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long)
	 */
	public WareHouseElementResponse getWarehouseElementsByFilters(
			Long wareHouseId, Long dealerId, Long branchDealerId, Long crewId,
			Long warehouseTypeId, String elementTypeCode, Long elementModelId, RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		return businessWarehouseElement.getWarehouseElementsByFilters(wareHouseId, dealerId, branchDealerId, crewId, warehouseTypeId, elementTypeCode, elementModelId, requestCollInfo);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal#getHistoryElementsByCustomerIBSCodeDocTypeIdAndDocNumber(java.lang.String, java.lang.Long, java.lang.String, java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public CustomerElementsResponse getElementsByCustomerIBSCodeDocTypeIdAndDocNumber(
			String customerIbsCode, Long documentTypeId,
			String customerDocumentNumber, Long countryId,
			RequestCollectionInfo requestCollInfo) throws BusinessException{
		return businessWarehouseElement.getElementsByCustomerIBSCodeDocTypeIdAndDocNumber(customerIbsCode, documentTypeId, customerDocumentNumber, countryId, requestCollInfo);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal#getWarehouseElementsByCustomerIBSCodeDocTypeIdAndDocNumber(java.lang.String, java.lang.Long, java.lang.String)
	 */
	public QuantityWarehouseElementResponse getWarehouseElementsByWarehouse(WhElementSearchFilter whElementSearchFilter,RequestCollectionInfo requestCollInfo,boolean doCount) throws BusinessException {
		return businessWarehouseElement.getWarehouseElementsByWarehouse(whElementSearchFilter, requestCollInfo, doCount);
	}	
	
	
	public WareHouseElementCustomerResponse getWarehouseElementsByWarehouseCustomerActual(WareHouseElementClientFilterRequestDTO request,RequestCollectionInfo requestCollInfo) throws BusinessException{
		return businessWarehouseElement.getWarehouseElementsByWarehouseCustomerActual(request, requestCollInfo);
	}

	/**
	 * Metodo: Obtiene los registros de todos los objetos NotSerialized propios de un WareHouse
	 * @return Lista con los registros de todos los objetos NotSerialized propios de un WareHouse
	 * @throws BusinessException en caso de error al tratar de ejecutar la tarea
	 * @author garciniegas
	 */
	public List<NotSerializedVO> getNotSerializedsByWareHouseId( WarehouseVO warehouseId ) throws BusinessException
	{
		return businessWarehouseElement.getNotSerializedsByWareHouseId( warehouseId );
	}
	
	/**
	 * Metodo: UC Inv11, retorna los elementos no serializados de la Bodega de Calidad, segun el dealerId (Operador Logistico.)
	 * @param dealerId
	 * @return
	 * @throws BusinessException
	 * @author hcorredor
	 */
	public WareHouseElementResponse getWarehouseQANOTSerializedElements( Long dealerId, RequestCollectionInfo requestCollInfo ) throws BusinessException{
		return businessWarehouseElement.getWarehouseQANOTSerializedElements(dealerId, requestCollInfo);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal#getWhElementsByChriteria(java.lang.Long, java.lang.String,java.lang.Long)
	 */
	@Override
	public WareHouseElementResponse getWhElementsByCriteria(
			Long warehouseId, String isSerialized, Long elementId, RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		return businessWarehouseElement.getWhElementsByCriteria(warehouseId, isSerialized, elementId, requestCollInfo);
	}
	
	/**
	 * Metodo: Permite realizar el movimiento masivo de elementos no serializados entre bodegas
	 * @param moveObject El objeto que encapsula toda la informacion requerida en el movimiento masivo
	 * @throws BusinessException Error cuando se realiza la consulta de los elementos
	 * @author garciniegas 
	 */
	public void massiveMovementOfNotSerializedElementsBetweenWareHouse( MassiveMovementBetweenWareHouseDTO moveObject)throws BusinessException
	{
		businessWarehouseElement.massiveMovementOfNotSerializedElementsBetweenWareHouse( moveObject, null, null,moveObject.getUserId());
	}

	 /*
	  * (non-Javadoc)
	  * @see co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal#getSerializedsByWareHouseId(co.com.directv.sdii.model.vo.WarehouseVO)
	  */
	public List<SerializedVO> getSerializedsByWareHouseId( WarehouseVO warehouseId ) throws BusinessException
	{
		return businessWarehouseElement.getSerializedsByWareHouseId( warehouseId );
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal#getWhElementsByWhIdAndElementTypeCodeAndSerials(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.String)
	 */
	public List<WarehouseElementVO> getWhElementsByWhIdAndElementTypeCodeAndSerials(
			Long warehouseId, Long elementTypeId1, Long elementTypeId2,
			String serialEl1, String serialEl2) throws BusinessException {
		return businessWarehouseElement.getWhElementsByWhIdAndElementTypeCodeAndSerials(warehouseId, elementTypeId1, elementTypeId2, serialEl1, serialEl2);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal#separateLinkedSerializedElementsInWh(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	public void separateLinkedSerializedElementsInWh(Long warehouseId,
			Long whElementMasterId, Long whElementLinkedId)
			throws BusinessException {
		businessWarehouseElement.separateLinkedSerializedElementsInWh(warehouseId, whElementMasterId, whElementLinkedId);
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal#massiveMovementOfSerializedElementsBetweenWareHouse(co.com.directv.sdii.model.dto.MassiveMovementBetweenWareHouseDTO)
	 */
	public void massiveMovementOfSerializedAndLinkedElementsBetweenWareHouse(MassiveMovementBetweenWareHouseDTO moveObject, Long userId)throws BusinessException
	{
		businessWarehouseElement.massiveMovementOfSerializedAndLinkedElementsBetweenWareHouse( moveObject, null, null, userId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal#getQualityControlWhNotSerElementsByDealerId(java.lang.Long)
	 */
	public List<WarehouseElementVO> getQualityControlWhNotSerElementsByDealerId(
			Long dealerId) throws BusinessException {
		return businessWarehouseElement.getQualityControlWhNotSerElementsByDealerId(dealerId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal#getWhElementQuantitySummariesByFilters(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.util.Date, java.util.Date)
	 */
	@Override
	public List<WHElementQtySummaryVO> getWhElementQuantitySummariesByFilters(
			Long dealerId, Long warehouseId, Long crewId, Long warehouseTypeId,
			Long elementTypeId, Long elementModelId, Date initialDate,
			Date finalDate) throws BusinessException {
		return businessWarehouseElement.getWhElementQuantitySummariesByFilters(dealerId, warehouseId, crewId, warehouseTypeId, elementTypeId, elementModelId, initialDate, finalDate);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal#getQuantityWarehouseElementsSummariesByFilters(co.com.directv.sdii.model.dto.QuantityWarehouseElementsDTO, co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO)
	 */
	public QuantityWarehouseElementResponse getQuantityWarehouseElementsSummariesByFilters(QuantityWarehouseElementsDTO quantityWarehouseElementsDTO,RequestCollectionInfoDTO requestCollInfo) throws BusinessException{
		return businessWarehouseElement.getQuantityWarehouseElementsSummariesByFilters(quantityWarehouseElementsDTO,requestCollInfo);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal#getQuantityWarehouseElementsDetailsByFilters(co.com.directv.sdii.model.dto.QuantityWarehouseElementsDTO, co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO)
	 */
	public QuantityWarehouseElementResponse getQuantityWarehouseElementsDetailsByFilters(QuantityWarehouseElementsDTO quantityWarehouseElementsDTO,RequestCollectionInfoDTO requestCollInfo) throws BusinessException{
		return businessWarehouseElement.getQuantityWarehouseElementsDetailsByFilters(quantityWarehouseElementsDTO,requestCollInfo);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal#getWarehouseElementsBySearchFilter(co.com.directv.sdii.model.vo.WhElementSearchFilterVO)
	 */
	@Override
	public WareHouseElementResponse getWarehouseElementsBySearchFilter(
			WhElementSearchFilterVO whElementSearchFilterVO, RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		return businessWarehouseElement.getWarehouseElementsBySearchFilter(whElementSearchFilterVO, requestCollInfo);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal#getWarehouseElementsBySearchFilter(co.com.directv.sdii.model.vo.WhElementSearchFilterVO)
	 */
	@Override
	public WareHouseElementHistoricalResponse getWareHouseElementHistoricalForSerializedElement( Long serializedId, RequestCollectionInfo requestCollInfo )throws BusinessException
	{
		return businessWarehouseElement.getWareHouseElementHistoricalForSerializedElement( serializedId, requestCollInfo );
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal#getMovedWareHouseElementSerializedByLinkedOrSerialCode(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public MovedElementSerializedResponse getMovedWareHouseElementSerializedByLinkedOrSerialCode(
			String linkedOrSerialCode,Long countryId, RequestCollectionInfo requestCollInfo)
			throws BusinessException{
		return businessWarehouseElement.getMovedWareHouseElementSerializedByLinkedOrSerialCode( linkedOrSerialCode ,countryId, requestCollInfo);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal#getSerializedWhElementsByCriteria(java.lang.Long,java.lang.Long,java.lang.Long,java.lang.Long,java.lang.Double)
	 */
	public SerializedWhElementsByCriteriaPaginationResponse getSerializedWhElementsByCriteria(Long warehouseId , Long typeId, Long modelId, RequestCollectionInfo requestCollectionInfo)throws BusinessException{
		return businessWarehouseElement.getSerializedWhElementsByCriteria(warehouseId,typeId,modelId,requestCollectionInfo);
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal#getWhouseSerializedElementsByElementTypeAndQAWh(co.com.directv.sdii.model.vo.DealerVO, co.com.directv.sdii.model.vo.ElementTypeVO, java.lang.String)
	 */
	public List<WarehouseElementVO> getWhouseSerializedElementsByElementTypeAndQAWh(
			DealerVO dealer, ElementTypeVO elementType, String serialCode)
			throws BusinessException {
		return businessWarehouseElement.getWhouseSerializedElementsByElementTypeAndQAWh(dealer,elementType,serialCode);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal#getCurrentQuantityInWarehouseByElementType(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	public WarehouseElementQuantityVO getCurrentQuantityInWarehouseByElementType(
			Long warehouseId, Long elementTypeId, Long elementModelId)
			throws BusinessException {
		return businessWarehouseElement.getCurrentQuantityInWarehouseByElementType(warehouseId, elementTypeId, elementModelId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal#getSerializedsAndWhEntryDateByWareHouseId(co.com.directv.sdii.model.vo.WarehouseVO)
	 */
	@Override
	public List<SerializedVO> getSerializedsAndWhEntryDateByWareHouseId( WarehouseVO warehouseId ) throws BusinessException{
		return businessWarehouseElement.getSerializedsAndWhEntryDateByWareHouseId(warehouseId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal#getSerializedsAndWhEntryDateByWareHouseId(co.com.directv.sdii.model.vo.WarehouseVO)
	 */
	@Override
	public List<NotSerPartialRetirementVO> getNotSerPartialRetirementByElementId(Long elementId)  throws BusinessException
	{
		return businessWarehouseElement.getNotSerPartialRetirementByElementId(elementId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal#moveSerializedElementBetweenWareHouses(co.com.directv.sdii.model.dto.ElementMovementDTO)
	 */
	@Override
	public void moveSerializedElementBetweenWareHouses(MovementElementDTO dto) throws BusinessException {
		businessMovementElement.moveSerializedElementBetweenWarehouse(dto);
	}


	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal#moveNotSerializedElementBetweenWareHouses(co.com.directv.sdii.model.dto.ElementMovementDTO)
	 */
	@Override
	public void moveNotSerializedElementBetweenWareHouses(MovementElementDTO dto) throws BusinessException {
		businessMovementElement.moveNotSerializedElementBetweenWarehouse(dto);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal#moveNotSerializedElementBetweenWareHouseQuality(java.util.List, java.util.List, co.com.directv.sdii.model.pojo.Dealer)
	 */
	@Override
	public void moveNotSerializedElementBetweenWareHouseQuality(
			List<ElementVO> elementsAvailables,
			List<ElementVO> elementsReturns, Dealer dealer, UserVO userVO) throws BusinessException {
		businessWarehouseElement.moveNotSerializedElementBetweenWareHouseQuality(elementsAvailables, elementsReturns, dealer, userVO, null, null);
	}

	@Override
	public void moveNotSerializedElementBetweenWareHouseQualityAvailable(List<ElementVO> elementsVO, Dealer dealer) throws BusinessException {
		businessWarehouseElement.moveNotSerializedElementBetweenWareHouseQualityAvailable(elementsVO, dealer, null, null, null);
	}

	@Override
	public void moveNotSerializedElementBetweenWareHouseQualityReturns(List<ElementVO> elementsVO, Dealer dealer) throws BusinessException {
		businessWarehouseElement.moveNotSerializedElementBetweenWareHouseQualityReturns(elementsVO, dealer, null, null, null);
	}

	@Override
	public WareHouseElementResponse getWarehouseQASerializedElements(
			Dealer dealer, String elementType, String serialCode,
			RequestCollectionInfo requestCollInfo) throws BusinessException {
		
		return businessWarehouseElement.getWarehouseQASerializedElements(dealer, elementType, serialCode, requestCollInfo);
	}

	@Override
	public void moveSerializedElementBetweenWareHouseQualityAvailable(
			List<ElementVO> elementsVO, Dealer dealer, Long userId) throws BusinessException {
		businessWarehouseElement.moveSerializedElementBetweenWareHouseQualityAvailable(elementsVO, dealer, null, null, userId);
	}


	@Override
	public void moveSerializedElementBetweenWareHouseQualityReturn(
			List<ElementVO> elementsVO, Dealer dealer, Long userId) throws BusinessException {
		businessWarehouseElement.moveSerializedElementBetweenWareHouseQualityReturn(elementsVO, dealer, null, null, userId);
		
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal#getWarehouseElementBySerialOrElementType(java.lang.Long, java.lang.String, java.lang.Long)
	 */
	@Override
	public WarehouseElementVO getWarehouseElementBySerialOrElementType(
			Long warehouseId, String serial, Long elementTypeId, Double quantity)
			throws BusinessException {
		return businessWarehouseElement.getWarehouseElementBySerialOrElementType(warehouseId, serial, elementTypeId, quantity);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal#getWarehouseElementsByCustomerIdSerialAndDatesRange(java.lang.Long, java.lang.String, java.util.Date, java.util.Date)
	 */
	@Override
	public CustomerElementsResponse getWarehouseElementsByCustomerIdSerialAndDatesRange(WareHouseElementClientFilterRequestDTO request, RequestCollectionInfo reqCollInfo) throws BusinessException {
		return businessWarehouseElement.getWarehouseElementsByCustomerIdSerialAndDatesRange(request, reqCollInfo);
	}


	@Override
	public List<WarehouseElement> getWhElementByElementIdAndNotWhType(
			Long elementId, String whType) throws BusinessException {
		return businessWarehouseElement.getWhElementByElementIdAndNotWhType(elementId, whType);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal#getWarehouseElementValidatingExistenceInMoreElementsRefInc(java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Double)
	 */
	@Override
	public WarehouseElementVO getWarehouseElementValidatingExistenceInMoreElementsRefInc(
			Long refId, Long warehouseId, String serial, Long elementTypeId,
			Double quantity, Long countryId) throws BusinessException {
		return businessWarehouseElement.getWarehouseElementValidatingExistenceInMoreElementsRefInc(
				refId, warehouseId, serial, elementTypeId, quantity, countryId);
	}


	@Override
	public WareHouseElementResponse getSerializedElementsLastByCriteria(
			WareHouseRequestDTO wareHouseRequestDTO) throws BusinessException {
		return businessWarehouseElement.getSerializedElementsLastByCriteria(wareHouseRequestDTO);
	}

	@Override
	public WareHouseElementResponse getNotSerializedElementsLastByCriteria(
			WareHouseRequestDTO wareHouseRequestDTO)
			throws BusinessException {
		return businessWarehouseElement.getNotSerializedElementsLastByCriteria(wareHouseRequestDTO);
	}	
	

	@Override
	public WareHouseElementResponse getWarehouseElementsByFiltersAndIsSerializedLast(
			FilterSerializedElementDTO filterSerializedElement,
			RequestCollectionInfo requestCollInfo) throws BusinessException {
		return businessWarehouseElement.getWarehouseElementsByFiltersAndIsSerializedLast(filterSerializedElement, requestCollInfo);
	}
	
}
