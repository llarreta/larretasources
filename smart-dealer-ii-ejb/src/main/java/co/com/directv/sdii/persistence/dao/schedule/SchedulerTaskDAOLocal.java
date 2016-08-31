package co.com.directv.sdii.persistence.dao.schedule;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.SchedulerTask;
import co.com.directv.sdii.model.pojo.SchedulerTaskDetails;
import co.com.directv.sdii.model.pojo.SchedulerTaskDetailsStatus;
import co.com.directv.sdii.model.pojo.SchedulerTaskStatus;

@Local
public interface SchedulerTaskDAOLocal {
	
	/**
     * Crea un parametro del sistema
     * @param obj - SystemParameter
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createSchedulerTask(SchedulerTask obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un systemparameter con el id especificado
     * @param id - Long
     * @return - SystemParameter
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public SchedulerTask getSchedulerTaskByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un systemparameter especificado
     * @param obj - SystemParameter
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateSchedulerTask(SchedulerTask obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un systemparameter del sistema
     * @param obj - SystemParameter
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteSchedulerTask(SchedulerTask obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los systemparameters del sistema
     * @return - List<SystemParameter>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<SchedulerTask> getAllSchedulerTask() throws DAOServiceException, DAOSQLException;
    
    /**
     * Obtiene una lista de los parámetros del sistema regionalizados
     * @param countryId identificador del país por el que se realizará el filtro
     * @return lista de parámetros del sistema regionalizados
     * @throws BusinessException en caso de error al tratar de obtener los parámetros del sistema
     * @author aharker
     */
    public List<SchedulerTask> getAllSchedulerTaskByCountryId(Long countryId) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un SystemParameter de acuerdo al nombre especificado
     * @param code - String
     * @return - SystemParameter
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public SchedulerTask getSchedulerTaskByCodeAndCountryId(String name, Long countryId) throws DAOServiceException, DAOSQLException;

	
	/**
	 * Metodo: Obtiene SystemParameter por Código de parámetro y país
	 * @param parameterCode - String
	 * @return SystemParameter
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<SchedulerTask> getSchedulerTaskByCode(String parameterCode)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene SystemParameter por Código de parámetro y país
	 * @param parameterCode - String
	 * @return SystemParameter
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<SchedulerTask> getSchedulerTaskForExecute(String status, Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: <Descripcion>
	 * @param statusCode
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public SchedulerTaskStatus getSchedulerTaskStatusByCode(String statusCode) throws DAOServiceException, DAOSQLException;
	
	public void createSchedulerTaskDetail(SchedulerTaskDetails obj) throws DAOServiceException, DAOSQLException;
	
	public SchedulerTaskDetailsStatus getSchedulerTaskDetailStatusByCode(String statusCode)throws DAOServiceException, DAOSQLException ;
	
	public Long finishSchedulerTaskDetail(Long id, String detail, boolean fail)throws DAOServiceException, DAOSQLException;
}
