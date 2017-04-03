package ar.com.larreta.persistence.configs;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ar.com.larreta.tools.Base64;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:persistence.properties")
public class PersistenceConfig  {
	
	public static final String CLOSE_PLACE_HOLDER_TAG = "}";
	public static final String OPEN_PLACE_HOLDER_TAG = "${";
	
	public static final String DRIVER_CLASS_NAME 						= "db.driverClassName";
	public static final String URL 										= "db.url";
	public static final String USERNAME 								= "db.username";
	public static final String PASSWORD 								= "db.password";
	
	public static final String HIBERNATE_PROPERTIES						= "hibernateProperties";
	
	public static final String HIBERNATE_DIALECT 						= "hibernate.dialect";
	public static final String HIBERNATE_SHOW_SQL 						= "hibernate.show_sql";
	public static final String HIBERNATE_FORMAT_SQL 					= "hibernate.format_sql";
	public static final String HIBERNATE_CONNECTION_POOL_SIZE 			= "hibernate.connection.poolSize";
	public static final String HIBERNATE_HBM2DDL_AUTO 					= "hibernate.hbm2ddl.auto";
	public static final String HIBERNATE_CONNECTION_CHARSET 			= "hibernate.connection.CharSet";
	public static final String HIBERNATE_CONNECTION_CHARACTERENCODING 	= "hibernate.connection.characterEncoding";
	public static final String HIBERNATE_CONNECTION_USEUNICODE 			= "hibernate.connection.useUnicode";
	
	@Value(OPEN_PLACE_HOLDER_TAG + 			DRIVER_CLASS_NAME 							+ CLOSE_PLACE_HOLDER_TAG)
	private String driverClassName;
	
	@Value(OPEN_PLACE_HOLDER_TAG + 			URL 										+ CLOSE_PLACE_HOLDER_TAG)
	private String url;
	
	@Value(OPEN_PLACE_HOLDER_TAG + 			USERNAME 									+ CLOSE_PLACE_HOLDER_TAG)
	private String username;
	
	@Value(OPEN_PLACE_HOLDER_TAG + 			PASSWORD 									+ CLOSE_PLACE_HOLDER_TAG)
	private String password;
	
	@Value(OPEN_PLACE_HOLDER_TAG + 			HIBERNATE_DIALECT							+ CLOSE_PLACE_HOLDER_TAG)
	private String hibernateDialect;
	
	@Value(OPEN_PLACE_HOLDER_TAG + 			HIBERNATE_SHOW_SQL							+ CLOSE_PLACE_HOLDER_TAG)
	private String hibernateShowSQL;

	@Value(OPEN_PLACE_HOLDER_TAG + 			HIBERNATE_FORMAT_SQL						+ CLOSE_PLACE_HOLDER_TAG)
	private String hibernateFormatSQL;

	@Value(OPEN_PLACE_HOLDER_TAG + 			HIBERNATE_CONNECTION_POOL_SIZE				+ CLOSE_PLACE_HOLDER_TAG)
	private String hibernatePoolSize;

	@Value(OPEN_PLACE_HOLDER_TAG + 			HIBERNATE_HBM2DDL_AUTO						+ CLOSE_PLACE_HOLDER_TAG)
	private String hibernateHBM2DDL;

	@Value(OPEN_PLACE_HOLDER_TAG + 			HIBERNATE_CONNECTION_CHARSET				+ CLOSE_PLACE_HOLDER_TAG)
	private String hibernateCharset;

	@Value(OPEN_PLACE_HOLDER_TAG + 			HIBERNATE_CONNECTION_CHARACTERENCODING		+ CLOSE_PLACE_HOLDER_TAG)
	private String hibernateCharacterEncoding;

	@Value(OPEN_PLACE_HOLDER_TAG + 			HIBERNATE_CONNECTION_USEUNICODE				+ CLOSE_PLACE_HOLDER_TAG)
	private String hibernateUseUnicode;
	
	@Autowired
	private Base64 base64;

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	@Qualifier(HIBERNATE_PROPERTIES)
	private Properties hibernateProperties;
	
	@Bean
	public Base64 Base64(){
		return new Base64();
	}
	
	/**
	 * To resolve ${} in @Value
	 * @return
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceHolderConfigurer() {
		PropertySourcesPlaceholderConfigurer placeholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		
		// En TRUE, evita que si no puede encontrar alguna propiedad, falle al iniciar
		placeholderConfigurer.setIgnoreUnresolvablePlaceholders(Boolean.TRUE);
		
		return placeholderConfigurer;
	}
	
	@Bean
	public DataSource dataSource() throws ClassNotFoundException, SQLException, IOException {
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(base64.decrypt(username));
		dataSource.setPassword(base64.decrypt(password));
		
		return dataSource;
	}
	
	@Bean(name=HIBERNATE_PROPERTIES)
	public Properties hibernateProperties(){
		Properties properties = new Properties();
		properties.put(HIBERNATE_DIALECT, hibernateDialect);
		properties.put(HIBERNATE_SHOW_SQL, hibernateShowSQL);
		properties.put(HIBERNATE_FORMAT_SQL, hibernateFormatSQL);
		properties.put(HIBERNATE_CONNECTION_POOL_SIZE, hibernatePoolSize);
		properties.put(HIBERNATE_HBM2DDL_AUTO, hibernateHBM2DDL);
		properties.put(HIBERNATE_CONNECTION_CHARSET, hibernateCharset);
		properties.put(HIBERNATE_CONNECTION_CHARACTERENCODING, hibernateCharacterEncoding);
		properties.put(HIBERNATE_CONNECTION_USEUNICODE, hibernateUseUnicode);
		return properties;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory(){
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);
		sessionFactoryBean.setHibernateProperties(hibernateProperties);
		sessionFactoryBean.setPackagesToScan("ar.com.larreta");
		return sessionFactoryBean;
	}
	
	 @Bean
	 @Autowired
	 public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		 HibernateTransactionManager txManager = new HibernateTransactionManager();
		 txManager.setSessionFactory(sessionFactory);
		 return txManager;
	 }

}
