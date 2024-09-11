package Model.db;

import Model.nodes.Comment;
import Model.nodes.Post;
import Model.nodes.User;

import java.util.*;
import java.util.stream.Collectors;


public class PostDatabase extends Database<Post, List<Comment>> {

    /**
     * Database to hold Post, List of Comments pairs.
     */
    private static PostDatabase instance;

    private PostDatabase(){

    }

    public static PostDatabase getInstance(){
        if(instance == null)instance = new PostDatabase();
        return instance;
    }

    public List<Post> getByUser(User u){
        List<Post> posts
                = map.keySet().stream()
                .filter(comments -> Objects.equals(comments.getUser().getNickname(), u.getNickname())).toList();
        return posts;
    }

    public List<Map.Entry<Post, List<Comment>>> getNonPrivate(){
        return map.entrySet().stream().filter(e->!e.getKey().isPrivate).collect(Collectors.toList());
    }
    @Override
    public List<Comment> get(Post post) {
        return (List<Comment>) super.get(post);
    }
}
