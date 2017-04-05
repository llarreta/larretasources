package ar.com.larreta.tools;

import java.text.SimpleDateFormat;

public class LockAppReader {

	private static String DATE_PATTERN = "dd/MM/yyyy";
	
	public static void main(String[] args) {
		
		try {
			String path = args[0];
			
			SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
			
			LockApp lockApp = new LockApp();
			lockApp.setDateFormatter(dateFormat);
			lockApp.setBase64(new Base64());
			
			lockApp.setPath(path);
			
			lockApp.decode();

			System.out.println("Informacion de bloqueo de la aplicacion");
			System.out.println("_______________________________________");
			System.out.println("Propietary     :" + lockApp.getPropietary());
			System.out.println("Expiration Date:" + lockApp.getExpirationDate());
			System.out.println("Last Date      :" + lockApp.getLastDate());
			System.out.println("Identifier     :" + lockApp.getIdentifier());

			
		} catch (Exception e){
			e.printStackTrace();
		}
			
	}

}
