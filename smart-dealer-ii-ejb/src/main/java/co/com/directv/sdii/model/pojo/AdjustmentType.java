package co.com.directv.sdii.model.pojo;



/**
 * AdjustmentType entity. @author MyEclipse Persistence Tools
 */

public class AdjustmentType  implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = -5584854315246888005L;
	private Long id;
     private String code;
     private String name;

    // Constructors

    /** default constructor */
    public AdjustmentType() {
    }

	/**
	 * 
	 * @param id
	 * @param code
	 * @param name
	 */
    public AdjustmentType(Long id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }
    
   
    // Property accessors

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}