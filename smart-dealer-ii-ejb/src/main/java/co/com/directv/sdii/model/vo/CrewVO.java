package co.com.directv.sdii.model.vo;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.Crew;
import co.com.directv.sdii.model.pojo.CrewType;
import co.com.directv.sdii.model.pojo.Dealer;

/**
 * 
 * Crews Value Object   
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Vehicles
 */
public class CrewVO extends Crew implements Serializable {

    private static final long serialVersionUID = -7934963983356583057L;
    private List<EmployeeCrewVO> employeesCrew;
    private String plateVehicle;
    private Long dealerCode;
    private String depotDealerCode;
    private String crewTypeName;
    private EmployeeVO responsable;
    
    private Long vehicleId;
    private Long dealerId;
    private String dealerName;
    private Long crewTypeId;
    private Long statusId;
    private Long amountWoAssigment;
    
    private Long loadWo;

    
    
    public CrewVO() {
		super();
	}

    public CrewVO(Long id) {
		super();
		setId(id);
	}
    
	public List<EmployeeCrewVO> getEmployeesCrew() {
        return employeesCrew;
    }

    public void setEmployeesCrew(List<EmployeeCrewVO> employeesCrew) {
        this.employeesCrew = employeesCrew;
        for (EmployeeCrewVO employeeCrewVO : employeesCrew) {
			employeeCrewVO.setCrew(null);
		}
    }

    public String getPlateVehicle() {
        return plateVehicle;
    }

    public void setPlateVehicle(String plate) {
        this.plateVehicle = plate;
    }

    public Long getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(Long dealerCode) {
        this.dealerCode = dealerCode;
    }

    public String getDepotDealerCode() {
        return depotDealerCode;
    }

    public void setDepotDealerCode(String depotDealerCode) {
        this.depotDealerCode = depotDealerCode;
    }

    public String getCrewTypeName() {
        return crewTypeName;
    }

    public void setCrewTypeName(String crewTypeName) {
        this.crewTypeName = crewTypeName;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    public Long getCrewTypeId() {
        return crewTypeId;
    }

    public void setCrewTypeId(Long crewTypeId) {
        this.crewTypeId = crewTypeId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getAmountWoAssigment() {
		return amountWoAssigment;
	}

	public void setAmountWoAssigment(Long amountWoAssigment) {
		this.amountWoAssigment = amountWoAssigment;
	}

	public void setLoadWo(Long loadWo) {
		this.loadWo = loadWo;
	}

	public Long getLoadWo() {
		return loadWo;
	}
	
	

	public EmployeeVO getResponsable() {
		return responsable;
	}

	public void setResponsable(EmployeeVO responsable) {
		this.responsable = responsable;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	/**
     * Asigna los valores correspondientes a las tablas relacionadas
     */
    public void populateBean() {

        CrewType cType = super.getCrewType();
        if (cType != null) {
            this.crewTypeId = cType.getId();
            this.crewTypeName = cType.getCrewTypeName();
            cType = null;
        }

        Dealer dealer = super.getDealer();
        if(dealer != null){
            this.dealerId = dealer.getId();
            this.dealerCode = dealer.getDealerCode();
            this.depotDealerCode = dealer.getDepotCode();
            this.dealerName = dealer.getDealerName();
            dealer = null;
        }
        
        this.statusId = super.getCrewStatus() == null ? null : super.getCrewStatus().getId();
        this.vehicleId = super.getVehicle() == null ? null : super.getVehicle().getId();
        this.plateVehicle = super.getVehicle() == null ? null: super.getVehicle().getPlate();
        super.setVehicle(null);
        super.setDealer(null);
        
        
    }
    
    public void generateFullName() {
		if(this.responsable!=null){
			String nameFull = this.responsable.getFirstName()==null?"":this.responsable.getFirstName();
			nameFull += this.responsable.getLastName()==null?"":" "+this.responsable.getLastName();
			this.responsable.setFirstNameLastName(nameFull);
		}
	}
}
