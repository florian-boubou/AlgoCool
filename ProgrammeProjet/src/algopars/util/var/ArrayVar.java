package algopars.util.var;

import algopars.tool.Transformer;

import java.util.ArrayList;
import java.util.HashMap;

import static com.sun.corba.se.spi.activation.IIOP_CLEAR_TEXT.value;

/**
 * Classe ArrayVar qui gère les structures de types primitifs que sont les tableaux
 *
 * @author Florian BOULANT, Clémence EDOUARD
 * @version 1.0.0a
 * @date 01/14/2017
 */
public class ArrayVar extends Variable
{
    private static final HashMap<String, String> hmType = new HashMap<String, String>() {{
        put("entier", "Integer");
        put("reel", "Double");
        put("caractere", "Char");
        put("chaine", "String");
        put("booleen", "Boolean");
    }};

    private int size;
    private String strSize;

    private String[] values;


    /**
     * Constructeur de ArrayVar
     * @param name Le nom du ArrayVar
     * @param value Le type du tableau
     */
    public ArrayVar(String name, String value)
    {
        super(name, null);
        value = value.trim().replace("d\'", "de ");
        this.strSize = value.substring(value.indexOf("[")+1,value.indexOf("]"));
        super.type = getJavaType(value.substring(value.indexOf("de ") + 3));
    }


    /**
     * Setter des valeurs du tableau
     * @param values Un ensemble de valeurs
     */
    public void setValues(String[] values) {
        this.values = values;
    }


    /**
     * Méthode fournissant l'équivalent en Java du type pseudo-code de la Variable courante
     * @return l'équivalent en Java du type pseudo-code de la Variable courante
     */
    @Override
    public String getJavaType() {
        return type;
    }


    /**
     * Setter du paramètre value hérité, qui ne fait.. rien
     * @param newValue nouvelle valeur a affecter
     */
    @Override
    public void setValue(String newValue) {
    }


    /**
     * Getter du paramètre strValue, qui renvoie les premières, jusqu'à 4, valeurs du tableau
     * @return les premières, jusqu'à 4, valeurs du tableau
     */
    @Override
    public String getStrValue()
    {
        StringBuilder s = new StringBuilder(  );
        for(int i = 0; i < size && i < 4; i++)
        {
            s.append( "[" + (i < size && values[i] != null?values[i]:"") +"]" );
        }
        return s.toString();
    }


    /**
     * Méthode permettant de modifier la valeur d'une des 'cases' du tableau
     * @param index l'indice de la case à modifier
     * @param value la nouvelle valeur
     */
    public void setCellValue( int index, String value )
    {
        values[index] = value;
    }


    /**
     * Getter du paramètre javaType
     * @param type le type dont on doit donner l'équivalent Java
     * @return l'équivalent Java du type passé en paramètre
     */
    private static String getJavaType(String type) {
        return hmType.get(type);
    }


    /**
     * Setter du paramètre size
     * @param size la nouvelle taille
     */
    public void setSize(int size)
    {
        this.size = size;
        this.values = new String[size];
    }


    /**
     * Getter du paramètre strSize
     * @return la valeur de strSize
     */
    public String getStrSize()
    {
        return strSize;
    }


    /**
     * Getter du paramètre size
     * @return la valeur du paramètre size
     */
    public int getSize()
    {
        return this.size;
    }


    /**
     * Méthode permettant de récupérer la valeur dans la case d'indice passé en paramètre
     * @param index l'indice de la case dont on souhaite la valeur
     * @return la valeur de la case d'indice index
     */
    public String getValue(int index)
    {
        return values[index];
    }


    /**
     * Méthode renvoyant une chaîne représentant l'état des 4 premières cases de l'ArrayVar
     * @return une chaîne représentant l'état des 4 premières cases de l'ArrayVar
     */
    public String toString()
    {

        String str = this.name + "[" + this.size + "]";
        int i = 0;
        while(i < this.size && i < 4 && this.getValue(i) != null) {
            str += this.name + "[" + i + "]=" + this.getValue(i) +" | ";
            i++;
        }
        return str;
    }

}