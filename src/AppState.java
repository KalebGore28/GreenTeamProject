public class AppState {
    private static User currentUser;
    private static String currentPanelName;
    private static String previousPanelName;

    // Synchronized setter
    public static synchronized void setCurrentUser(User user) {
        currentUser = user;
        System.out.println("Set Current User: " + currentUser);
    }

    // Synchronized getter
    public static synchronized User getCurrentUser() {
        return currentUser;
    }

    public static synchronized void setCurrentPanelName(String panelName) {
        previousPanelName = currentPanelName;
        currentPanelName = panelName;
        System.out.println("Current Panel: " + currentPanelName);
    }

    public static synchronized String getCurrentPanelName() {
        return currentPanelName;
    }

    public static synchronized String getPreviousPanelName() {
        return previousPanelName;
    }

    public static void clearUserState() {
        currentUser = null;
        currentPanelName = null;
        previousPanelName = null;
    }
}