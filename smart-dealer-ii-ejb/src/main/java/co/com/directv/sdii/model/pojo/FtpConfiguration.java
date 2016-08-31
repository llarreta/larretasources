package co.com.directv.sdii.model.pojo;



/**
 * Adjustment entity. @author aharker
 */

public class FtpConfiguration  implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String ftpConfigurationCode;
	private String host;
	private String ftpConfigurationUser;
	private String password;
	private String path;
	private Country country;
	private Long port;
	private String isSecurity;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPort() {
		return port;
	}
	
	public void setPort(Long port) {
		this.port = port;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFtpConfigurationCode() {
		return ftpConfigurationCode;
	}
	public void setFtpConfigurationCode(String ftpConfigurationCode) {
		this.ftpConfigurationCode = ftpConfigurationCode;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getFtpConfigurationUser() {
		return ftpConfigurationUser;
	}
	public void setFtpConfigurationUser(String ftpConfigurationUser) {
		this.ftpConfigurationUser = ftpConfigurationUser;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getIsSecurity() {
		return isSecurity;
	}

	public void setIsSecurity(String isSecurity) {
		this.isSecurity = isSecurity;
	}
	
}