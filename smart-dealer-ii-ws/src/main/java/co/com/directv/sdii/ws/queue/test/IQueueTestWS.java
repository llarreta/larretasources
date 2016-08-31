package co.com.directv.sdii.ws.queue.test;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name="QueueTestWS",targetNamespace="http://test.queue.ws.sdii.directv.com.co/")
public interface IQueueTestWS {

	@WebMethod(operationName="testQueue", action="testQueue")
	public void testQueue(@WebParam(name="dispatch") Boolean dispatch,
							@WebParam(name="techincianId") String techincianId, 
							@WebParam(name="pin") String pin, 
							@WebParam(name="woCode") String woCode, 
							@WebParam(name="countryCode") String countryCode, 
							@WebParam(name="userId") String userId,
							@WebParam(name="systemt") String systemt);
	
	@WebMethod(operationName="execResponse", action="execResponse")
	public void execResponse();
	
}
