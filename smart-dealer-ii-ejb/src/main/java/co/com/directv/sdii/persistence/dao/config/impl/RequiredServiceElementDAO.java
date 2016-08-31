package co.com.directv.sdii.persistence.dao.config.impl;


import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.RequiredServiceElement;
import co.com.directv.sdii.model.pojo.RequiredServiceElementId;
import co.com.directv.sdii.model.pojo.Service;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.RequiredServiceElementDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de RequiredServiceElement
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.RequiredServiceElement
 * @see co.com.directv.sdii.persistence.dao.config.RequiredServiceElementDAOLocal
 */
@Stateless(name="RequiredServiceElementDAOLocal",mappedName="ejb/RequiredServiceElementDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RequiredServiceElementDAO extends BaseDao implements RequiredServiceElementDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(RequiredServiceElementDAO.class);

  
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.RequiredServiceElementDAOLocal#createRequiredServiceElement(co.com.directv.sdii.model.pojo.RequiredServiceElement)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createRequiredServiceElement(RequiredServiceElement obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createRequiredServiceElement/RequiredServiceElementDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando RequiredServiceElement ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina createRequiredServiceElement/RequiredServiceElementDAO ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.RequiredServiceElementDAOLocal#getRequiredServiceElementByID(co.com.directv.sdii.model.pojo.RequiredServiceElementId)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public RequiredServiceElement getRequiredServiceElementByID(RequiredServiceElementId id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getRequiredServiceElementByID/RequiredServiceElementDAO ==");
        Session session = super.getSession();
        try {
            Object obj = session.get(RequiredServiceElement.class, id);
            if (obj != null) {
                return (RequiredServiceElement) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error consultando el RequiredServiceElement por ID ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getRequiredServiceElementByID/RequiredServiceElementDAO ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.RequiredServiceElementDAOLocal#updateRequiredServiceElement(co.com.directv.sdii.model.pojo.RequiredServiceElement)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateRequiredServiceElement(RequiredServiceElement obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateRequiredServiceElement/RequiredServiceElementDAO ==");
        Session session = super.getSession();

        try {
            session.merge(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el RequiredServiceElement==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina updateRequiredServiceElement/RequiredServiceElementDAO ==");
        }

    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.RequiredServiceElementDAOLocal#deleteRequiredServiceElement(co.com.directv.sdii.model.pojo.RequiredServiceElement)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteRequiredServiceElement(RequiredServiceElement obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteRequiredServiceElement/RequiredServiceElementDAO ==");
        Session session = super.getSession();

        try {
            session.delete(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el RequiredServiceElement==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina deleteRequiredServiceElement/RequiredServiceElementDAO ==");
        }

    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.RequiredServiceElementDAOLocal#getAll()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<RequiredServiceElement> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/RequiredServiceElementDAO ==");
        Session session = super.getSession();

        try {
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("from RequiredServiceElement requiredServiceElement ");
            queryBuffer.append("order by requiredServiceElement.elementType.typeElementName asc");
            Query query = session.createQuery(queryBuffer.toString());
            /*Query query = session.createQuery("from RequiredServiceElement requiredServiceElement " +
            		"order by requiredServiceElement.elementType.typeElementName asc");*/
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultando los RequiredServiceElement ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getAll/RequiredServiceElementDAO ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.RequiredServiceElementDAOLocal#getRequiredServiceElementByElementType(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<RequiredServiceElement> getRequiredServiceElementByElementType(Long idElementType) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getRequiredServiceElementByElementType/RequiredServiceElementDAO ==");
        Session session = super.getSession();
        try {
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("from ");
            queryBuffer.append(RequiredServiceElement.class.getName());
            queryBuffer.append(" requiredServiceElement ");
            queryBuffer.append("where requiredServiceElement.elementType.id = :idElementType ");
            queryBuffer.append("order by requiredServiceElement.elementType.typeElementName asc");
            Query query = session.createQuery(queryBuffer.toString());
            /*Query query = session.createQuery("from "+RequiredServiceElement.class.getName()+" requiredServiceElement " +
                    "where requiredServiceElement.elementType.id = :idElementType " +
                    "order by requiredServiceElement.elementType.typeElementName asc");*/
           query.setLong("idElementType", idElementType);
           return query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultando el RequiredServiceElement por TipoElemento==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getRequiredServiceElementByElementType/RequiredServiceElementDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.RequiredServiceElementDAOLocal#getRequiredServiceElementByServiceElementType(java.lang.Long, java.lang.Long)
     */
    @SuppressWarnings({ "unchecked", "null" })
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public RequiredServiceElement getRequiredServiceElementByServiceElementType(Long idElementType, Long idService) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getRequiredServiceElementByServiceElementType/RequiredServiceElementDAO ==");
        Session session = super.getSession();
        try {
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("from ");
            queryBuffer.append(RequiredServiceElement.class.getName());
            queryBuffer.append(" requiredServiceElement ");
            queryBuffer.append("where requiredServiceElement.service.id = :idService ");
            queryBuffer.append("and requiredServiceElement.elementType.id = :idElementType ");
            queryBuffer.append("order by requiredServiceElement.elementType.typeElementName asc");
            Query query = session.createQuery(queryBuffer.toString());
            /*Query query = session.createQuery("from "+RequiredServiceElement.class.getName()+" requiredServiceElement " +
                    "where requiredServiceElement.service.id = :idService " +
                    "and requiredServiceElement.elementType.id = :idElementType " +
                    "order by requiredServiceElement.elementType.typeElementName asc");*/
            query.setLong("idService", idService);
            query.setLong("idElementType", idElementType);
            
            List<RequiredServiceElement> requiredServiceElementList = query.list();

            if (requiredServiceElementList != null && requiredServiceElementList.size() > 0) {
                return requiredServiceElementList.get(0);
            }
            return null;

        } catch (Throwable ex) {
            log.debug("== Error consultando el RequiredServiceElement por tipo de elemento y servicio==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getRequiredServiceElementByServiceElementType/RequiredServiceElementDAO ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.RequiredServiceElementDAOLocal#getRequiredServiceElementByService(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<RequiredServiceElement> getRequiredServiceElementByService(Long idService) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getRequiredServiceElementByService/RequiredServiceElementDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("from ");
            queryBuffer.append(RequiredServiceElement.class.getName());
            queryBuffer.append(" requiredServiceElement " );
            queryBuffer.append("where requiredServiceElement.service.id = :idService ");
            queryBuffer.append("and requiredServiceElement.elementType.isActive =:isActive ");
            queryBuffer.append("order by requiredServiceElement.elementType.typeElementName asc");
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("idService", idService);
            query.setString("isActive", CodesBusinessEntityEnum.ELEMENT_TYPE_STATUS_ACTIVE.getCodeEntity());
           return query.list();
        } catch (Throwable ex) {
            log.debug("== Error getRequiredServiceElementByService/RequiredServiceElementDAO ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getRequiredServiceElementByService/RequiredServiceElementDAO ==");
        }
    }
    
    @Override
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<RequiredServiceElement> getRequiredServiceElementsSerialized(Long idService) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getRequiredServiceElementsSerialized/RequiredServiceElementDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("select new ");
            queryBuffer.append(RequiredServiceElement.class.getName());
            queryBuffer.append(" (requiredServiceElement.elementType) ");
            queryBuffer.append("from ");
            queryBuffer.append(RequiredServiceElement.class.getName());
            queryBuffer.append(" requiredServiceElement " );
            queryBuffer.append("where requiredServiceElement.service.id = :idService ");
            queryBuffer.append("and requiredServiceElement.elementType.isSerialized = :aSer ");
            queryBuffer.append("order by requiredServiceElement.elementType.typeElementName asc");
            Query query = session.createQuery(queryBuffer.toString());
          
            query.setString("aSer", "S");
            query.setLong("idService", idService);
           return query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultando getRequiredServiceElementsSerialized/RequiredServiceElementDAO ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getRequiredServiceElementsSerialized/RequiredServiceElementDAO ==");
        }
    }
    
    @Override
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<RequiredServiceElement> getRequiredServiceElementsNotSerialized(Long idService) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getRequiredServiceElementsNotSerialized/RequiredServiceElementDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("select new ");
            queryBuffer.append(RequiredServiceElement.class.getName());
            queryBuffer.append(" (requiredServiceElement.elementType,requiredServiceElement.quantity) ");
            queryBuffer.append("from ");
            queryBuffer.append(RequiredServiceElement.class.getName());
            queryBuffer.append(" requiredServiceElement " );
            queryBuffer.append("where requiredServiceElement.service.id = :idService ");
            queryBuffer.append("and requiredServiceElement.elementType.isSerialized = :aNSer ");
            queryBuffer.append("order by requiredServiceElement.elementType.typeElementName asc");
            Query query = session.createQuery(queryBuffer.toString());
          
            query.setString("aNSer", "N");
            query.setLong("idService", idService);
           return query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultando getRequiredServiceElementsNotSerialized/RequiredServiceElementDAO ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getRequiredServiceElementsNotSerialized/RequiredServiceElementDAO ==");
        }
    }



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.RequiredServiceElementDAOLocal#getRequiredServiceElementByServices(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RequiredServiceElement> getRequiredServiceElementByServices(
			List<Service> services) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getRequiredServiceElementByServices/RequiredServiceElementDAO ==");
        Session session = super.getSession();
        try {
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("from ");
            queryBuffer.append(RequiredServiceElement.class.getName());
            queryBuffer.append(" rqse " );
            queryBuffer.append("where rqse.service.id in (");
            
            for (Service service : services) {
            	queryBuffer.append(service.getId());
            	queryBuffer.append(", ");
			}
            String queryStr = StringUtils.removeEnd(queryBuffer.toString(), ", ");
            
            queryBuffer = new StringBuffer(queryStr);
            queryBuffer.append(") "); 
            queryBuffer.append("order by rqse.elementType.typeElementName asc");
            Query query = session.createQuery(queryBuffer.toString());
            
           return query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultando el RequiredServiceElement por lista de servicios==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getRequiredServiceElementByServices/RequiredServiceElementDAO ==");
        }
	}

}
	