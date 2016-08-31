package co.com.directv.sdii.ejb.business.file.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.file.FtpConfigurationBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.vo.FtpConfigurationVO;
import co.com.directv.sdii.persistence.dao.file.FtpConfigurationDAOLocal;

/**
 * Session Bean implementation class FtpConfigurationBusinessBean
 */
@Stateless
public class FtpConfigurationBusinessBean extends BusinessBase implements FtpConfigurationBusinessBeanLocal {

	@EJB(name="FtpConfigurationDAOLocal", beanInterface=FtpConfigurationDAOLocal.class)
	private FtpConfigurationDAOLocal ftpConfigurationDAOLocal;
	
    /**
     * Default constructor. 
     */
    public FtpConfigurationBusinessBean() {
    }
    
	private static final Log log = LogFactory.getLog(FtpConfigurationBusinessBean.class);
	
    /***
     * 
     * @param ftpConfiguration es la configuracion ftp nueva que se desea guardar
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author aharker
     * @throws BusinessException 
     */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public void createFtpConfiguration(FtpConfigurationVO ftpConfiguration) throws BusinessException{
		try {
			ftpConfigurationDAOLocal.createFtpConfiguration(FtpConfigurationVO.convertToFtpConfiguration(ftpConfiguration));
		} catch (DAOServiceException e) {
			e.printStackTrace();
			throw this.manageException(e);
		} catch (DAOSQLException e) {
			e.printStackTrace();
			throw this.manageException(e);
		}
	}
	
	/***
	 * 
	 * @param ftpcid es el id de la configuiracion FTP que se desea consultar
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author aharker
	 * @throws BusinessException 
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public FtpConfigurationVO getFtpConfigurationById(Long ftpcid) throws BusinessException{
		try {
			return new FtpConfigurationVO(ftpConfigurationDAOLocal.getFtpConfigurationById(ftpcid));
		} catch (DAOServiceException e) {
			e.printStackTrace();
			throw this.manageException(e);
		} catch (DAOSQLException e) {
			e.printStackTrace();
			throw this.manageException(e);
		}
	}

	/***
	 * 
	 * @param ftpcCode
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author aharker
	 * @throws BusinessException 
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public FtpConfigurationVO getFtpConfigurationByCode(String ftpcCode) throws BusinessException{
		try {
			return new FtpConfigurationVO(ftpConfigurationDAOLocal.getFtpConfigurationByCode(ftpcCode));
		} catch (DAOServiceException e) {
			e.printStackTrace();
			throw this.manageException(e);
		} catch (DAOSQLException e) {
			e.printStackTrace();
			throw this.manageException(e);
		}
	}

	/***
	 * 
	 * @param ftpConfiguration
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author aharker
	 * @throws BusinessException 
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateFtpConfiguration(FtpConfigurationVO ftpConfiguration) throws BusinessException {
		try {
			ftpConfigurationDAOLocal.updateFtpConfiguration(FtpConfigurationVO.convertToFtpConfiguration(ftpConfiguration));
		} catch (DAOServiceException e) {
			e.printStackTrace();
			throw this.manageException(e);
		} catch (DAOSQLException e) {
			e.printStackTrace();
			throw this.manageException(e);
		}
	}

	/***
	 * 
	 * @param ftpConfiguration
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author aharker
	 * @throws BusinessException 
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteFtpConfiguration(FtpConfigurationVO ftpConfiguration) throws BusinessException{
		try {
			ftpConfigurationDAOLocal.deleteFtpConfiguration(FtpConfigurationVO.convertToFtpConfiguration(ftpConfiguration));
		} catch (DAOServiceException e) {
			e.printStackTrace();
			throw this.manageException(e);
		} catch (DAOSQLException e) {
			e.printStackTrace();
			throw this.manageException(e);
		}
    }

}
