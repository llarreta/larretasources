package ar.com.larreta.commons.domain;

import javax.persistence.Basic;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Entity
@Table(name = "securityMatcher")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="matcherType")
@Where(clause="deleted IS NULL")
@SQLDelete (sql="UPDATE SecurityMatcher SET deleted=CURRENT_TIMESTAMP WHERE id=?")
public abstract class SecurityMatcher extends ar.com.larreta.commons.domain.Entity{

	private String pattern;
	private Security security;
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Security.class)
	@JoinColumn (name="idSecurity")
	public Security getSecurity() {
		return security;
	}

	public void setSecurity(Security security) {
		this.security = security;
	}

	@Basic
	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
	@Transient
	public abstract void process(HttpSecurity http) throws Exception;
	
}
