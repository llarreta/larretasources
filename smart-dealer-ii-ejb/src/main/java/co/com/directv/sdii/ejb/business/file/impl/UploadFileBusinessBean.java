package co.com.directv.sdii.ejb.business.file.impl;

import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.file.LoadFileBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.file.UploadFileBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.FilterUploadFileParamByFileTypeParamNameAndCodeDTO;
import co.com.directv.sdii.model.dto.UploadFileFilterDTO;
import co.com.directv.sdii.model.dto.UploadFileParamByFileTypeParamNameAndCodeDTO;
import co.com.directv.sdii.model.pojo.FileStatus;
import co.com.directv.sdii.model.pojo.FileType;
import co.com.directv.sdii.model.pojo.UploadFile;
import co.com.directv.sdii.model.pojo.UploadFileParam;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.UploadFileResponse;
import co.com.directv.sdii.model.vo.FileStatusVO;
import co.com.directv.sdii.model.vo.FileTypeVO;
import co.com.directv.sdii.model.vo.LoadFileVO;
import co.com.directv.sdii.model.vo.UploadFileParamVO;
import co.com.directv.sdii.model.vo.UploadFileVO;
import co.com.directv.sdii.persistence.dao.file.FileStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.file.FileTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.file.UploadFileDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.UploadFileParamDAOLocal;

/**
 * 
 * Invoca las operaciones de negocio relacionadas con la entidad UploadFile. 
 * 
 * Fecha de Creación: 23/02/2011
 * @author rdelarosa <a href="mailto:rdelarosa@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see UploadFileBusinessBeanLocal
 */
