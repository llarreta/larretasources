package co.com.directv.sdii.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import net.sf.jasperreports.engine.JRParameter;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.enumerations.WorkManagerConfigEnum;
import co.com.directv.sdii.exceptions.BusinessDetailException;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.HelperException;
import co.com.directv.sdii.exceptions.Log4jLoggerException;
import co.com.directv.sdii.exceptions.PDFException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.WorkOrderCSRDTO;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.SdiiTimeZone;
import co.com.directv.sdii.model.pojo.ServiceHour;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.WoPdfAnnex;
import co.com.directv.sdii.model.pojo.WorkOrderCSR;
import co.com.directv.sdii.model.pojo.WorkorderCSRStatus;
import co.com.directv.sdii.model.pojo.WorkorderReason;
import co.com.directv.sdii.model.pojo.WorkorderStatus;
import co.com.directv.sdii.model.vo.DomainVO;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.core.WoPdfAnnexDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.reports.dto.FileResponseDTO;
import co.com.directv.sdii.reports.dto.ReportWorkOrderDTO;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.sshtools.j2ssh.SftpClient;
import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import com.sshtools.j2ssh.transport.IgnoreHostKeyVerification;

/**
 * @author Alex Eduardo Quitiaquez Esquivel
 * 
 */
public final class UtilsBusiness {

	private UtilsBusiness() {
	}

	private final static Logger log = UtilsBusiness
			.getLog4J(UtilsBusiness.class);
	private static final Pattern patronEmail = Pattern
			.compile("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$");
	private static final Pattern patronCelular = Pattern
			.compile("^[3]{1}\\d{2}[2-8]{1}\\d{6}$");
	public static final String DATE_FORMAT = "MM/dd/yyyy";
	public static final String DATE_FORMAT_YYYYMMDD = "YYYYMMDD";
	public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_DDMMYYYYHHMMSS = "dd/MM/yyyy HH:mm:ss";
	public static final String DATE_FORMAT_YYMMDDHHMMSS = "HHmmssSSS";
	public static final String DATE_FORMAT_DDMMYYYY = "dd/MM/yyyy";
	private static DecimalFormat decimalFormat = new DecimalFormat(
			"#.#######################");
	public static final String TIME_MANAGER = "TIME_MANAGER";
	public static final String TIME_ALLOCATOR = "TIME_ALLOCATOR";
	
	private static final String INIT_CLAUSE = " EXISTS  (SELECT YY.COLUMN_VALUE FROM ( ";
	private static final String ODCI_NUMBER_LIST_FUNCTION_INIT_CLAUSE = " SELECT * FROM TABLE(sys.odcinumberlist( ";
	private static final String ODCI_NUMBER_LIST_FUNCTION_MIDDLED_END_CLAUSE = " ) ) ";
	private static final String UNION_CLAUSE = " UNION ";
	private static final String LAST_CLAUSE = " )))  YY  WHERE YY.COLUMN_VALUE = :queryParameter ) ";
	private static final int MAX_PARAM_FUNCTION_LENGTH = 1000;

	// @EJB(name="SystemParameterDAOLocal",
	// beanInterface=SystemParameterDAOLocal.class)
	// private static SystemParameterDAOLocal systemParameterDAO;

	/**
	 * Convierte una lista de un tipo T en una lista de otro tipo U siempre y
	 * cuando los tipos de los objetos sean compatibles y se puedan copiar sus
	 * atributos El tipo de dato U de retorno debe tener constructor por defecto
	 * 
	 * @param <T>
	 *            Tipo de dato de los objetos que contiene la lista origen
	 * @param <U>
	 *            Tipo de dato de los objetos que contendr� la lista destino
	 * @param listaOrigen
	 *            Lista que contiene los objetos que ser�n convertidos al otro
	 * @param instanciaTipoRetorno
	 *            Instancia nueva del tipo de dato de la lista destino, se usar�
	 *            para copiar los datos de cada objeto de la lista origen y
	 *            adicionarlo a la lista destino
	 * @return ListaDestino de objetos tipo U
	 * @throws BusinessException
	 */
	public static <T, U extends T> List<U> convertList(List<T> listaOrigen,
			Class<U> instanciaTipoRetorno) throws BusinessException {
		List<U> listaDestino = new ArrayList<U>();
		try {
			for (T entidad : listaOrigen) {
				U instancia = instanciaTipoRetorno.newInstance();
				BeanUtils.copyProperties(instancia, entidad);
				listaDestino.add(instancia);
			}
		} catch (IllegalAccessException e) {
			log.error("IllegalAccessException en funci�n convertList", e);
			throw new BusinessException(
					"Error copiando objetos en convertList", e);
		} catch (InvocationTargetException e) {
			log.error("InvocationTargetException en funci�n convertList", e);
			throw new BusinessException(
					"Error copiando objetos en convertList", e);
		} catch (InstantiationException e) {
			log.error(
					"InstantiationException en funci�n convertList, probablemente falte el constructor por defecto para el tipo destino",
					e);
			throw new BusinessException(
					"Error copiando objetos en convertList", e);
		}
		return listaDestino;

	}

	/**
	 * Convierte una lista de un tipo T en una lista de otro tipo U siempre y
	 * cuando los tipos de los objetos sean compatibles y se puedan copiar sus
	 * atributos El tipo de dato U de retorno debe tener constructor por defecto
	 * 
	 * @param <T>
	 *            Tipo de dato de los objetos que contiene la lista origen
	 * @param <U>
	 *            Tipo de dato de los objetos que contendr� la lista destino
	 * @param listaOrigen
	 *            Lista que contiene los objetos que ser�n convertidos al otro
	 * @param instanciaTipoRetorno
	 *            Instancia nueva del tipo de dato de la lista destino, se usar�
	 *            para copiar los datos de cada objeto de la lista origen y
	 *            adicionarlo a la lista destino
	 * @return ListaDestino de objetos tipo U
	 * @throws BusinessException
	 */
	public static <T, U> List<U> convertListWithoutExtends(List<T> listaOrigen,
			Class<U> instanciaTipoRetorno) throws BusinessException {
		List<U> listaDestino = new ArrayList<U>();
		try {
			for (T entidad : listaOrigen) {
				U instancia = instanciaTipoRetorno.newInstance();
				BeanUtils.copyProperties(instancia, entidad);
				listaDestino.add(instancia);
			}
		} catch (IllegalAccessException e) {
			log.error("IllegalAccessException en funci�n convertList", e);
			throw new BusinessException(
					"Error copiando objetos en convertList", e);
		} catch (InvocationTargetException e) {
			log.error("InvocationTargetException en funci�n convertList", e);
			throw new BusinessException(
					"Error copiando objetos en convertList", e);
		} catch (InstantiationException e) {
			log.error(
					"InstantiationException en funci�n convertList, probablemente falte el constructor por defecto para el tipo destino",
					e);
			throw new BusinessException(
					"Error copiando objetos en convertList", e);
		}
		return listaDestino;

	}

	/**
	 * Convierte una lista de un tipo T en una lista de otro tipo U siempre y
	 * cuando los tipos de los objetos sean compatibles y se puedan copiar sus
	 * atributos El tipo de dato U de retorno debe tener constructor por defecto
	 * 
	 * @param <T>
	 *            Tipo de dato de los objetos que contiene la lista origen
	 * @param <U>
	 *            Tipo de dato de los objetos que contendr� la lista destino
	 * @param listaOrigen
	 *            Set, Lista que contiene los objetos que ser�n convertidos al
	 *            otro
	 * @param instanciaTipoRetorno
	 *            Class, Instancia nueva del tipo de dato de la lista destino,
	 *            se usar� para copiar los datos de cada objeto de la lista
	 *            origen y adicionarlo a la lista destino
	 * @return ListaDestino de objetos tipo U
	 * @throws BusinessException
	 */
	public static <T, U extends T> List<U> convertList(Set<T> listaOrigen,
			Class<U> instanciaTipoRetorno) throws BusinessException {
		List<U> listaDestino = new ArrayList<U>();
		try {
			for (T entidad : listaOrigen) {
				U instancia = instanciaTipoRetorno.newInstance();
				BeanUtils.copyProperties(instancia, entidad);
				listaDestino.add(instancia);
			}
		} catch (IllegalAccessException e) {
			log.error("IllegalAccessException en funci�n convertList", e);
			throw new BusinessException(
					"Error copiando objetos en convertList", e);
		} catch (InvocationTargetException e) {
			log.error("InvocationTargetException en funci�n convertList", e);
			throw new BusinessException(
					"Error copiando objetos en convertList", e);
		} catch (InstantiationException e) {
			log.error(
					"InstantiationException en funci�n convertList, probablemente falte el constructor por defecto para el tipo destino",
					e);
			throw new BusinessException(
					"Error copiando objetos en convertList", e);
		}
		return listaDestino;

	}

	/**
	 * Copia un Objeto de un tipo T a un objeto de tipo U siempre y cuando los
	 * tipos de los objetos sean compatibles y se puedan copiar sus atributos El
	 * tipo de dato U de retorno debe tener constructor por defecto
	 * 
	 * @param <T>
	 *            Tipo de Dato Origen
	 * @param <U>
	 *            Tipo de Dato Destino
	 * @param tipoObjetoDestino
	 *            Clase de Objeto de Destino
	 * @param objetoOrigen
	 *            Objeto del Orgien
	 * @return Objeto Destino
	 * @throws BusinessException
	 */
	public static <T, U> U copyObject(Class<U> tipoObjetoDestino, T objetoOrigen)
			throws BusinessException {
		U instancia = null;
		if (objetoOrigen == null) {
			return null;
		}
		try {
			instancia = tipoObjetoDestino.newInstance();
			BeanUtils.copyProperties(instancia, objetoOrigen);
		} catch (IllegalAccessException e) {
			log.error("IllegalAccessException en funci�n copyObject", e);
			throw new BusinessException("Error copiando objeto en copyObject",
					e);
		} catch (InvocationTargetException e) {
			log.error("InvocationTargetException en funci�n copyObject", e);
			throw new BusinessException("Error copiando objeto en copyObject",
					e);
		} catch (InstantiationException e) {
			log.error(
					"InstantiationException en funci�n copyObject, probablemente falte el constructor por defecto para el tipo destino",
					e);
			throw new BusinessException("Error copiando objeto en copyObject",
					e);
		}
		return instancia;
	}

	/**
	 * Retorna Date de la fecha actual
	 * 
	 * @return Date
	 */
	public static Date fechaActual() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * Evalua si la hora de una fecha de la ma�ana o de la tarde
	 * 
	 * @author Leonardo Cardozo Cadavid
	 * @param date
	 * @return boolean
	 */
	public static boolean isAM(Date date) {
		if (date == null) {
			throw new IllegalArgumentException("Fecha no puede ser nula");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int a = c.get(Calendar.HOUR_OF_DAY);
		return a < 12;
	}

	/**
	 * Evalua si la fecha 1 es menor que la fecha 2
	 * 
	 * @author Leonardo Cardozo Cadavid
	 * @param date1
	 * @param date2
	 * @return boolean
	 */
	public static boolean isEarlierDate(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException(
					ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		}

		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);

		if (c1.get(Calendar.YEAR) < c2.get(Calendar.YEAR))
			return true;
		if (c1.get(Calendar.MONTH) < c2.get(Calendar.MONTH))
			return true;
		if (c1.get(Calendar.DAY_OF_MONTH) < c2.get(Calendar.DAY_OF_MONTH))
			return true;
		if (c1.get(Calendar.HOUR_OF_DAY) < c2.get(Calendar.HOUR_OF_DAY))
			return true;
		if (c1.get(Calendar.MINUTE) < c2.get(Calendar.MINUTE))
			return true;
		return false;

	}

	public static String dateToString(Date date) {
		return dateToString(date, DATE_FORMAT);
	}

