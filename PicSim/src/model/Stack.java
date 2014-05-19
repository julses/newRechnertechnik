package model;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 17.05.14
 * Time: 18:56
 * To change this template use File | Settings | File Templates.
 */
public class Stack {

    int pointer;
    int[] stack;

    public Stack() {
        pointer = 0;
        stack = new int[8];
    }

    public void push(int val) {
        if (pointer > 7) {
            //Stack voll es wird wieder bei 0 angefangen
            pointer = 0;
        }
        stack[pointer++] = val & 0x1FFF; //0b0001 1111 1111 11111
    }

    public int pop() {
        if (pointer <= 0)
            //Stack leer, es wird wieder ganz oben angefangen
            pointer = 8;
        return stack[--pointer];
    }

    public void clear() {
        stack = new int[8];
    }

    public int[] getStackArray() {
        return stack;
    }
}
