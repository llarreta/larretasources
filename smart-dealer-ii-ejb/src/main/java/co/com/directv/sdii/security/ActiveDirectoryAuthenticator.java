package co.com.directv.sdii.security;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.exceptions.BusinessException;


/**
 * 
 * Esta clase controla la autenticación contra el Directorio Activo.
 * 
 * @author Jimmy Vélez Muñoz
 * 
 */
public class ActiveDirectoryAuthenticator {
	private String userDn;
	private String server;
	private String port;
	private String errorMsg;
	private String ldapSuffix;
	private String startDirectory;
	private static Logger log;

	static {
		log = Logger.getLogger(ActiveDirectoryAuthenticator.class);
	}
	public ActiveDirectoryAuthenticator() {
		port = "389";
	}
	
	public ActiveDirectoryAuthenticator(String serverIp, String serverPort) {
		server = serverIp;
		port = serverPort;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public boolean authenticate(String password) {
		errorMsg = "";
        log.debug("== Inicia authenticate/ActiveDirectoryAuthenticator ==");
		try {
			Hashtable<String,String> env = new Hashtable<String,String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, "ldap://" + this.getServer() + ":" + getPort() + "/" + getLdapSuffix());
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			
			if(getStartDirectory() != null && getStartDirectory().trim().length() > 0){
				setUserDn(getStartDirectory() + getUserDn());
			}
			env.put(Context.SECURITY_PRINCIPAL, this.getUserDn());
			env.put(Context.SECURITY_CREDENTIALS, password);
			DirContext ctx = new InitialDirContext(env);
			ctx.close();
			log.debug("Autenticación Exitosa en Directorio Activo");
		} catch (CommunicationException comEx) {
			errorMsg = "CommunicationException: " + comEx.getMessage();
			log.debug("Error CommunicationException " + errorMsg);
			return false;
		} catch (AuthenticationException authEx) {
			errorMsg = "AuthenticationException: " + authEx.getMessage();
			log.debug("Error AuthenticationException " + errorMsg);
			return false;
		} catch (NamingException nameEx) {
			errorMsg = "NamingException: " + nameEx.toString();
			log.debug("Error NamingException " + errorMsg );
			return false;
		} catch (Exception e){
			errorMsg = "Exception: " + e.getMessage();
			log.debug("Error Exception " + errorMsg);
			return false;
		}
                log.debug("== Termina authenticate/ActiveDirectoryAuthenticator ==");
		return true;
	}
	
