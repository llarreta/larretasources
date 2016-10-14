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
import co.com.directv.sdii.ejb.business.dealers.EmployeeMediaContactCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.EmployeeMediaContact;
import co.com.directv.sdii.model.vo.EmployeeMediaContactVO;
import co.com.directv.sdii.persistence.dao.dealers.EmployeeMediaContactDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD (Create,Read, Update, Delete) de la
 * Entidad EmployeeMediaContact
 *  
 * Fecha de Creación: Mar 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.dealers.EmployeeMediaContactDAO
 * @see co.com.directv.sdii.ejb.business.dealers.EmployeeMediaContactCRUDBeanLocal
 */
@Stateless(name="EmployeeMediaContactCRUDBeanLocal",mappedName="ejb/EmployeeMediaContactCRUDBeanLocal")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EmployeeMediaContactCRUDBean extends BusinessBase implements EmployeeMediaContactCRUDBeanLocal {

    @EJB(name="EmployeeMediaContactDAOLocal",beanInterface=EmployeeMediaContactDAOLocal.class)
    private EmployeeMediaContactDAOLocal dao;
    
    private final static Logger log = UtilsBusiness.getLog4J(EmployeeMediaContactCRUDBean.class);

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createEmployeeMediaContact(EmployeeMediaContactVO obj) throws BusinessException {
        log.debug("== createEmployeeMediaContact/EmployeeMediaContactCRUDBean ==");
        try {
            if (obj == null) {
                log.debug("Parametro obj nulo. No se puede crear EmployeeMediaContact");
                throw new BusinessException("Parametro obj nulo. No se puede crear EmployeeMediaContact");
            }
            EmployeeMediaContact emc = UtilsBusiness.copyObject(EmployeeMediaContact.class, obj);
            if (emc == null) {
                throw new BusinessException("No se pudo crear el empleadoMediaContact.");
            }
            dao.createEmployeeMediaContact(emc);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createEmployeeMediaContact/EmployeeMediaContactCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createEmployeeMediaContact/EmployeeMediaContactCRUDBean ==");
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteEmployeeMediaContact(EmployeeMediaContactVO obj) throws BusinessException {
        log.debug("== deleteEmployeeMediaContact/EmployeeMediaContactCRUDBean ==");
        try {
            if (obj == null) {
                log.debug("Parametro obj nulo. No se puede eliminar EmployeeMediaContact");
                throw new BusinessException("Parametro obj nulo. No se puede eliminar EmployeeMediaContact");
            }
            EmployeeMediaContact emc = UtilsBusiness.copyObject(EmployeeMediaContact.class, obj);
            if (emc == null) {
                throw new BusinessException("No se pudo eliminar el empleadoMediaContact.");
            }
            dao.deleteEmployeeMediaContact(emc);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación deleteEmployeeMediaContact/EmployeeMediaContactCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteEmployeeMediaContact/EmployeeMediaContactCRUDBean ==");
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<EmployeeMediaContactVO> getAllEmployeeMediaContact() throws BusinessException {
        log.debug("== getAllEmployeeMediaContact/EmployeeMediaContactCRUDBean ==");
        try {
            List<EmployeeMediaContact> listEmc = dao.getAllEmployeeMediaContact();
            if (listEmc == null) {
                return null;
            }
            return UtilsBusiness.convertList(listEmc, EmployeeMediaContactVO.class);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllEmployeeMediaContact/EmployeeMediaContactCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllEmployeeMediaContact/EmployeeMediaContactCRUDBean ==");
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public EmployeeMediaContactVO getEmployeeMediaContactByID(Long id) throws BusinessException {
        log.debug("== getAllEmployeeMediaContact/EmployeeMediaContactCRUDBean ==");
        try {

            if (id == null) {
                log.debug("Parametro obj nulo. No se puede eliminar EmployeeMediaContact");
                throw new BusinessException("Parametro obj nulo. No se puede eliminar EmployeeMediaContact");
            }
            EmployeeMediaContact emc = dao.getEmployeeMediaContactByID(id);
            if (emc == null) {
                return null;
            }
            return UtilsBusiness.copyObject(EmployeeMediaContactVO.class, emc);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getEmployeeMediaContactByID/EmployeeMediaContactCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllEmployeeMediaContact/EmployeeMediaContactCRUDBean ==");
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateEmployeeMediaContact(EmployeeMediaContactVO obj) throws BusinessException {
        log.debug("== updateEmployeeMediaContact/EmployeeMediaContactCRUDBean ==");
        try {
            if (obj == null) {
                log.debug("Parametro obj nulo. No se puede actualizar EmployeeMediaContact");
                throw new BusinessException("Parametro obj nulo. No se puede actualizar EmployeeMediaContact");
            }
            EmployeeMediaContact emc = UtilsBusiness.copyObject(EmployeeMediaContact.class, obj);
            if (emc == null) {
                throw new BusinessException("No se pudo actualizar el empleadoMediaContact.");
            }
            dao.updateEmployeeMediaContact(emc);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateEmployeeMediaContact/EmployeeMediaContactCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateEmployeeMediaContact/EmployeeMediaContactCRUDBean ==");
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<EmployeeMediaContactVO> getEmployeeMediaContactByEmployeeId(Long id) throws BusinessException {
        log.debug("== getEmployeeMediaContactByEmployeeId/EmployeeMediaContactCRUDBean ==");
        try {
            List<EmployeeMediaContact> listEmc = dao.getEmployeeMediaContactByEmployeeId(id);
            if (listEmc == null) {
                return null;
            }
            return UtilsBusiness.convertList(listEmc, EmployeeMediaContactVO.class);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getEmployeeMediaContactByEmployeeId/EmployeeMediaContactCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeMediaContactByEmployeeId/EmployeeMediaContactCRUDBean ==");
        }
    }
}
