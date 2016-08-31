package co.com.directv.sdii.ejb.business.stock.impl;

import static co.com.directv.sdii.common.util.UtilsBusiness.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.ReferenceMailSenderLocal;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.stock.RefInconsistencyBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ReferenceModificationBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ReportedElementBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.ItemStatus;
import co.com.directv.sdii.model.pojo.NotSerialized;
import co.com.directv.sdii.model.pojo.RefIncStatus;
import co.com.directv.sdii.model.pojo.RefInconsistency;
import co.com.directv.sdii.model.pojo.Reference;
import co.com.directv.sdii.model.pojo.ReferenceElementItem;
import co.com.directv.sdii.model.pojo.ReferenceStatus;
import co.com.directv.sdii.model.pojo.ReportedElement;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.collection.RefInconsistencyResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.RefInconsistencyVO;
import co.com.directv.sdii.model.vo.ReferenceElementItemVO;
import co.com.directv.sdii.model.vo.ReferenceVO;
import co.com.directv.sdii.model.vo.ReportedElementVO;
import co.com.directv.sdii.model.vo.UserVO;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ItemStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.NotSerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.RefConfirmationDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.RefIncStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.RefIncTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.RefInconsistencyDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ReferenceStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ReportedElementDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal;
import co.com.directv.sdii.ws.model.dto.ReportedElementForValidationDTO;

/**
 * EJB que implementa las operaciones Tipo CRUD RefInconsistency
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.RefInconsistencyDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.RefInconsistencyBusinessBeanLocal
 */
