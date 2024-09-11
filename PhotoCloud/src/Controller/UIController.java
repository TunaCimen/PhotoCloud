package Controller;

import Model.nodes.Post;
import Model.nodes.User;
import View.CommentFrame;
import View.MenuBar;
import View.Pages;
import View.ProfilePage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Stack;

public class UIController extends JFrame{


    CardLayout cardLayout;
    JPanel cardPanel;

    public Stack<Pages> cardHistory;

    private Pages currentPage;

    private User currentUser = null;

    private static UIController instance = null;

    private UIController(){

        super("PhotoCloud");
        cardHistory = new Stack<>();
        setVisible(true);
        setSize(new Dimension(500,500));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.cardLayout = new CardLayout();
        this.cardPanel = new JPanel(cardLayout);
        this.add(cardPanel);
        setMenu(new MenuBar());
        instantiatePages();
        setCurrentPage(Pages.Login);

    }

    public User getCurrentUser(){
        return currentUser;
    }

    public void setCurrentUser(User u){

        currentUser = u;
    }

    /***
     * Create and return the instance, can only created once!
     * All pages are instantiated.
     * @return UIController instance.
     */
    public static UIController getUIController(){
        if(instance == null)instance = new UIController();
        return instance;
    }


    private void instantiatePages(){
        for(Pages p:Pages.values()){
            cardPanel.add(p.getValue(),p.getPage().getPanel());
        }
    }

    /**
     * Sets the menu bar of the mainFrame.
     * @param menuBar
     */
    public void setMenu(JMenuBar menuBar){
        super.setJMenuBar(menuBar);
    }

    /**
     * Creates and shows the comment frame.
     * Until comment frame is closed the main frame is non-interactable.
     * @param post which post's comment to show.
     */
    public void showCommentFrame(Post post){
        CommentFrame commentFrame = new CommentFrame(post);
        this.setEnabled(false);
        this.setResizable(false);
        commentFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDeactivated(WindowEvent e) {
                System.out.println("Window Closed Comment");
                UIController.getUIController().setResizable(true);
                UIController.getUIController().setEnabled(true);
                Pages.Discover.getPage().onStart();
            }
        });

    }

    /**
     * Whose profile to initiate, shows the profile for the user u.
     * @param u profile to show.
     */
    public void showProfileFor(User u){
        ((ProfilePage)Pages.Profile.getPage()).setUser(u);
        setCurrentPage(Pages.Profile);
    }

    /**
     * Changes current page to the provided Pages
     * Changed page is added to a stack for undo operations.
     * @param p page to change to
     */
    public void setCurrentPage(Pages p){
        if(!cardHistory.isEmpty()){
                cardHistory.peek().getPage().onExit();
                p.getPage().onStart();
        }
        EventQueue.invokeLater(()->{
                cardHistory.add(p);
                currentPage = p;
                this.getJMenuBar().setVisible(p.getPage().isMenuVisible());
                cardLayout.show(cardPanel,p.getValue());
        });
    }

    public Pages getCurrentPage(){return currentPage;}






}
