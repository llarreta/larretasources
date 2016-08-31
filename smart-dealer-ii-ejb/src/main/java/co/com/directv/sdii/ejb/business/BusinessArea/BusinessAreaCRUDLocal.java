package co.com.directv.sdii.ejb.business.BusinessArea;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.BusinessAreaVO;

/**
 * Interfaz de las operaciones Tipo CRUD (Create,Read, Update, Delete) de la
 * Entidad BusinessArea
 *
 * @author ialessan
 * marzo 2014 
 * release_4.2.1.5_Spira_28780 - Configuracion Masiva de Cobertura  de  La  compañía  por  Clase de Cliente
 */

public interface BusinessAreaCRUDLocal {
	
	
    /**
     * Método que permite consultar una CustomerCategory por code.
     */
    public BusinessAreaVO getBusinessAreaByCode(String code) throws BusinessException;


}