@Stateless(name="RefInconsistencyBusinessBeanLocal",mappedName="ejb/RefInconsistencyBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RefInconsistencyBusinessBean extends BusinessBase implements RefInconsistencyBusinessBeanLocal {

    @EJB(name="RefInconsistencyDAOLocal", beanInterface=RefInconsistencyDAOLocal.class)
    private RefInconsistencyDAOLocal daoRefInconsistency;
    
    @EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
    private UserDAOLocal daoUser;
    
    @EJB(name="ReferenceDAOLocal", beanInterface=ReferenceDAOLocal.class)
	private ReferenceDAOLocal daoReference;
    
    @EJB(name="ReferenceStatusDAOLocal", beanInterface=ReferenceStatusDAOLocal.class)
	private ReferenceStatusDAOLocal daoReferenceStatus;
    
    @EJB(name="RefIncStatusDAOLocal", beanInterface=RefIncStatusDAOLocal.class)
	private RefIncStatusDAOLocal daoRefIncStatus;
    
    @EJB(name="ReferenceMailSenderLocal", beanInterface=ReferenceMailSenderLocal.class)
    private ReferenceMailSenderLocal referenceMailSender;
    
    @EJB(name="ReportedElementBusinessBeanLocal", beanInterface=ReportedElementBusinessBeanLocal.class)
    private ReportedElementBusinessBeanLocal businessReportedElement;
    
    @EJB(name="ReportedElementDAOLocal", beanInterface=ReportedElementDAOLocal.class)
    private ReportedElementDAOLocal daoReportedElement;
    
    @EJB(name="RefIncTypeDAOLocal", beanInterface=RefIncTypeDAOLocal.class)
    private RefIncTypeDAOLocal daoRefIncType;
    
    @EJB(name="ItemStatusDAOLocal", beanInterface=ItemStatusDAOLocal.class)
    private ItemStatusDAOLocal daoItemStatus;
    
    @EJB(name="ReferenceElementItemDAOLocal", beanInterface=ReferenceElementItemDAOLocal.class)
    private ReferenceElementItemDAOLocal daoRefElementItem;
    
    @EJB(name="ReferenceModificationBusinessBeanLocal", beanInterface=ReferenceModificationBusinessBeanLocal.class)
    private ReferenceModificationBusinessBeanLocal businessReferenceModification;
    
    @EJB(name="ReferenceBusinessBeanLocal", beanInterface=ReferenceBusinessBeanLocal.class)
    private ReferenceBusinessBeanLocal businessReference;
    
    @EJB(name="SerializedDAOLocal", beanInterface=SerializedDAOLocal.class)
    private SerializedDAOLocal daoSerialized;
    
    @EJB(name="NotSerializedDAOLocal", beanInterface=NotSerializedDAOLocal.class)
    private NotSerializedDAOLocal daoNotSerialized;
    
    @EJB(name="RefConfirmationDAOLocal", beanInterface=RefConfirmationDAOLocal.class)
    private RefConfirmationDAOLocal daoRefConfirmation;
    
    private final static Logger log = UtilsBusiness.getLog4J(RefInconsistencyBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.RefInconsistencyBusinessBeanLocal#getAllRefInconsistencys()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<RefInconsistencyVO> getAllRefInconsistencys() throws BusinessException {
        log.debug("== Inicia getAllRefInconsistencys/RefInconsistencyBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoRefInconsistency.getAllRefInconsistencys(), RefInconsistencyVO.class);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllRefInconsistencys/RefInconsistencyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllRefInconsistencys/RefInconsistencyBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.RefInconsistencyBusinessBeanLocal#getRefInconsistencysByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public RefInconsistencyVO getRefInconsistencyByID(Long id) throws BusinessException {
        log.debug("== Inicia getRefInconsistencyByID/RefInconsistencyBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            RefInconsistency objPojo = daoRefInconsistency.getRefInconsistencyByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(RefInconsistencyVO.class, objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getRefInconsistencyByID/RefInconsistencyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getRefInconsistencyByID/RefInconsistencyBusinessBean ==");
        }
    }

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RefInconsistencyBusinessBeanLocal#createRefInconsistency(co.com.directv.sdii.model.vo.RefInconsistencyVO, boolean, boolean)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveRefInconsistency(RefInconsistencyVO refInconsistency, List<ReportedElementVO> reportedElements, Long userId) throws BusinessException {
        log.debug("== Inicia saveRefInconsistency/RefInconsistencyBusinessBean ==");
        
        try {
        	UtilsBusiness.assertNotNull(refInconsistency, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	UtilsBusiness.assertNotNull(refInconsistency.getReference(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	UtilsBusiness.assertNotNull(refInconsistency.getReference().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	UtilsBusiness.assertNotNull(refInconsistency.getRefIncType(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	UtilsBusiness.assertNotNull(refInconsistency.getRefIncType().getRefIncTypeCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	
        	//Consulto el usuario 
        	User user = daoUser.getUserById(userId);
        	if(user == null){
        		throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),	ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
        	}
        	
        	validateReferenceStatusForInconsistencyCreation(refInconsistency.getReference());

        	//guardar la inconsistencia
        	RefInconsistency refInconsistencyPojo = UtilsBusiness.copyObject(RefInconsistency.class, refInconsistency);
        	refInconsistencyPojo.setRefIncDate(UtilsBusiness.fechaActual());
        	RefIncStatus refIncStatusOpen = daoRefIncStatus.getRefIncStatusByCode(CodesBusinessEntityEnum.INCONSISTENCY_REFERENCE_STATUS_OPEN.getCodeEntity());
			refInconsistencyPojo.setRefIncStatus(refIncStatusOpen);
			
			refInconsistencyPojo.setRefIncType(daoRefIncType.getRefIncTypeByCode(refInconsistency.getRefIncType().getRefIncTypeCode()));
			
			if(refInconsistencyPojo.getAnswerUserId() != null && refInconsistencyPojo.getAnswerUserId().equals(0L)) {
				refInconsistencyPojo.setAnswerUserId(null);//es necesario por que de lo contrario saldría un error de integridad rererencial
			}
			
			refInconsistencyPojo = daoRefInconsistency.updateRefInconsistency(refInconsistencyPojo);
        	
        	//asegurar que la remisión esté en estado inconsistente
        	Reference referenceToUpdate = daoReference.getReferenceByID( refInconsistency.getReference().getId() );
        	
        	assertNotNull(referenceToUpdate.getShippingUserId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			assertNotNull(referenceToUpdate.getShippingUserId().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	
        	ReferenceStatus status =  daoReferenceStatus.getReferenceByCode( CodesBusinessEntityEnum.REFERENCE_STATUS_INCONSISTENCY_PROCESS.getCodeEntity() );            	
        	referenceToUpdate.setReferenceStatus( status );
        	daoReference.updateReference( referenceToUpdate );
        	
        	//guardar los elementos reportados en la inconsistencia
        	saveRefInconsistencyReportedElements(refInconsistency.isMoreElements(), refInconsistencyPojo, reportedElements, user.getCountry().getId());
        	
			if(refInconsistency.isMoreElements()) {
				//mover elementos de la inconsistencia a bodega origen transito para que no se puedan hacer mas movimientos desde la bodega de origen
				ItemStatus itemStatus = daoItemStatus.getItemStatusSended();
				
				Reference ref = refInconsistencyPojo.getReference();
				Long whIdSource = ref.getWarehouseBySourceWh().getId();//Ubicación  origen
				Long whIdTarget = ref.getSourceTransitWh().getId();//Ubicación  transito origen
				businessReference.moveElementsBetweenWarehouses(whIdSource, whIdTarget, reportedElements, UtilsBusiness.copyObject(ReferenceVO.class, refInconsistency.getReference()),itemStatus, false, user, true);
			}
        	
        	//Crea el registro en las modificaciones de remision
			if (refInconsistency.getCreateUserId() != null){
				this.businessReferenceModification.createReferenceModification( referenceToUpdate.getId() , CodesBusinessEntityEnum.REFERENCE_MODIFICATION_INCONSISTENCY_PROCESS.getCodeEntity(), user.getId() );
			}
			
        	referenceMailSender.sendRefInconsistencyCreatedMail(refInconsistencyPojo, reportedElements);
	        
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación saveRefInconsistency/RefInconsistencyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina saveRefInconsistency/RefInconsistencyBusinessBean ==");
        }
		
	}

	/**
	 * Metodo: almacena los elementos reportados para una inconsistencia de remisión.
	 * @param refInconsistency
	 * @param reportedElements listado completo de los elementos de la inconsistencia
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author wjimenez
	 * @throws BusinessException 
	 * @throws PropertiesException 
	 */
	private void saveRefInconsistencyReportedElements(boolean isMoreElements, RefInconsistency refInconsistency, List<ReportedElementVO> reportedElements, Long countryId) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException {
		businessReportedElement.deleteReportedElementsByRefInconsitencyId(refInconsistency.getId());
		
		for (ReportedElementVO reportedElementVO : reportedElements) {
			
			boolean isNotSerialized = StringUtils.isBlank(reportedElementVO.getSerialCode());
			
			if(isMoreElements) {
				
				if(isNotSerialized) {//validar que si es un elementos no serializado, no se haya recepcionado ya el elemento
					ReferenceElementItem refItem = daoRefElementItem.getReferenceElementItemByElementTypeIdAndReferenceId(reportedElementVO.getElementType().getId(), refInconsistency.getReference().getId());
					if(refItem != null && refItem.getItemStatus() != null && refItem.getItemStatus().equals(CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity())) {
						throw new BusinessException("No se puede agregar el elemento por que ya se encuentra en estado Recepcionado");
					}
				}
			}
			
			if( reportedElementVO.getReporteDate() == null ) {
				reportedElementVO.setReporteDate(UtilsBusiness.fechaActual());
			}
			
			reportedElementVO.setRefInconsistency(refInconsistency);
			
			//cuando la inconsistencia es mas elementos físicos, no lleva un referenceElementItem, ya que son elementos que no estaban en la remisión original
			if(reportedElementVO.getReferenceElementItem() != null && reportedElementVO.getReferenceElementItem().getId().equals(0L)) {
				reportedElementVO.setReferenceElementItem(null);
				
				//agregar la cantidad para poder hacer el movimiento a bodega transito de origen cuando es no serializado
				ReferenceElementItemVO refElementItem = new ReferenceElementItemVO();
				refElementItem.setRefIncAddedQuantity(reportedElementVO.getQty());
				
				//guardar el elemento adicionado para poder hacer el movimiento
				if(!StringUtils.isEmpty(reportedElementVO.getSerialCode())) {
					Serialized serialized = daoSerialized.getSerializedBySerial(reportedElementVO.getSerialCode(),refInconsistency.getReference().getCountryCodeId().getId());
					refElementItem.setElement(serialized.getElement());
					refElementItem.setRefIncAddedQuantity(1D);
				} else {
					NotSerialized notSerialized = daoNotSerialized.getNotSerializedbyElementTypeID(reportedElementVO.getElementType().getId(), countryId);
					refElementItem.setElement(notSerialized.getElement());
					refElementItem.setRefIncAddedQuantity(reportedElementVO.getQty());
				}
				reportedElementVO.addAddedElement(refElementItem);
				
			} else {
				//los elementos deben quedar con estado inconsistente				
				ReferenceElementItem refElementItem = daoRefElementItem.getReferenceElementItemByID(reportedElementVO.getReferenceElementItem().getId());
				refElementItem.setItemStatus(daoItemStatus.getItemStatusInconsistency());
				daoRefElementItem.updateReferenceElementItem(refElementItem);
			}
			
			businessReportedElement.updateReportedElement(reportedElementVO);
			
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RefInconsistencyBusinessBeanLocal#validateReportedElementsForLessElementsInRefInc(java.util.List)
	 */
	@Override
	public void validateReportedElementsForLessElementsInRefInc(List<ReportedElementForValidationDTO> elementsToValidate) throws BusinessException {
		log.debug("== Inicia validateReportedElementsForLessElementsInRefInc/RefInconsistencyBusinessBean ==");
		List<ReportedElementForValidationDTO> elementsWithNoEnoughQtyInReference = new ArrayList<ReportedElementForValidationDTO>();
		List<ReportedElementForValidationDTO> elementsInOtherInconsistency = new ArrayList<ReportedElementForValidationDTO>();
		if(elementsToValidate != null) {
			try {
				
				elementsInOtherInconsistency = getElementsInOtherInconsistency(elementsToValidate);
				//registrar que el elemento ya se encuentra en otra inconsistencia y ya no se pueden hacer mas
				if(elementsInOtherInconsistency != null && !elementsInOtherInconsistency.isEmpty()) {
					StringBuffer elementList = new StringBuffer();
					for (ReportedElementForValidationDTO errorElement : elementsInOtherInconsistency) {
						elementList.append(errorElement.getName()).append("\n");
					}
					String[] params = {elementList.toString()};
					String message = ErrorBusinessMessages.STOCK_IN458.getMessage();
					throw new BusinessException(ErrorBusinessMessages.STOCK_IN458.getCode(), message, UtilsBusiness.getParametersWS(params));
				}
				
				elementsWithNoEnoughQtyInReference = getElementsWithNoEnoughQtyInReference(elementsToValidate);
				//registrar que no hay cantidad suficiente en la remisión como para soportar la devolución
				if(elementsWithNoEnoughQtyInReference != null && !elementsWithNoEnoughQtyInReference.isEmpty()) {
					StringBuffer elementList = new StringBuffer();
					for (ReportedElementForValidationDTO errorElement : elementsWithNoEnoughQtyInReference) {
						elementList.append(errorElement.getName()).append("\t+"+ApplicationTextEnum.MAX_QUANTITY.getApplicationTextValue()+" = ").append(errorElement.getQuantityAvailable()).append("\n");
					}
					String[] params = {elementList.toString()};
					String message = ErrorBusinessMessages.STOCK_IN454.getMessage(params);
					throw new BusinessException(ErrorBusinessMessages.STOCK_IN454.getCode(), message, UtilsBusiness.getParametersWS(params));
				}
				
			}catch(Throwable ex){
	        	log.error("== Error al tratar de ejecutar la operación validateReportedElementsForLessElementsInRefInc/RefInconsistencyBusinessBean ==");
	        	throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina validateReportedElementsForLessElementsInRefInc/RefInconsistencyBusinessBean ==");
	        }
			
		}
	}
	
	private List<ReportedElementForValidationDTO> getElementsInOtherInconsistency(List<ReportedElementForValidationDTO> elementsToValidate) throws DAOServiceException, DAOSQLException, BusinessException {
		List<ReportedElementForValidationDTO> elementsWithErrors = new ArrayList<ReportedElementForValidationDTO>(); 
		for (ReportedElementForValidationDTO elementToValidate : elementsToValidate) {
			
			if(StringUtils.isBlank(elementToValidate.getSerial())) {
				Long count = daoReportedElement.getCountReportedElementsByRefIdAndElementTypeId(elementToValidate.getReferenceId(), elementToValidate.getElementTypeId(), true, true);
				if(count > 0) {//ya existen inconsistencias en la remisión con el mismo tipo de elemento no serializado
					elementsWithErrors.add(elementToValidate);
				}
			} else {
				ReportedElement reportedElement = daoReportedElement.getReportedElementByReferenceIdAndSerial(elementToValidate.getReferenceId(), elementToValidate.getSerial());
				if(reportedElement != null) {//se encontró el elemento serializado en otra inconsistencia
					elementsWithErrors.add(elementToValidate);
				}
			}
			
		}
		return elementsWithErrors;
	}
	
	private List<ReportedElementForValidationDTO> getElementsWithNoEnoughQtyInReference(List<ReportedElementForValidationDTO> elementsToValidate) throws DAOServiceException, DAOSQLException, BusinessException {
		List<ReportedElementForValidationDTO> elementsWithErrors = new ArrayList<ReportedElementForValidationDTO>(); 
		for (ReportedElementForValidationDTO elementToValidate : elementsToValidate) {
			ReportedElementForValidationDTO elementWithError = null;
			elementWithError = getElementWithNoEnoughQtyInReference(elementToValidate);
			if(elementWithError != null) {
				elementsWithErrors.add(elementWithError);
			}
		}
		return elementsWithErrors;
	}

	/**
	 * Metodo: obtiene el elemento, si es que este no tiene cantidad suficiente en la remisión como para
	 * realizar una devolución
	 * @param elementToValidate
	 * @return null si la cantidad en la remisión es suficiente para hacer el movimiento
	 */
	private ReportedElementForValidationDTO getElementWithNoEnoughQtyInReference(ReportedElementForValidationDTO elementToValidate) throws DAOServiceException, DAOSQLException, BusinessException {

		Double quantityInReference = 1D;
		if(!elementToValidate.isSerialized()) {
		
			//cuando la inconsistencia es de menos elementos, se debe validar que no se supere la cantidad existente en la remisión
			quantityInReference = elementToValidate.getQuantityInReference();
			//Double quantityInReference = daoRefElementItem.getReferenceElementItemQuantityByReferenceAndElement(elementToValidate.getReferenceId(), elementToValidate.getElementId());
		}
		
		//validar que no se supere la cantidad disponible real (teniendo en cuenta confirmaciones parciales e inconsistencias por menos elementos cerradas o abiertas)
		Long referenceElementId = elementToValidate.getReferenceElementId();
		
		Double quantityConfirmed = daoRefConfirmation.countElementConfirmedQuatity(referenceElementId);
		Double quantityInInconsistencies = businessReportedElement.getCountReportedElementsByRefElementItemId(referenceElementId, false, true);
		
		Double quantityAvailable = quantityInReference - (quantityConfirmed + quantityInInconsistencies);
		
		if(elementToValidate.getQuantity() > quantityAvailable) {
			elementToValidate.setQuantityAvailable(quantityAvailable);
			return elementToValidate;
		}
		
		return null;
	}
	
	/**
	 * Metodo: asegura que la remisión tenga un estado válido para poder crearle una inconsistencia
	 * @param reference
	 * @throws BusinessException
	 * @author wjimenez
	 * @throws PropertiesException 
	 */
	private void validateReferenceStatusForInconsistencyCreation(Reference reference) throws BusinessException, PropertiesException {
		//wjimenez CU Inv-36 indica como precondición que La remisión se encuentra en estado “Enviado”, “Confirmado parcial” o “En Inconsistencia”
    	UtilsBusiness.assertNotNull(reference.getReferenceStatus(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
    	UtilsBusiness.assertNotNull(reference.getReferenceStatus().getRefStatusCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
    	
    	String refStatus = reference.getReferenceStatus().getRefStatusCode();
    	if( ! (refStatus.equals(CodesBusinessEntityEnum.REFERENCE_STATUS_SENDED.getCodeEntity())
    		|| refStatus.equals(CodesBusinessEntityEnum.REFERENCE_STATUS_PARTIALLY_CONFIRMED.getCodeEntity())
    		|| refStatus.equals(CodesBusinessEntityEnum.REFERENCE_STATUS_INCONSISTENCY_PROCESS.getCodeEntity())) ) {
    		throw new BusinessException( ErrorBusinessMessages.STOCK_IN388.getCode(),ErrorBusinessMessages.STOCK_IN388.getMessageCode() );
    	}
	}
	
	/*
	//wjimenez revisar este envío por que no es correcto
	private void sendRefInconsistencyMail(Reference reference, ReferenceStatus status, Long userId) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException {
		User user = daoUser.getUserById(userId);
		SendEmailDTO email = prepareSendEmailDTOToReferenceInconsistency(user, reference.getId());
		List<String> recipientsWHSource = new ArrayList<String>();
		List<String> recipientsWHTarget = new ArrayList<String>();

		// informar al responsable de la bodega origen y a los analistas de logística DTV sobre el envío de la remisión  

		if(!StringUtils.isEmpty(reference.getWarehouseBySourceWh().getResponsibleEmail()))
			recipientsWHSource.add( reference.getWarehouseBySourceWh().getResponsibleEmail() );
		
		List<User> logisticsAnalystUsers = daoUser.getUsersByRoleTypeCodeAndCountryId(CodesBusinessEntityEnum.ROLE_TYPE_LOGISTICS_ANALYST.getCodeEntity(), user.getCountry().getId());
		if(logisticsAnalystUsers != null) {
			for (User aUser : logisticsAnalystUsers) {
				if(!StringUtils.isEmpty(aUser.getEmail())) {
					recipientsWHSource.add(aUser.getEmail());
				}
			}
		}
		
		if( recipientsWHSource.size()>0 ){
			email.setRecipient( recipientsWHSource );
			businessEmailType.sendEmailByEmailTypeCode( email, reference.getCountryCodeId().getId());
		}

		if( recipientsWHTarget.size()>0 )
		{
			email.setRecipient( recipientsWHTarget );
			//email.setNewsType( EmailTypesEnum.REFERENCES_ELEMENTS_HAS_BEEN_RECEIVED.getEmailTypecode() );
			businessEmailType.sendEmailByEmailTypeCode( email, reference.getCountryCodeId().getId() );
		}
		
	}


	private SendEmailDTO prepareSendEmailDTOToReferenceInconsistency(User user, Long referenceId) {
		
		SendEmailDTO email = new SendEmailDTO();
    	email.setNewsType( EmailTypesEnum.REFERENCE_INCONSISTENCY.getEmailTypecode() );
        email.setNewsDocument( user.getIdNumber() );
        email.setNewsObservation( "Registro de inconsistencia en Remisión #" +  referenceId);
        email.setNewsSourceUser( user.getName() );
        
        return email;
	}
*/

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RefInconsistencyBusinessBeanLocal#updateRefInconsistency(co.com.directv.sdii.model.vo.RefInconsistencyVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateRefInconsistency(RefInconsistencyVO obj) throws BusinessException {
        log.debug("== Inicia updateRefInconsistency/RefInconsistencyBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            RefInconsistency objPojo =  UtilsBusiness.copyObject(RefInconsistency.class, obj);
            daoRefInconsistency.updateRefInconsistency(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateRefInconsistency/RefInconsistencyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateRefInconsistency/RefInconsistencyBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RefInconsistencyBusinessBeanLocal#deleteRefInconsistency(co.com.directv.sdii.model.vo.RefInconsistencyVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteRefInconsistency(RefInconsistencyVO obj) throws BusinessException {
        log.debug("== Inicia deleteRefInconsistency/RefInconsistencyBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            RefInconsistency objPojo =  UtilsBusiness.copyObject(RefInconsistency.class, obj);
            daoRefInconsistency.deleteRefInconsistency(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación deleteRefInconsistency/RefInconsistencyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteRefInconsistency/RefInconsistencyBusinessBean ==");
        }
	}


	@Override
	public List<RefInconsistencyVO> getRefInconsistencysByReferenceID(Long refID)
			throws BusinessException {
		log.debug("== Inicia getRefInconsistencysByReferenceID/RefInconsistencyBusinessBean ==");
        
		try {
		
			UtilsBusiness.assertNotNull(refID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			List<RefInconsistencyVO> result = UtilsBusiness.convertList( daoRefInconsistency.getReferenceInconsistencyByReferenceID( refID ),RefInconsistencyVO.class);
			
			populateAditionalInfo(result);
			
			return result;
        
        } catch(Throwable ex){
        	log.debug("== Error getRefInconsistencysByReferenceID/RefInconsistencyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getRefInconsistencysByReferenceID/RefInconsistencyBusinessBean ==");
        }
	}
	
	private void populateAditionalInfo(List<RefInconsistencyVO> result) throws DAOServiceException, DAOSQLException, BusinessException {
		Long creationUserId, answerUserId;
		User user;
		UserVO userVo;
		for (RefInconsistencyVO refInconsistencyVO : result) {
			creationUserId = refInconsistencyVO.getCreateUserId();
			answerUserId = refInconsistencyVO.getAnswerUserId();
			
			if(creationUserId != null && creationUserId.longValue() > 0){
				user = daoUser.getUserById(creationUserId);
				if(user != null){
					userVo = UtilsBusiness.copyObject(UserVO.class, user);
					refInconsistencyVO.setInconsistencyCreationUser(userVo);
				}
			}
			
			if(answerUserId != null && answerUserId.longValue() > 0){
				user = daoUser.getUserById(answerUserId);
				if(user != null){
					userVo = UtilsBusiness.copyObject(UserVO.class, user);
					refInconsistencyVO.setAnswerInsconsistencyUser(userVo);
				}
			}
		}
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RefInconsistencyBusinessBeanLocal#getRefInconsistencysAndUserCreationByReferenceID(java.lang.Long
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<RefInconsistencyVO> getRefInconsistencysAndUserCreationByReferenceID(Long refID) throws BusinessException {
		 log.debug("== Inicia getRefInconsistencysAndUserCreationByReferenceID/RefInconsistencyBusinessBean ==");
	        try {
	        	List<Object[]> inconsistenciesPojo = this.daoRefInconsistency.getRefInconsistencysAndUserCreationByReferenceID(refID);
	            List<RefInconsistencyVO> inconsistencies = null;
	            if(inconsistenciesPojo != null && inconsistenciesPojo.size() > 0){
	            	inconsistencies = new ArrayList<RefInconsistencyVO>();
	            	for (Object[] obj : inconsistenciesPojo) {
	            		RefInconsistencyVO inconsistency = UtilsBusiness.copyObject(RefInconsistencyVO.class, (RefInconsistency)obj[0]);
	            		inconsistency.setInconsistencyCreationUser(UtilsBusiness.copyObject(UserVO.class, (User)obj[1]));
	            		inconsistencies.add(inconsistency);
	                }
	            }
	            return inconsistencies;

	        } catch(Throwable ex){
	        	log.debug("== Error al tratar de ejecutar la operación getRefInconsistencysAndUserCreationByReferenceID/RefInconsistencyBusinessBean ==");
	        	throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina getRefInconsistencysAndUserCreationByReferenceID/RefInconsistencyBusinessBean ==");
	        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RefInconsistencyBusinessBeanLocal#getRefInconsistenciesAndUserCreationToDetailsByReferenceID(java.lang.Long
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<RefInconsistencyVO> getRefInconsistenciesAndUserCreationToDetailsByReferenceID(Long refID) throws BusinessException {
		 log.debug("== Inicia getRefInconsistencysAndUserCreationByReferenceID/RefInconsistencyBusinessBean ==");
	        try {
	        	List<Object[]> inconsistenciesPojo = this.daoRefInconsistency.getRefInconsistencysAndUserCreationByReferenceID(refID);
	            List<RefInconsistencyVO> inconsistencies = null;
	            if(inconsistenciesPojo != null && inconsistenciesPojo.size() > 0){
	            	inconsistencies = new ArrayList<RefInconsistencyVO>();
	            	for (Object[] obj : inconsistenciesPojo) {
	            		RefInconsistency pojo = (RefInconsistency)obj[0];
	            		Reference reference = new Reference();
	            		reference.setId(pojo.getReference().getId());
	            		pojo.setReference(reference);
	            		RefInconsistencyVO inconsistency = UtilsBusiness.copyObject(RefInconsistencyVO.class, pojo);
	            		inconsistency.setInconsistencyCreationUser( UtilsBusiness.copyObject(UserVO.class, removeUserInformation( (User)obj[1])) );
	            		inconsistency.setAnswerInsconsistencyUser(UtilsBusiness.copyObject(UserVO.class, removeUserInformation(daoUser.getUserById(inconsistency.getAnswerUserId()))));
	            		inconsistencies.add(inconsistency);
	                }
	            }
	            return inconsistencies;

	        } catch(Throwable ex){
	        	log.debug("== Error al tratar de ejecutar la operación getRefInconsistencysAndUserCreationByReferenceID/RefInconsistencyBusinessBean ==");
	        	throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina getRefInconsistencysAndUserCreationByReferenceID/RefInconsistencyBusinessBean ==");
	        }
	}
	
	/**
	 * Metodo que elimina la informacion innecesaria del usuario
	 * @param user Usuario al que se le va a eliminar la informacion
	 * @throws BusinessException En caso de error al eliminar la informacion
	 */
	public User removeUserInformation(User user) throws BusinessException{
		try{
			User newUser = new User();
			newUser.setEmail(user.getEmail());
			newUser.setId(user.getId());
			newUser.setIdNumber(user.getIdNumber());
			newUser.setIsActive(user.getIsActive());
			newUser.setLogin(user.getLogin());
			newUser.setName(user.getName());
			return newUser;
		} catch(Throwable ex){
			throw this.manageException(ex);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RefInconsistencyBusinessBeanLocal#getReferenceInconsistencyByReferenceIdAndStatus(java.lang.Long, java.lang.String)
	 */
	@Override
	public RefInconsistencyResponse getReferenceInconsistencyByReferenceIdAndStatus(Long refID, String status,RequestCollectionInfo requestCollInfo) throws BusinessException {
		log.debug("== Inicia getReferenceInconsistenciesOpenedByReferenceId/RefInconsistencyBusinessBean ==");
        
		try {
			UtilsBusiness.assertNotNull(refID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			RefInconsistencyResponse response = new RefInconsistencyResponse();
			response = daoRefInconsistency.getReferenceInconsistencyByReferenceIdAndStatus(refID, status, requestCollInfo);
			List<RefInconsistencyVO> result = UtilsBusiness.convertList(response.getRefInconsistencys() , RefInconsistencyVO.class);
			populateAditionalInfo(result);
			response.setRefInconsistencys(null);
			response.setRefInconsistencysVO(result);
			return response;
			
        } catch(Throwable ex){
        	log.debug("== Error getReferenceInconsistenciesOpenedByReferenceId/RefInconsistencyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferenceInconsistenciesOpenedByReferenceId/RefInconsistencyBusinessBean ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RefInconsistencyBusinessBeanLocal#getReferenceInconsistencyByReferenceIdAndStatus(java.lang.Long, java.lang.String)
	 */
	@Override
	public RefInconsistencyResponse getReferenceInconsistenciesOpenedByReferenceId(Long refID,RequestCollectionInfo requestCollInfo) throws BusinessException {
		try {
			return getReferenceInconsistencyByReferenceIdAndStatus(refID, CodesBusinessEntityEnum.INCONSISTENCY_REFERENCE_STATUS_OPEN.getCodeEntity(), requestCollInfo);
		} catch (PropertiesException e) {
			throw this.manageException(e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RefInconsistencyBusinessBeanLocal#getReferenceInconsistenciesClosedByReferenceId(java.lang.Long)
	 */
	@Override
	public RefInconsistencyResponse getReferenceInconsistenciesClosedByReferenceId(Long refID, RequestCollectionInfo requestCollInfo) throws BusinessException {
		try {
			return getReferenceInconsistencyByReferenceIdAndStatus(refID, CodesBusinessEntityEnum.INCONSISTENCY_REFERENCE_STATUS_CLOSE.getCodeEntity(), requestCollInfo);
		} catch (PropertiesException e) {
			throw this.manageException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RefInconsistencyBusinessBeanLocal#getGeneratedReferencesByRefIncId(java.lang.Long)
	 */
	@Override
	public List<ReferenceVO> getGeneratedReferencesByRefIncId(Long refIncId)
			throws BusinessException {
		log.debug("== Inicia getReferenceInconsistenciesOpenedByReferenceId/RefInconsistencyBusinessBean ==");
        
		try {
			UtilsBusiness.assertNotNull(refIncId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			List<Reference> result = daoRefInconsistency.getGeneratedReferencesByRefIncId(refIncId);			
			return UtilsBusiness.convertList(result, ReferenceVO.class);
			
        } catch(Throwable ex){
        	log.debug("== Error getReferenceInconsistenciesOpenedByReferenceId/RefInconsistencyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferenceInconsistenciesOpenedByReferenceId/RefInconsistencyBusinessBean ==");
        }
	}
	
}
