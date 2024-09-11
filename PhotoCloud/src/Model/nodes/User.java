package Model.nodes;

import Image.ImageMatrix;
import Image.ImageSecretary;
import Model.db.Writable;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

public class User implements Comparable<User>, Writable {

    private static int IdAssigner = 0;
    protected int id;
    protected final String nickname;
    protected String name;
    protected String surname;

    protected String email;
    protected char[] password;

    public enum tiers{
        ProTier("Pro"),
        HobbyTier("Hobby"),
        FreeTier("Free"),
        Admin("Admin");

        final String val;

        tiers(String val){
            this.val = val;
        }

        public String getVal(){
            return val;
        }

        public static tiers getTier(String tierName){
            if(tierName.equals(ProTier.val))return ProTier;
            if(tierName.equals(HobbyTier.val))return HobbyTier;
            if(tierName.equals(Admin.val))return Admin;
            return FreeTier;
        }
    }



    public tiers tier;


    protected ImageMatrix profilePic = null;

    public User(String nickname, String name, String surname, char[] password,tiers tier) {
        IdAssigner++;
        this.id = IdAssigner;
        this.nickname = nickname;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.tier = tier;
    }

    public User(String nickname, String name, String surname, char[] password,tiers tier, RenderedImage pp) {
        IdAssigner++;
        this.id = IdAssigner;
        this.email = email;
        this.nickname = nickname;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.profilePic = new ImageMatrix((BufferedImage) pp);
        this.tier = tier;
    }

    public String picSaveName(){
        return nickname + "_pp";
    }
    public RenderedImage getProfilePic(){
        return profilePic.getBufferedImage();
    }

    public void setProfilePic(ImageMatrix iMatrix){
        profilePic = iMatrix;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public boolean doesHavePP(){
        return profilePic != null;
    }

    @Override
    public int compareTo(User o) {
        return Integer.compare(this.getId(),o.getId());
    }

    @Override
    public String toFile() {
        StringBuilder sb = new StringBuilder();
        sb.append(nickname);
        sb.append(",");
        sb.append(name);
        sb.append(",");
        sb.append(surname);
        sb.append(",");
        sb.append(password);
        sb.append(",");
        sb.append(picSaveName());
        sb.append(",");
        sb.append(doesHavePP());
        sb.append(",");
        sb.append(tier.val);
        sb.append("\n");
        return sb.toString();
    }
}
