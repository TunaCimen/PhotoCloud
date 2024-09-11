package Model.db;

import Image.ImageMatrix;
import Image.ImageSecretary;
import Model.nodes.Post;
import Model.nodes.User;

import java.awt.image.RenderedImage;
import java.io.*;
import java.nio.Buffer;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Reader {
    /**
     * This class was created to read the users and
     * posts from the textFile previously created.
     */

    BufferedReader userReader,postReader;
    final String userPath = "./Users.txt";
    final String postPath = "./Posts.txt";

    public Reader(){
        try{
            userReader = new BufferedReader(new FileReader(userPath));
            postReader = new BufferedReader(new FileReader(postPath));
        } catch (FileNotFoundException e) {
            System.err.println("Database files are corrupted.!!");
        }

    }

    /**
     * Read users from the file.
     */
    public void readUsers() {
        String line = null;
        while(true){
            try {
                if ((line = userReader.readLine()) == null) break;
            } catch (IOException e) {
                System.err.println("User file is corrupted.!!");
            }
            String[] fields = line.split(",");
            if(fields[5].equals("false")){
                UserDatabase.getInstance().put(

                        new User(fields[0],
                                fields[1],
                                fields[2],
                                fields[3].toCharArray(),
                                User.tiers.getTier(fields[6])),
                        new ArrayList<>());
            }
            else{
                try {
                    RenderedImage r = ImageSecretary.readResourceImage(fields[4],".jpg").getBufferedImage();
                    UserDatabase.getInstance().put(
                            new User(fields[0],
                                    fields[1],
                                    fields[2],
                                    fields[3].toCharArray(),
                                    User.tiers.getTier(fields[6]),
                                    r),
                            new ArrayList<>());
                } catch (IOException e) {
                    System.err.println("Profile Pic Location missing or corrupted.");
                }
            }

        }
    }


    /**
     * Read Posts from the file.
     */
    public void readPosts(){
        String line = null;
        while(true){
            try{
                if((line = postReader.readLine()) == null) break;
            }
            catch(IOException e){
                System.err.println("Post file is corrupted.!!");
            }
            String[] fields = line.split(",");
            User u = UserDatabase.getInstance().searchByNickname(fields[0]);
            LocalDateTime l = LocalDateTime.parse(fields[1]);
            PostDatabase.getInstance().put(
                    new Post(u,fields[2],l,Integer.parseInt(fields[3])),new ArrayList<>());
            Post.id_assigner ++;
        }
    }
}
