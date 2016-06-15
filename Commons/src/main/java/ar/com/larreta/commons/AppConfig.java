package ar.com.larreta.commons;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.Interceptor;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ar.com.larreta.commons.exceptions.AppException;
import ar.com.larreta.commons.impl.AuditInterceptorImpl;
import ar.com.larreta.commons.initializer.LockApp;
import ar.com.larreta.commons.persistence.impl.CommonsSessionFactoryImpl;
import ar.com.larreta.commons.persistence.impl.JDBCConnectionImpl;
import ar.com.larreta.commons.utils.Base64;
import ar.com.larreta.commons.utils.FormatPatterns;

@Configuration
@EnableTransactionManagement
@DependsOn(JDBCConnectionImpl.JDBC_CONNECTION)
public class AppConfig extends AppObjectImpl{
	
	private static final String DB_INITIALIZE_AUTOCOMMIT = "db.initialize.autocommit";
	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
	private static final String PROPERTY_NAME_HIBERNATE_CONNECTION_POOL_SIZE = "hibernate.connection.poolSize";
	private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
	private static final String PROPERTY_NAME_HIBERNATE_CONNECTION_CHARSET = "hibernate.connection.CharSet";
	private static final String PROPERTY_NAME_HIBERNATE_CONNECTION_CHARACTERENCODING = "hibernate.connection.characterEncoding";
	private static final String PROPERTY_NAME_HIBERNATE_CONNECTION_USEUNICODE = "hibernate.connection.useUnicode";
	
	@Autowired
	private AppConfigData appConfigData;

	@Autowired
	private Base64 base64;
	
	private LockApp lockApp; 
	
	public AppConfigData getAppConfigData() {
		return appConfigData;
	}

	public void setAppConfigData(AppConfigData appConfigData) {
		this.appConfigData = appConfigData;
	}
    
    public LockApp getLockApp() {
		return lockApp;
	}

	public void setLockApp(LockApp lockApp) {
		this.lockApp = lockApp;
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
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(getAppConfigData().getDatabaseDriver());
		dataSource.setUrl(getAppConfigData().getDatabaseURL());
		dataSource.setUsername(appConfigData.getDatabaseUsername());
		dataSource.setPassword(appConfigData.getDatabasePassword());
		
		return dataSource;
	}



	@Bean
	public Properties hibernateProperties(){
		Properties properties = new Properties();
		properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, getAppConfigData().getProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
		properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, getAppConfigData().getProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
		properties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, getAppConfigData().getProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL));
		properties.put(PROPERTY_NAME_HIBERNATE_CONNECTION_POOL_SIZE, getAppConfigData().getProperty(PROPERTY_NAME_HIBERNATE_CONNECTION_POOL_SIZE));
		properties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, getAppConfigData().getProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO));
		properties.put(PROPERTY_NAME_HIBERNATE_CONNECTION_CHARSET, getAppConfigData().getProperty(PROPERTY_NAME_HIBERNATE_CONNECTION_CHARSET));
		properties.put(PROPERTY_NAME_HIBERNATE_CONNECTION_CHARACTERENCODING, getAppConfigData().getProperty(PROPERTY_NAME_HIBERNATE_CONNECTION_CHARACTERENCODING));
		properties.put(PROPERTY_NAME_HIBERNATE_CONNECTION_USEUNICODE, getAppConfigData().getProperty(PROPERTY_NAME_HIBERNATE_CONNECTION_USEUNICODE));
		return properties;
	}
	
	
	 @Bean
	 @Autowired
	 public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		 HibernateTransactionManager txManager = new HibernateTransactionManager();
		 txManager.setSessionFactory(sessionFactory);
		 txManager.setEntityInterceptor((Interceptor) AppManager.getInstance().getBean(AuditInterceptorImpl.AUDIT_INTERCEPTOR));
 
		 return txManager;
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

	public Base64 getBase64() {
		return base64;
	}

	public void setBase64(Base64 base64) {
		this.base64 = base64;
	}
}
