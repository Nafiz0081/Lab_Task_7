import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RegularUser extends User implements ViewAccess {
    public RegularUser(String userId, String username, String email, String password) {
        super(userId, username, email, password);
    }

    @Override
    public void authenticate(String password) {
        if (this.password.equals(password)) {
            System.out.println("Regular user authenticated.");
        } else {
            throw new SecurityException("Invalid password for regular user.");
        }
    }

    @Override
    public void viewUserDetails() {
        System.out.println("Viewing user details:");
        String filePath = "User.csv"; // Path to the CSV file
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line); // Print each line from User.csv
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
}
