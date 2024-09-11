package Controller.command;

import Controller.UserValidator;
import Image.ImageSecretary;
import Model.db.Logger;
import Model.db.UserDatabase;
import Model.db.Writer;
import Model.nodes.User;
import View.Pages;
import Controller.UIController;


import java.awt.image.RenderedImage;
import java.util.ArrayList;

public class SignUpCommand implements Command{


    private String nickname;
    private String name;
    private String surname;
    private char[] password;
    private RenderedImage pp = null;
    UserValidator userValidator;

    User.tiers tier;

    /**
     * Sign a new user with given fields
     * @param nickname
     * @param name
     * @param surname
     * @param password
     * @param tier
     */
    public SignUpCommand(String nickname, String name, String surname, char[] password, User.tiers tier) {
        this.nickname = nickname;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.tier = tier;
        userValidator = new UserValidator();
    }


    /**
     * Sign a new user with given fields
     * @param nickname
     * @param name
     * @param surname
     * @param password
     * @param tier
     */
    public SignUpCommand(String nickname, String name, String surname, char[] password, User.tiers tier, RenderedImage pp) {
        this.nickname = nickname;
        this.tier = tier;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.pp = pp;
        userValidator = new UserValidator();
    }


    @Override
    public void execute() {
        if(!userValidator.validateNewUser(nickname))return;
        User u;
        if(pp!= null){
            u = new User(nickname,name,surname,password,tier,pp);
            ImageSecretary.writeImageToResources(pp,u.picSaveName(),".jpg");
        }
        else{
            u = new User(nickname,name,surname,password,tier);
        }

        UserDatabase.getInstance().put(u,new ArrayList<>());
        Command c  = new WarningCommand("User created successfully.");
        Logger.getInstance().log("User with nickname: " + u.getNickname() + "created.");
        UIController.getUIController().setCurrentPage(Pages.Login);
        Writer w = new Writer();
        w.writeUser(u);

    }
}
