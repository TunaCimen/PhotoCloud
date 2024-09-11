package Controller.command;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import Image.ImageSecretary;

public class SetProfilePictureCommand implements Command {

    private RenderedImage image;
    private String name;


    /**
     * Change or add the pp of a user.
     * @param image
     * @param name
     */
    public SetProfilePictureCommand(Image image,String name){
        this.name =name;
        this.image = ImageSecretary.convertImageToRenderedImage(image);

    }


    @Override
    public void execute() {

        System.out.println(ImageSecretary.writeImageToResources(image,name,".jpg"));
    }
}
