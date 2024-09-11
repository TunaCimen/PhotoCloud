package Controller.command;

import Model.db.Logger;
import Model.nodes.Post;
import Model.nodes.User;

public class LikeCommand implements Command{
    User user;
    Post post;

    /**
     * Like a post.
     * @param user user that liked
     * @param post post that has been liked.
     */
    public LikeCommand(User user, Post post){

        this.user = user;
        this.post = post;

    }

    @Override
    public void execute() {

        post.likePost(user);
        Logger.getInstance().log("Post with ID " + post.getID() +  " was liked by " + post.getUser().getNickname());
    }
}
