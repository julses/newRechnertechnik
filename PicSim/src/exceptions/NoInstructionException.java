package exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 15.04.14
 * Time: 23:48
 * To change this template use File | Settings | File Templates.
 */
public class NoInstructionException extends Exception{

    public NoInstructionException(int opcode) {
        super("Keine gültige Instruction: 0x" + Integer.toHexString(opcode));
    }
}
