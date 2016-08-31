package co.com.directv.sdii.model.pojo.collection;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.TransportCompany;

/**
 * 
 * Transporta TransportCompany entre los componentes
 * DAO y Business.
 * 
 * Fecha de Creación: 3/02/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class TransportCompanyResponse extends CollectionBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1783928054845724076L;

	private List<TransportCompany> transportCompanies;

	public List<TransportCompany> getTransportCompanies() {
		return transportCompanies;
	}

	public void setTransportCompanies(
			List<TransportCompany> transportCompanies) {
		this.transportCompanies = transportCompanies;
	}	
}
