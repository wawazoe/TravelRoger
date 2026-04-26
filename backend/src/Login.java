package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login {
    public static void main(String[] args) {

        String sql = "SELECT * FROM users WHERE email=? AND password=?";
        String url = "jdbc:postgresql://db:5432/travelloger";
        String user = "travelloger";
        String password = "travelloger";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, "test@test.com");
            pstmt.setString(2, "1234");

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("ログイン成功");
            } else {
                System.out.println("ログイン失敗");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}