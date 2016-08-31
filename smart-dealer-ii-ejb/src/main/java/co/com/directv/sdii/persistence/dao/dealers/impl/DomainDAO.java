package co.com.directv.sdii.persistence.dao.dealers.impl;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.Domain;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.DomainDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de Arp
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Arp
 * @see co.com.directv.sdii.model.hbm.Arp.hbm.xml
 */
@Stateless(name="DomainDAOLocal",mappedName="ejb/DomainDAOLocal")
public class DomainDAO extends BaseDao implements DomainDAOLocal {

	private final static Logger log = UtilsBusiness.getLog4J(DomainDAO.class);

    @Override
    public Domain getDomainValue(String domainName, String domainValue, Long countryId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getDomainValue/DomainDAO ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Domain.class.getName());
        	stringQuery.append(" entity where entity.country.id = :countryId");
        	stringQuery.append(" and entity.domainName = :domainName and entity.domainValue = :domainValue ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("countryId", countryId);
            query.setString("domainName", domainName);
            query.setString("domainValue", domainValue);
            Object obj = query.uniqueResult();
            return (Domain) obj;
            
        } catch (Throwable ex) {
            log.error("== Error getDomainValue/DomainDAO ==");
            throw manageException(ex);
        } finally {
            log.debug("== Termina getDomainValue/DomainDAO ==");
        }
    }

    @Override
    public Domain getDomainValue(String domainName, String domainCode, String countryCode) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getDomainValue/DomainDAO ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Domain.class.getName());
        	stringQuery.append(" entity where entity.country.id = ( select ct.id from " + Country.class.getName() + " ct where ct.countryCode=:countryCode ) ");
        	stringQuery.append(" and entity.domainName = :domainName and entity.domainCode = :domainCode ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("countryCode", countryCode);
            query.setString("domainName", domainName);
            query.setString("domainCode", domainCode);
            Object obj = query.uniqueResult();
            return (Domain) obj;
            
        } catch (Throwable ex) {
            log.error("== Error getDomainValue/DomainDAO ==");
            throw manageException(ex);
        } finally {
            log.debug("== Termina getDomainValue/DomainDAO ==");
        }
    }
    
    @Override
    public List<Domain> getDomainByDomainNameDomainValueCountryId(String domainName,Long countryId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getDomainByDomainNameDomainValueCountryId/DomainDAO ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Domain.class.getName());
        	stringQuery.append(" entity where entity.country.id = :countryId ");
        	stringQuery.append(" and entity.domainName = :domainName ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("countryId", countryId);
            query.setString("domainName", domainName);
            List<Domain> domain = query.list();
            return domain;
            
        } catch (Throwable ex) {
            log.error("== Error getDomainByDomainNameDomainValueCountryId/DomainDAO ==");
            throw manageException(ex);
        } finally {
            log.debug("== Termina getDomainByDomainNameDomainValueCountryId/DomainDAO ==");
        }
    }
    
}
