public class CurrentUser {
    private static CurrentUser instance = new CurrentUser();
    private CurrentUser(){}
    public static CurrentUser getInstance() {
        return instance;
    }

    private String currentUser;

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }
}
