
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
### 2.1.1.INPUT
```
POST http://localhost:8080/api/customer/register
Content-Type: application/json

{
  "email": 이메일,
  "password": 비밀번호,
  "name": 이름,
  "nickName": 별명,
  "phoneNumber" : 전화번호
}
```
- 이메일은 아이디@도메인 형식으로 작성하지 않을 시 예외가 던져지게 된다.
- 전화번호는 010-XXXX-XXXX 양식으로 작성하지 않으면 예외가 던져지게 된다.
- 모든값들은 필수적으로 적어야 하며 작성하지 않을 시 예외가 던져지게 된다.

### 2.1.1.OUTPUT
#### 2.1.1.1. SUCCESS SAMPLE
```
{
  "email": "sywon@subutai.com",
  "name": "원성연,",
  "nickName": "수부타이",
  "phoneNumber": "010-1111-2222",
  "regDate": "2022-10-03T07:17:05.571+00:00",
  "lastLoginDate": null,
  "loginYn": "N"
}
```
- 성공적으로 가입되었을시 관련 정보들이 반환된다.

#### 2.1.1.2. FAIL SAMPLE

```
{
  "code": 400,
  "message": 원인 메시지,
  "data": null
}
```
- 실패 시 원인 메시지가 표시 된다. 

2.2.로그인
----------
### 2.2.1.INPUT
```
POST http://localhost:8080/api/customer/login
Content-Type: application/json

{
  "email":이메일,
  "password": 비밀번호
}
```
- 이메일은 아이디@도메인 형식으로 작성하지 않을 시 fail메시지가 반환 된다.
- 모든값들은 필수적으로 적어야 하며 작성하지 않을 시 fail메시지가 반환 된다.

### 2.2.1.OUTPUT
#### 2.2.1.1. SUCCESS SAMPLE
```
{
  "grantType": "Bearer",
  "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzeXdvbkBzdWJ1dGFpLmNvbSIsImF1dGgiOiJST0xFX1VTRVIiLCJleHAiOjE2NjQ3ODU1OTd9.6Yx0k1e1fLVAN7B5WVrwYdfsa2HoCFojnfl8Ykcm3IA",
  "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NjU5OTE1OTd9.zhoJ42dpThDUXlOiHzOk2jenY9bLp1GGlEDWDNduONg"
}
```
- 성공적으로 로그인 되었을시 accessToekn, refreshToken이 던져지게 된다.
- 토큰은 jwt토큰으로 현재 각각 1시간, 2주로 지정 되어있다.

##### 2.2.1.2. FAIL SAMPLE

```
{
  "code": 400,
  "message": 원인 메시지,
  "data": null
}
```
- 실패 시 원인 메시지가 표시 된다. 

2.3.내 정보 확인
----------
### 2.3.1.INPUT
```
POST http://localhost:8080/api/customer/login
Content-Type: application/json

{
  "email":이메일,
  "password": 비밀번호
}
```
- 이메일은 아이디@도메인 형식으로 작성하지 않을 시 fail메시지가 반환 된다.
- 모든값들은 필수적으로 적어야 하며 작성하지 않을 시 fail메시지가 반환 된다.

### 2.3.1.OUTPUT
#### 2.3.1.1. SUCCESS SAMPLE
```
{
  "grantType": "Bearer",
  "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzeXdvbkBzdWJ1dGFpLmNvbSIsImF1dGgiOiJST0xFX1VTRVIiLCJleHAiOjE2NjQ3ODU1OTd9.6Yx0k1e1fLVAN7B5WVrwYdfsa2HoCFojnfl8Ykcm3IA",
  "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NjU5OTE1OTd9.zhoJ42dpThDUXlOiHzOk2jenY9bLp1GGlEDWDNduONg"
}
```
- 성공적으로 로그인 되었을시 accessToekn, refreshToken이 던져지게 된다.
- 토큰은 jwt토큰으로 현재 각각 1시간, 2주로 지정 되어있다.

##### 2.3.1.2. FAIL SAMPLE

```
{
  "code": 400,
  "message": 원인 메시지,
  "data": null
}

##### 2.3.1.3.  403 SAMPLE

```
<Response body is empty>

```
- 토큰이 정확하지 않을 시 해당 ap
