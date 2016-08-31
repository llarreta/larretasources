package co.com.directv.sdii.exceptions;

import java.util.List;

public class ScheduleException extends BusinessException{

	private static final long serialVersionUID = 8794154458972734162L;
	
	public ScheduleException() {
		super();
	}

	public ScheduleException(String message, Throwable cause){
		super(message, cause);
	}
	
	public ScheduleException(String message) {
		super(message);
	}
        
    public ScheduleException(String codeMessage, String message, Throwable cause) {
        super(codeMessage,message, cause);
    }

	public ScheduleException(String codeMessage,String message) {
        super(codeMessage,message);
    }
	
	public ScheduleException(String codeMessage, String message, List<String> parameters) {
        super(codeMessage, message, parameters);
    }

}
