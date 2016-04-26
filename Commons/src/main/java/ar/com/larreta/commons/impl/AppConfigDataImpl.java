package ar.com.larreta.commons.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import ar.com.larreta.commons.AppConfigData;
import ar.com.larreta.commons.AppManager;
import ar.com.larreta.commons.AppObject;
import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.commons.exceptions.AppException;
import ar.com.larreta.commons.utils.FormatPatterns;

//@Component(AppConfigDataImpl.APP_CONFIG_DATA)
public class AppConfigDataImpl extends Properties implements AppConfigData{
	
	public static final String APP_CONFIG_DATA = "appConfigData";

	private static final String APPLICATION_PROPERTIES_FILE = "application.properties";
	
	private static String APP_VERSION = "app.version";
	
	private static final String BAR = "/";
	private static final String TWO_POINTS = ":";
	private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
	
	private static final String PROPERTY_NAME_DATABASE_URL_PREFIX = "db.url.prefix";
	private static final String PROPERTY_NAME_DATABASE_URL_DOMAIN = "db.url.domain";
	private static final String PROPERTY_NAME_DATABASE_URL_PORT = "db.url.port";
	private static final String PROPERTY_NAME_DATABASE_URL_SCHEMMA = "db.url.schemma";
	private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";
	private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
	private static final String PROPERTY_NAME_DATABASE_ADMIN_USERNAME = "db.admin.username";
	private static final String PROPERTY_NAME_DATABASE_ADMIN_PASSWORD = "db.admin.password";
	private static final String PROPERTY_NAME_DATABASE_BACKUP = "db.backup";
	private static final String PROPERTY_NAME_DATABASE_INITIALIZE = "db.initialize";
	private static final String PROPERTY_NAME_DATABASE_INITIALIZE_DROPSCHEMMA = "db.initialize.dropschemma";
	private static final String PROPERTY_NAME_DATABASE_INITIALIZE_SCRIPTS = "db.initialize.scripts";
	private static final String PROPERTY_NAME_DATABASE_HOME_DIR = "db.home.directory";
	private static final String PROPERTY_NAME_DATABASE_DUMP_COMMAND = "db.dump.command";
	private static final String PROPERTY_NAME_DATABASE_DUMP_COMMAND_PARAM = "db.dump.command.param";
	private static final String PROPERTY_NAME_DATABASE_DUMP_DIR = "db.dump.directory";
	
	private static final String MAPPING_CLASS_PACKAGE = "hibernate.mapping.class.package";
	
	private static final String PROPERTY_NAME_MAIL_HOST = "mail.host";
	private static final String PROPERTY_NAME_MAIL_PORT = "mail.port";
	private static final String PROPERTY_NAME_MAIL_USERNAME = "mail.username";
	private static final String PROPERTY_NAME_MAIL_PASSWORD = "mail.password";

	 private static final String PROPERTY_MAIL_TEMPLATE_REGISTRATION = "mail.template.registration";
	 private static final String PROPERTY_MAIL_TEMPLATE_CONFIRM = "mail.template.confirm";
	 
	 private static final String PROPERTY_NAME_REPORT_VIRTUALIZER_SIZE = "report.virtualizer.size";
	 private static final String PROPERTY_NAME_REPORT_VIRTUALIZER_DIRECTORY = "report.virtualizer.directory";
	 private static final String PROPERTY_NAME_REPORT_PDF_AUTOPRINT = "report.pdf.autoprint";
	 
	 private static final String PROPERTY_NAME_SESSION_TIMEOUT = "session.timeout";
	 private static final String PROPERTY_NAME_NEW_USER_DEFAULT_PROFILE = "new.user.default.profile";

	 private static final String MESSAGE_REFRESH_TIME = "message.refresh.time";

	 private AppObject appObject = new AppObjectImpl(getClass());
	 
	public AppConfigDataImpl(){
		try {
			java.net.URL url = getClass().getClassLoader().getResource(APPLICATION_PROPERTIES_FILE);
			load(url.openStream());
		} catch(Exception e){
			getLog().error(AppException.getStackTrace(e));
		}
	}
	
	public String getVersion(){
		return getProperty(APP_VERSION);
	}
	
	public Integer getMailPort() {
		return new Integer(getProperty(PROPERTY_NAME_MAIL_PORT));
	}

	public String getMailHost() {
		return getProperty(PROPERTY_NAME_MAIL_HOST);
	}

	public String getMailPassword() {
		return getProperty(PROPERTY_NAME_MAIL_PASSWORD);
	}

	public String getMailUser() {
		return getProperty(PROPERTY_NAME_MAIL_USERNAME);
	}

	
	public String packagesToScan(){
		return getProperty(MAPPING_CLASS_PACKAGE);
	}
	
	public String getGeneralDateFormat() {
		return getProperty(FormatPatterns.GENERAL_DATE_FORMAT);
	}
	
	public String getDatabasePassword() {
		return getProperty(PROPERTY_NAME_DATABASE_PASSWORD);
	}

	public String getDatabaseUsername() {
		return getProperty(PROPERTY_NAME_DATABASE_USERNAME);
	}

