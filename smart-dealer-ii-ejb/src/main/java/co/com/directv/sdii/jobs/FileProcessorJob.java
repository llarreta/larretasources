/**
 * 
 */
package co.com.directv.sdii.jobs;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.CountriesFacadeBeanRemote;
import co.com.directv.sdii.facade.file.FileProcessorFacadeRemote;

/**
 * Esta clase ejecuta el Job para el procesamieto de archivos
 * Implementa StatefulJob de Quartz Scheduler.
 * @author Gustavo Fandiño
 * 
 *
 */
public class FileProcessorJob extends BusinessBase implements StatefulJob {

	private final static Logger logger = UtilsBusiness.getLog4J(FileProcessorJob.class);
	private FileProcessorFacadeRemote fileProcessorFacade;
	private CountriesFacadeBeanRemote countriesFacade;
	
	private String codeCountry;


	public FileProcessorFacadeRemote getFileProcessorFacade() {
		return fileProcessorFacade;
	}

	public void setFileProcessorFacade(FileProcessorFacadeRemote fileProcessorFacade) {
		this.fileProcessorFacade = fileProcessorFacade;
	}
	

	public CountriesFacadeBeanRemote getCountriesFacade() {
		return countriesFacade;
	}

	public void setCountriesFacade(CountriesFacadeBeanRemote countriesFacade) {
		this.countriesFacade = countriesFacade;
	}
	
	

	public String getCodeCountry() {
		return codeCountry;
	}

	public void setCodeCountry(String codeCountry) {
		this.codeCountry = codeCountry;
	}

	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
//		try {
//			processFile();
//		} catch (BusinessException e) {
//			e.printStackTrace();
//			throw new JobExecutionException(e.getMessageCode(), e, false);
//		}
	}
	
	/**
	 * Método: Genera el procesamiento de archivos
	 * @throws BusinessException en caso de error procesando los archivos
	 * @author gfandino
	 */
	private void processFile() throws BusinessException{
		
		logger.info("== Inicia processFile/FileProcessorJob ==");
		
		if(fileProcessorFacade == null){
			logger.error("No se ha asignado la fachada de fileProcessorFacade para procesar los archivos");
			return;
		}
		if(countriesFacade == null){
			logger.error("No se ha asignado la fachada de countriesFacade para consultar el país respectivo");
			return;
		}
		fileProcessorFacade.processFiles(countriesFacade.getCountriesByCode(codeCountry).getId());
	}
	

}
