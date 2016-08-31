package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.ElementBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.IbsElementsNotificationBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ElementFacadeBeanLocal;
import co.com.directv.sdii.model.dto.ElementSerializedLinkUnLinkFilterVO;
import co.com.directv.sdii.model.dto.ElementSerializedLinkUnLinkVO;
import co.com.directv.sdii.model.dto.LinkSerializedFilterVO;
import co.com.directv.sdii.model.dto.LinkSerializedVO;
import co.com.directv.sdii.model.dto.QAItemDTO;
import co.com.directv.sdii.model.dto.UpdateLinkedSerialsDTO;
import co.com.directv.sdii.model.dto.collection.CustomerElementsResponse;
import co.com.directv.sdii.model.dto.collection.NotSerializedAjustmentCollDTO;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.pojo.collection.ElementResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.ElementClassVO;
import co.com.directv.sdii.model.vo.ElementTypeVO;
import co.com.directv.sdii.model.vo.ElementVO;
import co.com.directv.sdii.model.vo.ImportLogVO;
import co.com.directv.sdii.model.vo.NotSerializedVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.model.vo.UserVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD Element
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.ElementFacadeLocal
 */
@Stateless(name="ElementFacadeLocal",mappedName="ejb/ElementFacadeLocal")
public class ElementFacadeBean implements ElementFacadeBeanLocal {

		
    @EJB(name="ElementBusinessBeanLocal", beanInterface=ElementBusinessBeanLocal.class)
    private ElementBusinessBeanLocal businessElement;
    
    @EJB(name="IbsElementsNotificationBusinessLocal", beanInterface=IbsElementsNotificationBusinessLocal.class)
    private IbsElementsNotificationBusinessLocal businessIbsElementsNotification;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ElementFacadeLocal#getAllElements()
     */
    public List<ElementVO> getAllElements(Long countryId) throws BusinessException {
    	return businessElement.getAllElements(countryId);
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ElementFacadeLocal#getElementsByID(java.lang.Long)
     */
    public ElementVO getElementByID(Long id) throws BusinessException {
    	return businessElement.getElementByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ElementFacadeLocal#createElement(co.com.directv.sdii.model.vo.ElementVO)
	 */
	public void createElement(ElementVO obj) throws BusinessException {
		businessElement.createElement(obj);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementFacadeBeanLocal#createNotSerializedElement(co.com.directv.sdii.model.vo.ElementVO, co.com.directv.sdii.model.vo.NotSerializedVO)
	 */
	@Override
	public void createNotSerializedElement(ElementVO element,
			NotSerializedVO noSerializado, Long impLogID) throws BusinessException {
		businessElement.createNotSerializedElement(element,noSerializado, impLogID);
		
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementFacadeBeanLocal#createSerializedElement(co.com.directv.sdii.model.vo.ElementVO, co.com.directv.sdii.model.vo.SerializedVO)
	 */
	@Override
	public void createSerializedElement(ElementVO element,
			SerializedVO serializado) throws BusinessException {
		businessElement.createSerializedElement(element,serializado);
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementFacadeBeanLocal#getElementsByElementType(co.com.directv.sdii.model.vo.ElementTypeVO)
	 */
	@Override
	public ElementResponse getElementsByElementType(ElementTypeVO elementType, RequestCollectionInfo requestCollInfo) throws BusinessException {
		return businessElement.getElementsByElementType(elementType, requestCollInfo);
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementFacadeBeanLocal#getElementsHistoryOnBuildingCode(java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public CustomerElementsResponse getElementsHistoryOnBuildingCode(String ibsCode,Long countryId, RequestCollectionInfo requestCollInfo) throws BusinessException {
		return businessElement.getElementsHistoryOnBuildingCode(ibsCode,countryId, requestCollInfo);
		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementFacadeBeanLocal#registerSerializedElementQualityControlAndByCountry(co.com.directv.sdii.model.vo.ImportLogVO, co.com.directv.sdii.model.vo.DealerVO, java.util.List, co.com.directv.sdii.model.vo.UserVO, java.lang.Long)
	 */
	@Override
	public void registerSerializedElementQualityControlAndByCountry(ImportLogVO importLog,
			DealerVO logisticOperator, List<QAItemDTO> qaItemDTOs,UserVO user,Long country)
			throws BusinessException {
		businessElement.registerSerializedElementQualityControlAndByCountry(importLog, logisticOperator, qaItemDTOs,user,country);
		
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementFacadeBeanLocal#getWhElementsAndNotSerPartRetByWarehouseId(java.lang.Long)
	 */
	@Override
	public NotSerializedAjustmentCollDTO getWhElementsAndNotSerPartRetByWarehouseId(Long warehouseId, RequestCollectionInfoDTO requestCollInfo)throws BusinessException{
		return businessElement.getWhElementsAndNotSerPartRetByWarehouseId(warehouseId, requestCollInfo);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementFacadeBeanLocal#getElementsToLinkSerializedElements(co.com.directv.sdii.model.dto.LinkSerializedFilterVO)
	 */
	@Override
	public LinkSerializedVO getElementsToLinkSerializedElements(LinkSerializedFilterVO criteria) throws BusinessException{
		return businessElement.getElementsToLinkSerializedElements(criteria);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementFacadeBeanLocal#linkSerializedElements(java.lang.Long,java.lang.Long)
	 */
	@Override
	public void linkSerializedElements(Long elementOneId, Long elementTwoId) throws BusinessException{
		businessElement.linkSerializedElements(elementOneId, elementTwoId);
	}
	
	@Override
	public void changeElementModel(Long idElement,
			String codTypeElement, Long idElementLinkElement,
			String codTypeElementLinkElement) throws BusinessException {
		businessElement.changeElementModel(idElement, codTypeElement, idElementLinkElement, codTypeElementLinkElement);
	}


	@Override
	public ElementSerializedLinkUnLinkVO getElementSerializedToLinkUnLink(
			ElementSerializedLinkUnLinkFilterVO elementSerializedLinkUnLinkFilterVO)
			throws BusinessException {
		return businessElement.getElementSerializedToLinkUnLink(elementSerializedLinkUnLinkFilterVO);
	}


	@Override
	public void updateLinkedSerials(UpdateLinkedSerialsDTO updateLinkedSerialsDTO)throws BusinessException {
		this.businessElement.updateLinkedSerials(updateLinkedSerialsDTO);
	}


	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementFacadeBeanLocal#sendStatusCmdToIBS(java.lang.Long)
	 * 
	 * Servicio utilizado para lanzar la ejecución del proceso 'Comandos de ibs' manualmente.
	 */
	@Override
	public void sendStatusCmdToIBS(Long countryId) throws BusinessException {
		//this.businessIbsElementsNotification.sendStatusCmdToIBS(countryId);
		//Req-0098 - Paralelismo de Inventarios
		this.businessIbsElementsNotification.sendToMovCmdQueue(countryId);
		
	}

}
