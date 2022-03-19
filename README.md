# 2022 Java Spring Cafe

2022년도 마스터즈 멤버스 백엔드 스프링 카페 프로젝트

<br/>

## 스프링 카페 4단계 - 로그인 구현

<br/>

## 기능 요구사항
- [X] 로그인과 로그아웃이 기능이 정상적으로 동작한다.
- [X] 현재 상태가 로그인 상태이면 상단 메뉴에서 “로그아웃”, “개인정보수정”이 표시되어야 한다.
- [X] 현재 상테가 로그인 상태가 아니라면 상단 메뉴에서 “로그인”, “회원가입”이 표시되어야 한다.

<br/>

## 프로그래밍 요구사항
- [X] Spring MVC에서 메소드의 인자로 HttpSession을 이용해서 로그인을 구현한다.
- [X] Spring Security와 같은 별도 라이브러리 등은 사용하지 않는다.
- [X] API가 아닌 템플릿 기반으로 구현한다.

<br/>

## 추가 요구 사항
### (선택) 개인정보 수정 기능 추가
- [X] 로그인한 사용자는 자신의 정보를 수정할 수 있어야 한다.
- [X] 이름, 이메일만 수정할 수 있으며, 사용자 아이디는 수정할 수 없다.
- [X] 비밀번호가 일치하는 경우에만 수정 가능하다.
- [X] HttpSession에 저장된 User 데이터를 가져온다.
- [X] 로그인한 사용자와 수정하는 계정의 id가 같은 경우만 수정하도록 한다.
- [X] 다른 사용자의 정보를 수정하려는 경우 에러 페이지를 만든 후 에러 메시지를 출력한다.

<br/>

## 이벤트 흐름도
> ![image](https://user-images.githubusercontent.com/82401504/158037575-7b20e55f-adb7-4b23-b8e0-fdcb53bb0fdf.png)

<br/>

## 패키지 및 계층별 역할과 책임
## main/java/com/kakao/cafe
> ![image](https://user-images.githubusercontent.com/82401504/158310975-da3e6fb7-9ec2-4239-bc05-2e9ece8dbb85.png)

### config 패키지
> #### MvcConfig
> + WebMvConfigurer를 구현하는 클래스로서 일부 특별한 로직이 없는 요청의 경우 별도 컨트롤러에서 매핑 처리를 하지 않고 addViewControllers 메서드를 통해 매핑 처리하였습니다.
> + 아울러 addResourceHandlers 메서드를 구현함으로써 CSS, JS 파일 등의 클래스패스(classpath)와 캐시의 유효기간을 설정해주도록 했습니다.

### controller 패키지
> 각각의 Controller들은 다음과 같은 사용자 요청 관련 URL에 대해 어떤 처리(Service 등)를 해줄지 또한 어떤 View를 반환해줄지 결정합니다.
> #### UserController
> + `회원 가입, 회원 정보 수정, 회원 목록 조회, 회원 프로필 조회`
> #### ArticleController
> + `글 쓰기, 글 목록 조회, 게시글 상세 보기`
> #### MainController
> + `메인 화면 조회`
> #### ExceptionController
> + `사용자 정의 예외 핸들러`

### service 패키지
> 각각의 Service들은 다음과 같은 비지니스 로직을 수행하는 메서드를 가지고 있습니다.
> #### UserService
> + `회원 가입(join), 회원 정보 수정(update), 회원 정보 검증(validateUser), 회원 세션 검증(validateSessionOfUser), 회원 목록 조회(findAll), 회원 프로필 조회(findOne)`
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

### dto 패키지
> RequestDto는 사용자로부터 입력받은 데이터를 전송하는 역할을 하며, Domain 객체를 반환합니다.
> ResponseDto는 Domain 객체로부터 반환된 객체로, DB 테이블로부터 매핑된 데이터 일부를 사용자에게 응답 전송하는 역할을 합니다.

### domain 패키지
> 실제 DB 테이블 필드와 매핑되는 속성을 가지고 있으며, 데이터 처리 관련 비지니스 로직을 지니고 있으며 ResponseDto 객체를 반환합니다.

### exception 패키지
> 로그인 실패, 사용자의 잘못된 접근(다른 사용자 정보 수정 등) 시 발생하는 별도 예외를 선언하였고 이를 `ExceptionController`에서 처리하도록 했습니다.