	public static String dateToString(Date date, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		String result = dateFormat.format(date);
		return result;
	}

	public static Date stringToDate(String string) {
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		Date date = null;
		try {
			date = dateFormat.parse(string);
		} catch (ParseException ex) {
			ex.printStackTrace();
			java.util.logging.Logger.getLogger(UtilsBusiness.class.getName())
					.log(Level.SEVERE, null, ex);
		}
		return date;
	}

	public static Date stringToDate(String string, String dateFormat) {
		DateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		Date date = null;
		try {
			date = simpleDateFormat.parse(string);
		} catch (ParseException ex) {
			ex.printStackTrace();
			java.util.logging.Logger.getLogger(UtilsBusiness.class.getName())
					.log(Level.SEVERE, null, ex);
		}
		return date;
	}

	/**
	 * Retorna XMLGregorianCalendar de la fecha actual
	 * 
	 * @return XMLGregorianCalendar
	 */
	public static XMLGregorianCalendar fechaActualXMLGregorianCalendar() {
		XMLGregorianCalendar dateXMLGregorianCalendar = null;
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(fechaActual());

		try {
			dateXMLGregorianCalendar = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e) {
			log.error(
					"DatatypeConfigurationException en funci�n fechaActualXMLGregorianCalendar",
					e);
		}
		return dateXMLGregorianCalendar;
	}

	/**
	 * Valida que la cadena pasada por par�metro corresponda a un valor numérico
	 * ejemplos de valores válidos: 50 1.5 6.56D
	 * 
	 * @author wjimenez
	 * @param strVal
	 * @return boolean
	 */
	public static boolean isNumericValue(String strVal) {
		return NumberUtils.isNumber(strVal);
	}

	/**
	 * Valida si una cadena contiene solo valores de numeros de tel�fonos de
	 * celular
	 * 
	 * @param cadena
	 * @return true si contiene exclusivamente valores de cuenta email; false en
	 *         otro caso
	 */
	public static boolean soloCelular(String cadena) {
		return patronCelular.matcher(cadena).find();
	}

	/**
	 * Valida si una cadena contiene solo valores de cuentas email
	 * 
	 * @param cadena
	 * @return true si contiene exclusivamente valores de cuenta email; false en
	 *         otro caso
	 */
	public static boolean isEmail(String cadena) {
		return patronEmail.matcher(cadena).find();
	}

	/**
	 * Retorna el valor booleano recibido por parametro String
	 * 
	 * @param value
	 * @return
	 */
	public static boolean getPropertyBoolean(String value) {
		Boolean bool = Boolean.valueOf(value);
		return bool.booleanValue();
	}

	/**
	 * Retorna una lista de valores separados por coma (,) de la propiedad
	 * recibida por parametro
	 * 
	 * @param value
	 * @return List
	 */
	public static List<String> getPropertyList(String value) {
		StringTokenizer tokenizer = new StringTokenizer(value, ",");
		List<String> listValues = new ArrayList<String>();
		while (tokenizer.hasMoreTokens()) {
			listValues.add(tokenizer.nextToken());
		}
		return listValues;
	}

	/**
	 * 
	 * Metodo: Lee el archivo de propiedades BusinesMessages y retorna la
	 * instacia del properties.
	 * 
	 * @return reader PropertiesReader
	 * @throws PropertiesException
	 * @author jalopez
	 */
	public static PropertiesReader getBusinessMessagesProperties()
			throws PropertiesException {

		PropertiesReader reader;
		reader = PropertiesReader.getInstance().getInstanceFileSystem(
				Constantes.LABEL_RUTA_BUSINESS_MESSAGES);
		return reader;
	}

	/**
	 * Metodo: Obtiene un lector de archivo de propiedades data la ruta
	 * especificada
	 * 
	 * @param filePath
	 *            llave para obtener la ruta configurada en el archivo
	 *            applicationSD.properties por lo general estará dado por el
	 *            valor de uan constante de la clase
	 *            <code>co.com.directv.sdii.common.util.Constantes</code>
	 * @return Lector de propiedades
	 * @throws PropertiesException
	 *             En caso de error al tratar de leer las propiedades
	 * @author jjimenezh
	 */
	public static PropertiesReader getMessageProperties(String filePathKey)
			throws PropertiesException {
		PropertiesReader reader;
		reader = PropertiesReader.getInstance().getInstanceFileSystem(
				filePathKey);

		return reader;
	}

	/**
	 * 
	 * Metodo: Se encarga de inicializar el log4j, segun archivo de
	 * configuracion y retornar instancia de Logger.
	 * 
	 * @param pClass
	 *            Class
	 * @return org.apache.log4j.Logger.Logger
	 * @author Joan Lopez
	 */
	public static Logger getLog4J(Class<?> pClass) {
		Logger logger = null;
		Log4jLogger log4jLogger = null;
		try {
			log4jLogger = new Log4jLogger(pClass);
			logger = log4jLogger.getLogger();
		} catch (Log4jLoggerException e) {
			e.printStackTrace();
		}
		return logger;
	}

