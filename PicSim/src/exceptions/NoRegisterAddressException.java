package exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 17.05.14
 * Time: 19:55
 * To change this template use File | Settings | File Templates.
 */
public class NoRegisterAddressException extends Exception{

    public NoRegisterAddressException(int address) {
        super("Kein gültige Addresse: 0x" + Integer.toHexString(address) + "");
    }

}
