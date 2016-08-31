package co.com.directv.sdii.persistence.dao.file;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.FileDetailProcess;
import co.com.directv.sdii.model.pojo.FtpConfiguration;
/***
 * 
 * @author aharker
 *
 */
@Local
public interface FtpConfigurationDAOLocal {

	public void createFtpConfiguration(FtpConfiguration ftpConfiguration) throws DAOServiceException, DAOSQLException;

	public FtpConfiguration getFtpConfigurationById(Long ftpcid)  throws DAOServiceException, DAOSQLException;
	
	public FtpConfiguration getFtpConfigurationByCode(String ftpcCode)  throws DAOServiceException, DAOSQLException ;
	
	public void updateFtpConfiguration(FtpConfiguration ftpConfiguration) throws DAOServiceException, DAOSQLException ;
	
    public void deleteFtpConfiguration(FtpConfiguration ftpConfiguration) throws DAOServiceException, DAOSQLException ;
    
    public List<FtpConfiguration> getFtpConfigurationByCountryIdAndCode(Long countryId, String code) throws DAOServiceException, DAOSQLException  ;

}
