import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MemberDAO {

    // ADD MEMBER
    public int addMember(String name, int age, String phone, String plan_name) {
        String sql = "INSERT INTO members(name, age, phone, plan_name, join_date) VALUES (?,?,?,?,CURDATE())";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, name);
            pst.setInt(2, age);
            pst.setString(3, phone);
            pst.setString(4, plan_name);

            int rows = pst.executeUpdate();

            if (rows > 0) {
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);  // return member ID
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;  // failure
    }


    // VIEW ALL MEMBERS
    public void viewMembers() {
        String sql = "SELECT id, name, age, phone, plan_name, join_date FROM members";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            ResultSet rs = pst.executeQuery();

            System.out.println("\n--- MEMBERS LIST ---");
            while (rs.next()) {
                System.out.println(
                        "ID: " + rs.getInt("id") +
                                " | Name: " + rs.getString("name") +
                                " | Age: " + rs.getInt("age") +
                                " | Phone: " + rs.getString("phone") +
                                " | Plan: " + rs.getString("plan_name") +
                                " | Join Date: " + rs.getDate("join_date")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean updateMemberPlan(int memberId, String newPlanName) {

        String sql = "UPDATE members SET plan_name = ? WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, newPlanName);
            pst.setInt(2, memberId);

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteMember(int memberId) {

        String sql = "DELETE FROM members WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, memberId);

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
