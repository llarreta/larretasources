package ar.com.larreta.mystic.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.tools.Const;

@Entity @Component @Scope(Const.PROTOTYPE)
@Table(name = "email")
public class Email extends ar.com.larreta.mystic.model.Entity {

	private String 		Address;
	
	@Basic @Column (name="address")
	public String getaddress() {
		return Address;
	}
	public void setaddress(String address) {
		this.Address = address;
	}
	
}