	public String getDatabaseAdminPassword() {
		return getProperty(PROPERTY_NAME_DATABASE_ADMIN_PASSWORD);
	}

	public String getDatabaseAdminUsername() {
		return getProperty(PROPERTY_NAME_DATABASE_ADMIN_USERNAME);
	}
	
	public String getDatabaseDriver() {
		return getProperty(PROPERTY_NAME_DATABASE_DRIVER);
	}
	
	public String getDatabaseURL(){
		return getDatabaseURLPrefix() + 
				getDatabaseURLDomain() +
				TWO_POINTS + getDatabaseURLPort() + BAR +
				getDatabaseURLSchemma();
	}

	public String getDatabaseURLSchemma() {
		return getProperty(PROPERTY_NAME_DATABASE_URL_SCHEMMA);
	}

	public String getDatabaseURLPort() {
		return getProperty(PROPERTY_NAME_DATABASE_URL_PORT);
	}

	public String getDatabaseURLDomain() {
		return getProperty(PROPERTY_NAME_DATABASE_URL_DOMAIN);
	}

	public String getDatabaseURLPrefix() {
		return getProperty(PROPERTY_NAME_DATABASE_URL_PREFIX);
	}

	/**
	 * Retorna el tamaño del cache de virtualizacion
	 * @return
	 */
	public Integer getReportVirtualizerSize(){
		return new Integer(getProperty(PROPERTY_NAME_REPORT_VIRTUALIZER_SIZE));
	}
	
	/**
	 * Retorna la ruta del directorio de virtualizacion 
	 * @return
	 */
	public String getReportVirtualizerDirectory(){
		return getProperty(PROPERTY_NAME_REPORT_VIRTUALIZER_DIRECTORY);
	}
	
	public Boolean getReportPDFAutoprint(){
		return new Boolean(getProperty(PROPERTY_NAME_REPORT_PDF_AUTOPRINT));
	}
	
	public Boolean getDatabaseBackup(){
		return new Boolean(getProperty(PROPERTY_NAME_DATABASE_BACKUP));
	}
	
	public Boolean getDatabaseInitialize(){
		return new Boolean(getProperty(PROPERTY_NAME_DATABASE_INITIALIZE));
	}

	public Collection<String> getDatabaseInitializeScripts() {
		Integer index = 0;
		String scripts =  getProperty(PROPERTY_NAME_DATABASE_INITIALIZE_SCRIPTS + "." + index);
		Collection<String> scriptsCol = new ArrayList<String>();
		while(scripts!=null){
			scriptsCol.add(scripts);
			index++;
			scripts =  getProperty(PROPERTY_NAME_DATABASE_INITIALIZE_SCRIPTS + "." + index);
		}

		return scriptsCol;
	}

	public Boolean getDatabaseInitializeDropSchemma(){
		return new Boolean(getProperty(PROPERTY_NAME_DATABASE_INITIALIZE_DROPSCHEMMA));
	}

	public String getDatabaseHomeDirectory(){
		return getProperty(PROPERTY_NAME_DATABASE_HOME_DIR);
	}
	
	public String getDatabaseDumpCommand(){
		return getProperty(PROPERTY_NAME_DATABASE_DUMP_COMMAND);
	}

	public String getDatabaseDumpCommandParam(){
		return getProperty(PROPERTY_NAME_DATABASE_DUMP_COMMAND_PARAM);
	}
	
	public String getDatabaseDumpDirectory(){
		return getProperty(PROPERTY_NAME_DATABASE_DUMP_DIR);
	}

	/**
	 * Retorna el tiempo maximo inactivo para la session
	 * @return
	 */
	public Integer getSessionTimeout(){
		return new Integer(getProperty(PROPERTY_NAME_SESSION_TIMEOUT));
	}

	/**
	 * Retorna el perfil por defecto a asignar con usuarios nuevos
	 * @return
	 */
	public Long getNewUserDefaultProfile(){
		return new Long(getProperty(PROPERTY_NAME_NEW_USER_DEFAULT_PROFILE));
	}

	public String getMailTemplateRegistration(){
		return getProperty(PROPERTY_MAIL_TEMPLATE_REGISTRATION);
	}
	
	public String getMailTemplateConfirm(){
		return getProperty(PROPERTY_MAIL_TEMPLATE_CONFIRM);
	}
	
	public Integer getMessageRefreshTime(){
		return new Integer(getProperty(MESSAGE_REFRESH_TIME));
	}

	public String decryptedPasswordUserMail() {
		return AppManager.getInstance().getBase64().decrypt(getMailPassword());
	}

	public String decryptedMailUser() {
		return AppManager.getInstance().getBase64().decrypt(getMailUser());
	}
	
	public Logger getLog(){
		return appObject.getLog();
	}

	public void setLog(Logger log){
		appObject.setLog(log);
	}
	
	/**
	 * Arranca a contabilizar estadisticas
	 * @param mark
	 * @return
	 */
	public Long statisticsStart(String mark){
		return appObject.statisticsStart(mark);
	}
	
	/**
	 * Finaliza de contabilizar estadisticas
	 * @param id
	 */
	public void statisticsStop(Long id){
		appObject.statisticsStop(id);
	}
}
