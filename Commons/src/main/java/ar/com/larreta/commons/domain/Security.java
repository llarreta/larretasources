package ar.com.larreta.commons.domain;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "security")
@Where(clause="deleted IS NULL")
@SQLDelete (sql="UPDATE Security SET deleted=CURRENT_TIMESTAMP WHERE id=?")
public class Security extends ar.com.larreta.commons.domain.Entity {

	private String loginPage;
	private String loginProcessingUrl;
	private String defaultSuccessUrl;
	private String failureUrl;
	private String usernameParameter;
	private String passwordParameter;
	private String logoutUrl;
	private String logoutSuccessUrl;
	private String deleteCookies;
	private Set<SecurityMatcher> securityMatchers;
	private Boolean avaiable = Boolean.TRUE;
	
	@Basic
	public Boolean getAvaiable() {
		return avaiable;
	}
	public void setAvaiable(Boolean avaiable) {
		this.avaiable = avaiable;
	}
	
	@Basic
	public String getLoginPage() {
		return loginPage;
	}
	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}
	
	@Basic
	public String getLoginProcessingUrl() {
		return loginProcessingUrl;
	}
	public void setLoginProcessingUrl(String loginProcessingUrl) {
		this.loginProcessingUrl = loginProcessingUrl;
	}
	
	@Basic
	public String getDefaultSuccessUrl() {
		return defaultSuccessUrl;
	}
	public void setDefaultSuccessUrl(String defaultSuccessUrl) {
		this.defaultSuccessUrl = defaultSuccessUrl;
	}
	
	@Basic
	public String getFailureUrl() {
		return failureUrl;
	}
	public void setFailureUrl(String failureUrl) {
		this.failureUrl = failureUrl;
	}
	
	@Basic
	public String getUsernameParameter() {
		return usernameParameter;
	}
	public void setUsernameParameter(String usernameParameter) {
		this.usernameParameter = usernameParameter;
	}
	
	@Basic
	public String getPasswordParameter() {
		return passwordParameter;
	}
	public void setPasswordParameter(String passwordParameter) {
		this.passwordParameter = passwordParameter;
	}
	
	@Basic
	public String getLogoutUrl() {
		return logoutUrl;
	}
	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}
	
	@Basic
	public String getLogoutSuccessUrl() {
		return logoutSuccessUrl;
	}
	public void setLogoutSuccessUrl(String logoutSuccessUrl) {
		this.logoutSuccessUrl = logoutSuccessUrl;
	}
	
	@Basic
	public String getDeleteCookies() {
		return deleteCookies;
	}
	public void setDeleteCookies(String deleteCookies) {
		this.deleteCookies = deleteCookies;
	}
	
	@OneToMany (mappedBy="security", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=SecurityMatcher.class)
	@Where(clause="deleted IS NULL")
	public Set<SecurityMatcher> getSecurityMatchers() {
		return securityMatchers;
	}
	public void setSecurityMatchers(Set<SecurityMatcher> securityMatchers) {
		this.securityMatchers = securityMatchers;
	}
	
	
	
	
}
