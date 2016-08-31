/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.com.directv.sdii.facade.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.ejb.business.dealers.impl.DealersBusinessBean;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DealerVO;

/**
 * Interfaz de Negocio, especializada en operaciones diferentes
 * a las de tipo CRUD.
 * Entidad Dealers
 * @author Joan Lopez
 * @version 1.0
 * @see DealersBusinessBean
 */
@Local
public interface DealersFacadeBusinessBeanLocal {

    /**
     * Lista las compañias
     * @param countryId identificador del país sobre el que se realiza la consulta
     * @return List<DealersDTO>
     * @throws BusinessException
     */
    public abstract List<DealerVO> getDealerCodes(Long countryId) throws BusinessException;

   /**
    *
    * Metodo: <Descripcion>
    * @param dealerCode
    * @param depodCode
    * @param countryId Identificador del país sobre el que se realiza la consulta
    * @return DealersDTO
    * @throws BusinessException <tipo> <descripcion>
    * @author jalopez
    */
    public abstract DealerVO getDealer(Long dealerCode,String depodCode, Long countryId) throws BusinessException;
}
