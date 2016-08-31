/**
 * 
 */
package co.com.directv.sdii.model.pojo.collection;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.RefInconsistency;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.vo.RefInconsistencyVO;
import co.com.directv.sdii.model.vo.SerializedVO;

/**
 * 
 * Transporta objetos Serialized entre las capas DAO, Business y Services. 
 * 
 * Fecha de Creación: 10/02/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class RefInconsistencyResponse extends	CollectionBase implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7355106339220159328L;
	
	private List<RefInconsistency> refInconsistencys;
	
	private List<RefInconsistencyVO> refInconsistencysVO;
	
	public List<RefInconsistency> getRefInconsistencys() {
		return refInconsistencys;
	}
	
	public void setRefInconsistencys(List<RefInconsistency> refInconsistencys) {
		this.refInconsistencys = refInconsistencys;
	}
	
	public List<RefInconsistencyVO> getRefInconsistencysVO() {
		return refInconsistencysVO;
	}
	
	public void setRefInconsistencysVO(List<RefInconsistencyVO> refInconsistencysVO) {
		this.refInconsistencysVO = refInconsistencysVO;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
