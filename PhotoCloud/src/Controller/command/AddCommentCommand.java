package Controller.command;

import Model.db.Logger;
import Model.db.PostDatabase;
import Model.nodes.Comment;
import Model.nodes.Post;
import Model.nodes.User;

import java.time.LocalDateTime;

public class AddCommentCommand implements Command{

    /***
     * Command class to add a new post
     */

    private Post post;
    private User user;
    private String comment;
    public AddCommentCommand(Post post,User user,String comment){
        this.post = post;
        this.user = user;
        this.comment = comment;
    }

    @Override
    public void execute() {

        PostDatabase.getInstance().get(post).add(new Comment(user,comment));
        Logger.getInstance().log("Comment added to the post with ID: " + post.getID() + " by user w/nickname " + user.getNickname());
    }
}
