import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AttendanceDAO {

    // MARK ATTENDANCE (current date)
    public boolean markAttendance(int memberId) {
        String sql = "INSERT INTO attendance(member_id, date) VALUES (?, CURDATE())";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, memberId);
            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // VIEW ATTENDANCE OF A MEMBER
    public void viewAttendance(int memberId) {
        String sql = "SELECT date FROM attendance WHERE member_id = ? ORDER BY date DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, memberId);
            ResultSet rs = pst.executeQuery();

            System.out.println("\nAttendance for Member ID: " + memberId);
            while (rs.next()) {
                System.out.println("Date: " + rs.getDate("date"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
