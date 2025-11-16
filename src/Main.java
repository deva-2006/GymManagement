import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {   // MAIN LOOP — keeps program running
            System.out.println("==== GYM MANAGEMENT SYSTEM ====");
            System.out.println("1. Admin Login");
            System.out.println("2. Member Login");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                adminLogin();   // after logout → comes back here
            }
            else if (choice == 2) {
                memberLoginMenu();  // after logout → comes back here
            }
            else if (choice == 3) {
                System.out.println("Goodbye!");
                return;
            }
            else {
                System.out.println("Invalid choice, try again.");
            }
        }
    }

    // ---------------------------------------------------------
    // ADMIN LOGIN
    // ---------------------------------------------------------
    public static void adminLogin() {
        Scanner sc = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();

        System.out.print("Admin Username: ");
        String username = sc.nextLine();

        System.out.print("Admin Password: ");
        String password = sc.nextLine();

        String role = userDAO.login(username, password);

        if ("admin".equals(role)) {
            System.out.println("Admin Login Successful!");
            adminMenu();   // after logout, returns here
        } else {
            System.out.println("Invalid admin credentials.");
        }
    }

    // ---------------------------------------------------------
    // ADMIN MENU
    // ---------------------------------------------------------
    public static void adminMenu() {
        Scanner sc = new Scanner(System.in);
        PlanDAO planDAO = new PlanDAO();
        MemberDAO memberDAO = new MemberDAO();

        while (true) {
            System.out.println("\n---- ADMIN MENU ----");
            System.out.println("1. Add Plan");
            System.out.println("2. View Plans");
            System.out.println("3. Add Member");
            System.out.println("4. View Members");
            System.out.println("5. Update Member Plan");
            System.out.println("6. Delete Member");
            System.out.println("7. Logout");
            System.out.print("Choose: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    sc.nextLine();
                    System.out.print("Plan Name: ");
                    String name = sc.nextLine();
                    System.out.print("Price: ");
                    int price = sc.nextInt();
                    System.out.print("Duration (days): ");
                    int days = sc.nextInt();

                    if (planDAO.addPlan(name, price, days))
                        System.out.println("Plan added successfully!");
                    else
                        System.out.println("Failed to add plan.");
                    break;

                case 2:
                    planDAO.viewPlans();
                    break;

                case 3:
                    sc.nextLine();
                    System.out.print("Member Name: ");
                    String mname = sc.nextLine();
                    System.out.print("Age: ");
                    int age = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Phone: ");
                    String phone = sc.nextLine();
                    System.out.print("Plan Name: ");
                    String plan_name = sc.nextLine();

                    int newId = memberDAO.addMember(mname, age, phone, plan_name);

                    if (newId != -1)
                        System.out.println("Member added! Member ID = " + newId);
                    else
                        System.out.println("Failed to add member.");
                    break;

                case 4:
                    memberDAO.viewMembers();
                    break;

                case 5:
                    System.out.print("Enter Member ID: ");
                    int uid = sc.nextInt();

                    sc.nextLine();
                    System.out.print("Enter New Plan Name: ");
                    String newPlanName = sc.nextLine();

                    if (memberDAO.updateMemberPlan(uid, newPlanName))



                        System.out.println("Plan updated successfully!");
                    else
                        System.out.println("Failed to update plan.");
                    break;

                case 6:
                    System.out.print("Enter Member ID to Delete: ");
                    int delId = sc.nextInt();

                    if (memberDAO.deleteMember(delId))
                        System.out.println("Member deleted successfully!");
                    else
                        System.out.println("Failed to delete member.");
                    break;

                case 7:
                    System.out.println("Logging out...");
                    return;   // RETURNS TO MAIN MENU
            }
        }
    }

    // ---------------------------------------------------------
    // MEMBER LOGIN
    // ---------------------------------------------------------
    public static void memberLoginMenu() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Member ID: ");
        int memberId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Phone Number: ");
        String phone = sc.nextLine();

        if (validateMemberLogin(memberId, phone)) {
            System.out.println("Login Successful!");
            memberMenu(memberId);   // after logout → returns here → main loop
        } else {
            System.out.println("Invalid Member ID or Phone Number.");
        }
    }

    // Validate Member Login
    public static boolean validateMemberLogin(int memberId, String phone) {
        String sql = "SELECT * FROM members WHERE id=? AND phone=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, memberId);
            pst.setString(2, phone);

            ResultSet rs = pst.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Remaining Days
    public static int getRemainingDays(int memberId) {

        String sql = "SELECT p.duration_days, DATEDIFF(CURDATE(), m.join_date) AS used " +
                "FROM members m JOIN plans p ON m.plan_name = p.plan_name " +
                "WHERE m.id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, memberId);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int duration = rs.getInt("duration_days");
                int used = rs.getInt("used");
                return duration - used;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // ---------------------------------------------------------
    // MEMBER MENU
    // ---------------------------------------------------------
    public static void memberMenu(int memberId) {

        int remaining = getRemainingDays(memberId);
        System.out.println("\nMember ID: " + memberId);
        System.out.println("[" + remaining + " day(s) remaining]");

        Scanner sc = new Scanner(System.in);
        AttendanceDAO attendanceDAO = new AttendanceDAO();

        while (true) {
            System.out.println("\n---- MEMBER MENU ----");
            System.out.println("1. Mark Attendance");
            System.out.println("2. View Attendance");
            System.out.println("3. Logout");
            System.out.print("Choose: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    if (attendanceDAO.markAttendance(memberId))
                        System.out.println("Attendance marked!");
                    else
                        System.out.println("Failed to mark attendance.");
                    break;

                case 2:
                    attendanceDAO.viewAttendance(memberId);
                    break;

                case 3:
                    System.out.println("Logging out...");
                    return;   // ← GOES BACK TO MAIN MENU
            }
        }
    }

}
