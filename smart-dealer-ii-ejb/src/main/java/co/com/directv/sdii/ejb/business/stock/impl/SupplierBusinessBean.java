package co.com.directv.sdii.ejb.business.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.stock.SupplierBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Supplier;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SuppliersResponse;
import co.com.directv.sdii.model.vo.SupplierVO;
import co.com.directv.sdii.persistence.dao.stock.SupplierDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD Supplier
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.SupplierDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.SupplierBusinessBeanLocal
 */
@Stateless(name="SupplierBusinessBeanLocal",mappedName="ejb/SupplierBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SupplierBusinessBean extends BusinessBase implements SupplierBusinessBeanLocal {

    @EJB(name="SupplierDAOLocal", beanInterface=SupplierDAOLocal.class)
    private SupplierDAOLocal daoSupplier;
    
    private final static Logger log = UtilsBusiness.getLog4J(SupplierBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.SupplierBusinessBeanLocal#getAllSuppliers()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public SuppliersResponse getAllSuppliers(Long countryId, RequestCollectionInfo requestCollInfo) throws BusinessException {
        log.debug("== Inicia getAllSuppliers/SupplierBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(countryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	SuppliersResponse response = daoSupplier.getAllSuppliers(countryId,requestCollInfo);
        	List<Supplier> suppliers = response.getSuppliers();
        	response.setSuppliersVO( UtilsBusiness.convertList(suppliers, SupplierVO.class) );
        	response.setSuppliers( null );
            return response;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllSuppliers/SupplierBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllSuppliers/SupplierBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.SupplierBusinessBeanLocal#getSuppliersByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public SupplierVO getSupplierByID(Long id) throws BusinessException {
        log.debug("== Inicia getSupplierByID/SupplierBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Supplier objPojo = daoSupplier.getSupplierByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(SupplierVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getSupplierByID/SupplierBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSupplierByID/SupplierBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.SupplierBusinessBeanLocal#createSupplier(co.com.directv.sdii.model.vo.SupplierVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createSupplier(SupplierVO obj) throws BusinessException {
        log.debug("== Inicia createSupplier/SupplierBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
            Supplier objPojo =  UtilsBusiness.copyObject(Supplier.class, obj);
            if(daoSupplier.getSupplierByNit(obj.getSupplierNit())!=null){
            	throw new BusinessException(ErrorBusinessMessages.SUPPLIER_NIT_ALREADY_EXIST.getCode(),ErrorBusinessMessages.SUPPLIER_NIT_ALREADY_EXIST.getMessage());
            }
            if(daoSupplier.getSupplierByCode(obj.getSupplierCode())!=null){
            	throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(),ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
            }
            daoSupplier.createSupplier(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createSupplier/SupplierBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createSupplier/SupplierBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.SupplierBusinessBeanLocal#updateSupplier(co.com.directv.sdii.model.vo.SupplierVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateSupplier(SupplierVO obj) throws BusinessException {
        log.debug("== Inicia updateSupplier/SupplierBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	Supplier objPojo =  UtilsBusiness.copyObject(Supplier.class, obj);
        	Supplier objPojoTmp =  this.daoSupplier.getSupplierByID(obj.getId());
        	if(objPojoTmp!=null){
        		if(!objPojoTmp.getSupplierNit().equals(obj.getSupplierNit())){
        			if(obj.getSupplierNit()!=null && daoSupplier.getSupplierByNit(obj.getSupplierNit())!=null){
                    	throw new BusinessException(ErrorBusinessMessages.SUPPLIER_NIT_ALREADY_EXIST.getCode(),ErrorBusinessMessages.SUPPLIER_NIT_ALREADY_EXIST.getMessage());
                    }
        		}
        		if(!objPojoTmp.getSupplierCode().equals(obj.getSupplierCode())){
        			if(obj.getSupplierCode()!=null &&  daoSupplier.getSupplierByCode(obj.getSupplierCode())!=null){
                    	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
                    }
        		}
                daoSupplier.updateSupplier(objPojo);
        	}else{
        		throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
        	}
            
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n updateSupplier/SupplierBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateSupplier/SupplierBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.SupplierBusinessBeanLocal#deleteSupplier(co.com.directv.sdii.model.vo.SupplierVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteSupplier(SupplierVO obj) throws BusinessException {
        log.debug("== Inicia deleteSupplier/SupplierBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Supplier objPojo =  UtilsBusiness.copyObject(Supplier.class, obj);
            daoSupplier.deleteSupplier(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteSupplier/SupplierBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteSupplier/SupplierBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.SupplierBusinessBeanLocal#getSupplierByCode(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public SupplierVO getSupplierByCode(String code) throws BusinessException {
		log.debug("== Inicia getSupplierByCode/SupplierBusinessBean ==");
        UtilsBusiness.assertNotNull(code, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Supplier objPojo = daoSupplier.getSupplierByCode(code);
            return UtilsBusiness.copyObject(SupplierVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getSupplierByCode/SupplierBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSupplierByCode/SupplierBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.SupplierBusinessBeanLocal#getSupplierByCountry(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<SupplierVO> getSupplierByCountry(Long countryId)
			throws BusinessException {
		log.debug("== Inicia getSupplierByCode/SupplierBusinessBean ==");
        UtilsBusiness.assertNotNull(countryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            List<Supplier> objPojo = daoSupplier.getSupplierByCountryId(countryId);
            return UtilsBusiness.convertList(objPojo, SupplierVO.class);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getSupplierByCode/SupplierBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSupplierByCode/SupplierBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.SupplierBusinessBeanLocal#getSupplierByNit(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public SupplierVO getSupplierByNit(String nit) throws BusinessException {
		log.debug("== Inicia getSupplierByNit/SupplierBusinessBean ==");
        UtilsBusiness.assertNotNull(nit, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Supplier objPojo = daoSupplier.getSupplierByNit(nit);
            return UtilsBusiness.copyObject(SupplierVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getSupplierByNit/SupplierBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSupplierByNit/SupplierBusinessBean ==");
        }
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.SupplierBusinessBeanLocal#getSupplierByActiveStatus()
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<SupplierVO> getSupplierByActiveStatus()
			throws BusinessException {
		log.debug("== Inicia getSupplierByActiveStatus/SupplierBusinessBean ==");
        try {
        	List<Supplier> objPojo = daoSupplier.getSupplierByStatus(CodesBusinessEntityEnum.SUPPLIER_STATUS_ACTIVE.getCodeEntity());
            return UtilsBusiness.convertList(objPojo, SupplierVO.class);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getSupplierByActiveStatus/SupplierBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSupplierByActiveStatus/SupplierBusinessBean ==");
        }
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.SupplierBusinessBeanLocal#getSupplierByActiveStatus(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<SupplierVO> getSupplierByActiveStatus(Long countryId)
			throws BusinessException {
		log.debug("== Inicia getSupplierByActiveStatus/SupplierBusinessBean ==");
        try {
        	List<Supplier> objPojo = daoSupplier.getSupplierByStatusAndCountry(CodesBusinessEntityEnum.SUPPLIER_STATUS_ACTIVE.getCodeEntity(),countryId);
            return UtilsBusiness.convertList(objPojo, SupplierVO.class);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getSupplierByActiveStatus/SupplierBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSupplierByActiveStatus/SupplierBusinessBean ==");
        }
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.SupplierBusinessBeanLocal#getSupplierByInactiveStatus()
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<SupplierVO> getSupplierByInactiveStatus()
			throws BusinessException {
		log.debug("== Inicia getSupplierByActiveStatus/SupplierBusinessBean ==");
        try {
        	List<Supplier> objPojo = daoSupplier.getSupplierByStatus(CodesBusinessEntityEnum.SUPPLIER_STATUS_INACTIVE.getCodeEntity());
            return UtilsBusiness.convertList(objPojo, SupplierVO.class);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getSupplierByActiveStatus/SupplierBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSupplierByActiveStatus/SupplierBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.SupplierBusinessBeanLocal#getSupplierByInactiveStatus(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<SupplierVO> getSupplierByInactiveStatus(Long countryId)
			throws BusinessException {
		log.debug("== Inicia getSupplierByActiveStatus/SupplierBusinessBean ==");
        try {
        	List<Supplier> objPojo = daoSupplier.getSupplierByStatusAndCountry(CodesBusinessEntityEnum.SUPPLIER_STATUS_INACTIVE.getCodeEntity(),countryId);
            return UtilsBusiness.convertList(objPojo, SupplierVO.class);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getSupplierByActiveStatus/SupplierBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSupplierByActiveStatus/SupplierBusinessBean ==");
        }
	}
}
