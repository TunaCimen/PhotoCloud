package Model.nodes;

public class Comment {

    String comment;
    User u;

    public Comment(User u, String comment) {
        this.comment = comment;
        this.u = u;
    }


    public User getUser(){
        return u;
    }

    public String getComment(){
        return comment;
    }
}
