package ar.com.larreta.commons.initializer;

import java.text.SimpleDateFormat;

import ar.com.larreta.commons.utils.impl.Base64Impl;

public class LockAppGenerator {

	private static String DATE_PATTERN = "dd/MM/yyyy HH:mm";
	
	private static String PROPIETARY = "ElPibeDeLasPruebas";
	private static String LAST_DATE = "01/01/1900 00:00";
	private static String EXPIRATION_DATE = "31/12/2030 00:00";

	public static void main(String[] args) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
			
			LockApp lockApp = new LockApp(dateFormat, new Base64Impl());
			lockApp.setPropietary(PROPIETARY);
			lockApp.setLastDate(dateFormat.parse(LAST_DATE));
			lockApp.setExpirationDate(dateFormat.parse(EXPIRATION_DATE));
			
			System.out.println(lockApp);
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
