package Controller.command;

import Image.ImageSecretary;
import Model.db.Logger;
import Model.db.PostDatabase;
import Model.db.UserDatabase;
import Model.db.Writer;
import Model.nodes.Post;
import View.DiscoverPage;
import View.Pages;

import java.util.ArrayList;
import java.util.List;

public class AddPostCommand implements Command{


    PostDatabase postDatabase;
    UserDatabase userDatabase;
    Post post;

    /**
     *
     * @param post to add
     */
    public AddPostCommand(Post post){
        postDatabase = PostDatabase.getInstance();
        this.post = post;
    }
    @Override
    public void execute() {
        postDatabase.put(post,new ArrayList<>());
        Writer writer = new Writer();
        writer.writePost(post);
        ImageSecretary.writeImageToResources(post.getImage(),post.imageFileName,".jpg");
        Logger.getInstance().log("Post added by user w/nickname " + post.getUser().getNickname());
        //((DiscoverPage)Pages.Discover.getPage()).repaintView();//TODO:make it better maybe event.
        //((DiscoverPage)Pages.Discover.getPage()).onStart();

    }
}
