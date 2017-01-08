import engine.type.*;
import tool.Regex;

import java.util.HashMap;
/**
 * Created by Terawa on 08/01/2017.
 */
public class DataFactory {
	private enum Type {
		INTEGER("entier", Regex.REGEX_INTEGER),
		DOUBLE("double",Regex.REGEX_DOUBLE),
		CHAR("caractere", Regex.REGEX_CHAR),
		STRING("string", Regex.REGEX_STRING),
		BOOLEAN("booleen", Regex.REGEX_BOOLEAN);

		private String pCType;
		private String regexType;

		Type(String pcType, String regexType) {
			this.pCType = pcType;
			this.regexType = regexType;
		}

		public String getpCType() {
			return pCType;
		}

		public String getRegexType() {
			return regexType;
		}
	}
	private HashMap<String ,Variable> hMapData;

	public DataFactory() {
		hMapData = new HashMap<>();
	}

	public void dataDeclaration(String varName, String type, String value) throws Exception{
		if(type == null) {
			type = determineType(value);
		}
		switch(type) {
			case "entier" :
				hMapData.put(varName,new IntegerVar(varName, value));
				break;
			case "double" :
				hMapData.put(varName,new DoubleVar(varName, value));
				break;
			case "caractere" :
				hMapData.put(varName,new CharVar(varName, value));
				break;
			case "chaine" :
				hMapData.put(varName,new StringVar(varName, value));
				break;
			case "booleen" :
				hMapData.put(varName,new BooleanVar(varName, value));
				break;
			default :
				throw new Exception("Erreur: type invalide");
		}
	}

	private String determineType(String varName) {
		String ret = null;
		for(Type t : Type.values()) {
			if(varName.matches(t.getRegexType())) {
				ret = t.getpCType();
			}
		}
		return ret;
	}

	public HashMap<String, Variable> getHMapData() {
		return hMapData;
	}

}
