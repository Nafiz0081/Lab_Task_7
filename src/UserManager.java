import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static UserManager instance;
    private List<User> users;
    private List<AdminUser> adminUsers;

    private UserManager() {
        users = new ArrayList<>();
        adminUsers = new ArrayList<>();
        loadUsersFromFile("User.csv");
        loadAdminsFromFile("Admin.csv");
    }

    public static UserManager getInstance() {
        if (instance == null) {
            synchronized (UserManager.class) {
                if (instance == null) {
                    instance = new UserManager();
                }
            }
        }
        return instance;
    }

    private void loadUsersFromFile(String filePath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                String userId = fields[0];
                String username = fields[1];
                String email = fields[2];
                String password = fields[3];
                UserType userType = UserType.valueOf(fields[4]);

                switch (userType) {
                    case REGULAR -> users.add(new RegularUser(userId, username, email, password));
                    case POWER -> users.add(new PowerUser(userId, username, email, password));
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error loading users from User.csv: " + e.getMessage());
        }
    }

    private void loadAdminsFromFile(String filePath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                String userId = fields[0];
                String username = fields[1];
                String email = fields[2];
                String password = fields[3];
                adminUsers.add(new AdminUser(userId, username, email, password));
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error loading admins from Admin.csv: " + e.getMessage());
        }
    }

    public void saveAdminsToFile(String filePath) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
            for (AdminUser admin : adminUsers) {
                String line = admin.getUserId() + "," + admin.getUsername() + "," + admin.getEmail() + "," + admin.password;
                bw.write(line);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Error saving admins to Admin.csv: " + e.getMessage());
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public List<AdminUser> getAdminUsers() {
        return adminUsers;
    }

    public AdminUser authenticateAdmin(String username, String password) {
        for (AdminUser admin : adminUsers) {
            if (admin.getUsername().equals(username)) {
                admin.authenticate(password);
                return admin;
            }
        }
        throw new SecurityException("Admin user not found or invalid credentials.");
    }

    public User authenticate(String username, String password) {
        // Check in admin users
        for (AdminUser admin : adminUsers) {
            if (admin.getUsername().equals(username)) {
                admin.authenticate(password);
                System.out.println("Admin user authenticated.");
                return admin;
            }
        }

        // Check in regular users
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                user.authenticate(password);
                System.out.println("Regular or Power user authenticated.");
                return user;
            }
        }

        // If no user matches, throw an exception
        throw new SecurityException("Invalid username or password.");
    }

}
