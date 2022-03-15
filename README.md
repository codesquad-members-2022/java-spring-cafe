# 2022 Java Spring Cafe

2022년도 마스터즈 멤버스 백엔드 스프링 카페 프로젝트

<details>
<summary>스프링 카페 1단계 - 회원 가입 및 목록 기능</summary>

## API 요구사항

| HTTP Method | URL             | Description |
|-------------|-----------------|-------------|
| POST        | /users          | 회원 가입       |
| GET         | /users          | 회원 목록 조회    |
| GET         | /users/{userId} | 회원 프로필 조회   |

## 상세 기능 요구사항

- [x] 웹페이지 디자인
- [x] 별도의 데이터베이스는 사용하지 않는다.
- [x] 회원 가입 기능 구현
- [x] 회원 목록 조회 기능 구현
- [x] 회원 프로필 조회 기능 구현

## 프로그래밍 요구사항

- [x] 각 기능에 따른 url과 메소드 convention
- [x] 회원가입 기능 구현
- [x] 회원가입 기능 구현
- [x] 회원 프로필 정보보기
- [x] HTML의 중복 제거

</details>

<details>
<summary>스프링 카페 2단계 - 글 쓰기 기능 구현</summary>

## 신경쓴 부분

1. 각 계층의 역할
   `controller` : 웹 계층 처리, domain -> dto 변환
   `service` : 요청 dto -> domain, 비즈니스적 검증사항 검증
   `repository` : 객체를 저장하고 관리
   `domain` : 도메인 데이터 저장, 필수값 검증, 도메인 로직

2. 최대한 요구사항에 맞게 개발
   `User` 클래스는 초기에 DB를 고려하며 개발해버려서, 부득이 `Long id` 로 만들었습니다.
   하지만 `Article` 의 경우 요구사항에서 `int` 형을 반환하는 `ArrayList.size` 를 id 로 사용하라고 하여
   `Article` 의 `id` 는 `Integer` 으로 선언하였습니다.

3. 특정 `도메인`과 관련된 클래스들만 모아서 `패키징`
   클래스를 찾을 때, 각 `계층별`로 패키징된 구조보다 `도메인 관련성`을 기준으로 `패키징`한 구조가 클래스를 찾기에 더 좋았습니다.
   이 부분은 주관적인 것 같긴 합니다..

4. 각 계층별로 맞춤형 테스트 진행
- `Controller` : 행위 검증에 집중
    - Service 를 Mocking 하여 `단위 테스트 진행`
      Service 를 Mocking 하지 않으면 **Repository 의존성**도 생기고,
      Repository에 상태를 미리 준비하는 작업에 대한 코드가 많아질 것 같아 Service 를 Mocking 하였습니다.
- `Service` : 행위 및 상태 검증
    - `통합 테스트` 진행
      `단위 테스트`를 하려면 Repository 를 Mocking 해야하는데 MemoryRepository 를 사용중이라서 `오버헤드`가 적고,
      Mocking 하면서 하면 너무 미션 진행시간이 길어질 것 같아 `통합테스트`로 진행하였습니다
- `Repository` : 상태 검증에 집중
    - `단위 테스트` 진행
- `Domain` : 행위 및 상태 검증
    - `단위 테스트` 진행
- `Util` : 상태 검증
    -  `단위 테스트` 진행
    - 비즈니스와 관련없는 로직의 테스트는 어떤 기능에 대한 테스트인지 알아보기 쉬운 설명을 작성하였습니다.

5. Dto 네이밍
   의미없는 접미어 `Dto` 를 붙이는 것을 지양하고, 이름 그 자체로 의미를 가지도록 하였습니다.

## API 요구사항

| HTTP Method | URL                | Description   |
|-------------|--------------------|---------------|
| POST        | /questions         | 글 등록          |
| GET         | /                  | 글 목록 조회       |
| GET         | /articles/{userId} | 글 상세보기 |

## 글 등록 요구사항

