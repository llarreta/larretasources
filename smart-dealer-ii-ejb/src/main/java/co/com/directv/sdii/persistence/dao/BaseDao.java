package co.com.directv.sdii.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NameNotFoundException;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.exception.LockAcquisitionException;
import org.hibernate.exception.SQLGrammarException;

import co.com.directv.sdii.business.AbstractPaginationBase;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.persistence.hibernate.ConnectionFactory;

/**
 * Define operaciones comunes a las implementaciones de los dao con hibernate
 * 
 * Fecha de Creación: 24/04/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 */
public abstract class BaseDao extends AbstractPaginationBase {

	/**
	 * Metodo: trata una excepción para convertirla en una administrada por el sistema
	 * @param ex exepción a ser convertida
	 * @return excepción administrada por el sistema que tiene un código identificador de la misma
	 * @throws DAOServiceException En caso de error de tipo servicio de dao
	 * @author jjimenezh
	 */
	public DAOSQLException manageException(Throwable ex) throws DAOServiceException{
		if (ex instanceof ConstraintViolationException) {
            return new DAOSQLException(ErrorBusinessMessages.CONSTRAINT_VIOLATION.getCode(),ErrorBusinessMessages.CONSTRAINT_VIOLATION.getMessageCode(), ex);
        } else if (ex instanceof DataException) {
        	return new DAOSQLException(ErrorBusinessMessages.ERROR_DATA.getCode(),ErrorBusinessMessages.ERROR_DATA.getMessageCode(), ex);
        } else if (ex instanceof GenericJDBCException) {
        	return new DAOSQLException(ErrorBusinessMessages.GENERIC_JDBC.getCode(),ErrorBusinessMessages.GENERIC_JDBC.getMessageCode(), ex);
        } else if (ex instanceof JDBCConnectionException) {
        	return new DAOSQLException(ErrorBusinessMessages.JDBC_CONNECTION.getCode(),ErrorBusinessMessages.JDBC_CONNECTION.getMessageCode(), ex);
        } else if (ex instanceof LockAcquisitionException) {
        	return new DAOSQLException(ErrorBusinessMessages.LOCK_ACQUISITION.getCode(),ErrorBusinessMessages.LOCK_ACQUISITION.getMessageCode(), ex);
        } else if (ex instanceof SQLGrammarException) {
        	return new DAOSQLException(ErrorBusinessMessages.SQL_GRAMMAR.getCode(),ErrorBusinessMessages.SQL_GRAMMAR.getMessageCode(), ex);
        } else if (ex instanceof PropertiesException){
        	throw new DAOServiceException(ErrorBusinessMessages.ERROR_SERVICE.getCode(),ErrorBusinessMessages.ERROR_SERVICE.getMessageCode(), ex);
        } else if (ex instanceof NameNotFoundException){
        	throw new DAOServiceException(ErrorBusinessMessages.DATASOURCE_NOT_FOUND.getCode(),ErrorBusinessMessages.DATASOURCE_NOT_FOUND.getMessageCode(), ex);
        } else {
            throw new DAOServiceException(ErrorBusinessMessages.ERROR_SERVICE.getCode(),ErrorBusinessMessages.ERROR_SERVICE.getMessageCode(), ex);
        }
	}
	
	/**
	 * Metodo: realiza el flush sobre la sesión administrada por el sistema
	 * @param session sessión sobre la que se realizará el flush
	 * @author jjimenezh
	 */
	public void doFlush(Session session){
		session.flush();
	}
	
	/**
	 * Metodo: Libera la Conexion JDBC que hibernate esta utilizando 
	 * @param session session de JDBC que se desea cerrar
	 * @author jalopez
	 */
	public void closeSession(Session session){
		session.close();
	}
	
	/**
	 * Metodo: Obtiene una sesión con la persistencia y se asegura que no se retorne nulo
	 * @return Sesión de persistencia
	 * @throws DAOServiceException en caso de error al tratar de obtener la sesión o que se obtenga nula
	 * @author jjimenezh
	 */
	public Session getSession() throws DAOServiceException{
		Session session = ConnectionFactory.getSession();
		if (session == null) {
            throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode(), ErrorBusinessMessages.SESSION_NULL.getMessageCode());
        }
		return session;
	}
	
	/**
	 * Metodo: Obtiene una sesión con la persistencia. Se usa en el caso de los procesos batch, 
	 * por ejemplo cuando se tiene que hacer inserts o updates masivos, 
	 * así se evita que cada vez que se hace el save de un objeto, el mismo se quede en memoria y 
	 * en el correr del proceso se produzca un error del tipo OutOfMemoryError. 
	 * La StatelessSession no interactúa con el Cache de Primer Nivel ni con el Cache de Segundo Nivel,
	 * @return statelessSession StatelessSession
	 * @throws DAOServiceException en caso de error al tratar de obtener la sesión o que se obtenga nula
	 * @author jalopez
	 */
	public StatelessSession getStatelessSession() throws DAOServiceException {
		StatelessSession statelessSession = ConnectionFactory.getStatelessSession();
		if (statelessSession == null) {
            throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode(), ErrorBusinessMessages.SESSION_NULL.getMessageCode());
        }
		return statelessSession;
	}
	
	public Query createCountQuery(String query) throws DAOServiceException{
		StringBuffer countQuery = new StringBuffer("select count(*) from (");
		countQuery.append(query);
		countQuery.append(")");
		Query queryResult = getSession().createQuery(countQuery.toString());
		return queryResult;
	}
	
	
	public <T> List<T> findByExample(T t){
		List<T> listObjects = new ArrayList<T>();
		try {
			Criteria criteria = getSession().createCriteria(t.getClass());
			listObjects = criteria.list();
		} catch (DAOServiceException e) {
			e.printStackTrace();
		}
		return listObjects;
	}
	
}
