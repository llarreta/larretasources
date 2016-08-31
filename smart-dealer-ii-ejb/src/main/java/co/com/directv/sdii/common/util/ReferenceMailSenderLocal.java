package co.com.directv.sdii.common.util;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.model.pojo.RefInconsistency;
import co.com.directv.sdii.model.pojo.Reference;
import co.com.directv.sdii.model.vo.RefInconsistencyVO;
import co.com.directv.sdii.model.vo.ReportedElementVO;

@Local
public interface ReferenceMailSenderLocal {

	/**
	 * Metodo: Envía el correo de notificación de cierre de una inconsistencia de remisión
	 * por mas elementos físicos
	 * @param refInconsistencyVO
	 * @param reportedElementVOs
	 * @author wjimenez
	 */
	public void sendRefInconsistencyMoreElementsClosedMail(RefInconsistencyVO refInconsistencyVO, List<ReportedElementVO> reportedElementVOs);
	
	/**
	 * Metodo: Envía el correo de notificación de cierre de una inconsistencia de remisión
	 * por menos elementos físicos
	 * @param refInconsistencyVO
	 * @param devolutionReference
	 * @param reportedElementVOs
	 * @author wjimenez
	 */
	public void sendRefInconsistencyLessElementsClosedMail(RefInconsistencyVO refInconsistencyVO, Reference devolutionReference, List<ReportedElementVO> reportedElementVOs);

	/**
	 * Metodo: Envía el correo de notificación de creación de una inconsistencia de remisión
	 * @param refInconsistency
	 * @param reportedElementVOs
	 * @author wjimenez
	 */
	public void sendRefInconsistencyCreatedMail(RefInconsistency refInconsistency,
			List<ReportedElementVO> reportedElementVOs);
	
}
