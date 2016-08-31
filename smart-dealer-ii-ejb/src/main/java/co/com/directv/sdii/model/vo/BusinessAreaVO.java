package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.BusinessArea;

/**
 * Req-0096 - Requerimiento Consolidado Asignador
 * @author ialessan
 *
 */
public class BusinessAreaVO extends BusinessArea implements Serializable {

	private static final long serialVersionUID = -8370769183896109784L;

		public BusinessAreaVO() {
			super();
		}

		public BusinessAreaVO(BusinessArea copy) {
			super(copy);
		}
	

}
