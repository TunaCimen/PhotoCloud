package View;

import Controller.UIController;
import Controller.command.AddCommentCommand;
import Controller.command.ChangeFieldCommand;
import Controller.command.Command;
import Controller.command.ReturnCommand;
import Model.db.PostDatabase;
import Model.nodes.Comment;
import Model.nodes.Post;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CommentFrame extends JFrame implements ActionListener {

    List<Comment> comments;

    JPanel commentPanel;
    JPanel buttonPanel;

    JButton addCommentButton;
    Post post;

    CommentFeed cf;


    /**
     * Initiate the frame of comments for the post
     * @param post
     */
    public CommentFrame(Post post){
        comments = PostDatabase.getInstance().get(post);
        this.post = post;
        setLayout(new BorderLayout());
        buttonPanel = new JPanel();
        commentPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setPreferredSize(new Dimension(500,75));
        this.add(buttonPanel,BorderLayout.NORTH);
        addCommentButton = new JButton("ADD COMMENT");
        addCommentButton.setMaximumSize(new Dimension(121,50));
        buttonPanel.add(addCommentButton);
        addCommentButton.addActionListener(this);
        cf = new CommentFeed();
        this.add(cf.returnMainPanel(),BorderLayout.CENTER);
        this.setSize(new Dimension(500,500));
        this.setVisible(true);
    }

    /**
     * Repaint the comment frame falls it is updated.
     */
    public void update(){
        comments=PostDatabase.getInstance().get(post);
        cf.repaintView();
        this.repaint();
        this.revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JButton b){
            ReturnCommand<String> c = new ChangeFieldCommand(buttonPanel,"Comment: ");
            String comment = c.executeWithReturn();
            Command addCommand = new AddCommentCommand(post, UIController.getUIController().getCurrentUser(), comment);
            addCommand.execute();
            update();
        }
    }

    public class CommentFeed extends RecycleView<CommentHolder>{
        public CommentFeed(){
            super();
            createView();
        }

        @Override
        void extendsRepaint() {

        }

        @Override
        ViewHolder onCreateHolder() {
            return new CommentHolder(commentPanel);
        }

        @Override
        void onBindHolder(ViewHolder panel, int position) {
            CommentHolder commentHolder = (CommentHolder) panel;
            commentHolder.nicknameLabel.setText("user: " + comments.get(position).getUser().getNickname());
            commentHolder.commentLabel.setText(comments.get(position).getComment());
        }

        @Override
        int getItemCount() {
            return comments.size();
        }
    }



    private static class CommentHolder extends ViewHolder {
        JLabel nicknameLabel;
        JLabel commentLabel;
        public CommentHolder(JPanel binding) {
            super(binding);
            nicknameLabel = new JLabel();
            commentLabel =new JLabel();
            addComponents();
        }

        @Override
        public void addComponents() {
            viewHolder.add(nicknameLabel);
            viewHolder.add(Box.createHorizontalStrut(2));
            viewHolder.add(commentLabel);
        }
    }
}
