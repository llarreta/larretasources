package co.com.directv.sdii.model.pojo;

// default package
import co.com.directv.sdii.audit.Auditable;
import co.com.directv.sdii.rules.BusinessRequired;

/**
 * Position entity. @author MyEclipse Persistence Tools
 */
public class Position implements java.io.Serializable,Auditable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3425944786972330382L;
	// Fields
    private Long id;
    @BusinessRequired(isComplexType = true, fieldNameRequired = "id")
    private Dealer dealer;
    @BusinessRequired
    private String positionName;
    @BusinessRequired
    private String positionCode;
    @BusinessRequired
    private String description;
    @BusinessRequired
    private Country country;
    
    private String notifyIbs;
    
    // Constructors
    /** default constructor */
    public Position() {
    }

    /** minimal constructor */
    public Position(Dealer dealer, String positionName, String positionCode) {
        this.dealer = dealer;
        this.positionName = positionName;
        this.positionCode = positionCode;
    }

    /** minimal constructor */
    public Position(String positionName, String positionCode, Long id) {
        this.positionName = positionName;
        this.positionCode = positionCode;
        this.id = id;
    }
    
    /** full constructor */
    public Position(String positionName, String positionCode, Long id,Country country) {
        this.positionName = positionName;
        this.positionCode = positionCode;
        this.id = id;
        this.country = country;
    }

    // Property accessors
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Dealer getDealer() {
        return this.dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public String getPositionName() {
        return this.positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionCode() {
        return this.positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
	public String getNotifyIbs() {
		return notifyIbs;
	}

	public void setNotifyIbs(String notifyIbs) {
		this.notifyIbs = notifyIbs;
	}

	@Override
	public String toString() {
		return "Position [id=" + id + ", positionCode=" + positionCode + "]";
	}
}
