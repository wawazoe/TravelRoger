import java.sql.*;

public class Login {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://db:5432/travelloger";
        String user = "travelloger";
        String password = "travelloger";
        String inputEmail = "test@test.com";
        String inputPassword = "1234";

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, inputEmail);
            ps.setString(2, inputPassword);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("ログイン成功");
            } else {
                System.out.println("ログイン失敗");
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}