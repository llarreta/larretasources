/**
 * 
 */
package co.com.directv.sdii.model.pojo.collection;

import java.util.List;

import co.com.directv.sdii.model.pojo.ImportLog;
import co.com.directv.sdii.model.vo.ImportLogVO;
import co.com.directv.sdii.reports.dto.ImportLogDTO;

/**
 * 
 * Transporta ImportLog entre la capa de DAO
 * y de Servicios.
 * 
 * Fecha de Creación: 8/02/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class ImportLogResponse extends CollectionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 939086109869143427L;
	
	private List<ImportLog> importLog;
	private List<ImportLogVO> importLogVO;
	private List<ImportLogDTO> importLogDTO;
	
	public List<ImportLog> getImportLog() {
		return importLog;
	}
	public void setImportLog(List<ImportLog> importLog) {
		this.importLog = importLog;
	}
	public List<ImportLogVO> getImportLogVO() {
		return importLogVO;
	}
	public void setImportLogVO(List<ImportLogVO> importLogVO) {
		this.importLogVO = importLogVO;
	}
	public void setImportLogDTO(List<ImportLogDTO> importLogDTO) {
		this.importLogDTO = importLogDTO;
	}
	public List<ImportLogDTO> getImportLogDTO() {
		return importLogDTO;
	}
}
