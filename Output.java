import java.sql.SQLException;

public class Output {
    void printInit() {
        System.out.println("> 빈 자리는 다음과 같습니다.");
    }

    void printEmptySeats() throws SQLException {
        Counter counter = new Counter();
        System.out.println(counter.getEmptySeats() + "\n");
    }

    void printSeatAndUser() throws SQLException {
        Counter counter = new Counter();
        System.out.println(counter.getPcNumber() + "번 자리에 앉으세요 : #" + counter.getUserNumber());
    }

    void printEmptyMessage() {
        System.out.println("이제 " + Counter.pcNumber + "번 자리가 비었습니다.");
    }

    void printError() {
        System.out.println("올바르지 않은 형식입니다. 다시 입력하세요.");
    }

}
