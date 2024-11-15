import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PowerUser extends User implements EditAccess {
    public PowerUser(String userId, String username, String email, String password) {
        super(userId, username, email, password);
    }

    @Override
    public void authenticate(String password) {
        if (this.password.equals(password)) {
            System.out.println("Power user authenticated.");
        } else {
            throw new SecurityException("Invalid password for power user.");
        }
    }

    @Override
    public void addUser(String userId, String username, String email, String password, String userType) {
        System.out.println("Adding user: " + username);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("User.csv", true))) {
            String newUserLine = userId + "," + username + "," + email + "," + password + "," + userType;
            bw.write(newUserLine);
            bw.newLine();
            System.out.println("User added successfully.");
        } catch (IOException e) {
            System.out.println("Error while adding user to User.csv: " + e.getMessage());
        }
    }
}
