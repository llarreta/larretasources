package co.com.directv.sdii.excelgen;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.com.directv.sdii.common.util.ExcelGeneratorLocal;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.commands.ICommandFactory;

/**
 * Servlet implementation class ExcelGeneratorServlet
 */
public class ExcelGeneratorServlet extends HttpServlet {
       
    /**
	 * 
	 */
	private static final long serialVersionUID = -9218139736591233244L;
	
	@EJB
	private ExcelGeneratorLocal excelGeneratorLocal;

	@EJB
	private ICommandFactory commandFactory;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public ExcelGeneratorServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List dataList = null;
		List<String> fieldList = null;
		String[] sheetName = {"Data"};
		String cmd = request.getParameter("cmd");
		String args = request.getParameter("args");
		String fileName = request.getParameter("fileName");
		String reportExtension = request.getParameter("reportExtension");
		ICommand command;
			
		try {
			if(fileName == null || fileName.equals("")) {
				fileName="reporte";
			}
			
			command = commandFactory.getCommand(cmd);
			
			if( reportExtension == null || reportExtension.equals("") ){
				reportExtension = ".xls";
			}
			
			
			dataList = command.execute(args);
			fieldList = command.getFieldList();	
			ByteArrayOutputStream arrayOutputStream = null;
			if( reportExtension.contains("xls") ){
				arrayOutputStream = excelGeneratorLocal.createExcelStreamWithJasper(dataList, fieldList, sheetName,cmd);
				response.setHeader ("Content-Disposition", "attachment;filename=\"" +fileName + ".xls" + "\""); 
				response.setContentType("application/vnd.ms-excel");
			} else {
				arrayOutputStream = excelGeneratorLocal.createPdfStream(dataList);
				response.setHeader ("Content-Disposition", "attachment;filename=\"" +fileName + ".pdf" + "\""); 
				response.setContentType("application/pdf");
			}
			if( arrayOutputStream != null )
				response.setContentLength( arrayOutputStream.toByteArray().length );
			
			ServletOutputStream out = response.getOutputStream ();
			if( arrayOutputStream != null )
				out.write ( arrayOutputStream.toByteArray() );
			out.flush ();
			out.close ();
		} catch (Throwable e) {
			throw new ServletException(e.getMessage(),e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}
