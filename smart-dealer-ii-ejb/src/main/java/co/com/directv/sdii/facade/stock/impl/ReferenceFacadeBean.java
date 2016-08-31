package co.com.directv.sdii.facade.stock.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal;
import co.com.directv.sdii.model.dto.FilterReferencesToPrintDTO;
import co.com.directv.sdii.model.dto.InfoToPrintReferencesDTO;
import co.com.directv.sdii.model.dto.ReferenceShipmentDTO;
import co.com.directv.sdii.model.dto.ReferencesFilterDTO;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.collection.NotSerializedElementInReferencePaginationResponse;
import co.com.directv.sdii.model.pojo.collection.NotSerializedElementInSelectReferenceToPrintPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.ReferenceResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SerializedElementInReferencePaginationResponse;
import co.com.directv.sdii.model.vo.DeliveryVO;
import co.com.directv.sdii.model.vo.RefConfirmationVO;
import co.com.directv.sdii.model.vo.RefInconsistencyVO;
import co.com.directv.sdii.model.vo.RefRecieveDataVO;
import co.com.directv.sdii.model.vo.ReferenceElementItemVO;
import co.com.directv.sdii.model.vo.ReferenceModificationVO;
import co.com.directv.sdii.model.vo.ReferenceStatusVO;
import co.com.directv.sdii.model.vo.ReferenceVO;
import co.com.directv.sdii.model.vo.SpecialCommentVO;
import co.com.directv.sdii.model.vo.UserVO;
import co.com.directv.sdii.model.vo.WarehouseVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD Reference
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeLocal
 */
