package ar.com.larreta.school.messages;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.stepper.messages.JSONable;
import ar.com.larreta.tools.Const;

@Component @Scope(Const.PROTOTYPE)
public class ObligationStatusData extends JSONable {

	private Long 			student;
	private Boolean 		paidOff;
	private ObligationData 	obligation;
	public Long getStudent() {
		return student;
	}
	public void setStudent(Long student) {
		this.student = student;
	}
	public Boolean getPaidOff() {
		return paidOff;
	}
	public void setPaidOff(Boolean paidOff) {
		this.paidOff = paidOff;
	}
	public ObligationData getObligation() {
		return obligation;
	}
	public void setObligation(ObligationData obligation) {
		this.obligation = obligation;
	}
	
	
	
	
}
