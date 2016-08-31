package co.com.directv.sdii.model.pojo;

/**
 * 
 * Clase que mapea la tabla que almacena los procesos de movimiento de elementos 
 * 
 * Fecha de Creación: Nov 4, 2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class MovElementProcess implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9182625601578377007L;
	
	private Long id;
	private String movElementProcessName;
	private String movElementProcessCode;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMovElementProcessName() {
		return movElementProcessName;
	}
	public void setMovElementProcessName(String movElementProcessName) {
		this.movElementProcessName = movElementProcessName;
	}
	public String getMovElementProcessCode() {
		return movElementProcessCode;
	}
	public void setMovElementProcessCode(String movElementProcessCode) {
		this.movElementProcessCode = movElementProcessCode;
	}
	
	

}
