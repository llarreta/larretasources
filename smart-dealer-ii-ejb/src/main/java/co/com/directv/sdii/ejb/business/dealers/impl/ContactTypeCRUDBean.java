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
import co.com.directv.sdii.ejb.business.dealers.ContactTypeCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ContactTypeVO;
import co.com.directv.sdii.persistence.dao.dealers.ContactTypeDAOLocal;

/**
 *
 * EJB que implementa las operaciones Tipo CRUD (Read) de la
 * Entidad ContactType
 * Solo implementa operaciones de consulta
 *
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see co.com.directv.sdii.persistence.contactTypeDAO.dealers.ContactTypeDAOLocal
 * @see co.com.directv.sdii.ejb.business.dealers.ContactTypeCRUDBeanLocal
 *
 */
@Stateless(name="ContactTypeCRUDBeanLocal",mappedName="ejb/ContactTypeCRUDBeanLocal")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ContactTypeCRUDBean extends BusinessBase implements ContactTypeCRUDBeanLocal {

    @EJB(name="ContactTypeDAOLocal",beanInterface=ContactTypeDAOLocal.class)
    private ContactTypeDAOLocal contactTypeDAO;

    private final static Logger log = UtilsBusiness.getLog4J(ContactTypeCRUDBean.class);
    
    /**
    *
    * Metodo: Retorna un listado de todos los registros
    * de la Entidad ContactType
    *
    * @return List<ContactTypeVO>
    * @throws BusinessException <tipo> <descripcion>
    * @author jalopez
    */
   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public List<ContactTypeVO> getAllContactType() throws BusinessException {
       log.debug("== Inicia getAllContactType/ContactTypeCRUDBean ==");
       try {
           List<ContactTypeVO> listVO = UtilsBusiness.convertList(contactTypeDAO.getAllContactType(), ContactTypeVO.class);
           return listVO;
       } catch(Throwable ex){
       	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
    	throw this.manageException(ex);
    } finally {
           log.debug("== Termina getAllContactType/ContactTypeCRUDBean ==");
       }
   }

   /**
    *
    * Metodo: Reorna el resultado de la consulta por codigo
    * de la Entidad ContactType.
    * @param code
    * @return ContactTypeVO
    * @throws BusinessException <tipo> <descripcion>
    * @author jalopez
    */
   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public ContactTypeVO getContactTypeByCode(String code) throws BusinessException {
       log.debug("== Inicia getContactTypeByID/ContactTypeCRUDBean ==");
       try {
           return UtilsBusiness.copyObject(ContactTypeVO.class, contactTypeDAO.getContactTypeByCode(code));
       }catch(Throwable ex){
       	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
    	throw this.manageException(ex);
    } finally {
           log.debug("== Termina getContactTypeByID/ContactTypeCRUDBean ==");
       }
   }

   /**
    *
    * Metodo: Reorna el resultado de la consulta por ID
    * de la Entidad ContactType.
    * @param id
    * @return ContactTypeVo
    * @throws BusinessException <tipo> <descripcion>
    * @author jalopez
    */
   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public ContactTypeVO getContactTypeByID(Long id) throws BusinessException {
       log.debug("== Inicia getContactTypeByID/ContactTypeCRUDBean ==");
       try {
           return UtilsBusiness.copyObject(ContactTypeVO.class, contactTypeDAO.getContactTypeByID(id));
       }catch(Throwable ex){
       	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
    	throw this.manageException(ex);
    } finally {
           log.debug("== Termina getContactTypeByID/ContactTypeCRUDBean ==");
       }
   }
 }
