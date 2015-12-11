package ar.com.larreta.commons.persistence.impl;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import ar.com.larreta.commons.persistence.CommonsSessionFactory;

//@Component
public class CommonsSessionFactoryImpl extends LocalSessionFactoryBean implements CommonsSessionFactory {
	
	@Autowired
	@Override
	public void setPackagesToScan(String... packagesToScan) {
		super.setPackagesToScan(packagesToScan);
	}

	@Autowired
	@Override
	public void setHibernateProperties(Properties hibernateProperties) {
		super.setHibernateProperties(hibernateProperties);
	}

	@Autowired
	@Override
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

}
