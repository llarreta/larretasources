package co.com.directv.sdii.persistence.dao.dealers.impl;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.State;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.StatesDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de States
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.States
 * @see co.com.directv.sdii.model.hbm.States.hbm.xml
 */
@Stateless(name="StatesDAOLocal",mappedName="ejb/StatesDAOLocal")
public class StatesDAO extends BaseDao implements StatesDAOLocal {
	
    private final static Logger log = UtilsBusiness.getLog4J(StatesDAO.class);

    /**
     * Metodo: Consultar States por ID
     * @param id
     * @return States
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    public State getStatesByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getStatesByID/StatesDAO ==");
       
        State obj = null;
       
        try {
        	Session session = this.getSession();
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(State.class.getName());
            stringQuery.append(" st where st.id= :aStId");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from "+State.class.getName()+" st where st.id= :aStId");
            query.setLong("aStId", id);
            obj = (State)query.uniqueResult();

            return obj;
        } catch (Throwable ex) {
            log.debug("== Error en getStatesByID ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getStatesByID/StatesDAO ==");
        }
    }	
    
    /**
	 * Metodo: Consultar States por Codigo
	 * @param code
	 * @return States
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
    public State getStatesByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getStatesByCode/StatesDAO ==");
       
        State obj = null;
       
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(State.class.getName());
        	stringQuery.append(" st where st.stateCode = :aStCode");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from "+State.class.getName()+" st where st.stateCode = :aStCode");
            query.setString("aStCode", code);

            obj = (State) query.uniqueResult();

            return obj;
        } catch (Throwable ex) {
            log.debug("== Error en getStatesByCode ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getStatesByCode/StatesDAO ==");
        }
    }
	
    /**
	 * Metodo: Consultar todos los States
	 * @return List<States>
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
    @SuppressWarnings("unchecked")
	public List<State> getAllStates() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllStates/StatesDAO ==");
        
        List<State> list = null;
        
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(State.class.getName());
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from "+State.class.getName());
            list = query.list();
            return list;
        } catch (Throwable ex) {
            log.debug("== Error en getAllStates ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getAllStates/StatesDAO ==");
        }
    }

    @SuppressWarnings("unchecked")
	public List<State> getStatesByCountryID(Long countryId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getStatesByCountryID/StatesDAO ==");
        
        List<State> states  = null;

        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(State.class.getName());
        	stringQuery.append(" st where st.country.id= :aCtId order by st.stateName");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from "+State.class.getName()+" st where st.country.id= :aCtId order by st.stateName");
            query.setLong("aCtId", countryId);
            states = query.list();

            return states;
        } catch (Throwable ex) {
            log.debug("== Error en getStatesByCountryID ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getStatesByCountryID/StatesDAO ==");
        }
    }
}