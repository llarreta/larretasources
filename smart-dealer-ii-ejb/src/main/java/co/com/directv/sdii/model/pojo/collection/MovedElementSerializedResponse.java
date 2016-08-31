/**
 * 
 */
package co.com.directv.sdii.model.pojo.collection;

import java.util.List;

import co.com.directv.sdii.model.dto.MovedElementSerializedDTO;

/**
 * 
 * Transporta objetos  BuildingElementHistoryVO entre las capas
 * de DAO y business.
 * 
 * Fecha de Creación: 9/02/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class MovedElementSerializedResponse extends CollectionBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5832781211383598728L;
	
	private  List<MovedElementSerializedDTO> movedElementSerialized;

	/**
	 * @return the movedElementSerialized
	 */
	public List<MovedElementSerializedDTO> getMovedElementSerialized() {
		return movedElementSerialized;
	}

	/**
	 * @param movedElementSerialized the movedElementSerialized to set
	 */
	public void setMovedElementSerialized(
			List<MovedElementSerializedDTO> movedElementSerialized) {
		this.movedElementSerialized = movedElementSerialized;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
	
}
