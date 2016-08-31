package co.com.directv.sdii.facade.core.impl;

import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.file.UploadFileBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.core.UploadFileFacadeBeanLocal;
import co.com.directv.sdii.model.dto.FilterUploadFileParamByFileTypeParamNameAndCodeDTO;
import co.com.directv.sdii.model.dto.UploadFileFilterDTO;
import co.com.directv.sdii.model.dto.UploadFileParamByFileTypeParamNameAndCodeDTO;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.UploadFileResponse;
import co.com.directv.sdii.model.vo.FileStatusVO;
import co.com.directv.sdii.model.vo.FileTypeVO;
import co.com.directv.sdii.model.vo.UploadFileVO;

@Stateless(name="UploadFileFacadeBeanLocal",mappedName="ejb/UploadFileFacadeBeanLocal")
@Local({UploadFileFacadeBeanLocal.class})
public class UploadFileFacadeBean implements UploadFileFacadeBeanLocal  {
	
	@EJB(name="UpdaloadFileBusinessBeanLocal",beanInterface=UploadFileBusinessBeanLocal.class)
	private UploadFileBusinessBeanLocal updaloadFileBusinessBean;

	public UploadFileVO findById(Long id)  throws BusinessException {
		return updaloadFileBusinessBean.findById(id);
	}	
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.UpdateFileFacadeBeanLocal#findByTypeAndStatusAndUploadDate(java.lang.String, java.lang.String, java.util.Date, java.util.Date)
	 */
	public List<UploadFileVO> findByTypeAndStatusAndUploadDate(String fileTypeCode, String fileStatusCode, Date initialUploadDate, Date finalUploadDate, String  loginNameUser, String countryCode)  
	   throws BusinessException {
		return updaloadFileBusinessBean.findByTypeAndStatusAndUploadDate(fileTypeCode, fileStatusCode, initialUploadDate, finalUploadDate, loginNameUser, countryCode) ;
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.UploadFileFacadeBeanLocal#findByTypeAndStatusAndUploadDate(co.com.directv.sdii.model.dto.UploadFileFilterDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public UploadFileResponse findByTypeAndStatusAndUploadDate(
			UploadFileFilterDTO filter, RequestCollectionInfo request)
			throws BusinessException{
		return updaloadFileBusinessBean.findByTypeAndStatusAndUploadDate(filter, request);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.UpdateFileFacadeBeanLocal#createUploadFile(co.com.directv.sdii.model.vo.UploadFileVO, javax.activation.DataHandler)
	 */
	public Long createUploadFile(UploadFileVO uploadFileVO, DataHandler dHandler) throws BusinessException {
		return updaloadFileBusinessBean.createUploadFile(uploadFileVO, dHandler);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.UploadFileFacadeBeanLocal#downloadUploadedFile(java.lang.Long)
	 */
	public DataHandler downloadUploadedFile(Long uploadFileId)throws BusinessException{
		return updaloadFileBusinessBean.downloadUploadedFile(uploadFileId);
	}
	
	public DataHandler downloadOutUploadedFile(Long uploadFileId)throws BusinessException{
		return updaloadFileBusinessBean.downloadOutUploadedFile(uploadFileId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.UploadFileFacadeBeanLocal#getAllFileStatus()
	 */
	@Override
	public List<FileStatusVO> getAllFileStatus() throws BusinessException {
		return updaloadFileBusinessBean.getAllFileStatus();
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.UploadFileFacadeBeanLocal#getAllFileTypes()
	 */
	@Override
	public List<FileTypeVO> getAllFileTypes() throws BusinessException {
		return updaloadFileBusinessBean.getAllFileTypes();
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.UploadFileFacadeBeanLocal#getUploadFileParamByFileTypeParamNameAndCode(java.util.List)
	 */
	public List<UploadFileParamByFileTypeParamNameAndCodeDTO> getUploadFileParamByFileTypeParamNameAndCode(List<FilterUploadFileParamByFileTypeParamNameAndCodeDTO> filters) throws BusinessException {
		return updaloadFileBusinessBean.getUploadFileParamByFileTypeParamNameAndCode(filters);
	}
	
}
