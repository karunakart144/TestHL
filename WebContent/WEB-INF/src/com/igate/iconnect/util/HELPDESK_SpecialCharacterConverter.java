/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.util;

public class HELPDESK_SpecialCharacterConverter {
	
	public  String convertSpecialChars(String descriptionVal)
	{		
		if(descriptionVal!=null){
		if(descriptionVal.length()!=0)
		{		
		
			if(descriptionVal.contains("&#60;")){
				descriptionVal=descriptionVal.replace("&#60;", "<");
			}
			if(descriptionVal.contains("&#62;")){
				descriptionVal=descriptionVal.replace("&#62;", ">");
			}
			if(descriptionVal.contains("&#61;")){
				descriptionVal=descriptionVal.replace("&#61;", "=");
			}			
			if(descriptionVal.contains("&#34;")){
				descriptionVal=descriptionVal.replace("&#34;", "\"");
			}						
			if(descriptionVal.contains("&#92;")){
				descriptionVal=descriptionVal.replace("&#92;", "\\");
			}
			if(descriptionVal.contains("&#47;")){
				descriptionVal=descriptionVal.replace("&#47;", "\\/");
			}
			if(descriptionVal.contains("&#126;")){
				descriptionVal=descriptionVal.replace("&#126;", "~");
			}
			if(descriptionVal.contains("&#039;") || descriptionVal.contains("\'")){
				descriptionVal=descriptionVal.replace("&#039;", "\'");// Changed it as &#039; as part of L2 : 4200
			}
			// Added Special Characters by Nazeeb as part of L2 : 4200
			if(descriptionVal.contains("\b")){
				descriptionVal=descriptionVal.replace("\b", "\\b");
			}
			if(descriptionVal.contains("\t")){
				descriptionVal=descriptionVal.replace("\t", "\\t");
			}
			if(descriptionVal.contains("\f")){
				descriptionVal=descriptionVal.replace("\f", "\\f");
			}			
			/*if(descriptionVal.contains("+")){
				descriptionVal=descriptionVal.replace("+", "");
			}*/
			if(descriptionVal.contains("&#037;")){
				descriptionVal=descriptionVal.replace("&#037;", "%");
			}
			if(descriptionVal.contains("&#064;")){
				descriptionVal=descriptionVal.replace("&#064;", "@");
			}
			if(descriptionVal.contains("&#033;")){
				descriptionVal=descriptionVal.replace("&#033;", "!");
			}
			if(descriptionVal.contains("&#040;")){
				descriptionVal=descriptionVal.replace("&#040;", "(");				
			}
			if(descriptionVal.contains("&#041;")){
				descriptionVal=descriptionVal.replace("&#041;", ")");				
			}
			if(descriptionVal.contains("&#091;")){
				descriptionVal=descriptionVal.replace("&#091;", "[");				
			}
			if(descriptionVal.contains("&#093;")){
				descriptionVal=descriptionVal.replace("&#093;", "]");				
			}
			if(descriptionVal.contains("&#094;")){
				descriptionVal=descriptionVal.replace("&#094;", "^");				
			}
			if(descriptionVal.contains("&#095;")){
				descriptionVal=descriptionVal.replace("&#095;", "_");				
			}
			if(descriptionVal.contains("&#096;")){
				descriptionVal=descriptionVal.replace("&#096;", "`");				
			}
			if(descriptionVal.contains("&#123;")){
				descriptionVal=descriptionVal.replace("&#123;", "{");				
			}
			if(descriptionVal.contains("&#124;")){
				descriptionVal=descriptionVal.replace("&#124;", "|");				
			}
			if(descriptionVal.contains("&#125;")){
				descriptionVal=descriptionVal.replace("&#125;", "}");				
			}
			if(descriptionVal.contains("&#036;")){
				descriptionVal=descriptionVal.replace("&#036;", "$");				
			}	
			// End of Special Characters by Nazeeb as part of L2 : 4200
			if(descriptionVal.contains("&#38;")){
				descriptionVal=descriptionVal.replace("&#38;", "&");
			}
				if(descriptionVal.contains("&#042;")){
				descriptionVal=descriptionVal.replace("&#042;", "*");
			}
			if(descriptionVal.contains("&amp;")){
				descriptionVal=descriptionVal.replace("&amp;", "&");
			}
			
			
		}
		}
		
		return descriptionVal;
	}
	
