public class Output {
    void printInit() {
        System.out.println("> 빈 자리는 다음과 같습니다.");
        printEmptySeats();
    }

    void printEmptySeats() {
        System.out.println();//pc_number list 출력
    }

    void printUserKey(int user) {
        System.out.println("#");
    }

    void printOrder(int seat, int user) {
        System.out.print(seat + "번 자리에 앉으세요 : ");
        printUserKey(user);
    }

    void printEmptyMessage(int user) {
        //user가 앉아있던 pc_number
        System.out.println("이제 " + "번 자리가 비었습니다.");
        printEmptySeats();
    }

    void printError() {
        System.out.println("올바르지 않은 형식입니다. 다시 입력하세요.");
    }

}
