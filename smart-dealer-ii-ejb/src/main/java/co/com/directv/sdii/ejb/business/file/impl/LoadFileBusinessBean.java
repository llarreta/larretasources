package co.com.directv.sdii.ejb.business.file.impl;

import java.io.InputStream;

import javax.activation.DataHandler;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.file.LoadFileBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.LoadFile;
import co.com.directv.sdii.model.pojo.UploadFile;
import co.com.directv.sdii.model.vo.LoadFileVO;
import co.com.directv.sdii.persistence.dao.file.LoadFileDAOLocal;
import co.com.directv.sdii.persistence.dao.file.LoadFileTypeDAOLocal;

/**
 * Invoca las operaciones de negocio relacionadas con la entidad LoadFile.
 * 
 * Fecha de Creación: 24/01/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Stateless(name="LoadFileBusinessBeanLocal",mappedName="ejb/LoadFileBusinessBeanLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class LoadFileBusinessBean extends BusinessBase implements LoadFileBusinessBeanLocal {
	
	@EJB(name="LoadFileDAOLocal", beanInterface=LoadFileDAOLocal.class)
	private LoadFileDAOLocal loadFileDAO;
	
	@EJB(name="LoadFileTypeDAOLocal", beanInterface=LoadFileTypeDAOLocal.class)
	private LoadFileTypeDAOLocal loadFileTypeDAO;
	
	private final static Logger log = UtilsBusiness.getLog4J(LoadFileBusinessBean.class);
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.file.LoadFileBusinessBeanLocal#createLoadFile(co.com.directv.sdii.model.pojo.UploadFile, java.lang.String, javax.activation.DataHandler)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createLoadFile(UploadFile uploadFile, 
			                   String codeLoadFileType,
			                   DataHandler dHandler) throws BusinessException {
		
		log.debug("== Inicia createLoadFile/LoadFileBusinessBean ==");
		LoadFile loadFile = null;
		try {
			loadFile = new LoadFile();
			loadFile.setUploadFile(uploadFile);
			loadFile.setLoadFileType(loadFileTypeDAO.getLoadFileTypeByCode(codeLoadFileType));
			InputStream is = dHandler.getInputStream();
			byte[] arrayByte = IOUtils.toByteArray(is);
			
			loadFile.setObjectName(uploadFile.getName());
			loadFileDAO.createLoadFile(loadFile,arrayByte);
		} catch (Throwable t){
			log.debug("== Error al tratar de ejecutar la operación createLoadFile/LoadFileBusinessBean ==", t);
			throw this.manageException(t);
		} finally {
			log.debug("== Termina createLoadFile/LoadFileBusinessBean ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.file.LoadFileBusinessBeanLocal#createLoadFile(co.com.directv.sdii.model.pojo.UploadFile, java.lang.String, byte[])
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createLoadFile(UploadFile uploadFile, 
			                   String codeLoadFileType,
			                   byte[] arrayByte) throws BusinessException {
		
		log.debug("== Inicia createLoadFile/LoadFileBusinessBean ==");
		LoadFile loadFile = null;
		try {
			loadFile = new LoadFile();
			loadFile.setUploadFile(uploadFile);
			loadFile.setLoadFileType(loadFileTypeDAO.getLoadFileTypeByCode(codeLoadFileType));
			loadFile.setObjectName(uploadFile.getName());
			loadFileDAO.createLoadFile(loadFile,arrayByte);
		} catch (Throwable t){
			log.debug("== Error al tratar de ejecutar la operación createLoadFile/LoadFileBusinessBean ==", t);
			throw this.manageException(t);
		} finally {
			log.debug("== Termina createLoadFile/LoadFileBusinessBean ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.file.LoadFileBusinessBeanLocal#getLoadFileByIdUploadFileAndFileOut(java.lang.Long)
	 */
	public LoadFileVO getLoadFileByIdUploadFileAndFileIn(Long idUploadFile) throws BusinessException  {
		
		log.debug("== Inicia getLoadFileByIdUploadFileAndFileOut/LoadFileBusinessBean ==");
		try {
			String code = CodesBusinessEntityEnum.LOAD_FILE_TYPE_IN.getCodeEntity();
			return UtilsBusiness.copyObject(LoadFileVO.class, loadFileDAO.getLoadFileByUploadFileAndCodeLoadFileType(idUploadFile,code));
		} catch (Throwable t){
			log.debug("== Error al tratar de ejecutar la operación getLoadFileByIdUploadFileAndFileOut/LoadFileBusinessBean ==", t);
			throw this.manageException(t);
		} finally {
			log.debug("== Termina getLoadFileByIdUploadFileAndFileOut/LoadFileBusinessBean ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.file.LoadFileBusinessBeanLocal#getLoadFileByIdUploadFileAndFileOut(java.lang.Long)
	 */
	public LoadFileVO getLoadFileByIdUploadFileAndFileOut(Long idUploadFile) throws BusinessException  {
		
		log.debug("== Inicia getLoadFileByIdUploadFileAndFileOut/LoadFileBusinessBean ==");
		try {
			String code = CodesBusinessEntityEnum.LOAD_FILE_TYPE_OUT.getCodeEntity();
			return UtilsBusiness.copyObject(LoadFileVO.class, loadFileDAO.getLoadFileByUploadFileAndCodeLoadFileType(idUploadFile,code));
		} catch (Throwable t){
			log.debug("== Error al tratar de ejecutar la operación getLoadFileByIdUploadFileAndFileOut/LoadFileBusinessBean ==", t);
			throw this.manageException(t);
		} finally {
			log.debug("== Termina getLoadFileByIdUploadFileAndFileOut/LoadFileBusinessBean ==");
		}
	}
	
}
