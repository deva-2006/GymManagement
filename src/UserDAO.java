import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    // SIGNUP (admin only)
    public boolean signup(String username, String password, String role) {
        String sql = "INSERT INTO users(username, password, role) VALUES (?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, role);

            int rows = pst.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // LOGIN
    public String login(String username, String password) {
        String sql = "SELECT role FROM users WHERE username=? AND password=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getString("role");  // "admin" / "member"
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // invalid login
    }
}
