package co.com.directv.sdii.persistence.dao.dealers.impl;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.CrewOff;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.CrewsOffDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de Vehicle
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.CrewOff
 * @see co.com.directv.sdii.model.hbm.CrewOff.hbm.xml
 */
@Stateless(name="CrewsOffDAOLocal",mappedName="ejb/CrewsOffDAOLocal")
public class CrewsOffDAO extends BaseDao implements CrewsOffDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(CrewsOffDAO.class);

    public CrewsOffDAO() {
    }

    /**
     * Metodo: Crear CrewOff
     * @param obj CrewOff
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    public void createCrewOff(CrewOff obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createCrewOff/CrewOffDAO ==");
        Session session = getSession();

        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina createCrewOff/CrewOffDAO ==");
        }
    }

    /**
     * Metodo: Consultar CrewOff por ID
     * @param id
     * @return CrewOff
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    public CrewOff getCrewOffByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCrewOffByID/CrewOffDAO ==");
        Session session = getSession();
        CrewOff obj = null;
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from CrewOff co ");
        	stringQuery.append("where ");
        	stringQuery.append("co.id = :aCrewOffId ");
        	Query query = session.createQuery(stringQuery.toString());
        	//Query query = session.createQuery("from CrewOff co where co.id = :aCrewOffId");
        	query.setLong("aCrewOffId", id);
        	obj = (CrewOff) query.uniqueResult();
            return obj;
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina getCrewOffByID/CrewOffDAO ==");
        }
    }

    /**
     * Metodo: Actualizar CrewOff
     * @param obj CrewOff
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    public void updateCrewOff(CrewOff obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateCrewOff/CrewOffDAO ==");
        Session session = getSession();

        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina updateCrewOff/CrewOffDAO ==");
        }
    }

    /**
     * Metodo: Eliminar CrewOff
     * @param obj CrewOff
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    public void deleteCrewOff(CrewOff obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteCrewOff/CrewOffDAO ==");
        Session session = getSession();

        try {
            session.delete(obj);
            this.doFlush(session);
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina deleteCrewOff/CrewOffDAO ==");
        }

    }

    /**
     * Metodo: Consultar todos los CrewOff
     * @return List<CrewOff>
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    @SuppressWarnings("unchecked")
	public List<CrewOff> getAllCrewOff() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllCrewOff/CrewOffDAO ==");
        Session session = getSession();
        List<CrewOff> list = null;
        try {
        	Query query = session.createQuery("from CrewOff");
        	list = query.list();
            return list;
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina getAllCrewOff/CrewOffDAO ==");
        }
    }
    
    /**
     * Metodo: Consultar CrewOff por el id de la cuadrilla
     * @param  crewOff CrewOff
     * @return List<CrewOff> 
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author jalopez
     */
    public List<CrewOff> getCrewOffByCreId(CrewOff crewOff) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCrewOffByCreId/CrewOffDAO ==");
        Session session = getSession();        
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append(" select new CrewOff(co.offDate,co.offDescription) from ");
        	stringQuery.append(CrewOff.class.getName());
        	stringQuery.append(" co where ");
        	stringQuery.append(" co.crew.id = :aCrewId order by co.offDate desc");
        	Query query = session.createQuery(stringQuery.toString());
        	
        	query.setLong("aCrewId", crewOff.getCrew().getId());
        	
        	return query.list();
        } catch (Throwable ex) {
			log.error("== Error en la operacion getCrewOffByCreId/CrewOffDAO ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina getCrewOffByCreId/CrewOffDAO ==");
        }
    }
}
