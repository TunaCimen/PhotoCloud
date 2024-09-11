package Controller.command;

import Image.ImageFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import Image.*;

public class EffectCommand implements ReturnCommand<ImageIcon> {


    ImageFactory.Effects effect;
    int intensity;
    Image img;

    /**
     * @param img img to apply effect
     * @param effect effect to apply.
     * @param intensity adjuster.
     */
    public EffectCommand(Image img, ImageFactory.Effects effect, int intensity){
        this.effect = effect;
        this.intensity = intensity;
        this.img = img;
    }

    @Override
    public void execute() {

    }

    @Override
    public ImageIcon executeWithReturn() {

        ImageMatrix imgMatrix = new ImageMatrix((BufferedImage) ImageSecretary.convertImageToRenderedImage(img));
        ImageMatrix blurred = ImageFactory.getImageFactory().applyEffect(imgMatrix,effect,intensity);

        return new ImageIcon((BufferedImage)blurred.getBufferedImage());
    }


}
