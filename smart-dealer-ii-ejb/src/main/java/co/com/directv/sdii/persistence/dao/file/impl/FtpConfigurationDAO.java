package co.com.directv.sdii.persistence.dao.file.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.FileDetailProcess;
import co.com.directv.sdii.model.pojo.FtpConfiguration;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.file.FtpConfigurationDAOLocal;

/**
 * Session Bean implementation class FtpConfigurationDAO
 */
/***
 * 
 * @author aharker
 * 
 */
@Stateless
public class FtpConfigurationDAO extends BaseDao implements FtpConfigurationDAOLocal {

	private static final Log log = LogFactory.getLog(FtpConfigurationDAO.class);
	
    /**
     * Default constructor. 
     */
    public FtpConfigurationDAO() {
    }

    /***
     * 
     * @param ftpConfiguration es la configuracion ftp nueva que se desea guardar
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author aharker
     */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public void createFtpConfiguration(FtpConfiguration ftpConfiguration) throws DAOServiceException, DAOSQLException {
        Session session = null ;
        try {
            log.debug("== Inicio createFtpConfiguration/FtpConfigurationDAO ==");
            session = getSession();
            session.save(ftpConfiguration);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.error("== Error createFtpConfiguration/FtpConfigurationDAO ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina createFtpConfiguration/FtpConfigurationDAO ==");
        }
	}
	
	
	/***
	 * 
	 * @param ftpcid es el id de la configuiracion FTP que se desea consultar
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author aharker
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public FtpConfiguration getFtpConfigurationById(Long ftpcid)  throws DAOServiceException, DAOSQLException  {
        Session session = null ;
		try {
			log.debug("== Inicio getFtpConfigurationById/FtpConfigurationDAO ==");
			String queryString = "select fc from "+FtpConfiguration.class.getName()+" fc where fc.id = :ftpcid";
			session = getSession();
			Query queryObject = session.createQuery(queryString);
			queryObject.setLong("ftpcid", ftpcid);
			return (FtpConfiguration) queryObject.uniqueResult();
		}catch (Throwable ex) {
            log.error("== Error getFtpConfigurationById/FileDetailProcessDAO ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina getFtpConfigurationById/FileDetailProcessDAO ==");        	
        }
	}

	/***
	 * 
	 * @param ftpcCode
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author aharker
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public FtpConfiguration getFtpConfigurationByCode(String ftpcCode)  throws DAOServiceException, DAOSQLException  {
        Session session = null ;
		try {
			log.debug("== Inicio getFtpConfigurationByCode/FtpConfigurationDAO ==");
			String queryString = "select fc from "+FtpConfiguration.class.getName()+" fc where fc.code = :ftpcCode";
			session = getSession();
			Query queryObject = session.createQuery(queryString);
			queryObject.setString("ftpcCode", ftpcCode);
			return (FtpConfiguration) queryObject.uniqueResult();
		}catch (Throwable ex) {
            log.error("== Error getFtpConfigurationByCode/FileDetailProcessDAO ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina getFtpConfigurationByCode/FileDetailProcessDAO ==");        	
        }
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<FtpConfiguration> getFtpConfigurationByCountryIdAndCode(Long countryId, String code) throws DAOServiceException, DAOSQLException  {
        Session session = null ;
		try {
			log.debug("== Inicio getFtpConfigurationByCountryId/FtpConfigurationDAO ==");
			String queryString = "select fc from "+FtpConfiguration.class.getName()+" fc where fc.country.id = :countryId and fc.ftpConfigurationCode = :code";
			session = getSession();
			Query queryObject = session.createQuery(queryString);
			queryObject.setLong("countryId", countryId);
			queryObject.setString("code", code);
			return (List<FtpConfiguration>) queryObject.list();
		}catch (Throwable ex) {
            log.error("== Error getFtpConfigurationByCountryId/FileDetailProcessDAO ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina getFtpConfigurationByCountryId/FileDetailProcessDAO ==");        	
        }
	}
	
	/***
	 * 
	 * @param ftpConfiguration
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author aharker
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateFtpConfiguration(FtpConfiguration ftpConfiguration) throws DAOServiceException, DAOSQLException {
	    log.debug("== Inicio updateFtpConfiguration/FtpConfigurationDAO ==");
	    Session session = super.getSession();
	    try {
	       session.merge(ftpConfiguration);
	       this.doFlush(session);
	    } catch (Throwable ex) {
	       log.error("== Error actualizando el FtpConfiguration ==");
	       throw this.manageException(ex);
	    } finally {
	       log.debug("== Termina updateFtpConfiguration/FtpConfigurationDAO ==");
	    }
	}

	/***
	 * 
	 * @param ftpConfiguration
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author aharker
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteFtpConfiguration(FtpConfiguration ftpConfiguration) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio deleteIndicators/IndicatorsDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from "+FtpConfiguration.class.getName()+" fc where fc.id = :ftpConfigurationId");
            query.setLong("ftpConfigurationId", ftpConfiguration.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el Indicators ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteIndicators/IndicatorsDAO ==");
        }
    }

}
