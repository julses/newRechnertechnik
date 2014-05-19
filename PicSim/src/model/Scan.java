package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Converter;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 07.04.14
 * Time: 14:20
 * To change this template use File | Settings | File Templates.
 */
public class Scan {

    private Converter convert = new Converter();
    //private List<String> binaryCode;
    private List<String> hexCode;
    private Iterator<String> iterator;
    private Register register;
    private final String DELIM = "([0-9]|[A-F])+([0-9]|[A-F])*";
    private final Pattern PATTERN = Pattern.compile(DELIM);
    private Matcher matcher;
    private Path pathToLSTFile;

    public Scan(Register register) {
        //this.binaryCode = binaryCode;
        this.register = register;
    }

    public void setPathToLSTFile(Path pathToLSTFile) {
        this.pathToLSTFile = pathToLSTFile;
    }


    public void reader() throws IOException {
        hexCode = new ArrayList<String>();
        register.valueOnReset();
        FileReader fr = new FileReader(String.valueOf(pathToLSTFile));
        BufferedReader br = new BufferedReader(fr);

        String zeile = "";
        while ((zeile = br.readLine()) != null) {

            zeile = zeile.substring(5, 9);              //Befehlscode aus LST lesen

            if (zeile.equals("    ") || !isHexValue(zeile)) {                 //Abfrage ob Leerzeile in LST
                continue;
            } else {
                /* String val = "0x";
                 * val += zeile;
                 */
                hexCode.add(zeile);
                /*
                binaryCode.add(convert.hexStringToBinString(zeile));           //binaryCode binär
                for (int j = 0; j < binaryCode.size(); j++) {
                    if (zeile.length() < 4) binaryCode.set(j, "Fehler");
                }*/
            }
        }
        dotxt(hexCode, "HexCodeBefehle");
        //dotxt(binaryCode, "BinärCodeBefehle");
        iterator = hexCode.iterator();
        br.close();
    }


    private boolean isHexValue(String val) {
        matcher = PATTERN.matcher(val);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }


    private void dotxt(List<String> CodeListe, String dateiName) throws IOException{
        //Erstellen einer .txt
        FileWriter fw = null;
        try {
            fw = new FileWriter(dateiName+".txt");
            for (int i = 0; i < CodeListe.size(); i++) {
                fw.write(CodeListe.get(i));
                fw.append(System.getProperty("line.separator")); // e.g. "\n"
            }
        } catch (IOException e) {
            System.err.println("Konnte Datei nicht erstellen");
        } finally {
            if (fw != null)
                try {
                    fw.close();
                } catch (IOException e) {
            }
        }

    }

    /*Oldie but goldie ;)
    public String getOperation(){
        //Test welche Binärzahlen in Liste stehen
        if(iterator.hasNext()){
            return iterator.next();
        }else{
            System.out.println("Kein Befehl mehr vorhanden!");
            return null;
        }
    }*/

    public String getOper() {
        return hexCode.get(register.getPC());
    }

}