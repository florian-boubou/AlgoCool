package algopars.tool;


import java.util.ArrayList;

/**
 * Classe Transformer, classe utilitaire qui transforme une ligne de pseudo-code en syntaxe Java
 *
 * @author Mathieu CHOUGUI
 * @version 1.0.0a
 * @date 01/09/2017
 */
public abstract class Transformer
{
	public String transformAffectation( String pseudoCode )
	{
		return pseudoCode.replace("◄—", "=");
	}
	
	public String transformExpression( String pseudoCode )
	{
		String javaCode = new String(pseudoCode);
		
		if( !javaCode.matches(Regex.REGEX_OPERATION) )
			return pseudoCode;
		else
		{
			if( javaCode.contains( "×" ) ) javaCode = javaCode.replace( "×", "*" );
			if( javaCode.contains("MOD") ) javaCode = javaCode.replace("MOD", "%");
			if( javaCode.contains("DIV") ) javaCode = javaCode.replace("DIV", "/");
			
			if( Regex.isConcatenation( javaCode ) ) javaCode = javaCode.replace('&', '+');
			
			return javaCode;
		}
	}

	/**
	 * Méthode permettant de transformer une expression booléenne pseudo-code en une expression
	 * booléenne Java
	 *
	 * @param pseudoCode
	 *      L'expression booléenne en pseudo-code
	 * @return
	 *      L'expression booléenne en Java
	 */
	public static String transformCondition( String pseudoCode )
	{
		String javaCode = new String( pseudoCode );
		//Transformations classiques pseudo-code -> Java
		javaCode = javaCode.replaceAll( "/="    , "!=" );
		javaCode = javaCode.replaceAll( "ET|et" , "&&" );
		javaCode = javaCode.replaceAll( "OU|ou" , "||" );

		//On stocke les opérateurs logiques pour les réassigner plus tard
		ArrayList<String> logicOps = new ArrayList<>();
		{
			String tmp = new String( javaCode );
			int    opET, opOU;
			
			opET = opOU = 0;

			for( ; (opET = tmp.indexOf( "&&" )) != -1 || (opOU = tmp.indexOf( "||" )) != -1; )
			{
				if( opET == -1 )
					logicOps.add( "||" );
				else if( opOU == -1 )
					logicOps.add( "&&" );
				else if( opET < opOU )
					logicOps.add( "&&" );
				else
					logicOps.add( "||" );

				tmp = tmp.replace( "&&", "" ).replace( "||", "" );
			}
		}

		String[] tabS = javaCode.split( "\\&\\&|\\|\\|" );
		javaCode = "";

		//Transformation du = (pseudo-code) en == (pseudo-code)
		for( int i = 0; i < tabS.length; i++ )
		{
			if( tabS[i].trim().matches( "^\\w*\\s*=\\s*\\w*$" ) )
				tabS[i] = tabS[i].replaceAll( "=", "==" );
			javaCode += tabS[i] + (i < tabS.length - 1 ? logicOps.get( i ) : "");
		}

		return javaCode;
	}
}
