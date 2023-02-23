import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBClean {
    public void cleanDB() throws SQLException {
        String cleanPc = "truncate pc";
        String cleanUser = "truncate user";
        String cleanTime = "truncate time";
        PreparedStatement st1 = PCRoom.con.prepareStatement(cleanPc);
        PreparedStatement st2 = PCRoom.con.prepareStatement(cleanUser);
        PreparedStatement st3 = PCRoom.con.prepareStatement(cleanTime);
        st1.executeUpdate();
        st2.executeUpdate();
        st3.executeUpdate();
        PCRoom.con.commit();
        System.out.println("The tables are all cleaned." + "\n");
        System.out.println("---System closed---");
        System.exit(0);
    }
}