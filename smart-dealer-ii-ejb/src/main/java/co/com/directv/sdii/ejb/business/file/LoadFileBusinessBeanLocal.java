package co.com.directv.sdii.ejb.business.file;

import javax.activation.DataHandler;
import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.UploadFile;
import co.com.directv.sdii.model.vo.LoadFileVO;


/**
 *  
 * 
 * Fecha de Creación: 25/01/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Local
public interface LoadFileBusinessBeanLocal {

    /**
     * Metodo: Permite crear un LoadFile con los parametro de entrada.
     * @param uploadFile
     * @param codeLoadFileType
     * @param dHandler
     * @throws BusinessException <tipo> <descripcion>
     * @author
     */
    public void createLoadFile(UploadFile uploadFile, 
            String codeLoadFileType,
            DataHandler dHandler ) throws BusinessException;
    
    /**
     * Metodo: Permite crear un LoadFile con los parametro de entrada.
     * @param uploadFile
     * @param codeLoadFileType
     * @param arrayByte
     * @throws BusinessException <tipo> <descripcion>
     * @author
     */
    public void createLoadFile(UploadFile uploadFile, 
			                   String codeLoadFileType,
			                   byte[] arrayByte) throws BusinessException;
    
    /**
     * Metodo: Permite consultar los LoadFile de tipo In por id de UploadFile
     * @param idUploadFile
     * @return
     * @throws BusinessException <tipo> <descripcion>
     * @author
     */
    public LoadFileVO getLoadFileByIdUploadFileAndFileIn(Long idUploadFile) throws BusinessException;
    
    /**
     * Metodo: Permite consultar los LoadFile de tipo OUT por id de UploadFile
     * @param idUploadFile
     * @return
     * @throws BusinessException <tipo> <descripcion>
     * @author
     */
    public LoadFileVO getLoadFileByIdUploadFileAndFileOut(Long idUploadFile) throws BusinessException;

}
