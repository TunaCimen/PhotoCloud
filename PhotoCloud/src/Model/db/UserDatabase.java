package Model.db;


import Model.db.Database;
import Model.nodes.Post;
import Model.nodes.User;

import java.util.List;
import java.util.Optional;

public class UserDatabase extends Database<User, List<Post>>{

    /**
     * Database to hold User,List of Posts
     * In order to avoid inquiring each post by nickname if app gets too big(Extensibility.)
     */
    private static UserDatabase instance;
    private UserDatabase(){

    }

    public static UserDatabase getInstance(){
        if(instance == null)instance = new UserDatabase();
        return instance;
    }

    /**
     * Search the user with the nickname.
     * @param nick
     * @return user if found null otherwise.
     */
    public User searchByNickname(String nick){
        Optional<User> userBox =  map.keySet()
                .stream()
                .filter(e->e.getNickname().equals(nick))
                .findFirst();
        if(userBox.isEmpty())return null;
        return userBox.get();
    }






}
