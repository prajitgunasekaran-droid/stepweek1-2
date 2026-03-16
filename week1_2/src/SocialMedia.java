import java.util.*;

class UsernameSystem {

    // HashMap to store registered usernames
    HashMap<String, Integer> users = new HashMap<>();

    // HashMap to store attempt frequency
    HashMap<String, Integer> attempts = new HashMap<>();


    // Check if username is available
    public boolean checkAvailability(String username) {

        // increase attempt count
        attempts.put(username, attempts.getOrDefault(username, 0) + 1);

        // check existence
        if (users.containsKey(username)) {
            return false;
        }

        return true;
    }


    // Register a new user
    public void registerUser(String username, int userId) {
        users.put(username, userId);
    }


    // Suggest alternative usernames
    public List<String> suggestAlternatives(String username) {

        List<String> suggestions = new ArrayList<>();

        // append numbers
        for (int i = 1; i <= 5; i++) {

            String newName = username + i;

            if (!users.containsKey(newName)) {
                suggestions.add(newName);
            }
        }

        // replace underscore with dot
        String modified = username.replace("_", ".");

        if (!users.containsKey(modified)) {
            suggestions.add(modified);
        }

        return suggestions;
    }


    // Get most attempted username
    public String getMostAttempted() {

        int max = 0;
        String result = "";

        for (Map.Entry<String, Integer> entry : attempts.entrySet()) {

            if (entry.getValue() > max) {
                max = entry.getValue();
                result = entry.getKey();
            }
        }

        return result;
    }


    // Main method for testing
    public static void main(String[] args) {

        UsernameSystem system = new UsernameSystem();

        system.registerUser("john_doe", 101);
        system.registerUser("alice", 102);

        System.out.println(system.checkAvailability("john_doe"));   // false
        System.out.println(system.checkAvailability("jane_smith")); // true

        System.out.println(system.suggestAlternatives("john_doe"));

        System.out.println(system.getMostAttempted());
    }
}