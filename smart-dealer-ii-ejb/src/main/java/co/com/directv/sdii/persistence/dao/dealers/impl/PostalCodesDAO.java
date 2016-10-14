package co.com.directv.sdii.persistence.dao.dealers.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.PostalCode;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.PostalCodesDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de Eps
 * 
 * Fecha de Creación: Mar 15, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Eps
 * @see co.com.directv.sdii.model.hbm.Eps.hbm.xml
 */
@Stateless(name="PostalCodesDAOLocal",mappedName="ejb/PostalCodesDAOLocal")
public class PostalCodesDAO extends BaseDao implements PostalCodesDAOLocal {

	private final static Logger log = UtilsBusiness.getLog4J(PostalCodesDAO.class);

	/**
	 * Metodo: Consultar PostalCodes por ID
	 * @param id
	 * @return PostalCodes
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public PostalCode getPostalCodesByID(Long id) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getPostalCodesByID/PostalCodesDAO ==");

		PostalCode obj = null;

		try {
			Session session = this.getSession();
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(PostalCode.class.getName());
			stringQuery.append(" postCodes where postCodes.id= :PcId");
			Query query = session.createQuery(stringQuery.toString());
			//Query query = session.createQuery("from "+PostalCode.class.getName()+" postCodes where postCodes.id= :PcId");
			query.setLong("PcId", id);
			obj = (PostalCode)query.uniqueResult();

			return obj;
		} catch (Throwable ex) {
			log.debug("== Error en getPostalCodesByID ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getPostalCodesByID/PostalCodesDAO ==");
		}
	}	

	/**
	 * Metodo: Consultar PostalCodes por Codigo
	 * @param code
	 * @return PostalCodes
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public PostalCode getPostalCodesByCode(String code) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getPostalCodesByCode/PostalCodesDAO ==");

		PostalCode obj = null;

		try {
			Session session = this.getSession();
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(PostalCode.class.getName());
			stringQuery.append(" pc where pc.postalCodeCode = :aPostalCode");
			Query query = session.createQuery(stringQuery.toString());
			//Query query = session.createQuery("from "+PostalCode.class.getName()+" pc where pc.postalCodeCode = :aPostalCode");
			query.setString("aPostalCode", code);

			obj = (PostalCode) query.uniqueResult();

			return obj;
		} catch (Throwable ex) {
			log.debug("== Error en getPostalCodesByCode ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getPostalCodesByCode/PostalCodesDAO ==");
		}
	}

	/**
	 * Metodo: Consultar todos los PostalCodes
	 * @return List<PostalCodes>
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<PostalCode> getAllPostalCodes() throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllPostalCodes/PostalCodesDAO ==");

		List<PostalCode> list = null;

		try {
			Session session = this.getSession();

			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(PostalCode.class.getName());
			stringQuery.append(" pc ");
			stringQuery.append("order by pc.postalCodeName asc");
			Query query = session.createQuery(stringQuery.toString());

			list = query.list();

			return list;
		} catch (Throwable ex) {
			log.debug("== Error en getAllPostalCodes ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getAllPostalCodes/PostalCodesDAO ==");
		}
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<PostalCode> getPostalCodesByCityId(Long cityId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getPostalCodesByCityId/PostalCodesDAO ==");

		List<PostalCode> list = null;

		try {
			Session session = this.getSession();
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(PostalCode.class.getName());
			stringQuery.append(" pc where pc.city.id = :aCityId order by pc.postalCodeName");
			Query query = session.createQuery(stringQuery.toString());
			//Query query = session.createQuery("from "+PostalCode.class.getName()+" pc where pc.city.id = :aCityId order by pc.postalCodeName");
			query.setLong("aCityId", cityId);

			list = query.list();

			return list;
		} catch (Throwable ex) {
			log.debug("== Error en getPostalCodesByCityId ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getPostalCodesByCityId/PostalCodesDAO ==");
		}
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<PostalCode> getPostalCodesByName(String name) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getPostalCodesByName/PostalCodesDAO ==");

		List<PostalCode> list = null;

		try {
			Session session = this.getSession();
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(PostalCode.class.getName());
			stringQuery.append(" pc where pc.postalCodeName = :aPostCodeName");
			Query query = session.createQuery(stringQuery.toString());
			//Query query = session.createQuery("from "+PostalCode.class.getName()+" pc where pc.postalCodeName = :aPostCodeName");
			query.setString("aPostCodeName", name);

			list = query.list();

			return list;
		} catch (Throwable ex) {
			log.debug("== Error en getPostalCodesByName ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getPostalCodesByName/PostalCodesDAO ==");
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createPostalCode(PostalCode obj) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio createPositions/PositionsDAO ==");

		try {
			Session session = this.getSession();
			session.save(obj);
			this.doFlush(session);
		} catch (Throwable ex) {
			log.debug("== Error en createPostalCode ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina createPositions/PositionsDAO ==");
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updatePostalCode(PostalCode obj) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio updatePostalCode/PositionsDAO ==");

		try {
			Session session = this.getSession();
			session.merge(obj);
			this.doFlush(session);
		} catch (Throwable ex) {
			log.debug("== Error en updatePostalCode ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina updatePostalCode/PositionsDAO ==");
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deletePostalCode(PostalCode obj) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio deletePostalCode/PositionsDAO ==");

		try {
			Session session = this.getSession();
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("delete from ");
			stringQuery.append(PostalCode.class.getName());
			stringQuery.append(" pc where pc.id = :aPcId");
			Query query = session.createQuery(stringQuery.toString());
			//Query query = session.createQuery("delete from " + PostalCode.class.getName() + " pc where pc.id = :aPcId");
			query.setLong("aPcId", obj.getId());
			query.executeUpdate();
			this.doFlush(session);
		} catch (Throwable ex) {
			log.debug("== Error en deletePostalCode ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina deletePostalCode/PositionsDAO ==");
		}
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<PostalCode> getAllPostalCodesByCountryId(Long countryId)
	throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllPostalCodesByCountryId/PostalCodesDAO ==");

		if(countryId == null || countryId <= 0L){
			return getAllPostalCodes();
		}


		List<PostalCode> list = null;

		try {
			Session session = this.getSession();

			StringBuffer sb = new StringBuffer("from ");
			sb.append(PostalCode.class.getName());
			sb.append(" pc where pc.city.state.country.id = :aCountryId");
			sb.append(" order by pc.postalCodeName asc");


			Query query = session.createQuery(sb.toString());
			query.setLong("aCountryId", countryId);

			list = query.list();

			return list;
		} catch (Throwable ex) {
			log.debug("== Error en getAllPostalCodesByCountryId ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getAllPostalCodesByCountryId/PostalCodesDAO ==");
		}
	}

	/**
	 * Metodo: Consultar PostalCodes por codigo postal y por
	 * el codigo del pais
	 * @param postalCode String
	 * @param countryCode String
	 * @return PostalCode
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public PostalCode getPostalCodesByCodeByCountryCode(String postalCode,String countryCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getPostalCodesByCode/PostalCodesDAO ==");

		PostalCode obj = null;

		try {
			Session session = this.getSession();
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("select new ");
			stringQuery.append(PostalCode.class.getName());
			stringQuery.append(" (pc.id,pc.zoneType,pc.city,pc.postalCodeName,pc.postalCodeCode) ");
			stringQuery.append("  from " + PostalCode.class.getName() + " pc ");
			stringQuery.append("           join pc.city as ci ");
			stringQuery.append("           join ci.state as st ");
			stringQuery.append("           join st.country as co ");
			stringQuery.append(" where ( pc.postalCodeCode = :aPostalCode  ");
			if(postalCode==null || postalCode.trim().equalsIgnoreCase("")){
				stringQuery.append(" or TRIM(pc.postalCodeCode) is null ");
			}
			stringQuery.append(" ) ");
			stringQuery.append("       and co.countryCode = :aCountryCode");

			Query query = session.createQuery(stringQuery.toString());

			query.setString("aPostalCode", postalCode);
			query.setString("aCountryCode", countryCode);

			List<PostalCode> pcs = query.list();
			if(pcs.isEmpty()){
				return null;
			}
			obj = pcs.get(0);
			return obj;
		} catch (Throwable ex) {
			log.debug("== Error en getPostalCodesByCode ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getPostalCodesByCode/PostalCodesDAO ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.PostalCodesDAOLocal#getPostalCodesByCodeByCountryId(java.lang.String, java.lang.Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public PostalCode getPostalCodesByCodeByCountryId(String postalCode,
			Long countryId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getPostalCodesByCodeByCountryId/PostalCodesDAO ==");
		PostalCode obj = null;

		try {
			Session session = this.getSession();
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("select new ");
			stringQuery.append(PostalCode.class.getName());
			stringQuery.append(" (pc.id,pc.zoneType,pc.city,pc.postalCodeName,pc.postalCodeCode) from ");
			stringQuery.append(PostalCode.class.getName());
			stringQuery.append(" pc ");
			stringQuery.append(" join pc.city as ci ");
			stringQuery.append(" join ci.state as st ");
			stringQuery.append(" join st.country as co ");
			stringQuery.append(" where pc.postalCodeCode = :aPostalCode and ");
			stringQuery.append(" co.id = :aCountryId");

			Query query = session.createQuery(stringQuery.toString());

			query.setString("aPostalCode", postalCode);
			query.setLong("aCountryId", countryId);

			List<PostalCode> pcs = query.list();
			if(pcs.isEmpty()){
				return null;
			}
			obj = pcs.get(0);
			return obj;
		} catch (Throwable ex) {
			log.debug("== Error en getPostalCodesByCodeByCountryId ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getPostalCodesByCodeByCountryId/PostalCodesDAO ==");
		}
	}
}