- [x] 게시글 기능 구현을 담당할 ArticleController를 추가하고 애노테이션 매핑한다.
- [x] 게시글 작성 요청(POST 요청)을 처리할 메소드를 추가하고 매핑한다.
- [x] 사용자가 전달한 값을 Article 클래스를 생성해 저장한다.
- [x] 게시글 목록을 관리하는 ArrayList를 생성한 후 앞에서 생성한 Article 인스턴스를 ArrayList에 저장한다.
- [x] 게시글 추가를 완료한 후 메인 페이지(“redirect:/”)로 이동한다.

## 글 목록 조회 요구사항

- [x] 메인 페이지(요청 URL이 “/”)를 담당하는 Controller의 method에서 게시글 목록을 조회한다.
- [x] 조회한 게시글 목록을 Model에 저장한 후 View에 전달한다. 게시글 목록은 앞의 게시글 작성 단계에서 생성한 ArrayList를 그대로 전달한다.
- [x] View에서 Model을 통해 전달한 게시글 목록을 출력한다.

## 글 상세보기 요구사항

- [x] 게시글 목록(qna/list.html)의 제목을 클릭했을 때 게시글 상세 페이지에 접속할 수 있도록 한다.
    - 게시글 상세 페이지 접근 URL은 "/articles/{index}"(예를 들어 첫번째 글은 /articles/1)와 같이 구현한다.
    - 게시글 객체에 id 인스턴스 변수를 추가하고 ArrayList에 게시글 객체를 추가할 때 ArrayList.size() + 1을 게시글 객체의 id로 사용한다.
- [x] Controller에 상세 페이지 접근 method를 추가하고 URL은 /articles/{index}로 매핑한다.
- [x] ArrayList에서 index - 1 해당하는 데이터를 조회한 후 Model에 저장해 /qna/show.html에 전달한다.
- [x] /qna/show.html에서는 Controller에서 전달한 데이터를 활용해 html을 생성한다.

<details>
<summary>테스트 코드 실행 결과</summary>
<img src="https://user-images.githubusercontent.com/45728407/158217866-492db94e-a4ec-45c2-9cb1-8f4a6601fc5d.png" alt=""/><br>
<img src="https://user-images.githubusercontent.com/45728407/158200503-88fd3e0c-069f-4978-83e3-c1b6492b9af0.png" alt=""/><br>
<img src="https://user-images.githubusercontent.com/45728407/158191343-ee6fd00c-5a13-447b-89d2-600ad80737e9.png" alt=""/><br>
<img src="https://user-images.githubusercontent.com/45728407/158191618-0a2c8774-9479-4daa-938a-91d0e4c1adf8.png" alt=""/><br>
<img src="https://user-images.githubusercontent.com/45728407/158191690-61e455cc-d5aa-480f-b4b1-5169b18534a4.png" alt=""/><br>
</details>
</details>

<details>
<summary>스프링 카페 3단계 - DB에 저장하기</summary>

프로젝트 URL : https://java-spring-cafe-jinan159.herokuapp.com/

## 신경쓴 부분

## 1. 테스트 코드를 작성하기 쉬운 형태로

기존에 UserRepository 는 save 에서 `id` 를 반환하고, ArticleRepository 는 `Article` 을 반환했습니다.
두 가지로 개발해보며 확실히 저장 후 `도메인 객체`를 반환하는것이, 로직의 흐름이나 테스트코드 작성에도 유리하다고 느꼈습니다.
그래서 UserRepository 도 `id` 가 아닌, `User` 를 반환하도록 개선하였습니다.


### 2. 테스트에 대한 공부의 중요성<br>

