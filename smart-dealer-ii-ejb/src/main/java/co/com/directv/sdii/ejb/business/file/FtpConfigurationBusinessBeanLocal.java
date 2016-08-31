package co.com.directv.sdii.ejb.business.file;
import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.FtpConfigurationVO;

@Local
public interface FtpConfigurationBusinessBeanLocal {
	
	public void createFtpConfiguration(FtpConfigurationVO ftpConfiguration) throws BusinessException;
	public FtpConfigurationVO getFtpConfigurationById(Long ftpcid) throws BusinessException;
	public FtpConfigurationVO getFtpConfigurationByCode(String ftpcCode) throws BusinessException;
	public void updateFtpConfiguration(FtpConfigurationVO ftpConfiguration) throws BusinessException;
    public void deleteFtpConfiguration(FtpConfigurationVO ftpConfiguration) throws BusinessException;

}
