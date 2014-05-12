package exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 05.05.14
 * Time: 19:12
 * To change this template use File | Settings | File Templates.
 */
public class IllegalCarryOperationException extends Exception {
    public IllegalCarryOperationException(int f, int w) {
            super("Keine gültige Carryoperation mit f: 0x" +Integer.toHexString(f) +" und W: " +Integer.toHexString(w));
    }
}
