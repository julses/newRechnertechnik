package controller;

import java.math.BigInteger;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 04.05.14
 * Time: 12:37
 * To change this template use File | Settings | File Templates.
 */
public class Converter {

    public String hexStringToBinString(String hex) {
        String var = new BigInteger(hex, 16).toString(2);   //Umrechnung in Binärzahl
        if(var.length()<16){
            StringBuffer sb = new StringBuffer (var);           //StringBuffer um append nutzen zu können
            for(int n=var.length(); n<16; n++) {                //Nullen vorne anhängen bis 16bit
                sb.insert(0, "0");
            }
            var = sb.toString();
        }
        return var;
    }

    //Umrechnung in Integerzahl
    public int hexStringToInt(String hex){
        return new BigInteger(hex, 16).intValue();   //Umrechnung in Binärzahl
    }
}
