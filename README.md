# 2022 Java Spring Cafe

2022년도 마스터즈 멤버스 백엔드 스프링 카페 프로젝트

<br/>

## 스프링 카페 3단계 - DB에 저장하기

## Heroku 배포 URL : https://ikjo-spring-cafe.herokuapp.com

<br/>

## 프로그래밍 요구사항
### H2 데이터베이스 연동
- [X] H2 데이터베이스 의존성을 추가하고 연동한다.
- [X] ORM은 사용하지 않는다.
- [X] Spring JDBC를 사용한다.
- [X] DB 저장 및 조회에 필요한 SQL은 직접 작성한다.

### 게시글 데이터 저장하기
- [X] Article 클래스를 DB 테이블에 저장할 수 있게 구현한다.
- [X] Article 테이블이 적절한 PK를 가지도록 구현한다.

### 게시글 목록 구현하기
- [X] 전체 게시글 목록 데이터를 DB에서 조회하도록 구현한다.

### 게시글 상세보기 구현하기
- [X] 게시글의 세부 내용을 DB에서 가져오도록 구현한다.

### 사용자 정보 DB에 저장
- [X] 회원가입을 통해 등록한 사용자 정보를 DB에 저장한다.

### 배포
- [X] Heroku로 배포를 진행하고 README에 배포 URL을 기술한다.

<br/>

## 이벤트 흐름도
> ![image](https://user-images.githubusercontent.com/82401504/158037575-7b20e55f-adb7-4b23-b8e0-fdcb53bb0fdf.png)

<br/>

## 패키지 및 계층별 역할과 책임
## main/java/com/kakao/cafe
### config 패키지
> #### MvcConfig
> + WebMvConfigurer를 구현하는 클래스로서 일부 특별한 로직이 없는 요청의 경우 별도 컨트롤러에서 매핑 처리를 하지 않고 addViewControllers 메서드를 통해 매핑 처리하였습니다.
> + 아울러 addResourceHandlers 메서드를 구현함으로써 CSS, JS 파일 등의 클래스패스(classpath)와 캐시의 유효기간을 설정해주도록 했습니다.

### controller 패키지
> 각각의 Controller들은 다음과 같은 사용자 요청 관련 URL에 대해 어떤 처리(Service)를 해줄지 또한 어떤 View를 반환해줄지 결정합니다.
> #### UserController
> + `회원 가입, 회원 정보 수정, 회원 목록 조회, 회원 프로필 조회`
> #### ArticleController
> + `글 쓰기, 글 목록 조회, 게시글 상세 보기`
> #### MainController
> + `메인 화면 조회`

### service 패키지
> 각각의 Service들은 다음과 같은 비지니스 로직을 수행하는 메서드를 가지고 있습니다.
> #### UserService
> + `회원 가입(join), 회원 정보 수정(update), 회원 목록 조회(findAll), 회원 프로필 조회(findOne)`
> #### ArticleService
> + `글 쓰기(upload), 글 목록 조회(findAll), 게시글 상세 보기(findOne)`

### repository 패키지
> 각각의 Reopository들은 다음과 같이 데이터 저장소에 접근할 수 있는 메서드를 가지고 있습니다.
> #### MemoryArticleRepository
> + 메모리(ArrayList) 활용 `사용자 정보 저장(save), 특정 사용자 정보 조회(findByUserId), 사용자 정보 목록 조회(findAll)`
> #### MemoryArticleRepository
> + 메모리(ArrayList) 활용 `글 저장(save), 특정 글 조회(findById), 글 목록 조회(findAll)`
> #### JdbcArticleRepository
> + 데이터 베이스(h2) 활용 `사용자 정보 저장(save), 특정 사용자 정보 조회(findByUserId), 사용자 정보 목록 조회(findAll)`
> #### JdbcArticleRepository
> + 데이터 베이스(h2) 활용 `글 저장(save), 특정 글 조회(findById), 글 목록 조회(findAll)`
