import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PlanDAO {

    // ADD PLAN
    public boolean addPlan(String name, int price, int duration) {
        String sql = "INSERT INTO plans(plan_name, price, duration_days) VALUES (?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, name);
            pst.setInt(2, price);
            pst.setInt(3, duration);

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // VIEW ALL PLANS
    public void viewPlans() {
        String sql = "SELECT * FROM plans";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            ResultSet rs = pst.executeQuery();

            System.out.println("\n--- AVAILABLE PLANS ---");
            while (rs.next()) {
                System.out.println(

                                " | Name: " + rs.getString("plan_name") +
                                " | Price: " + rs.getInt("price") +
                                " | Days: " + rs.getInt("duration_days")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
