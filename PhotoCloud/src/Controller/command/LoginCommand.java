package Controller.command;

import Model.db.Logger;
import Model.db.UserDatabase;
import Model.nodes.User;
import View.Pages;
import Controller.UIController;

import java.util.Arrays;

public class LoginCommand implements Command{

    String nickname;
    char[] password;

    /**
     * check if user can login, paint the view if yes.
     * @param nickname user nick trying to login
     * @param password
     */
    public LoginCommand(String nickname,char[] password){
        this.nickname = nickname;
        this.password = password;
    }

    public User canLogin(){
        User u = UserDatabase.getInstance().searchByNickname(nickname);
        if(u == null)return null;
        if(!Arrays.equals(u.getPassword(), password))return null;
        return u;

    }
    @Override
    public void execute() {
        User u = canLogin();
        if(u == null){
            Command c = new WarningCommand("Username or password is wrong !! ");
            c.execute();
            return;
        };
        UIController.getUIController().setCurrentUser(u);
        UIController.getUIController().setCurrentPage(Pages.Discover);
        Logger.getInstance().log("User: " + nickname + " has login");

    }
}
