package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.Program;

/**
 * 
 * Program Value Object
 * 
 * Fecha de Creación: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Program
 */
public class ProgramVO extends Program implements Serializable {

	private static final long serialVersionUID = -526914556280188435L;

	private Long loadWo;

	public void setLoadWo(Long loadWo) {
		this.loadWo = loadWo;
	}

	public Long getLoadWo() {
		return loadWo;
	}
	
	
}
