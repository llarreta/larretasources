package ar.com.larreta.commons.persistence;

import java.util.Properties;

import javax.sql.DataSource;

public interface CommonsSessionFactory extends org.springframework.beans.factory.FactoryBean<org.hibernate.SessionFactory>, 
						org.springframework.context.ResourceLoaderAware, 
						org.springframework.beans.factory.InitializingBean, 
						org.springframework.beans.factory.DisposableBean {

	public void setPackagesToScan(String... packagesToScan);
	public void setHibernateProperties(Properties hibernateProperties);
	public void setDataSource(DataSource dataSource);

}
