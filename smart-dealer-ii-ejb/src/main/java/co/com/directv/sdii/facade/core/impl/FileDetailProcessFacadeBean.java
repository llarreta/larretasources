package co.com.directv.sdii.facade.core.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.file.FileDetailProcessBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.core.FileDetailProcessFacadeLocal;
import co.com.directv.sdii.model.dto.collection.FileDetailProcessCollectionDTO;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.vo.FileDetailProcessVO;


@Stateless(name="FileDetailProcessFacadeLocal",mappedName="ejb/FileDetailProcessFacadeLocal")
@Local({FileDetailProcessFacadeLocal.class})
public class FileDetailProcessFacadeBean implements FileDetailProcessFacadeLocal {
	
	@EJB(name="FileDetailProcessBusinessBeanLocal",beanInterface=FileDetailProcessBusinessBeanLocal.class)
	private FileDetailProcessBusinessBeanLocal fileDetailProcessBusinessBean;

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.FileDetailProcessFacadeLocal#findByFileId(java.lang.Long)
	 */
	@Override
	public List<FileDetailProcessVO> findByFileId(Long fileId)  throws BusinessException {
	   return fileDetailProcessBusinessBean.findByFileId(fileId) ;
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.FileDetailProcessFacadeLocal#findFileDetailProcessByFileId(java.lang.Long, co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO)
	 */
	@Override
	public FileDetailProcessCollectionDTO findFileDetailProcessByFileId(
			Long fileId, RequestCollectionInfoDTO requestCollection)
			throws BusinessException {
		return fileDetailProcessBusinessBean.findFileDetailProcessByFileId(fileId, requestCollection);
	}
}
