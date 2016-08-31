package co.com.directv.sdii.ws.queue.test.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.locator.JMSLocator;
import co.com.directv.sdii.dto.esb.dispatchtechnician.DispatchWorkOrder;
import co.com.directv.sdii.dto.esb.dispatchtechnician.DispatchWorkOrderRequest;
import co.com.directv.sdii.dto.esb.dispatchtechnician.DispatchWorkOrderResponse;
import co.com.directv.sdii.dto.esb.dispatchtechnician.DispatchWorkOrderResult;
import co.com.directv.sdii.dto.esb.dispatchtechnician.PhysicalDevice;
import co.com.directv.sdii.dto.esb.dispatchtechnician.PhysicalDeviceCollection;
import co.com.directv.sdii.dto.esb.dispatchtechnician.RequestMetadataType;
import co.com.directv.sdii.dto.esb.dispatchtechnician.ResponseMetadataType;
import co.com.directv.sdii.dto.esb.dispatchtechnician.SenderType;
import co.com.directv.sdii.dto.esb.dispatchtechnician.SharedApplicationCollection;
import co.com.directv.sdii.dto.esb.dispatchtechnician.SharedApplicationItem;
import co.com.directv.sdii.dto.esb.dispatchtechnician.Status;
import co.com.directv.sdii.dto.esb.dispatchtechnician.TargetType;
import co.com.directv.sdii.dto.esb.dispatchtechnician.TelecomTechnician;
import co.com.directv.sdii.dto.esb.dispatchtechnician.WithdrawWorkOrderFromTechnician;
import co.com.directv.sdii.dto.esb.dispatchtechnician.WithdrawWorkOrderFromTechnicianRequest;
import co.com.directv.sdii.dto.esb.dispatchtechnician.WorkOrder;
import co.com.directv.sdii.dto.esb.dispatchtechnician.WorkOrderCollection;
import co.com.directv.sdii.dto.esb.dispatchtechnician.WorkOrderResultCollection;
import co.com.directv.sdii.dto.esb.dispatchtechnician.WorkOrderResultItem;
import co.com.directv.sdii.ejb.business.core.DispatchWorkOrderBusinessLocal;
import co.com.directv.sdii.jms.common.util.DistributedQueueMessage;
import co.com.directv.sdii.ws.queue.test.IQueueTestWS;

@MTOM
@WebService(serviceName="QueueTestWSService",
		endpointInterface="co.com.directv.sdii.ws.queue.test.IQueueTestWS",
		targetNamespace="http://test.queue.ws.sdii.directv.com.co/",
		portName="QueueTestWSPort")
@Stateless()
public class QueueTestWS implements IQueueTestWS {
	
	@EJB(name="DispatchWorkOrderBusinessLocal", beanInterface=DispatchWorkOrderBusinessLocal.class)
	private DispatchWorkOrderBusinessLocal dispatchWorkOrderBusinessBean;
	
