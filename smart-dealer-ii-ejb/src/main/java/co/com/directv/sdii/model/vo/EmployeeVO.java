package co.com.directv.sdii.model.vo;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import co.com.directv.sdii.model.pojo.Employee;

/**
 * 
 * Employee Value Object
 * 
 * Fecha de Creaci�n: Mar 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Employee
 */
public class EmployeeVO extends Employee implements Comparable<EmployeeVO>{

    private static final long serialVersionUID = -6137341663445050289L;
    
    private Long positionId;
    private String positionName;
    private String positionCode;
    private String positionNotifyIbs;
    private Long dealerId;
    private List<EmployeeMediaContactVO> employeesMediaContact;
    private List<TrainingVO> trainingVOs;
    
    private Long dealerCode;
    private String dealerName;
    private String dealerDepotCode;
    private EmployeeRetirementVO lastRetirament;
    private String firstNameLastName;
    
    
    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public List<EmployeeMediaContactVO> getEmployeesMediaContact() {
        return employeesMediaContact;
    }

    public void setEmployeesMediaContact(List<EmployeeMediaContactVO> employeesMediaContact) {
        this.employeesMediaContact = employeesMediaContact;
    }

    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

	public String getPositionNotifyIbs() {
		return positionNotifyIbs;
	}

	public void setPositionNotifyIbs(String positionNotifyIbs) {
		this.positionNotifyIbs = positionNotifyIbs;
	}

	public void setTrainingVOs(List<TrainingVO> trainingVOs) {
		this.trainingVOs = trainingVOs;
	}

	public List<TrainingVO> getTrainingVOs() {
		return trainingVOs;
	}
	
	public Long getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(Long dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getDealerDepotCode() {
		return dealerDepotCode;
	}

	public void setDealerDepotCode(String dealerDepotCode) {
		this.dealerDepotCode = dealerDepotCode;
	}
	
	
	
	

	public EmployeeRetirementVO getLastRetirament() {
		return lastRetirament;
	}

	public void setLastRetirament(EmployeeRetirementVO lastRetirament) {
		this.lastRetirament = lastRetirament;
	}

	public String getFirstNameLastName() {
		return firstNameLastName;
	}

	public void setFirstNameLastName(String firstNameLastName) {
		this.firstNameLastName = firstNameLastName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public int compareTo(EmployeeVO o) {
		if (o == null || o.getDocumentNumber() == null
				|| this.getDocumentNumber() == null) {
			return -1;
		} else {
			int maxLen = this.getDocumentNumber().length() >= o.getDocumentNumber().length() ? this.getDocumentNumber().length() : o.getDocumentNumber().length();
			String docThis = StringUtils.leftPad(this.getDocumentNumber(),maxLen, '0');
			String docOther = StringUtils.leftPad(o.getDocumentNumber(), maxLen, '0');

			if (NumberUtils.isNumber(docThis)&& NumberUtils.isNumber(docOther)) {
				return new Double(docThis).compareTo(new Double(docOther));
			} else if (NumberUtils.isNumber(docThis) && !NumberUtils.isNumber(docOther)) {
				return -1;
			} else if (!NumberUtils.isNumber(docThis) && NumberUtils.isNumber(docOther)) {
				return 1;
			} else {
				return docThis.compareTo(docOther);
			}
		}
	}
}