	/**
	 * 
	 * Metodo: Autenticacion nativa, se pasan todos los parametros
	 * por referencia
	 * @param contextFactory - Ej., com.sun.jndi.ldap.LdapCtxFactory
	 * @param String server - servidor LDAP
	 * @param String port - puerto del servidor LDAP
	 * @param String baseDn - estructura de la organizacion
	 * @param String userBaseDn - si el usuario se debe loguear en una estructura organizacional especifica
	 * @param String authentication - tipo de autenticacion (Ej. simple)
	 * @param String userDn - usuario 
	 * @param String password - password
	 * @param String startDirectory - si requiere que se adicione el sufijo de nombre para la autenticacion, por ej., cn,uid,sn,etc
	 * @return boolean, true efectivo
	 * @throws BusinessException
	 * @author Joan Lopez
	 */
	public boolean authenticate(String contextFactory, String server, String port, String baseDn, String userBaseDn, String authentication, String userDn, String password, String startDirectory ) throws BusinessException{
		
		log.debug("***** ActiveDirectoryAuthenticator inicio [authenticate]");
		boolean isValid = true;
		StringBuffer bufferUserDn = new StringBuffer();		
		StringBuffer providerUrl = new StringBuffer();	
		
		try{
			@SuppressWarnings("unused")
			DirContext ctx = null;	
			Hashtable<String, String> env = new Hashtable<String, String>();
			
			
			
			//Se realizan la validacion para verificar si es necesario anteponer el atributo de nombere
			if(!startDirectory.equalsIgnoreCase("")){
				bufferUserDn.append(startDirectory);
				bufferUserDn.append("=");
			}
			
			bufferUserDn.append( userDn );
			
			//Se realiza la validacion para concatenar el baseDN del usuario
			if( !userBaseDn.equalsIgnoreCase("") ){
				bufferUserDn.append(",");
				bufferUserDn.append(userBaseDn);
			}
			
			providerUrl.append("ldap://");
			providerUrl.append(server);
			providerUrl.append(":");
			providerUrl.append(port);
			providerUrl.append("/");
			providerUrl.append(baseDn);
			
			env.put(Context.INITIAL_CONTEXT_FACTORY,contextFactory);			
			env.put(Context.PROVIDER_URL, providerUrl.toString());
			env.put(Context.SECURITY_AUTHENTICATION, authentication);
			env.put(Context.SECURITY_PRINCIPAL, bufferUserDn.toString());
			env.put(Context.SECURITY_CREDENTIALS, password );
			
			ctx = new InitialDirContext(env);
		} catch (CommunicationException ex) {
			log.error("***** ActiveDirectoryAuthenticator [authenticate] CommunicationException");
			log.error("***** ActiveDirectoryAuthenticator [authenticate] Error Retornado por el LDAP: "+ex.getMessage());
			isValid = false;						
			throw new BusinessException(ErrorBusinessMessages.LDAP_ERROR_AUTHENTICATOR.getCode(),ErrorBusinessMessages.LDAP_ERROR_AUTHENTICATOR.getMessageCode( getMessageException(providerUrl.toString(),bufferUserDn.toString()) ),ex);			
		} catch (AuthenticationException ex) {
			log.error("***** ActiveDirectoryAuthenticator [authenticate] AuthenticationException");
			log.error("***** ActiveDirectoryAuthenticator [authenticate] Error Retornado por el LDAP: "+ex.getMessage());
			isValid = false;
			throw new BusinessException(ErrorBusinessMessages.LDAP_ERROR_AUTHENTICATOR.getCode(),ErrorBusinessMessages.LDAP_ERROR_AUTHENTICATOR.getMessageCode( getMessageException(providerUrl.toString(),bufferUserDn.toString()) ),ex);	
		} catch (NamingException ex) {
			log.error("***** ActiveDirectoryAuthenticator [authenticate] NamingException");
			log.error("***** ActiveDirectoryAuthenticator [authenticate] Error Retornado por el LDAP: "+ex.getMessage());
			isValid = false;
			throw new BusinessException(ErrorBusinessMessages.LDAP_ERROR_AUTHENTICATOR.getCode(),ErrorBusinessMessages.LDAP_ERROR_AUTHENTICATOR.getMessageCode( getMessageException(providerUrl.toString(),bufferUserDn.toString()) ),ex);	
		} catch (Exception ex) {
			log.error("***** ActiveDirectoryAuthenticator [authenticate] Exception");
			throw new BusinessException(ErrorBusinessMessages.LDAP_ERROR_AUTHENTICATOR.getCode(),ErrorBusinessMessages.LDAP_ERROR_AUTHENTICATOR.getMessageCode( getMessageException(providerUrl.toString(),bufferUserDn.toString()) ),ex);	
		}
		log.debug("***** ActiveDirectoryAuthenticator fin [authenticate]");
		return isValid;
	}

	/**
	 * 
	 * Metodo: Retorna un vector de objetos con los parametros
	 * de la exception.
	 * @param p1 String
	 * @param p2 String
	 * @param p3 String
	 * @return Object[]
	 * @author jalopez
	 */
	private Object[] getMessageException(String p1,String p2){
		Object[] params = new Object[2];
		params[0] = p1;
		params[1] = p2;
		return params;
	}
	
	
	public String getUserDn() {
		return userDn;
	}

	public void setUserDn(String user_rdn) {
		this.userDn = user_rdn;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	public String getLdapSuffix() {
		return ldapSuffix;
	}

	public void setLdapSuffix(String ldapSuffix) {
		this.ldapSuffix = ldapSuffix;
	}

	public String getStartDirectory() {
		return startDirectory;
	}

	public void setStartDirectory(String startDirectory) {
		this.startDirectory = startDirectory;
	}
}
