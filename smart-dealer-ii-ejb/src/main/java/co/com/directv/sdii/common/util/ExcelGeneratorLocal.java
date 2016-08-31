package co.com.directv.sdii.common.util;
import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.ejb.Local;

import org.apache.poi.ss.usermodel.Workbook;

import co.com.directv.sdii.exceptions.ExcelGenerationException;

/**
 * Interfaz que define los métodos para generar archivos de excel
 * a partir de una lista de objetos recibidos.
 *
 * Fecha: 11-02-2011
 * 
 * @author jvelezmu
 *
 */
@Local
public interface ExcelGeneratorLocal {

	/**
	 * Método que genera un archivo de excel a partir de una lista de
	 * objetos recibidos por parámetro.
	 * 
	 * @param dataList Lista separada por pipes (|) que contiene los datos a visualizar en excel
	 * @param sheetName Nombre de la hoja de cálculo
	 * @param fieldList Lista de nombres de campos a visualizar como encabezado de columna en la hoja de excel
	 * @return  Workbook
	 * @throws ExcelGenerationException
	 */
	public Workbook createExcelFile(List<String> dataList, List<String> fieldList, String sheetName) throws ExcelGenerationException;
	
	/**
	 * Method: 
	 * @param dataList List
	 * @param fieldList List<String>
	 * @param sheetName String[]
	 * @param command String
	 * @return String, ruta en la cual se genero el archivo en disco
	 * @throws ExcelGenerationException
	 */
	public String createExcelFileWithJasper(List dataList, List<String> fieldList, String[] sheetName,String command, String... pathAndName ) throws ExcelGenerationException;
	
	/**
	 * Method: 
	 * @param dataList List
	 * @param fieldList List<String>
	 * @param sheetName String[]
	 * @param command String
	 * @return ByteArrayOutputStream, stream de bytes del reporte generado
	 * @throws ExcelGenerationException
	 */
	public ByteArrayOutputStream createExcelStreamWithJasper(List dataList, List<String> fieldList, String[] sheetName,String command) throws ExcelGenerationException;
	
	/**
	 * Metodo: Genera un outputStream que contiene los archivos pdf generados en la capa ed negocio
	 * @param dataList	 
	 * @return
	 * @throws ExcelGenerationException
	 */
	public ByteArrayOutputStream createPdfStream(List<String> dataList) throws ExcelGenerationException;

	/**
	 * Metodo: Genera un reporte en PDF a partir de una plantilla de Jasper Report
	 * @param dataList Lista con los datos para llenar el pdf
	 * @param command nombre del comando a ejecutar debe ser igual al nombre del archivo .jasper que se encuentra en la carpeta de plantillas del sistema
	 * @return Stream con los baytes de un archivo pdf
	 * @throws ExcelGenerationException En caso de error al ejecutar el reporte o no encontrar la plantilla .jasper
	 * @author cduarte
	 */
	public ByteArrayOutputStream createPdfStreamWithJasper(List dataList,String command) throws ExcelGenerationException;
	
    public void populateExcelFile(List<String> dataField,List<String> dataList,String originalWorkBookName, int sheetNumber)
	throws ExcelGenerationException ;
	
}
