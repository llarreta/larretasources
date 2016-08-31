/**
 * Creado 3/12/2010 18:11:33
 */
package co.com.directv.sdii.model.pojo;

import java.io.Serializable;

/**
 * Representa los posibles estados en los que puede quedar una Work order
 * que trata de ser atendida
 * 
 * Fecha de Creación: 3/12/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class AttentionStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8488502449184461390L;

	private Long id;
	
	private String code;
	
	private String name;

	
	
	public AttentionStatus() {
		super();
	}

	public AttentionStatus(Long id, String code, String name) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "AttentionStatus [code=" + code + ", id=" + id + "]";
	}
	
	
	
}
