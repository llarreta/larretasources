/**
 * 
 */
package co.com.directv.sdii.ejb.business.file;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.file.DTO.FileRecordDTO;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.FileDetailProcess;
import co.com.directv.sdii.model.pojo.UploadFile;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.vo.LoadFileVO;
import co.com.directv.sdii.model.vo.UploadFileVO;
import co.com.directv.sdii.persistence.dao.file.FileDetailProcessDAOLocal;

/**
 * Interfaz que define los métodos de ejecución
 * del patrón comando.
 * 
 * Fecha: 15-02-2011 
 * @author jvelezmu
 *
 */
public abstract class FileProcessor extends BusinessBase implements IFileProcessor{
	
	private List<FileRecordDTO> fileData;
	private User user;
	private UploadFile uploadFile; 
	
	 private final static Logger log = UtilsBusiness.getLog4J(FileProcessor.class);

	 @EJB(name="FileDetailProcessDAOLocal", beanInterface=FileDetailProcessDAOLocal.class)
	 private FileDetailProcessDAOLocal fileDetailProcessDAO ;
	 
	 @EJB(name="UploadFileBusinessBeanLocal", beanInterface=UploadFileBusinessBeanLocal.class)
	 private UploadFileBusinessBeanLocal uploadFileBusiness ;
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.file.IFileProcessor#readFile(java.lang.String)
	 */
	public void readFile(LoadFileVO loadFileVO)throws BusinessException{
		log.debug("== Inicia readFile/FileProcessorAbstract ==");
		fileData = new ArrayList<FileRecordDTO>();
        try {
        	uploadFile = loadFileVO.getUploadFile();
        	user = loadFileVO.getUploadFile().getUser();
        	log.debug(" user:   " + user.getId());
        	//genera el libro
        	Workbook wb = UtilsBusiness.getWorkbook(loadFileVO.getFile()/*, objFile.getName()*/);
        	Sheet sheet = wb.getSheetAt(0);

        	boolean isEmptyRow = false;
        	//Recorre las filas del archivo
        	for (int i = 0; i <= sheet.getLastRowNum() && !isEmptyRow; i++) {
        		FileRecordDTO dto =  new FileRecordDTO();
        		Row row = sheet.getRow(i);
        		
        		if(row == null){// esto sucede cuando hay filas vacías intermedias
        			isEmptyRow = true;
        			continue;
        		}
        		if(row.getLastCellNum()<=0){
        			continue;
        		}
        		String[] values = new String[row.getLastCellNum()];
        		//Recorre las celdas de la fila
        		for (short j = 0;j < row.getLastCellNum(); j++) {
        			Cell celda = row.getCell(j);
        			values[j] = UtilsBusiness.getCellValue(celda);
        		}

        		isEmptyRow = isEmptyRow(values);
        		if(!isEmptyRow) {
	        		dto.setRow(i + 1);//se corre en 1 teniendo en cuenta que hay una fila de títulos y los errores deben indicar el número de la fila en el archivo
	        		dto.setRowData(values);
	        		fileData.add(dto);
        		}
        		
        	}
        } catch(Throwable ex){
        	log.error(ex);
        	try{
				if (ex.getMessage() != null){
					saveFileDetailProcess(0L,ex.getMessage());
				}
				updateFileStatus(CodesBusinessEntityEnum.FILE_STATUS_PROCESS_ENDED_WITH_ERRORS.getCodeEntity(),  UtilsBusiness.copyObject(UploadFileVO.class, this.uploadFile));
			 }catch (Exception pf){
				 log.error("== Se produce un error al guardar mensaje de error al procesar un archivo ==" + pf.getMessage());
			 }
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina readFile/FileProcessorAbstract ==");
        }
	}
	
