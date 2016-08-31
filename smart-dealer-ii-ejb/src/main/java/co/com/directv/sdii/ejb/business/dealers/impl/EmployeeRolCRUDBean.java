package co.com.directv.sdii.ejb.business.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.dealers.EmployeeRolCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.EmployeeRol;
import co.com.directv.sdii.model.vo.EmployeeRolVO;
import co.com.directv.sdii.persistence.dao.dealers.EmployeeRolDAOLocal;

/**
 * 
 * EJB que implementa las operaciones Tipo CRUD (Read) de la
 * Entidad EmployeeRol
 * Solo implementa operaciones de consulta
 * 
 * Fecha de Creaci�n: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.dealers.EmployeeRolDAOLocal
 * @see co.com.directv.sdii.ejb.business.dealers.EmployeeRolCRUDBeanLocal
 * 
 */
@Stateless(name="EmployeeRolCRUDBeanLocal",mappedName="ejb/EmployeeRolCRUDBeanLocal")
public class EmployeeRolCRUDBean extends BusinessBase implements EmployeeRolCRUDBeanLocal {

    @EJB(name="EmployeeRolDAOLocal",beanInterface=EmployeeRolDAOLocal.class)
    private EmployeeRolDAOLocal daoEmployeeRol;
    private final static Logger log = UtilsBusiness.getLog4J(EmployeeRolCRUDBean.class);

    /**
     * Obtiene todos los roles de un empleado
     * @return - List<EmployeeRolVO>
     * @throws BusinessException 
     */
    public List<EmployeeRolVO> getAllEmployeeRol() throws BusinessException {
        log.debug("== Inicia getAllEmployeeRol/EmployeeRolCRUDBean ==");
        try {
            List<EmployeeRol> listERol = daoEmployeeRol.getAllEmployeeRol();
            if(listERol == null){
                return null;
            }
            List<EmployeeRolVO> listVo = UtilsBusiness.convertList(listERol, EmployeeRolVO.class);
            return listVo;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllEmployeeRol/EmployeeRolCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllEmployeeRol/EmployeeRolCRUDBean ==");
        }
    }

    /**
     * Obtiene un rol de empleado por el codigo especificado
     * @param code - String
     * @return - EmployeeRolVO
     * @throws BusinessException 
     */
    public EmployeeRolVO getEmployeeRolByCode(String code) throws BusinessException {
        log.debug("== Inicia getEmployeeRolByCode/EmployeeRolCRUDBean ==");
        try {
            if (code == null || code.equals("")) {
                log.debug("Parametro code nulo. No se puede consultar EmployeeRolByCode");
                throw new IllegalArgumentException("Parametro obj code. No se puede consultar EmployeeRolByCode");
            }

            EmployeeRol eRol = daoEmployeeRol.getEmployeeRolByCode(code);
            if (eRol == null) {
                return null;
            }
            EmployeeRolVO employee = UtilsBusiness.copyObject(EmployeeRolVO.class, eRol);
            return employee;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getEmployeeRolByCode/EmployeeRolCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeRolByCode/EmployeeRolCRUDBean ==");
        }
    }

    /**
     * Obtiene un rol de empleado por el id especificado
     * @param id - Long
     * @return - EmployeeRolVO
     * @throws BusinessException 
     */
    public EmployeeRolVO getEmployeeRolByID(Long id) throws BusinessException {
        log.debug("== Inicia getEmployeeRolByID/EmployeeRolCRUDBean ==");
        try {
            if (id == null) {
                log.debug("Parametro id nulo. No se puede consultar EmployeeRolByID");
                throw new IllegalArgumentException("Parametro id nulo. No se puede consultar EmployeeRolByID");
            }
            EmployeeRol eRol = daoEmployeeRol.getEmployeeRolByID(id);
            if (eRol == null) {
                return null;
            }
            EmployeeRolVO employee = UtilsBusiness.copyObject(EmployeeRolVO.class, eRol);
            return employee;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getEmployeeRolByID/EmployeeRolCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeRolByID/EmployeeRolCRUDBean ==");
        }
    }
}