	/**
	 * Retorna la fecha con 23 horas, 59 minutos y 59 segundos
	 * 
	 * @param fechaFinal
	 * @return Date
	 */
	public static Date obtenerUltimaHoraDia(Date fechaFinal) {
		if (fechaFinal != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fechaFinal);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			return calendar.getTime();
		}
		return null;
	}

	/**
	 * Retorna la fecha con 0 horas, 0 minutos y 0 segundos
	 * 
	 * @param fechaFinal
	 * @return Date
	 */
	public static Date obtenerPrimeraHoraDia(Date fechaFinal) {
		if (fechaFinal != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fechaFinal);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			return calendar.getTime();
		}
		return null;
	}

	/**
	 * 
	 * Metodo: Lee el archivo de propiedades CodesBusinesEntity y retorna la
	 * instacia del properties.
	 * 
	 * @return reader PropertiesReader
	 * @throws PropertiesException
	 * @author Joan Lopez
	 */
	public static PropertiesReader getCodesBusinessEntityProperties()
			throws PropertiesException {

		PropertiesReader reader;
		reader = PropertiesReader.getInstance().getInstanceFileSystem(
				Constantes.LABEL_RUTA_CODES_BUSINESS_ENTITY);

		return reader;
	}

	/**
	 * 
	 * Metodo: Lee el archivo de propiedades CodesBusinesEntity y retorna la
	 * instacia del properties.
	 * 
	 * @return reader PropertiesReader
	 * @throws PropertiesException
	 * @author cduarte
	 */
	public static PropertiesReader getWorkManagerConfigProperties()
			throws PropertiesException {

		PropertiesReader reader;
		reader = PropertiesReader.getInstance().getInstanceFileSystem(
				Constantes.RESOURCE_WORK_MANAGER_CONFIG);

		return reader;
	}

	/**
	 * 
	 * Metodo: Lee el archivo de propiedades WsdlLocations y retorna la instacia
	 * del properties.
	 * 
	 * @return reader PropertiesReader
	 * @throws PropertiesException
	 * @author Joan Lopez
	 */
	public static PropertiesReader getWsdlLocationsProperties()
			throws PropertiesException {

		PropertiesReader reader;
		reader = PropertiesReader.getInstance().getInstanceFileSystem(
				Constantes.LABEL_RUTA_WSDL_LOCATIONS);

		return reader;
	}

	/**
	 * Método: Incrementa date en add días
	 * 
	 * @param date
	 *            - Date
	 * @param days
	 *            - int
	 * @return Date
	 */
	public static Date addDate(Date date, int days) {
		return addDate(date, Calendar.DATE, days);
	}

	/**
	 * Método: Incrementa date en add días33
	 * 
	 * @param date
	 *            - Date
	 * @param days
	 *            - int
	 * @return Date
	 */
	public static Date addDate(Date date, int field, int amount) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(date.getTime());
		calendar.add(field, amount);
		return calendar.getTime();

	}

	/**
	 * Metodo: Convierte una fecha al año 1900 mes enero día 1
	 * 
	 * @param date
	 *            fecha a convertir
	 * @return Fecha convertida quedando únicamente la hora
	 * @author jjimenezh
	 */
	public static Date convert2FirstYearMonthAndDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.YEAR, 1900);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * Coloca "00" al campo de segundos de una fecha
	 * 
	 * @param date
	 *            Fecha a la que se le colocaran lo segundos
	 * @return Fecha modificada
	 */
	public static Date setInitSecondsToDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.SECOND, 00);
		return cal.getTime();
	}

	/**
	 * Coloca "59" al campo de segundos de una fecha
	 * 
	 * @param date
	 *            Fecha a la que se le colocaran lo segundos
	 * @return Fecha modificada
	 */
	public static Date setFinalSecondsToDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	/**
	 * Convierte una fecha a la ultima hora del dia
	 * 
	 * @param fecha
	 * @return Date
	 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
	 */
	public final static Date dateTo12pm(Date fecha) {
		if (fecha == null)
			throw new IllegalArgumentException("Fecha no puede ser null");
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	/**
	 * Convierte una fecha a la primera hora del dia
	 * 
	 * @param fecha
	 * @return Date
	 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
	 */
	public final static Date dateTo12am(Date fecha) {
		if (fecha == null) {
			throw new IllegalArgumentException("Fecha no puede ser null");
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.set(Calendar.HOUR_OF_DAY, 00);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		cal.set(Calendar.MILLISECOND, 00);
		return cal.getTime();
	}

	/**
	 * retorna la fecha formateada p.ej: 20090902
	 * 
	 * @param fecha
	 * @return String
	 * @author Leonardo Cardozo Cadavid
	 */
	public final static String formatYYYYMMDD(Date fecha) {
		if (fecha == null) {
			throw new IllegalArgumentException("Fecha no puede ser null");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(fecha);
		int day = c.get(Calendar.DAY_OF_MONTH);
		int month = c.get(Calendar.MONTH) + 1;
		return c.get(Calendar.YEAR)
				+ ("" + (month < 10 ? ("0" + month) : month))
				+ ("" + (day < 10 ? ("0" + day) : day));
	}

	/**
	 * retorna la fecha formateada p.ej: 09/02/2009
	 * 
	 * @param fecha
	 * @return String
	 * @author Leonardo Cardozo Cadavid
	 */
	public final static String formatDate(Date fecha) {
		if (fecha == null) {
			throw new IllegalArgumentException("Fecha no puede ser null");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(fecha);
		int day = c.get(Calendar.DAY_OF_MONTH);
		int month = c.get(Calendar.MONTH) + 1;
		return ("" + (day < 10 ? ("0" + day) : day)) + "/"
				+ ("" + (month < 10 ? ("0" + month) : month)) + "/"
				+ c.get(Calendar.YEAR);
	}

	/**
	 * Retorna la diferencia en dias de dos fechas
	 * 
	 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
	 * @param fecha1
	 * @param fecha2
	 * @return int
	 */
	public final static int getDaysBetween(Date fecha1, Date fecha2) {
		if (fecha1 == null || fecha2 == null)
			throw new IllegalArgumentException("Las fechas no pueden ser nulas");
		return Math
				.abs((int) ((fecha1.getTime() - fecha2.getTime()) / (1000 * 60 * 60 * 24)));
	}

	/**
	 * Retorna la diferencia en dias de dos fechas
	 * 
	 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
	 * @param fecha1
	 * @param fecha2
	 * @return int
	 */
	public final static double getDaysBetween2Dates(Date fecha1, Date fecha2) {
		if (fecha1 == null || fecha2 == null)
			throw new IllegalArgumentException("Las fechas no pueden ser nulas");
		Double numerador = new Double(fecha1.getTime() - fecha2.getTime());
		Double denominador = new Double(1000 * 60 * 60 * 24);
		return (numerador / denominador);
	}

	public final static int getDaysBetweenWithNegativesValues(Date fecha1,
			Date fecha2) {
		if (fecha1 == null || fecha2 == null)
			throw new IllegalArgumentException("Las fechas no pueden ser nulas");
		return (int) ((fecha1.getTime() - fecha2.getTime()) / (1000 * 60 * 60 * 24));
	}

	public static void assertNotNull(Object obj2Assert, String exceptionCode,
			String exceptionMessage) throws BusinessException {
		if (obj2Assert == null) {
			log.error("== Parametro Requerido nulo ==");
			throw new BusinessException(exceptionCode, exceptionMessage);
		} else if (obj2Assert instanceof String) {
			String string = (String) obj2Assert;
			if ("".equals(string)) {
				log.error("== Parametro Requerido vacio ==");
				throw new BusinessException(exceptionCode, exceptionMessage);
			}
		}
	}

	/**
	 * Metodo: Valida un objeto determinando si es nulo, en caso que sea nulo
	 * lanza una excepción y escribe en el log el atributo que fué validado
	 * 
	 * @param parameterName
	 *            Nombre del atributo a ser validado o mensaje para ser escrito
	 *            en el log en caso que el objeto sea nulo
	 * @param operation
	 *            nombre de la operacion
	 * @param value2Validate
	 *            objeto a ser validado
	 * @param errorCode
	 *            código de error a ser lanzado
	 * @param errorMessage
	 *            mensaje de error a ser lanzado
	 * @param Object
	 *            [] pParams Parametros que se reemplazan en la exception
	 *            (operationServicioWeb, nombreParametro)
	 * @author jalopez
	 * @throws BusinessDetailException
	 */
	public static void validateRequestResponseWebService(Object[] pParams,
			Object value2Validate, String errorCode, String errorMessage)
			throws BusinessException {
		List<String> params = getParametersWS(pParams);
		try {
			UtilsBusiness
					.assertNotNull(value2Validate, errorCode, errorMessage);
		} catch (BusinessException e) {
			log.error("== Error de validación de parámetros de un WebService: el parámetro: \""
					+ pParams[1]
					+ "\" de la operacion \""
					+ pParams[0]
					+ "\" es requerido ==");
			throw new BusinessException(e.getMessageCode(), e.getMessage(),
					params);
		}
	}

	/**
	 * Retorna los parametros que se formatean en el mensaje de error.
	 * 
	 * @param parameterName
	 *            parametro que llega null o vacio
	 * @param operation
	 *            nombre de la operacion
	 * @return Object[]
	 * @author jalopez
	 */
	public static Object[] getErrorParameters(String parameterName,
			String operation) {
		Object[] errorParameters = new Object[2];
		errorParameters[0] = parameterName;
		errorParameters[1] = operation;
		return errorParameters;
	}

	/**
	 * Retorna los parametros que se formatean en el mensaje de error para el
	 * servicio web.
	 * 
	 * @param errorParameters
	 * @return List<String>
	 * @author jalopez
	 */
	public static List<String> getParametersWS(Object[] errorParameters) {
		List<String> params = new ArrayList<String>();
		for (int i = 0; i < errorParameters.length; i++) {
			params.add((String) errorParameters[i]);
		}
		return params;
	}

	/**
	 * Valida si un parametro es null o vacio y retorna un
	 * BusinessDetailException
	 * 
	 * @param obj2Assert
	 * @param exceptionCode
	 * @param exceptionMessage
	 * @throws BusinessDetailException
	 */
	public static void assertNotNullBusinessDetail(Object obj2Assert,
			String exceptionCode, String exceptionMessage)
			throws BusinessDetailException {
		if (obj2Assert == null) {
			log.error("== Parametro Requerido nulo ==");
			throw new BusinessDetailException(exceptionCode, exceptionMessage);
		} else if (obj2Assert instanceof String) {
			String string = (String) obj2Assert;
			if ("".equals(string)) {
				log.error("== Parametro Requerido vacio ==");
				throw new BusinessDetailException(exceptionCode,
						exceptionMessage);
			}
		}
	}

	public static void assertNotEmpty(Object obj2Assert, String exceptionCode,
			String exceptionMessage) throws BusinessException {
		if (obj2Assert == null) {
			throw new BusinessException(exceptionCode, exceptionMessage);
		} else if (obj2Assert instanceof Object[]) {
			if (((Object[]) obj2Assert).length == 0) {
				throw new BusinessException(exceptionCode, exceptionMessage);
			}
		} else if (obj2Assert instanceof Collection<?>) {
			if (((Collection<?>) obj2Assert).size() == 0) {
				throw new BusinessException(exceptionCode, exceptionMessage);
			}
		}
	}

	/**
	 * Obtiene el valor de un parámetro de tipo numérico
	 * 
	 * @param parameterCode
	 *            código del parámetro del sistema en la base de datos
	 * @param countryId
	 *            identificador del país
	 * @return número con el valor persistido para ese parámetro
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @author jjimenezh 2010-05-11
	 */
	public static Long getNumericSystemParameter(String parameterCode,
			Long countryId, SystemParameterDAOLocal systemParameterDao)
			throws DAOServiceException, DAOSQLException, BusinessException {
		SystemParameter sysParam = systemParameterDao
				.getSysParamByCodeAndCountryId(parameterCode, countryId);
		UtilsBusiness
				.assertNotNull(
						sysParam,
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED
								.getMessage()
								+ " No se encontró el parámetro del sistema por el código especificadO: "
								+ parameterCode
								+ " en el país con id especificado: "
								+ countryId);
		if (!NumberUtils.isNumber(sysParam.getValue())) {
			log.error("Parametro no es numerico");
			throw new BusinessException(
					ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
					ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		}
		Long parameterValue = Long.parseLong(sysParam.getValue());
		return parameterValue;
	}

	/**
	 * Obtiene el valor de un parámetro de tipo numérico
	 * 
	 * @param parameterCode
	 *            código del parámetro del sistema en la base de datos
	 * @param countryId
	 *            identificador del país
	 * @return número con el valor persistido para ese parámetro
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @author cduarte
	 * @throws DAOSQLException
	 * @throws DAOServiceException
	 */
	public static String getSystemParameter(String parameterCode,
			Long countryId, SystemParameterDAOLocal systemParameterDao)
			throws DAOServiceException, DAOSQLException {
		SystemParameter sysParam = systemParameterDao
				.getSysParamByCodeAndCountryId(parameterCode, countryId);
		return sysParam == null ? "" : sysParam.getValue();
	}

	/**
	 * 
	 * Metodo: <Descripcion>
	 * 
	 * @param gregorianCal
	 * @return Date
	 * @author
	 */
	public static Date dateFromGregorianCalendar(
			XMLGregorianCalendar gregorianCal) {
		if (gregorianCal == null) {
			return null;
		}

		Date result = gregorianCal.toGregorianCalendar().getTime();
		Calendar cal = Calendar.getInstance();
		cal.setTime(result);

		if (cal.get(Calendar.YEAR) == 1) {
			return null;
		}
		return result;
	}

	/**
	 * 
	 * Metodo: Retorna una fecha en fromato XMLGregorianCalendar.
	 * 
	 * @param date
	 * @return XMLGregorianCalendar
	 * @author jalopez
	 */
	public static XMLGregorianCalendar dateToGregorianCalendar(Date date) {

		if (date == null) {
			return null;
		}

		TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("UTC");
		XMLGregorianCalendar dateXMLGregorianCalendar = null;
		GregorianCalendar newCalendar = new GregorianCalendar();
		GregorianCalendar oldCalendar = new GregorianCalendar();

		oldCalendar.setTime(date);
		newCalendar.set(oldCalendar.get(GregorianCalendar.YEAR),
				oldCalendar.get(GregorianCalendar.MONTH),
				oldCalendar.get(GregorianCalendar.DAY_OF_MONTH),
				oldCalendar.get(GregorianCalendar.HOUR_OF_DAY),
				oldCalendar.get(GregorianCalendar.MINUTE),
				oldCalendar.get(GregorianCalendar.SECOND));
		newCalendar.setTimeZone(UTC_TIME_ZONE);

		try {
			dateXMLGregorianCalendar = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(newCalendar);
		} catch (DatatypeConfigurationException e) {
			log.error(
					"DatatypeConfigurationException en funcion dateToGregorianCalendar",
					e);
		}
		return dateXMLGregorianCalendar;
	}

	public static Date getDateLastChangeOfUser(Long countryId,
			UserDAOLocal userDao, SystemParameterDAOLocal systemParameterDAO) {
		Long userId = UtilsBusiness.getUserIdAdmin(userDao, systemParameterDAO,
				countryId);
		return UtilsBusiness.getCurrentTimeZoneDateByUserId(userId, userDao);
	}

	/**
	 * Metodo: Obtiene la fecha en una zona horaria específica
	 * 
	 * @param userId
	 *            identificador del usuario
	 * @param userDao
	 *            objeto que provee acceso a la capa de datos
	 * @return Fecha lozalizada
	 * @author jjimenezh
	 */
	public static Date getCurrentTimeZoneDateByUserId(Long userId,
			UserDAOLocal userDao) {
		try {
			UtilsBusiness.assertNotNull(userId,
					ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
					ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			User user = userDao.getUserById(userId);
			assertNotNull(user, ErrorBusinessMessages.USER_NOT_EXIST.getCode(),
					ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
			SdiiTimeZone sdiiTimeZone = user.getSdiiTimeZone();
			assertNotNull(sdiiTimeZone,
					ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
					ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage()
							+ "No se ha asignado zona horaria al usuario");
			String timeZoneKey = sdiiTimeZone.getTimeZoneKey();
			Date result = getCurrentDateByTimeZoneKey(timeZoneKey, new Date());
			return result;
		} catch (Exception e) {
			log.error(
					"Al tratar de obtener la fecha en la zona horaria del usuario con id: "
							+ userId + " el error es: ", e);
			return new Date();
		}
	}

	/**
	 * Metodo: Obtiene la fecha en una zona horaria específica
	 * 
	 * @param userId
	 *            identificador del usuario
	 * @param userDao
	 *            objeto que provee acceso a la capa de datos
	 * @param localDate
	 *            fecha a convertir
	 * @return Fecha lozalizada
	 * @author jjimenezh
	 */
	public static Date getCurrentTimeZoneDateByUserId(Date localDate,
			Long userId, UserDAOLocal userDao) {
		try {
			User user = userDao.getUserById(userId);
			assertNotNull(user, ErrorBusinessMessages.USER_NOT_EXIST.getCode(),
					ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
			SdiiTimeZone sdiiTimeZone = user.getSdiiTimeZone();
			assertNotNull(sdiiTimeZone,
					ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
					ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage()
							+ "No se ha asignado zona horaria al usuario");
			String timeZoneKey = sdiiTimeZone.getTimeZoneKey();
			Date result = getCurrentDateByTimeZoneKey(timeZoneKey, localDate);
			return result;
		} catch (Exception e) {
			log.error(
					"Al tratar de obtener la fecha en la zona horaria del usuario con id: "
							+ userId + " el error es: ", e);
			return new Date();
		}
	}

	/**
	 * Metodo: <Descripcion>
	 * 
	 * @param timeZoneKey
	 * @param localDate
	 * @return <tipo> <descripcion>
	 * @author jjimenezh
	 */
	public static Date getCurrentDateByTimeZoneKey(String timeZoneKey,
			Date localDate) {
		TimeZone timeZone = TimeZone.getTimeZone(timeZoneKey);
		TimeZone localTimeZone = TimeZone.getDefault();

		Calendar countryTime = Calendar.getInstance(timeZone);
		Calendar localCalendar = Calendar.getInstance();

		Date result = countryTime.getTime();
		Date localTime = localDate;

		int offset = timeZone.getOffset(result.getTime());
		int localOffset = localTimeZone.getOffset(localTime.getTime());
		int difference = offset - localOffset;

		localCalendar.add(Calendar.MILLISECOND, difference);

		Date finalResult = localCalendar.getTime();

		if (log.isDebugEnabled()) {
			DateFormat dateFormat = new SimpleDateFormat(
					DATE_FORMAT_YYYYMMDDHHMMSS);
			dateFormat.setTimeZone(timeZone);
			DateFormat localDateFormat = new SimpleDateFormat(
					DATE_FORMAT_YYYYMMDDHHMMSS);
			log.debug("Se recibe una petición de transformación de hora, la hora local es: \""
					+ localDateFormat.format(localTime)
					+ "\" la zona horaria destino es: \""
					+ timeZoneKey
					+ "\" la hora destino es: \""
					+ dateFormat.format(result)
					+ "\"");
		}
		return finalResult;
	}

	/**
	 * Este metodo retorna la fecha enviada en GMT-0 en el GMT correspondiente
	 * al timeZone enviado por parametro. Por ejemplo si se envia la fecha
	 * 2015/05/15 12:12:12 (GMT-0) y se envia el timeZone "Chile/Continental" El
	 * cual tiene un GMT-4, El resultado será la fecha 2015/05/15 08:12:12
	 * (GMT-4). Metodo: <Descripcion>
	 * 
	 * @param toConvert
	 * @param timeZoneKey
	 * @author fsanabria
	 */
	public static Date getDateInTimezone(Date toConvert, String timeZoneKey) {
		TimeZone timeZone = TimeZone.getTimeZone(timeZoneKey);
		Calendar cSchedStartCal = Calendar.getInstance(TimeZone
				.getTimeZone("GMT"));
		cSchedStartCal.setTime(toConvert);
		long gmtTime = cSchedStartCal.getTime().getTime();

		long timezoneAlteredTime = gmtTime + timeZone.getRawOffset();
		Calendar cSchedStartCal1 = Calendar.getInstance(timeZone);
		cSchedStartCal1.setTimeInMillis(timezoneAlteredTime);

		return cSchedStartCal1.getTime();
	}
	
	
    public static Date convertDateInTimezone (XMLGregorianCalendar xmlCalendar, TimeZone countryTimeZone) {
        GregorianCalendar gCal = xmlCalendar.toGregorianCalendar();
                     
        gCal.setTimeZone(xmlCalendar.getTimeZone(0));

        TimeZone timeZone = countryTimeZone;
        TimeZone localTimeZone = TimeZone.getDefault();

        Calendar countryTime = Calendar.getInstance(timeZone);
        Calendar localCalendar = gCal;

        Date result = countryTime.getTime();
        Date localTime = gCal.getTime();

        int offset = timeZone.getOffset(result.getTime());
        int localOffset = localTimeZone.getOffset(localTime.getTime());
        int difference = offset - localOffset;

        localCalendar.add(Calendar.MILLISECOND, difference);
       
        return localCalendar.getTime();
  }
	
	
	public static Date getDateInTimezone(XMLGregorianCalendar toConvert, String toTimeZoneKey) {
		int timeZoneVal = toConvert.getTimezone()/60;
		String gmtTZ = "GMT";
		if(timeZoneVal < 0){
			gmtTZ = gmtTZ + timeZoneVal;
		}else if (timeZoneVal > 0){
			gmtTZ = gmtTZ + "+" + timeZoneVal;
		}
		
		TimeZone toTimeZone = TimeZone.getTimeZone(toTimeZoneKey);
		TimeZone fromTimeZone = TimeZone.getTimeZone(gmtTZ);
		
		Date myDate = toConvert.toGregorianCalendar(fromTimeZone, null,null).getTime();
		
		
		Calendar cSchedStartCal = Calendar.getInstance(fromTimeZone);
		cSchedStartCal.setTime(myDate);
		long gmtTime = cSchedStartCal.getTime().getTime();

		long timezoneAlteredTime = gmtTime + toTimeZone.getRawOffset() - fromTimeZone.getRawOffset();
		Calendar cSchedStartCal1 = Calendar.getInstance(toTimeZone);
		cSchedStartCal1.setTimeInMillis(timezoneAlteredTime);
		
		return cSchedStartCal1.getTime();
	}

	/**
	 * 
	 * Metodo: Permite dar formato a un mensaje por medio de parametros
	 * asignados.
	 * 
	 * @param text
	 *            String - Cadena con el mensaje
	 * @param params
	 *            [] Object - parametros para reemplazar en el mensaje
	 * @return String - retorna el mensaje formateado.
	 * @author Joan Lopez
	 */
	public static String getMessageResourceString(String text, Object params[]) {
		if (text != null && params != null) {
			if (!text.equals("")) {
				MessageFormat mf = new MessageFormat(text);
				text = mf.format(params, new StringBuffer(), null).toString();
			}
		}
		return text;
	}

	/**
	 * Metodo: Le da formato a una fecha
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date formatDate(Date date, String format) {
		try {
			if (date != null && format != null && !format.equals("")) {
				SimpleDateFormat dateFormat = new SimpleDateFormat(format);
				String stringDate = dateFormat.format(date);
				return stringToDate(stringDate);
			} else
				return null;

		} catch (Exception e) {
			log.error("Al tratar de formatear la fecha ", e);
			return null;
		}
	}

	/**
	 * 
	 * Metodo: Convierte una lista de String en un solo String separadando cada
	 * elemento de la lista por el caracter enviado por parametro
	 * 
	 * @param list
	 *            List<Long> Lista que se desea unir en un solo String
	 * @param character
	 *            String Caracter por el cual se van a separar los elementos de
	 *            la lista
	 * @return String Lista unida seperando cada elemento por el caracter del
	 *         parametro
	 * @author jnova
	 */
	public static String longListToString(List<Long> list, String character) {
		StringBuffer sb = new StringBuffer();
		if (list != null && !list.isEmpty()) {
			for (Object object : list) {
				sb.append(object.toString());
				sb.append(character);
			}
		}
		return StringUtils.removeEnd(sb.toString(), character);
	}

	

	/**
	 * 
	 * @param Convierte
	 *            una lista de String en un solo String utilizando la condicion
	 *            EXISTS de SQL e igualanado cada elemento de la lista al
	 *            parametro
	 * @param queryParameter
	 *            Parametro al que se va a igualar todo el resultado del WHERE
	 * @return Lista unida por EXISTS de SQL
	 * @author cvaldezv
	 */
	public static String longListToExistsInQuery(List<Long> list,
			String queryParameter) {

		StringBuffer sb = new StringBuffer();
		if (list != null && !list.isEmpty()) {
			Iterator<Long> it = list.iterator();

			sb.append(INIT_CLAUSE);
			while (it.hasNext()) {
				sb.append(ODCI_NUMBER_LIST_FUNCTION_INIT_CLAUSE);
				int count = 1;
				// La longitud no puede superar los 1000 parametros para la
				// creacion de la tabla en memoria
				while (it.hasNext() && count < UtilsBusiness.MAX_PARAM_FUNCTION_LENGTH) {
					Long item = it.next();
					count++;
					sb.append(item.toString() + ",");
				}
				sb.deleteCharAt(sb.lastIndexOf(","));
				if (it.hasNext()) {
					sb.append(ODCI_NUMBER_LIST_FUNCTION_MIDDLED_END_CLAUSE);
					sb.append(UNION_CLAUSE);
				}
			}
			sb.append(LAST_CLAUSE);
		}
		return sb.toString().replace(":queryParameter", queryParameter);
	}

//	public static void main(String arg[]) {
//		List<Long> longLst = new ArrayList<Long>();
//		for (int i = 0; i < 360; i++) {
//			longLst.add(new Long(i));
//		}
//		String resp = UtilsBusiness.longListToExistsInQuery(longLst, "w.ID");
//		System.out.println(resp);
//	}

	/**
	 * 
	 * @param Convierte
	 *            una lista de String en un solo String separando cada elemento
	 *            por un OR de SQL e igualanado cada elemento de la lista al
	 *            parametro
	 * @param queryParameter
	 *            Parametro al que se va a igualar cada elemento de la lista en
	 *            un String de OR's
	 * @return Lista unida por OR de SQL igualados al caracter enviado por
	 *         parametro
	 * @author jnova
	 */
	public static String longListToOrInQuery(List<Long> list,
			String queryParameter) {
		StringBuffer sb = new StringBuffer();
		if (list != null && !list.isEmpty()) {
			for (Object object : list) {
				sb.append(queryParameter);
				sb.append("=");
				sb.append(object.toString());
				sb.append(" OR ");
			}
		}
		return "( " + StringUtils.removeEnd(sb.toString(), " OR ") + " )";
	}

	/**
	 * 
	 * @param Convierte
	 *            una lista de String en un solo String separando cada elemento
	 *            por un OR de SQL e igualando cada elemento de la lista al
	 *            parametro
	 * @param queryParameter
	 *            Parametro al que se va a igualar cada elemento de la lista en
	 *            un String de OR's
	 * @return Lista unida por OR de SQL igualados al caracter enviado por
	 *         parametro
	 * @author waguilera
	 */
	public static String stringListToOrInQuery(List<String> list,
			String queryParameter) {
		StringBuffer sb = new StringBuffer();
		if (list != null && !list.isEmpty()) {
			for (String object : list) {
				sb.append(queryParameter);
				sb.append("=");
				sb.append("'" + object + "'");
				sb.append(" OR ");
			}
		}
		return "( " + StringUtils.removeEnd(sb.toString(), " OR ") + " )";
	}

	/**
	 * 
	 * Metodo: Convierte una lista de String en un solo String separadando cada
	 * elemento de la lista por el caracter enviado por parametro
	 * 
	 * @param list
	 *            List<String> Lista que se desea unir en un solo String
	 * @param character
	 *            String Caracter por el cual se van a separar los elementos de
	 *            la lista
	 * @return String Lista unida seperando cada elemento por el caracter del
	 *         parametro
	 * @author jnova
	 */
	public static String stringListToString(List<String> list, String character) {
		StringBuffer sb = new StringBuffer();
		if (list != null && !list.isEmpty()) {
			for (Object object : list) {
				sb.append(object.toString());
				sb.append(character);
			}
		}
		return StringUtils.removeEnd(sb.toString(), character);
	}

	/**
	 * 
	 * Metodo: Convierte una lista de String en un solo String separadando cada
	 * elemento de la lista por el caracter enviado por parametro poniendo para
	 * cada elemento de la lista los caracteres para ser reconocidos por la BD
	 * como cadena de caracteres
	 * 
	 * @param list
	 * @param character
	 * @return <tipo> <descripcion>
	 * @author jnova
	 */
	public static String stringListToStringForQuery(List<String> list,
			String character) {
		StringBuffer sb = new StringBuffer();
		if (list != null && !list.isEmpty()) {
			for (Object object : list) {
				sb.append("'");
				sb.append(object.toString());
				sb.append("'");
				sb.append(character);
			}
		}
		return StringUtils.removeEnd(sb.toString(), character);
	}

	/**
	 * 
	 * Metodo: Agrega comillas a una cadena con el fin de enviar los datos a una
	 * consulta recibe un caracter separador, y otro para el nuevo separador
	 * 
	 * @param list
	 * @param character
	 * @return <tipo> <descripcion>
	 * @author jnova
	 */

	/**
	 * @param fecha
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String formatDateWithoutTimeInAStringForBetweenWhere(
			Date fecha) {
		int year = fecha.getYear() + 1900;
		int month = fecha.getMonth() + 1;
		int day = fecha.getDate();
		String date = year + "-" + month + "-" + day;
		return date;
	}

	/**
	 * Metodo que construye queries
	 * 
	 * @param variables
	 * @param variablesBetween
	 * @param variablesBetweenDates
	 * @return
	 * @throws Throwable
	 * @throws Exception
	 */
	public static String buildQuery(Object[] variables,
			Object[] variablesBetween, Object[] variablesBetweenDates)
			throws Throwable {
		String where;
		String tempWhere = new String();

		if (variables != null) {
			for (int i = 0; i < variables.length; i++) {
				if ((variables[i] != null) && (variables[i + 1] != null)
						&& (variables[i + 2] != null)
						&& (variables[i + 3] != null)) {
					String variable = (String) variables[i];
					Boolean booVariable = (Boolean) variables[i + 1];
					Object value = variables[i + 2];
					String comparator = (String) variables[i + 3];

					if (booVariable.booleanValue()) {
						tempWhere = (tempWhere.length() == 0) ? ("(model."
								+ variable + " " + comparator + " \'" + value + "\' )")
								: (tempWhere + " AND (model." + variable + " "
										+ comparator + " \'" + value + "\' )");
					} else {
						tempWhere = (tempWhere.length() == 0) ? ("(model."
								+ variable + " " + comparator + " " + value + " )")
								: (tempWhere + " AND (model." + variable + " "
										+ comparator + " " + value + " )");
					}
				}

				i = i + 3;
			}
		}

		if (variablesBetween != null) {
			for (int j = 0; j < variablesBetween.length; j++) {
				if ((variablesBetween[j] != null)
						&& (variablesBetween[j + 1] != null)
						&& (variablesBetween[j + 2] != null)
						&& (variablesBetween[j + 3] != null)
						&& (variablesBetween[j + 4] != null)) {
					String variable = (String) variablesBetween[j];
					Object value = variablesBetween[j + 1];
					Object value2 = variablesBetween[j + 2];
					String comparator1 = (String) variablesBetween[j + 3];
					String comparator2 = (String) variablesBetween[j + 4];
					tempWhere = (tempWhere.length() == 0) ? ("(" + value + " "
							+ comparator1 + " " + variable + " and " + variable
							+ " " + comparator2 + " " + value2 + " )")
							: (tempWhere + " AND (" + value + " " + comparator1
									+ " " + variable + " and " + variable + " "
									+ comparator2 + " " + value2 + " )");
				}

				j = j + 4;
			}
		}

		if (variablesBetweenDates != null) {
			for (int k = 0; k < variablesBetweenDates.length; k++) {
				if ((variablesBetweenDates[k] != null)
						&& (variablesBetweenDates[k + 1] != null)
						&& (variablesBetweenDates[k + 2] != null)) {
					String variable = (String) variablesBetweenDates[k];
					Object object1 = variablesBetweenDates[k + 1];
					Object object2 = variablesBetweenDates[k + 2];
					String value = null;
					String value2 = null;

					try {
						Date date1 = (Date) object1;
						Date date2 = (Date) object2;
						value = formatDateWithoutTimeInAStringForBetweenWhere(date1);
						value2 = formatDateWithoutTimeInAStringForBetweenWhere(date2);
					} catch (Throwable e) {
						throw e;
					}

					tempWhere = (tempWhere.length() == 0) ? ("(model."
							+ variable + " between \'" + value + "\' and \'"
							+ value2 + "\')") : (tempWhere + " AND (model."
							+ variable + " between \'" + value + "\' and \'"
							+ value2 + "\')");
				}

				k = k + 2;
			}
		}

		if (tempWhere.length() == 0) {
			where = "";
		} else {
			where = "where (" + tempWhere + ")";
		}

		return where;
	}

	public static void assertNotNull(Object obj2Assert, String code,
			String message, List<String> asList) throws BusinessException {
		if (obj2Assert == null) {
			throw new BusinessException(code, message, asList);
		}
	}

	public static Date actualDateWithoutHour() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date dateWithoutHour(Date dateWoutTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateWoutTime);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 
	 * Metodo: Adiciona hora, minutos, segundos a una fecha.
	 * 
	 * @param hours
	 *            Date
	 * @param date
	 *            Date
	 * @return Date
	 * @author jalopez
	 */
	public static Date addHoursToDate(Date hours, Date date) {

		Calendar cal = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date);
		cal.setTime(hours);
		cal.set(Calendar.YEAR, cal2.get(Calendar.YEAR));
		cal.set(Calendar.MONTH, cal2.get(Calendar.MONTH));
		cal.set(Calendar.DAY_OF_YEAR, cal2.get(Calendar.DAY_OF_YEAR));
		date = cal.getTime();

		return date;
	}

	/**
	 * Metodo que retorna el nombre de la WO dependiendo del pais
	 * 
	 * @param workOrderInfo
	 * @return
	 * @throws Throwable
	 */
	public static String generateWoName(Long countryId,
			WoPdfAnnexDAOLocal woPdfAnnexDAOLocal) throws Throwable {
		WoPdfAnnex woPdf = null;
		List<WoPdfAnnex> woPdfTemp = null;
		String builtPdfAnnexQuery = null;

		builtPdfAnnexQuery = UtilsBusiness.buildQuery(
				buildVariablesForWoOrAnnex(countryId,
						CodesBusinessEntityEnum.PDF_WORKORDER_CODE
								.getCodeEntity()), null, null);
		// Busca el formato de la WO necesaria dependiendo del pais
		woPdfTemp = woPdfAnnexDAOLocal
				.searchWoPdfAnnexByCriteria(builtPdfAnnexQuery);

		if (woPdfTemp == null) {
			return "continue";
		}

		if (woPdfTemp.size() == 0) {
			return "continue";
		}

		woPdf = woPdfTemp.get(0);

		return woPdf.getFormatName();
	}

	/**
	 * Metodo que llena un arreglo de objetos para ayduar a buscar los anexos o
	 * wo necesaria para armar el PDF de la wo dependiendo del pais
	 * 
	 * @param workOrderInfo
	 * @return
	 */
	public static Object[] buildVariablesForWoOrAnnex(Long countryId,
			String woOrAnnex) throws PropertiesException {

		Object variables[] = new Object[4 * 5];

		int varCount = 0;
		variables[0 + varCount] = "countryId";
		variables[1 + varCount] = true;
		variables[2 + varCount] = countryId;
		variables[3 + varCount] = "=";

		varCount = 1 * 4;
		variables[0 + varCount] = "formatTypeId";
		variables[1 + varCount] = true;
		variables[2 + varCount] = woOrAnnex;
		variables[3 + varCount] = "=";

		return variables;
	}

	/**
	 * 
	 * Metodo: extrae el primer elemento de una lista
	 * 
	 * @param list
	 * @return objeto del tipo que está contenido en la lista
	 * @author
	 */
	public static Object getFirstItem(List list) {
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * Método: Retorna una instancia HSSFWorkbook si el archivo es .xls o
	 * XSSFWorkbook si es .xlsx
	 * 
	 * @param file
	 *            - File Archivo que se va a asociar al libro
	 * @return Workbook con una instancia HSSFWorkbook si el archivo es .xls o
	 *         XSSFWorkbook si es .xlsx
	 * @throws IOException
	 *             en caso de error asociando el archivo al libro
	 */
	public static Workbook getWorkbookByFileName(File file, String fileName)
			throws IOException {
		Workbook wb = null;
		try {
			if (fileName.endsWith(".xls")) {
				wb = new HSSFWorkbook(new FileInputStream(file));
			}
			if (fileName.endsWith(".xlsx")) {
				wb = new XSSFWorkbook(new FileInputStream(file));
			}
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}
		return wb;
	}

	/**
	 * Metodo: obtiene el workbook adecuado para el archivo excel.
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 *             en caso de error asociando el archivo al libro
	 * @author wjimenez
	 */
	public static Workbook getWorkbook(File file) throws Exception {
		Workbook wb = null;
		try {
			wb = new HSSFWorkbook(new FileInputStream(file));
		} catch (Exception e) {
			try {
				wb = new XSSFWorkbook(new FileInputStream(file));
			} catch (Exception ex) {
				throw ex;
			}
		}
		return wb;
	}

	/**
	 * Método: Retorna el string correspondiente al valor de una celda
	 * 
	 * @param cell
	 *            - Cell Celda de la que se va a obtener
	 * @return String con el valor de la celda. En caso que el valor sea nulo se
	 *         retorna una cadena vacía
	 * @author gfandino, wjimenez
	 */
	public static String getCellValue(Cell cell) {
		String cellValue = null;
		if (cell != null) {
			try {
				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					cellValue = cell.getStringCellValue();
					cellValue = (cellValue == null ? "" : cellValue.trim()
							.toUpperCase());
				} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					double doubleVal = cell.getNumericCellValue();
					cellValue = decimalFormat.format(doubleVal);
				} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
					cellValue = Boolean.valueOf(cell.getBooleanCellValue())
							.toString();
				} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
					cellValue = cell.getStringCellValue();
					cellValue = (cellValue == null ? "" : cellValue.trim()
							.toUpperCase());
				}
			} catch (Throwable t) {
				log.error(t.getMessage(), t);
			}
		}
		cellValue = (cellValue == null ? "" : cellValue);
		return cellValue;
	}

	public static boolean isTheSameDateAsToday(Calendar actualTime,
			Date date2Evaluate) {
		Date actualDate = UtilsBusiness.dateTo12am(actualTime.getTime());
		Date date2EvalWoutTime = UtilsBusiness.dateTo12am(date2Evaluate);
		return actualDate.equals(date2EvalWoutTime);
	}

	/**
	 * Método: Retorna la hora actual con hora minuto segundo y milisegundo
	 * 
	 * @return String con la fecha actual
	 * @author gfandino
	 */
	public static String currentTimeToString() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				DATE_FORMAT_YYMMDDHHMMSS);
		String stringDate = dateFormat.format(c.getTime());
		return stringDate;

	}

	/**
	 * Método: Permite validar si una cadena es una expresión regular válida
	 * 
	 * @param regEx
	 *            Expresión regular
	 * @throws PatternSyntaxException
	 *             En caso que se lance una excepcción
	 */
	public static void validateRegEx(String regEx) {
		Pattern.compile(regEx);
	}

	/**
	 * Valida que los parametros sean especificados correctamente y nosean nulos
	 * 
	 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
	 * @param parameters
	 * @throws BusinessException
	 */
	public static void validateParameters(Object... parameters)
			throws BusinessException {
		if (parameters != null) {
			for (Object param : parameters) {
				UtilsBusiness.assertNotNull(param,
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED
								.getMessage());
			}
		}
	}

	/**
	 * Metodo: retorna la fecha que se pasa como parámetro asignandole el último
	 * milisegundo del día
	 * 
	 * @param aDate
	 *            fecha a modificar
	 */
	public static Date getLastMomentOfDay(Date aDate) {
		Date modifiedDate = new Date(aDate.getTime());
		modifiedDate = DateUtils.setHours(modifiedDate, 23);
		modifiedDate = DateUtils.setMinutes(modifiedDate, 59);
		modifiedDate = DateUtils.setSeconds(modifiedDate, 59);
		modifiedDate = DateUtils.setMilliseconds(modifiedDate, 999);
		return modifiedDate;
	}

	/**
	 * Metodo: retorna la fecha que se pasa como parámetro asignandole el primer
	 * milisegundo del día
	 * 
	 * @param aDate
	 *            fecha a modificar
	 */
	public static Date getFirstMomentOfDay(Date aDate) {
		Date modifiedDate = new Date(aDate.getTime());
		modifiedDate = DateUtils.setHours(modifiedDate, 0);
		modifiedDate = DateUtils.setMinutes(modifiedDate, 0);
		modifiedDate = DateUtils.setSeconds(modifiedDate, 0);
		modifiedDate = DateUtils.setMilliseconds(modifiedDate, 0);
		return modifiedDate;
	}

	/**
	 * Metodo: retorna la fecha que se pasa como parámetro asignandole el primer
	 * milisegundo del día
	 * 
	 * @param aDate
	 *            fecha a modificar
	 * @throws PropertiesException
	 */
	public static List<String> getListWarehouseAvailable()
			throws PropertiesException {

		List<String> whTypeCode = new ArrayList<String>();

		whTypeCode.add("'"
				+ CodesBusinessEntityEnum.WAREHOUSE_TYPE_DISPONIBILIDADES
						.getCodeEntity() + "'");
		whTypeCode.add("'"
				+ CodesBusinessEntityEnum.WAREHOUSE_TYPE_S02.getCodeEntity()
				+ "'");
		whTypeCode.add("'"
				+ CodesBusinessEntityEnum.WAREHOUSE_TYPE_S03.getCodeEntity()
				+ "'");
		whTypeCode.add("'"
				+ CodesBusinessEntityEnum.WAREHOUSE_TYPE_CALIDAD
						.getCodeEntity() + "'");
		whTypeCode.add("'"
				+ CodesBusinessEntityEnum.WAREHOUSE_TYPE_S05.getCodeEntity()
				+ "'");
		whTypeCode.add("'"
				+ CodesBusinessEntityEnum.WAREHOUSE_TYPE_S06.getCodeEntity()
				+ "'");
		whTypeCode.add("'"
				+ CodesBusinessEntityEnum.WAREHOUSE_TYPE_S01P.getCodeEntity()
				+ "'");

		return whTypeCode;

	}

	/**
	 * 
	 * Metodo: Compara la informacion de una lista con la informacion de un
	 * array
	 * 
	 * @param listObjects
	 *            Lista de objectos a comparar
	 * @param arrayObjects
	 *            array de objectos a comparar
	 * @return boolean retorna true si existe un objeto de la lista en el array
	 * @throws BusinessException
	 *             <tipo> <descripcion>
	 * @author cduarte
	 */
	public static boolean isListInArray(List<String> listObjects,
			String[] arrayObjects) {

		for (String object : listObjects) {
			for (int i = 0; i < arrayObjects.length; i++) {
				if (object.equals(arrayObjects[i])) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * Metodo: Compara la informacion de una lista con la informacion de un
	 * array
	 * 
	 * @param listObjects
	 *            Lista de objectos a comparar
	 * @param arrayObjects
	 *            array de objectos a comparar
	 * @return boolean retorna true si existe un objeto de la lista en el array
	 * @throws BusinessException
	 *             <tipo> <descripcion>
	 * @author cduarte
	 */
	public static boolean deleteFile(String pathFile) {

		File f = new File(pathFile);
		return f.delete();

	}

	/**
	 * 
	 * Metodo: Lee el archivo de propiedades JmsLocation y retorna la instacia
	 * del properties.
	 * 
	 * @return reader PropertiesReader
	 * @throws PropertiesException
	 * @author Joan Lopez
	 */
	public static PropertiesReader getJmsLocationProperties()
			throws PropertiesException {

		PropertiesReader reader;
		reader = PropertiesReader.getInstance().getInstanceFileSystem(
				Constantes.LABEL_RUTA_JMS_LOCATION);

		return reader;
	}

	public static Long readTimeProperties(String codeEntity, long valueMin) {

		Long timeAllocator = valueMin * 1000;
		String strTime = "";
		try {
			if (codeEntity.equals(TIME_MANAGER))
				strTime = WorkManagerConfigEnum.TIME_MANAGER.getCodeEntity();
			else if (codeEntity.equals(TIME_ALLOCATOR))
				strTime = WorkManagerConfigEnum.TIME_ALLOCATOR.getCodeEntity();

			if (!strTime.equals("") && !strTime.equals("0"))
				timeAllocator = Long.parseLong(strTime) * 1000;

		} catch (PropertiesException e) {
			e.printStackTrace();
		}
		return timeAllocator;

	}

	public static WorkOrderCSR buildWorkOrderCSR(WorkOrderCSRDTO workOrderCSRDTO)
			throws BusinessException {
		WorkOrderCSR workOrderCSR = new WorkOrderCSR();

		workOrderCSR.setId(workOrderCSRDTO.getId());

		if (workOrderCSRDTO.getServiceHourId() != null) {
			ServiceHour serviceHour = new ServiceHour();
			serviceHour.setId(workOrderCSRDTO.getServiceHourId());
			workOrderCSR.setServiceHour(serviceHour);
		}

		workOrderCSR.setContactPerson(workOrderCSRDTO.getContactPerson());

		String descriptionError = workOrderCSRDTO.getDescriptionError();
		if (descriptionError != null && descriptionError.length() > 0) {
			if (descriptionError.length() > 1000) {
				descriptionError = descriptionError.substring(0, 999);
			}
		}
		workOrderCSR.setDescriptionError(descriptionError);
		workOrderCSR.setCommentManagment(workOrderCSRDTO.getCommentManagment());
		workOrderCSR.setDateLastChange(workOrderCSRDTO.getDateLastChange());
		workOrderCSR.setWoCode(workOrderCSRDTO.getWoCode());

		if (workOrderCSRDTO.getActualStatusId() != null) {
			WorkorderStatus woStatus = new WorkorderStatus();
			woStatus.setId(workOrderCSRDTO.getActualStatusId());
			workOrderCSR.setWoStatus(woStatus);
		}

		if (workOrderCSRDTO.getWoReasonId() != null) {
			WorkorderReason woReason = new WorkorderReason();
			woReason.setId(workOrderCSRDTO.getWoReasonId());
			workOrderCSR.setWoReason(woReason);
		}

		workOrderCSR.setScheduleDate(workOrderCSRDTO.getScheduleDate());

		if (workOrderCSRDTO.getCountryId() != null) {
			Country country = new Country();
			country.setId(workOrderCSRDTO.getCountryId());
			workOrderCSR.setCountry(country);
		}

		if (workOrderCSRDTO.getDealerId() != null) {
			Dealer dealer = new Dealer();
			dealer.setId(workOrderCSRDTO.getDealerId());
			workOrderCSR.setDealer(dealer);
		}

		if (workOrderCSRDTO.getUserId() != null) {
			User user = new User();
			user.setId(workOrderCSRDTO.getUserId());
			workOrderCSR.setUser(user);
		}

		if (workOrderCSRDTO.getWorkOrderCSRStatusId() != null) {
			WorkorderCSRStatus workOrderCSRStatus = new WorkorderCSRStatus();
			workOrderCSRStatus.setId(workOrderCSRDTO.getWorkOrderCSRStatusId());
			workOrderCSR.setWorkOrderCSRStatus(workOrderCSRStatus);
		}

		workOrderCSR.setReSchedule(workOrderCSRDTO.isReSchedule() ? "S" : "N");
		workOrderCSR.setAddressCode(workOrderCSRDTO.getAddressCode());
		workOrderCSR.setIbsCustomerCode(workOrderCSRDTO.getIbsCustomerCode());
		workOrderCSR.setIbsBuildingCode(workOrderCSRDTO.getIbsBuildingCode());
		workOrderCSR.setIbsCustomerTypeCode(workOrderCSRDTO
				.getIbsCustomerTypeCode());
		workOrderCSR.setIbsSaleDealerCode(workOrderCSRDTO
				.getIbsSaleDealerCode());
		workOrderCSR.setPostalCode(workOrderCSRDTO.getPostalCode());

		return workOrderCSR;
	}

	public static WorkorderCSRStatus getWorkorderCSRStatusAgen()
			throws HelperException, PropertiesException {
		WorkorderCSRStatus workOrderCSRStatus = new WorkorderCSRStatus();
		Long id = null;
		id = CodesBusinessEntityEnum.CODE_WORKORDER_CSR_STATUS_AGENDADA
				.getIdEntity(WorkorderCSRStatus.class.getName());
		workOrderCSRStatus.setId(id);
		return workOrderCSRStatus;
	}

	public static WorkorderCSRStatus getWorkorderCSRStatusPend()
			throws HelperException, PropertiesException {
		WorkorderCSRStatus workOrderCSRStatus = new WorkorderCSRStatus();
		Long id = null;
		id = CodesBusinessEntityEnum.CODE_WORKORDER_CSR_STATUS_PENDIENTE
				.getIdEntity(WorkorderCSRStatus.class.getName());
		workOrderCSRStatus.setId(id);
		return workOrderCSRStatus;
	}

	public static WorkorderCSRStatus getWorkorderCSRStatusTerm()
			throws HelperException, PropertiesException {
		WorkorderCSRStatus workOrderCSRStatus = new WorkorderCSRStatus();
		Long id = null;
		id = CodesBusinessEntityEnum.CODE_WORKORDER_CSR_STATUS_TERMINADA
				.getIdEntity(WorkorderCSRStatus.class.getName());
		workOrderCSRStatus.setId(id);
		return workOrderCSRStatus;
	}

	public static Long getUserIdAdmin(UserDAOLocal userDao,
			SystemParameterDAOLocal systemParameterDAO, Long countryId) {
		Long userId = null;
		String paramIbs = "";
		String parameterCode = null;
		try {
			parameterCode = CodesBusinessEntityEnum.SYSTEM_PARAM_WORK_ORDER_OWNER_USER_LOGIN
					.getCodeEntity();
			SystemParameter pageSizeParam = systemParameterDAO
					.getSysParamByCodeAndCountryId(parameterCode, countryId);
			if (pageSizeParam != null) {
				paramIbs = pageSizeParam.getValue();
				User ibsUser = userDao.getUserByLoginName(paramIbs, countryId);
				if (ibsUser != null) {
					userId = ibsUser.getId();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(
					"Al tratar de obtener el parámetro del sistema que determina el tiempo de espera para obtener las wo: "
							+ parameterCode, e);
		}
		return userId;
	}

	public static BigDecimal removeDecimal(double valueDecimal, int scale,
			RoundingMode roundingMode) {

		BigDecimal big = new BigDecimal(valueDecimal);
		big = big.setScale(scale, roundingMode);
		return big;
	}

	public static FileResponseDTO generateResponseFromNameFileTempCsv(
			String uniqueName, String fileName, String fileMimeType)
			throws BusinessException {
		try {
			FileResponseDTO response = new FileResponseDTO();
			ByteArrayOutputStream baos = null;
			baos = new ByteArrayOutputStream();
			FileInputStream fileInput;
			fileInput = new FileInputStream(ExcelGenerator.getReportsPathTemp()
					+ uniqueName);

			BufferedInputStream bufferedInput = new BufferedInputStream(
					fileInput);
			byte[] array = new byte[1000];
			int leidos;
			leidos = bufferedInput.read(array);

			while (leidos > 0) {
				baos.write(array, 0, leidos);
				leidos = bufferedInput.read(array);
			}

			DataSource ds = new ByteArrayDataSource(baos.toByteArray(),
					fileMimeType);
			DataHandler dh = new DataHandler(ds);
			response.setDataHandler(dh);
			response.setFileName(fileName);
			bufferedInput.close();
			fileInput.close();
			File fileTemp = new File(ExcelGenerator.getReportsPathTemp()
					+ uniqueName);
			fileTemp.delete();
			return response;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new BusinessException(" No encuentra el archivo temporal ", e);
		} catch (PDFException e) {
			e.printStackTrace();
			throw new BusinessException(" Error obteniendo el path temporal ",
					e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(" Error de entrada y salida ", e);
		}
	}

	/**
	 * Metodo: Genera un archivo CSV por medio de instrospeccion
	 * 
	 * @param dto
	 * @param fielNames
	 * @param columnNames
	 * @param page
	 * @param uniqueName
	 * @param columns
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws PDFException
	 * @throws IOException
	 *             <tipo> <descripcion>
	 * @author
	 */
	public static String generateCsv(List dto, List<String> fielNames,
			List<String> columnNames, int page, String uniqueName,
			String... columns) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			SecurityException, NoSuchMethodException, PDFException, IOException {
		List<String> comillasColumn = new ArrayList<String>();
		if (columns != null && columns.length > 0) {
			for (int i = 0; i < columns.length; ++i) {
				comillasColumn.add(columns[i]);
			}
		}
		if (uniqueName == null) {
			String uniqueNameValue = String.valueOf(Calendar.getInstance()
					.getTimeInMillis());
			uniqueName = uniqueNameValue;
		}
		if (fielNames == null || fielNames.isEmpty() || columnNames == null
				|| columnNames.isEmpty()
				|| columnNames.size() != fielNames.size()) {
			// excepcion
			throw new IllegalArgumentException(
					"No se tienen campos para el archivo, o no se tienen los nombres de columnas o no coinciden sus cantidades");
		}
		List<Method> methods = new ArrayList<Method>();
		List<Integer> incluirComillas = new ArrayList<Integer>();
		int indice = 0;
		if (dto != null && !dto.isEmpty()) {
			for (String fn : fielNames) {
				String nameMethod = "get";
				String nameField = fn;
				char firstLetter = fn.charAt(0);
				if (firstLetter >= 'a' && firstLetter <= 'z') {
					int firstLetterNew = firstLetter - 32;
					char newFirstLetter = (char) firstLetterNew;
					nameField = newFirstLetter + fn.substring(1);
				}
				nameMethod += nameField;
				if (dto.get(0).getClass().getMethod(nameMethod, null) != null) {
					methods.add(dto.get(0).getClass()
							.getMethod(nameMethod, null));
				} else {
					// excepcion
					throw new IllegalArgumentException("No existe el atributo "
							+ fn + " en la clase " + dto.getClass().getName());
				}
				if (comillasColumn.contains(fn)) {
					incluirComillas.add(indice);
				}
				indice++;
			}
		}

		int tamMethods = methods.size();
		File directoryTemp = new File(ExcelGenerator.getReportsPathTemp());
		if (!directoryTemp.exists()) {
			directoryTemp.mkdir();
		}

		FileWriterWithEncoding fichero = new FileWriterWithEncoding(
				ExcelGenerator.getReportsPathTemp() + "report" + uniqueName
						+ ".csv", Constantes.ISO_LATIN_ENCODING, true);
		PrintWriter pw = null;
		pw = new PrintWriter(fichero);
		if (page == 0) {
			int tam = columnNames.size();
			for (int i = 0; i < tam; ++i) {
				pw.write(columnNames.get(i));
				if (i != (tam - 1)) {
					pw.write(";");
				}
			}
			pw.write("\n");
		}
		int tam = 0;
		if (dto != null) {
			tam = dto.size();
		}

		for (int i = 0; i < tam; ++i) {
			for (int j = 0; j < tamMethods; ++j) {
				Object returnValueMethod = methods.get(j).invoke(dto.get(i),
						null);
				if (returnValueMethod != null) {
					if (incluirComillas.contains(j)) {
						pw.write("=\"");
					}
					pw.write(returnValueMethod.toString().replace('\n', ' ')
							.replace(';', ','));
					if (incluirComillas.contains(j)) {
						pw.write("\"");
					}
				}
				if (j != (tamMethods - 1)) {
					pw.write(";");
				}
			}
			pw.write("\n");
		}
		pw.close();
		fichero.close();
		return uniqueName;
	}

	public static String findDomainValue(List<DomainVO> domainsVO,
			String domainValue) {
		for (DomainVO domainVO : domainsVO) {
			if (domainVO.getDomainValue().equals(domainValue)) {
				return domainVO.getDomainValuePar();
			}
		}
		return "";
	}

	public static void createZip(String filename, String fileComplete,
			String nameFile, String internalNameFile) {

		try {
			// Nuestro InputStream

			BufferedInputStream origin = null;

			// Declaramos el FileOutputStream para guardar el archivo

			FileOutputStream dest = new FileOutputStream(filename);

			// Indicamos que será un archivo ZIP

			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
					dest));

			// Indicamos que el archivo tendrá compresión

			out.setMethod(ZipOutputStream.DEFLATED);

			byte data[] = new byte[1000];

			FileInputStream fi = new FileInputStream(fileComplete);

			origin = new BufferedInputStream(fi, 1000);

			ZipEntry entry = new ZipEntry(internalNameFile);

			out.putNextEntry(entry);

			int count;

			// Escribimos el objeto en el ZIP

			while ((count = origin.read(data, 0, 1000)) != -1) {

				out.write(data, 0, count);

			}

			origin.close();

			out.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	/**
	 * Merge multiple pdf into one pdf
	 * 
	 * @param list
	 *            of pdf input stream
	 * @param outputStream
	 *            output file output stream
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static void doMergePdf(List<InputStream> list, File outputFile)
			throws DocumentException, IOException {

		FileOutputStream out = new FileOutputStream(outputFile);

		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, out);
		document.open();
		PdfContentByte cb = writer.getDirectContent();

		for (InputStream in : list) {
			PdfReader reader = new PdfReader(in);
			for (int i = 1; i <= reader.getNumberOfPages(); i++) {
				document.newPage();
				// import the page from source pdf
				PdfImportedPage page = writer.getImportedPage(reader, i);
				// add the page to the destination pdf
				cb.addTemplate(page, 0, 0);
			}
		}

		out.flush();
		document.close();
		out.close();
	}

	/**
	 * Metodo: Envia archivo por SFTP
	 * 
	 * @param fileName
	 * @param file
	 * @param ip
	 * @param puerto
	 * @param sUsername
	 * @param sPassword
	 * @param directorio
	 * @throws Throwable
	 *             <tipo> <descripcion>
	 * @author
	 */
	public static void sendFileSFtp(String fileName, InputStream file,
			String ip, int puerto, String sUsername, String sPassword,
			String directorio) throws Throwable {
		SshClient ssh = new SshClient();
		try {
			ssh.connect(ip, puerto, new IgnoreHostKeyVerification());
			// Autenticar
			PasswordAuthenticationClient pwd = new PasswordAuthenticationClient();
			pwd.setUsername(sUsername);
			pwd.setPassword(sPassword);

			int result = ssh.authenticate(pwd);
			if (result != AuthenticationProtocolState.COMPLETE) {
				throw new BusinessException("Login to " + ip + ":" + puerto
						+ " " + sUsername + "/" + sPassword + " failed");
			}
			// Abrir canal SFTP
			SftpClient client = ssh.openSftpClient();
			// enviar Archivo
			client.cd(directorio);
			client.put(file, fileName);
			// desconectar
			client.quit();
			ssh.disconnect();
		} catch (Throwable ex) {
			log.error(
					"== Error al tratar de ejecutar la operación sendFileSFtp/WorkOrderBusinessBean ==",
					ex);
			throw ex;
		}
	}

	/**
	 * Metodo: Envia archivo por FTP
	 * 
	 * @param localPath
	 * @param file
	 * @param server
	 * @param port
	 * @param user
	 * @param pass
	 * @param remotePath
	 * @throws Throwable
	 *             <tipo> <descripcion>
	 * @author
	 */
	public static void sendFileFtp(String localPath, InputStream file,
			String server, int port, String user, String pass, String remotePath)
			throws Throwable {

		FTPClient client = new FTPClient();

		try {
			client.connect(server, port);
			int reply = client.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				throw new BusinessException("No se conectó al FTP.");
			} else {
				log.info("Se conectó al FTP.");
				if (client.login(user, pass)) {

					client.enterLocalPassiveMode();
					client.setFileType(FTP.BINARY_FILE_TYPE);
					if (!client.storeFile(remotePath + "/" + localPath, file)) {
						throw new BusinessException(
								"No se subió el archivo al FTP.");
					} else {
						if (!client.logout()) {
							throw new BusinessException(
									"No se desconectó del FTP.");
						} else {
							log.info("Se almacenó satisfactoreamente el archivo en el servidor FTP.");
						}
					}
				} else {
					throw new BusinessException("No se conectó al ftp.");
				}

			}

		} catch (Throwable ex) {
			log.error(
					"== Error al tratar de ejecutar la operación sendFileFtp/WorkOrderBusinessBean ==",
					ex);
			throw ex;
		} finally {
			try {
				if (file != null) {
					file.close();
				}
				client.disconnect();
				log.info("Se desconectó del FTP.");
			} catch (IOException e) {
				throw new BusinessException("No se desconectó del FTP.", e);
			}
		}

	}

	/**
	 * Metodo: Genera un archivo CSV por medio de instrospeccion
	 * 
	 * @param dto
	 * @param fielNames
	 * @param columnNames
	 * @param page
	 * @param uniqueName
	 * @param columns
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws PDFException
	 * @throws IOException
	 *             <tipo> <descripcion>
	 * @author
	 */
	public static String generateExcelPOI(List dto, List<String> fielNames,
			List<String> columnNames, int page)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, SecurityException,
			NoSuchMethodException, PDFException, IOException {

		FileOutputStream archivoSalida = null;
		HSSFWorkbook objWB = null;
		HSSFSheet hoja1 = null;
		HSSFRow fila = null;
		HSSFCell celda = null;
		int col;
		int fil;

		Date now = new Date();
		String uniqueName = UtilsBusiness.formatYYYYMMDD(now) + "-"
				+ now.getTime();

		if (fielNames == null || fielNames.isEmpty() || columnNames == null
				|| columnNames.isEmpty()
				|| columnNames.size() != fielNames.size()) {
			throw new IllegalArgumentException(
					"No se tienen campos para el archivo, o no se tienen los nombres de columnas o no coinciden sus cantidades");
		}

		// Por introspeccion se obtienen los get
		List<Method> methods = new ArrayList<Method>();
		List<Integer> incluirComillas = new ArrayList<Integer>();
		int indice = 0;
		if (dto != null && !dto.isEmpty()) {
			for (String fn : fielNames) {
				String nameMethod = "get";
				String nameField = fn;
				char firstLetter = fn.charAt(0);
				if (firstLetter >= 'a' && firstLetter <= 'z') {
					int firstLetterNew = firstLetter - 32;
					char newFirstLetter = (char) firstLetterNew;
					nameField = newFirstLetter + fn.substring(1);
				}
				nameMethod += nameField;
				if (dto.get(0).getClass().getMethod(nameMethod, null) != null) {
					methods.add(dto.get(0).getClass()
							.getMethod(nameMethod, null));
				} else {
					// excepcion
					throw new IllegalArgumentException("No existe el atributo "
							+ fn + " en la clase " + dto.getClass().getName());
				}

				indice++;
			}
		}

		// Se crea el directorio si no existe
		int tamMethods = methods.size();
		File directoryTemp = new File(ExcelGenerator.getReportsPathTemp());
		if (!directoryTemp.exists()) {
			directoryTemp.mkdir();
		}

		// Se coloca el nombre del archivo
		archivoSalida = new FileOutputStream(new File(
				ExcelGenerator.getReportsPathTemp() + "Work_Orders_"
						+ uniqueName + ".xls"), true);
		objWB = new HSSFWorkbook();
		hoja1 = objWB.createSheet("Hoja1");

		fil = 0;
		fila = hoja1.createRow(fil++);
		fila.setHeight((short) 825);

		// Se adicionan los titulos a las columnas del excel
		for (col = 1; col <= columnNames.size(); col++) {

			celda = fila.createCell(col);
			celda.setCellType(HSSFCell.CELL_TYPE_STRING);
			celda.setCellStyle(styleCell(objWB));
			celda.setCellValue(columnNames.get(col - 1));

		}

		int tam = 0;
		if (dto != null) {
			tam = dto.size();
		}

		// Se colocan los datos en el archivo de excel
		for (int i = 0; i < tam; ++i) {
			fila = hoja1.createRow(fil++);
			for (int j = 0; j < tamMethods; ++j) {
				Object returnValueMethod = methods.get(j).invoke(dto.get(i),
						null);
				if (returnValueMethod != null) {
					celda = fila.createCell(j + 1);
					celda.setCellType(HSSFCell.CELL_TYPE_STRING);
					celda.setCellValue(returnValueMethod.toString()
							.replace('\n', ' ').replace(';', ','));
				}
			}
		}

		// Se autoajustan las celdas
		hoja1.setColumnWidth(0, 800);
		for (int i = 1; i <= col; i++) {
			hoja1.setColumnWidth(i, 3200);
		}

		// Se escribe en disco
		objWB.write(archivoSalida);
		archivoSalida.close();

		return uniqueName;
	}

	/**
	 * Metodo: Permite colocar el estilo a la celda del titulo
	 * 
	 * @param objWB
	 * @return <tipo> <descripcion>
	 * @author
	 */
	public static HSSFCellStyle styleCell(HSSFWorkbook objWB) {

		HSSFFont fuente = null;
		HSSFCellStyle estiloCelda = objWB.createCellStyle();
		estiloCelda.setWrapText(true);
		estiloCelda.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		estiloCelda.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		estiloCelda.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		estiloCelda.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		estiloCelda.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		estiloCelda.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);

		estiloCelda.setFillForegroundColor(HSSFColor.BLUE.index);
		estiloCelda.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		fuente = objWB.createFont();
		fuente.setFontHeightInPoints((short) 12);
		fuente.setFontName(HSSFFont.FONT_ARIAL);
		fuente.setColor(HSSFColor.WHITE.index);
		fuente.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		estiloCelda.setFont(fuente);

		return estiloCelda;

	}

	/**
	 * Metodo: Obtiene un FileResponseDTO mediante una lista ReportWorkOrderDTO
	 * utilizando POI
	 * 
	 * @param wos
	 * @return
	 * @throws BusinessException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws PDFException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws IOException
	 *             <tipo> <descripcion>
	 * @author
	 */
	public static FileResponseDTO getFileResponseDTOByReportWorkOrderDTOS(
			List<ReportWorkOrderDTO> wos) throws BusinessException,
			IllegalArgumentException, SecurityException, PDFException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, IOException {

		FileResponseDTO response = new FileResponseDTO();

		List<String> fielNames = new ArrayList<String>();
		fielNames.add("woCode");
		fielNames.add("customerCode");
		fielNames.add("customerDocument");
		fielNames.add("customerName");
		fielNames.add("customerTypeName");

		fielNames.add("customerPhoneHome");
		fielNames.add("customerPhoneWork");
		fielNames.add("customerCel");
		fielNames.add("customerMail");
		fielNames.add("customerFax");

		fielNames.add("customerAddress");
		fielNames.add("extraIndication");
		fielNames.add("dealerName");
		fielNames.add("depotCode");
		fielNames.add("woStateName");
		fielNames.add("woStateCode");
		fielNames.add("ibs6StateName");
		fielNames.add("reason");
		fielNames.add("dateLastModification");
		fielNames.add("model");
		fielNames.add("contDecos");
		fielNames.add("previousVisits");
		fielNames.add("retiredIRDSeries");
		fielNames.add("retiredSCSeries");
		fielNames.add("installedIRDSeries");
		fielNames.add("installedSCSeries");
		fielNames.add("creationDate");
		fielNames.add("importDate");
		fielNames.add("assignDealerDate");
		fielNames.add("contactPersonAgenda");
		fielNames.add("jornada");
		fielNames.add("jornadaDate");
		fielNames.add("woRealizationDate");
		fielNames.add("finalizationDate");
		fielNames.add("observationCompany");
		fielNames.add("woDescription");
		fielNames.add("woAction");
		fielNames.add("customerClass");
		fielNames.add("serialNumber");
		fielNames.add("linkedSerialNumber");
		fielNames.add("serviceTypeName");
		fielNames.add("serviceTypeCode");
		fielNames.add("serviceCode");
		fielNames.add("serviceName");
		fielNames.add("assignDealerCode");
		fielNames.add("assignDealerCodeCode");
		fielNames.add("assignDealer");
		fielNames.add("postalCodeCode");
		fielNames.add("stateName");
		fielNames.add("cityName");
		fielNames.add("zoneTypeName");
		fielNames.add("responsableDoc");
		fielNames.add("ibsTechnical");
		fielNames.add("responsableName");
		fielNames.add("helpers");
		fielNames.add("dispatcher");
		fielNames.add("loginDispatcher");
		fielNames.add("problemDescriptions");
		fielNames.add("isRequiredContract");
		fielNames.add("optimusStatus");
		fielNames.add("optimusStatusDate");
		fielNames.add("optimusDeclineReason");
		fielNames.add("optimusDeclineDate");

		// fielNames.add("woProgrammingDate");

		List<String> columnNames = new ArrayList<String>();
		columnNames.add(ApplicationTextEnum.WO.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.CLIENT_IBS
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.CLIENT_DOCUMENT
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.CLIENT.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.CLIENT_TYPE
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.TELEPHONE_HOUSE
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.TELEPHONE_OFFICE
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.CELL_PHONE
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.MAIL.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.FAX.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.ADDRESS.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.ADDITIONAL_INDICATIONS
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.SELLER.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.DEPOT_SELLER
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.HSP_STATUS
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.IBS_STATUS_CODE
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.IBS_STATUS
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.REASON.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.LAST_MODIFIED_DATE
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.ELEMENT_MODEL
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.DECOS_QUANTITY
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.PREVIOUS_VISITS
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.RETIRED_IRD_SERIE
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.RETIRED_SC_SERIE
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.INSTALLED_IRD_SERIE
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.INSTALLED_SC_SERIE
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.CREATION_DATE
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.PUBLICATION_DATE
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.ASSIGNATION_DATE
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.CONTACT.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.PROGRAMATION_JOURNEY
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.PROGRAMATION_DATE
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.ATTENTION_DATE
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.FINALIZATION_DATE
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.COMPANY_OBSERVATION
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.WO_DESCRIPTION
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.ACTION.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.CUSTOMER_CLASS
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.SERIAL_NUMBER
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.LINKED_SERIAL_NUMBER
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.SERVICE_CATEGORY
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.SERVICE_CATEGORY_CODE
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.SERVICE_CODE
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.SERVICE_NAME
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.DEPOT_INSTALLER_COMPANY
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.INSTALLER_COMPANY_CODE
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.INSTALLER_COMPANY
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.POSTAL_CODE
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.DEPARTMENT
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.CITY.getApplicationTextValue());
		columnNames
				.add(ApplicationTextEnum.PERIMETER.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.RESPONSABLE_DOCUMENT
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.TECHNICAL_CODE
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.RESPONSABLE_CREW
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.AUXILIAR_CREW
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.DISPATCHER_NAME
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.DISPATCHER_USER
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.PROBLEM_DESCRIPTION
				.getApplicationTextValue());
		columnNames.add("Requiere Contrato");
		columnNames.add(ApplicationTextEnum.OPTIMUS_STATUS
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.OPTIMUS_STATUS_DATE
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.OPTIMUS_DECLINE_REASON
				.getApplicationTextValue());
		columnNames.add(ApplicationTextEnum.OPTIMUS_DECLINE_DATE
				.getApplicationTextValue());

		String uniqueName = generateExcelPOI(wos, fielNames, columnNames, 0);
		String fileName = "Work_Orders_" + uniqueName + ".xls";

		DataHandler dh = null;
		DataSource ds = new FileDataSource(new File(
				ExcelGenerator.getReportsPathTemp() + fileName));
		dh = new DataHandler(ds);
		response.setDataHandler(dh);
		response.setFileName(fileName);

		return response;
	}

	/**
	 * Verifica que la fecha de inicio sea menor a la fecha final, en caso
	 * contrario se lanza un excepciones.
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param exceptionCode
	 * @param exceptionMessage
	 * @author martlago
	 */
	public static void assertBeginDateNotLessToDate(Date beginDate,
			Date endDate, String exceptionCode, String exceptionMessage)
			throws BusinessException {

		if (beginDate.after(endDate)) {
			log.error("== La fechas Desde/Hasta estan al reves. ==");
			;
			throw new BusinessException(exceptionCode, exceptionMessage);
		}
	}

	/**
	 * 
	 * Metodo: Obtiene el nombre del reporte
	 * 
	 * @param reportsPath
	 * @param subReportName
	 * @param crewId
	 * @return <tipo> <descripcion>
	 * @author
	 */
	public static StringBuffer generateSingleWOReportName(String reportsPath,
			String subReportName) {
		Date now = new Date();
		StringBuffer reportName = new StringBuffer(reportsPath);
		reportName.append("work_order_");
		if (subReportName != null) {
			reportName.append(subReportName);
			reportName.append("_");
		}
		reportName.append(UtilsBusiness.formatYYYYMMDD(now) + "-"
				+ now.getTime());
		reportName.append(".pdf");

		return reportName;
	}

	/**
	 * Metodo: convierte tipo de datos date ak estandar ISO 8601
	 * 
	 * @param date
	 * @return <tipo> <descripcion>
	 * @author
	 */
	public static String DateToISO8601(final Date date) {
		String formatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
				.format(date);
		return formatted;
	}

	/**
	 * Metodo: Valida si es una fecha valida
	 * 
	 * @param s
	 * @return <tipo> <descripcion>
	 * @author
	 */
	public static boolean isDateValid(String s) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DDMMYYYYHHMMSS);
		sdf.setLenient(false);
		return sdf.parse(s, new ParsePosition(0)) != null;
	}

	/**
	 * Crea un File con el inputStream que recibe por parametro en el directorio
	 * filePath.
	 * 
	 * @param inputStream
	 */
	public static void createFile(String filePath, InputStream inputStream)
			throws BusinessException {
		try {
			OutputStream out = new FileOutputStream(filePath);

			byte[] buf = new byte[1024];
			int len;
			while ((len = inputStream.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			inputStream.close();

			log.info("Datos del inputStream copiados al File");

		} catch (IOException e) {
			throw new BusinessException(
					"No se pudo crear el file en el filePath", e);
		}
	}

	/**
	 * Carga la property con el idioma requerido
	 * 
	 * @param inputStream
	 * @throws PropertiesException
	 */
	public static ResourceBundle getResourceBundle() {
		return ResourceBundleReader.getInstance().getResourceBundle();
	}

	/**
	 * Carga la property con el idioma requerido que luego manipularan los
	 * reportes
	 * 
	 * @param inputStream
	 * @throws PropertiesException
	 */
	public static ResourceBundle getResourceBundleReport()
			throws PropertiesException {
		return ResourceBundleReport.getInstance().getResourceBundle();
	}

	public static HashMap getReportParams() throws PropertiesException {
		HashMap params = new HashMap();
		params.put(JRParameter.REPORT_RESOURCE_BUNDLE,
				getResourceBundleReport());

		return params;
	}

	/**
	 * Verifica que entre la fecha de inicio y la fecha final, no se supere la
	 * cantidad de meses lanza un excepciones.
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param exceptionCode
	 * @param exceptionMessage
	 *            Req.ACM-F-05_HSP_ReportesSC_CC053
	 */
	public static void assertMonthsBetween(Date beginDate, Date endDate,
			String monthsQuantity, String exceptionCode, String exceptionMessage)
			throws BusinessException {
		// assertBeginDateNotLessToDate se llama afuera
		// beginDate y endDate llegan con hora 00:00:00, no se puede cargar hora
		// desde el cal de pantalla
		Integer monthsQuant = Integer.parseInt(monthsQuantity);
		Calendar startCalendar = new GregorianCalendar();
		startCalendar.setTime(beginDate);
		Calendar endCalendar = new GregorianCalendar();
		endCalendar.setTime(endDate);
		Calendar auxCalendar = new GregorianCalendar();
		auxCalendar.setTime(beginDate);
		auxCalendar.add(Calendar.MONTH, monthsQuant);
		if (endCalendar.after(auxCalendar)) {
			log.error("== La fechas Desde/Hasta superan el rango de "
					+ monthsQuantity + " meses. ==");
			;
			throw new BusinessException(exceptionCode, exceptionMessage);
		}

	}

}