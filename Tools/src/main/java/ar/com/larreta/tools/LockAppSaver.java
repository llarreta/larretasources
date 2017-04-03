package ar.com.larreta.tools;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.annotations.Log;

@Component
@Scope("prototype")
public class LockAppSaver extends Thread {

	private static @Log Logger LOG;
	
	@Autowired
	private LockApp lockApp;
	
	@Override
	public void run() {
		FileWriter fileWriter = null;
        PrintWriter printWriter = null;
        try
        {
            fileWriter = new FileWriter(lockApp.getPath());
            printWriter = new PrintWriter(fileWriter);
            printWriter.write(lockApp.toString());
        } catch (Exception e) {
        	LOG.error("Ocurrio un error escribiendo archivo lock.app", e);
        } finally {
           try {
        	   if (null != printWriter){
        		   printWriter.close();
        	   }
        	   if (null != fileWriter){
        		   fileWriter.close();
        	   }
           } catch (Exception e2) {
        	   LOG.error("Ocurrio un error escribiendo archivo lock.app", e2);
           }
        }
	}

}
