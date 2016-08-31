/**
 * 
 */
package co.com.directv.sdii.ejb.business.BusinessArea.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.BusinessArea.BusinessAreaCRUDLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.BusinessAreaVO;
import co.com.directv.sdii.persistence.dao.config.BusinessAreaDAOLocal;

/**
 * @author ialessan
 *
 */


	/**
	 * @author ialessan
	 * marzo 2014
	 * 
	 * release_4.2.1.5_Spira_28780 - Configuracion Masiva de Cobertura  de  La  compañía  por  Clase de Cliente
	 *
	 */

	/**
	 * EJB que implementa las operaciones Tipo CRUD (Create,Read, Update, Delete) de la
	 * Entidad BusinessArea
	 *
	 * @author ialessan
	 */
	@Stateless(name="BusinessAreaCRUDLocal",mappedName="ejb/BusinessAreaCRUDLocal")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@TransactionManagement(TransactionManagementType.CONTAINER)
	public class BusinessAreaCRUDBean extends BusinessBase implements BusinessAreaCRUDLocal {
		
		
	    /*
	      @EJB(name="BusinessAreaCRUDLocal",beanInterface=BusinessAreaCRUDLocal.class)	     
	      private BusinessAreaCRUDLocal businessAreayDAOLocal;
	    */
		
		@EJB(name="BusinessAreaDAOLocal",beanInterface=BusinessAreaDAOLocal.class)
		private BusinessAreaDAOLocal businessAreaDAOLocal;
		
	    private final static Logger log = UtilsBusiness.getLog4J(BusinessAreaCRUDBean.class);
	    
	    /**
	     * Método que permite consultar una BusinessArea por code.
	     */
	    public BusinessAreaVO getBusinessAreaByCode(String businessAreaCode) throws BusinessException {
	    	
	        log.debug("== Inicia getBusinessAreaByCode/BusinessAreaCRUDBean ==");
	        
	        try {
	            if (businessAreaCode == null || businessAreaCode.equals("") || businessAreaCode.isEmpty()) {
	                throw new BusinessException("Parametro Area Code no especificado", new IllegalArgumentException("Param businessAreaCode"));
	            }

	            BusinessAreaVO  businessAreaVO  = businessAreaDAOLocal.getBusinessAreaByCode(businessAreaCode);

	            return businessAreaVO;
	            
	        } catch(Throwable ex){
	        	log.debug("== Error al tratar de ejecutar la getBusinessAreaByCode/BusinessAreaCRUDBean ==");
	        	throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina getBusinessAreaByCode/BusinessAreaCRUDBean ==");
	        }
	    }

	 }
