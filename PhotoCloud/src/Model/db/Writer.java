package Model.db;

import Model.nodes.Post;
import Model.nodes.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    String userFilePath = "./Users.txt";
    String postFilePath  = "./Posts.txt";
    BufferedWriter userWriter,postWriter;
    public Writer(){

        try {
            userWriter = new BufferedWriter(new FileWriter(userFilePath,true));
            postWriter = new BufferedWriter(new FileWriter(postFilePath,true));
        } catch (IOException e) {
            System.err.println("Databases not found.!!");
        }

    }

    /**
     * Write u to the User file.
     * @param u
     */
    public void writeUser(User u){
        if(userWriter == null){
            System.err.println("UserTxt file is corrupted!!");
            return;
        }
        try {
            userWriter.write(u.toFile());
            userWriter.flush();
            System.out.println("User written succesfully");
        } catch (IOException e) {
            System.err.println("UserTxt file is corrupted!!");
        }

    }

    /**
     * Write post to the Posts file.
     * @param p
     */
    public void writePost(Post p){
            if(postWriter == null){
                System.err.println("PostTxt file is corrupted!!");
            }
            try{
                postWriter.write(p.toFile());
                postWriter.flush();
            } catch(IOException e){
                System.err.println("PostTxt file is corrupted!!");
            }
    }

}
