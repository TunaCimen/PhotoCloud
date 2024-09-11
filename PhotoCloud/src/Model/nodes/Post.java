package Model.nodes;

import Image.ImageMatrix;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import Image.*;
import Model.db.Writable;

public class Post implements Comparable<Post>, Writable {

    public static int id_assigner = 0;
    private final int ID;
    private ImageMatrix imgMatrix;
    public LocalDateTime postDate;

    public String description;

    public List<User> likers;

    public String imageFileName;

    public boolean isPrivate;



    private User user;
    public Post(User user, ImageMatrix imgMatrix, String description, LocalDateTime postDate){
        isPrivate = false;
        this.user = user;
        this.likers = new LinkedList<>();
        this.imgMatrix = imgMatrix;
        this.postDate = postDate;
        this.description = description;
        ID = id_assigner;
        id_assigner ++;
        this.imageFileName = Integer.toString(ID);
    }

    public Post(User user,String description, LocalDateTime postDate,int ID){
        this.user = user;
        this.postDate = postDate;
        this.description = description;
        this.likers = new LinkedList<>();
        this.ID = ID;
        this.imageFileName = Integer.toString(ID);
        try {
            this.imgMatrix = ImageSecretary.readResourceImage(imageFileName,".jpg");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Post(User user, Image image, String description, LocalDateTime postDate){
        RenderedImage r = ImageSecretary.convertImageToRenderedImage(image);
        ImageMatrix imgMatrix = new ImageMatrix((BufferedImage) r);
        this.user = user;
        this.imgMatrix = imgMatrix;
        this.postDate = postDate;
        this.description = description;
        likers = new LinkedList<>();
        ID = id_assigner;
        id_assigner ++;
        this.imageFileName = Integer.toString(ID);
    }


    public int getLikeCount(){
        return likers.size();
    }

    public void likePost(User u){
        if(likers.contains(u)){
            likers.remove(u);
        }
        else{
            likers.add(u);
        }

    }

    public int getID(){
        return ID;
    }
    public ImageMatrix getImgMatrix() {
        return imgMatrix;
    }

    public void setImgMatrix(ImageMatrix imgMatrix) {
        this.imgMatrix = imgMatrix;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RenderedImage getImage(){
        return imgMatrix.getBufferedImage();
    }

    @Override
    public int compareTo(Post o) {
        return postDate.compareTo(o.postDate);
    }

    @Override
    public String toFile() {
        StringBuilder sb = new StringBuilder();

        sb.append(user.getNickname());
        sb.append(",");
        sb.append(postDate);
        sb.append(",");
        sb.append(description);
        sb.append(",");
        sb.append(ID);
        sb.append("\n");

        return sb.toString();
    }

    public String getDescription() {
        return description;
    }



    public void setDescription(String description) {
        this.description = description;
    }
}
