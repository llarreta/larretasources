package co.com.directv.sdii.persistence.dao.config.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Program;
import co.com.directv.sdii.model.pojo.WoAssignment;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.ProgramDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de Program
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jcasas <a href="mailto:jcasas@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Program
 * @see co.com.directv.sdii.persistence.dao.config.ProgramDAOLocal
 */
@Stateless(name="ProgramDAOLocal",mappedName="ejb/ProgramDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ProgramDAO extends BaseDao implements ProgramDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ProgramDAO.class);


    /**
     * Crea un Program en el sistema
     * @param obj - Objeto con la informacion basica del programa
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createProgram(Program obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createProgram/DAOProgramBean ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el Program ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina createProgram/DAOProgramBean ==");
        }
    }

    /**
     * Obtiene un program con el id especificado
     * @param idProgram - Id del programa a consultar
     * @return - Objeto con la informacion basica del Programa consultado
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Program getProgramByID(Long idProgram) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getProgramByID/DAOProgramBean ==");
        Session session = super.getSession();
        try {
            StringBuffer bufferSql = new StringBuffer();
            bufferSql.append("select program from ");
            bufferSql.append(Program.class.getName());
            bufferSql.append(" program where program.id = :id");
            
            Query query = session.createQuery(bufferSql.toString());
            query.setLong("id", idProgram);
            
            Object obj = query.uniqueResult();
            if (obj != null) {
                return (Program) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error consultando el Program por ID ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getProgramByID/DAOProgramBean ==");
        }
    }

    /**
     * Actualiza un program especificado
     * @param obj - Objeto con la informacion basica del programa
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateProgram(Program obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateProgram/DAOProgramBean ==");
        Session session = super.getSession();

        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el Program ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina updateProgram/DAOProgramBean ==");
        }

    }

    /**
     * Elimina un program del sistema
     * @param obj - Objeto con la informacion basica del programa
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteProgram(Program obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteProgram/DAOProgramBean ==");
        Session session = super.getSession();

        try {
            StringBuffer bufferSql = new StringBuffer();
            bufferSql.append("delete from ");
            bufferSql.append(Program.class.getName());
            bufferSql.append(" prog where prog.id = :id");
            
            Query query = session.createQuery(bufferSql.toString());
            query.setLong("id", obj.getId());
            
            query.executeUpdate();
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error borrando el Program ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina deleteProgram/DAOProgramBean ==");
        }

    }

    /**
     * Obtiene todos los programas del sistema
     * @return - Listado con los programas que existen en el sistema
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Program> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/DAOProgramBean ==");
        Session session = super.getSession();

        try {
            Query query = session.createQuery("from Program");
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error borrando el Program ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getAll/DAOProgramBean ==");
        }
    }

    /**
     * Obtiene todos los programas de un dealer especifico, adicional a esto, se retorna cada una de las cargas
     * que posee cada programa
     * @param - Id de la compa�ia
     * @return - Listado con los programas pertenecientes a una compa�ia especifica
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author jcasas
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Object[]> getProgramsByDealerId(Long dealerId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getProgramsByDealerId/DAOProgramBean ==");
        Session session = super.getSession();

        try {
           StringBuffer bufferSql = new StringBuffer();
           bufferSql.append("select prog, (select count(wo.id) from "); 
           bufferSql.append(WoAssignment.class.getName()); 
           bufferSql.append(" woa inner join woa.workOrder as wo where woa.program.id = prog.id and wo.workorderStatusByActualStatusId.woStateCode = :statusCode) from ");
           bufferSql.append(Program.class.getName());
           bufferSql.append(" prog where prog.dealerId = :dealerId");
           
           Query query = session.createQuery(bufferSql.toString());
           log.debug("Buscando program con dealerId: "+dealerId+" y statusCode: "+CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
           query.setLong("dealerId", dealerId);
           query.setString("statusCode", CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());

           return query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultando el Program ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getProgramsByDealerId/DAOProgramBean ==");
        }
    }
    
    /**
     * Obtiene todos los programs por nombre y dealer especifico
     * @param dealerId - Id de la compa�ia 
     * @param name - Nombre del programa
     * @return - Listado con los programas que pertenecen a un dealer y nombre especifico 
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author jcasas
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Object[]> getProgramsByNameAndDealerId(Long dealerId, String name) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getProgramsByNameAndDealerId/DAOProgramBean ==");
        Session session = super.getSession();

        try {
           StringBuffer bufferSql = new StringBuffer();
           bufferSql.append(" select prog, (select count(wo.id) from "); 
           bufferSql.append(WoAssignment.class.getName()); 
           bufferSql.append(" woa inner join woa.workOrder as wo where woa.program.id = prog.id and wo.workorderStatusByActualStatusId.woStateCode = :statusCode) from ");
           bufferSql.append(Program.class.getName());
           bufferSql.append(" prog where prog.dealerId = :dealerId ");
           bufferSql.append(" and lower(prog.programName) LIKE '%'||:name||'%' ");

           Query query = session.createQuery(bufferSql.toString());
           query.setLong("dealerId", dealerId);
           query.setString("statusCode", CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
           query.setString("name", name.toLowerCase());
           
           return query.list();
           
        } catch (Throwable ex) {
            log.debug("== Error consultando el Program ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getProgramsByNameAndDealerId/DAOProgramBean ==");
        }
    }
    
    /**
     * Obtiene un listado de programas con el estado especificado
     * @param statusId - Id del status de un programa
     * @return Listado de programas con el estado especificado
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Program> getProgramsByStatusId(Long statusId) throws DAOServiceException, DAOSQLException{
    	log.debug("== Inicia getProgramsByStatusId/DAOProgramBean ==");
        Session session = super.getSession();

        try {
           StringBuffer bufferSql = new StringBuffer();
           bufferSql.append("from ");
           bufferSql.append(Program.class.getName());
           bufferSql.append(" prog where prog.programStatus.id = :statusId");
           
           Query query = session.createQuery(bufferSql.toString());
           query.setLong("statusId", statusId);
           
           return query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultando el Program ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getProgramsByStatusId/DAOProgramBean ==");
        }
    }
    
    /**
     * Obtiene un programa con el codigo especificado
     * @param programCode - Codigo del programa a consultar
     * @return Programa con el codigo especificado
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Program getProgramsByCode(String programCode) throws DAOServiceException, DAOSQLException{
    	log.debug("== Inicia getProgramsByCode/DAOProgramBean ==");
        Session session = super.getSession();

        try {
           StringBuffer bufferSql = new StringBuffer();
           bufferSql.append("from ");
           bufferSql.append(Program.class.getName());
           bufferSql.append(" prog where prog.programCode = :programCode");
           
           Query query = session.createQuery(bufferSql.toString());
           query.setString("programCode", programCode);
           
           Program program = (Program)query.uniqueResult();
           return program;
        } catch (Throwable ex) {
            log.debug("== Error consultando el Program ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getProgramsByCode/DAOProgramBean ==");
        }
    }
    
    /**
     * Obtiene todos los programs por nombre  
     * @param name - Nombre del programa
     * @return - Listado con los programas con el nombre especifico 
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author jcasas
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Program> getProgramsByName(String name) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getProgramsByName/DAOProgramBean ==");
        Session session = super.getSession();

        try {

           StringBuffer bufferSql = new StringBuffer();
           bufferSql.append("from ");
           bufferSql.append(Program.class.getName());
           bufferSql.append(" prog where lower(prog.programName) LIKE '%'||:name||'%'");
           
           Query query = session.createQuery(bufferSql.toString());
           query.setString("name", name.toLowerCase());

           return query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultando el Program ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getProgramsByName/DAOProgramBean ==");
        }
    }
    
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Program> getProgramsByNameExact(String name) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getProgramsByName/DAOProgramBean ==");
        Session session = super.getSession();

        try {

           StringBuffer bufferSql = new StringBuffer();
           bufferSql.append("from ");
           bufferSql.append(Program.class.getName());
           bufferSql.append(" prog where prog.programName =:name");
           
           Query query = session.createQuery(bufferSql.toString());
           query.setString("name", name);

           return query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultando el Program ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getProgramsByName/DAOProgramBean ==");
        }
    }


	@Override
	public List<Program> getProgramsRegisteredByDealerId(Long dealerId,Long statusId)throws DAOServiceException, DAOSQLException {
		   log.debug("== Inicia getProgramsRegisteredByDealerId/DAOProgramBean ==");
	       Session session = super.getSession();

	        try {
	           StringBuffer bufferSql = new StringBuffer();
	           bufferSql.append( "select distinct prog from " );
	           bufferSql.append( Program.class.getName() );
	           bufferSql.append( " prog where prog.dealerId = :paramId" );
	           if( statusId != null ){
	        	   bufferSql.append( " and prog.programStatus.id = :statusId" );
	           }
	           
	           Query query = session.createQuery( bufferSql.toString() );
	           query.setParameter("paramId",dealerId);
	           if( statusId != null ){
	        	   query.setParameter("statusId",statusId);
	           }

	           return query.list();
	        } catch (Throwable ex) {
	            log.debug("== Error consultando el Program ==");
	            throw super.manageException(ex);
	        }finally {
	            log.debug("== Termina getProgramsRegisteredByDealerId/DAOProgramBean ==");
	        }
	}

	@Override
	public Integer getTotalProgramsByDealerAndProgram(Long dealerId, Long programId)
			throws DAOServiceException, DAOSQLException {
		   log.debug("== Inicia getTotalProgramsByDealerAndProgram/DAOProgramBean ==");
	       Session session = super.getSession();

	        try {
	           StringBuffer bufferSql = new StringBuffer();
	           bufferSql.append( "SELECT COUNT(*) FROM WORK_ORDER_ASSIGNMENTS WHERE DEALER_ID = :paramDealer AND PROGRAM_ID = :paramProgram" );
	           bufferSql.append( " AND IS_ACTIVE = :activeStatus" );
	           Query query = session.createSQLQuery( bufferSql.toString() );
	           query.setLong("paramDealer",dealerId );
	           query.setLong("paramProgram",programId );
	           query.setString("activeStatus",CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity() );
	           return ((BigDecimal)query.uniqueResult()).intValue();
	           
	        } catch (Throwable ex) {
	            log.debug("== Error consultando el Program ==");
	            throw super.manageException(ex);
	        }finally {
	            log.debug("== Termina getTotalProgramsByDealerAndProgram/DAOProgramBean ==");
	        }
	
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.ProgramDAOLocal#getProgramsByDealerIdAndStatusCode(java.lang.Long, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Program> getProgramsByDealerIdAndStatusCode(Long dealerId,String statusCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getProgramsByDealerIdAndStatusCode/DAOProgramBean ==");
		Session session = super.getSession();

		try {
			boolean isDealerId = dealerId != null && dealerId.longValue() > 0;
			StringBuffer bufferSql = new StringBuffer();
			bufferSql.append("select distinct prog from ");
			bufferSql.append(Program.class.getName());
			bufferSql.append(" prog where prog.programStatus.programStatusCode = :statusCode ");

			if( isDealerId ){
				bufferSql.append("and prog.dealerId = :dealerId");
			}

			Query query = session.createQuery(bufferSql.toString());
			query.setString("statusCode", statusCode);
			if( isDealerId ) {
				query.setLong("dealerId", dealerId);
			}
			return query.list();
		} catch (Throwable ex) {
			log.debug("== Error getProgramsByDealerIdAndStatusCode/DAOProgramBean ==",ex);
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getProgramsByDealerIdAndStatusCode/DAOProgramBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.ProgramDAOLocal#getMaxProgramID()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public Long getMaxProgramID() throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getMaxProgramID/DAOProgramBean ==");
		Session session = super.getSession();
		try {
			StringBuffer bufferSql = new StringBuffer();
			bufferSql.append("SELECT MAX(TO_NUMBER(PROG.PROGRAM_CODE)) FROM ");
			bufferSql.append(" WORK_ORDER_PROGRAMS ");
			bufferSql.append(" PROG ");
			Query query = session.createSQLQuery(bufferSql.toString());
			BigDecimal max =  (BigDecimal) query.uniqueResult();
			return (max == null? 1L: max.longValue());
		} catch (Throwable ex) {
			log.debug("== Error getMaxProgramID/DAOProgramBean ==",ex);
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getMaxProgramID/DAOProgramBean ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.ProgramDAOLocal#getProgramsByDealerIdsAndStatusCode(java.util.List, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Program> getProgramsByDealerIdsAndStatusCode(List<Long> dealerIds ,String statusCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getProgramsByDealerIdsAndStatusCode/DAOProgramBean ==");
		Session session = super.getSession();

		try {
			boolean isDealerId = dealerIds != null && !dealerIds.isEmpty();
			StringBuffer bufferSql = new StringBuffer();
			bufferSql.append("select distinct prog from ");
			bufferSql.append(Program.class.getName());
			bufferSql.append(" prog where prog.programStatus.programStatusCode = :statusCode ");

			if( isDealerId ){
				bufferSql.append("and prog.dealerId in ("+ UtilsBusiness.longListToString(dealerIds, ",") +") ");
			}

			Query query = session.createQuery(bufferSql.toString());
			query.setString("statusCode", statusCode);
			
			return query.list();
		} catch (Throwable ex) {
			log.debug("== Error getProgramsByDealerIdsAndStatusCode/DAOProgramBean ==",ex);
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getProgramsByDealerIdsAndStatusCode/DAOProgramBean ==");
		}
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Program> getProgramsByNameExactAndDealerId(String name,Long dealerId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getProgramsByNameExactAndDealerId/DAOProgramBean ==");
        Session session = super.getSession();

        try {

           StringBuffer bufferSql = new StringBuffer();
           bufferSql.append("from ");
           bufferSql.append(Program.class.getName());
           bufferSql.append(" prog where prog.programName =:name");
           bufferSql.append(" and prog.dealerId =:dealerId");
           
           Query query = session.createQuery(bufferSql.toString());
           query.setString("name", name);
           query.setLong("dealerId", dealerId);

           return query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultando el getProgramsByNameExactAndDealerId ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getProgramsByNameExactAndDealerId/DAOProgramBean ==");
        }
    }

}
