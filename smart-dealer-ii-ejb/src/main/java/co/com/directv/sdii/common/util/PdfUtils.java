package co.com.directv.sdii.common.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 
 * Clase que provee operaciones utilitarias en la administración de archivos
 * pdf para la aplicación.
 * 
 * Fecha de Creación: May 7, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public final class PdfUtils {

	private PdfUtils(){}
	
	private static Log logger = LogFactory.getLog(PdfUtils.class);

	/**
	 * Método que concatena varios archivos pdf para generar uno solo.
	 * @param sourcePdfFileNames Lista con las ubicaciones de los archivos pdf a ser concatenados
	 * @param targetPdfFileName ruta y nombre del archivo resultado de la concatenación
	 * @param paginate indicador para especificar si las páginas del archivo resultado deben ser numeradas
	 */
	public static void concatPDFs(List<String> sourcePdfFileNames, String targetPdfFileName, boolean paginate){
		try {
			List<InputStream> pdfs = new ArrayList<InputStream>();
			for (String inputFileName : sourcePdfFileNames) {
				pdfs.add(new FileInputStream(inputFileName));
			}
			OutputStream output = new FileOutputStream(targetPdfFileName);
			concatPDFs(pdfs, output, paginate);
			
			for (InputStream inputStream : pdfs) {
				inputStream.close();
				inputStream = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Al tratar de concatenar archivos pdf: " + e.getMessage(),e);
			throw new IllegalStateException(e.getMessage());
		}
	}

	/**
	 * Concatena una lista de archivos pdf 
	 * @param streamOfPDFFiles lista con los stream de los archivos pdf origen
	 * @param outputStream stream donde se escribirá el archivo PDF destino
	 * @param paginate
	 */
	private static void concatPDFs(List<InputStream> streamOfPDFFiles, OutputStream outputStream, boolean paginate) {

		Document document = new Document();
		try {
			List<InputStream> pdfs = streamOfPDFFiles;
			List<PdfReader> readers = new ArrayList<PdfReader>();
			int totalPages = 0;
			Iterator<InputStream> iteratorPDFs = pdfs.iterator();

			// Create Readers for the pdfs.
			while (iteratorPDFs.hasNext()) {
				InputStream pdf = iteratorPDFs.next();
				PdfReader pdfReader = new PdfReader(pdf);
				readers.add(pdfReader);
				totalPages += pdfReader.getNumberOfPages();
			}
			// Create a writer for the outputstream
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);

			document.open();
			BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			PdfContentByte cb = writer.getDirectContent(); // Holds the PDF
			// data

			PdfImportedPage page;
			int currentPageNumber = 0;
			int pageOfCurrentReaderPDF = 0;
			Iterator<PdfReader> iteratorPDFReader = readers.iterator();

			// Loop through the PDF files and add to the output.
			while (iteratorPDFReader.hasNext()) {
				PdfReader pdfReader = iteratorPDFReader.next();
				
				// Create a new page in the target for each source page.
				while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
					
					pageOfCurrentReaderPDF++;
					currentPageNumber++;
					page = writer.getImportedPage(pdfReader, pageOfCurrentReaderPDF);
					document.setPageSize(pdfReader.getPageSize(pageOfCurrentReaderPDF));
					document.newPage();
					cb.addTemplate(page, 0, 0);

					// Code for pagination.
					if (paginate) {
						cb.beginText();
						cb.setFontAndSize(bf, 9);
						cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "" + currentPageNumber + " of " + totalPages, 520, 5, 0);
						cb.endText();
					}
				}
				pageOfCurrentReaderPDF = 0;
			}
			outputStream.flush();
			document.close();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Al tratar de concatenar archivos pdf: " + e.getMessage(),e);
			throw new IllegalStateException(e.getMessage());
		} finally {
			if (document.isOpen())
				document.close();
			try {
				if (outputStream != null)
					outputStream.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
	
	/**
	 * Método que permite poblar un archivo en formato pdf que contiene una definición de
	 * formulario, con los parámetros adecuados
	 * @param formParams Mapa de cadenas de caracter, cuya llave es el nombre del campo en el formulario pdf
	 * y cuyo valor es el que se asignará a dicho campo dentro del formulario
	 * @param sourcePdfFormLocation ubicación del archivo formulario origen
	 * @param targetPdfFormLocation ubicación en donde se pondrá el archivo pdf formulario, diligenciado 
	 * por la operación
	 */
	@SuppressWarnings("unchecked")
	public static void fillPdfForm(Map<String, String> formParams, String sourcePdfFormLocation, String targetPdfFormLocation){
		try {
			PdfReader reader = new PdfReader(sourcePdfFormLocation);
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(targetPdfFormLocation), '\0', false);
			AcroFields form = stamper.getAcroFields();
			stamper.setFormFlattening(true);
			stamper.setFreeTextFlattening(true);
			
			Iterator iterator = formParams.entrySet().iterator();
			while (iterator.hasNext()) {
		        Map.Entry entry = (Map.Entry)iterator.next();
		        form.setField((String) entry.getKey(), formParams.get(entry.getKey()));
		    }
			//26-07-2011 se comenta para hacer mejor uso de iterador de mapa
			/*Set<String> formItems = formParams.keySet();
			
			for (String formItemKey : formItems) {
				form.setField(formItemKey, formParams.get(formItemKey));
			}*/
			stamper.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Al tratar de llenar un formulario en pdf: " + e.getMessage(),e);
			throw new IllegalStateException(e.getMessage());
		} catch (DocumentException e) {
			e.printStackTrace();
			logger.error("Al tratar de llenar un formulario en pdf: " + e.getMessage(),e);
			throw new IllegalStateException(e.getMessage());
		}
	}

}
