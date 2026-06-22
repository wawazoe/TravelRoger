import java.sql.*;

public class Record {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://db:5432/travelloger";
        String title = "title";
        String event_date = "2026-01-01";
        String purpose = "旅行";
        String location = "東京";
        String transportation = "飛行機";
        String impression = "楽しい";

        try {
            Class.forName("org.postgresql.Driver");
            String user = "travelloger";
            String pass = "travelloger";
            Connection conn =
            DriverManager.getConnection(url, user, pass);
            String sql =
                "INSERT INTO records(title, event_date, purpose, location, transportation, impression) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, title);
            ps.setDate(
                2,
                java.sql.Date.valueOf(event_date)
            );
            ps.setString(3, purpose);
            ps.setString(4, location);
            ps.setString(5, transportation);
            ps.setString(6, impression);
            int count = ps.executeUpdate();

System.out.println(title);

System.out.println(event_date);

System.out.println(purpose);

System.out.println(location);

System.out.println(transportation);

System.out.println(impression);


            if (count > 0) {
                System.out.println("投稿しました");
            } else {
                System.out.println("投稿失敗");
            }
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}