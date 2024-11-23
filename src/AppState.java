public class AppState {
    private static User currentUser;

    // Manage the current user
    public static synchronized void setCurrentUser(User user) {
        currentUser = user;
        System.out.println("Set Current User: " + currentUser);
    }

    public static synchronized User getCurrentUser() {
        return currentUser;
    }

    // Clear user state (e.g., during logout)
    public static void clearUserState() {
        currentUser = null;
    }
}