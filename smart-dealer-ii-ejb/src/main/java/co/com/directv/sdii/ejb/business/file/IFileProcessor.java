package co.com.directv.sdii.ejb.business.file;

import java.util.List;

import co.com.directv.sdii.ejb.business.file.DTO.FileRecordDTO;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.FileDetailProcess;
import co.com.directv.sdii.model.vo.LoadFileVO;

public interface IFileProcessor {

	/**
	 * Método: Permite poblar la lista 'fileData' con los datos del archivo
	 * @param loadFileVO
	 * @throws BusinessException
	 * @author gfandino
	 */
	public void readFile(LoadFileVO loadFileVO) throws BusinessException;

	/**
	 * Metodo encargado de eliminar un archivo de disco
	 * @param pathFile ruta del archivo que se eliminara
	 * @throws BusinessException
	 */
	public void deleteFile(String pathFile) throws BusinessException;
	
	/**
	 * Método: Realiza el procesamiento de los datos del archivo contenidos en
	 * 'fileData'
	 * @throws BusinessException
	 * @author gfandino
	 */
	public void processFile(LoadFileVO loadFileVO) throws BusinessException;

	public List<FileRecordDTO> getFileData();

	public boolean validateFile()throws BusinessException;

	public void saveFileDetailProcess(long row, String message);

	public void saveFileDetailProcess(FileDetailProcess fileDetailProcess);

}