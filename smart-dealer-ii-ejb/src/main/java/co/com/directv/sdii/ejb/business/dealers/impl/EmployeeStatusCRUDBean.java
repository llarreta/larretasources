package co.com.directv.sdii.ejb.business.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.dealers.EmployeeStatusCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.EmployeeStatus;
import co.com.directv.sdii.model.vo.EmployeeStatusVO;
import co.com.directv.sdii.persistence.dao.dealers.EmployeeStatusDAOLocal;

/**
 * 
 * EJB que implementa las operaciones Tipo CRUD (Read) de la
 * Entidad EmployeeStatus.
 * Solo implementa operaciones de consulta.
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.dealers.EmployeeStatusDAOLocal
 * @see co.com.directv.sdii.ejb.business.dealers.EmployeeStatusCRUDBeanLocal
 * 
 */
@Stateless(name="EmployeeStatusCRUDBeanLocal",mappedName="ejb/EmployeeStatusCRUDBeanLocal")
public class EmployeeStatusCRUDBean extends BusinessBase implements EmployeeStatusCRUDBeanLocal {

    @EJB(name="EmployeeStatusDAOLocal",beanInterface=EmployeeStatusDAOLocal.class)
    private EmployeeStatusDAOLocal daoEmployeeStatus;
    
    private final static Logger log = UtilsBusiness.getLog4J(EmployeeStatusCRUDBean.class);

    /**
     * Obtiene todos los employeeStatus del sistema
     * @return List<EmployeeStatusVO>
     * @throws BusinessException 
     */
    public List<EmployeeStatusVO> getAllEmployeeStatus() throws BusinessException {
        log.debug("== Inicia getAllEmployeeStatus/EmployeeStatusCRUDBean ==");
        try {
            List<EmployeeStatus> listES = daoEmployeeStatus.getAllEmployeeStatus();

            if(listES == null)
                return null;
            List<EmployeeStatusVO> listVo = UtilsBusiness.convertList(listES, EmployeeStatusVO.class);
            return listVo;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllEmployeeStatus/EmployeeStatusCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllEmployeeStatus/EmployeeStatusCRUDBean ==");
        }
    }

    /**
     * Obtiene un employeeStatus por id
     * @return EmployeeStatusVO
     * @throws BusinessException
     */
    public EmployeeStatusVO getEmployeeStatusById(Long id) throws BusinessException {
        log.debug("== Inicia getEmployeeStatusById/EmployeeStatusCRUDBean ==");
        try {
             if (id == null) {
                log.debug("== Error Parametro id con valor establecido en null ==");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode());
            }
            EmployeeStatus employeeStatus = daoEmployeeStatus.getEmployeeStatusById(id);

            if(employeeStatus == null)
                return null;
            EmployeeStatusVO employeeStatusVo = UtilsBusiness.copyObject(EmployeeStatusVO.class, employeeStatus);
            return employeeStatusVo;
            
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getEmployeeStatusById/EmployeeStatusCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeStatusById/EmployeeStatusCRUDBean ==");
        }
    }
}
