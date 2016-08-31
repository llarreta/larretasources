package co.com.directv.sdii.facade.file.impl;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.file.FileProcessorBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.file.FileProcessorFacadeBeanLocal;
import co.com.directv.sdii.facade.file.FileProcessorFacadeRemote;

/**
 * Session Bean implementation class AllocatorFacadeBean
 */
@Stateless(name="FileProcessorFacadeBean")
@Local({FileProcessorFacadeBeanLocal.class})
@Remote({FileProcessorFacadeRemote.class})
public class FileProcessorFacadeBean implements FileProcessorFacadeBeanLocal, FileProcessorFacadeRemote {

	@EJB(name="FileProcessorBusinessBeanLocal", beanInterface=FileProcessorBusinessBeanLocal.class)
	private FileProcessorBusinessBeanLocal fileProcessorBean;
	
	/**
     * Default constructor. 
     */
    public FileProcessorFacadeBean() {
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.file.FileProcessorFacadeBeanLocal#processFiles(java.lang.Long)
	 */
	@Override
	public void processFiles(Long idCountry) throws BusinessException {
		fileProcessorBean.processFiles(idCountry);
		
	}

	

}
