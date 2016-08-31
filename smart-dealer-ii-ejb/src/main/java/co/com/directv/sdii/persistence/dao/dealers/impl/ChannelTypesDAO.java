package co.com.directv.sdii.persistence.dao.dealers.impl;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ChannelType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.ChannelTypesDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de ChannelTypes
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ChannelTypes
 * @see co.com.directv.sdii.model.hbm.ChannelTypes.hbm.xml
 */
@Stateless(name="ChannelTypesDAOLocal",mappedName="ejb/ChannelTypesDAOLocal")
public class ChannelTypesDAO extends BaseDao implements ChannelTypesDAOLocal {

	private final static Logger log = UtilsBusiness.getLog4J(ChannelTypesDAO.class);

	/**
	 * Metodo: Consultar ChannelTypes por ID
	 * @param id
	 * @return ChannelTypes
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
	public ChannelType getChannelTypesByID(Long id) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getChannelTypesByID/ChannelTypesDAO ==");
		Session session = getSession();
		ChannelType obj = null;

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(ChannelType.class.getName());
			stringQuery.append(" ct where ct.id= :aCtId");
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("aCtId", id);
			obj = (ChannelType)query.uniqueResult();

			return obj;
		} catch (Throwable ex){
			log.debug("== Error consultando el ChannelTypes por ID ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getChannelTypesByID/ChannelTypesDAO ==");
		}
	}	

	/**
	 * Metodo: Consultar ChannelTypes por Codigo
	 * @param code
	 * @return ChannelTypes
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
	public ChannelType getChannelTypesByCode(String code) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getChannelTypesByCode/ChannelTypesDAO ==");
		Session session = getSession();
		ChannelType obj = null;

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ChannelType ct ");
			stringQuery.append("where ");
			stringQuery.append("ct.canalCode = :aCahnnelTypeCode ");
			Query query = session.createQuery(stringQuery.toString());
			query.setString("aCahnnelTypeCode", code);
			obj = (ChannelType)query.uniqueResult();
			return obj;
		} catch (Throwable ex){
			log.debug("== Error consultando el ChannelTypes por Code ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getChannelTypesByCode/ChannelTypesDAO ==");
		}
	}

	/**
	 * Metodo: Consultar todos los ChannelTypes
	 * @return List<ChannelTypes>
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
	@SuppressWarnings("unchecked")
	public List<ChannelType> getAllChannelTypes() throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllChannelTypes/ChannelTypesDAO ==");
		Session session = getSession();
		List<ChannelType> list = null;

		try {
			Query query = session.createQuery("from ChannelType");
			list = query.list();
			return list;
		} catch (Throwable ex){
			log.debug("== Error consultando todos los ChannelTypes ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getAllChannelTypes/ChannelTypesDAO ==");
		}
	}
}