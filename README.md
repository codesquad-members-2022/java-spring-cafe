# 2022 Java Spring Cafe

2022년도 마스터즈 멤버스 백엔드 스프링 카페 프로젝트

<br/>

## 스프링 카페 2단계 - 글 쓰기 기능 구현

<br/>

## 기능 요구사항
- [X] 사용자는 게시글을 작성할 수 있어야 한다.
- [X] 모든 사용자는 게시글 목록을 볼 수 있어야 한다.
- [X] 모든 사용자는 게시글 상세 내용을 볼 수 있어야 한다.
- [X] (선택) 사용자 정보를 수정할 수 있어야 한다.

<br/>

## 프로그래밍 요구사항
### 글쓰기
- [X] 게시글 페이지는 static/qna/form.html을 수정해서 사용한다.
- [X] static에 있는 html을 templates로 이동한다.
- [X] 게시글 기능 구현을 담당할 ArticleController를 추가하고 애노테이션 매핑한다.
- [X] 게시글 작성 요청(POST 요청)을 처리할 메소드를 추가하고 매핑한다.
- [X] 사용자가 전달한 값을 Article 클래스를 생성해 저장한다.
- [X] 게시글 목록을 관리하는 ArrayList를 생성한 후 앞에서 생성한 Article 인스턴스를 ArrayList에 저장한다.
- [X] 게시글 추가를 완료한 후 메인 페이지(“redirect:/”)로 이동한다.

### 글 목록 조회하기

- [X] 메인 페이지(요청 URL이 “/”)를 담당하는 Controller의 method에서 게시글 목록을 조회한다.
- [X] 조회한 게시글 목록을 Model에 저장한 후 View에 전달한다. 게시글 목록은 앞의 게시글 작성 단계에서 생성한 ArrayList를 그대로 전달한다.
- [X] View에서 Model을 통해 전달한 게시글 목록을 출력한다.

### 게시글 상세보기

- [X] 게시글 목록(qna/list.html)의 제목을 클릭했을 때 게시글 상세 페이지에 접속할 수 있도록 한다.
  - [X] 게시글 상세 페이지 접근 URL은 "/articles/{index}"(예를 들어 첫번째 글은 /articles/1)와 같이 구현한다.
  - [X] 게시글 객체에 id 인스턴스 변수를 추가하고 ArrayList에 게시글 객체를 추가할 때 ArrayList.size() + 1을 게시글 객체의 id로 사용한다.
- [X] Controller에 상세 페이지 접근 method를 추가하고 URL은 /articles/{index}로 매핑한다.
- [X] ArrayList에서 index - 1 해당하는 데이터를 조회한 후 Model에 저장해 /qna/show.html에 전달한다.
- [X] /qna/show.html에서는 Controller에서 전달한 데이터를 활용해 html을 생성한다.

<br/>

## 추가 요구 사항 - (선택미션) 회원정보 수정
### 요구사항
- [X] 회원 목록에서 회원가입한 사용자의 정보를 수정할 수 있어야 한다.
- [X] 비밀번호, 이름, 이메일만 수정할 수 있으며, 사용자 아이디는 수정할 수 없다.
- [X] 비밀번호가 일치하는 경우에만 수정 가능하다.

### 회원정보 수정 화면
- [X] 회원가입한 사용자 정보를 수정할 수 있는 수정 화면과 사용자가 수정한 값을 업데이트할 수 있는 기능을 나누어 개발해야 한다.
- [X] /user/form.html 파일을 /user/updateForm.html로 복사한 후 수정화면을 생성한다.
- [X] URL 매핑을 할 때 "/users/{id}/form"와 같이 URL을 통해 인자를 전달하는 경우 @PathVariable 애노테이션을 활용해 인자 값을 얻을 수 있다.
- [X] public String updateForm(@PathVariable String id)와 같이 구현 가능하다.
- [X] Controller에서 전달한 값을 입력 폼에서 출력하려면 value를 사용하면 된다.

### 회원정보 수정

- [X] URL 매핑을 할 때 "/users/{id}"와 같이 URL을 통해 인자를 전달하는 경우 @PathVariable 애노테이션을 활용해 인자 값을 얻을 수 있다.
- [X] UserController의 사용자가 수정한 정보를 User 클래스에 저장한다.
- [X] {id}에 해당하는 User를 DB에서 조회한다(UserRepository의 findOne()).
- [X] DB에서 조회한 User 데이터를 새로 입력받은 데이터로 업데이트한다.
- [X] UserRepository의 save() 메소드를 사용해 업데이트한다.

### PUT method 사용
- [X] 수정 화면(updateForm.html)에서 수정된 값을 전송할 때 PUT값을 다음과 같이 hidden으로 전송한다.

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
> + `사용자 정보 저장(save), 특정 사용자 정보 조회(findByUserId), 사용자 정보 목록 조회(findAll)`
> #### MemoryArticleRepository
> + `글 저장(save), 특정 글 조회(findById), 글 목록 조회(findAll)`

### domain 패키지
> 각각의 domain들은 다음과 같은 데이터를 클라이언트로부터 입력받아오거나 반환하는 등 데이터를 전송하는 역할을 하며(3단계에서 DTO를 별도 추가하여 분리할 예정입니다.), 해당 데이터와 관련 비지니스 관련 로직을 일부 가지고 있습니다.
> #### UserInformation
> + `사용자 ID, 비밀번호, 이름, 이메일`
> #### UserArticle
> + `글 index, 작성자, 제목, 본문, 작성일자`

<br/>

## test/java/com/kakao/cafe
### combination 패키지
> 각각의 Controller 동작들에 대해 통합 테스트를 진행하도록 했습니다.

### controller 패키지
> 각각의 Controller에 대한 단위 테스트 코드들을 통해 `사용자 요청별로 지정된 handler method가 제대로 호출되는지`, `입력 파라미터가 handler method에 제대로 전달되는지`, `model에 설정한 
> 값이 제대로 참조되는지`, `요청에 대한 view가 제대로 반환되는지` 등을 검증하도록 했습니다.

### service 패키지
> 각각의 Service에 대한 단위 테스트 코드들을 통해 `회원 가입, 회원 조회, 글 쓰기` 등 관련 비지니스 로직 수행에 대한 기능을 검증하도록 했습니다.

### repository 패캐지
> 각각의 Repository에 대한 단위 테스트 코드들을 통해 `사용자 정보 및 글에 대한 정보를 저장하거나 조회`하는 등 데이터 저장소에 접근하는 메서드 기능을 검증하도록 했습니다.