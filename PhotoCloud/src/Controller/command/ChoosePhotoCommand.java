package Controller.command;

import Model.db.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ChoosePhotoCommand implements ReturnCommand<ImageIcon>{

    JFileChooser fileChooser;

    private int maxWidth;

    private int maxHeight;

    /**
     * Photo chooser from the file system.
     */
    public ChoosePhotoCommand(){
        fileChooser = new JFileChooser();
        maxWidth = 200;
        maxHeight = 200;
    }

    public ChoosePhotoCommand(int maxHeight,int maxWidth){
        fileChooser = new JFileChooser();
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
    }

    public ImageIcon executeWithReturn() {
        int returnValue = fileChooser.showOpenDialog(null);
        if(returnValue == JFileChooser.APPROVE_OPTION){
            File selectedFile = fileChooser.getSelectedFile();
            ImageIcon photoSelected = new ImageIcon(selectedFile.getAbsolutePath());
            Image img = photoSelected.getImage();
            Image scaledImg = img.getScaledInstance(maxWidth,maxHeight,Image.SCALE_SMOOTH);

            return new ImageIcon(scaledImg);
        }
        return null;
    }

    @Override
    public void execute() {

    }
}
