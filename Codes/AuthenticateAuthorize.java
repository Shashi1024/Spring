import java.util.Map;

public class AuthenticateAuthorize {
    // A simple in-memory "database" of users and their roles.
    // Maps: username -> role
    private static final Map<String, String> userRoles = Map.of(
        "admin", "ADMIN",
        "guest", "GUEST"
    );

    // A simple in-memory "database" of user passwords.
    // Maps: username -> password
    private static final Map<String, String> userPasswords = Map.of(
        "admin", "admin123",
        "guest", "guestpass"
    );

    /**
     * AUTHENTICATION: "Who are you?"
     * Verifies credentials and returns the user's role if successful.
     */
    public static String authenticate(String username, String password) {
        System.out.println("\n--- Authenticating user: " + username + " ---");
        String expectedPassword = userPasswords.get(username);
        if (expectedPassword != null && expectedPassword.equals(password)) {
            System.out.println("✅ Authentication successful.");
            return userRoles.get(username); // Return the user's role
        }
        System.out.println("❌ Authentication failed.");
        return null; // Failed authentication
    }

    /**
     * AUTHORIZATION: "What are you allowed to do?"
     * Checks if a given role has permission for an action.
     */
    public static boolean authorize(String role, String permission) {
        System.out.println("--- Authorizing role '" + role + "' for permission '" + permission + "' ---");
        if (role == null) return false;

        switch (permission) {
            case "DELETE_FILES":
                // Only the ADMIN role can delete files
                boolean isAuthorized = "ADMIN".equals(role);
                System.out.println(isAuthorized ? "✅ Authorization successful." : "❌ Authorization failed.");
                return isAuthorized;
            default:
                System.out.println("❌ Unknown permission.");
                return false;
        }
    }

    public static void main(String[] args) {
        // --- SCENARIO 1: Successful Admin Login and Action ---
        System.out.println("--- SCENARIO 1: Admin attempts to delete files ---");
        String adminRole = authenticate("admin", "admin123");
        if (authorize(adminRole, "DELETE_FILES")) {
            System.out.println(">> ACTION: Admin user successfully deleted the files.\n");
        }

        // --- SCENARIO 2: Successful Guest Login, Failed Action ---
        System.out.println("\n--- SCENARIO 2: Guest attempts to delete files ---");
        String guestRole = authenticate("guest", "guestpass");
        if (!authorize(guestRole, "DELETE_FILES")) {
            System.out.println(">> ACTION: Guest user could not perform the action.\n");
        }
        
        // --- SCENARIO 3: Failed Login Attempt ---
        System.out.println("\n--- SCENARIO 3: A user with wrong password attempts to log in ---");
        String failedRole = authenticate("admin", "wrongpassword");
        if (failedRole == null) {
            System.out.println(">> ACTION: System prevented access.\n");
        }
    }
}
