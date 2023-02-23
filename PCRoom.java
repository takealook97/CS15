import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class PCRoom {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/pc_room_db";
        String userName = "takealook";
        String password = "0205";
        Connection con = null;
        PreparedStatement userPst = null;
        PreparedStatement pcPst = null;
        PreparedStatement timePst = null;
        String pcSQL = "INSERT INTO pc(id, pc_number) VALUES (?, ?)";
        String userSQL = "INSERT INTO user(id, user_number) VALUES (?, ?)";
        String timeSQL = "INSERT INTO time(id, start_time, end_time) VALUES (?, ?, ?)";

        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, userName, password);
//            con.setAutoCommit(false);
            userPst = con.prepareStatement(userSQL);
            pcPst = con.prepareStatement(pcSQL);
            timePst = con.prepareStatement(timeSQL);
            for (int i = 1; i <= 2; i++) {
                for (int j = 1; j <= 16; j++) {
                    pcPst.setInt(i, j);
                    userPst.setInt(i, j);
                    pcPst.executeUpdate();
                    userPst.executeUpdate();
                }
            }


            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            Output output = new Output();
            Counter counter = new Counter();
            output.printInit();

            while (true) {
                System.out.print("> ");
                String[] input = br.readLine().split(" ");
                switch (input[0]) {
                    case "new" -> {
                        counter.getPcNumber();
                        counter.getUserNumber();
                        output.printOrder(counter.PcNumber, counter.UserNumber);

                    }
                    case "stop" -> {
                        counter.stop(Integer.parseInt(input[1]));
                        output.printEmptyMessage(counter.PcNumber);
                    }
                    case "close" -> System.exit(0);
                    default -> output.printError();
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Add this line to print the full stack trace
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            } finally {
                if (userPst != null) try {
                    userPst.close();
                    userPst = null;
                } catch (SQLException ex) {
                }
                if (pcPst != null) try {
                    pcPst.close();
                    pcPst = null;
                } catch (SQLException ex) {
                }
                if (con != null) try {
                    con.close();
                    con = null;
                } catch (SQLException ex) {
                }
            }
        }
    }
}
