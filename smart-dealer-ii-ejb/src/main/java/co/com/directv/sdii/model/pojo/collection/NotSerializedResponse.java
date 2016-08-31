package co.com.directv.sdii.model.pojo.collection;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.NotSerialized;
import co.com.directv.sdii.model.vo.NotSerializedVO;

/**
 * 
 * Transporta objetos de tipo  NotSerialized
 * 
 * Fecha de Creación: 18/02/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class NotSerializedResponse extends CollectionBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3229524097171266379L;

	private List<NotSerialized> notSerializeds;
	private List<NotSerializedVO> notSerializedsVO;
	
	public List<NotSerialized> getNotSerializeds() {
		return notSerializeds;
	}
	public void setNotSerializeds(List<NotSerialized> notSerializeds) {
		this.notSerializeds = notSerializeds;
	}
	public List<NotSerializedVO> getNotSerializedsVO() {
		return notSerializedsVO;
	}
	public void setNotSerializedsVO(List<NotSerializedVO> notSerializedsVO) {
		this.notSerializedsVO = notSerializedsVO;
	}
}