@Stateless(name="UploadFileBusinessBeanLocal",mappedName="ejb/UploadFileBusinessBeanLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UploadFileBusinessBean extends BusinessBase implements UploadFileBusinessBeanLocal {
	
	@EJB(name="FileStatusDAOLocal", beanInterface=FileStatusDAOLocal.class)
	private FileStatusDAOLocal fileStatusDAO;

	@EJB(name="FileTypeDAOLocal", beanInterface=FileTypeDAOLocal.class)
	private FileTypeDAOLocal fileTypeDAO;
	
	@EJB(name="UploadFileDAOLocal", beanInterface=UploadFileDAOLocal.class)
	private UploadFileDAOLocal uploadFileDAO;
	
	@EJB(name="UploadFileParamDAOLocal", beanInterface=UploadFileParamDAOLocal.class)
	private UploadFileParamDAOLocal uploadFileParamDAO;
	
	@EJB(name="LoadFileBusinessBeanLocal", beanInterface=LoadFileBusinessBeanLocal.class)
	private LoadFileBusinessBeanLocal loadFileBusiness;
	
	private final static Logger log = UtilsBusiness.getLog4J(UploadFileBusinessBean.class);
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.file.UpdaloadFileBusinessBeanLocal#findById(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public UploadFileVO findById(Long id)  throws BusinessException {
		UploadFile uploadFile = null ;
		UploadFileVO uploadFileVO = null ;
		
		try {
			uploadFile = uploadFileDAO.findById(id);
			uploadFileVO = new UploadFileVO( uploadFile ) ;
	        return uploadFileVO;		
		} catch (Throwable t){
			log.debug("== Error al tratar de ejecutar la operación createUploadFile/UploadFileBusinessBean ==", t);
			throw this.manageException(t);
		} finally {
			log.debug("== Termina createUploadFile/UploadFileBusinessBean ==");
		}	
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.file.UpdaloadFileBusinessBeanLocal#findByTypeAndStatusAndUploadDate(java.lang.String, java.lang.String, java.util.Date, java.util.Date)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<UploadFileVO> findByTypeAndStatusAndUploadDate(String fileTypeCode, String fileStatusCode, Date initialUploadDate, Date finalUploadDate, String  loginNameUser, String countryCode)  
		throws BusinessException {
		try {
			List<UploadFile> listUploadFile = uploadFileDAO.findByTypeAndStatusAndUploadDate(fileTypeCode, fileStatusCode, initialUploadDate, finalUploadDate, loginNameUser, countryCode);
			List<UploadFileVO> listUploadFileVO = UtilsBusiness.convertList(listUploadFile, UploadFileVO.class);
			return listUploadFileVO ;
		} catch (Throwable t){
			log.debug("== Error al tratar de ejecutar la operación createUploadFile/UploadFileBusinessBean ==", t);
			throw this.manageException(t);
		} finally {
			log.debug("== Termina createUploadFile/UploadFileBusinessBean ==");
		}	
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.file.UploadFileBusinessBeanLocal#findByTypeAndStatusAndUploadDate(co.com.directv.sdii.model.dto.UploadFileFilterDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public UploadFileResponse findByTypeAndStatusAndUploadDate(
			UploadFileFilterDTO filter, RequestCollectionInfo request)
			throws BusinessException {
		log.debug("== Inicia findByTypeAndStatusAndUploadDate/UploadFileBusinessBean ==");
		List<UploadFile> tmpList;
		try {
			UploadFileResponse response = uploadFileDAO.findByTypeAndStatusAndUploadDate(filter,request);
        	tmpList = response.getUploadFile();
        	response.setUploadFileVO(UtilsBusiness.convertList(tmpList, UploadFileVO.class));
        	response.setUploadFile(null);
            
            return response;
		} catch (Throwable t){
			log.debug("== Error al tratar de ejecutar la operación findByTypeAndStatusAndUploadDate/UploadFileBusinessBean ==", t);
			throw this.manageException(t);
		} finally {
			log.debug("== Termina findByTypeAndStatusAndUploadDate/UploadFileBusinessBean ==");
		}
	}
	
    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.file.UpdaloadFileBusinessBeanLocal#createUploadFile(co.com.directv.sdii.model.vo.UploadFileVO, javax.activation.DataHandler)
     */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Long createUploadFile(UploadFileVO uploadFileVO, DataHandler dHandler) throws BusinessException {
		
		log.debug("== Inicia createUploadFile/UploadFileBusinessBean ==");
		FileStatus fileStatus = null ;
		FileType fileType = null ;
		UploadFile uploadFile = null ;		
		try {
			UtilsBusiness.assertNotNull(uploadFileVO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(uploadFileVO.getFileType(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(uploadFileVO.getFileType().getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(uploadFileVO.getName() , ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(uploadFileVO.getUser() , ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(uploadFileVO.getUser().getId() , ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

			//  Vamos a recuperar un FileType 
			fileType = fileTypeDAO.findByCode( uploadFileVO.getFileType().getCode() );
			UtilsBusiness.assertNotNull(fileType, ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage() + " No se encontró el tipo de archivo por el código especificado: " + uploadFileVO.getFileType().getCode());
			uploadFileVO.setFileType(fileType);
			
			// Vamos a recuperar un FileStatus con estado "PENDIENTE" 
			fileStatus = fileStatusDAO.findByCode(CodesBusinessEntityEnum.FILE_STATUS_PENDING.getCodeEntity());
			uploadFileVO.setFileStatus(fileStatus);
			
			java.util.Date loadDate = new java.util.Date(); 
			uploadFileVO.setLoadDate( loadDate );
			
			uploadFile = UtilsBusiness.copyObject(UploadFile.class, uploadFileVO);
			
			uploadFileDAO.save(uploadFile);
			
			//Si tiene parámetros, se almacena
			if(uploadFileVO.getUploadFileParam() != null){
				for(UploadFileParamVO tmp : uploadFileVO.getUploadFileParam()){
					UploadFileParam param = UtilsBusiness.copyObject(UploadFileParam.class, tmp);
					param.setUploadFile(uploadFile);
					uploadFileParamDAO.createUploadFileParam(param);
				}
			}
			
			//Se crea el archivo a cargar
			loadFileBusiness.createLoadFile(uploadFile,
					                        CodesBusinessEntityEnum.LOAD_FILE_TYPE_IN.getCodeEntity(),
					                        dHandler);
			
            
            return uploadFile.getId();
		} catch (Throwable t){
			log.debug("== Error al tratar de ejecutar la operación createUploadFile/UploadFileBusinessBean ==", t);
			throw this.manageException(t);
		} finally {
			log.debug("== Termina createUploadFile/UploadFileBusinessBean ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.file.UploadFileBusinessBeanLocal#downloadUploadedFile(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public DataHandler downloadUploadedFile(Long uploadFileId) throws BusinessException {
		try {
			
			UploadFile fileInfo = uploadFileDAO.findById(uploadFileId);
			UtilsBusiness.assertNotNull(fileInfo, ErrorBusinessMessages.FILE_ELEMENTS_DONT_MATCH.getCode(), ErrorBusinessMessages.FILE_ELEMENTS_DONT_MATCH.getMessage());
			LoadFileVO loadFileVO = loadFileBusiness.getLoadFileByIdUploadFileAndFileIn(fileInfo.getId());
			DataHandler dh = null;
			if(loadFileVO!=null){
				DataSource ds = new FileDataSource(loadFileVO.getFile());
				dh = new DataHandler(ds);
			}
			return dh;
		} catch (Throwable t){
			log.debug("== Error al tratar de ejecutar la operación createUploadFile/UploadFileBusinessBean ==", t);
			throw this.manageException(t);
		} finally {
			log.debug("== Termina createUploadFile/UploadFileBusinessBean ==");
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public DataHandler downloadOutUploadedFile(Long uploadFileId) throws BusinessException {
		try {
			
			UploadFile fileInfo = uploadFileDAO.findById(uploadFileId);
			UtilsBusiness.assertNotNull(fileInfo, ErrorBusinessMessages.FILE_ELEMENTS_DONT_MATCH.getCode(), ErrorBusinessMessages.FILE_ELEMENTS_DONT_MATCH.getMessage());
			LoadFileVO loadFileVO = loadFileBusiness.getLoadFileByIdUploadFileAndFileOut(fileInfo.getId());
			DataHandler dh = null;
			if(loadFileVO!=null){
				DataSource ds = new FileDataSource(loadFileVO.getFile());
				dh = new DataHandler(ds);
			}
			return dh;
			
		} catch (Throwable t){
			log.debug("== Error al tratar de ejecutar la operación createUploadFile/UploadFileBusinessBean ==", t);
			throw this.manageException(t);
		} finally {
			log.debug("== Termina createUploadFile/UploadFileBusinessBean ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.file.UploadFileBusinessBeanLocal#updateUploadFile(co.com.directv.sdii.model.vo.UploadFileVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void updateUploadFile(UploadFileVO uploadFile)
	throws BusinessException {
		log.debug("== Inicia updateUploadFile/UploadFileBusinessBean ==");
		try {
			UploadFile file2Update = UtilsBusiness.copyObject(UploadFile.class, uploadFile);
			uploadFileDAO.updateUploadFile(file2Update);
		} catch (Throwable t){
			log.debug("== Error al tratar de ejecutar la operación updateUploadFile/UploadFileBusinessBean ==", t);
			throw this.manageException(t);
		} finally {
			log.debug("== Termina updateUploadFile/UploadFileBusinessBean ==");
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void updateUploadFile(UploadFileVO uploadFile, String newFileStatusCode)
	throws BusinessException {
		log.debug("== Inicia updateUploadFile/UploadFileBusinessBean ==");
		try {
			FileStatus newFilestatus = fileStatusDAO.findByCode(newFileStatusCode);
			uploadFile.setFileStatus(newFilestatus);
			uploadFile.setProcessDate(new Date());
			UploadFile file2Update = UtilsBusiness.copyObject(UploadFile.class, uploadFile);
			uploadFileDAO.updateUploadFile(file2Update);
		} catch (Throwable t){
			log.debug("== Error al tratar de ejecutar la operación updateUploadFile/UploadFileBusinessBean ==", t);
			throw this.manageException(t);
		} finally {
			log.debug("== Termina updateUploadFile/UploadFileBusinessBean ==");
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<FileStatusVO> getAllFileStatus() throws BusinessException {
		log.debug("== Inicia getAllFileStatus/UploadFileBusinessBean ==");
		try {
			List<FileStatus> allFileStatus = fileStatusDAO.getAllFileStatus();
			List<FileStatusVO> allFileStatusVo = UtilsBusiness.convertList(allFileStatus, FileStatusVO.class);
			return allFileStatusVo;
		} catch (Throwable t){
			log.debug("== Error al tratar de ejecutar la operación getAllFileStatus/UploadFileBusinessBean ==", t);
			throw this.manageException(t);
		} finally {
			log.debug("== Termina getAllFileStatus/UploadFileBusinessBean ==");
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<FileTypeVO> getAllFileTypes() throws BusinessException {
		log.debug("== Inicia getAllFileTypes/UploadFileBusinessBean ==");
		try {
			List<FileType> allFileTypes = fileTypeDAO.getAllFileTypes();
			List<FileTypeVO> allFileTypesVo = UtilsBusiness.convertList(allFileTypes, FileTypeVO.class);
			return allFileTypesVo;
		} catch (Throwable t){
			log.debug("== Error al tratar de ejecutar la operación getAllFileTypes/UploadFileBusinessBean ==", t);
			throw this.manageException(t);
		} finally {
			log.debug("== Termina getAllFileTypes/UploadFileBusinessBean ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.file.UploadFileBusinessBeanLocal#getUploadFileParamByFileTypeParamNameAndCode(java.util.List)
	 */
	public List<UploadFileParamByFileTypeParamNameAndCodeDTO> getUploadFileParamByFileTypeParamNameAndCode(List<FilterUploadFileParamByFileTypeParamNameAndCodeDTO> filters) throws BusinessException {
		log.debug("== Inicia getUploadFileParamByFileTypeParamNameAndCode/UploadFileBusinessBean ==");
		try {
			
			//UtilsBusiness.assertNotNull(uploadFileVO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			return uploadFileParamDAO.getUploadFileParamByFileTypeParamNameAndCode(filters);

		} catch (Throwable t){
			log.debug("== Error al tratar de ejecutar la operación getUploadFileParamByFileTypeParamNameAndCode/UploadFileBusinessBean ==", t);
			throw this.manageException(t);
		} finally {
			log.debug("== Termina getUploadFileParamByFileTypeParamNameAndCode/UploadFileBusinessBean ==");
		}
	}
	
}
