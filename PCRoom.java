import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class PCRoom {
    static Connection con;

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/pc_room_db";
        String userName = "takealook";
        String password = "0205";
        PreparedStatement userPst = null;
        PreparedStatement pcPst = null;
        PreparedStatement timePst = null;
        String pcSQL = "INSERT INTO pc(id, pc_number) VALUES (?, ?)";
        String userSQL = "INSERT INTO user(id, user_number) VALUES (?, ?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, userName, password);
            con.setAutoCommit(false);
            userPst = con.prepareStatement(userSQL);
            pcPst = con.prepareStatement(pcSQL);

            for (int i = 1; i <= 16; i++) {
                pcPst.setInt(1, i);
                pcPst.setInt(2, i);
                pcPst.executeUpdate();
                userPst.setInt(1, i);
                userPst.setInt(2, i);
                userPst.executeUpdate();
                con.commit();
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            Output output = new Output();
            Counter counter = new Counter();
            output.printInit();
            output.printEmptySeats();

            while (true) {
                System.out.print("> ");
                String[] input = br.readLine().split(" ");
                switch (input[0]) {
                    case "new" -> {
                        output.printSeatAndUser();
                        counter.deleteField();
                        counter.addStartTime();
                        output.printEmptySeats();
                    }
                    case "stop" -> {
                        counter.stop(Integer.parseInt(input[1]));
                        output.printEmptyMessage(Counter.pcNumber);
                    }
                    case "close" -> new DBClean().cleanDB();

                    default -> output.printError();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            } finally {
                if (userPst != null) try {
                    userPst.close();
                } catch (SQLException ex) {
                }
                if (pcPst != null) try {
                    pcPst.close();
                } catch (SQLException ex) {
                }
                if (con != null) try {
                    con.close();
                } catch (SQLException ex) {
                }
            }
        }
    }
}
