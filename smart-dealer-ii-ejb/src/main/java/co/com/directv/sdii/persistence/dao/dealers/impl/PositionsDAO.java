package co.com.directv.sdii.persistence.dao.dealers.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Position;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.PositionsDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de Positions
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Positions
 * @see co.com.directv.sdii.model.hbm.Positions.hbm.xml
 */
@Stateless(name="PositionsDAOLocal",mappedName="ejb/PositionsDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PositionsDAO extends BaseDao implements PositionsDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(DealersDAO.class);
   

    /**
     * Crea un nuevo cargo en el sistema
     * @param obj - Positions
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createPositions(Position obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createPositions/PositionsDAO ==");
       
        try {
        	Session session = this.getSession();
            
            session.save(obj);
            this.doFlush(session);
        }  catch (Throwable ex) {
            log.debug("== Error en createPositions ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createPositions/PositionsDAO ==");
        }
    }

    /**
     * Obtiene un cargo por el id especificado
     * @param id - Long
     * @return Positions
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Position getPositionsByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getPositionsByID/PositionsDAO ==");
        
        try {
        	Session session = this.getSession();
            StringBuffer stringQuery =  new StringBuffer();
            stringQuery.append("select p from ");
            stringQuery.append(Position.class.getName());
            stringQuery.append(" p where p.id = :id");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("select p from " + Position.class.getName() + " p where p.id = :id");
            query.setLong("id", id);

            return (Position) query.uniqueResult();

        }  catch (Throwable ex) {
            log.debug("== Error en getPositionsByID ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getPositionsByID/PositionsDAO ==");
        }
    }

    /**
     * Obtiene un cargo de acuerdo con el codigo del mismo
     * @param code - String
     * @return - Positions
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Position getPositionsByPositionCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getPositionsByPositionCode/PositionsDAO ==");
        

        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("select p from ");
        	stringQuery.append(Position.class.getName());
        	stringQuery.append(" p where p.positionCode = :positionCode");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("select p from " + Position.class.getName() + " p where p.positionCode = :positionCode");
            query.setString("positionCode", code);

            return (Position) query.uniqueResult();

        }  catch (Throwable ex) {
            log.debug("== Error en getPositionsByPositionCode ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getPositionsByPositionCode/PositionsDAO ==");
        }
    }

    /**
     * Actualiza un cargo especifico
     * @param obj - Positions
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updatePositions(Position obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updatePositions/PositionsDAO ==");
        

        try {
        	Session session = this.getSession();
            session.merge(obj);
            this.doFlush(session);
        }  catch (Throwable ex) {
            log.debug("== Error en updatePositions ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updatePositions/PositionsDAO ==");
        }

    }

    /**
     * Elimina un cargo del sistema
     * @param obj - Positions
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deletePositions(Position obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deletePositions/PositionsDAO ==");
        

        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("delete from ");
        	stringQuery.append(Position.class.getName());
        	stringQuery.append(" pos where pos.id = :aPosId");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("delete from " + Position.class.getName() + " pos where pos.id = :aPosId");
            query.setLong("aPosId", obj.getId());

            query.executeUpdate();
            this.doFlush(session);
        }  catch (Throwable ex) {
            log.debug("== Error en deletePositions ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deletePositions/PositionsDAO ==");
        }

    }

    /**
     * Obtiene todos los cargos existentes en el sistema
     * @return - List<Positions>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Position> getAllPositions() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllPositions/PositionsDAO ==");
        

        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("select p from ");
        	stringQuery.append(Position.class.getName());
        	stringQuery.append(" p");
        	return session.createQuery(stringQuery.toString()).list();
            //return session.createQuery("select p from " + Position.class.getName() + " p").list();
        }  catch (Throwable ex) {
            log.debug("== Error en getAllPositions ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllPositions/PositionsDAO ==");
        }
    }

    /**
     * Obtiene un cargo por el nombre especificado
     * @param positionName - String
     * @return Positions
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Position> getPositionsByName(String positionName) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getPositionsByName/PositionsDAO ==");
        
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Position.class.getName());
        	stringQuery.append(" p where lower(p.positionName) = :positionName");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + Position.class.getName() + " p where lower(p.positionName) = :positionName");
            query.setString("positionName", positionName.toLowerCase());

            return query.list();

        }  catch (Throwable ex) {
            log.debug("== Error en getPositionsByName ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getPositionsByName/PositionsDAO ==");
        }
    }

    /**
     * Obtiene un listado de Cargos de un Dealer especifico
     * @param dealerId - Long
     * @return - List<Positions>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Position> getPositionsByDealerId(Long dealerId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getPositionsByDealerId/PositionsDAO ==");
        
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("select p from ");
        	stringQuery.append(Position.class.getName());
        	stringQuery.append(" p where p.dealer.id = :dealerId");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("select p from " + Position.class.getName() + " p where p.dealer.id = :dealerId");
            query.setLong("dealerId", dealerId);

            return query.list();

        }  catch (Throwable ex) {
            log.debug("== Error en getPositionsByDealerId ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getPositionsByDealerId/PositionsDAO ==");

        }
    }

    /**
     * Obtiene un cargo por el nombre especificado y el dealer id
     * @param id - Long
     * @Param positionName - String
     * @return Positions
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Position getPositionsByNameAndDealerId(String positionName, Long dealerId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getPositionsByID/PositionsDAO ==");
        
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("select p from ");
        	stringQuery.append(Position.class.getName());
        	stringQuery.append(" p where lower(p.positionName) = :positionName and p.dealer.id = :dealerId");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("select p from " + Position.class.getName() + " p where lower(p.positionName) = :positionName and p.dealer.id = :dealerId");
            query.setString("positionName", positionName.toLowerCase());
            query.setLong("dealerId", dealerId);

            return (Position) query.uniqueResult();

        } catch (Throwable ex) {
            log.debug("== Error en getPositionsByNameAndDealerId ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getPositionsByID/PositionsDAO ==");
        }
    }

    /**
     * Obtiene un cargo de acuerdo con el codigo del mismo y al dealer especificado
     * @param code - String
     * @param id - Long
     * @return - Positions
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Position getPositionsByPositionCodeAndDealerId(String code, Long dealerId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getPositionsByPositionCode/PositionsDAO ==");
        

        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("select p from ");
        	stringQuery.append(Position.class.getName());
        	stringQuery.append(" p where p.positionCode = :positionCode and p.dealer.id = :dealerId");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("select p from " + Position.class.getName() + " p where p.positionCode = :positionCode and p.dealer.id = :dealerId");
            query.setString("positionCode", code);
            query.setLong("dealerId", dealerId);

            return (Position) query.uniqueResult();

        }  catch (Throwable ex) {
            log.debug("== Error en getPositionsByPositionCodeAndDealerId ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getPositionsByPositionCode/PositionsDAO ==");
        }
    }

	@SuppressWarnings("unchecked")
	public List<Position> getPositionsByCodeAndNameAndDealerId(String code, String name, Long dealerId) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getPositionsByCodeAndNameAndDealerId/PositionsDAO ==");
        
        try {
        	Session session = this.getSession();
            StringBuffer queryStr = new StringBuffer("from ");
            queryStr.append(Position.class.getName());
            
            boolean codeEmpty = code == null || code.trim().isEmpty();
            boolean nameEmpty = name == null || name.trim().isEmpty();
            boolean dealerIdEmpty = dealerId == null || dealerId <= 0L;
            boolean someParameter = !codeEmpty || !nameEmpty || !dealerIdEmpty;
            
            if(someParameter){
            	queryStr.append(" p where ");
            }
            
            if(!codeEmpty){
            	queryStr.append(" p.positionCode = :aPositionCode and");
            }
            if(!nameEmpty){
            	queryStr.append(" p.positionName = :aPositionName and");
            }
            if(!dealerIdEmpty){
            	queryStr.append(" p.dealer.id = :aDealerId and");
            }
            String finalQuery = queryStr.toString();
            if(someParameter){
            	finalQuery = StringUtils.removeEnd(finalQuery, " and");
            }
            
            Query query = session.createQuery(finalQuery);
            if(!codeEmpty){
            	query.setString("aPositionCode", code);
            }
            if(!nameEmpty){
            	query.setString("aPositionName", name);
            }
            if(!dealerIdEmpty){
            	query.setLong("aDealerId", dealerId);
            }
            
            return query.list();

        }  catch (Throwable ex) {
            log.debug("== Error en getPositionsByCodeAndNameAndDealerId ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getPositionsByCodeAndNameAndDealerId/PositionsDAO ==");

        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.PositionsDAOLocal#getAllPositionsByCountryId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<Position> getAllPositionsByCountryId(Long countryId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllPositionsByCountryId/PositionsDAO ==");
        
		if(countryId == null || countryId <= 0L){
        	return getAllPositions();
        }

        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Position.class.getName());
        	stringQuery.append(" p where p.country.id = :aCountryId");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + Position.class.getName() + " p where p.country.id = :aCountryId");
            query.setLong("aCountryId", countryId);
            
            return query.list();
        }  catch (Throwable ex) {
            log.debug("== Error en getAllPositionsByCountryId ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllPositionsByCountryId/PositionsDAO ==");
        }
	}
}
