package co.com.directv.sdii.persistence.dao.stock.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.NotSerialized;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.WarehouseElement;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.NotSerializedDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad NotSerialized
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.NotSerialized
 * @see co.com.directv.sdii.model.hbm.NotSerialized.hbm.xml
 */
@Stateless(name="NotSerializedDAOLocal",mappedName="ejb/NotSerializedDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class NotSerializedDAO extends BaseDao implements NotSerializedDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(NotSerializedDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.NotSerializedsDAOLocal#createNotSerialized(co.com.directv.sdii.model.pojo.NotSerialized)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createNotSerialized(NotSerialized obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createNotSerialized/NotSerializedDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el NotSerialized ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createNotSerialized/NotSerializedDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.NotSerializedsDAOLocal#updateNotSerialized(co.com.directv.sdii.model.pojo.NotSerialized)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateNotSerialized(NotSerialized obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateNotSerialized/NotSerializedDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el NotSerialized ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateNotSerialized/NotSerializedDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.NotSerializedsDAOLocal#deleteNotSerialized(co.com.directv.sdii.model.pojo.NotSerialized)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteNotSerialized(NotSerialized obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteNotSerialized/NotSerializedDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from NotSerialized entity where entity.elementId = :anEntityId");
            query.setLong("anEntityId", obj.getElementId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el NotSerialized ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteNotSerialized/NotSerializedDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.NotSerializedsDAOLocal#getNotSerializedsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public NotSerialized getNotSerializedByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getNotSerializedByID/NotSerializedDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(NotSerialized.class.getName());
        	stringQuery.append(" entity where entity.elementId = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (NotSerialized) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getNotSerializedByID/NotSerializedDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.NotSerializedsDAOLocal#getAllNotSerializeds()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<NotSerialized> getAllNotSerializeds() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllNotSerializeds/NotSerializedDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(NotSerialized.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllNotSerializeds/NotSerializedDAO ==");
        }
    }
    
    /**
	 * Metodo: Obtiene los registros de todos los objetos NotSerialized propios de un WareHouse
	 * @return Lista con los registros de todos los objetos NotSerialized propios de un WareHouse
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la tarea
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la tarea
	 * @author garciniegas
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<NotSerialized> getNotSerializedsByWareHouseId( Warehouse warehouseId ) throws DAOServiceException, DAOSQLException
	{
		log.debug("== Inicia getNotSerializedsByWareHouseId/NotSerializedDAO ==");
        Session session           = super.getSession();
        List<NotSerialized> lista = null;
        
        try {
        	
        	StringBuffer buffer = new StringBuffer();
        	buffer.append("select distinct whouse.notSerialized from ");
        	buffer.append( WarehouseElement.class.getName());
        	buffer.append( " as whouse where whouse.warehouseId.id = :idWareHouse" );
        	buffer.append( " and whouse.actualQuantity > 0" );
        	buffer.append( " and whouse.recordStatus.recordStatusCode = :recordStatusCode" );
        	Query query = session.createQuery(buffer.toString());
        	query.setParameter( "idWareHouse",warehouseId.getId() );
        	query.setParameter( "recordStatusCode" , CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
        	lista = query.list();
            
        } catch (Throwable ex){
        	log.error("== Error =="+ ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getNotSerializedsByWareHouseId/NotSerializedDAO ==");
        }
        return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Double getNotSerializedQuantityByWarehouseIdAndNotSerializedId( Long wareHouseId,Long notSerId ) throws DAOServiceException, DAOSQLException
    {
    	log.debug("== Inicia getNotSerializedQuantityByWarehouseIdAndNotSerializedId/NotSerializedDAO ==");
        Session session           = super.getSession();
        
        try {
        	
        	StringBuffer buffer = new StringBuffer();
        	buffer.append("select sum( wh.actualQuantity ) from ");
        	buffer.append( WarehouseElement.class.getName());
        	buffer.append( " as wh where wh.notSerialized.elementId =:notSerId and wh.warehouseId.id =:wareHouseId" );
        	buffer.append( " and wh.recordStatus.recordStatusCode = :recordStatusCode" );
        	
        	Query query = session.createQuery(buffer.toString());
        	query.setParameter( "notSerId",notSerId);
        	query.setParameter( "wareHouseId",wareHouseId);
        	query.setParameter( "recordStatusCode" , CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());

            return (Double)query.uniqueResult();
            
        } catch (Throwable ex){
        	log.error("== Error =="+ ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getNotSerializedQuantityByWarehouseIdAndNotSerializedId/NotSerializedDAO ==");
        }
    }



	@Override
	public NotSerialized getNotSerializedbyElementTypeID(Long elementTypeId, Long countryId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getNotSerializedByID/NotSerializedDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(NotSerialized.class.getName());
        	stringQuery.append(" entity where entity.element.elementType.id = :elementTypeId ");
        	stringQuery.append(" and entity.element.country.id = :countryId ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("elementTypeId", elementTypeId);
            query.setLong("countryId", countryId);
            return (NotSerialized) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getNotSerializedByID/NotSerializedDAO ==");
        }
	}

	
}
