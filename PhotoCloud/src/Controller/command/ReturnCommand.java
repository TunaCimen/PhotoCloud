package Controller.command;

import javax.swing.*;

public interface ReturnCommand<T> extends Command{
    /***
     * Extended Command class with new behaviour.
     * @return T a type parameter that the command returns when executed.
     */

    T executeWithReturn();

}
