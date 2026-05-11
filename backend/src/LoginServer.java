import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.InetSocketAddress;
import java.sql.*;
import java.util.stream.Collectors;

public class LoginServer {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/login", (HttpExchange exchange) -> {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStream is = exchange.getRequestBody();
                String body = new BufferedReader(new InputStreamReader(is))
                        .lines().collect(Collectors.joining());
                // 👇ここで使う
                String email = getParam(body, "email");
                String password = getParam(body, "password");
                boolean isLogin = checkLogin(email, password);
                String response = isLogin ? "OK" : "NG";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });
        server.start();
        System.out.println("Server started on 8081");
    }

    private static String getParam(String body, String key) {
        String[] params = body.split("&");
        for (String param : params) {
            String[] pair = param.split("=");
            if (pair[0].equals(key)) {
                return java.net.URLDecoder.decode(
                    pair[1],
                    java.nio.charset.StandardCharsets.UTF_8
                );
            }
        }
        return "";

    }

    private static boolean checkLogin(String email, String password) {
        String url = "jdbc:postgresql://db:5432/travelloger";
        String user = "travelloger";
        String pass = "travelloger";

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, pass);
            String sql = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            boolean result = rs.next();
            conn.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}