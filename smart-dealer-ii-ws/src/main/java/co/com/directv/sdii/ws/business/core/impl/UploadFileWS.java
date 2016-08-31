package co.com.directv.sdii.ws.business.core.impl;

import java.util.List;

import javax.activation.DataHandler;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.core.FileDetailProcessFacadeLocal;
import co.com.directv.sdii.facade.core.UploadFileFacadeBeanLocal;
import co.com.directv.sdii.model.dto.FilterUploadFileParamByFileTypeParamNameAndCodeDTO;
import co.com.directv.sdii.model.dto.UploadFileFilterDTO;
import co.com.directv.sdii.model.dto.UploadFileParamByFileTypeParamNameAndCodeDTO;
import co.com.directv.sdii.model.dto.collection.FileDetailProcessCollectionDTO;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.pojo.FiltersFindUpdateFile;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.UploadFileResponse;
import co.com.directv.sdii.model.vo.FileStatusVO;
import co.com.directv.sdii.model.vo.FileTypeVO;
import co.com.directv.sdii.model.vo.UploadFileVO;
import co.com.directv.sdii.ws.business.core.IUploadFileWS;

@MTOM(threshold=3072)
@WebService(serviceName="UploadFileWSService",
		endpointInterface="co.com.directv.sdii.ws.business.core.IUploadFileWS",
		targetNamespace="http://core.business.ws.sdii.directv.com.co/",
		portName="UploadFileWSPort")	
@Stateless()
public class UploadFileWS  implements IUploadFileWS {


	@EJB(name="UploadFileFacadeBeanLocal", beanInterface=UploadFileFacadeBeanLocal.class)
	private UploadFileFacadeBeanLocal uploadFileFacadeBean;
	
	@EJB(name="FileDetailProcessFacadeLocal", beanInterface=FileDetailProcessFacadeLocal.class)
	private FileDetailProcessFacadeLocal fileDetailProcessFacade;
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.core.IUpdateFileWS#findById(java.lang.Long)
	 */
	@Override
	public UploadFileVO findUploadFileById(Long id)  throws BusinessException {
	   return uploadFileFacadeBean.findById(id); 	
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.core.IUpdateFileWS#findByTypeAndStatusAndUploadDate(co.com.directv.sdii.model.pojo.FiltersFindUpdateFile)
	 */
	@Override
	public List<UploadFileVO> findUploadFileByTypeAndStatusAndUploadDate(FiltersFindUpdateFile filters) throws BusinessException
	{
		return uploadFileFacadeBean.findByTypeAndStatusAndUploadDate( filters.getFileTypeCode() 
				, filters.getFileStatusCode() 
				, filters.getInitialUploadDate() 
				, filters.getFinalUploadDate() 
				, filters.getLoginNameUser()
				, filters.getCountryCode() );
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.core.IUpdateFileWS#createUploadFile(co.com.directv.sdii.model.vo.UploadFileVO, javax.activation.DataHandler)
	 */
	@Override
	public Long createUploadFile(UploadFileVO uploadFileVO, @XmlMimeType("application/octet-stream") DataHandler dHandler) throws BusinessException
	{
		return uploadFileFacadeBean.createUploadFile(uploadFileVO, dHandler);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.core.IUploadFileWS#downloadUploadedFile(java.lang.Long)
	 */
	@Override
	public DataHandler downloadUploadedFile(Long uploadFileId)throws BusinessException{
		return uploadFileFacadeBean.downloadUploadedFile(uploadFileId);
	}
	
	public DataHandler downloadOutUploadedFile(Long uploadFileId)throws BusinessException{
		return uploadFileFacadeBean.downloadOutUploadedFile(uploadFileId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.core.IUploadFileWS#getAllFileStatus()
	 */
	@Override
	public List<FileStatusVO> getAllFileStatus()throws BusinessException{
		return uploadFileFacadeBean.getAllFileStatus();
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.core.IUploadFileWS#getAllFileTypes()
	 */
	@Override
	public List<FileTypeVO> getAllFileTypes()throws BusinessException{
		return uploadFileFacadeBean.getAllFileTypes();
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.core.IUploadFileWS#findUploadedFilesByFilters(co.com.directv.sdii.model.dto.UploadFileFilterDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public UploadFileResponse findUploadedFilesByFilters(
			UploadFileFilterDTO filter,
			RequestCollectionInfo requestCollectionInfo)
			throws BusinessException {
		return uploadFileFacadeBean.findByTypeAndStatusAndUploadDate(filter, requestCollectionInfo);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.core.IUploadFileWS#findFileDetailProcessByFileId(java.lang.Long, co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO)
	 */
	@Override
	public FileDetailProcessCollectionDTO findFileDetailProcessByFileId(
			Long fileId, RequestCollectionInfoDTO requestCollection)
			throws BusinessException {
		return fileDetailProcessFacade.findFileDetailProcessByFileId(fileId, requestCollection);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.core.IUploadFileWS#getUploadFileParamByFileTypeParamNameAndCode(java.util.List)
	 */
	@Override
	public List<UploadFileParamByFileTypeParamNameAndCodeDTO> getUploadFileParamByFileTypeParamNameAndCode(List<FilterUploadFileParamByFileTypeParamNameAndCodeDTO> filters) throws BusinessException { 
		return uploadFileFacadeBean.getUploadFileParamByFileTypeParamNameAndCode(filters);
	}
	
}