@Stateless(name="ReferenceFacadeLocal",mappedName="ejb/ReferenceFacadeLocal")
public class ReferenceFacadeBean implements ReferenceFacadeBeanLocal {

		
    @EJB(name="ReferenceBusinessBeanLocal", beanInterface=ReferenceBusinessBeanLocal.class)
    private ReferenceBusinessBeanLocal businessReference;
   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ReferenceFacadeLocal#getAllReferences()
     */
    public List<ReferenceVO> getAllReferences() throws BusinessException {
    	return businessReference.getAllReferences();
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getAllReferencesAndByCountry(java.lang.Long)
     */
    public List<ReferenceVO> getAllReferencesAndByCountry(Long country) throws BusinessException {
    	return businessReference.getAllReferencesAndByCountry(country);
    }
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ReferenceFacadeLocal#getReferencesByID(java.lang.Long)
     */
    public ReferenceVO getReferenceByID(Long id) throws BusinessException {
    	return businessReference.getReferenceByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ReferenceFacadeLocal#createReference(co.com.directv.sdii.model.vo.ReferenceVO)
	 */
	public void createReference(ReferenceVO obj) throws BusinessException {
		businessReference.createReference(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ReferenceFacadeLocal#updateReference(co.com.directv.sdii.model.vo.ReferenceVO)
	 */
	public void updateReference(ReferenceVO obj) throws BusinessException {
		businessReference.updateReference(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ReferenceFacadeLocal#deleteReference(co.com.directv.sdii.model.vo.ReferenceVO)
	 */
	public void deleteReference(ReferenceVO obj) throws BusinessException {
		businessReference.deleteReference(obj);
	}
	
	public Long deleteReferenceLogic(Long referenceID, Long userDelete) throws BusinessException {
		return businessReference.deleteReferenceLogic(referenceID, userDelete);
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getReferencesByReferenceStatusAndByCountry(co.com.directv.sdii.model.vo.ReferenceStatusVO, java.lang.Long)
	 */
	public List<ReferenceVO> getReferencesByReferenceStatusAndByCountry(
			ReferenceStatusVO refStatus,Long country) throws BusinessException {
		return businessReference.getReferencesByReferenceStatusAndByCountry(refStatus,country);
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getReferencesByReferenceStatusAndWhAndByCountry(co.com.directv.sdii.model.vo.ReferenceStatusVO, co.com.directv.sdii.model.vo.WarehouseVO, co.com.directv.sdii.model.vo.WarehouseVO, java.lang.Long)
	 */
	public List<ReferenceVO> getReferencesByReferenceStatusAndWhAndByCountry(
			ReferenceStatusVO refStatus, WarehouseVO whSource,
			WarehouseVO whTarget,Long country) throws BusinessException {
		return businessReference.getReferencesByReferenceStatusAndWhAndByCountry(refStatus,whSource,whTarget,country);
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getReferencesBySourceAndTargetWareHouseAndByCountry(co.com.directv.sdii.model.vo.WarehouseVO, co.com.directv.sdii.model.vo.WarehouseVO, java.lang.Long)
	 */
	public List<ReferenceVO> getReferencesBySourceAndTargetWareHouseAndByCountry(
			WarehouseVO whSource, WarehouseVO whTarget,Long country)
			throws BusinessException {
		return businessReference.getReferencesBySourceAndTargetWareHouseAndByCountry(whSource,whTarget,country);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getReferencesByIdOrSourceWhOrTargetWh(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	public List<ReferenceVO> getReferencesByIdOrSourceWhOrTargetWh(
			Long referenceId, Long sourceWhId, Long targetWhId, Date referenceCreationDate, String recorderUserName)
			throws BusinessException {
		return businessReference.getReferencesByIdOrSourceWhOrTargetWh(referenceId, sourceWhId, targetWhId, referenceCreationDate, recorderUserName);
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getReferencesBySourceWareHouseAndByCountry(co.com.directv.sdii.model.vo.WarehouseVO, java.lang.Long)
	 */
	public List<ReferenceVO> getReferencesBySourceWareHouseAndByCountry(WarehouseVO whSource,Long country)
			throws BusinessException {
		return businessReference.getReferencesBySourceWareHouseAndByCountry(whSource,country);
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getReferencesBySourceWareHouseAndRefStatusInconsistentAndByCountry(co.com.directv.sdii.model.vo.WarehouseVO, java.lang.Long)
	 */
	public List<ReferenceVO> getReferencesBySourceWareHouseAndRefStatusInconsistentAndByCountry(
			WarehouseVO whSource,Long country) throws BusinessException {
		return businessReference.getReferencesBySourceWareHouseAndRefStatusInconsistentAndByCountry(whSource,country);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getAllReferencesByIdSourceTargetWareHouseStatus(co.com.directv.sdii.model.dto.ReferencesFilterToPrintDTO,co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public NotSerializedElementInSelectReferenceToPrintPaginationResponse getAllReferencesByIdSourceTargetWareHouseStatus(
			FilterReferencesToPrintDTO referencesFilterToPrintDTO, RequestCollectionInfo requestCollectionInfo)
			throws BusinessException{
				return businessReference.getAllReferencesByIdSourceTargetWareHouseStatus(referencesFilterToPrintDTO,requestCollectionInfo);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getInfoToPrintReferencesById(java.lang.Long)
	 */
	public InfoToPrintReferencesDTO getInfoToPrintReferencesById(Long referenceId)throws BusinessException{
				return businessReference.getInfoToPrintReferencesById(referenceId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getReferencesByTargetWareHouseAndByCountry(co.com.directv.sdii.model.vo.WarehouseVO, java.lang.Long)
	 */
	public List<ReferenceVO> getReferencesByTargetWareHouseAndByCountry(WarehouseVO whTarget,Long country)
			throws BusinessException {
		return businessReference.getReferencesByTargetWareHouseAndByCountry(whTarget,country);
	}
	
	/**
	 * Metodo: Registra el envio de una remision creada
	 * @param referencesShipment ReferenceShipmentDTO - El objeto DTO que encapsula la data del envio
	 * de una remision dada. 
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author garciniegas 
	 */
	public void registerReferenceShipment( ReferenceShipmentDTO referencesShipment,UserVO user) throws BusinessException {
		businessReference.registerReferenceShipment( referencesShipment,user );
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#updateReferenceAndElements(co.com.directv.sdii.model.vo.ReferenceVO, java.util.List, boolean, java.lang.Long)
	 */
	public void updateReferenceAndElements(ReferenceVO reference,
			List<ReferenceElementItemVO> referenceElements, boolean isFinished, Long userId)
			throws BusinessException {
		businessReference.updateReferenceAndElements(reference, referenceElements, isFinished, userId);
	}
	
	/**
	 * Metodo: Regresa un <b>posible</b> conjunto de remisiones definidas por un fitro basado
	 * en el nm de remision y el codigo de pais
	 * @param referenceId Long - El identificador de la remision
	 * @param countryCode Long - El codigo del pais
	 * @throws BusinessException en caso de error al tratar de listar las remisiones	 
	 * @author garciniegas 
	 */
    public List<ReferenceVO>getReferenceByIdAndCountryCode( Long referenceId,Long countryCode )throws BusinessException
    {
    	return businessReference.getReferenceByIdAndCountryCode( referenceId,countryCode);
    }
    
    /* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#validateElementsInWHQuantities(java.util.List<co.com.directv.sdii.model.vo.ReferenceElementItemVO>, java.lang.Long)
	 */
    public void validateElementsInWHQuantities(List<ReferenceElementItemVO> referencesElementItems , Long sourceWareHouseId) throws BusinessException{
    	businessReference.validateElementsInWHQuantities(referencesElementItems, sourceWareHouseId);
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#registerReferenceDeliveryAndElementMovement(java.lang.Long, java.util.Date, java.lang.Long, java.lang.String)
	 */
    @Override
	public void registerReferenceDeliveryAndElementMovement(Long referenceId,
			Date deliveryDate, Long targetEmployeeId, String comments, Double sentUnits, Long userId)
			throws BusinessException {
		businessReference.registerReferenceDeliveryAndElementMovement(referenceId, deliveryDate, targetEmployeeId, comments, sentUnits, userId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getReferenceByIdAndWarhouseTarget(java.lang.Long, java.lang.Long)
	 */
	public List<ReferenceVO> getReferenceByIdAndWarhouseTarget(Long referenceId, Long warehouseId )throws BusinessException{
		return businessReference.getReferenceByIdAndWarhouseTarget(referenceId,warehouseId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getReferenceByIdAndWarhouseTarget(java.lang.Long, java.lang.Long)
	 */
	public List<ReferenceVO> getReferenceByComplexFilterToFindInconsistenceReferences( 
			WarehouseVO whSource,WarehouseVO whTarget,ReferenceVO reference,Long country )throws BusinessException
	{
		return businessReference.getReferenceByComplexFilterToFindInconsistenceReferences(whSource, whTarget, reference, country);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getReferenceByIdAndWarhouseTarget(java.lang.Long, java.lang.Long)
	 */
	public NotSerializedElementInReferencePaginationResponse getNotSerializedElementInReference(
			ReferenceVO reference, RequestCollectionInfo requestCollectionInfo) throws BusinessException {
		
		return businessReference.getNotSerializedElementInReference(reference, requestCollectionInfo);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getReferenceByIdAndWarhouseTarget(java.lang.Long, java.lang.Long)
	 */
	public SerializedElementInReferencePaginationResponse getSerializedElementInReference(
			ReferenceVO reference, RequestCollectionInfo requestCollectionInfo) throws BusinessException {
		return businessReference.getSerializedElementInReference(reference, requestCollectionInfo);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getReferenceByIdAndWarhouseTarget(java.lang.Long, java.lang.Long)
	 */
	public List<ReferenceModificationVO> getReferenceModificationsByReferenceID(
			ReferenceVO refID) throws BusinessException {
		return businessReference.getReferenceModificationsByReferenceID( refID );
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getReferenceDetailsById(java.lang.Long,)
	 */
	public ReferenceVO getReferenceDetailsById(Long referenceId)throws BusinessException{
		return businessReference.getReferenceDetailsById(referenceId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getReferenceByIdAndWarhouseTarget(java.lang.Long, java.lang.Long)
	 */
	public List<RefInconsistencyVO>getReferenceInconsistencyByReferenceID( Long id )throws BusinessException
	{
		return businessReference.getReferenceInconsistencyByReferenceID( id );
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#saveReferenceDeliveries(java.lang.Long,java.util.List<co.com.directv.sdii.model.vo.DeliveryVO>,java.util.List<co.com.directv.sdii.model.vo.SpecialCommentVO>)
	 */
	public void saveReferenceDeliveries(Long referenceId ,List<DeliveryVO> deliveriesList ,List<SpecialCommentVO> specialCommentsList)throws BusinessException {
		businessReference.saveReferenceDeliveries(referenceId, deliveriesList, specialCommentsList);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#saveReferenceDeliveries(java.lang.Long,java.util.List<co.com.directv.sdii.model.vo.DeliveryVO>,java.util.List<co.com.directv.sdii.model.vo.SpecialCommentVO>)
	 */
	public void closeReferenceInconsistencyStatus( List<Long>inconsistenciesListIds )throws BusinessException
	{
		businessReference.closeReferenceInconsistencyStatus( inconsistenciesListIds );
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#saveReferenceDeliveries(java.lang.Long,java.util.List<co.com.directv.sdii.model.vo.DeliveryVO>,java.util.List<co.com.directv.sdii.model.vo.SpecialCommentVO>)
	 */
	public void modifyReferenceElementItemCuantityByReferenceAndItemList( List<ReferenceElementItemVO>items,ReferenceVO reference,Boolean finished,UserVO user )throws BusinessException
	{
	   businessReference.modifyReferenceElementItemCuantityByReferenceAndItemList( items, reference,finished,user);	
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#saveReferenceDeliveries(java.lang.Long,java.util.List<co.com.directv.sdii.model.vo.DeliveryVO>,java.util.List<co.com.directv.sdii.model.vo.SpecialCommentVO>)
	 */
	public void addReferenceElementItemToReference(List<ReferenceElementItemVO> items, ReferenceVO reference)throws BusinessException {
      businessReference.addReferenceElementItemToReference(items, reference);		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#saveReferenceDeliveries(java.lang.Long,java.util.List<co.com.directv.sdii.model.vo.DeliveryVO>,java.util.List<co.com.directv.sdii.model.vo.SpecialCommentVO>)
	 */
	public void deleteReferenceElementItemInReference(List<Long> itemsId)throws BusinessException {
		businessReference.deleteReferenceElementItemInReference( itemsId );
	}
    
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#confirmReferenceElementsReception(java.lang.Long,co.com.directv.sdii.model.vo.UserVO)
	 */
	public void confirmReferenceElementsReception(Long referenceId,UserVO user)throws BusinessException {
		businessReference.confirmReferenceElementsReception(referenceId,user);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#confirmNotSerializeReferenceElementsReception(java.lang.Long,co.com.directv.sdii.model.vo.UserVO)
	 */
	public void confirmNotSerializeReferenceElementsReception(Long referenceId,UserVO user)throws BusinessException {
		businessReference.confirmNotSerializeReferenceElementsReception(referenceId,user);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#confirmSerializeReferenceElementsReception(java.lang.Long,co.com.directv.sdii.model.vo.UserVO)
	 */
	public void confirmSerializeReferenceElementsReception(Long referenceId,UserVO user)throws BusinessException {
		businessReference.confirmSerializeReferenceElementsReception(referenceId,user);
	}

	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getReferencesByReferenceAndWhAndByCountry(java.lang.Long, co.com.directv.sdii.model.vo.WarehouseVO, co.com.directv.sdii.model.vo.WarehouseVO, java.lang.Long)
	 */
	public List<ReferenceVO> getReferencesByReferenceAndWhAndByCountry(Long ref,
			WarehouseVO whSource, WarehouseVO whTarget,Long country)
			throws BusinessException {
		return businessReference.getReferencesByReferenceAndWhAndByCountry(ref,whSource,whTarget,country);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getReferencesByReferenceAndWhAndRefStatusRefStatus(java.lang.Long, co.com.directv.sdii.model.vo.WarehouseVO, co.com.directv.sdii.model.vo.WarehouseVO, co.com.directv.sdii.model.vo.ReferenceStatusVO)
	 */
	public ReferenceResponse getReferencesByReferenceAndWhAndRefStatusRefStatus(
			Long ref, WarehouseVO whSource, WarehouseVO whTarget,
			ReferenceStatusVO refStatus, Long userId, RequestCollectionInfo requestCollectionInfo ) throws BusinessException {
		return businessReference.getReferencesByReferenceAndWhAndRefStatusRefStatus(ref,whSource,whTarget,refStatus,userId, requestCollectionInfo);
	}

	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getReferencesByConfirmationDateAndByCountry(java.lang.Long, java.lang.Long)
	 */
	public ReferenceResponse getReferencesByConfirmationDateAndByCountry(Long dealer,Long country, RequestCollectionInfo requestCollectionInfo) throws BusinessException{
		return businessReference.getReferencesByConfirmationDateAndByCountry(dealer, country,requestCollectionInfo);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getReferencesByConfirmationDate(java.lang.Long)
	 */
	 public List<ReferenceVO>getReferenceByIdAndWarehouseTarget( Long referenceId,Long warehouseId )throws BusinessException
	 {
	    return businessReference.getReferenceByIdAndWarehouseTarget( referenceId, warehouseId);	 
	 }
	 
	 /* (non-Javadoc)
		 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getReferencesByConfirmationDate(java.lang.Long)
		 */
	 public List<RefConfirmationVO> getConfirmationsByReferenceId( Long referenceId ) throws BusinessException
	 {
		 return businessReference.getConfirmationsByReferenceId( referenceId );
	 }
	 
	 /* (non-Javadoc)
	  * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getReferencesByConfirmationDate(java.lang.Long)
	 */ 
	 public List<RefConfirmationVO> getConfirmationsByReferenceIdAndElementId( Long referenceId,Long elementId ) throws BusinessException
	 {
		 return businessReference.getConfirmationsByReferenceIdAndElementId( referenceId, elementId );
	 }
	 
	 public List<ReferenceVO>getReferenceByIdAndWarehouseSourceAndWareHouseTarget( Long referenceId,Long whSourceId,Long whTargetId )throws BusinessException
	 {
		 return businessReference.getReferenceByIdAndWarehouseSourceAndWareHouseTarget(referenceId, whSourceId,whTargetId);
	 }
	
	 public List<ReferenceVO>getReferenceByIdAndWarehouseSource( Long referenceId,Long warehouseId )throws BusinessException
	 {
		 return businessReference.getReferenceByIdAndWarehouseSource(referenceId, warehouseId);
	 }
	 
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#createReferenceByFile(java.lang.Long, java.util.List, java.lang.Long, java.lang.Long, co.com.directv.sdii.model.vo.UserVO)
	 */
	 public Long createReferenceByFile(List<ReferenceElementItemVO> refElements, Long userId ) throws BusinessException
	 {
		 return businessReference.createReferenceByFile(refElements, userId);
	 }
	 
	 public List<ReferenceVO> getReferencesByElementId(Long elementId) throws BusinessException
	 {
		 return businessReference.getReferencesByElementId( elementId );
	 }

	 /*
	  * (non-Javadoc)
	  * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getReferencesByReferenceAndWhAndByCountryAndCreatedStatus(java.lang.Long, co.com.directv.sdii.model.vo.WarehouseVO, co.com.directv.sdii.model.vo.WarehouseVO, java.lang.Long)
	  */
	@Override
	public List<ReferenceVO> getReferencesByReferenceAndWhAndByCountryAndCreatedStatus(
			Long ref, WarehouseVO whSource, WarehouseVO whTarget, Long country)
			throws BusinessException {
		return businessReference.getReferencesByReferenceAndWhAndByCountryAndCreatedStatus( ref,  whSource,  whTarget,  country);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getReferencesByReferenceAndWhAndByCountryAndCreatedStatusAndCreationstatus(java.lang.Long, co.com.directv.sdii.model.vo.WarehouseVO, co.com.directv.sdii.model.vo.WarehouseVO, java.lang.Long)
	 */
	@Override
	public List<ReferenceVO> getReferencesByReferenceAndWhAndByCountryAndCreatedStatusAndCreationstatus(
			Long ref, WarehouseVO whSource, WarehouseVO whTarget, Long country)
			throws BusinessException {
		return businessReference.getReferencesByReferenceAndWhAndByCountryAndCreatedStatusAndCreationstatus( ref,  whSource,  whTarget,  country);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getReferencesByReferenceAndWhAndByCountryAndSendedOrPartialConfirmed(java.lang.Long, co.com.directv.sdii.model.vo.WarehouseVO, co.com.directv.sdii.model.vo.WarehouseVO, java.lang.Long)
	 */
	@Override
	public List<ReferenceVO> getReferencesByReferenceAndWhAndByCountryAndSendedOrPartialConfirmed(Long ref,
			WarehouseVO whSource, WarehouseVO whTarget,Long country)
			throws BusinessException {
		return businessReference.getReferencesByReferenceAndWhAndByCountryAndSendedOrPartialConfirmed(ref,whSource,whTarget,country);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getReferencesByRefIdOrSourceWhIdOrTargetWhIdAndCreatedStatusSameWhDealerOwner(java.lang.Long, co.com.directv.sdii.model.vo.WarehouseVO, co.com.directv.sdii.model.vo.WarehouseVO, java.lang.Long)
	 */
	@Override
	public List<ReferenceVO> getReferencesByRefIdOrSourceWhIdOrTargetWhIdAndCreatedStatusSameWhDealerOwner(
			Long ref, WarehouseVO whSource, WarehouseVO whTarget, Long country)
			throws BusinessException {
		return businessReference.getReferencesByRefIdOrSourceWhIdOrTargetWhIdAndCreatedStatusSameWhDealerOwner(ref, whSource, whTarget, country);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getReferencesByRefIdOrWhSourceIdAndDifferentDealerWhs(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<ReferenceVO> getReferencesByRefIdOrWhSourceIdAndDifferentDealerWhs(
			Long referenceId, Long sourceWarehouseId) throws BusinessException {
		return businessReference.getReferencesByRefIdOrWhSourceIdAndDifferentDealerWhs(referenceId, sourceWarehouseId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getReferencesByRefIdOrSourceWhIdOrTargetWhIdAndDifferentDealerWhs(java.lang.Long, co.com.directv.sdii.model.vo.WarehouseVO, co.com.directv.sdii.model.vo.WarehouseVO, java.lang.Long)
	 */
	@Override
	public List<ReferenceVO> getReferencesByRefIdOrSourceWhIdOrTargetWhIdAndDifferentDealerWhs(
			Long ref, WarehouseVO whSource, WarehouseVO whTarget, Long country)
			throws BusinessException {
		return businessReference.getReferencesByRefIdOrSourceWhIdOrTargetWhIdAndDifferentDealerWhs(ref, whSource, whTarget, country);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getCreatedReferencesByFilter(co.com.directv.sdii.model.dto.ReferencesFilterDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public ReferenceResponse getCreatedReferencesByFilter (ReferencesFilterDTO referenceDTO,RequestCollectionInfo requestCollInfo)throws BusinessException{
		return businessReference.getCreatedReferencesByFilter (referenceDTO, requestCollInfo);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#generateReference(co.com.directv.sdii.model.vo.ReferenceVO)
	 */
	@Override
	public ReferenceVO generateReference(ReferenceVO reference)
			throws BusinessException {
		return businessReference.generateReference (reference);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#logicDeleteReference(co.com.directv.sdii.model.vo.ReferenceVO)
	 */
	@Override
	public void logicDeleteReference(ReferenceVO obj) throws BusinessException {
		businessReference.logicDeleteReference (obj);
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getPreloadedReferences(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ReferenceResponse getPreloadedReferences(Long sourceWhId,RequestCollectionInfo requestCollInfo) throws BusinessException {
		return businessReference.getPreloadedReferences(sourceWhId, requestCollInfo);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#isReferenceFromSameDealer(java.lang.Long)
	 */
	@Override
	public boolean isReferenceFromSameDealer(Long referenceId) throws BusinessException {
		return businessReference.isReferenceFromSameDealer(referenceId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#sendReference(co.com.directv.sdii.model.vo.ReferenceVO, boolean, boolean)
	 */
	@Override
	public void sendReference(ReferenceVO referenceVO, boolean isBetweenDifDealers,boolean validateQuantityControl, Long userId) throws BusinessException {
		this.businessReference.sendReference(referenceVO, isBetweenDifDealers,validateQuantityControl, userId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#sendReferenceBetweenDifDealers(co.com.directv.sdii.model.vo.ReferenceVO)
	 */
	@Override
	public void sendReferenceBetweenDifDealers(ReferenceVO referenceVO, User user) throws BusinessException {
		this.businessReference.sendReferenceBetweenDifDealers(referenceVO, user);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getReferencesByParentReferenceId(java.lang.Long)
	 */
	@Override
	public List<ReferenceVO> getReferencesByParentReferenceId(
			Long parentReferenceId) throws BusinessException {
		return businessReference.getReferencesByParentReferenceId(parentReferenceId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getReferencesByFilter(co.com.directv.sdii.model.dto.ReferencesFilterDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ReferenceResponse getReferencesByFilter(ReferencesFilterDTO referenceDTO,RequestCollectionInfo requestCollInfo) throws BusinessException {
		return businessReference.getReferencesByFilter(referenceDTO, requestCollInfo);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#validateReferenceReceiveData(java.lang.Long)
	 */
	@Override
	public boolean validateReferenceReceiveData(Long referenceId) throws BusinessException {
		return businessReference.validateReferenceReceiveData(referenceId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#createRefRecieveData(co.com.directv.sdii.model.vo.RefRecieveDataVO)
	 */
	@Override
	public void createRefRecieveData(RefRecieveDataVO refRecieveDataVO, Long userId)throws BusinessException {
		businessReference.createRefRecieveData(refRecieveDataVO, userId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#getRefRecieveDataByReferenceId(java.lang.Long)
	 */
	public RefRecieveDataVO getRefRecieveDataByReferenceId(Long referenceId)throws BusinessException {
		return businessReference.getRefRecieveDataByReferenceId(referenceId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#closeRefInconsistency(co.com.directv.sdii.model.vo.RefInconsistencyVO)
	 */
	@Override
	public Long closeRefInconsistency(RefInconsistencyVO refInconsistencyVO, Long userId)
			throws BusinessException {
		return businessReference.closeRefInconsistency(refInconsistencyVO, userId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal#isTargetACrewOfSourceWh(java.lang.Long, java.lang.Long)
	 */
	@Override
	public boolean isReferenceRequiresValidationProcess(Long sourceWhId, Long targetWhId)
			throws BusinessException {
		return businessReference.isReferenceRequiresValidationProcess(sourceWhId, targetWhId);
	}
	
}
