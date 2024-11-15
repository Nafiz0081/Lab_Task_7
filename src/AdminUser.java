import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AdminUser extends User implements AdminControl {
    public AdminUser(String userId, String username, String email, String password) {
        super(userId, username, email, password);
    }

    @Override
    public void authenticate(String password) {
        if (this.password.equals(password)) {
            System.out.println("Admin user authenticated.");
        } else {
            throw new SecurityException("Invalid password for admin.");
        }
    }


    @Override
    public void modifyUserDetails(String userId, String newEmail, String newPassword) {
        System.out.println("Modifying user details for user: " + userId);

        // Logic to modify user details in User.csv
        try {
            // Read all lines from the file
            List<String> lines = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader("User.csv"));
            String line;
            boolean userFound = false;

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields[0].equals(userId)) {
                    // Modify the user's details
                    fields[2] = newEmail; // Update email
                    fields[3] = newPassword; // Update password
                    line = String.join(",", fields);
                    userFound = true;
                }
                lines.add(line);
            }
            br.close();

            // Write updated lines back to the file
            if (userFound) {
                BufferedWriter bw = new BufferedWriter(new FileWriter("User.csv"));
                for (String updatedLine : lines) {
                    bw.write(updatedLine);
                    bw.newLine();
                }
                bw.close();
                System.out.println("User details updated successfully.");
            } else {
                System.out.println("User with ID " + userId + " not found.");
            }
        } catch (IOException e) {
            System.out.println("Error modifying user details: " + e.getMessage());
        }
    }


    @Override
    public void addAdminUser(String userId, String username, String email, String password) {
        AdminUser newAdmin = new AdminUser(userId, username, email, password);
        UserManager.getInstance().getAdminUsers().add(newAdmin);
        UserManager.getInstance().saveAdminsToFile("Admin.csv");
        System.out.println("Admin user added and saved to Admin.csv successfully.");
    }

    @Override
    public void renameFile(String oldFileName, String newFileName) {
        //
    }
}
