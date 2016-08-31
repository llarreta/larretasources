package co.com.directv.sdii.ejb.business.config;
import java.util.List;

import javax.ejb.Remote;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.SchedulerTaskDTO;
import co.com.directv.sdii.model.pojo.SchedulerTask;
import co.com.directv.sdii.model.pojo.SchedulerTaskDetailsStatus;
import co.com.directv.sdii.model.pojo.SchedulerTaskStatus;
import co.com.directv.sdii.model.vo.SchedulerTaskVO;

import commonj.work.WorkManager;

@Remote
public interface SchedulerTaskBussinessRemote {

	/**
     * Crea un parametro del sistema
     * @param obj - SystemParameter
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createSchedulerTask(SchedulerTaskVO obj) throws BusinessException;

    /**
     * Obtiene un systemparameter con el id especificado
     * @param id - Long
     * @return - SystemParameter
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public SchedulerTaskVO getSchedulerTaskByID(Long id) throws BusinessException ;

    /**
     * Actualiza un systemparameter especificado
     * @param obj - SystemParameter
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateSchedulerTask(SchedulerTaskVO obj) throws  BusinessException ;
    
    /**
     * Actualiza un systemparameter especificado
     * @param obj - SystemParameter
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateSchedulerTask(SchedulerTaskDTO obj) throws BusinessException;

    /**
     * Elimina un systemparameter del sistema
     * @param obj - SystemParameter
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteSchedulerTask(SchedulerTaskVO obj) throws  BusinessException ;

    /**
     * Obtiene todos los systemparameters del sistema
     * @return - List<SystemParameter>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<SchedulerTask> getAll() throws  BusinessException ;
    
    /**
     * Obtiene una lista de los parámetros del sistema regionalizados
     * @param countryId identificador del país por el que se realizará el filtro
     * @return lista de parámetros del sistema regionalizados
     * @throws BusinessException en caso de error al tratar de obtener los parámetros del sistema
     * @author aharker
     */
    public List<SchedulerTask> getAllSchedulerTaskByCountryId(Long countryId) throws  BusinessException ;

    /**
     * Obtiene un SystemParameter de acuerdo al nombre especificado
     * @param code - String
     * @return - SystemParameter
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public SchedulerTaskVO getSchedulerTaskByCodeAndCountryId(String name, Long countryId) throws  BusinessException ;

	
	/**
	 * Metodo: Obtiene SystemParameter por Código de parámetro y país
	 * @param parameterCode - String
	 * @return SystemParameter
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<SchedulerTask> getSchedulerTaskByCode(String parameterCode)throws  BusinessException ;

	/**
	 * Metodo: Obtiene SystemParameter por Código de parámetro y país
	 * @param parameterCode - String
	 * @return SystemParameter
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<SchedulerTask> getSchedulerTaskByStateAndId(String state, Long id)throws  BusinessException ;
	
	/**
	 * Metodo: <Descripcion>
	 * @param state
	 * @param id
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public List<SchedulerTaskVO> getSchedulerTaskVOByStateAndId(String state,Long id) throws BusinessException;
	
	public SchedulerTaskStatus getSchedulerTaskStatusByCode(String statusCode)throws  BusinessException ;
	
	public void doWorkManager(WorkManager workManager, List<SchedulerTask> schedulerTask) throws BusinessException, PropertiesException;
	
	public SchedulerTaskDetailsStatus getSchedulerTaskDetailStatusByCode(String statusCode)	throws BusinessException;
	
	public Long beginWork(Long idSchedulerTask, Long countryId) throws BusinessException, PropertiesException ;
	
	public void finishWork(Long idSchedulerTaskDetail, String detail, boolean fail) throws BusinessException, PropertiesException;
	
}
