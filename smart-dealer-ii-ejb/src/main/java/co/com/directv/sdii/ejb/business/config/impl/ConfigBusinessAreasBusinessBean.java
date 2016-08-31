package co.com.directv.sdii.ejb.business.config.impl;

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
import co.com.directv.sdii.ejb.business.config.ConfigBusinessAreasBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.BusinessAreaVO;
import co.com.directv.sdii.persistence.dao.config.BusinessAreaDAOLocal;

/**
 * Req-0096 - Requerimiento Consolidado Asignador
 * 
 * Clase que implementa el contrato de operaciones para los servicios web
 * de Configuración de Áreas de Negocio.
 *
 * Fecha de Creación: Sab 14, 2013
 * @author ialessan
 * @version 1.0
 *
 * @see co.com.directv.sdii.persistence.dao.config.BusinessAreaDAOLocal
 */
@Stateless(name="ConfigBusinessAreasBusinessLocal",mappedName="ejb/ConfigBusinessAreasBusinessLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ConfigBusinessAreasBusinessBean extends BusinessBase implements ConfigBusinessAreasBusinessLocal {   
    
    @EJB
    private BusinessAreaDAOLocal businessAreaDAOLocal;
    
    private final static Logger log = UtilsBusiness.getLog4J(ConfigBusinessAreasBusinessBean.class);
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigBusinessAreasBusinessLocal#getAllBusinessAreas(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<BusinessAreaVO> getAllBusinessAreas(Long countryId) throws BusinessException {
    	log.debug("== Inicia getAllBusinessAreas/ConfigBusinessAreasBusinessBean ==");
        try {
        	
        	UtilsBusiness.assertNotNull(countryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " el pais es requerido ");        	
            List<BusinessAreaVO> businessAreaVOList = UtilsBusiness.convertList(businessAreaDAOLocal.getAllBusinessAreas(countryId),BusinessAreaVO.class);
            return businessAreaVOList;
            
        } catch (Throwable ex) {
            log.error("== Error al tratar de ejecutar la operacion ConfigBusinessAreasBusinessBean/getAllBusinessAreas");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getAllBusinessAreas/ConfigBusinessAreasBusinessBean ==");
        }

    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigBusinessAreasBusinessLocal#getBusinessAreaByServiceCode(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public BusinessAreaVO getBusinessAreaByServiceCode(String serviceCode) throws BusinessException {
    	log.debug("== Inicia getBusinessAreaByServiceCode/ConfigBusinessAreasBusinessBean ==");
        try {
        	
        	UtilsBusiness.assertNotNull(serviceCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());        	
            BusinessAreaVO businessAreaVO = UtilsBusiness.copyObject(BusinessAreaVO.class, businessAreaDAOLocal.getBusinessAreaByServiceCode(serviceCode));
            return businessAreaVO;
            
        } catch (Throwable ex) {
            log.error("== Error al tratar de ejecutar la operacion getBusinessAreaByServiceCode/ConfigBusinessAreasBusinessBean");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getBusinessAreaByServiceCode/ConfigBusinessAreasBusinessBean ==");
        }

    }
    
    	
}
