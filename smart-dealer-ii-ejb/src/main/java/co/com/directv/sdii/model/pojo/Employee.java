package co.com.directv.sdii.model.pojo;

// default package
import java.util.Date;

import co.com.directv.sdii.audit.Auditable;
import co.com.directv.sdii.rules.BusinessRequired;

/**
 * Employee entity. @author MyEclipse Persistence Tools
 */
public class Employee implements java.io.Serializable,Auditable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7518271082612689505L;
	// Fields
    private Long id;
    @BusinessRequired(isComplexType = true, fieldNameRequired = "id")
    private Position position;
    @BusinessRequired(isComplexType = true, fieldNameRequired = "id")
    private DocumentType documentType;
    @BusinessRequired(isComplexType = true, fieldNameRequired = "id")
    private Pension pension;
    @BusinessRequired(isComplexType = true, fieldNameRequired = "id")
    private Eps eps;
    @BusinessRequired(isComplexType = true, fieldNameRequired = "id")
    private City city;
    @BusinessRequired(isComplexType = true, fieldNameRequired = "id")
    private Dealer dealer;
    @BusinessRequired(isComplexType = true, fieldNameRequired = "id")
    private ContactType contactType;
    @BusinessRequired(isComplexType = true, fieldNameRequired = "id")
    private EmployeeStatus employeeStatus;
    @BusinessRequired(isComplexType = true, fieldNameRequired = "id")
    private ContractType contractType;
    @BusinessRequired(isComplexType = true, fieldNameRequired = "id")
    private Arp arp;
    @BusinessRequired
    private String documentNumber;
    @BusinessRequired
    private String firstName;
    @BusinessRequired
    private String lastName;
    @BusinessRequired
    private String address;
    @BusinessRequired
    private Date hireDate;    
    private Double score;    
    @BusinessRequired
    private String isCertified;
    
    private Date birthDate;
    private City birthCity;
    private MaritalStatus maritalStatus;
    private EducationLevel educationLevel;
    
    private Long ibsTechnical;
    private String pin;

    // Constructors
    /** default constructor */
    public Employee() {
    	ibsTechnical=null;
    }
    
    public Employee(Long id) {
    	this.id = id;
    }

    
    
    public Employee(Employee copy) {
		super();
		this.id = copy.id;
		this.position = copy.position;
		this.documentType = copy.documentType;
		this.pension = copy.pension;
		this.eps = copy.eps;
		this.city = copy.city;
		this.dealer = copy.dealer;
		this.contactType = copy.contactType;
		this.employeeStatus = copy.employeeStatus;
		this.contractType = copy.contractType;
		this.arp = copy.arp;
		this.documentNumber = copy.documentNumber;
		this.firstName = copy.firstName;
		this.lastName = copy.lastName;
		this.address = copy.address;
		this.hireDate = copy.hireDate;
		this.score = copy.score;
		this.isCertified = copy.isCertified;
		this.birthDate = copy.birthDate;
		this.birthCity = copy.birthCity;
		this.maritalStatus = copy.maritalStatus;
		this.educationLevel = copy.educationLevel;
		this.ibsTechnical = copy.ibsTechnical;
		this.pin = copy.pin;
	}

	/** full constructor */
    public Employee(Long id, String firstName, String lastName, String address, String documentNumber, DocumentType documentType,
            Position position, Pension pension, Eps eps, City city, EmployeeStatus employeeStatus, ContractType contractType,
            Arp arp, Date hireDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.documentNumber = documentNumber;
        this.documentType = documentType;
        this.position = position;
        this.pension = pension;
        this.eps = eps;
        this.city = city;
        this.employeeStatus = employeeStatus;
        this.contractType = contractType;
        this.arp = arp;
        this.hireDate = hireDate;
    }
    /** minimal constructor */
    public Employee(Position position, Pension pension, Eps eps, City city,
            Dealer dealer, ContactType contactType,
            EmployeeStatus employeeStatus, ContractType contractType, Arp arp,
            String firstName, String lastName, String address, Date hireDate) {
        this.position = position;
        this.pension = pension;
        this.eps = eps;
        this.city = city;
        this.dealer = dealer;
        this.contactType = contactType;
        this.employeeStatus = employeeStatus;
        this.contractType = contractType;
        this.arp = arp;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.hireDate = hireDate;
    }   
    /** minimal constructor */
    public Employee(Long id, String firstName, String lastName, String address, String documentNumber, DocumentType documentType,
            Position position, Pension pension, Eps eps, City city, EmployeeStatus employeeStatus, ContractType contractType,
            Arp arp, Date hireDate,String isCertified) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.documentNumber = documentNumber;
        this.documentType = documentType;
        this.position = position;
        this.pension = pension;
        this.eps = eps;
        this.city = city;
        this.employeeStatus = employeeStatus;
        this.contractType = contractType;
        this.arp = arp;
        this.hireDate = hireDate;
        this.isCertified = isCertified;
    }
    /** minimal constructor */
    public Employee(Long id, String firstName, String lastName, String address, String documentNumber, Date hireDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.documentNumber = documentNumber;
        this.hireDate = hireDate;
    }
    
    public Employee(Long id, String documentNumber){
    	this.id = id;
    	this.documentNumber = documentNumber;
    }

    // Property accessors
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public DocumentType getDocumentType() {
        return this.documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public Pension getPension() {
        return this.pension;
    }

    public void setPension(Pension pension) {
        this.pension = pension;
    }

    public Eps getEps() {
        return this.eps;
    }

    public void setEps(Eps eps) {
        this.eps = eps;
    }

    public City getCity() {
        return this.city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Dealer getDealer() {
        return this.dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public ContactType getContactType() {
        return this.contactType;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    public EmployeeStatus getEmployeeStatus() {
        return this.employeeStatus;
    }

    public void setEmployeeStatus(EmployeeStatus employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public ContractType getContractType() {
        return this.contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public Arp getArp() {
        return this.arp;
    }

    public void setArp(Arp arp) {
        this.arp = arp;
    }

    public String getDocumentNumber() {
        return this.documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getHireDate() {
        return this.hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Double getScore() {
        return this.score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

	public String getIsCertified() {
		return isCertified;
	}

	public void setIsCertified(String isCertified) {
		this.isCertified = isCertified;
	}
	
	

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public City getBirthCity() {
		return birthCity;
	}

	public void setBirthCity(City birthCity) {
		this.birthCity = birthCity;
	}

	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public EducationLevel getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(EducationLevel educationLevel) {
		this.educationLevel = educationLevel;
	}

	public Long getIbsTechnical() {
		return ibsTechnical;
	}

	public void setIbsTechnical(Long ibsTechnical) {
		this.ibsTechnical = ibsTechnical;
	}
	
	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + "]";
	}



	
	
}
