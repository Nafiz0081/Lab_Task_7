public interface AdminControl {
    void modifyUserDetails(String userId, String newEmail, String newPassword);

    void addAdminUser(String userId, String username, String email, String password);

    void renameFile(String oldFileName, String newFileName);
}
