package View;

import javax.swing.*;
import java.awt.*;

public interface Page{

    JPanel getPanel();

    /**
     *
     * @return are menus of the frame should be visible for this page.
     */
     default boolean isMenuVisible(){
        return true;
    }

    /**
     * Executed when Page is exited
     */
    default void onExit(){

    }

    /**
     * Repaint the components of the panel.
     */
    default void repaintPage(){
        getPanel().repaint();
        getPanel().revalidate();
    }

    /**
     * Executed when page is opened.
     */
    default void onStart(){

    }


}
