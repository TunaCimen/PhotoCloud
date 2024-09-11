package View;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public abstract class ViewHolder {

    protected JPanel viewHolder;

    static LinkedList<ViewHolder> holders = new LinkedList<>();//list of holders.

    /**
     * Which super panel this viewHolder going to be attached to.
     * @param binding
     */
    public ViewHolder(JPanel binding){
        viewHolder=new JPanel();
        viewHolder.setMaximumSize(new Dimension(1000,100));
        binding.add(viewHolder);
        holders.add(this);


    }

    /**
     * If needed classes layout can be changed.
     * @param layout
     */
    public void setLayout(LayoutManager layout){
        viewHolder.setLayout(layout);
    }

    /**
     *     Must implement, what will the individual view holders hold.
     */

    public abstract void addComponents();
}
