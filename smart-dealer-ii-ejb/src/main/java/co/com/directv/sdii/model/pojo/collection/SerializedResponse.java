/**
 * 
 */
package co.com.directv.sdii.model.pojo.collection;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.Serialized;
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
public class SerializedResponse extends	CollectionBase implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7355106339220159328L;
	
	private List<Serialized> serializeds;
	private List<SerializedVO> serializedsVO;
	
	public List<Serialized> getSerializeds() {
		return serializeds;
	}
	public void setSerializeds(List<Serialized> serializeds) {
		this.serializeds = serializeds;
	}
	public List<SerializedVO> getSerializedsVO() {
		return serializedsVO;
	}
	public void setSerializedsVO(List<SerializedVO> serializedsVO) {
		this.serializedsVO = serializedsVO;
	}

	
}
