package co.com.directv.sdii.model.dto;

import java.io.Serializable;

import co.com.directv.sdii.model.vo.NotSerializedVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.model.vo.ShippingOrderElementVO;
import co.com.directv.sdii.model.vo.ShippingOrderVO;
import co.com.directv.sdii.model.vo.WarehouseElementVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;



/**
 * 
 * BuildingElementHistoryVO Value Object
 * Clase utilitaria para enviar la informacion a la presentacion de la consulta del historial de
 * elementos en un edificio
 * 
 * Fecha de Creacion: Agosto 24, 2010
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * 
 */
public class BuildingElementHistoryVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5683588806148951141L;
		
	private WorkOrderVO workorder;
	private ShippingOrderVO shippingOrder;
	private ShippingOrderElementVO shippingOrderElementVO;
	private SerializedVO serialized;
	private NotSerializedVO notSerialized;
	private WarehouseElementVO wareHouseElement;
	
	public WorkOrderVO getWorkorder() {
		return workorder;
	}
	public void setWorkorder(WorkOrderVO workorder) {
		this.workorder = workorder;
	}
	public ShippingOrderVO getShippingOrder() {
		return shippingOrder;
	}
	public void setShippingOrder(ShippingOrderVO shippingOrder) {
		this.shippingOrder = shippingOrder;
	}
	public ShippingOrderElementVO getShippingOrderElementVO() {
		return shippingOrderElementVO;
	}
	public void setShippingOrderElementVO(
			ShippingOrderElementVO shippingOrderElementVO) {
		this.shippingOrderElementVO = shippingOrderElementVO;
	}
	public SerializedVO getSerialized() {
		return serialized;
	}
	public void setSerialized(SerializedVO serialized) {
		this.serialized = serialized;
	}
	public NotSerializedVO getNotSerialized() {
		return notSerialized;
	}
	public void setNotSerialized(NotSerializedVO notSerialized) {
		this.notSerialized = notSerialized;
	}
	public WarehouseElementVO getWareHouseElement() {
		return wareHouseElement;
	}
	public void setWareHouseElement(WarehouseElementVO wareHouseElement) {
		this.wareHouseElement = wareHouseElement;
	}
	
	public String toXLSString(){
		StringBuffer buff = new StringBuffer();
		buff.append(getWorkorder().getCustomer().getCustomerCode());
		buff.append(getWorkorder().getPostalCode().getCity().getCityName());
		buff.append(getWorkorder().getWoAddress());
		return buff.toString();
	}
	
	public String toXLSStringSerialized(){
		StringBuffer buff = new StringBuffer();
		buff.append(getSerialized().getElement().getId());
		buff.append(getWorkorder().getCustomer().getCustomerCode());
		buff.append(getWorkorder().getPostalCode().getCity().getCityName());
		buff.append(getWorkorder().getWoAddress());
		buff.append(getSerialized().getElement().getElementType().getTypeElementName());
		return buff.toString();
	}
	
	public String toXLSStringNotSerialized(){
		StringBuffer buff = new StringBuffer();
		buff.append(getSerialized().getElement().getId());
		buff.append(getWorkorder().getCustomer().getCustomerCode());
		buff.append(getWorkorder().getPostalCode().getCity().getCityName());
		buff.append(getWorkorder().getWoAddress());
		buff.append(getSerialized().getElement().getElementType().getTypeElementName());
		return buff.toString();
	}
}
