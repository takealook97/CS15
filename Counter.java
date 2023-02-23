import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Counter {
    static int pcNumber;
    static int userNumber;
    static ArrayList<Integer> emptySeats;
    static ArrayList<Integer> waitingUsers;

    public ArrayList getEmptySeats() throws SQLException {
        Statement stmt = PCRoom.con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from pc");
        emptySeats = new ArrayList<>();
        while (rs.next()) {
            emptySeats.add(rs.getInt("pc_number"));
        }
        Collections.sort(emptySeats);
        return emptySeats;
    }

    public void getWaitingUsers() throws SQLException {
        Statement stmt = PCRoom.con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from user");
        waitingUsers = new ArrayList<>();
        while (rs.next()) {
            waitingUsers.add(rs.getInt("user_number"));
        }
        Collections.sort(waitingUsers);
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


    public void stopPC(int user) throws SQLException {
        //유저와 pc 지정 및 list에 원상복귀
        Statement stmt = PCRoom.con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from time");
        userNumber = user;
        while (rs.next()) {
            if (userNumber == rs.getInt("user_number")) {
                pcNumber = rs.getInt("pc_number");
            }
        }
        emptySeats.add(pcNumber);
        waitingUsers.add(userNumber);

        //유저와 pc 테이블에 원상복귀
        String pcSQL = "INSERT INTO pc(id, pc_number) VALUES (?, ?)";
        String userSQL = "INSERT INTO user(id, user_number) VALUES (?, ?)";
        PreparedStatement userPst = PCRoom.con.prepareStatement(userSQL);
        PreparedStatement pcPst = PCRoom.con.prepareStatement(pcSQL);
        pcPst.setInt(1, pcNumber);
        pcPst.setInt(2, pcNumber);
        pcPst.executeUpdate();
        userPst.setInt(1, userNumber);
        userPst.setInt(2, userNumber);
        userPst.executeUpdate();
        PCRoom.con.commit();

        new DateTime().addEndTime();
    }


}
