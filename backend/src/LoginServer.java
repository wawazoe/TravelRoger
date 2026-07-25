import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.InetSocketAddress;
import java.sql.*;
import java.util.stream.Collectors;

public class LoginServer {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/signup", (HttpExchange exchange) -> {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStream is = exchange.getRequestBody();
                String body = new BufferedReader(new InputStreamReader(is))
                        .lines().collect(Collectors.joining());

                String name = getParam(body, "name");
                String email = getParam(body, "email");
                String password = getParam(body, "password");

                boolean result = signup(name, email, password);
                String response = result ? name : "NG";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });

        server.createContext("/login", (HttpExchange exchange) -> {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStream is = exchange.getRequestBody();
                String body = new BufferedReader(new InputStreamReader(is))
                        .lines().collect(Collectors.joining());
                String email = getParam(body, "email");
                String password = getParam(body, "password");
                String username = checkLogin(email, password);
                String response = username != null ? username : "NG";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });

        server.createContext("/record", (HttpExchange exchange) -> {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStream is = exchange.getRequestBody();
                String body = new BufferedReader(
                    new InputStreamReader(is)
                ).lines().collect(Collectors.joining());
                String title = getParam(body, "title");
                String event_date = getParam(body, "event_date");
                String purpose = getParam(body, "purpose");
                String location = getParam(body, "location");
                String transportation = getParam(body, "transportation");
                String impression = getParam(body, "impression");
                boolean result =
                    saveRecord(
                        title,
                        event_date,
                        purpose,
                        location,
                        transportation,
                        impression
                    );
                String response = result ? "OK" : "NG";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else if ("GET".equals(exchange.getRequestMethod())) {
                String query = exchange.getRequestURI().getQuery();
                String id = query.split("=")[1];
                String response = getRecord(id);
                exchange.getResponseHeaders().add(
                    "Content-Type",
                    "application/json"
                );
                exchange.sendResponseHeaders(
                    200,
                    response.getBytes().length
                );
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });

        server.createContext("/history", (HttpExchange exchange) -> {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            if ("GET".equals(exchange.getRequestMethod())) {
                String response = getHistory();
                exchange.getResponseHeaders().add(
                    "Content-Type",
                    "application/json"
                );
        exchange.sendResponseHeaders(
            200,
            response.getBytes().length
        );
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

    private static String checkLogin(String email, String password) {
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

            if (rs.next()) {
                return rs.getString("name");
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean signup(String name, String email, String password) {
        String url = "jdbc:postgresql://db:5432/travelloger";
        String user = "travelloger";
        String pass = "travelloger";
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, pass);
            String sql = "INSERT INTO users(name, email, password) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.executeUpdate();
            conn.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean saveRecord(
    String title,
    String event_date,
    String purpose,
    String location,
    String transportation,
    String impression
)
{
    String url = "jdbc:postgresql://db:5432/travelloger";
    String user = "travelloger";
    String pass = "travelloger";
    try {
        Class.forName("org.postgresql.Driver");
        Connection conn =
            DriverManager.getConnection(url, user, pass);
        String sql =
            "INSERT INTO records(title, event_date, purpose, location, transportation, impression) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps =
            conn.prepareStatement(sql);
        ps.setString(1, title);
        ps.setDate(
            2,
            java.sql.Date.valueOf(event_date)
        );
        ps.setString(3, purpose);
        ps.setString(4, location);
        ps.setString(5, transportation);
        ps.setString(6, impression);
        ps.executeUpdate();
        conn.close();
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}


    private static String getRecord(String id) {
        String url = "jdbc:postgresql://db:5432/travelloger";
        String user = "travelloger";
        String pass = "travelloger";

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn =
                DriverManager.getConnection(url, user, pass);
            String sql =
                "SELECT * FROM records WHERE id = ?";
            PreparedStatement ps =
                conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(id));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String json = "{";
                json += "\"id\":" + rs.getInt("id") + ",";
                json += "\"title\":\"" + rs.getString("title") + "\",";
                json += "\"event_date\":\"" + rs.getDate("event_date") + "\",";
                json += "\"purpose\":\"" + rs.getString("purpose") + "\",";
                json += "\"location\":\"" + rs.getString("location") + "\",";
                json += "\"transportation\":\"" + rs.getString("transportation") + "\",";
                json += "\"impression\":\"" + rs.getString("impression") + "\"";
                json += "}";
                conn.close();

                return json;
            }
            conn.close();
            return "{}";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
    }
}

            private static String getHistory() {
            String url = "jdbc:postgresql://db:5432/travelloger";
            String user = "travelloger";
            String pass = "travelloger";

            try {
                Class.forName("org.postgresql.Driver");
                Connection conn =
                    DriverManager.getConnection(url, user, pass);
                String sql =
                    "SELECT * FROM records ORDER BY event_date DESC";
                PreparedStatement ps =
                    conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                String json = "[";
                while (rs.next()) {
                    json += "{";
                    json += "\"id\":" + rs.getInt("id") + ",";
                    json += "\"title\":\"" + rs.getString("title") + "\",";
                    json += "\"event_date\":\"" + rs.getDate("event_date") + "\",";
                    json += "\"purpose\":\"" + rs.getString("purpose") + "\",";
                    json += "\"location\":\"" + rs.getString("location") + "\"";
                    json += "},";            
                }

                if (json.endsWith(",")) {
                    json = json.substring(0, json.length() - 1);
                }

                json += "]";
                conn.close();
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                return "[]";
            }

}}
