package co.com.directv.sdii.ejb.business.file.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.file.FileDetailProcessBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.collection.FileDetailProcessCollectionDTO;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.pojo.FileDetailProcess;
import co.com.directv.sdii.model.pojo.UploadFile;
import co.com.directv.sdii.model.vo.FileDetailProcessVO;
import co.com.directv.sdii.persistence.dao.file.FileDetailProcessDAOLocal;
import co.com.directv.sdii.persistence.dao.file.UploadFileDAOLocal;

@Stateless(name="FileDetailProcessBusinessBeanLocal",mappedName="ejb/FileDetailProcessBusinessBeanLocal")
public class FileDetailProcessBusinessBean extends BusinessBase implements FileDetailProcessBusinessBeanLocal {

	@EJB(name="FileDetailProcessDAOLocal", beanInterface=FileDetailProcessDAOLocal.class)
	private FileDetailProcessDAOLocal fileDetailProcessDAO ;
	
	@EJB(name="UploadFileDAOLocal", beanInterface=UploadFileDAOLocal.class)
	private UploadFileDAOLocal uploadFileDAO;
	
	private final static Logger log = UtilsBusiness.getLog4J(FileDetailProcessBusinessBean.class);
	
    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.file.FileDetailProcessBusinessBeanLocal#findByFileId(java.lang.Long)
     */
	public List<FileDetailProcessVO> findByFileId(Long fileId)  throws BusinessException  {
		List<FileDetailProcess> listFDP = null ;
		List<FileDetailProcessVO> listFDPVO = null ;
		FileDetailProcessVO fileDetailProcessVO = null ;
		
		try {
			log.debug("== Inicio findByFileId/FileDetailProcessBusinessBean ==");
			
			listFDP = fileDetailProcessDAO.findByFileId(fileId);
						
			if ((listFDP!=null) && ! listFDP.isEmpty()){
			   listFDPVO = UtilsBusiness.convertList(listFDP, FileDetailProcessVO.class);
			}else {
			   listFDPVO = new ArrayList<FileDetailProcessVO>();	
			}
			return listFDPVO;
		} catch (Throwable t){
			log.debug("== Error al tratar de ejecutar la operación findByFileId/FileDetailProcessBusinessBean ==", t);
			throw this.manageException(t);
		} finally {
		   log.debug("== Termina findByFileId/FileDetailProcessBusinessBean ==");
		}	

	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public FileDetailProcessCollectionDTO findFileDetailProcessByFileId(Long fileId, RequestCollectionInfoDTO requestCollection)  throws BusinessException  {
		List<FileDetailProcessVO> listFDPVO = null ;
		FileDetailProcessCollectionDTO response = new FileDetailProcessCollectionDTO();
		try {
			log.debug("== Inicio findFileDetailProcessByFileId/FileDetailProcessBusinessBean ==");
			
			response = fileDetailProcessDAO.findFileDetailByFileId(fileId, requestCollection);
			listFDPVO = UtilsBusiness.convertList(response.getFileDetailProcess(), FileDetailProcessVO.class);
			response.setFileDetailProcess(null);
			response.setFileDetailProcessVos(listFDPVO);
			
			return response;
		} catch (Throwable t){
			log.debug("== Error al tratar de ejecutar la operación findFileDetailProcessByFileId/FileDetailProcessBusinessBean ==", t);
			throw this.manageException(t);
		} finally {
		   log.debug("== Termina findFileDetailProcessByFileId/FileDetailProcessBusinessBean ==");
		}	
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.file.FileDetailProcessBusinessBeanLocal#ceateFleDetailProccess(co.com.directv.sdii.model.vo.FileDetailProcessVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void ceateFleDetailProccess(FileDetailProcessVO fileDetailProVO)
			throws BusinessException {
		log.debug("== Inicia ceateFleDetailProccess/FileDetailProcessBusinessBean ==");
		try{
			fileDetailProcessDAO.save(UtilsBusiness.copyObject(FileDetailProcess.class, fileDetailProVO));
		} catch (Throwable t){
			log.debug("== Error al tratar de ejecutar la operación ceateFleDetailProccess/FileDetailProcessBusinessBean ==", t);
			throw this.manageException(t);
		} finally {
		   log.debug("== Termina ceateFleDetailProccess/FileDetailProcessBusinessBean ==");
		}	
		
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void saveFileDetailProces(long row, String message, Long uploafFileWorkingId) throws BusinessException {
		 try{	
			FileDetailProcess fileDetailProcess = new FileDetailProcess();
			UploadFile uploadFile = uploadFileDAO.findById(uploafFileWorkingId);
			fileDetailProcess.setUploadFile(uploadFile);
			fileDetailProcess.setFdprow( row);
			fileDetailProcess.setMessage(message);
			fileDetailProcessDAO.save(fileDetailProcess);
		 } catch (Throwable t){
				log.debug("== Error al tratar de ejecutar la operación saveFileDetailProces/FileDetailProcessBusinessBean ==", t);
				throw this.manageException(t);
			} finally {
			   log.debug("== Termina saveFileDetailProces/FileDetailProcessBusinessBean ==");
			}	
		}
	
	
}
/*
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<UploadFileVO> findByTypeAndStatusAndUploadDate(String fileTypeCode, String fileStatusCode, Date initialUploadDate, Date finalUploadDate)  
	   throws BusinessException {
		List<UploadFile> listUploadFile = null ;
		List<UploadFileVO> listUploadFileVO = null ;
		UploadFileVO uploadFileVO = null ;
		
		try {
			listUploadFile = uploadFileDAO.findByTypeAndStatusAndUploadDate(fileTypeCode, fileStatusCode, initialUploadDate, finalUploadDate);
			listUploadFileVO = new ArrayList<UploadFileVO> () ;
			for (int i=0 ; i < listUploadFile.size() ; i++ ) {
				uploadFileVO = new UploadFileVO( listUploadFile.get(i) ) ;
				listUploadFileVO.add(uploadFileVO);
			}
			
			return listUploadFileVO ;
		} catch (Throwable t){
			log.debug("== Error al tratar de ejecutar la operación createUploadFile/UploadFileBusinessBean ==", t);
			throw this.manageException(t);
		} finally {
			log.debug("== Termina createUploadFile/UploadFileBusinessBean ==");
		}	
	}
 * */
