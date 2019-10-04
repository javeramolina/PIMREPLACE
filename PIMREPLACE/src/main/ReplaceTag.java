package main;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

public class ReplaceTag {
	
	private static final Logger LOGGER = Logger.getLogger(ReplaceTag.class.getName());

	public static void main(String[] args) throws IOException {
		
		LOGGER.info("Start method ReplaceTag"); 
		
		
		try (InputStream input = ReplaceTag.class.getClassLoader().getResourceAsStream("src/properties/config.properties")) {

            Properties prop = new Properties();

            if (input == null) {
          	LOGGER.info("Sorry, unable to find config.properties");
                return;
            }

            //load a properties file from class path, inside static method
            prop.load(input);

            //get the property value and print it out
            
            Set<String> lista = listFilesUsingDirectoryStream(prop.getProperty("path.origen"));
            
            LOGGER.info("Path orgine: "+prop.getProperty("path.origen").toString());
        	

    		for (String archivoXML : lista) {
    			FormatFileName f = new FormatFileName();
    			
    			if(f.validate(archivoXML)) {
    				
    				LOGGER.info("Archivo a procesar: "+archivoXML);   				
    				   				
    				Path path = Paths.get(prop.getProperty("path.origen").concat(archivoXML).toString());    				   				
    				Path pathDestino = Paths.get(prop.getProperty("path.destino").concat(archivoXML).toString());
    				
    	    		Charset charset = StandardCharsets.UTF_8;
    	    		    	    		    	    		
    	    		String content = new String(Files.readAllBytes(path), charset);
    	    		content = content.replaceAll("UserTypeID=\"Products\"", "UserTypeID=\"CL-PROD\"");
    	    		content = content.replaceAll("UserTypeID=\"Level1\"", "UserTypeID=\"CL-LEV1\"");
    	    		content = content.replaceAll("UserTypeID=\"Level2\"", "UserTypeID=\"CL-LEV2\"");
    	    		content = content.replaceAll("UserTypeID=\"Level3\"", "UserTypeID=\"CL-LEV3\"");
    	    		content = content.replaceAll("UserTypeID=\"Level4\"", "UserTypeID=\"CL-LEV4\"");
    	    		content = content.replaceAll("UserTypeID=\"SodimacSKU\"", "UserTypeID=\"CL-SKU\"");
    	    		content = content.replaceAll("UserTypeID=\"SSKU\"", "UserTypeID=\"CL-SSKU\"");  	    		
    	    		
    	    		Files.write(pathDestino, content.getBytes(charset));
    	    		
    				}   			   		
    		}
    		
    		LOGGER.info("Path destino archivo: "+ prop.getProperty("path.destino"));    	

        } catch (IOException ex) {
            ex.printStackTrace();
        }
		
	}
	
	public static Set<String> listFilesUsingDirectoryStream(String dir) throws IOException {
	    Set<String> fileList = new HashSet<>();
	    try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir))) {
	        for (Path path : stream) {
	            if (!Files.isDirectory(path)) {
	                fileList.add(path.getFileName()
	                    .toString());
	            }
	        }
	    }
	    return fileList;
	}

}
