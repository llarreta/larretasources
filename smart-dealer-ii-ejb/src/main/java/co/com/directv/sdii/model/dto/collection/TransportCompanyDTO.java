package co.com.directv.sdii.model.dto.collection;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.collection.CollectionBase;
import co.com.directv.sdii.model.vo.TransportCompanyVO;

/**
 * 
 * Transporta TransportCompany entre la capa de Business
 * y de Servicios.
 * 
 * Fecha de Creación: 3/02/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class TransportCompanyDTO extends CollectionBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2927781449302237751L;

	private List<TransportCompanyVO> transportCompaniesVO;

	public List<TransportCompanyVO> getTransportCompaniesVO() {
		return transportCompaniesVO;
	}

	public void setTransportCompaniesVO(
			List<TransportCompanyVO> transportCompaniesVO) {
		this.transportCompaniesVO = transportCompaniesVO;
	}
}
