import Model.db.Reader;
import Controller.UIController;


public class Main {
    public static void main(String[] args) {
        Reader r = new Reader();
        r.readUsers();
        r.readPosts();
        UIController uiController = UIController.getUIController();
    }
}