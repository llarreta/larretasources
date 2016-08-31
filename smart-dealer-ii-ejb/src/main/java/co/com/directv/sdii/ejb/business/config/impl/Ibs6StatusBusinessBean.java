/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.com.directv.sdii.ejb.business.config.impl;

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
import co.com.directv.sdii.ejb.business.config.Ibs6StatusBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Ibs6Status;
import co.com.directv.sdii.model.vo.Ibs6StatusVO;
import co.com.directv.sdii.persistence.dao.config.Ibs6StatusDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD (Create,Read, Update, Delete) de
 * la Entidad IBS6Status
 * 
 * @author Jose Andres Casas
 */
@Stateless(name = "Ibs6StatusBusinessBeanLocal", mappedName = "ejb/Ibs6StatusBusinessBeanLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class Ibs6StatusBusinessBean extends BusinessBase implements Ibs6StatusBusinessBeanLocal {
	@EJB(name = "Ibs6StatusDAOLocal", beanInterface = Ibs6StatusDAOLocal.class)
	private Ibs6StatusDAOLocal ibs6StatusDAO;
	private final static Logger log = UtilsBusiness
			.getLog4J(Ibs6StatusBusinessBean.class);

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createIbs6Status(Ibs6StatusVO obj) throws BusinessException {
		log.debug("== Inicia createIbs6Status/Ibs6StatusBusinessBeanBean ==");
		try {
			if (obj == null) {
				throw new IllegalArgumentException("Parametro obj nulo. No se puede crear el Codigo IBS6");
			}
			Ibs6Status ibs6 = UtilsBusiness.copyObject(Ibs6Status.class, obj);
			this.ibs6StatusDAO.createIbs6Status(ibs6);
		} catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion Ibs6StatusBusinessBean/createIbs6Status");
            throw super.manageException(ex);
        } finally {
			log.debug("== Termina createIbs6Status/Ibs6StatusBusinessBeanBean ==");
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Ibs6StatusVO getIbs6StatusByID(Long id) throws BusinessException {
		log.debug("== Inicia getIbs6StatusByID/Ibs6StatusBusinessBeanBean ==");
		try {
			if (id == null) {
				throw new BusinessException("Parametro id no especificado",
						new IllegalArgumentException("Param Id"));
			}

			Ibs6Status ibs6Status = this.ibs6StatusDAO.getIbs6StatusByID(id);
			if (ibs6Status == null) {
				return null;
			}
			return UtilsBusiness.copyObject(Ibs6StatusVO.class, ibs6Status);
		} catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion Ibs6StatusBusinessBean/getIbs6StatusByID");
            throw super.manageException(ex);
        } finally {
			log.debug("== Termina getIbs6StatusByID/Ibs6StatusBusinessBeanBean ==");
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateIbs6Status(Ibs6StatusVO obj) throws BusinessException {
		log.debug("== Inicia updateIbs6Status/Ibs6StatusBusinessBeanBean ==");
		try {
			if (obj == null) {
				throw new IllegalArgumentException(
						"Parametro obj nulo. No se puede actualizar el Ibs6Status");
			}
			Ibs6Status ibs6 = UtilsBusiness.copyObject(Ibs6Status.class, obj);
			this.ibs6StatusDAO.updateIbs6Status(ibs6);
		} catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion Ibs6StatusBusinessBean/updateIbs6Status");
            throw super.manageException(ex);
        } finally {
			log.debug("== Termina updateIbs6Status/Ibs6StatusBusinessBeanBean ==");
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteIbs6Status(Ibs6StatusVO obj) throws BusinessException {
		log.debug("== Inicia deleteIbs6Status/Ibs6StatusBusinessBeanBean ==");
		try {
			if (obj == null) {
				throw new IllegalArgumentException(
						"Parametro obj nulo. No se puede eliminar el Ibs6Status");
			}
			Ibs6Status ibs6 = UtilsBusiness.copyObject(Ibs6Status.class, obj);
			this.ibs6StatusDAO.updateIbs6Status(ibs6);
		} catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion Ibs6StatusBusinessBean/deleteIbs6Status");
            throw super.manageException(ex);
        } finally {
			log.debug("== Termina deleteIbs6Status/Ibs6StatusBusinessBeanBean ==");
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Ibs6StatusVO> getAll() throws BusinessException {
		log.debug("== Inicia getAll/Ibs6StatusBusinessBeanBean ==");
		try {
			List<Ibs6Status> listIbs6Status = this.ibs6StatusDAO.getAll();
			if (listIbs6Status == null)
				return null;
			return UtilsBusiness.convertList(listIbs6Status, Ibs6StatusVO.class);
		} catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion Ibs6StatusBusinessBean/getAll");
            throw super.manageException(ex);
        } finally {
			log.debug("== Termina getAll/Ibs6StatusBusinessBeanBean ==");
		}
	}
}
