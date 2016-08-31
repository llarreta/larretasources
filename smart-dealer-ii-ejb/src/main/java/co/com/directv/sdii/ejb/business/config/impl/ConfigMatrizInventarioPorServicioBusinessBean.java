package co.com.directv.sdii.ejb.business.config.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.config.ConfigMatrizInventarioPorServicioBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ElementType;
import co.com.directv.sdii.model.pojo.RequiredServiceElement;
import co.com.directv.sdii.model.pojo.RequiredServiceElementId;
import co.com.directv.sdii.model.pojo.Service;
import co.com.directv.sdii.model.vo.ElementTypeVO;
import co.com.directv.sdii.model.vo.RequiredServiceElementVO;
import co.com.directv.sdii.model.vo.ServiceVO;
import co.com.directv.sdii.persistence.dao.config.RequiredServiceElementDAOLocal;
import co.com.directv.sdii.persistence.dao.config.ServiceDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ElementClassDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * Clase que implementa el contrato de operaciones para los servicios web
 * del módulo de Configuración de Matriz de Inventario por Servicio.
 *
 * Caso de Uso CFG - 16 - Administrar Matriz de Iventario Requerido por Servicio
 *
 * Fecha de Creación: Mar 25, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see
 */
@Stateless(name="ConfigMatrizInventarioPorServicioBusinessLocal",mappedName="ejb/ConfigMatrizInventarioPorServicioBusinessLocal")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ConfigMatrizInventarioPorServicioBusinessBean extends BusinessBase implements ConfigMatrizInventarioPorServicioBusinessLocal {

    @EJB(name="RequiredServiceElementDAOLocal",beanInterface=RequiredServiceElementDAOLocal.class)
    private RequiredServiceElementDAOLocal dAORequiredServiceElement;
    
    @EJB(name="ElementClassDAOLocal",beanInterface=ElementClassDAOLocal.class)
    private ElementClassDAOLocal elementClassDAO;

    @EJB(name="ElementTypeDAOLocal",beanInterface=ElementTypeDAOLocal.class)
    private ElementTypeDAOLocal elementTypeDAO;

    @EJB(name="ServiceDAOLocal",beanInterface=ServiceDAOLocal.class)
    private ServiceDAOLocal serviceDAO;

    private final static Logger log = UtilsBusiness.getLog4J(ConfigMatrizInventarioPorServicioBusinessBean.class);

     
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigMatrizInventarioPorServicioBusinessLocal#getRequiredServiceElementByID(co.com.directv.sdii.model.pojo.RequiredServiceElementId)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public RequiredServiceElementVO getRequiredServiceElementByID(RequiredServiceElementId id) throws BusinessException {
        log.debug("== Inicia getRequiredServiceElementByID/ConfigMatrizInventarioPorServicioBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            RequiredServiceElement soReqServiceId = dAORequiredServiceElement.getRequiredServiceElementByID(id);
            if (soReqServiceId != null) {
                RequiredServiceElementVO soReqServiceVo = UtilsBusiness.copyObject(RequiredServiceElementVO.class, soReqServiceId);
                return soReqServiceVo;
            }
            return null;

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getRequiredServiceElementByID/ConfigMatrizInventarioPorServicioBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getRequiredServiceElementByID/ConfigMatrizInventarioPorServicioBusinessBean ==");
        }
    }

     
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigMatrizInventarioPorServicioBusinessLocal#getRequiredServiceElementByService(co.com.directv.sdii.model.vo.ServiceVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<RequiredServiceElementVO> getRequiredServiceElementByService(ServiceVO service) throws BusinessException {
        log.debug("== Inicia getRequiredServiceElementByService/ConfigMatrizInventarioPorServicioBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(service, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	UtilsBusiness.assertNotNull(service.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	List<RequiredServiceElement> listPojo = dAORequiredServiceElement.getRequiredServiceElementByService(service.getId());
        	Service servicePojo = serviceDAO.getServiceByID(service.getId());
        	
        	addEmptyRequiredServiceElements(listPojo, servicePojo);
        	
        	List<RequiredServiceElementVO> listVo = UtilsBusiness.convertList(listPojo, RequiredServiceElementVO.class);
            
            return listVo;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getRequiredServiceElementByService/ConfigMatrizInventarioPorServicioBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getRequiredServiceElementByService/ConfigMatrizInventarioPorServicioBusinessBean ==");
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<RequiredServiceElementVO> getRequiredServiceElementsByServiceIdOnlySelectedElements(Long serviceId) throws BusinessException {
        log.debug("== Inicia getRequiredServiceElementByService/ConfigMatrizInventarioPorServicioBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(serviceId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	List<RequiredServiceElement> listPojo = dAORequiredServiceElement.getRequiredServiceElementByService(serviceId);
        	List<RequiredServiceElementVO> listVo = UtilsBusiness.convertList(listPojo, RequiredServiceElementVO.class);
            
            return listVo;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getRequiredServiceElementByService/ConfigMatrizInventarioPorServicioBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getRequiredServiceElementByService/ConfigMatrizInventarioPorServicioBusinessBean ==");
        }
    }

   
    private void addEmptyRequiredServiceElements(List<RequiredServiceElement> listPojo, Service service) throws DAOServiceException, DAOSQLException {
    	
    	List<ElementType> allElementTypes = elementTypeDAO.getElementTypesByModelStatusAndIsSerialized(null, null, Boolean.TRUE);
    	List<RequiredServiceElement> dummies = new ArrayList<RequiredServiceElement>();
    	boolean elementTypeFound = false;
    	RequiredServiceElement dummyReq = null;
		for (ElementType elementTypes : allElementTypes) {
			elementTypeFound = false;
			for (RequiredServiceElement requiredServiceElement : listPojo) {
				if(requiredServiceElement.getElementType().getId().longValue() == elementTypes.getId().longValue()){
					elementTypeFound = true;
				}
			}
			if(!elementTypeFound){
				dummyReq = new RequiredServiceElement();
				dummyReq.setElementType(elementTypes);
				dummyReq.setQuantity(0D);
				dummyReq.setService(service);
				RequiredServiceElementId id = new RequiredServiceElementId();
				id.setElementTypeId(elementTypes.getId());
				id.setServiceId(service.getId());
				dummies.add(dummyReq);
			}
		}
		listPojo.addAll(dummies);
	}


	/* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigMatrizInventarioPorServicioBusinessLocal#getRequiredServiceElementByElementType(co.com.directv.sdii.model.vo.ElementTypeVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<RequiredServiceElementVO> getRequiredServiceElementByElementType(ElementTypeVO elementType) throws BusinessException {
        log.debug("== Inicia getRequiredServiceElementByElementType/ConfigMatrizInventarioPorServicioBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(elementType, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            List<RequiredServiceElementVO> listVo = UtilsBusiness.convertList(dAORequiredServiceElement.getRequiredServiceElementByElementType(elementType.getId()), RequiredServiceElementVO.class);
            return listVo;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getRequiredServiceElementByElementType/ConfigMatrizInventarioPorServicioBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getRequiredServiceElementByElementType/ConfigMatrizInventarioPorServicioBusinessBean ==");
        }
    }

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigMatrizInventarioPorServicioBusinessLocal#getRequiredServiceElementByServiceElementType(co.com.directv.sdii.model.vo.ServiceVO, co.com.directv.sdii.model.vo.ElementTypeVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public RequiredServiceElementVO getRequiredServiceElementByServiceElementType(ServiceVO service, ElementTypeVO elementType) throws BusinessException {
        log.debug("== Inicia getRequiredServiceElementByServiceElementType/ConfigMatrizInventarioPorServicioBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(service, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	UtilsBusiness.assertNotNull(elementType, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            RequiredServiceElement reqServiceElement = dAORequiredServiceElement.getRequiredServiceElementByServiceElementType(elementType.getId(), service.getId());
            if(reqServiceElement == null)
                return null;

            RequiredServiceElementVO reqServiceElementVO = UtilsBusiness.copyObject(RequiredServiceElementVO.class, reqServiceElement);

            return reqServiceElementVO;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getRequiredServiceElementByServiceElementType/ConfigMatrizInventarioPorServicioBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getRequiredServiceElementByServiceElementType/ConfigMatrizInventarioPorServicioBusinessBean ==");
        }
    }

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigMatrizInventarioPorServicioBusinessLocal#getAll()
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<RequiredServiceElementVO> getAll() throws BusinessException {
        log.debug("== Inicia getAll/ConfigMatrizInventarioPorServicioBusinessBean ==");
        try {
            List<RequiredServiceElementVO> listVo = UtilsBusiness.convertList(dAORequiredServiceElement.getAll(), RequiredServiceElementVO.class);
            return listVo;
        }  catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getAll/ConfigMatrizInventarioPorServicioBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAll/ConfigMatrizInventarioPorServicioBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigMatrizInventarioPorServicioBusinessLocal#createRequiredServiceElement(co.com.directv.sdii.model.vo.RequiredServiceElementVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createRequiredServiceElement(RequiredServiceElementVO obj) throws BusinessException {
        log.debug("== Inicio createRequiredServiceElement/ConfigMatrizInventarioPorServicioBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

            if(!BusinessRuleValidationManager.getInstance().isValid(obj)){
            	log.debug(ErrorBusinessMessages.ERROR_DATA.getMessage());
                throw new BusinessException(ErrorBusinessMessages.ERROR_DATA.getCode(),ErrorBusinessMessages.ERROR_DATA.getMessage());
            }

            RequiredServiceElement reqServicePojo = UtilsBusiness.copyObject(RequiredServiceElement.class, obj);
            dAORequiredServiceElement.createRequiredServiceElement(reqServicePojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion createRequiredServiceElement/ConfigMatrizInventarioPorServicioBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createRequiredServiceElement/ConfigMatrizInventarioPorServicioBusinessBean ==");
        }
    }

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigMatrizInventarioPorServicioBusinessLocal#updateRequiredServiceElement(co.com.directv.sdii.model.vo.RequiredServiceElementVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateRequiredServiceElement(RequiredServiceElementVO obj) throws BusinessException {
        log.debug("== Inicia updateRequiredServiceElement/ConfigMatrizInventarioPorServicioBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

            if(!BusinessRuleValidationManager.getInstance().isValid(obj)){
            	log.debug(ErrorBusinessMessages.ERROR_DATA.getMessage());
                throw new BusinessException(ErrorBusinessMessages.ERROR_DATA.getCode(),ErrorBusinessMessages.ERROR_DATA.getMessage());
            }

            RequiredServiceElement reqServicePojo = UtilsBusiness.copyObject(RequiredServiceElement.class, obj);
            if(reqServicePojo.getQuantity() <= 0D){
            	dAORequiredServiceElement.deleteRequiredServiceElement(reqServicePojo);
            }else{
            	dAORequiredServiceElement.updateRequiredServiceElement(reqServicePojo);
            }
            
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion updateRequiredServiceElement/ConfigMatrizInventarioPorServicioBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateRequiredServiceElement/ConfigMatrizInventarioPorServicioBusinessBean ==");
        }
    }
    
   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigMatrizInventarioPorServicioBusinessLocal#deleteRequiredServiceElement(co.com.directv.sdii.model.vo.RequiredServiceElementVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteRequiredServiceElement(RequiredServiceElementVO obj) throws BusinessException {
        log.debug("== Inicia deleteRequiredServiceElement/ConfigMatrizInventarioPorServicioBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            RequiredServiceElement reqServicePojo = UtilsBusiness.copyObject(RequiredServiceElement.class, obj);
            dAORequiredServiceElement.deleteRequiredServiceElement(reqServicePojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion deleteRequiredServiceElement/ConfigMatrizInventarioPorServicioBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteRequiredServiceElement/ConfigMatrizInventarioPorServicioBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigMatrizInventarioPorServicioBusinessLocal#getElementTypeByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ElementTypeVO getElementTypeByID(Long id) throws BusinessException {
        log.debug("== Inicia getElementTypeByID/ConfigMatrizInventarioPorServicioBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            ElementType elementTypeId = elementTypeDAO.getElementTypeByID(id);
            if (elementTypeId != null) {
                ElementTypeVO elementTypeVo = UtilsBusiness.copyObject(ElementTypeVO.class, elementTypeId);
                return elementTypeVo;
            }
            return null;

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getElementTypeByID/ConfigMatrizInventarioPorServicioBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getElementTypeByID/ConfigMatrizInventarioPorServicioBusinessBean ==");
        }
    }

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigMatrizInventarioPorServicioBusinessLocal#getServiceByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ServiceVO getServiceByID(Long id) throws BusinessException {
        log.debug("== Inicia getServiceByID/ConfigMatrizInventarioPorServicioBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            Service serviceId = serviceDAO.getServiceByID(id);
            if (serviceId != null) {
                ServiceVO serviceVo = UtilsBusiness.copyObject(ServiceVO.class, serviceId);
                return serviceVo;
            }
            return null;

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getServiceByID/ConfigMatrizInventarioPorServicioBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getServiceByID/ConfigMatrizInventarioPorServicioBusinessBean ==");
        }
    }
 
}
