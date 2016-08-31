/**
 * 
 */
package co.com.directv.sdii.ejb.business.customer.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.customer.CustomerCategoryCRUDLocal;
import co.com.directv.sdii.ejb.business.dealers.impl.DealersCRUDBean;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.CustomerCategory;
import co.com.directv.sdii.model.vo.CustomerCategoryVO;
import co.com.directv.sdii.persistence.dao.config.CustomerCategoryDAOLocal;

/**
 * @author ialessan
 * marzo 2014
 * 
 * release_4.2.1.5_Spira_28780 - Configuracion Masiva de Cobertura  de  La  compañía  por  Clase de Cliente
 *
 */

/**
 * EJB que implementa las operaciones Tipo CRUD (Create,Read, Update, Delete) de la
 * Entidad CustomerCategory
 *
 * @author ialessan
 */
@Stateless(name="CustomerCategoryCRUDLocal",mappedName="ejb/CustomerCategoryCRUDLocal")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CustomerCategoryCRUDBean extends BusinessBase implements CustomerCategoryCRUDLocal {
	
	
    @EJB(name="CustomerCategoryDAOLocal",beanInterface=CustomerCategoryDAOLocal.class)
    private CustomerCategoryDAOLocal customerCategoryDAOLocal;

    private final static Logger log = UtilsBusiness.getLog4J(DealersCRUDBean.class);
    
    /**
     * 
	 * modificacion ialessan
	 * release_4.2.1.5_Spira_28780 - Configuracion Masiva de Cobertura  de  La  compañía  por  Clase de Cliente
	 * marzo 2014
	 * 
     * Método que permite consultar una CustomerCategory por code.
     */
    public CustomerCategoryVO getCustomerCategorByCode(String code) throws BusinessException {
    	
        log.debug("== Inicia getCustomerCategorByCode/CustomerCategoryCRUDBean ==");
        
        try {
            if (code == null || code.equals("") || code.isEmpty()) {
                throw new BusinessException("Parametro Customer Category Code no especificado", new IllegalArgumentException("Param code"));
            }

             CustomerCategory  customerCategory  = customerCategoryDAOLocal.getCustomerCategoryByCode(code);

            return (CustomerCategoryVO) customerCategory;
            
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la getCustomerCategorByCode/CustomerCategoryCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerCategorByCode/CustomerCategoryCRUDBean ==");
        }
    }

 }

    
 
