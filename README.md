
1.1.회원 API 구성 및 실행
====================
개발 환경
------
|제목|기술
|------|---|
|백엔드|JAVA 8|
|db|h2 database|
|프레임워크|스프링부트(스프링 시큐리티, 스프링 데이타 JPA) 활용|
|빌드 툴|gradle|
|개발OS|macOS|


1.2.프로젝트 구성
----------
- 서비스는 도메인 별로 나누었으며 도메인은 서비스당 하나의 repository만 갖고있음.
- 도메인의 핵심인 entity는 db본연의 값을 지니고 있기에 setter 메소드를 갖고 있지 않고, 사용 시에는 dto를 생성함.
- 계층간 교환은 dto로 교환함.
- exception hanler를 두어 return null;을 지양하고 예외를 상세히 구성함.
- 로그인은 rest api가 지향하는 방향을 따르기위해 세션방식이 아닌 jwt토큰방식을 사용함.

1.3.도메인 구성
--------
- 도메인은 고객, 문자전송내역로 구성하고 컨트롤러에서 dto 데이터를 서로 호출하도록 함.

1.4.실행방법
-------
```
   $ git https://github.com/wonsungyoun/user.git
   $ cd user
   $ ./gradlew clean build
   $ java -jar build/libs/user-0.0.1-SNAPSHOT.jar
```

2.API 사용 방법
=============
2.1.회원가입
----------
2.1.1.INPUT

