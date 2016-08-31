package co.com.directv.sdii.ws.attachements;

//import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataHandler;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.MTOM;

@MTOM
@WebService
@Stateless
public class WSTestAttachment {
	

	@WebMethod
	public void hello(String name, @XmlMimeType("application/octet-stream") DataHandler data) {
		try {
			
			/**
            StreamingDataHandler dh = (StreamingDataHandler)data;			 
			**/
			
            DataHandler dh = data;
            File file = new File(name);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
           
            //IOUtils.copy(dh.getInputStream(), fos);
            copy(dh.getInputStream(), fos);
            fos.flush();
            fos.close();
            
            //Close the output stream
            //dh.close();
       } catch(Exception e) {
            throw new WebServiceException(e);
       }
	}
	
	private static final int IO_BUFFER_SIZE = 4 * 1024;  
	   
	private static void copy(InputStream in, OutputStream out) throws IOException {  
		byte[] b = new byte[IO_BUFFER_SIZE];  
		int read;  
	    while ((read = in.read(b)) != -1) {  
	       out.write(b, 0, read);  
	    }  
	}  

	
}