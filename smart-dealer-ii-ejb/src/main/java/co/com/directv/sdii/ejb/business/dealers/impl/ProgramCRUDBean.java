package co.com.directv.sdii.ejb.business.dealers.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.dealers.ProgramCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Program;
import co.com.directv.sdii.model.pojo.ProgramStatus;
import co.com.directv.sdii.model.vo.ProgramVO;
import co.com.directv.sdii.persistence.dao.config.ProgramDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WoAssignmentDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * Session Bean implementation class ProgramCRUDBean
 * @author jcasas
 * 
 */
@Stateless(name="ProgramCRUDBeanLocal",mappedName="ejb/ProgramCRUDBeanLocal")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ProgramCRUDBean extends BusinessBase implements ProgramCRUDBeanLocal {

	@EJB(name="ProgramDAOLocal", beanInterface=ProgramDAOLocal.class)
	private ProgramDAOLocal programDAO;
	
	@EJB(name="WoAssignmentDAOLocal", beanInterface=WoAssignmentDAOLocal.class)
	private WoAssignmentDAOLocal woAssignmentDao;
	
	
    private final static Logger log = UtilsBusiness.getLog4J(ProgramCRUDBean.class);
	

    /**
     * Obtiene todos los programas del sistema
     * @return - Listado con los programas que existen en el sistema
     * @throws BusinessException
     */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ProgramVO> getAll() throws BusinessException {
		log.debug("== Inicia getAll/ProgramCRUDBean ==");
		try {

           List<Program> programs = programDAO.getAll();
           if(programs == null || programs.size()==0)
        	   return null;
           
           return UtilsBusiness.convertList(programs, ProgramVO.class);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getAll/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAll/ProgramCRUDBean ==");
        }
	}

	
    /**
     * Obtiene todos los programas de un dealer especifico, adicional a esto, se retorna cada una de las cargas
     * que posee cada programa
     * @param - Id de la compa�ia
     * @param - getAll true si los consulta todos - false si solo los activos
     * @return - Listado con los programas pertenecientes a una compa�ia especifica
     * @throws BusinessException
     * @author jcasas
     */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ProgramVO> getProgramsByDealerId(Long dealerId,boolean getAll)throws BusinessException {
		log.debug("== Inicia getProgramsByDealerId/ProgramCRUDBean ==");
		try {

		   UtilsBusiness.assertNotNull(dealerId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
           List<ProgramVO> listProgramVO = new ArrayList<ProgramVO>();
           Long statusId = null;
           if(!getAll)
        	   statusId = CodesBusinessEntityEnum.PROGRAM_STATUS_ACTIVE.getIdEntity( ProgramStatus.class.getName() );
           
           List<Program>programs = programDAO.getProgramsRegisteredByDealerId( dealerId ,statusId);
           
           for (Program programId : programs) {
        	   log.info( ">>>>>>>>>>>--> "+programId );
        	   ProgramVO programa = UtilsBusiness.copyObject(ProgramVO.class, programDAO.getProgramByID( programId.getId() ));
        	   programa.setLoadWo( programDAO.getTotalProgramsByDealerAndProgram(dealerId, programId.getId()).longValue() );
        	   listProgramVO.add( programa );
           }
          
           return listProgramVO;

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getProgramsByDealerId/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getProgramsByDealerId/ProgramCRUDBean ==");
        }
	}

    /**
     * Obtiene todos los programs por nombre y dealer especifico
     * @param dealerId - Id de la compa�ia 
     * @param name - Nombre del programa
     * @return - Listado con los programas que pertenecen a un dealer y nombre especifico 
     * @throws BusinessException
     * @author jcasas
     */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ProgramVO> getProgramsByNameAndDealerId(Long dealerId, String name) throws BusinessException {
		log.debug("== Inicia getProgramsByNameAndDealerId/ProgramCRUDBean ==");
		try {

		   UtilsBusiness.assertNotNull(dealerId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		   UtilsBusiness.assertNotNull(name, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
		   List<Object[]> programs = programDAO.getProgramsByNameAndDealerId(dealerId, name);
           if(programs == null || programs.size()==0)
        	   return null;
           
           List<ProgramVO> listProgramVO = new ArrayList<ProgramVO>();
           
           for (Object[] obj : programs) {
        	   ProgramVO programVO = UtilsBusiness.copyObject(ProgramVO.class, (Program)obj[0]);
        	   programVO.setLoadWo((Long)obj[1]);
        	   listProgramVO.add(programVO);
           }
           return listProgramVO;
           
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getProgramsByNameAndDealerId/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getProgramsByNameAndDealerId/ProgramCRUDBean ==");
        }
	}

    /**
     * Crea una compa�ia en el sistema
     * @param obj - Objeto con la informacion basica del programa
     * @throws BusinessException
     */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createProgram(ProgramVO obj) throws BusinessException {
		log.debug("== Inicia createProgram/ProgramCRUDBean ==");
		try {
			synchronized (programDAO) {
				UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
				   
				   if(!BusinessRuleValidationManager.getInstance().isValid(obj)){
		           	throw new BusinessException( ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() );
		           }
				   
				   List<Program> bdPrograms = programDAO.getProgramsByNameExactAndDealerId(obj.getProgramName(),obj.getDealerId());
				   if( bdPrograms != null && !bdPrograms.isEmpty() ){
					   throw new BusinessException(ErrorBusinessMessages.PROGRAM_ALREADY_EXIST.getCode(), ErrorBusinessMessages.PROGRAM_ALREADY_EXIST.getMessage());
				   }
				   //jnova 21-07-2011 Se autogenera el codigo del programa, por esto se quita validacion
				   /*Program oldProgram = programDAO.getProgramsByCode(obj.getProgramCode());
				   
				   if(oldProgram != null){
					   throw new BusinessException(ErrorBusinessMessages.PROGRAM_ALREADY_EXIST.getCode(), ErrorBusinessMessages.PROGRAM_ALREADY_EXIST.getMessage());
				   }*/
				   //Se autogenera codigo de programa
				   Long maxId = this.programDAO.getMaxProgramID();
				   maxId = maxId.longValue() + 1;
				   if( maxId == null )
					   maxId = 1L;
				   else
					   maxId++;
				   Program program = UtilsBusiness.copyObject(Program.class, obj);
				   program.setProgramCode( maxId.toString() );
				   this.programDAO.createProgram(program);
			}
		   
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion createProgram/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createProgram/ProgramCRUDBean ==");
        }
	}

	 /**
     * Elimina un program del sistema
     * @param obj - Objeto con la informacion basica del programa
     * @throws BusinessException
     */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteProgram(ProgramVO obj) throws BusinessException {
		log.debug("== Inicia deleteProgram/ProgramCRUDBean ==");
		try {
		   UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		   
		   Program program = UtilsBusiness.copyObject(Program.class, obj);
		   Long woAssignmentCount = woAssignmentDao.countWoAssignmenstByProgramId(obj.getId());
		   if(woAssignmentCount.longValue() > 0){
			   throw new BusinessException(ErrorBusinessMessages.PROGRAM_HAS_ASSOCIATED_DATA.getCode(), ErrorBusinessMessages.PROGRAM_HAS_ASSOCIATED_DATA.getMessage());
		   }
		   this.programDAO.deleteProgram(program);
		   
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion deleteProgram/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteProgram/ProgramCRUDBean ==");
        }
	}

	 /**
     * Obtiene un program con el id especificado
     * @param idProgram - Id del programa a consultar
     * @return - Objeto con la informacion basica del Programa consultado
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ProgramVO getProgramByID(Long id) throws BusinessException {
		log.debug("== Inicia deleteProgram/ProgramCRUDBean ==");
		try {
		   
		   UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		   
		   Program program = this.programDAO.getProgramByID(id); 
		   
		   if(program == null)
			   return null;
		   
		   return UtilsBusiness.copyObject(ProgramVO.class, program);
		   
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getProgramByID/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteProgram/ProgramCRUDBean ==");
        }
	}

	 /**
     * Obtiene un programa con el codigo especificado
     * @param programCode - Codigo del programa a consultar
     * @return Programa con el codigo especificado
     * @throws BusinessException
     */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ProgramVO getProgramsByCode(String code) throws BusinessException {
		log.debug("== Inicia getProgramsByCode/ProgramCRUDBean ==");
		try {
		   
		   if(code == null || "".equals(code)){
			   log.debug("== Parametro code no especificado ==");
			   throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		   }
		   
		   Program program = this.programDAO.getProgramsByCode(code); 
		   
		   if(program == null){
			   return null;
		   }
		   
		   return UtilsBusiness.copyObject(ProgramVO.class, program);
		   
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getProgramsByCode/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getProgramsByCode/ProgramCRUDBean ==");
        }
	}

    /**
     * Obtiene todos los programs por nombre  
     * @param name - Nombre del programa
     * @return - Listado con los programas con el nombre especifico 
     * @throws BusinessException
     * @author jcasas
     */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ProgramVO> getProgramsByName(String name) throws BusinessException {
		log.debug("== Inicia getProgramsByName/ProgramCRUDBean ==");
		try {
		   
		   if(name == null || "".equals(name)){
			   log.debug("== Parametro name no especificado ==");
			   throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		   }
		   
		   List<Program> programs = this.programDAO.getProgramsByName(name); 
		   
		   if(programs == null){
			   return null;
		   }
		   
		   return UtilsBusiness.convertList(programs, ProgramVO.class);
		   
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getProgramsByName/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getProgramsByName/ProgramCRUDBean ==");
        }
	}

	/**
     * Obtiene un listado de programas con el estado especificado
     * @param statusId - Id del status de un programa
     * @return Listado de programas con el estado especificado
     * @throws BusinessException
     */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ProgramVO> getProgramsByStatusId(Long statusId) throws BusinessException {
		log.debug("== Inicia getProgramsByStatusId/ProgramCRUDBean ==");
		try {
		   
		  UtilsBusiness.assertNotNull(statusId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		   
		   List<Program> programs = this.programDAO.getProgramsByStatusId(statusId); 
		   
		   if(programs == null)
			   return null;
		   
		   return UtilsBusiness.convertList(programs, ProgramVO.class);
		   
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getProgramsByStatusId/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getProgramsByStatusId/ProgramCRUDBean ==");
        }
	}

	/**
     * Actualiza un program especificado
     * @param obj - Objeto con la informacion basica del programa
     * @throws BusinessException
     */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateProgram(ProgramVO obj) throws BusinessException {
		log.debug("== Inicia deleteProgram/ProgramCRUDBean ==");
		try {
		   UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		   
		   if(!BusinessRuleValidationManager.getInstance().isValid(obj)){
           	throw new BusinessException( ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() );
           }
		   
		   Program program = UtilsBusiness.copyObject(Program.class, obj);
		   this.programDAO.deleteProgram(program);
		   
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion updateProgram/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteProgram/ProgramCRUDBean ==");
        }	
	}	
}
