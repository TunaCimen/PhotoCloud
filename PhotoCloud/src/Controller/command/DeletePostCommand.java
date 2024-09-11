package Controller.command;

import Model.db.Logger;
import Model.db.PostDatabase;
import Model.nodes.Post;
import Controller.UIController;

import javax.swing.*;

public class DeletePostCommand implements Command{

    Post post;

    /**
     * @param post post to delete.
     */
    public DeletePostCommand(Post post){
        this.post = post;
    }


    @Override
    public void execute() {
        if(PostDatabase.getInstance().get(post) == null){
            System.out.println("doesnt exist post");
            return;
        }
        int affirmDialog = JOptionPane.showConfirmDialog(UIController.getUIController(),"Are you sure?");
        if(affirmDialog == JOptionPane.NO_OPTION)return;
        PostDatabase.getInstance().remove(post);
        Logger.getInstance().log("Post with ID " + post.getID() +  " was removed");
    }
}
