package co.com.directv.sdii.persistence.dao.dealers.impl;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.SdiiTimeZone;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.SdiiTimeZoneDAOLocal;

/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 25/11/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Stateless(name="SdiiTimeZoneDAOLocal",mappedName="ejb/SdiiTimeZoneDAOLocal")
public class SdiiTimeZoneDAO extends BaseDao implements SdiiTimeZoneDAOLocal {

	private final static Logger log = UtilsBusiness.getLog4J(SdiiTimeZoneDAO.class);
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.SdiiTimeZoneDAOLocal#getAllTimeZones()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<SdiiTimeZone> getAllTimeZones() throws DAOServiceException, DAOSQLException  {
		log.debug("== Inicia getAllTimeZones/SdiiTimeZoneDAO ==");
        
        List<SdiiTimeZone> list = null;
        
        try {
        	Session session = this.getSession();
            
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(SdiiTimeZone.class.getName());
            stringQuery.append(" tz ");
            stringQuery.append("order by tz.timeZoneName asc");
            Query query = session.createQuery(stringQuery.toString());
            query.setCacheable(true);
            
            list = query.list();

            return list;
        } catch (Throwable ex) {
            log.debug("== Error en getAllTimeZones ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getAllTimeZones/SdiiTimeZoneDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.SdiiTimeZoneDAOLocal#getTimeZoneById(java.lang.Long)
	 */
	@Override
	public SdiiTimeZone getTimeZoneById(Long id) throws DAOServiceException, DAOSQLException  {
		log.debug("== Inicio getTimeZoneById/SdiiTimeZoneDAO ==");
        
		SdiiTimeZone obj = null;
       
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(SdiiTimeZone.class.getName());
        	stringQuery.append(" tz where tz.id= :aTzId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aTzId", id);
            query.setCacheable(true);
            obj = (SdiiTimeZone)query.uniqueResult();

            return obj;
        } catch (Throwable ex) {
            log.debug("== Error en getTimeZoneById ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getTimeZoneById/SdiiTimeZoneDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.SdiiTimeZoneDAOLocal#getTimeZonesByCountryId(java.lang.Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<SdiiTimeZone> getTimeZonesByCountryId(Long countryId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getTimeZonesByCountryId/SdiiTimeZoneDAO ==");
        
        List<SdiiTimeZone> list = null;
        
        try {
        	Session session = this.getSession();
            
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(SdiiTimeZone.class.getName());
            stringQuery.append(" tz where tz.countryId = :aCountryId");
            stringQuery.append(" order by tz.timeZoneName asc");
            Query query = session.createQuery(stringQuery.toString());

            query.setLong("aCountryId", countryId);
            query.setCacheable(true);
            
            list = query.list();

            return list;
        } catch (Throwable ex) {
            log.debug("== Error en getTimeZonesByCountryId ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getTimeZonesByCountryId/SdiiTimeZoneDAO ==");
        }
	}

}
