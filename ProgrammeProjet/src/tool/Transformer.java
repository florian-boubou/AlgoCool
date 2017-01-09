package tool;


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
}
