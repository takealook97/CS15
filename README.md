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
## 테이블 설계

- [ ] 사용자들을 키값 = 인덱스 (null 불가)
- [ ] PC 자리번호 (null 불가)
- [ ] PC 시작시간 (null 불가)
- [ ] PC 종료시간 (null 가능)


## Mission
- user
- pc_number
- start_time & end_time

### 1. db 생성 및 지정
![image](https://user-images.githubusercontent.com/118447769/220831937-089e615c-085c-4ce0-b496-b7db3fa2367a.png)  

### 2. 테이블 생성
- user 테이블, pc 테이블, time 테이블 3가지 생성
- user, pc_number 는 null 값 지정이 불가능하기에 PRIMARY KEY 제한조건을 걸어주었다.
- time 테이블 중 시작 시간은 null 값 지정이 불가능하기에 PRIMARY KEY로 지정해주었고 종료시간은 null값이 가능하기에 지정하지 않았다.

![image](https://user-images.githubusercontent.com/118447769/220831990-c89b995f-52d4-4e20-81dd-ee41f564d2c5.png)  
![image](https://user-images.githubusercontent.com/118447769/220832012-b4b5f383-6119-4caf-903b-f37125928faa.png)  




