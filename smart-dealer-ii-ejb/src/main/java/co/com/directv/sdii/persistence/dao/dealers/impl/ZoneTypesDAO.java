package co.com.directv.sdii.persistence.dao.dealers.impl;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ZoneType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.ZoneTypesDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de Eps
 * 
 * Fecha de Creación: Mar 15, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Eps
 * @see co.com.directv.sdii.model.hbm.Eps.hbm.xml
 */
@Stateless(name="ZoneTypesDAOLocal",mappedName="ejb/ZoneTypesDAOLocal")
public class ZoneTypesDAO extends BaseDao implements ZoneTypesDAOLocal {
	
    private final static Logger log = UtilsBusiness.getLog4J(ZoneTypesDAO.class);
    

    /**
     * Metodo: Consultar ZoneTypes por ID
     * @param id
     * @return ZoneTypes
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    public ZoneType getZoneTypesByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getZoneTypesByID/ZoneTypesDAO ==");
        
        ZoneType obj = null;
       
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ZoneType.class.getName());
        	stringQuery.append(" zt where zt.id = :aZtId");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + ZoneType.class.getName() + " zt where zt.id = :aZtId");
            query.setLong("aZtId", id);

            obj = (ZoneType) query.uniqueResult();

            return obj;
        } catch (Throwable ex) {
            log.debug("== Error en getZoneTypesByID ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getZoneTypesByID/ZoneTypesDAO ==");
        }
    }	
    
    /**
	 * Metodo: Consultar ZoneTypes por Codigo
	 * @param code
	 * @return ZoneTypes
	 * @throws DAOServiceException 
	 * @throws DAOSQLException <tipo> <descripcion> 
	 * @author jalopez
	 */
    public ZoneType getZoneTypesByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getZoneTypesByCode/ZoneTypesDAO ==");
        
        ZoneType obj = null;
       
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ZoneType.class.getName());
        	stringQuery.append(" zt where zt.zoneTypeCode = :aZtCode");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + ZoneType.class.getName() + " zt where zt.zoneTypeCode = :aZtCode");
            query.setString("aZtCode", code);

            obj = (ZoneType) query.uniqueResult();

            return obj;
        } catch (Throwable ex) {
            log.debug("== Error en getZoneTypesByCode ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getZoneTypesByCode/ZoneTypesDAO ==");
        }
    }
	
    /**
	 * Metodo: Consultar todos los ZoneTypes
	 * @return List<ZoneTypes>
	 * @throws DAOServiceException 
	 * @throws DAOSQLException <tipo> <descripcion> 
	 * @author jalopez
	 */
    @SuppressWarnings("unchecked")
	public List<ZoneType> getAllZoneTypes() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllZoneTypes/ZoneTypesDAO ==");
        
        List<ZoneType> list = null;
        
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ZoneType.class.getName());
        	Query query = session.createQuery(stringQuery.toString());
            list = query.list();

            return list;
        } catch (Throwable ex) {
            log.debug("== Error en getAllZoneTypes ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getAllZoneTypes/ZoneTypesDAO ==");
        }
    }
}