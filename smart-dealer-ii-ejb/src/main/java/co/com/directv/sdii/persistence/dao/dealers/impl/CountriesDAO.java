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
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.CountriesDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de Countries
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Countries
 * @see co.com.directv.sdii.model.hbm.Countries.hbm.xml
 */
@Stateless(name="CountriesDAOLocal",mappedName="ejb/CountriesDAOLocal")
public class CountriesDAO extends BaseDao implements CountriesDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(CountriesDAO.class);

    /**
     * Metodo: Consultar Countries por ID
     * @param id
     * @return Countries
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    public Country getCountriesByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCountriesByID/CountriesDAO ==");
        Session session = getSession();
        Country obj = null;

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Country.class.getName());
        	stringQuery.append(" ctr where ctr.id= :aCtrId");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + Country.class.getName() + " ctr where ctr.id= :aCtrId");
            query.setLong("aCtrId", id);
            obj = (Country) query.uniqueResult();
            return obj;
        } catch (Throwable ex) {
            log.debug("== Error consultando el Countries por ID ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCountriesByID/CountriesDAO ==");
        }
    }

    /**
     * Metodo: Consultar Countries por Codigo
     * @param code
     * @return Countries
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    public Country getCountriesByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCountriesByCode/CountriesDAO ==");
        Session session = getSession();
        Country obj = null;

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Country.class.getName());
        	stringQuery.append(" ct where ct.countryCode= :aCtCode");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + Country.class.getName() + " ct where ct.countryCode= :aCtCode");
            query.setString("aCtCode", code);
            obj = (Country) query.uniqueResult();

            return obj;
        } catch (Throwable ex) {
            log.debug("== Error consultando el Countries por Code ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCountriesByCode/CountriesDAO ==");
        }
    }

    /**
     * Metodo: Consultar todos los Countries
     * @return List<Countries>
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    @SuppressWarnings("unchecked")
	public List<Country> getAllCountries() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllCountries/CountriesDAO ==");
        Session session = getSession();
        List<Country> list = null;

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Country.class.getName());
        	stringQuery.append(" c order by c.countryName");
        	Query query = session.createQuery(stringQuery.toString());
        	//Query query = session.createQuery("from " + Country.class.getName() + " c order by c.countryName");
            list = query.list();

            return list;
        } catch (Throwable ex) {
            log.debug("== Error consultando todos los Countries ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllCountries/CountriesDAO ==");
        }
    }
    
    /**
     * Metodo: Consultar Countries por ISO3CODE
     * @param code
     * @return Countries
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    public Country getCountriesByIso3Code(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCountriesByIso3Code/CountriesDAO ==");
        Session session = getSession();
        Country obj = null;

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Country.class.getName());
        	stringQuery.append(" ct where ct.iso3Code= :aCtCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aCtCode", code);
            obj = (Country) query.uniqueResult();

            return obj;
        } catch (Throwable ex) {
            log.debug("== Error consultando el Countries por Iso3Code ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCountriesByIso3Code/CountriesDAO ==");
        }
    }
}
