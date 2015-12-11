package ar.com.larreta.commons.exceptions;

import java.io.PrintWriter;
import java.io.StringWriter;

public class AppException extends Exception {

	public static String getStackTrace(Throwable e) {
		StringWriter sWriter = new StringWriter();
		PrintWriter pilaMensajes = new PrintWriter(sWriter);
		e.printStackTrace(pilaMensajes);
		String stackTrace = sWriter.toString();
		return stackTrace;
	}
	
	public String getExceptionStackTrace(){
		return AppException.getStackTrace(this);
	}
	
}
