package co.com.directv.sdii.model.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import co.com.directv.sdii.model.pojo.WoAssignment;

/**
 * 
 * WoAssignment Value Object
 * 
 * Fecha de Creaci�n: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.WoAssignment
 */
public class WoAssignmentVO extends WoAssignment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3198671477440214029L;

	public String toXLSString(){
		StringBuffer buff = new StringBuffer();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		if (getWorkOrder() != null) {
			buff.append(getWorkOrder().getWoCode() == null ? "" : getWorkOrder().getWoCode());
			buff.append("|");
			buff.append(getWorkOrder().getWorkorderStatusByActualStatusId() == null ? "" : getWorkOrder().getWorkorderStatusByActualStatusId().getWoStateCode());
			buff.append("|");
			buff.append(getWorkOrder().getWoProgrammingDate() == null ? "" : df.format(getWorkOrder().getWoProgrammingDate()));
			buff.append("|");
			buff.append(getWorkOrder().getCustomer() == null ? "" : getWorkOrder().getCustomer().getCustomerCode());
			buff.append("|");
			buff.append(getWorkOrder().getCustomer() == null ? "" : getWorkOrder().getCustomer().getFirstName());
			buff.append("|");
			buff.append(getWorkOrder().getCustomer() == null ? "" : getWorkOrder().getCustomer().getLastName());
			buff.append("|");
			buff.append(getWorkOrder().getWoAddress() == null ? "" : getWorkOrder().getWoAddress());
			buff.append("|");
			buff.append(getCrewId() == null ? "" : getCrewId());
		}
		return buff.toString();
	}
}
