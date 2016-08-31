package co.com.directv.sdii.persistence.dao.dealers.impl;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.City;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.CitiesDAOLocal;

/** 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de Cities
 * 
 * Fecha de Creacion: Mar 8, 2010
 * @author jcasas <a href="mailto:jcasas@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Cities
 * @see co.com.directv.sdii.model.hbm.Cities.hbm.xml
 */
@Stateless(name="CitiesDAOLocal",mappedName="ejb/CitiesDAOLocal")
public class CitiesDAO extends BaseDao implements CitiesDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(CitiesDAO.class);

    /**
     * Obtiene una ciudad con el ID especificado
     * @param id - Long
     * @return City
     * @throws DAOServiceException
     * @throws DAOSQLException 
     * @author jcasas
     */
    public City getCitiesByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCitiesByID/CitiesDAO ==");
        Session session = getSession();
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(City.class.getName());
        	stringQuery.append(" city where city.id = :id");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("id", id);

            return (City) query.uniqueResult();

        } catch (Throwable ex) {
            log.debug("== Error consultando el Cities por ID ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCitiesByID/CitiesDAO ==");
        }
    }

    /**
     * Obtiene las ciudades por Codigo especificado
     * @param code - String
     * @return City
     * @throws DAOServiceException
     * @throws DAOSQLException 
     * @author jcasas
     */
    public City getCitiesByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCitiesByCode/CitiesDAO ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(City.class.getName());
        	stringQuery.append(" city where city.cityCode = :cityCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("cityCode", code);

            return (City) query.uniqueResult();

        } catch (Throwable ex) {
            log.debug("== Error consultando el Cities por Code ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCitiesByCode/CitiesDAO ==");
        }
    }

    /**
     * Obtiene todas las ciudades del sistema
     * @return List<Cities>
     * @throws DAOServiceException
     * @throws DAOSQLException 
     * @author jcasas
     */
    @SuppressWarnings("unchecked")
	public List<City> getAllCities() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllCities/CitiesDAO ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(City.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
        } catch (Throwable ex) {
            log.debug("== Error consultando todos los Cities ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllCities/CitiesDAO ==");
        }
    }

    @SuppressWarnings("unchecked")
	public List<City> getCitiesByStateId(Long stateId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getCitiesByStateId/CitiesDAO ==");
        Session session = getSession();
        List<City> cities = null;
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(City.class.getName());
        	stringQuery.append(" ct where ct.state.id = :aStateId order by ct.cityName");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aStateId", stateId);

            cities = query.list();

            return cities;

        } catch (Throwable ex) {
            log.debug("== Error consultando todos los Cities ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCitiesByStateId/CitiesDAO ==");
        }
    }
}
