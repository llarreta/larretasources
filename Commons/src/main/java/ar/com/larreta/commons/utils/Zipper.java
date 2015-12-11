package ar.com.larreta.commons.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.springframework.util.FileCopyUtils;

import ar.com.larreta.commons.AppObject;
import ar.com.larreta.commons.AppObjectImpl;

/**
 * Utiliza la memoria para zippear
 * Si lo que esta procesando es muy grande puede haber problemas
 * Se soluciona haciendo una implementacion del zipper que no utilice ByteArrayOutputStream
 * y que lo haga directamente en disco
 * @author leonel.g.larreta
 *
 */
public class Zipper extends AppObjectImpl {
	
	private static final AppObject APP_OBJECT = new AppObjectImpl(Zipper.class);
	
    /**
     * This method zips the directory
     * @param dir
     * @param zipDirName
     */
    public static void zipDirectory(File dir, String zipDirName) {
    	try {
            FileOutputStream fos = new FileOutputStream(zipDirName);
            ByteArrayOutputStream os = zipDirectory(dir);
            
            FileCopyUtils.copy(os.toByteArray(), fos);
        } catch (IOException e) {
        	APP_OBJECT.getLog().error(e);
        }
    }

    /**
     * This method zips the directory
     * @param dir
     * @param zipDirName
     */
    public static ByteArrayOutputStream zipDirectory(File dir) {
    	List<String> filesListInDir = new ArrayList<String>();
    	try {
            populateFilesList(dir, filesListInDir);
            //now zip files one by one
            //create ZipOutputStream to write to the zip file
            ByteArrayOutputStream fos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(fos);
            for(String filePath : filesListInDir){
            	APP_OBJECT.getLog().debug("Zipping "+filePath);
                //for ZipEntry we need to keep only relative file path, so we used substring on absolute path
                ZipEntry ze = new ZipEntry(filePath.substring(dir.getAbsolutePath().length()+1, filePath.length()));
                zos.putNextEntry(ze);
                //read the file and write to ZipOutputStream
                FileInputStream fis = new FileInputStream(filePath);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
                fis.close();
            }
            zos.close();
            fos.close();
            
            return fos;
        } catch (IOException e) {
        	APP_OBJECT.getLog().error(e);
        }
    	
    	return null;
    }
    
    /**
     * This method populates all the files in a directory to a List
     * @param dir
     * @throws IOException
     */
    private static void populateFilesList(File dir, List<String> filesListInDir) throws IOException {
        File[] files = dir.listFiles();
        for(File file : files){
            if(file.isFile()){ 
            	filesListInDir.add(file.getAbsolutePath());
            } else { 
            	populateFilesList(file, filesListInDir);
            }
        }
    }
 
    /**
     * This method compresses the single file to zip format
     * @param file
     * @param zipFileName
     */
    public static void zipSingleFile(File file, String zipFileName) {
        try {
            FileOutputStream fos = new FileOutputStream(zipFileName);
            ByteArrayOutputStream os = zipSingleFile(file);
            
            FileCopyUtils.copy(os.toByteArray(), fos);
        } catch (IOException e) {
        	APP_OBJECT.getLog().error(e);
        }
    }

    /**
     * This method compresses the single file to zip format
     * @param file
     * @param zipFileName
     */
    public static ByteArrayOutputStream zipSingleFile(File file) {
        try {
            //create ZipOutputStream to write to the zip file
            ByteArrayOutputStream fos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(fos);
            //add a new Zip Entry to the ZipOutputStream
            ZipEntry ze = new ZipEntry(file.getName());
            zos.putNextEntry(ze);
            //read the file and write to ZipOutputStream
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
             
            //Close the zip entry to write to zip file
            zos.closeEntry();
            //Close resources
            zos.close();
            fis.close();
            fos.close();
            APP_OBJECT.getLog().debug(file.getCanonicalPath()+" is zipped");
            
            return fos; 
        } catch (IOException e) {
        	APP_OBJECT.getLog().error(e);
        }
        return null;
    }
    
    public static File unzip(String zipFile, String outputFolder){
    	try {
			return unzip(new FileInputStream(zipFile), outputFolder);
		} catch (FileNotFoundException e) {
			APP_OBJECT.getLog().error(e);
		}
    	return null;
    }
    
    public static File unzip(InputStream zipFile, String outputFolder){
 
     byte[] buffer = new byte[1024];
 
     try{
 
    	//create output directory is not exists
    	File folder = new File(outputFolder);
    	if(!folder.exists()){
    		folder.mkdir();
    	}
 
    	//get the zip file content
    	ZipInputStream zis = new ZipInputStream(zipFile);
    	//get the zipped file list entry
    	ZipEntry ze = zis.getNextEntry();
 
    	File newFile = null;
    	while(ze!=null){
 
    	   String fileName = ze.getName();
           newFile = new File(outputFolder + File.separator + fileName);
 
            //create all non exists folders
            //else you will hit FileNotFoundException for compressed folder
            new File(newFile.getParent()).mkdirs();
 
            FileOutputStream fos = new FileOutputStream(newFile);             
 
            int len;
            while ((len = zis.read(buffer)) > 0) {
            	fos.write(buffer, 0, len);
            }
 
            fos.close();   
            ze = zis.getNextEntry();
    	}
 
        zis.closeEntry();
    	zis.close();
    	
    	return newFile;
    }catch(IOException ex){
    	APP_OBJECT.getLog().error(ex); 
    }
     return null;
   }    
    

    
}
