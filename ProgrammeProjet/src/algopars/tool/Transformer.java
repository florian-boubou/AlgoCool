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
	public String syntaxTransformer(String statement)
	{
		String transformed = new String(statement);

		if( transformed.contains("=") ) transformed.replace( "=", "==" );
		if( transformed.contains("◄—")) transformed.replace( "◄—", "=" );
		if( transformed.contains("ET")) transformed.replace( "ET", "&" );
		if( transformed.contains("OU")) transformed.replace( "OU", "|" );

		if( transformed.contains(Regex.REGEX_OPERATION))
		{
			if ( transformed.contains( "×" ) ) transformed.replace( "×", "*" );
			if ( transformed.contains("MOD") ) transformed.replace("MOD", "%");
			if ( transformed.contains("DIV") ) transformed.replace("DIV", "/");
		}

		return statement;
	}

	/**
	 * Méthode permettant de transformer une expression booléenne pseudo-code en une expression
	 * booléenne Java
	 *
	 * @param pseudoCode L'expression booléenne en pseudo-code
	 * @return L'expression booléenne en Java
	 */
	public static String transformCondition( String pseudoCode )
	{
		String javaCode = new String( pseudoCode );
		//Transformations classiques pseudo-code -> Java
		javaCode = javaCode.replaceAll( "/=", "!=" );
		javaCode = javaCode.replaceAll( "ET|et", "&&" );
		javaCode = javaCode.replaceAll( "OU|ou", "||" );

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
