package Controller;

import Controller.command.Command;
import Controller.command.WarningCommand;
import Model.db.UserDatabase;

public class UserValidator {


    public UserValidator(){

    }

    /**
     * Validate the new user using nickname.
     * Show warning if not validated
     * @param nickname nickname to check.
     * @return check if user with nickname exist or not. True if can else otherwise.
     */
    public boolean validateNewUser(String nickname){
        if(UserDatabase.getInstance().searchByNickname(nickname) != null)
        {
            Command c = new WarningCommand("Nickname already exists, pick another one");
            c.execute();
            return false;
        }
        return true;
    }
}
