package co.com.directv.sdii.common.util;

/**
 *
 * Clase para manejo de Constantes de la logica
 * de negocio
 *
 * Fecha de Creación: Mar 2, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see
 */
public abstract class Constantes {
    /**
     * Archivo de Recursos internos de la aplicación
     */

    public static final String RESOURCE_APP = "/properties/applicationSD.properties";
    
    /**
     * Archivo que contiene la informacion de la version actual de la capa de negocio
     */
    public static final String VERSION_BUSINESS_TIER = "versionBusinessTier";

    /**
     * Archivo de Recursos externos de la aplicación
     */
    public static final String RESOURCE_CONFIGURATION = "configuracionSD.properties";
    
    /**
     * Archivo de Recursos externos de la configuracion de work manager (job)
     */
    public static final String RESOURCE_WORK_MANAGER_CONFIG = "rutaWorkManagerConfig";


    /**
     * Ruta log4j.xml
     */
    public static final String LOG_APP = "log4jConfigFile";

    /**
     * Ruta propiedades de la aplicación
     */
    public static final String PROPERTIES_APP = "rutaProperties";

    /**
     * Etiqueta con el nombre de la ruta del archivo
     * BusinessMessages dentro del archivo de propiedades
     * externas(applicationSD.properties).
     */
    public static final String LABEL_RUTA_BUSINESS_MESSAGES = "rutaBusinessMessages";

     /**
     * Etiqueta con el nombre de la ruta del archivo
     * log4j.xml dentro del archivo de propiedades
     * externas(applicationSD.properties).
     */
    public static final String LABEL_RUTA_LOG4J = "log4jConfigFile";

     /**
     * Etiqueta con el nombre de la ruta del archivo
     * CodesBusinessEntity dentro del archivo de propiedades
     * externas(applicationSD.properties).
     */
    public static final String LABEL_RUTA_CODES_BUSINESS_ENTITY = "rutaCodesBusinessEntity";
    
    /**
     * Etiqueta con el nombre de la ruta del archivo
     * EJBRemoteJNDIName dentro del archivo de propiedades
     * externas(applicationSD.properties).
     */
    public static final String LABEL_RUTA_EJB_REMOTE_JNDI_NAME = "rutaEJBRemoteJNDIName";
    
    /**
     * Etiqueta con el nombre de la ruta del archivo
     * de reportes
     */
    public static final String LABEL_RUTA_REPORTS = "rutaReports";
    
    /**
     * Etiqueta con el nombre de la ruta del archivo
     * de reportes temporales
     */
    public static final String LABEL_RUTA_REPORTS_TEMP = "rutaReportsTemp";
    
    public static final String LABEL_RUTA_REPORTS_RELATIVE_TEMP = "rutaReportsRelativeTemp";
    
    /**
     * Etiqueta con el nombre de la ruta del archivo
     * de WSDLLOCATIONS
     */
    public static final String LABEL_RUTA_WSDL_LOCATIONS = "rutaWsdlLocations";
    
    /**
     * Etiqueta con el nombre de la ruta del archivo
     * de CONFIGURACIÓN DE JOBS
     */
    public static final String LABEL_RUTA_JOBS_CONFIG = "rutaJobConfiguration";
    
    /**
     * Etiqueta con el nombre de la ruta del archivo
     * de CONFIGURACIÓN DE JOBS
     */
    public static final String LABEL_RUTA_SCHEDULER_CONFIG = "rutaSchedulerConfiguration";
    
    /**
     * Etiqueta con el nombre de la ruta del archivo
     * de CONFIGURACIÓN DE JOBS
     */
    public static final String LABEL_TIMEOUT_IBS = "timeOutIBS";
    
    /**
     * Nombre del recurso JNDI a ser usado por la aplicación
     */
    public static final String JNDI_DATA_SOURCE_NAME = "jndiDataSourceName";   
    
    /**
     * Etiqueta con la version de la capa de negocio
     */
    public static final String LABEL_VERSION = "version";
    
    /**
     * Etiqueta con la fecha de la version de la capa de negocio
     */
    public static final String LABEL_VERSION_DATE = "fecha";
    
    /**
     * Etiqueta con el nombre de la ruta del archivo
     * de JMSLOCATIONS
     */
    public static final String LABEL_RUTA_JMS_LOCATION = "rutaJmsLocations";
    
    public static final String ISO_LATIN_ENCODING = "ISO-8859-1";
    
    /**
     * ruta de los archivos properties empleados para la internacionalizacion
     * de la aplicacion
     * 
     */
    public static final String PROPERTIES_APP_I18N = "rutaPropertiesi18n";
  
    /**
     * ruta de los archivos properties empleados para la internacionalizacion
     * de la aplicacion
     * 
     */
    public static final String BASE_NAME_APPLICATION_TEXT = "baseNameApplicationText";    
    
    /**
     * Base name de las properties que contienen las variables de todos los reportes
     * internacionalizados.
     * 
     */
    public static final String BASE_NAME_REPORT = "baseNameReport";
    
    public static final int BATCH_INSERT_SIZE = 1000;
    
}

