package View;

import Model.nodes.Comment;

public enum Pages {

    /**
     * Enum to hold the all pages available.
     */

    Login("Login", new LoginPage()),
    Discover("Discover", new DiscoverPage()),

    Images("Images", new ImagePage()),

    Profile("Profile", new ProfilePage()),
    SignUp("SignUp", new SignUpPage()),

    Post("Post",new PostPage()),
    Search("Search",new SearchUserPage());

    private final String val;
    private  Page page;
    Pages(String val, Page commentPage) {
        this.val = val;
        this.page = commentPage;
    }

    public String getValue() {
        return val;
    }
    public Page getPage(){
        return page;
    }
    public void setPage(Page p){page = p;}


}
