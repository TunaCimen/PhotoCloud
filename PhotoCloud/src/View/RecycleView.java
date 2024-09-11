package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public abstract class RecycleView<VH extends ViewHolder> {
    private int oldPosition;
    private int currentPosition;
    protected JScrollPane adapter;


    protected JPanel containerPanel;

    protected JPanel mainPanel;


    public RecycleView(){
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel,BoxLayout.Y_AXIS));
        adapter = new JScrollPane(containerPanel);
        mainPanel.add(adapter,gbc);
    }


    protected void createView() {
        for (int i = 0; i < getItemCount(); i++) {
            ViewHolder v = onCreateHolder();
            onBindHolder(v, i);
            containerPanel.add(v.viewHolder);
            containerPanel.add(Box.createVerticalGlue());
            containerPanel.getComponent(containerPanel.getComponentCount() - 1)
                    .setMaximumSize(new Dimension(0, 0));
        }
    }

    /**
     * Called when an update is made.
     */
    public void repaintView(){
        extendsRepaint();
        containerPanel.removeAll();
        ViewHolder.holders.clear();
        createView();

    }

    //Extended utilities for the repaintView()
    abstract void extendsRepaint();


    //Method to fill what to add when ViewHolder is created.
    abstract ViewHolder onCreateHolder();

    //How to bind each ViewHolder with container list values.
    abstract void onBindHolder(ViewHolder panel,int position);

    //What indicated the size of the items.
    abstract int getItemCount();

    public JPanel returnMainPanel(){
        return mainPanel;
    }



}
