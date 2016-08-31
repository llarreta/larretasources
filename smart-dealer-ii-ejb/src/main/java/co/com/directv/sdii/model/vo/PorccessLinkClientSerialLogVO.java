package co.com.directv.sdii.model.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import co.com.directv.sdii.model.pojo.PorccessLinkClientSerialLog;
import co.com.directv.sdii.model.pojo.WorkOrder;

public class PorccessLinkClientSerialLogVO extends PorccessLinkClientSerialLog
		implements Serializable {

	public PorccessLinkClientSerialLogVO(PorccessLinkClientSerialLog copy) {
		super(copy);
	}
	

	public PorccessLinkClientSerialLogVO(Long id, WorkOrder workOrder,
			String principalSerial, String linkedSerial, String logMessage,
			Timestamp dateLog) {
		super(id, workOrder, principalSerial, linkedSerial, logMessage, dateLog);
	}


	public PorccessLinkClientSerialLogVO() {
		super();
	}
}
