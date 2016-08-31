package co.com.directv.sdii.ws.handler;

import java.util.Date;

import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;

public class LogicalHandlerBusinessWS extends BaseHandler<LogicalMessageContext> implements LogicalHandler<LogicalMessageContext> {

	private static final String HANDLER_NAME = "LogicalHandlerBusinessWS";
	Date startTime, endTime;
	
	public LogicalHandlerBusinessWS() {
		super();
		super.setHandlerName(HANDLER_NAME);
	}
	
	@Override
	public boolean handleMessage(LogicalMessageContext context) {
		
		Boolean outboundProperty = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

		out.println("------------------------------------");
		out.println("In LogicalHandler " + HandlerName + ":handleMessage()");
		
		if (outboundProperty.booleanValue()) {
			out.println("\ndirection = outbound ");
			startTime = new Date();
			context.put(HandlerName + "StartTime", startTime);
		} else {
			out.println("\ndirection = inbound ");
			try {
				startTime = (Date) context.get(HandlerName + "StartTime");
				endTime = new Date();
				long elapsedTime = endTime.getTime() - startTime.getTime();
				
				context.put("ELAPSED_TIME", elapsedTime);
				context.setScope("ELAPSED_TIME", MessageContext.Scope.APPLICATION);
				
				out.println("");
				out.println("Elapsed time in handler " + HandlerName + " is "
						+ elapsedTime);
				out.println("");
			} catch (Exception x) {
				x.printStackTrace();
			}
		}
		out.println("Exiting LogicalHandler " + HandlerName
				+ ":handleMessage()");
		out.println("------------------------------------");
		
		return false;
	}

}
