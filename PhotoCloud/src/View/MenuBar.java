package View;

import Controller.UIController;
import Controller.command.ChangeFieldCommand;
import Controller.command.ChangePasswordCommand;
import Controller.command.ReturnCommand;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar implements ActionListener {

    JMenu menu;
    JMenuItem profileItem;
    JMenuItem addPostItem;
    JMenuItem logOutItem;

    JMenuItem searchUserItem;
    public JMenuItem changePasswordItem;
    JMenuItem discoverItem;

    public MenuBar(){
        super();
        menu  = new JMenu("Menu");
        profileItem  = new JMenuItem("Profile");
        addPostItem = new JMenuItem("Add Post");
        discoverItem = new JMenuItem("Discover");
        logOutItem = new JMenuItem("Log Out");
        changePasswordItem = new JMenuItem("Change Password");
        searchUserItem = new JMenuItem("Search");


        profileItem.addActionListener(this);
        addPostItem.addActionListener(this);
        discoverItem.addActionListener(this);
        logOutItem.addActionListener(this);
        changePasswordItem.addActionListener(this);
        searchUserItem.addActionListener(this);

        this.add(menu);
        menu.add(profileItem);
        menu.add(addPostItem);
        menu.add(discoverItem);
        menu.add(logOutItem);
        menu.add(changePasswordItem);
        menu.add(searchUserItem);

    }

    public void setMenuVisibility(boolean b){
        this.setVisible(b);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JMenuItem itemClicked){
            switch(itemClicked.getText()){
                case "Profile" -> UIController.getUIController().setCurrentPage(Pages.Profile);
                case "Add Post" -> UIController.getUIController().setCurrentPage(Pages.Post);
                case "Discover" ->UIController.getUIController().setCurrentPage(Pages.Discover);
                case "Log Out" -> UIController.getUIController().setCurrentPage(Pages.Login);
                case "Search" -> UIController.getUIController().setCurrentPage(Pages.Search);
                case "Change Password" -> {
                    ReturnCommand<String> changeFieldCommand =
                            new ChangeFieldCommand(UIController
                                    .getUIController().cardHistory.peek().getPage().getPanel()
                                    ,"Enter New Password: ");
                    String newPass = changeFieldCommand.executeWithReturn();
                    ChangePasswordCommand c = new ChangePasswordCommand(UIController.getUIController().getCurrentUser(), newPass.toCharArray());
                    c.execute();
                }
                //case
            }
        }
    }
}
