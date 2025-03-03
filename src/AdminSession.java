public class AdminSession {
    private static String username;

    public static void setUsername(String username) {
        AdminSession.username = username;
    }

    public static String getUsername() {
        return username;
    }
}