2단계에서 테스트에 엄청나게 공을 들였었는데, 그 효과가 이번 3단계에서 바로 나타났습니다.
이전 단계에서 작성했던 테스트 코드에는 미션의 요구사항과 제가 생각했던 제약조건들이 녹아들어있습니다.
이를 MemoryRepository 에서 JDBCRepository 로 이전을 하면서 테스트 코드를 통해 이전 모듈과의 호환성을 검증할 수 있어서
개발 생산성이 엄청나게 올라간다는 것을 느꼈습니다.
그래서 이번 단계를 통해 더 효율적이고 정확한 테스트를 작성할 수 있도록 공부해야겠다는 동기부여를 얻게 되었습니다.

### 3. 배포시 프로파일 분리 및 환경변수 활용

로컬에서는 돌리는 DB는 프로젝트 설정파일(application.yml)에 접속정보가 있어도 크게 상관이 없지만,
배포될 서버의 접속 정보를 Git 저장소에 올리는 절대 안됩니다.
방법을 강구하던 중 application.yml 에서 시스템 환경변수를 활용할 수 있다는 것을 알게되어 적용하였습니다.

### 4. 배포 브랜치를 별도로 관리

기존에 사용하는 `step1`, `step2`, `step3` 등등은 개발용으로 사용하고 PR merge 후 삭제하기 때문에 배포용 브랜치로 사용할 수 없습니다.
그리고 `jinan159` 라는 제 프로젝트의 master 브랜치는 개발 소스를 보관하는 역할만 하는것이 맞다고 생각했습니다.
게다가 배포를 위해서는 application.yml 의 설정도 일부 변경해야 하기에, 따로 브랜치를 만들어서 관리하기로 했습니다.

저는 그래서`step3` 에서 배포에 필요한 사전준비를 모두 완료한 후, `deploy` 브랜치를 따로 생성하여 이 브랜치를 `heroku` 와 연결하였습니다.
결과적으로 `jinan159` 브랜치의 application.yml 에는 `개발용 프로파일`이 실행되도록 설정하고,
`deploy` 브랜치의 application.yml 에는 `배포용 프로파일`이 실행되도록 설정하였습니다.

## 프로그래밍 요구사항

### H2 데이터베이스 연동
- [x] H2 데이터베이스 의존성을 추가하고 연동한다.
- [x] ORM은 사용하지 않는다.
- [x] Spring JDBC를 사용한다.
- [x] DB 저장 및 조회에 필요한 SQL은 직접 작성한다.

### 게시글 데이터 저장하기
- [x] Article 클래스를 DB 테이블에 저장할 수 있게 구현한다.
- [x] Article 테이블이 적절한 PK를 가지도록 구현한다.

### 게시글 목록 구현하기
- [x] 전체 게시글 목록 데이터를 DB에서 조회하도록 구현한다.

### 게시글 상세보기 구현하기
- [x] 게시글의 세부 내용을 DB에서 가져오도록 구현한다.

### 사용자 정보 DB에 저장
- [x] 회원가입을 통해 등록한 사용자 정보를 DB에 저장한다.

### 배포
- [x] Heroku로 배포를 진행하고 README에 배포 URL을 기술한다.

<details>
<summary>테스트 코드 실행 결과</summary>
<img src="https://user-images.githubusercontent.com/45728407/158328464-84f9c3c5-be07-4eaf-bf72-ba315e6f252f.png" alt=""/><br>
<img src="https://user-images.githubusercontent.com/45728407/158328234-2c23e81d-52c9-4120-b3c1-ce0656705bd3.png" alt=""/><br>
<img src="https://user-images.githubusercontent.com/45728407/158328319-60575cda-e5d1-4753-b489-45b14d0fc213.png" alt=""/><br>
<img src="https://user-images.githubusercontent.com/45728407/158328374-89c8ee0d-78e6-4de8-85cb-e7206328fb5a.png" alt=""/><br>
<img src="https://user-images.githubusercontent.com/45728407/158328441-66234ab2-0fb8-4f25-80f8-6c954d1b5dd5.png" alt=""/><br>
</details>

</details>

[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2Fjinan159%2Fjava-spring-cafe%2Ftree%2Fstep2&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)