package co.com.directv.sdii.model.pojo.collection;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.ElementModel;
import co.com.directv.sdii.model.vo.ElementModelVO;

/**
 * 
 * Transporta ElementModel entre la capa de DAO
 * y de Servicios.
 * 
 * Fecha de Creación: 3/02/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class ElementModelResponse extends CollectionBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7859072254173951326L;

	private List<ElementModel> elementModel;
	private List<ElementModelVO> elementModelVO;

	public List<ElementModel> getElementModel() {
		return elementModel;
	}

	public void setElementModel(List<ElementModel> elementModel) {
		this.elementModel = elementModel;
	}

	public List<ElementModelVO> getElementModelVO() {
		return elementModelVO;
	}

	public void setElementModelVO(List<ElementModelVO> elementModelVO) {
		this.elementModelVO = elementModelVO;
	}	
}
