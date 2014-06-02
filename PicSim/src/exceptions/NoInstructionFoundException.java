package exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 30.05.14
 * Time: 01:59
 * To change this template use File | Settings | File Templates.
 */
public class NoInstructionFoundException extends Exception{

    public NoInstructionFoundException(int pc) {
        super("Kein gültige PCL-Addresse: 0x" + Integer.toHexString(pc) +"\nPCL wurde auf 0 zurückgesetzt!");
    }

}
