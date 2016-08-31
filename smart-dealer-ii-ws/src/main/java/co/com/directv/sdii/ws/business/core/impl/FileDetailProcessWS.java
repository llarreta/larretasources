package co.com.directv.sdii.ws.business.core.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.core.FileDetailProcessFacadeLocal;
import co.com.directv.sdii.model.vo.FileDetailProcessVO;
import co.com.directv.sdii.ws.business.core.IFileDetailProcessWS;

@MTOM(threshold=3072)
@WebService
@Stateless()
public class FileDetailProcessWS   implements IFileDetailProcessWS {

	@EJB(name="FileDetailProcessFacadeLocal", beanInterface=FileDetailProcessFacadeLocal.class)
	private FileDetailProcessFacadeLocal fileDetailProcessFacade;

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.core.IFileDetailProcessWS#findByFileId(java.lang.Long)
	 */
	public List<FileDetailProcessVO> findByFileId(Long fileId)  throws BusinessException {
       return fileDetailProcessFacade.findByFileId(fileId) ; 		
	}
		
}