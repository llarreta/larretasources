package co.com.directv.sdii.persistence.dao.file.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.LoadFile;
import co.com.directv.sdii.model.pojo.UploadFile;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.file.LoadFileDAOLocal;

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
@Stateless(name="LoadFileDAOLocal",mappedName="ejb/LoadFileDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class LoadFileDAO  extends BaseDao implements LoadFileDAOLocal  {
	
	private static final Log log = LogFactory.getLog(LoadFileDAO.class);
    
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.file.LoadFileDAOLocal#createLoadFile(co.com.directv.sdii.model.pojo.LoadFile)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createLoadFile(LoadFile loadFile)  throws DAOServiceException, DAOSQLException {
        Session session = null ;

        try {
            log.debug("== Inicio createLoadFile/LoadFileDAO ==");
            session = getSession();
            
            session.save(loadFile);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error createLoadFile/LoadFileDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createLoadFile/LoadFileDAO ==");
        }		

	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.file.LoadFileDAOLocal#createLoadFile(co.com.directv.sdii.model.pojo.LoadFile, byte[])
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createLoadFile(LoadFile loadFile,byte[] arrayByte)  throws DAOServiceException, DAOSQLException {
        Session session = null ;

        try {
            log.debug("== Inicio createLoadFile/LoadFileDAO ==");
            session = getSession();
            loadFile.setObjectFile( Hibernate.createBlob(arrayByte, session) );
            session.save(loadFile);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error createLoadFile/LoadFileDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createLoadFile/LoadFileDAO ==");
        }		

	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.file.LoadFileDAOLocal#updateLoadFile(co.com.directv.sdii.model.pojo.LoadFile)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateLoadFile(LoadFile loadFile) throws DAOServiceException, DAOSQLException {
        
        log.debug("== Inicio updateLoadFile/UploadFileDAO ==");
        Session session = super.getSession();
        try {
            session.merge(loadFile);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el LoadFile ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateLoadFile/UploadFileDAO ==");
        }

	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.file.LoadFileDAOLocal#getLoadFileById(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public LoadFile getLoadFileById(Long id)  throws DAOServiceException, DAOSQLException {
        Session session = null ;
		try {
			log.debug("== Inicio getLoadFileById/UploadFileDAO ==" );
			
			session = getSession();

			StringBuffer queryString = new StringBuffer("from ");
		    queryString.append( LoadFile.class.getName() ) ;
		    queryString.append(" as lf ") ;
			queryString.append(" where lf.id = :id");
            Query queryObject = session.createQuery(queryString.toString());			
			queryObject.setLong("id", id);			

			return (LoadFile) queryObject.uniqueResult();
		}catch (Throwable ex) {
			log.error("== Error getLoadFileById/UploadFileDAO ==");
            throw this.manageException(ex);
		}finally {
            log.debug("== Termina getLoadFileById/UploadFileDAO ==");			
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.file.LoadFileDAOLocal#getLoadFileByUploadFileAndCodeLoadFileType(java.lang.Long, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public LoadFile getLoadFileByUploadFileAndCodeLoadFileType(Long idUploadFile,String codeLoadFileType)  throws DAOServiceException, DAOSQLException {
        Session session = null ;
		try {
			log.debug("== Inicio getLoadFileById/UploadFileDAO ==" );
			
			session = getSession();

			StringBuffer queryString = new StringBuffer("from ");
		    queryString.append( LoadFile.class.getName() + " as lf ") ;
			queryString.append(" where lf.uploadFile.id=:idUploadFile ") ;
			queryString.append("       and lf.loadFileType.code=:codeLoadFileType ") ;
					
            Query queryObject = session.createQuery(queryString.toString());			
			queryObject.setLong("idUploadFile", idUploadFile);
			queryObject.setString("codeLoadFileType", codeLoadFileType);

			return (LoadFile) queryObject.uniqueResult();
		}catch (Throwable ex) {
			log.error("== Error getLoadFileById/UploadFileDAO ==");
            throw this.manageException(ex);
		}finally {
            log.debug("== Termina getLoadFileById/UploadFileDAO ==");			
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.file.LoadFileDAOLocal#getLoadFileByUploadFileAndCodeLoadFileType(co.com.directv.sdii.model.pojo.UploadFile, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public LoadFile getLoadFileByUploadFileAndCodeLoadFileType(UploadFile uploadFile,String codeLoadFileType)  throws DAOServiceException, DAOSQLException {
        Session session = null ;
		try {
			log.debug("== Inicio getLoadFileByUploadFileAndCodeLoadFileType/UploadFileDAO ==" );
			
			session = getSession();

			StringBuffer queryString = new StringBuffer("from ");
		    queryString.append( LoadFile.class.getName() + " as lf ") ;
			queryString.append(" where lf.uploadFile.id=:idUploadFile ") ;
			queryString.append("       and lf.loadFileType.code=:codeLoadFileType ") ;
					
            Query queryObject = session.createQuery(queryString.toString());			
			queryObject.setLong("idUploadFile", uploadFile.getId());
			queryObject.setString("codeLoadFileType", codeLoadFileType);

			return (LoadFile) queryObject.uniqueResult();
		}catch (Throwable ex) {
			log.error("== Error getLoadFileByUploadFileAndCodeLoadFileType/UploadFileDAO ==");
            throw this.manageException(ex);
		}finally {
            log.debug("== Termina getLoadFileByUploadFileAndCodeLoadFileType/UploadFileDAO ==");			
		}
	}
	
}