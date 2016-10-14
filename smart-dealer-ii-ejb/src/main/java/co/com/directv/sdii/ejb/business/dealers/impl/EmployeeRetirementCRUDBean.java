package co.com.directv.sdii.ejb.business.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.dealers.EmployeeRetirementCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Employee;
import co.com.directv.sdii.model.pojo.EmployeeRetirement;
import co.com.directv.sdii.model.pojo.EmployeeStatus;
import co.com.directv.sdii.model.vo.EmployeeRetirementVO;
import co.com.directv.sdii.persistence.dao.dealers.EmployeeDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.EmployeeRetirementDAOLocal;

/**
 * 
 * EJB que implementa las operaciones Tipo CRUD (Create,Read, Update, Delete) de la
 * Entidad EmployeeRetirement
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.dealers.EmployeeRetirementDAOLocal;
 * @see co.com.directv.sdii.ejb.business.dealers.EmployeeRetirementCRUDBeanLocal;
 */
@Stateless(name="EmployeeRetirementCRUDBeanLocal",mappedName="ejb/EmployeeRetirementCRUDBeanLocal")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)

public class EmployeeRetirementCRUDBean  extends BusinessBase implements EmployeeRetirementCRUDBeanLocal {

    @EJB(name="EmployeeRetirementDAOLocal",beanInterface=EmployeeRetirementDAOLocal.class)
    private EmployeeRetirementDAOLocal daoEmployeeRetirement;
    @EJB(name="EmployeeDAOLocal",beanInterface=EmployeeDAOLocal.class)
    private EmployeeDAOLocal daoEmployee;
    
    private final static Logger log = UtilsBusiness.getLog4J(EmployeeCRUDBean.class);

    /**
     * Crea un nuevo retiro de colaborador en la base de datos
     * @param eVo - EmployeeRetirementVO
     * @param employeeStatusId - long
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createEmployeeRetirement(EmployeeRetirementVO eVo, Long employeeStatusId) throws BusinessException {
        log.debug("== iniciando createEmployeeRetirement/EmployeeRetirementCRUDBean ==");
        try {

            if (eVo == null || employeeStatusId == null) {
                log.debug("Parametro obj nulo. No se puede crear EmployeeRetirement");
                throw new IllegalArgumentException("Parametro obj nulo. No se puede crear EmployeeRetirement");
            }

            EmployeeRetirement employeePojo = UtilsBusiness.copyObject(EmployeeRetirement.class, eVo);
            Employee employeeDao = daoEmployee.getEmployeeByID(eVo.getEmployeeId());
            EmployeeStatus employeeStatus = new EmployeeStatus();

            if (employeeDao == null) {
                log.debug("Error al tratar de obtener el empleado: [Cod Employee: " + eVo.getEmployeeId() + "]");
                throw new BusinessException("Error al tratar de obtener el empleado: [Cod Employee: " + eVo.getEmployeeId() + "]");
            }
            employeeStatus.setId(employeeStatusId);
            employeeDao.setEmployeeStatus(employeeStatus);
            daoEmployee.updateEmployee(employeeDao);

            daoEmployeeRetirement.createEmployeeRetirement(employeePojo);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createEmployeeRetirement/EmployeeRetirementCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createEmployeeRetirement/EmployeeRetirementCRUDBean ==");
        }

    }

    /**
     * Hace el llamado al DAO, para eliminar un retiro de un colaborador de la base de datos
     * @param erVO - EmployeeRetirementVO
     * @throws BusinessException 
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteEmployeeRetirement(EmployeeRetirementVO erVO) throws BusinessException {
        log.debug("== iniciando deleteEmployeeRetirement/EmployeeRetirementCRUDBean ==");
        try {

            if (erVO == null) {
                log.debug("Parametro erVO nulo. No se puede eliminar EmployeeRetirement");
                throw new IllegalArgumentException("Parametro erVO nulo. No se puede eliminar EmployeeRetirement");
            }
            EmployeeRetirement employeePojo = UtilsBusiness.copyObject(EmployeeRetirement.class, erVO);
            daoEmployeeRetirement.deleteEmployeeRetirement(employeePojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación deleteEmployeeRetirement/EmployeeRetirementCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteEmployeeRetirement/EmployeeRetirementCRUDBean ==");
        }

    }

    /**
     * Retorna la lista de todos los retiros
     * @return List<EmployeeRetirementVO>
     * @throws BusinessException 
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<EmployeeRetirementVO> getAllEmployeeRetirement() throws BusinessException {
        log.debug("== Inicia getAllEmployeeRetirement/EmployeeRetirementCRUDBean ==");
        try {
            return UtilsBusiness.convertList(daoEmployeeRetirement.getAllEmployeeRetirement(), EmployeeRetirementVO.class);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllEmployeeRetirement/EmployeeRetirementCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllEmployeeRetirement/EmployeeRetirementCRUDBean ==");
        }
    }

    /**
     * Hace el llamado al DAO para buscar un retiro de colaborador por su identificador unico
     * @param id - Long
     * @return EmployeeRetirementVO
     * @throws BusinessException 
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public EmployeeRetirementVO getEmployeeRetirementByID(Long id) throws BusinessException {
        log.debug("== Inicia getEmployeeRetirementByID/EmployeeRetirementCRUDBean ==");
        try {

            if (id == null) {
                log.debug("Parametro id nulo. No se puede obtener EmployeeRetirementById");
                throw new IllegalArgumentException("Parametro id nulo. No se puede obtener EmployeeRetirementById");
            }
            EmployeeRetirement empRetirement = daoEmployeeRetirement.getEmployeeRetirementByID(id);
            if (empRetirement == null) {
                return null;
            }
            EmployeeRetirementVO employee = UtilsBusiness.copyObject(EmployeeRetirementVO.class, empRetirement);
            return employee;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getEmployeeRetirementByID/EmployeeRetirementCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeRetirementByID/EmployeeRetirementCRUDBean ==");
        }
    }

    /**
     * Hace el llamado al DAO para que actualice un registro de retiro de empleado en la vase de datos
     * @param erVO - EmployeeRetirementVO
     * @throws BusinessException 
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateEmployeeRetirement(EmployeeRetirementVO erVO) throws BusinessException {
        log.debug("== Inicia updateEmployeeRetirement/EmployeeRetirementCRUDBean ==");
        try {
            if (erVO == null) {
                log.debug("Parametro erVO nulo. No se puede obtener updateEmployeeRetirement");
                throw new IllegalArgumentException("Parametro erVO nulo. No se puede obtener updateEmployeeRetirement");
            }
            EmployeeRetirement employeeRetirementPojo = UtilsBusiness.copyObject(EmployeeRetirement.class, erVO);
            daoEmployeeRetirement.updateEmployeeRetirement(employeeRetirementPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateEmployeeRetirement/EmployeeRetirementCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateEmployeeRetirement/EmployeeRetirementCRUDBean ==");
        }
    }
}
