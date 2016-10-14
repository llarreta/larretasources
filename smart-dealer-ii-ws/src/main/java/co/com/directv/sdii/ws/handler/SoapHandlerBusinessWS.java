package co.com.directv.sdii.ws.handler;

import java.util.Collections;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 * 
 * <Descripcion> 
 * 
 * Fecha de Creación: 23/04/2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see javax.xml.ws.handler.soap.SOAPHandler
 * @see javax.xml.ws.handler.soap.SOAPMessageContext
 * @see javax.xml.ws.handler.MessageContext
 */
public class SoapHandlerBusinessWS extends BaseHandler<SOAPMessageContext> implements SOAPHandler<SOAPMessageContext> {
	
		
	private final String HANDLER_NAME = "ClientAuthenticationSOAPHandler";

	public SoapHandlerBusinessWS() {
		super();
		super.setHandlerName(HANDLER_NAME);
	}
	
	public Set<QName> getHeaders() {
		System.out.println("getHeaders");
		 return Collections.emptySet();
	}
		
	public boolean handleMessage(SOAPMessageContext context) {
		
		 Boolean outboundProperty = (Boolean)context.get (MessageContext.MESSAGE_OUTBOUND_PROPERTY);     
		 SOAPEnvelope envelope;
		 if (outboundProperty.booleanValue()) {         
			 System.out.println("\nOutbound message:");  			 
			 
			 try {
				envelope = context.getMessage().getSOAPPart().getEnvelope();
				
				// create instance of SOAP factory
				SOAPFactory soapFactory = SOAPFactory.newInstance();

				// create SOAP elements specifying prefix and URI				
				SOAPElement headerElm = soapFactory.createElement(
						"authnHeader",
						"nsAuthn",
						"");
				SOAPElement idElm = soapFactory.createElement("id",
						"nsAuthn",
						"");

				// attach value to id element
				idElm.addTextNode("authn_userid");	

				// add child elements to the root element
				headerElm.addChildElement(idElm);		

				// create SOAPHeader instance for SOAP envelope
				SOAPHeader sh = envelope.addHeader();

				// add SOAP element for header to SOAP header object
				sh.addChildElement(headerElm);
			} catch (SOAPException e) {
				e.printStackTrace();
			}
			 
		 } else {         
			 System.out.println("\nInbound message:");     
			 try {
				envelope = context.getMessage().getSOAPPart().getEnvelope();
				System.out.println("\nexit Inbound message:");  
			} catch (SOAPException e) {
				e.printStackTrace();
			}
		 }     
		 System.out.println("** Response : "+context.getMessage().toString());    	 	 
		 
		try {
				SOAPMessage soapMessage = context.getMessage();
				SOAPPart soapPart = context.getMessage().getSOAPPart();
				SOAPEnvelope soapEnvelope = soapPart.getEnvelope();						
				SOAPBody soapBody = soapEnvelope.getBody();
				SOAPHeader soapHeader = soapEnvelope.getHeader(); 
				System.out.println("soapPart->"+soapPart.toString());
				System.out.println("soapEnvelope->"+soapEnvelope.toString());
				System.out.println("soapMessage->"+soapMessage.toString());
				System.out.println("soapBody->"+soapBody.toString());
				System.out.println("soapHeader->"+soapHeader.toString());
		} catch (SOAPException e) {
			e.printStackTrace();
		}
		 
		 return true;		
	}
	
	public boolean handleRequest(MessageContext context) {
		
		SOAPMessageContext messageContext = (SOAPMessageContext) context;
		System.out.println("\nRequest :"+messageContext.getMessage());
		return true;
	}
	
	public boolean handleResponse(MessageContext context) {
		
		SOAPMessageContext messageContext = (SOAPMessageContext) context;
		System.out.println("\nResponse :"+messageContext.getMessage());
		return true;
	}
}
