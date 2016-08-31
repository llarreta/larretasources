package co.com.directv.sdii.model.pojo;

// default package


import co.com.directv.sdii.rules.BusinessRequired;



/**
 * Dealer entity. @author MyEclipse Persistence Tools
 */

public class Dealer implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6588372986017444731L;

	private Long id;

    @BusinessRequired(isComplexType=true, fieldNameRequired="id")
    private DealerType dealerType;

    @BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private ChannelType channelType;

    @BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private PostalCode postalCode;

    @BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private DealerStatus dealerStatus;

	private Dealer dealer;

    @BusinessRequired
	private String dealerName;

    @BusinessRequired
    private String legalRepresentative;

    @BusinessRequired
	private String nit;

    @BusinessRequired
	private String address;

	private Double score;

    @BusinessRequired
    private String depotCode;

    @BusinessRequired
	private Long dealerCode;
    
    private String isSeller;
    
    private String isInstaller;

	// Constructors

	/** default constructor */
	public Dealer() {
	}
	
	public Dealer(Long id) {
		this.id = id;
	}

	/** minimal constructor */
	public Dealer(DealerType dealerType, ChannelType channelType,
			PostalCode postalCode, DealerStatus dealerStatus,
			String dealerName, String legalRepresentative, String nit,
			String address, String depotCode, Long dealerCode) {
		this.dealerType = dealerType;
		this.channelType = channelType;
		this.postalCode = postalCode;
		this.dealerStatus = dealerStatus;
		this.dealerName = dealerName;
		this.legalRepresentative = legalRepresentative;
		this.nit = nit;
		this.address = address;
		this.depotCode = depotCode;
		this.dealerCode = dealerCode;
	}

        public Dealer(Long id, String dealerName, String legalRepresentative, String nit,
                String address, String depotCode, Long dealerCode) {
		this.id = id;
		this.dealerName = dealerName;
		this.legalRepresentative = legalRepresentative;
		this.nit = nit;
		this.address = address;
		this.depotCode = depotCode;
		this.dealerCode = dealerCode;
	}
        
        public Dealer(Long id, String dealerName, String legalRepresentative, String nit,
                String address, String depotCode, Long dealerCode, PostalCode postalCode ) {
		this.id = id;
		this.dealerName = dealerName;
		this.legalRepresentative = legalRepresentative;
		this.nit = nit;
		this.address = address;
		this.depotCode = depotCode;
		this.dealerCode = dealerCode;
		this.postalCode = postalCode;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DealerType getDealerType() {
		return this.dealerType;
	}

	public void setDealerType(DealerType dealerType) {
		this.dealerType = dealerType;
	}

	public ChannelType getChannelType() {
		return this.channelType;
	}

	public void setChannelType(ChannelType channelType) {
		this.channelType = channelType;
	}

	public PostalCode getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(PostalCode postalCode) {
		this.postalCode = postalCode;
	}

	public DealerStatus getDealerStatus() {
		return this.dealerStatus;
	}

	public void setDealerStatus(DealerStatus dealerStatus) {
		this.dealerStatus = dealerStatus;
	}

	public Dealer getDealer() {
		return this.dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public String getDealerName() {
		return this.dealerName == null ? null : this.dealerName.trim();
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getLegalRepresentative() {
		return this.legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}

	public String getNit() {
		return this.nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getDepotCode() {
		return this.depotCode;
	}

	public void setDepotCode(String depotCode) {
		this.depotCode = depotCode;
	}

	public Long getDealerCode() {
		return this.dealerCode;
	}

	public void setDealerCode(Long dealerCode) {
		this.dealerCode = dealerCode;
	}
	
	public void setIsSeller(String isSeller){
		this.isSeller = isSeller;
	}
	
	public String getIsSeller(){
		return this.isSeller;
	}
	
	public void setIsInstaller(String isInstaller){
		this.isInstaller = isInstaller;
	}
	
	public String getIsInstaller() {
		return this.isInstaller;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dealer other = (Dealer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Dealer [dealerCode=" + dealerCode + ", id=" + id + "]";
	}
	
	
}