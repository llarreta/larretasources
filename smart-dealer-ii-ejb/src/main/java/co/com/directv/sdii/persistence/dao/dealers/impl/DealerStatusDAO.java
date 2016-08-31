package co.com.directv.sdii.persistence.dao.dealers.impl;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.DealerStatus;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.DealerStatusDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de DealerStatus
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.DealerStatus
 * @see co.com.directv.sdii.model.hbm.DealerStatus.hbm.xml
 */
@Stateless(name="DealerStatusDAOLocal",mappedName="ejb/DealerStatusDAOLocal")
public class DealerStatusDAO extends BaseDao implements DealerStatusDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(DealerStatusDAO.class);

    /**
     * Metodo: Consultar DealerStatus por ID
     * @param id
     * @return DealerStatus
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    public DealerStatus getDealerStatusByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getDealerStatusByID/DealerStatusDAO ==");
        Session session = getSession();
        DealerStatus obj = null;

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DealerStatus.class.getName());
        	stringQuery.append(" ds where ds.id = :aDsId");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + DealerStatus.class.getName() + " ds where ds.id = :aDsId");
            query.setLong("aDsId", id);
            obj = (DealerStatus) query.uniqueResult();

            return obj;
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina getDealerStatusByID/DealerStatusDAO ==");
        }
    }

    /**
     * Metodo: Consultar DealerStatus por Codigo
     * @param code
     * @return DealerStatus
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    public DealerStatus getDealerStatusByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getDealerStatusByCode/DealerStatusDAO ==");
        Session session = getSession();
        DealerStatus obj = null;

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from DealerStatus ds ");
        	stringQuery.append("where ");
        	stringQuery.append("ds.statusCode = :aDsCode ");
        	Query query = session.createQuery(stringQuery.toString());
        	//Query query = session.createQuery("from DealerStatus ds where ds.statusCode = :aDsCode");
        	query.setString("aDsCode", code);
        	obj = (DealerStatus)query.uniqueResult();
        	
            return obj;
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina getDealerStatusByCode/DealerStatusDAO ==");
        }
    }

    /**
     * Metodo: Consultar todos los DealerStatus
     * @return List<DealerStatus>
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    @SuppressWarnings("unchecked")
	public List<DealerStatus> getAllDealerStatus() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllDealerStatus/DealerStatusDAO ==");
        Session session = getSession();
        List<DealerStatus> list = null;

        try {
        	Query query = session.createQuery("from DealerStatus");
        	list = query.list();
        	
            return list;
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina getAllDealerStatus/DealerStatusDAO ==");
        }
    }
}
