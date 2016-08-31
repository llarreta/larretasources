package co.com.directv.sdii.persistence.dao.file.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.LoadFileType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.file.LoadFileTypeDAOLocal;

/**
 * A data access object (DAO) providing persistence and search support for
 * LoadFile entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * Fecha de Creación: 24/01/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Stateless(name="LoadFileTypeDAOLocal",mappedName="ejb/LoadFileTypeDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class LoadFileTypeDAO  extends BaseDao implements LoadFileTypeDAOLocal  {
	
	private static final Log log = LogFactory.getLog(LoadFileTypeDAO.class);
    
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createLoadFileType(LoadFileType loadFileType)  throws DAOServiceException, DAOSQLException {
        Session session = null ;

        try {
            log.debug("== Inicio createLoadFileType/LoadFileTypeDAO ==");
            session = getSession();
            
            session.save(loadFileType);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error createLoadFileType/LoadFileTypeDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createLoadFileType/LoadFileTypeDAO ==");
        }		

	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateLoadFileType(LoadFileType loadFileType) throws DAOServiceException, DAOSQLException {
        
        log.debug("== Inicio updateLoadFileType/LoadFileTypeDAO ==");
        Session session = super.getSession();
        try {
            session.merge(loadFileType);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el loadFileType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateLoadFileType/LoadFileTypeDAO ==");
        }

	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public LoadFileType getLoadFileTypeById(Long id)  throws DAOServiceException, DAOSQLException {
        Session session = null ;
		try {
			log.debug("== Inicio getLoadFileTypeById/LoadFileTypeDAO ==" );
			
			session = getSession();

			StringBuffer queryString = new StringBuffer("from ");
		    queryString.append( LoadFileType.class.getName() ) ;
		    queryString.append(" as lft ") ;
			queryString.append(" where lft.id = :id ");
            Query queryObject = session.createQuery(queryString.toString());			
			queryObject.setLong("id", id);			

			return (LoadFileType) queryObject.uniqueResult();
		}catch (Throwable ex) {
			log.error("== Error getLoadFileTypeById/LoadFileTypeDAO ==");
            throw this.manageException(ex);
		}finally {
            log.debug("== Termina getLoadFileTypeById/LoadFileTypeDAO ==");			
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public LoadFileType getLoadFileTypeByCode(String code)  throws DAOServiceException, DAOSQLException {
		Session session = null ;
		try {
			log.debug("== Inicio getLoadFileTypeByCode/LoadFileTypeDAO ==" );
			
			session = getSession();

			StringBuffer queryString = new StringBuffer("from ");
		    queryString.append( LoadFileType.class.getName() + " lft ") ;
			queryString.append(" where lft.code = :aCode ");
            Query queryObject = session.createQuery(queryString.toString());			
			queryObject.setString("aCode", code);			

			return (LoadFileType) queryObject.uniqueResult();
		}catch (Throwable ex) {
			log.error("== Error getLoadFileTypeByCode/LoadFileTypeDAO ==");
            throw this.manageException(ex);
		}finally {
            log.debug("== Termina getLoadFileTypeByCode/LoadFileTypeDAO ==");			
		}
	}
	
}