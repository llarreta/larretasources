package co.com.directv.sdii.persistence.dao.dealers.impl;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.IbsMediaContactType;
import co.com.directv.sdii.model.pojo.MediaContactType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.MediaContactTypesDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de MediaContactTypes
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.MediaContactTypes
 * @see co.com.directv.sdii.model.hbm.MediaContactTypes.hbm.xml
 */
@Stateless(name="MediaContactTypesDAOLocal",mappedName="ejb/MediaContactTypesDAOLocal")
public class MediaContactTypesDAO extends BaseDao implements MediaContactTypesDAOLocal {

	 private final static Logger log = UtilsBusiness.getLog4J(MediaContactTypesDAO.class);

    /**
     * Metodo: Consultar MediaContactTypes por ID
     * @param id
     * @return MediaContactTypes
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public MediaContactType getMediaContactTypesByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getMediaContactTypesByID/MediaContactTypesDAO ==");
        
        MediaContactType obj = null;

        try {
        	Session session = this.getSession();
        	StringBuilder stringQuery = new StringBuilder();
        	stringQuery.append("select mct from ");
        	stringQuery.append(MediaContactType.class.getName());
        	stringQuery.append(" mct where mct.id = :aMctId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aMctId", id);
            obj = (MediaContactType) query.uniqueResult();

            return obj;
        } catch (Throwable ex) {
            log.debug("== Error en getMediaContactTypesByID ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getMediaContactTypesByID/MediaContactTypesDAO ==");
        }
    }

    /**
     * Metodo: Consultar todos los MediaContactTypes
     * @return List<MediaContactTypes>
     * @throws DAOServiceException
     * @throws DAOSQLException 
     */
    @SuppressWarnings("unchecked")
	public List<MediaContactType> getAllMediaContactTypes() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllMediaContactTypes/MediaContactTypesDAO ==");
        
        List<MediaContactType> list = null;

        try {
        	Session session = this.getSession();
        	StringBuilder stringQuery = new StringBuilder();
        	stringQuery.append("from ");
        	stringQuery.append(MediaContactType.class.getName());
        	Query query = session.createQuery(stringQuery.toString());
            list = query.list();

            return list;
        } catch (Throwable ex) {
            log.debug("== Error en getAllMediaContactTypes ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllMediaContactTypes/MediaContactTypesDAO ==");
        }
    }

	@Override
	public MediaContactType getMediaContactTypesByCode(String mediaCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getMediaContactTypesByCode/MediaContactTypesDAO ==");        
        MediaContactType obj = null;
        try {
        	Session session = this.getSession();
        	StringBuilder stringQuery = new StringBuilder();
        	stringQuery.append("select mct from ");
        	stringQuery.append(MediaContactType.class.getName());
        	stringQuery.append(" mct where mct.mediaCode = :aMctCode");
        	Query query = session.createQuery( stringQuery.toString() );
            query.setString("aMctCode", mediaCode);
           
            obj = (MediaContactType) query.uniqueResult();

            return obj;
        } catch (Throwable ex) {
            log.debug("== Error en getMediaContactTypesByCode ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getMediaContactTypesByCode/MediaContactTypesDAO ==");
        }
	}
	
	@Override
	public IbsMediaContactType getMediaContactTypeByIbsCode(String mediaCode, String ibsCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getMediaContactTypeByIbsCode/MediaContactTypesDAO ==");        
		IbsMediaContactType obj = null;
        try {
        	Session session = this.getSession();
        	StringBuilder stringQuery = new StringBuilder();
        	stringQuery.append("select imt from ");
        	stringQuery.append(IbsMediaContactType.class.getName());
        	stringQuery.append(" imt where imt.mediaContactType.mediaCode = :aMctCode");
        	stringQuery.append(" and imt.ibsCode = :aIbsCode");
        	Query query = session.createQuery( stringQuery.toString() );
        	
            query.setString("aMctCode", mediaCode);
            query.setString("aIbsCode", ibsCode);
            
            obj = (IbsMediaContactType) query.uniqueResult();

            return obj;
        } catch (Throwable ex) {
            log.debug("== Error en getMediaContactTypeByIbsCode ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getMediaContactTypeByIbsCode/MediaContactTypesDAO ==");
        }
	}
	
	@Override
	public List<IbsMediaContactType> getIbsMediaContactTypeByMediaContactTypeId(Long mediaContactTypeId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getIbsMediaContactTypeByMediaContactTypeId/MediaContactTypesDAO ==");        
		IbsMediaContactType obj = null;
        try {
        	
            List<IbsMediaContactType> list = null;

        	Session session = this.getSession();
        	StringBuilder stringQuery = new StringBuilder();
        	stringQuery.append("select imt from ");
        	stringQuery.append(IbsMediaContactType.class.getName());
        	stringQuery.append(" imt where imt.mediaContactType.id = :aMediaContactTypeId");
        	Query query = session.createQuery( stringQuery.toString() );
        	
            query.setLong("aMediaContactTypeId", mediaContactTypeId);
          
            list = query.list();
            return list;
        } catch (Throwable ex) {
            log.debug("== Error en getIbsMediaContactTypeByMediaContactTypeId ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getIbsMediaContactTypeByMediaContactTypeId/MediaContactTypesDAO ==");
        }
	}
}