	public  String replaceSpecialChars(String descriptionVal){
		if(descriptionVal!=null){
		if(descriptionVal.length()!=0)
		{
			if(descriptionVal.contains("&")){
				descriptionVal=descriptionVal.replace("&", "&#38;");
			}
			if(descriptionVal.contains("<")){
				descriptionVal=descriptionVal.replace("<", "&#60;");
			}
			if(descriptionVal.contains(">")){
				descriptionVal=descriptionVal.replace(">", "&#62;");
			}
			if(descriptionVal.contains("=")){
				descriptionVal=descriptionVal.replace("=", "&#61;");
			}		
			if(descriptionVal.contains("!qt")){
				descriptionVal=descriptionVal.replace("!qt", "&#34;");
			}
			if(descriptionVal.contains("\"")){
				descriptionVal=descriptionVal.replace("\"", "&#34;");
			}
			if(descriptionVal.contains("\'")){
				descriptionVal=descriptionVal.replace("\'", "&#039;");// Changed it as &#039; as part of L2 : 4200
			}
			if(descriptionVal.contains("~")){
				descriptionVal=descriptionVal.replace("~", "&#126;");
			}		
			if(descriptionVal.contains("\\")){
				descriptionVal=descriptionVal.replace("\\", "&#92;");
			}
			// Added Special Characters by Nazeeb as part of L2 : 4200
			if(descriptionVal.contains("\b")){
				descriptionVal=descriptionVal.replace("\b", "\\b");
			}
			if(descriptionVal.contains("\t")){
				descriptionVal=descriptionVal.replace("\t", "\\t");
			}
			if(descriptionVal.contains("\f")){
				descriptionVal=descriptionVal.replace("\f", "\\f");
			}
			/*if(descriptionVal.contains("+")){
				descriptionVal=descriptionVal.replace("+", "");
			}*/
			if(descriptionVal.contains("%")){
				descriptionVal=descriptionVal.replace("%", "&#037;");
			}
			if(descriptionVal.contains("@")){
				descriptionVal=descriptionVal.replace("@", "&#064;");
			}
			if(descriptionVal.contains("!")){
				descriptionVal=descriptionVal.replace("!", "&#033;");
			}
			if(descriptionVal.contains("(")){
				descriptionVal=descriptionVal.replace("(", "&#040;");				
			}
			if(descriptionVal.contains(")")){
				descriptionVal=descriptionVal.replace(")", "&#041;");				
			}			
			if(descriptionVal.contains("[")){
				descriptionVal=descriptionVal.replace("[", "&#091;");				
			}
			if(descriptionVal.contains("]")){
				descriptionVal=descriptionVal.replace("]", "&#093;");				
			}
			if(descriptionVal.contains("^")){
				descriptionVal=descriptionVal.replace("^", "&#094;");				
			}
			if(descriptionVal.contains("_")){
				descriptionVal=descriptionVal.replace("_", "&#095;");				
			}
			if(descriptionVal.contains("`")){
				descriptionVal=descriptionVal.replace("`", "&#096;");				
			}
			if(descriptionVal.contains("{")){
				descriptionVal=descriptionVal.replace("{", "&#123;");				
			}
			if(descriptionVal.contains("|")){
				descriptionVal=descriptionVal.replace("|", "&#124;");				
			}
			if(descriptionVal.contains("}")){
				descriptionVal=descriptionVal.replace("}", "&#125;");				
			}
			if(descriptionVal.contains("$")){
				descriptionVal=descriptionVal.replace("$", "&#036;");				
			}
			// End of Special Characters by Nazeeb as part of L2 : 4200
			if(descriptionVal.contains("\\/")){
				descriptionVal=descriptionVal.replace("\\/", "&#47;");
			}
			if(descriptionVal.contains("*")){
				descriptionVal=descriptionVal.replace("*", "&#042;");
			}
			
		}
		}
		return descriptionVal;
	}

}
