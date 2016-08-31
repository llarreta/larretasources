package co.com.directv.sdii.ejb.business.stock.impl;

import java.util.Calendar;
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
import co.com.directv.sdii.ejb.business.stock.SerializedBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.ElementDTO;
import co.com.directv.sdii.model.pojo.Element;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.persistence.dao.stock.ElementDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD Serialized
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.SerializedBusinessBeanLocal
 */
@Stateless(name="SerializedBusinessBeanLocal",mappedName="ejb/SerializedBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SerializedBusinessBean extends BusinessBase implements SerializedBusinessBeanLocal {

    @EJB(name="SerializedDAOLocal", beanInterface=SerializedDAOLocal.class)
    private SerializedDAOLocal daoSerialized;
    
    @EJB(name="ElementDAOLocal", beanInterface=ElementDAOLocal.class)
    private ElementDAOLocal daoElement;
    
    
    private final static Logger log = UtilsBusiness.getLog4J(SerializedBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.SerializedBusinessBeanLocal#getAllSerializeds()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<SerializedVO> getAllSerializeds() throws BusinessException {
        log.debug("== Inicia getAllSerializeds/SerializedBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoSerialized.getAllSerializeds(), SerializedVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllSerializeds/SerializedBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllSerializeds/SerializedBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.SerializedBusinessBeanLocal#getSerializedsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public SerializedVO getSerializedByID(Long id) throws BusinessException {
        log.debug("== Inicia getSerializedByID/SerializedBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Serialized objPojo = daoSerialized.getSerializedByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(SerializedVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getSerializedByID/SerializedBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSerializedByID/SerializedBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.SerializedBusinessBeanLocal#createSerialized(co.com.directv.sdii.model.vo.SerializedVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createSerialized(SerializedVO obj) throws BusinessException {
        log.debug("== Inicia createSerialized/SerializedBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
            Serialized objPojo =  UtilsBusiness.copyObject(Serialized.class, obj);
            daoSerialized.createSerialized(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createSerialized/SerializedBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createSerialized/SerializedBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.SerializedBusinessBeanLocal#updateSerialized(co.com.directv.sdii.model.vo.SerializedVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateSerialized(SerializedVO obj) throws BusinessException {
        log.debug("== Inicia updateSerialized/SerializedBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Serialized objPojo =  UtilsBusiness.copyObject(Serialized.class, obj);
            daoSerialized.updateSerialized(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateSerialized/SerializedBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateSerialized/SerializedBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.SerializedBusinessBeanLocal#deleteSerialized(co.com.directv.sdii.model.vo.SerializedVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteSerialized(SerializedVO obj) throws BusinessException {
        log.debug("== Inicia deleteSerialized/SerializedBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getElementId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Serialized objPojo =  UtilsBusiness.copyObject(Serialized.class, obj);
            daoSerialized.deleteSerialized(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteSerialized/SerializedBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteSerialized/SerializedBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.SerializedBusinessBeanLocal#getSerializedBySerial(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public SerializedVO getSerializedBySerial(String serial,Long countryId)
			throws BusinessException {
		log.debug("== Inicia getSerializedBySerial/SerializedBusinessBean ==");
        UtilsBusiness.assertNotNull(serial, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(countryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	Object[] params = new Object[1];			
			params[0] = serial;
            Serialized objPojo = daoSerialized.getSerializedBySerial(serial,countryId);    
            if(objPojo == null)
            	throw new BusinessException(ErrorBusinessMessages.CORE_CR032.getCode(), ErrorBusinessMessages.CORE_CR032.getMessage(params), UtilsBusiness.getParametersWS(params));     
            return UtilsBusiness.copyObject(SerializedVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getSerializedBySerial/SerializedBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSerializedBySerial/SerializedBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.SerializedBusinessBeanLocal#getSerializedBySerial(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public SerializedVO getSerializedBySerial(String serial,String countryCode) throws BusinessException {
		log.debug("== Inicia getSerializedBySerial/SerializedBusinessBean ==");
        UtilsBusiness.assertNotNull(serial, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(countryCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	Object[] params = new Object[1];			
			params[0] = serial;
            Serialized objPojo = daoSerialized.getSerializedBySerial(serial,countryCode);    
            if(objPojo == null)
            	throw new BusinessException(ErrorBusinessMessages.CORE_CR032.getCode(), ErrorBusinessMessages.CORE_CR032.getMessage(params), UtilsBusiness.getParametersWS(params));     
            return UtilsBusiness.copyObject(SerializedVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getSerializedBySerial/SerializedBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSerializedBySerial/SerializedBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.SerializedBusinessBeanLocal#getSerializedByElementId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<SerializedVO> getSerializedByElementId(Long id)	throws BusinessException {
		log.debug("== Inicia getSerializedByElementId/SerializedBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	List<Serialized> listSerialized = daoSerialized.getSerializedByElementId(id);
            return UtilsBusiness.convertList(listSerialized,SerializedVO.class);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getSerializedByElementId/SerializedBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSerializedByElementId/SerializedBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.SerializedBusinessBeanLocal#saveSerializedList(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveSerializedList(List<SerializedVO> listSerialized,Long countryId)throws BusinessException {
		log.debug("== Inicia saveSerializedList/SerializedBusinessBean ==");
        UtilsBusiness.assertNotNull(listSerialized, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	//recorre la lista de serialized recibida
        	for (SerializedVO serializedVo : listSerialized){
        		//busca el elemento correspondiente al serializado
        		Element element = daoElement.getElementByID(serializedVo.getElementId());
        		//si encontro el elemento
        		if (element != null && element.getId() != null){
        			SerializedVO newSerializedVo = new SerializedVO();
        			newSerializedVo.setElement(element);
        			newSerializedVo.setElementId(element.getId());
        			newSerializedVo.setIrd(serializedVo.getIrd());
        			newSerializedVo.setRegistrationDate(Calendar.getInstance().getTime());
        			newSerializedVo.setSerialCode(serializedVo.getSerialCode());
        			if (serializedVo.getSerialized().getSerialCode() != null && !serializedVo.getSerialized().getSerialCode().equals("")){
        				//Busca el serializado vinculado
        				SerializedVO linkedSerialized = this.getSerializedBySerial(serializedVo.getSerialized().getSerialCode(),countryId);
        				//Si encontro el serializado vinculado
        				if (linkedSerialized != null && linkedSerialized.getElementId() != null){
        					newSerializedVo.setSerialized(linkedSerialized);
        					newSerializedVo.setValidSerialLinked(true);
        				}
        			}else{
        				newSerializedVo.setValidSerialLinked(false);
        			}
        			this.updateSerialized(newSerializedVo);
        		//si NO encontro el elemento
        		}else{
        			throw new BusinessException(ErrorBusinessMessages.ELEMENT_NOT_EXIST.getCode(),ErrorBusinessMessages.ELEMENT_NOT_EXIST.getMessage());
        		}
        	}
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operacion saveSerializedList/SerializedBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina saveSerializedList/SerializedBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.SerializedBusinessBeanLocal#getLinkedSerializedBySerializedId(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<SerializedVO> getLinkedSerializedBySerializedId(Long serialized) throws BusinessException{
		log.debug("== Inicia getLinkedSerializedBySerializedId/SerializedBusinessBean ==");
        try {
        	return UtilsBusiness.convertList( daoSerialized.getLinkedSerializedBySerializedId(serialized),SerializedVO.class);
        	
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operacion getLinkedSerializedBySerializedId/SerializedBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getLinkedSerializedBySerializedId/SerializedBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.SerializedBusinessBeanLocal#getSerializedBySerial(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ElementDTO getElementBySerial(String serial,Long countryId) throws BusinessException {
		log.debug("== Inicia getSerializedBySerial/SerializedBusinessBean ==");
        UtilsBusiness.assertNotNull(serial, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	ElementDTO elementDTO = new ElementDTO();
        	Object[] params = new Object[1];			
			params[0] = serial;
            Serialized objPojo = daoSerialized.getSerializedBySerial(serial,countryId);    
            if(objPojo == null)
            	throw new BusinessException(ErrorBusinessMessages.CORE_CR032.getCode(), ErrorBusinessMessages.CORE_CR032.getMessage(params), UtilsBusiness.getParametersWS(params));
            elementDTO.setSerializedVO(UtilsBusiness.copyObject(SerializedVO.class, objPojo));
            return elementDTO;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getSerializedBySerial/SerializedBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSerializedBySerial/SerializedBusinessBean ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.SerializedBusinessBeanLocal#getSerializedBySerialCode(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public SerializedVO getSerializedBySerialCode(String serial,Long countryId)
			throws BusinessException {
		log.debug("== Inicia getSerializedBySerialCode/SerializedBusinessBean ==");
        UtilsBusiness.assertNotNull(serial, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	Object[] params = new Object[1];			
			params[0] = serial;
            Serialized objPojo = daoSerialized.getSerializedBySerial(serial,countryId);    
            if(objPojo == null)
            	return null;
            return UtilsBusiness.copyObject(SerializedVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getSerializedBySerialCode/SerializedBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSerializedBySerialCode/SerializedBusinessBean ==");
        }
	}

}
