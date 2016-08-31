/**
 * 
 */
package co.com.directv.sdii.facade.core.impl;

import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.facade.core.CoreWOAttentionsFacadeLocal;

/**
 * @author jvelezmu
 *
 */

//@Stateless(name="CoreWOAttentionsFacadeLocal",mappedName="ejb/CoreWOAttentionsFacadeLocal")
//@TransactionAttribute(TransactionAttributeType.REQUIRED)
//@TransactionManagement(TransactionManagementType.CONTAINER)
public class CoreWOAttentionsFacadeBean extends BusinessBase implements CoreWOAttentionsFacadeLocal{

//	@EJB(name="CoreWOAttentionsBusinessHelperLocal", beanInterface=TrayManagmentBusinessHelperLocal.class)
//	private TrayManagmentBusinessHelperLocal business;
//
//	/* (non-Javadoc)
//	 * @see co.com.directv.sdii.facade.core.CoreWOAttentionsFacadeLocal#activarElementosEnIBS(co.com.directv.sdii.ws.model.dto.WOAttentionElementsRequestDTO)
//	 */
//	@Override
//	@TransactionAttribute(TransactionAttributeType.REQUIRED)
//	public void activarElementosEnIBS(WOAttentionElementsRequestDTO request)
//			throws BusinessException {
//		business.activarElementosEnIBS(request);
//
//	}
//
//
//	/* (non-Javadoc)
//	 * @see co.com.directv.sdii.facade.core.CoreWOAttentionsFacadeLocal#actualizarCambioDeEstadoWO(co.com.directv.sdii.ws.model.dto.WOAttentionsRequestDTO)
//	 */
//	@Override
//	@TransactionAttribute(TransactionAttributeType.REQUIRED)
//	public void actualizarCambioDeEstadoWO(WOAttentionsRequestDTO request)
//			throws BusinessException {
//		business.actualizarCambioDeEstadoWO(request);
//
//	}
//
//
//	/* (non-Javadoc)
//	 * @see co.com.directv.sdii.facade.core.CoreWOAttentionsFacadeLocal#atenderService(co.com.directv.sdii.ws.model.dto.ServiceAttentionRequestDTO)
//	 */
//	@Override
//	@TransactionAttribute(TransactionAttributeType.REQUIRED)
//	public ServiceAttendResponseDTO atenderService(
//			ServiceAttentionRequestDTO request) throws BusinessException {
//		//return business.atenderService(request);
//		return null;
//	}
//
//
//	/* (non-Javadoc)
//	 * @see co.com.directv.sdii.facade.core.CoreWOAttentionsFacadeLocal#crearNuevaWO(co.com.directv.sdii.ws.model.dto.CreateNewWORequestDTO)
//	 */
//	@Override
//	@TransactionAttribute(TransactionAttributeType.REQUIRED)
//	public void crearNuevaWO(CreateNewWORequestDTO request)
//			throws BusinessException {
//		business.crearNuevaWO(request);
//
//	}
//
//
//	/* (non-Javadoc)
//	 * @see co.com.directv.sdii.facade.core.CoreWOAttentionsFacadeLocal#informarCambioDeIRDIBS(co.com.directv.sdii.ws.model.dto.WOAttentionElementsRequestDTO)
//	 */
//	@Override
//	@TransactionAttribute(TransactionAttributeType.REQUIRED)
//	public void informarCambioDeIRDIBS(WOAttentionElementsRequestDTO request)
//			throws BusinessException {
//		business.informarCambioDeIRDIBS(request);
//
//	}
//
//
//	/* (non-Javadoc)
//	 * @see co.com.directv.sdii.facade.core.CoreWOAttentionsFacadeLocal#reportarElementosRecuperadosEnServicios(co.com.directv.sdii.ws.model.dto.WOAttentionElementsRequestDTO)
//	 */
//	@Override
//	@TransactionAttribute(TransactionAttributeType.REQUIRED)
//	public void reportarElementosRecuperadosEnServicios(
//			WOAttentionElementsRequestDTO request) throws BusinessException {
//		business.reportarElementosRecuperadosEnServicios(request);
//
//	}
//
//
//	/* (non-Javadoc)
//	 * @see co.com.directv.sdii.facade.core.CoreWOAttentionsFacadeLocal#reportarElementosUtilizadosEnServicios(co.com.directv.sdii.ws.model.dto.WOAttentionElementsRequestDTO)
//	 */
//	@Override
//	@TransactionAttribute(TransactionAttributeType.REQUIRED)
//	public void reportarElementosUtilizadosEnServicios(
//			WOAttentionElementsRequestDTO request) throws BusinessException {
//		business.reportarElementosUtilizadosEnServicios(request);
//
//	}
//
//
//	/* (non-Javadoc)
//	 * @see co.com.directv.sdii.facade.core.CoreWOAttentionsFacadeLocal#validarDatosWOAttentions(co.com.directv.sdii.ws.model.dto.WOAttentionsRequestDTO)
//	 */
//	@Override
//	@TransactionAttribute(TransactionAttributeType.REQUIRED)
//	public void validarDatosWOAttentions(WOAttentionsRequestDTO request)
//			throws BusinessException {
//		business.validarDatosWOAttentions(request);
//
//	}
//
//
//	/* (non-Javadoc)
//	 * @see co.com.directv.sdii.facade.core.CoreWOAttentionsFacadeLocal#compensarWOAttention(co.com.directv.sdii.ws.model.dto.WOAttentionsRequestDTO)
//	 */
////	@Override
////	@TransactionAttribute(TransactionAttributeType.REQUIRED)
////	public void compensarWOAttention(WOAttentionsRequestDTO request)throws BusinessException {
////		business.compensarWOAttention(request);
////	}
//
//
//	/* (non-Javadoc)
//	 * @see co.com.directv.sdii.facade.core.CoreWOAttentionsFacadeLocal#compensarNotificacionIbsElementosSerializados(co.com.directv.sdii.ws.model.dto.WOAttentionsRequestDTO, co.com.directv.sdii.ws.model.dto.ReportarIBSElementosSerializadosResponseDTO)
//	 */
//	@Override
//	public void compensarNotificacionIbsElementosSerializados(
//			WOAttentionsRequestDTO request,
//			ReportarIBSElementosSerializadosResponseDTO elementsNotified)
//			throws BusinessException {
//		business.compensarNotificacionIbsElementosSerializados(request, elementsNotified);
//	}
//
//
//	/* (non-Javadoc)
//	 * @see co.com.directv.sdii.facade.core.CoreWOAttentionsFacadeLocal#notificarIbsCambioDeEstadoWO(co.com.directv.sdii.ws.model.dto.WOAttentionsRequestDTO)
//	 */
//	@Override
//	public void notificarIbsCambioDeEstadoWO(WOAttentionsRequestDTO request)
//			throws BusinessException {
//		business.notificarIbsCambioDeEstadoWO(request);
//	}
//
//
//	/* (non-Javadoc)
//	 * @see co.com.directv.sdii.facade.core.CoreWOAttentionsFacadeLocal#reportarIBSElementosSerializados(co.com.directv.sdii.ws.model.dto.WOAttentionElementsRequestDTO)
//	 */
//	@Override
//	public ReportarIBSElementosSerializadosResponseDTO reportarIBSElementosSerializados(
//			WOAttentionElementsRequestDTO request) throws BusinessException {
//		return business.reportarIBSElementosSerializados(request);
//	}

}
