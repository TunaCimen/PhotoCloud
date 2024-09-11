package View;

import Controller.UIController;
import Controller.command.Command;
import Controller.command.DeletePostCommand;
import Controller.command.LikeCommand;
import Model.db.PostDatabase;
import Model.db.UserDatabase;
import Model.nodes.Comment;
import Model.nodes.Post;
import Model.nodes.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;


public class DiscoverPage extends RecycleView<DiscoverPage.FeedHolder> implements Page  {

    //PostDatabase postDatabase;
    List<Map.Entry<Post,List<Comment>>> posts;




    public DiscoverPage() {
        super();
        //postDatabase = PostDatabase.getInstance();
        posts = PostDatabase.getInstance().getNonPrivate();
        createView();
    }

    @Override
    void extendsRepaint() {

    }



    @Override
    public void repaintPage() {
        onStart();
    }

    @Override
    ViewHolder onCreateHolder() {
        return new FeedHolder(mainPanel);
    }

    @Override
    void onBindHolder(ViewHolder v, int position) {
        FeedHolder feedHolder = (FeedHolder) v;
        feedHolder.nicknameLabel
                .setText(posts.get(position).getKey().getUser().getNickname());
        feedHolder.p = posts.get(position).getKey();
        feedHolder.descriptionLabel.setText(posts.get(position).getKey().getDescription());

        ImageIcon photoIcon = new ImageIcon((BufferedImage)posts.get(position).getKey().getImage());
        //ImageIcon photoIcon = new ImageIcon("./images/megadeth.jpeg");
        int maxWidth = 90;
        int maxHeight = 90;
        Image img = photoIcon.getImage();
        Image scaledImg = img.getScaledInstance(maxWidth,maxHeight,Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg);
        feedHolder.photoLabel.setIcon(scaledIcon);
    }

    @Override
    public void onStart() {
        System.out.println("Discover on start ");
        FeedHolder.holders.clear();
        posts = PostDatabase.getInstance().getNonPrivate();
        System.out.println("size of holders" + FeedHolder.holders.size());
        repaintView();
        for(ViewHolder holder : FeedHolder.holders){
            FeedHolder fh = (FeedHolder) holder;
            if(UIController.getUIController().getCurrentUser().tier == User.tiers.Admin){
                System.out.println("admin so set true");
                fh.deleteButton.setVisible(true);

            }
        }

    }

    @Override
    public void onExit() {
        for(ViewHolder holder : FeedHolder.holders){
            FeedHolder fh = (FeedHolder) holder;
            if(UIController.getUIController().getCurrentUser().tier == User.tiers.Admin){
                fh.deleteButton.setVisible(false);
            }
        }
    }

    @Override
    int getItemCount() {
        return posts.size();
    }


    @Override
    public JPanel getPanel() {
        return mainPanel;
    }


    public static class FeedHolder extends ViewHolder implements ActionListener, MouseListener {
        public JLabel nicknameLabel;
        public JLabel descriptionLabel;
        public JLabel photoLabel;

        public Post p;
        public JButton commentsButton;

        public JButton deleteButton;

        public JButton likeButton;
        public FeedHolder(JPanel binding) {
            super(binding);
            deleteButton = new JButton("Delete");
            deleteButton.addActionListener(this);
            likeButton = new JButton("Like");
            likeButton.setBackground(Color.GRAY);
            commentsButton = new JButton("Comments");
            commentsButton.addActionListener(this);
            likeButton.addActionListener(this);
            nicknameLabel = new JLabel("nickname");
            descriptionLabel = new JLabel("desc");
            photoLabel = new JLabel();
            nicknameLabel.addMouseListener(this);
            addComponents();

        }
        @Override
        public void addComponents() {
            viewHolder.setLayout(new BoxLayout(viewHolder,BoxLayout.X_AXIS));
            viewHolder.add(photoLabel);
            deleteButton.setVisible(false);
            viewHolder.add(Box.createHorizontalStrut(10));
            viewHolder.add(nicknameLabel);
            viewHolder.add(Box.createHorizontalStrut(10));
            viewHolder.add(descriptionLabel);
            viewHolder.add(commentsButton);
            viewHolder.add(likeButton);
            viewHolder.add(deleteButton);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() instanceof JButton button){
                if(button.getText().equals(commentsButton.getText())){
                    UIController.getUIController().showCommentFrame(p);

                }
                else if(button.getText().equals("Delete")){
                    System.out.println("Delete is clickeddd");
                    Command deletePostCommand = new DeletePostCommand(p);
                    deletePostCommand.execute();
                    Pages.Discover.getPage().repaintPage();

                }
                if(button.getText().equals(likeButton.getText())){
                    Command c = new LikeCommand(UIController.getUIController().getCurrentUser(), p);
                    c.execute();
                    if(button.getText().equals("Like")){
                        button.setText(Integer.toString(p.getLikeCount()));
                        viewHolder.repaint();
                        viewHolder.revalidate();
                    }

                    else{
                        button.setText("Like");
                        viewHolder.repaint();
                        viewHolder.revalidate();
                    }
                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getSource() instanceof JLabel labelPressed){
                if(labelPressed.getText().equals(nicknameLabel.getText())){
                    User u = UserDatabase.getInstance().searchByNickname(labelPressed.getText());
                    UIController.getUIController().showProfileFor(u);
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }


}
