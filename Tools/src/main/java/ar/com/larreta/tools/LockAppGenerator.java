package ar.com.larreta.tools;

import java.text.SimpleDateFormat;

public class LockAppGenerator {

	private static String DATE_PATTERN = "dd/MM/yyyy HH:mm";
	
	private static String PROPIETARY = "ElPibeDeLasPruebas";
	private static String LAST_DATE = "01/01/1900 00:00";
	private static String EXPIRATION_DATE = "31/12/2030 00:00";
	private static Long ID = new Long(33);

	public static void main(String[] args) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
			
			LockApp lockApp = new LockApp();
			lockApp.setDateFormatter(dateFormat);
			lockApp.setBase64(new Base64());
			
			lockApp.setPropietary(PROPIETARY);
			lockApp.setLastDate(dateFormat.parse(LAST_DATE));
			lockApp.setExpirationDate(dateFormat.parse(EXPIRATION_DATE));
			lockApp.setIdentifier(ID);
			
			System.out.println(lockApp);
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
