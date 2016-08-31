/**
 * Creado 13/07/2011 15:01:58
 */
package co.com.directv.sdii.model.pojo.collection;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.SaleChanel;
import co.com.directv.sdii.model.vo.SaleChanelVO;

/**
 * Encapsula la información de paginación para los canales de venta 
 * 
 * Fecha de Creación: 13/07/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class SaleChannelResponse extends CollectionBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6469936520212259369L;
	
	private List<SaleChanelVO> channelVos;
	
	private List<SaleChanel> channelPojos;

	public List<SaleChanelVO> getChannelVos() {
		return channelVos;
	}

	public void setChannelVos(List<SaleChanelVO> channelVos) {
		this.channelVos = channelVos;
	}

	public List<SaleChanel> getChannelPojos() {
		return channelPojos;
	}

	public void setChannelPojos(List<SaleChanel> channelPojos) {
		this.channelPojos = channelPojos;
	}
	
}
