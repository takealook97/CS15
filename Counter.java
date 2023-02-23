import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Counter {
    int pcNumber;
    int userNumber;
    static ArrayList<Integer> emptySeats;
    static ArrayList<Integer> waitingUsers;

    public ArrayList getEmptySeats() throws SQLException {
        Statement stmt = PCRoom.con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from pc");
        emptySeats = new ArrayList<>();
        while (rs.next()) {
            emptySeats.add(rs.getInt("pc_number"));
        }
        return emptySeats;
    }

    public void getWaitingUsers() throws SQLException {
        Statement stmt = PCRoom.con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from user");
        waitingUsers = new ArrayList<>();
        while (rs.next()) {
            waitingUsers.add(rs.getInt("user_number"));
        }
    }

    public int getPcNumber() {
        Random rd = new Random();
        int randomIndex = rd.nextInt(emptySeats.size());
        pcNumber = emptySeats.get(randomIndex);
        return pcNumber;
    }

    public int getUserNumber() throws SQLException {
        getWaitingUsers();
        Random rd = new Random();
        int randomIndex = rd.nextInt(waitingUsers.size());
        userNumber = waitingUsers.get(randomIndex);
        return userNumber;
    }

    public void deleteField() throws SQLException {
        //자리,유저,시작시간 추가 (종료시간 = null)
        String pcSQL = "DELETE FROM pc WHERE id = ?";
        PreparedStatement pcPst = PCRoom.con.prepareStatement(pcSQL);
        pcPst.setInt(1, pcNumber);
        pcPst.executeUpdate();

        String userSQL = "DELETE FROM user WHERE id = ?";
        PreparedStatement userPst = PCRoom.con.prepareStatement(userSQL);
        userPst.setInt(1, userNumber);
        userPst.executeUpdate();

        PCRoom.con.commit();
    }

    public void addStartTime() throws SQLException, ParseException {
        String timeSQL = "INSERT INTO time(pc_number, user_number, start_time, end_time) VALUES (?, ?, ?, ?)";
        PreparedStatement timePst = PCRoom.con.prepareStatement(timeSQL);
        timePst.setInt(1, pcNumber);
        timePst.setInt(2, userNumber);
        timePst.setString(3, dateTime());
        timePst.setString(4, null);
        timePst.executeUpdate();
        PCRoom.con.commit();
    }

    public void stop(int user) {
        //종료시각 추가
    }

    public String dateTime() throws ParseException {
        Date date = new Date();
        String date_time = date.toString();
        SimpleDateFormat dateParser = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");
        return dateParser.parse(date_time).toString();
    }
}
