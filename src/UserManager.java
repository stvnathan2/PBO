import java.sql.*;

public class UserManager {
    private static UserManager instance;

    private UserManager() {
        try (Connection conn = DatabaseUtils.getConnection();
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS user (" +
                         "username TEXT PRIMARY KEY," +
                         "password TEXT NOT NULL," +
                         "namalengkap TEXT NOT NULL," +
                         "email TEXT NOT NULL," +
                         "jenis_kelamin TEXT NOT NULL)";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public boolean addUser(User user) {
        
        if (isUsernameTaken(user.getUsername())) {
            return false;
        }

        String sql = "INSERT INTO user(username, password, namalengkap, email, jenis_kelamin) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getFullName());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getGender());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isUsernameTaken(String username) {
        String sql = "SELECT * FROM user WHERE username = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return true; 
        }
    }

    public boolean validateUser(String username, String password) {
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
