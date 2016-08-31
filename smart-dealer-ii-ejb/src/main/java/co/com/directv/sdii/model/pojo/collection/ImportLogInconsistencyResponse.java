package co.com.directv.sdii.model.pojo.collection;

import java.util.List;

import co.com.directv.sdii.model.pojo.ImportLog;
import co.com.directv.sdii.model.pojo.ImportLogInconsistency;
import co.com.directv.sdii.model.vo.ImportLogInconsistencyVO;
import co.com.directv.sdii.model.vo.ImportLogVO;
import co.com.directv.sdii.reports.dto.ImportLogDTO;
import co.com.directv.sdii.reports.dto.ImportLogInconsistencyDTO;

/**
 * 
 * Transporta ImportLog entre la capa de DAO
 * y de Servicios.
 * 
 * Fecha de Creación: 28/09/2011
 * @author jalopez <a href="mailto:hcorredorp@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class ImportLogInconsistencyResponse extends CollectionBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2876975504406692090L;
	
	private List<ImportLogInconsistency> importLogInconsistency;
	private List<ImportLogInconsistencyVO> importLogInconsistencyVO;
	private List<ImportLogInconsistencyDTO> importLogInconsistencyDTO;
	
	public List<ImportLogInconsistency> getImportLogInconsistency() {
		return importLogInconsistency;
	}
	public void setImportLogInconsistency(
			List<ImportLogInconsistency> importLogInconsistency) {
		this.importLogInconsistency = importLogInconsistency;
	}
	public List<ImportLogInconsistencyVO> getImportLogInconsistencyVO() {
		return importLogInconsistencyVO;
	}
	public void setImportLogInconsistencyVO(
			List<ImportLogInconsistencyVO> importLogInconsistencyVO) {
		this.importLogInconsistencyVO = importLogInconsistencyVO;
	}
	public List<ImportLogInconsistencyDTO> getImportLogInconsistencyDTO() {
		return importLogInconsistencyDTO;
	}
	public void setImportLogInconsistencyDTO(
			List<ImportLogInconsistencyDTO> importLogInconsistencyDTO) {
		this.importLogInconsistencyDTO = importLogInconsistencyDTO;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
