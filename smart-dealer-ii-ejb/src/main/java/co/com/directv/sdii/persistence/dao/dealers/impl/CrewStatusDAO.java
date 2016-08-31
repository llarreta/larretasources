package co.com.directv.sdii.persistence.dao.dealers.impl;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.CrewStatus;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.CrewStatusDAOLocal;
import co.com.directv.sdii.persistence.hibernate.ConnectionFactory;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de CrewStatus
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.CrewStatus
 * @see co.com.directv.sdii.model.hbm.CrewStatus.hbm.xml
 */
@Stateless(name="CrewStatusDAOLocal",mappedName="ejb/CrewStatusDAOLocal")
public class CrewStatusDAO extends BaseDao implements CrewStatusDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(CrewStatusDAO.class);

    /**
     * Obtiene un estado de una cuadrilla con el id especificado
     * @param id - Long
     * @return - CrewStatus
     * @throws DAOServiceException
     * @throws DAOSQLException 
     */
    public CrewStatus getCrewStatusByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCrewStatusByID/CrewStatusDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
            	throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode(),ErrorBusinessMessages.SESSION_NULL.getMessage());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(CrewStatus.class.getName());
            stringQuery.append(" crewStatus where crewStatus.id = :id");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + CrewStatus.class.getName() + " crewStatus where crewStatus.id = :id");
            query.setLong("id", id);
            Object obj = query.uniqueResult();
            if (obj == null) {
                return null;
            }
            return (CrewStatus) obj;

        }catch (Throwable ex){
			log.error("== Error consultando CrewStatus por ID==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getCrewStatusByID/CrewStatusDAO ==");
        }
    }

    /**
     * Obtiene un estado de cuadrilla con el codigo especificado
     * @param code - String
     * @return - CrewStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public CrewStatus getCrewStatusByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCrewStatusByCode/CrewStatusDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
            	throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode(),ErrorBusinessMessages.SESSION_NULL.getMessage());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(CrewStatus.class.getName());
            stringQuery.append(" crewStatus where crewStatus.statusCode = :statusCode");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + CrewStatus.class.getName() + " crewStatus where crewStatus.statusCode = :statusCode");
            query.setString("statusCode", code);

            Object obj = query.uniqueResult();
            if (obj == null) {
                return null;
            }
            return (CrewStatus) obj;

        } catch (Throwable ex){
			log.error("== Error consultando CrewStatus por código==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina getCrewStatusByCode/CrewStatusDAO ==");
        }
    }

    /**
     * Consulta todos los CrewStatus
     * @return List<CrewStatus>
     * @throws DAOServiceException
     * @throws DAOSQLException 
     */
    @SuppressWarnings("unchecked")
	public List<CrewStatus> getAllCrewStatus() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllCrewStatus/CrewStatusDAO ==");
        Session session = ConnectionFactory.getSession();
        List<CrewStatus> list = null;

        try {
            if (session == null) {
            	throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode(),ErrorBusinessMessages.SESSION_NULL.getMessage());
            }
            StringBuffer queryBuffer =  new StringBuffer();
            queryBuffer.append(" from ");
            queryBuffer.append(CrewStatus.class.getName());
            queryBuffer.append(" status order by status.statusName");
            
            list = session.createQuery(queryBuffer.toString()).list();
            
            return list;
        }catch (Throwable ex){
			log.error("== Error consultando todos los CrewStatus ==");
			throw this.manageException(ex);
		}finally {
            log.debug("== Termina getAllCrewStatus/CrewStatusDAO ==");
        }
    }
}
