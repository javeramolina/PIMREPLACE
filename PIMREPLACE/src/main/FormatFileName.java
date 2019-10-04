package main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatFileName {
	
   private Pattern pattern;
   private Matcher matcher;
 
   private static final String FILENAME_PATTERN = "([^\\s]+(\\.(?i)(xml))$)";
	        
   public FormatFileName(){
	  pattern = Pattern.compile(FILENAME_PATTERN);
   }
	  
   /**
   * Validate file name with regular expression
   * @param file name for validation
   * @return true valid name, false invalid name
   */
   public boolean validate(final String file){
		  
	  matcher = pattern.matcher(file);
	  return matcher.matches();
	    	    
   }
}