	public void deleteFile(String pathFile) throws BusinessException{
		UtilsBusiness.deleteFile(pathFile);
	}
/*
	public static void main(String... args) {
		ArrayList<FileRecordDTO> fileData = new ArrayList<FileRecordDTO>();
        try {
        	String path = "c:/dtv/sdii/fileUploads/mov_ele_entre_bod/1441.hsp";
        	//1. Obtener el archivo
        	File file = new File (path) ;
        	//genera el libro
        	Workbook wb = UtilsBusiness.getWorkbookByFileName(file, "algo.xls");
        	Sheet sheet = wb.getSheetAt(0);

        	boolean isEmptyRow = false;
        	//Recorre las filas del archivo
        	for (int i = 0; i <= sheet.getLastRowNum() && !isEmptyRow; i++) {
        		FileRecordDTO dto =  new FileRecordDTO();
        		Row row = sheet.getRow(i);
        		
        		if(row == null){// esto sucede cuando hay filas vacías intermedias
        			isEmptyRow = true;
        			continue;
        		}
        		
        		String[] values = new String[row.getLastCellNum()];
        		//Recorre las celdas de la fila
        		for (short j = 0;j < row.getLastCellNum(); j++) {
        			Cell celda = row.getCell(j);
        			values[j] = UtilsBusiness.getCellValue(celda);
        		}

        		isEmptyRow = isEmptyRow(values);
        		if(!isEmptyRow) {
	        		dto.setRow(i + 1);//se corre en 1 teniendo en cuenta que hay una fila de títulos y los errores deben indicar el número de la fila en el archivo
	        		dto.setRowData(values);
	        		fileData.add(dto);
        		}
        		
        	}
        } catch(Throwable ex){
        	log.error(ex);
        } finally {
            log.debug("== Termina readFile/FileProcessorAbstract ==");
        }
	}
	
	
	private static boolean isEmptyRow2(String[] values) {
		if(values != null) {
			for (int j = 0; j < values.length; j++) {
				if(!StringUtils.isBlank(values[j])) {
					return false;
				}
			}
		}
		return true;
	}*/
	
	private boolean isEmptyRow(String[] values) {
		if(values != null) {
			for (int j = 0; j < values.length; j++) {
				if(!StringUtils.isBlank(values[j])) {
					return false;
				}
			}
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.file.IFileProcessor#processFile()
	 */
	public abstract void processFile(LoadFileVO loadFileVO) throws BusinessException; 
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.file.IFileProcessor#getFileData()
	 */
	public List<FileRecordDTO> getFileData() {
		return fileData;
	}
	public void setFileData(List<FileRecordDTO> fileData) {
		this.fileData = fileData;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setUploadFile(UploadFile uploadFile) {
		this.uploadFile = uploadFile;
	}

	public UploadFile getUploadFile() {
		return uploadFile;
	}
	
	private void updateFileStatus(String newFileStatusCode, UploadFileVO file2Update) throws DAOServiceException, DAOSQLException, BusinessException{
		uploadFileBusiness.updateUploadFile(file2Update, newFileStatusCode);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void saveFileDetailProcess(long row, String message) {
		try {
			FileDetailProcess fileDetailProcess = new FileDetailProcess();
			//UploadFile uploadFile = uploadFileDAO.findById(this.uploadFile.getId());
			fileDetailProcess.setUploadFile(uploadFile);
			fileDetailProcess.setFdprow(row);
			if(message == null) message = "";
			fileDetailProcess.setMessage(message);
			fileDetailProcessDAO.save(fileDetailProcess);
		} catch (Exception dse) {
			log.error(dse);
		} 
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void saveFileDetailProcess(FileDetailProcess fileDetailProcess) {
		try {
			if(fileDetailProcess != null) {
				if(fileDetailProcess.getUploadFile() == null) {
					fileDetailProcess.setUploadFile(uploadFile);
				}
				if(fileDetailProcess.getMessage() == null) {
					fileDetailProcess.setMessage("");
				}
				fileDetailProcessDAO.save(fileDetailProcess);
			}else  {
				log.error("se intentó guardar un FileDetailProcess nulo");
			}
		} catch (Exception dse) {
			log.error(dse);
		}
	}
	
}
