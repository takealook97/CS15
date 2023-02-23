import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {
    public String getDateTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);
        return str;
    }

    public String findStartTime() throws SQLException {
        Statement stmt = PCRoom.con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from time");
        while (rs.next()) {
            if (Counter.userNumber == rs.getInt("user_number")) break;
        }
        return rs.getString("start_time");
    }

    public void addStartTime() throws SQLException {
        String timeSQL = "INSERT INTO time(pc_number, user_number, start_time, end_time) VALUES (?, ?, ?, ?)";
        PreparedStatement timePst = PCRoom.con.prepareStatement(timeSQL);
        timePst.setInt(1, Counter.pcNumber);
        timePst.setInt(2, Counter.userNumber);
        timePst.setString(3, new DateTime().getDateTime());
        timePst.setString(4, null);
        timePst.executeUpdate();
        PCRoom.con.commit();
    }

    public void addEndTime() throws SQLException {
        //종료시간 추가
        DateTime dateTime = new DateTime();
        String timeSQL = "INSERT INTO time(pc_number, user_number, start_time, end_time) VALUES (?, ?, ?, ?)";
        PreparedStatement timePst = PCRoom.con.prepareStatement(timeSQL);
        timePst.setInt(1, Counter.pcNumber);
        timePst.setInt(2, Counter.userNumber);
        timePst.setString(3, dateTime.findStartTime());
        timePst.setString(4, dateTime.getDateTime());
        timePst.executeUpdate();
        PCRoom.con.commit();
    }
}
