package co.com.directv.sdii.common.enumerations;

import co.com.directv.sdii.business.CodesBusinessEntityHelper;
import co.com.directv.sdii.common.util.PropertiesReader;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.HelperException;
import co.com.directv.sdii.exceptions.PropertiesException;

/**
 * 
 * Clase de tipo Enum, encargada de realizar el mapping
 * de los codigos o id de las entidades de negocio, los cuales se
 * encuentran ubicados en un archivo de propiedades externo a la 
 * aplicacion.
 * 
 * Fecha de Creación: 26/03/2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see properties.CodesBusinessEntityEnum.properties
 */
public enum WorkManagerConfigEnum {

	TIME_ALLOCATOR("sdii_CODE_time_allocator","sdii_CODE_time_allocator"),
	TIME_MANAGER("sdii_CODE_time_manager","sdii_CODE_time_manager"),
	TIME_CORE_WO("sdii_CODE_time_core_wo","sdii_CODE_time_core_wo"),
	TIME_FILE_PROCESS("sdii_CODE_time_file_process","sdii_CODE_time_file_process"),
	JNDI_ALLOCATOR("sdii_CODE_jndi_allocator","sdii_CODE_jndi_allocator"),
	JNDI_TIME_ALLOCATOR("sdii_CODE_jndi_time_allocator","sdii_CODE_jndi_time_allocator"),
	JNDI_SCHEDULER_TASK_BUSSINESS("sdii_CODE_jndi_scheduler_task_bussiness","sdii_CODE_jndi_scheduler_task_bussiness")
    ;
	
    
    private String codeEntity;
    private String columnEntity;
   
    WorkManagerConfigEnum(String pCodeEntity,String pColumnEntity) {
        this.codeEntity = pCodeEntity;
        this.columnEntity = pColumnEntity;
    }
    
     /**
     *
     * Metodo: Retorna el codigo de la
     * entidad relacionada.
     * @return String codeEntity
     * @author Joan Lopez
     */
    public String getCodeEntity() throws PropertiesException{
    	PropertiesReader reader = null;
    
        reader = UtilsBusiness.getWorkManagerConfigProperties();
        String codeResource = reader.getKey(this.codeEntity);
        this.codeEntity = codeResource != null ? codeResource.trim() : this.codeEntity;
        
        return this.codeEntity;
    }
    
    /**
     * 
     * Metodo: Retorna el id de la entidad enviada
     * filtrando por el codigo.
     * Invoca una consulta sobre la base de datos
     * para obtener el id de la tabla segun 
     * la entidad.
     * @param pClassName String    
     * @return Long
     * @throws BusinessException 
     * @author Joan Lopez
     */
    public Long getIdEntity(String pClassName) throws HelperException,PropertiesException{
    	CodesBusinessEntityHelper helper;    
    	PropertiesReader reader = null;
    	
    	reader = UtilsBusiness.getWorkManagerConfigProperties();
        String codeResource = reader.getKey(this.codeEntity);
        this.codeEntity = codeResource != null ? codeResource : this.codeEntity;
        
        String columnName = reader.getKey(this.columnEntity);
        this.columnEntity = columnName != null ? columnName : this.columnEntity;
    	
    	helper = new CodesBusinessEntityHelper(pClassName, this.codeEntity,  this.columnEntity);    	
    	return helper.getIdEntityByCodeEntity();
    }
}
