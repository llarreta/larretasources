package co.com.directv.sdii.persistence.dao.dealers.impl;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.CrewType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.CrewTypesDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de CrewTypes
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.CrewTypes
 * @see co.com.directv.sdii.model.hbm.CrewTypes.hbm.xml
 */
@Stateless(name="CrewTypesDAOLocal",mappedName="ejb/CrewTypesDAOLocal")
public class CrewTypesDAO extends BaseDao implements CrewTypesDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(CrewTypesDAO.class);

    /**
     * Obtiene un tipo de una cuadrilla con el id especificado
     * @param id - Long
     * @return - CrewStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public CrewType getCrewTypesByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCrewTypesByID/CrewTypesDAO ==");
        Session session = getSession();
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(CrewType.class.getName());
        	stringQuery.append(" crewType where crewType.id = :id");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + CrewType.class.getName() + " crewType where crewType.id = :id");
            query.setLong("id", id);
            Object obj = query.uniqueResult();
            if (obj == null) {
                return null;
            }
            return (CrewType) obj;
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getCrewTypesByID/CrewTypesDAO ==");
        }
    }

    /**
     * Obtiene un tipo de cuadrilla con el codigo especificado
     * @param code
     * @return CrewTypes
     * @throws DAOServiceException
     * @throws DAOSQLException 
     */
    public CrewType getCrewTypesByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCrewTypesByCode/CrewTypesDAO ==");
        Session session = getSession();
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(CrewType.class.getName());
        	stringQuery.append(" crewType where crewType.crewTypeCode = :crewTypeCode");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + CrewType.class.getName() + " crewType where crewType.crewTypeCode = :crewTypeCode");
            query.setString("crewTypeCode", code);

            Object obj = query.uniqueResult();
            if (obj == null) {
                return null;
            }
            return (CrewType) obj;
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getCrewTypesByCode/CrewTypesDAO ==");
        }
    }

    /**
     * Consulta todos los CrewTypes
     * @return List<CrewTypes>
     * @throws DAOServiceException
     * @throws DAOSQLException 
     */
    @SuppressWarnings("unchecked")
	public List<CrewType> getAllCrewTypes() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllCrewTypes/CrewTypesDAO ==");
        Session session = getSession();
        List<CrewType> list = null;

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(CrewType.class.getName());
        	list = session.createQuery(stringQuery.toString()).list();
            //list = session.createQuery("from " + CrewType.class.getName()).list();
            return list;
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllCrewTypes/CrewTypesDAO ==");
        }
    }
}
