import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://db:5432/travelloger";
        String user = "travelloger";
        String password = "travelloger";

        String sql = "INSERT INTO users (name) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "yuuhi_java");
            pstmt.executeUpdate();

            System.out.println("登録成功！");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}