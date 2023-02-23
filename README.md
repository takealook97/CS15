🎯 CS15. PC방 관리자
=

## 제약조건 (constraint)
참고 : http://www.tcpschool.com/mysql/mysql_constraint_notNull
- 제약 조건이란 데이터의 무결성을 지키기 위해, 데이터를 입력받을 때 실행되는 검사 규칙을 의미한다.
- 제약조건은 CREATE 문으로 테이블 생성할 때나, ALTER 문으로 필드를 추가할 때도 설정할 수 있다.

### NOT NULL
- 해당 필드는 NULL 값 저장이 불가능하다. (무조건 데이터 가지고 있어야 함)
- 해당 필드에 NULL 값을 저장할 수 없도록 하는 것이지 해당 필드를 생략하지 못하도록 하는 제약조건은 아니다.
  - INSERT 문으로 레코드 저장할 때 NOT NULL 제약 조건이 설정된 필드의 값을 생략할 수도 있음

### UNIQUE
- 필드의 타입 뒤에 UNIQUE를 명시하면, 해당 필드에는 더는 중복된 값을 저장할 수 없다.

### PRIMARY KEY (= 기본키)
- NOT NULL과 UNIQUE 제약 조건의 특징을 모두 가진다.
- 필드는 NULL 값을 가질 수 없으며, 중복된 값을 가져서도 안된다.
- UNIQUE는 한 테이블의 여러 필드에 설정할 수 있지만, PRIMARY KEY는 테이블당 오직 하나의 필드에만 설정 가능하다.
- ∴ PRIMARY KEY 제약 조건은 테이블의 데이터를 쉽고 빠르게 찾도록 도와주는 역할을 한다.

### FOREIGN KEY (= 외래키)
- 한 테이블을 다른 테이블과 연결해주는 역할을 한다.
- 외래 키가 설정된 테이블에 레코드를 입력하면, 기준이 되는 테이블의 내용을 참조해서 레코드가 입력된다.
  - FOREIGN KEY 제약 조건은 하나의 테이블을 다른 테이블에 의존하게 만든다.
- FOREIGN KEY 제약 조건을 설정할 때 참조되는 테이블의 필드는 반드시 UNIQUE나 PRIMARY KEY 제약 조건이 설정되어 있어야 한다.

### DEFAULT
- 해당 필드의 타입 뒤에 DEFAULT를 명시하면, 해당 필드의 기본값을 설정할 수 있다.


---

# Mission
- [x] 사용자들을 키값 = 인덱스 (null 불가)
- [x] PC 자리번호 (null 불가)
- [x] PC 시작시간 (null 불가)
- [x] PC 종료시간 (null 가능)

## db와 테이블

### 1. db 생성 및 지정
![image](https://user-images.githubusercontent.com/118447769/220831937-089e615c-085c-4ce0-b496-b7db3fa2367a.png)  

### 2. 테이블 생성
- user 테이블, pc 테이블, time 테이블 3가지 생성
- user_number, pc_number, start_time은 null 값 지정이 불가능하기에 제한조건을 걸어주었다.
- time 테이블에는 id와 더불어 pc_number, user_number, start_time, end_time 을 넣어주었다.
  - time table은 시작시간만 있는 데이터와, 시작시간과 끝낸시간 모두가 있는 데이터가 계속해서 스택 형식으로 쌓인다.
  - 데이터의 수정이나 제거가 이루어지지 않고 단순히 쌓이는 구조이기에 id를 auto_increment로 두어 관리하기 편하게 했다.
  - 참고 : https://cogito87.tistory.com/73

![image](https://user-images.githubusercontent.com/118447769/221023632-d9f35564-71a0-42e0-9498-f748766f899b.png)  
![image](https://user-images.githubusercontent.com/118447769/221028880-80a189ac-0476-4062-a18b-ac2e7cfa7062.png)  
![image](https://user-images.githubusercontent.com/118447769/221028453-8a6936c0-f1a8-450b-ae8e-c7f1b930f97c.png)  
---

## 구조
### PCRoom 클래스 (main)
- 서버 연결
- user와 pc 테이블에 각각 1~16의 id를 가진 고유한 값들을 넣어주고 시작
- 입력을 받고 로직을 명령

### Counter 클래스
- 피시방의 카운터를 의미
- 전체적인 자료구조는 테이블을 배열로 받는 형식
- new 입력 시 랜덤하게 뽑히는 user와 pc를 전역변수로 지정
- 마찬가지로 stop 입력시 해당하는 user와 pc를 전역변수로 지정
- 메인에서 받은 주요 로직들을 수행

### DateTime 클래스
- 전반적인 시간의 처리
- 시작시간, 끝시간을 다루는 클래스
- 시간은 파싱을 썼다가 SimpleDateFormat으로 수정하였음

### DBClean 클래스
- 매번 run을 하고 프로그램을 종료하면 데이터가 테이블에 쌓여있음
- truncate를 테이블마다 지워주기 귀찮아서 만든 클래스
- close를 입력하면 테이블 내의 데이터를 비우고 프로그램을 종료

### Output 클래스
- 출력에 필요한 여러 메소드 배치

---
### 빈 자리 list 메서드

```java
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
```
- Statement 타입 선언 후 pc를 기준으로 result set 을 가져온다.
- rs의 포인터를 기준으로 반복문을 돌며 list에 pc_number column을 넣어준다 (= pc 번호 = pc 이름)
- list를 리턴한다.
- 참고 : https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=50after&logNo=220917018940

### 시간 변환(SimpleDateFormat)
참고 : https://readystory.tistory.com/55

### auto-increment
참고 : https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=goddlaek&logNo=221005664911

---
## 구현 이미지

### 초기 지정 테이블
![image](https://user-images.githubusercontent.com/118447769/221029775-4dfaf246-bea0-4341-ae22-eb90bdeba68a.png)
![image](https://user-images.githubusercontent.com/118447769/221029657-6024c50c-0c48-4a01-8bcf-f69ccf10a7fb.png)

### 구동
![image](https://user-images.githubusercontent.com/118447769/221029995-13f517df-6eeb-4fb0-9f53-bcda25157792.png)  

### time 테이블 데이터 누적
![image](https://user-images.githubusercontent.com/118447769/221030054-705e8064-f464-48ee-837a-a8a062f394d7.png)  