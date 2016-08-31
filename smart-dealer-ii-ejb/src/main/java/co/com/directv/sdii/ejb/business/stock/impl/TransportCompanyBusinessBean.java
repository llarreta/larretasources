package co.com.directv.sdii.ejb.business.stock.impl;

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
import co.com.directv.sdii.ejb.business.stock.TransportCompanyBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.dto.collection.TransportCompanyDTO;
import co.com.directv.sdii.model.pojo.TransportCompany;
import co.com.directv.sdii.model.pojo.collection.TransportCompanyResponse;
import co.com.directv.sdii.model.vo.TransportCompanyVO;
import co.com.directv.sdii.persistence.dao.stock.TransportCompanyDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD TransportCompany
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.TransportCompanyDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.TransportCompanyBusinessBeanLocal
 */
@Stateless(name="TransportCompanyBusinessBeanLocal",mappedName="ejb/TransportCompanyBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TransportCompanyBusinessBean extends BusinessBase implements TransportCompanyBusinessBeanLocal {

    @EJB(name="TransportCompanyDAOLocal", beanInterface=TransportCompanyDAOLocal.class)
    private TransportCompanyDAOLocal daoTransportCompany;
    
    private final static Logger log = UtilsBusiness.getLog4J(TransportCompanyBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.TransportCompanyBusinessBeanLocal#getAllTransportCompanys()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<TransportCompanyVO> getTransportCompaniesByCountryId(Long countryId) throws BusinessException {
        log.debug("== Inicia getAllTransportCompanys/TransportCompanyBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoTransportCompany.getTransportCompaniesByCountryId(countryId), TransportCompanyVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllTransportCompanys/TransportCompanyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllTransportCompanys/TransportCompanyBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.TransportCompanyBusinessBeanLocal#getTransportCompanysByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public TransportCompanyVO getTransportCompanyByID(Long id) throws BusinessException {
        log.debug("== Inicia getTransportCompanyByID/TransportCompanyBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            TransportCompany objPojo = daoTransportCompany.getTransportCompanyByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(TransportCompanyVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getTransportCompanyByID/TransportCompanyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getTransportCompanyByID/TransportCompanyBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.TransportCompanyBusinessBeanLocal#createTransportCompany(co.com.directv.sdii.model.vo.TransportCompanyVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createTransportCompany(TransportCompanyVO obj) throws BusinessException {
        log.debug("== Inicia createTransportCompany/TransportCompanyBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
        	TransportCompany objPojo = daoTransportCompany.getTransportCompanyByCode(obj.getCompanyCode());
        	if(objPojo != null){
        		log.error("El objeto que intenta crear como nuevo ya existe");
        		throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(), ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
        	}
        	
        	objPojo =  UtilsBusiness.copyObject(TransportCompany.class, obj);
            daoTransportCompany.createTransportCompany(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createTransportCompany/TransportCompanyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createTransportCompany/TransportCompanyBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.TransportCompanyBusinessBeanLocal#updateTransportCompany(co.com.directv.sdii.model.vo.TransportCompanyVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateTransportCompany(TransportCompanyVO obj) throws BusinessException {
        log.debug("== Inicia updateTransportCompany/TransportCompanyBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
        	TransportCompany objPojo = daoTransportCompany.getTransportCompanyByCode(obj.getCompanyCode());
        	if(objPojo != null && objPojo.getId().longValue() != obj.getId().longValue()){
        		log.error("El objeto que intenta crear como nuevo ya existe");
        		throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(), ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
        	}
        	
        	objPojo =  UtilsBusiness.copyObject(TransportCompany.class, obj);
            daoTransportCompany.updateTransportCompany(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateTransportCompany/TransportCompanyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateTransportCompany/TransportCompanyBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.TransportCompanyBusinessBeanLocal#deleteTransportCompany(co.com.directv.sdii.model.vo.TransportCompanyVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteTransportCompany(TransportCompanyVO obj) throws BusinessException {
        log.debug("== Inicia deleteTransportCompany/TransportCompanyBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            TransportCompany objPojo =  UtilsBusiness.copyObject(TransportCompany.class, obj);
            daoTransportCompany.deleteTransportCompany(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteTransportCompany/TransportCompanyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteTransportCompany/TransportCompanyBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.TransportCompanyBusinessBeanLocal#getTransportCompanyByCode(java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public TransportCompanyVO getTransportCompanyByCode(String code)
			throws BusinessException {
		log.debug("== Inicia getTransportCompanyByCode/TransportCompanyBusinessBean ==");
        UtilsBusiness.assertNotNull(code, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            TransportCompany objPojo = daoTransportCompany.getTransportCompanyByCode(code);
            if (objPojo == null) {
            	return null;
            }
            return UtilsBusiness.copyObject(TransportCompanyVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getTransportCompanyByCode/TransportCompanyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getTransportCompanyByCode/TransportCompanyBusinessBean ==");
        }
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public TransportCompanyVO getTransportCompanyByCodeAndCountryId(String code, Long countryId)
			throws BusinessException {
		log.debug("== Inicia getTransportCompanyByCodeAndCountryId/TransportCompanyBusinessBean ==");
        UtilsBusiness.assertNotNull(code, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            TransportCompany objPojo = daoTransportCompany.getTransportCompanyByCodeAndCountryId(code, countryId);
            if (objPojo == null) {
            	return null;
            }
            return UtilsBusiness.copyObject(TransportCompanyVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getTransportCompanyByCodeAndCountryId/TransportCompanyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getTransportCompanyByCodeAndCountryId/TransportCompanyBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.TransportCompanyBusinessBeanLocal#getActiveTransportCompanys()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public TransportCompanyDTO getActiveTransportCompaniesByCountryId(Long countryId, RequestCollectionInfoDTO requestCollInfo)
			throws BusinessException { 
		log.debug("== Inicia getActiveTransportCompanys/TransportCompanyBusinessBean ==");
        try {
        	TransportCompanyDTO transportCompanyDTO;
        	TransportCompanyResponse transportCompanyRes =  daoTransportCompany.getActiveTransportCompaniesByCountryId(countryId, requestCollInfo);
        	List<TransportCompany> transportCompanies = transportCompanyRes.getTransportCompanies();
        	transportCompanyDTO = UtilsBusiness.copyObject( TransportCompanyDTO.class, transportCompanyRes );
        	transportCompanyDTO.setTransportCompaniesVO( UtilsBusiness.convertList(transportCompanies, TransportCompanyVO.class) );
        	
            return transportCompanyDTO;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getActiveTransportCompanys/TransportCompanyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getActiveTransportCompanys/TransportCompanyBusinessBean ==");
        }
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.TransportCompanyBusinessBeanLocal#getActiveTransportCompanys()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public TransportCompanyDTO getAllTransportCompaniesByCountryId(Long countryId, RequestCollectionInfoDTO requestCollInfo)
			throws BusinessException { 
		log.debug("== Inicia getActiveTransportCompanys/TransportCompanyBusinessBean ==");
        try {
        	TransportCompanyDTO transportCompanyDTO;
        	TransportCompanyResponse transportCompanyRes =  daoTransportCompany.getAllTransportCompaniesByCountryId(countryId, requestCollInfo);
        	List<TransportCompany> transportCompanies = transportCompanyRes.getTransportCompanies();
        	transportCompanyDTO = UtilsBusiness.copyObject( TransportCompanyDTO.class, transportCompanyRes );
        	transportCompanyDTO.setTransportCompaniesVO( UtilsBusiness.convertList(transportCompanies, TransportCompanyVO.class) );
        	
            return transportCompanyDTO;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getActiveTransportCompanys/TransportCompanyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
        	log.debug("== Termina getActiveTransportCompanys/TransportCompanyBusinessBean ==");
        }
	}
	
}
