package co.com.directv.sdii.model.vo;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.ShippingOrder;

/**
 * 
 * ShippingOrder Value Object
 * 
 * Fecha de Creación: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ShippingOrder
 */
public class ShippingOrderVO extends ShippingOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -813616196187608584L;
	
	private List<ShippingOrderElementVO> shippingOrderElements;
	
	private List<TechnologyVO> shippingOrderTechnologies;

	public List<ShippingOrderElementVO> getShippingOrderElements() {
		return shippingOrderElements;
	}

	public void setShippingOrderElements(
			List<ShippingOrderElementVO> shippingOrderElements) {
		this.shippingOrderElements = shippingOrderElements;
	}

	public List<TechnologyVO> getShippingOrderTechnologies() {
		return shippingOrderTechnologies;
	}

	public void setShippingOrderTechnologies(
			List<TechnologyVO> shippingOrderTechnologies) {
		this.shippingOrderTechnologies = shippingOrderTechnologies;
	}

}
