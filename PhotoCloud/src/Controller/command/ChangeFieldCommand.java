package Controller.command;

import Model.db.Logger;

import javax.swing.*;

public class ChangeFieldCommand implements ReturnCommand<String>{



    JPanel parent;
    Object[] message;
    JTextField textField;


    /**
     *
     * @param parent component to show to OptionPane
     * @param message message to display
     */
    public ChangeFieldCommand(JPanel parent, String message){
        textField = new JTextField();
        this.message = new Object[]{
                message, textField
        };

        this.parent = parent;
    }
    @Override
    public void execute() {

    }

    @Override
    public String executeWithReturn() {
        int option = JOptionPane.showOptionDialog(
                parent
                ,message
                ,""
                ,JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,null,null);
        if(option == JOptionPane.OK_OPTION){
            if(textField.getText().equals("")){
                Command c = new WarningCommand("Field cannot be blank");
                c.execute();
                return "";
            }
            Logger.getInstance().log("field changed");
            return textField.getText();

        }
        return "";
    }
}
