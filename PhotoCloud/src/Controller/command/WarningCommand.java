package Controller.command;

import Model.db.Logger;
import Controller.UIController;

import javax.swing.*;

public class WarningCommand implements Command{

    UIController uc = UIController.getUIController();
    String msg;

    /**
     * Show a warning pane with the given message.
     * @param msg
     */
    public WarningCommand(String msg){
        this.msg = msg;

    }

    @Override
    public void execute() {
        JOptionPane.showMessageDialog(uc.getContentPane(),msg,"Warning",JOptionPane.WARNING_MESSAGE);
        Logger.getInstance().log(msg);
    }
}