	@Override
	public void testQueue(Boolean dispatch, String techincianId, String pin, String woCode, String countryCode, String userId, String systemt){
		try {
			
			RequestMetadataType metadata = new RequestMetadataType();
			metadata.setUserID(userId);
			metadata.setSourceID(countryCode);
			metadata.setSystemID("HSP");
			metadata.setRequestID("4295829627834857283");
			TargetType tt = new TargetType();
			tt.setName(systemt);
			metadata.setTarget(tt);
			
			SenderType st = new SenderType();
			st.setCountry(countryCode);
			metadata.setSender(st);
			
			
			//technician
			TelecomTechnician technician = new TelecomTechnician();
			technician.setID(techincianId);
			PhysicalDeviceCollection physicalDeviceCollection = new PhysicalDeviceCollection();
			PhysicalDevice phyisicalDevice = new PhysicalDevice();
			phyisicalDevice.setBrand(pin);
			phyisicalDevice.setPin(pin);
			phyisicalDevice.setImei(pin);
			phyisicalDevice.setAni(pin);
			phyisicalDevice.setFirmware(pin);
			physicalDeviceCollection.getPhysicalDevice().add(phyisicalDevice);
			technician.setOwnsResource(physicalDeviceCollection);
			
			//Add workorder codes
			WorkOrderCollection woCollection = new WorkOrderCollection();
			WorkOrder esbWo = new WorkOrder();
			esbWo.setID(woCode);
			woCollection.getWorkOrder().add(esbWo);			
			
			DistributedQueueMessage distributedQueueMessage = JMSLocator.getInstance().getQueueDispatchTechnician();

			if(dispatch){				
				DispatchWorkOrder dispatchWorkOrder = new DispatchWorkOrder();
				dispatchWorkOrder.setTechnician(technician);
				dispatchWorkOrder.setWorkOrderCollection(woCollection);
				DispatchWorkOrderRequest request = new DispatchWorkOrderRequest();
				request.setRequestMetadata(metadata);
				request.setDispatchWorkOrder(dispatchWorkOrder);

				JAXBContext jc = JAXBContext.newInstance(co.com.directv.sdii.dto.esb.dispatchtechnician.DispatchWorkOrderRequest.class);
				Marshaller m = jc.createMarshaller();
				java.io.OutputStream os = new java.io.ByteArrayOutputStream();
				m.marshal( request, new java.io.PrintWriter(os) );

				 Map<String,String> properties = new HashMap<String, String>();
				 properties.put(CodesBusinessEntityEnum.DISPATCH_PROPERTY_NAME.getCodeEntity(), CodesBusinessEntityEnum.DISPATCH_PROPERTY_DISPATCH_WO.getCodeEntity());
				 
				 distributedQueueMessage.sendTextMessage(os.toString(), properties);
			}else{
				WithdrawWorkOrderFromTechnician withdrawWo = new WithdrawWorkOrderFromTechnician();
				withdrawWo.setTechnician(technician);
				withdrawWo.setWorkOrderCollection(woCollection);
				WithdrawWorkOrderFromTechnicianRequest request = new WithdrawWorkOrderFromTechnicianRequest();
				request.setRequestMetadata(metadata);
				request.setWithdrawWorkOrderFromTechnician(withdrawWo);	
				
				JAXBContext jc = JAXBContext.newInstance(co.com.directv.sdii.dto.esb.dispatchtechnician.WithdrawWorkOrderFromTechnicianRequest.class);
				Marshaller m = jc.createMarshaller();
				java.io.OutputStream os = new java.io.ByteArrayOutputStream();
				m.marshal( request, new java.io.PrintWriter(os) );
				
				Map<String,String> properties = new HashMap<String, String>();
				properties.put(CodesBusinessEntityEnum.DISPATCH_PROPERTY_NAME.getCodeEntity(), CodesBusinessEntityEnum.DISPATCH_PROPERTY_WITHDRAW_WO_FROM_TECHNICIAN.getCodeEntity());
				 
				distributedQueueMessage.sendTextMessage(os.toString(), properties);

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void execResponse(){
		DispatchWorkOrderResponse response = new DispatchWorkOrderResponse();
		ResponseMetadataType responseMD = new ResponseMetadataType();
		RequestMetadataType requestMD = new RequestMetadataType();
		requestMD.setSourceID("CO");
		requestMD.setRequestID("CO");
		responseMD.setRequestMetadata(requestMD);
		TargetType t = new TargetType();
		t.setName("HSP+");
		requestMD.setTarget(t);
		response.setResponseMetadata(responseMD);
		DispatchWorkOrderResult result = new DispatchWorkOrderResult();
		WorkOrderResultCollection coll = new WorkOrderResultCollection();
		WorkOrderResultItem wo = new WorkOrderResultItem();
		TelecomTechnician tt = new TelecomTechnician();
		tt.setID("12345");
		wo.setTechnician(tt);
		WorkOrder woi = new WorkOrder();
		woi.setID("123456");		
		SharedApplicationCollection shColl = new SharedApplicationCollection();
		SharedApplicationItem shItem = new SharedApplicationItem();
		shItem.setApplicationCode("IBS");
		Status s = new Status();
		s.setCode(0);
		shItem.setStatus(s);
		shColl.getSharedApplicationItem().add(shItem);
		SharedApplicationItem shItem1 = new SharedApplicationItem();
		shItem1.setApplicationCode("OPTIMUS");
		shItem1.setStatus(s);
		shColl.getSharedApplicationItem().add(shItem1);
		wo.setSharedApplicationCollection(shColl);
		wo.setWorkOrder(woi);		
		coll.getWorkOrderResultItem().add(wo);
		result.setWorkOrderResultCollection(coll);
		response.setDispatchWorkOrderResult(result);
		try {
			String myXml = "<v1:DispatchWorkOrderResponse xmlns:v1=\"http://directvla.com/contract/businessdomain/EventListener/v1-0\" xmlns:v11=\"http://directvla.com/schema/util/commondatatypes/v2-0\" xmlns:v12=\"http://directvla.com/schema/businessdomain/common/v1-0\"><v1:responseMetadata><v11:requestMetadata><v11:requestID>1234</v11:requestID><v11:systemID>HSP+</v11:systemID><v11:sourceID>CL</v11:sourceID><v11:sender><v11:country>CL</v11:country></v11:sender><v11:target><v11:name>IBS|OPTIMUS|ALL</v11:name></v11:target></v11:requestMetadata><v11:returnCode>A DEFINIR</v11:returnCode><v11:returnMessage>A DEFINIR</v11:returnMessage><v11:returnType>A DEFINIR</v11:returnType><v11:returnTrace>A DEFINIR</v11:returnTrace></v1:responseMetadata><v1:DispatchWorkOrderResult><v1:WorkOrderResultCollection><v1:WorkOrderResultItem><v1:WorkOrder><v12:ID>18015751</v12:ID></v1:WorkOrder><v1:Technician><v12:OwnsResource><v12:PhysicalDevice><v12:brand></v12:brand><v12:pin></v12:pin><v12:imei></v12:imei><v12:ani></v12:ani><v12:firmware></v12:firmware></v12:PhysicalDevice></v12:OwnsResource><v12:Dealer/><v12:ID>15430170</v12:ID></v1:Technician><v1:SharedApplicationCollection><v1:SharedApplicationItem><v1:ApplicationCode>IBS</v1:ApplicationCode><v1:Status><v1:Code>0</v1:Code></v1:Status></v1:SharedApplicationItem><v1:SharedApplicationItem><v1:ApplicationCode>OPTIMUS</v1:ApplicationCode><v1:Status><v1:Code>-1</v1:Code><v1:Message>Error</v1:Message></v1:Status></v1:SharedApplicationItem></v1:SharedApplicationCollection></v1:WorkOrderResultItem></v1:WorkOrderResultCollection></v1:DispatchWorkOrderResult></v1:DispatchWorkOrderResponse>";
			byte[] byteMsgArr = myXml.getBytes("UTF-8");
			InputStream inStream = new ByteArrayInputStream(byteMsgArr);
			
			dispatchWorkOrderBusinessBean.processDispatchWorkOrderResponse(response, byteMsgArr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
}

