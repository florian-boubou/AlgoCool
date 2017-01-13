package algopars.util.var;

import algopars.tool.Transformer;

import java.util.ArrayList;
import java.util.HashMap;

import static com.sun.corba.se.spi.activation.IIOP_CLEAR_TEXT.value;


/**
 * Created by clemence on 12/01/17.
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

    public ArrayVar(String name, String value)
    {
        super(name, null);
        value = value.trim().replace("d\'", "de ");
        this.strSize = value.substring(value.indexOf("[")+1,value.indexOf("]"));
        super.type = getJavaType(value.substring(value.indexOf("de ") + 3));
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    @Override
    public String getJavaType() {
        return type;
    }

    @Override
    public void setValue(String newValue) {
    }

    public void setCellValue(int index, String value)
    {
        values[index] = value;
    }

    private static String getJavaType(String type) {
        return hmType.get(type);
    }

    public void setSize(int size) {
        this.size = size;
        this.values = new String[size];
    }

    public String getStrSize()
    {
        return strSize;
    }

    public int getSize()
    {
        return this.size;
    }

    public String getValue(int index)
    {
        return values[index];
    }

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