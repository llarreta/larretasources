package ar.com.larreta.commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.Interceptor;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ar.com.larreta.commons.exceptions.AppException;
import ar.com.larreta.commons.impl.AppConfigDataImpl;
import ar.com.larreta.commons.initializer.LockApp;
import ar.com.larreta.commons.persistence.JDBCConnection;
import ar.com.larreta.commons.persistence.impl.CommonsSessionFactoryImpl;
import ar.com.larreta.commons.utils.Base64;
import ar.com.larreta.commons.utils.FormatPatterns;
import ar.com.larreta.commons.utils.Zipper;
import ar.com.larreta.commons.utils.impl.Base64Impl;

@Configuration
@EnableTransactionManagement
public class AppConfig extends AppObjectImpl{
	
	private static final String DB_INITIALIZE_AUTOCOMMIT = "db.initialize.autocommit";
	private static final String DB_INITIALIZE_STOPONERROR = "db.initialize.stoponerror";

	private static final String ZIP = ".zip";
	private static final String EXPORT = "export_";
	

	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
	private static final String PROPERTY_NAME_HIBERNATE_CONNECTION_POOL_SIZE = "hibernate.connection.poolSize";
	private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";

	@Autowired
	private AuditInterceptor auditInterceptor;

	private AppConfigData appConfigData;
	
	private Base64 base64;
	
	private LockApp lockApp; 
    
    public LockApp getLockApp() {
		return lockApp;
	}

	public void setLockApp(LockApp lockApp) {
		this.lockApp = lockApp;
	}

	public Base64 getBase64() {
    	if(base64==null){
    		base64=new Base64Impl();
    	}
		return base64;
	}

	public void setBase64(Base64 base64) {
		this.base64 = base64;
	}

	@Bean
	public AppConfigData getAppConfigData() {
		if (appConfigData==null){
			appConfigData = new AppConfigDataImpl();
		}
		return appConfigData;
	}

	public void setAppConfigData(AppConfigData appConfigData) {
		this.appConfigData = appConfigData;
	}
    
	public String getMailPassword() {
		return getBase64().decrypt(getAppConfigData().getMailPassword());
	}

	public String getMailUser() {
		return getBase64().decrypt(getAppConfigData().getMailUser());
	}
		
	public String getDatabasePassword() {
		return getBase64().decrypt(getAppConfigData().getDatabasePassword());
	}

	public String getDatabaseUsername() {
		return getBase64().decrypt(getAppConfigData().getDatabaseUsername());
	}

	public String getDatabaseAdminPassword() {
		return getBase64().decrypt(getAppConfigData().getDatabaseAdminPassword());
	}

	public String getDatabaseAdminUsername() {
		return getBase64().decrypt(getAppConfigData().getDatabaseAdminUsername());
	}
    
	@Bean
	public String packagesToScan(){
		return getAppConfigData().packagesToScan();
	}

	@Bean
	public FormatPatterns formatPatterns(){
		return new FormatPatterns(getAppConfigData());
	}

	@Bean
	public DataSource dataSource() throws ClassNotFoundException, SQLException, IOException {
		
		//initializeSchemma();
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(getAppConfigData().getDatabaseDriver());
		dataSource.setUrl(getAppConfigData().getDatabaseURL());
		dataSource.setUsername(getDatabaseUsername());
		dataSource.setPassword(getDatabasePassword());
		
		return dataSource;
	}

	public synchronized void initializeSchemma(JDBCConnection connection) throws ClassNotFoundException, SQLException, IOException {
		if (AppState.getInstance().isInitializing()){
			if (getAppConfigData()!=null){
				AppState.getInstance().advanceLevel();
				
				//JDBCConnection connection = jdbcConnection;
				if (getAppConfigData().getDatabaseBackup()){
					backup(connection);
				}
				
				if (getAppConfigData().getDatabaseInitialize()){
					File dumpDir = new File(getAppConfigData().getDatabaseDumpDirectory());
					if (!dumpDir.exists()||!dumpDir.isDirectory()){
						dumpDir.mkdirs();
					}
					
					if (getAppConfigData().getDatabaseInitializeDropSchemma()){
						//Borramos el esquema anterior
						connection.dropSchema();
						//Generamos nuevamente el esquema
						connection.createSchema();
					}
					
					//Creamos las tablas necesarias
					Collection<String> scripts = getAppConfigData().getDatabaseInitializeScripts();
					Iterator<String> itScripts = scripts.iterator();
					while (itScripts.hasNext()) {
						String actualScript = (String) itScripts.next();
						InputStream inputStream = getInputStream(actualScript);
						InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
						connection.runScript(inputStreamReader);	
					}
				}
			} else {
				getLog().info("PRECAUCION!!! no se inicializo el esquema debido a que appConfigData es nulo.");
			}
		}
	}

	private InputStream getInputStream(String actualScript) throws FileNotFoundException {
		InputStream inputStream = null;
		try {
			inputStream = getClass().getResourceAsStream(AppConfigDataImpl.SEPARATOR + actualScript);
			if (inputStream==null){
				inputStream = new FileInputStream(new File(actualScript));
			}
		} catch (Exception e){
			inputStream = new FileInputStream(new File(actualScript));
		}
		return inputStream;
	}

	public void backup(JDBCConnection connection) throws IOException {
		//Generamos el backup de lo que exista hasta este momento
		Zipper zipper = new Zipper();
		File sqlFile = connection.getDump();
		zipper.zipSingleFile(sqlFile, getAppConfigData().getDatabaseDumpDirectory() + File.separator + EXPORT + System.currentTimeMillis() + ZIP);
		sqlFile.delete();
	}


	@Bean
	public Properties hibernateProperties(){
		Properties properties = new Properties();
		properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, getAppConfigData().getProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
		properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, getAppConfigData().getProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
		properties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, getAppConfigData().getProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL));
		properties.put(PROPERTY_NAME_HIBERNATE_CONNECTION_POOL_SIZE, getAppConfigData().getProperty(PROPERTY_NAME_HIBERNATE_CONNECTION_POOL_SIZE));
		properties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, getAppConfigData().getProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO));
		return properties;
	}
	
	
	 @Bean
	 @Autowired
	 public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		 HibernateTransactionManager txManager = new HibernateTransactionManager();
		 txManager.setSessionFactory(sessionFactory);
		 txManager.setEntityInterceptor((Interceptor) auditInterceptor);
 
		 return txManager;
	 }
	
	public Boolean getStopOnError(){
		try{
			return new Boolean((String) getAppConfigData().get(DB_INITIALIZE_STOPONERROR));
		} catch (Exception e){
			getLog().error(AppException.getStackTrace(e));
		}
		return Boolean.TRUE;
	}
	
	public Boolean getAutocommit(){
		try{
			return new Boolean((String) getAppConfigData().get(DB_INITIALIZE_AUTOCOMMIT));
		} catch (Exception e){
			getLog().error(AppException.getStackTrace(e));
		}
		return Boolean.TRUE;
	}
	
	@Bean
	public CommonsSessionFactoryImpl commonsSessionFactoryImpl() throws ClassNotFoundException, SQLException, IOException{
		CommonsSessionFactoryImpl commonsSessionFactoryImpl = new CommonsSessionFactoryImpl();
		commonsSessionFactoryImpl.setPackagesToScan(packagesToScan());
		commonsSessionFactoryImpl.setHibernateProperties(hibernateProperties());
		commonsSessionFactoryImpl.setDataSource(dataSource());
		return commonsSessionFactoryImpl;
	}
	
}
