package co.com.directv.sdii.ejb.business.file;

import java.util.List;

import co.com.directv.sdii.ejb.business.file.DTO.FileRecordDTO;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.FileDetailProcess;

public interface IBasicFileProcessor extends IFileProcessor {

	/**
	 * 
	 * Metodo: procesa un registro de datos, teniendo en cuenta que el arreglo
	 * que se obtiene mediante <code>fileRecordDTO.getRowData()</code> tiene el mismo
	 * tamaño que el número de columnas definidas por el tamaño del arreglo <code>columnTitles</code>
	 * @param fileRecordDTO
	 * @throws BusinessException
	 * @author wjimenez
	 */
	public abstract void processRecord(FileRecordDTO fileRecordDTO) throws BusinessException;

	public abstract List<FileDetailProcess> doGlobalValidationsAndDropNotValidRecords(List<FileRecordDTO> fileData);
	
	public abstract List<FileDetailProcess> doPostProcess();
	
	public abstract String[] getColumnTitles();
	
}