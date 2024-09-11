package Controller.command;

import Model.db.UserDatabase;
import Model.nodes.User;

import java.util.Arrays;

public class ChangePasswordCommand implements Command{

    User user;
    char[] newPassword;

    /**
     *
     * @param user user to change the password of
     * @param newPassword password to change to.
     */
    public ChangePasswordCommand(User user, char[] newPassword){
        this.user = user;
        this.newPassword = newPassword;
    }
    @Override
    public void execute() {
        if(!Arrays.toString(newPassword).equals("")){
            UserDatabase.getInstance().searchByNickname(user.getNickname()).setPassword(newPassword);
        }

    }
}
