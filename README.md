# 2022 Java Spring Cafe

2022년도 마스터즈 멤버스 백엔드 스프링 카페 프로젝트

# 스프링 카페 1단계 - 회원 가입 및 목록 기능
***

- # 기능요구사항

  - ## 회원 가입 기능 구현
    - [x] 가입하기 페이지에서 회원 가입 폼을 표시한다.
    - [x] 개인정보를 입력하고 확인을 누르면 회원 목록 조회 페이지로 이동한다.
    
  - ## 회원 목록 조회 기능 구현
    - [x] 목록 조회 페이지에서는 가입한 회원들의 목록을 출력한다.
    
  - ## 회원 프로필 조회 기능 구현
    - [x] 회원 프로필 페이지에서는 개별 회원의 프로필 정보를 출력한다.

- # 프로그래밍 요구사항

  - ## 각 기능에 따른 url과 메소드 convention
    - [x] 먼저 각 기능에 대한 대표 url을 결정한다.
      - 예를 들어 회원관리와 관련한 기능의 대표 URL은 "/users"와 같이 설계한다.
    - [x] 기능의 세부 기능에 대해 일반적으로 구현하는 기능(목록, 상세보기, 수정, 삭제)에 대해서는 URL convention을 결정한 후 사용할 것을 추천한다.
      - 예를 들어 todo 앱에 대한 URL convention을 다음과 같은 기준으로 잡을 수 있다.

- # 회원가입 기능 구현

  - [x] 가입하기 페이지는 static/user/form.html을 사용한다.
  - [x] static에 있는 html을 templates로 이동한다.
  - [x] 사용자 관리 기능 구현을 담당할 UserController를 추가하고 애노테이션 매핑한다.
    - @Controller 애노테이션 추가
  - [x] 회원가입하기 요청(POST 요청)을 처리할 메소드를 추가하고 매핑한다.
    - @PostMapping 추가하고 URL 매핑한다.
  - [x] 사용자가 전달한 값을 User 클래스를 생성해 저장한다.
    - 회원가입할 때 전달한 값을 저장할 수 있는 필드를 생성한 후 setter와 getter 메소드를 생성한다.
  - [x] 사용자 목록을 관리하는 ArrayList를 생성한 후 앞에서 생성한 User 인스턴스를 ArrayList에 저장한다.
  - [x] 사용자 추가를 완료한 후 사용자 목록 페이지("redirect:/users")로 이동한다.

- # 회원목록 기능 구현

  - [x] 회원목록 페이지는 static/user/list.html을 사용한다.
  - [x] static에 있는 html을 templates로 이동한다.
  - [x] Controller 클래스는 회원가입하기 과정에서 추가한 UserController를 그대로 사용한다.
  - [x] 회원목록 요청(GET 요청)을 처리할 메소드를 추가하고 매핑한다.
    - @GetMapping을 추가하고 URL 매핑한다.
  - [x] Model을 메소드의 인자로 받은 후 Model에 사용자 목록을 users라는 이름으로 전달한다.
  - [x] 사용자 목록을 user/list.html로 전달하기 위해 메소드 반환 값을 "user/list"로 한다.
  - [x] user/list.html 에서 사용자 목록을 출력한다.
  - [x] user/list.html 에서 사용자 목록 전체를 조회하는 방법은 다음과 같다.

- # 회원 프로필 정보보기

  - [x] 회원 프로필 보기 페이지는 static/user/profile.html을 사용한다.
  - [x] static에 있는 html을 templates로 이동한다.
  - [x] 앞 단계의 사용자 목록 html인 user/list.html 파일에 닉네임을 클릭하면 프로필 페이지로 이동하도록 한다.
    - html에서 페이지 이동은 <a /> 태그를 이용해 가능하다.
    - \<a href="/users/{{userId}}" />와 같이 구현한다.
  - [x] Controller 클래스는 앞 단계에서 사용한 UserController를 그대로 사용한다.
  - [x] 회원프로필 요청(GET 요청)을 처리할 메소드를 추가하고 매핑한다.
    - @GetMapping을 추가하고 URL 매핑한다.
    - URL은 "/users/{userId}"와 같이 매핑한다.
  - [x] URL을 통해 전달한 사용자 아이디 값은 @PathVariable 애노테이션을 활용해 전달 받을 수 있다.
  - [x] ArrayList에 저장되어 있는 사용자 중 사용자 아이디와 일치하는 User 데이터를 Model에 저장한다.
  - [x] user/profile.html 에서는 Controller에서 전달한 User 데이터를 활용해 사용자 정보를 출력한다.

- # HTML의 중복 제거
  - [x] index.html, /user/form.html, /qna/form.html 코드를 보면 header, navigation bar, footer 부분에 많은 중복 코드가 있다. 중복 코드를 제거한다.
  
# 스프링 카페 2단계 - 글 쓰기 기능 구현
***

- # 기능요구사항
  - [x] 사용자는 게시글을 작성할 수 있어야 한다.
    - ArticleController writeArticle(POST) 메서드 uri: "/articles/write"
  - [x] 모든 사용자는 게시글 목록을 볼 수 있어야 한다.
    - HomeController getArticles(GET) 메서드 uri: "/"
  - [x] 모든 사용자는 게시글 상세 내용을 볼 수 있어야 한다.
    - ArticleController getDetail(GET) 메서드 "articles/{id}"
  - [x] (선택) 사용자 정보를 수정할 수 있어야 한다.
    - UserController modifyProfile(PUT) 메서드 uri: "/{userId}/update"

- # 프로그래밍 요구사항

  - ## 글쓰기
    - [x] 게시글 페이지는 static/qna/form.html을 수정해서 사용한다.
    - [x] static에 있는 html을 templates로 이동한다.
    - [x] 게시글 기능 구현을 담당할 ArticleController를 추가하고 애노테이션 매핑한다.
    - [x] 게시글 작성 요청(POST 요청)을 처리할 메소드를 추가하고 매핑한다.
    - [x] 사용자가 전달한 값을 Article 클래스를 생성해 저장한다.
    - [x] 게시글 목록을 관리하는 ArrayList를 생성한 후 앞에서 생성한 Article 인스턴스를 ArrayList에 저장한다.
    - [x] 게시글 추가를 완료한 후 메인 페이지(“redirect:/”)로 이동한다.
    
  - ## 글 목록 조회하기
    - [x] 메인 페이지(요청 URL이 “/”)를 담당하는 Controller의 method에서 게시글 목록을 조회한다.
    - [x] 조회한 게시글 목록을 Model에 저장한 후 View에 전달한다. 게시글 목록은 앞의 게시글 작성 단계에서 생성한 ArrayList를 그대로 전달한다.
    - [x] View에서 Model을 통해 전달한 게시글 목록을 출력한다.
      - * 게시글 목록을 구현하는 과정은 사용자 목록을 구현하는 html 코드를 참고한다.

  - ## 게시글 상세보기
    - [x] 게시글 목록(qna/list.html)의 제목을 클릭했을 때 게시글 상세 페이지에 접속할 수 있도록 한다.
      - [x] 게시글 상세 페이지 접근 URL은 "/articles/{index}"(예를 들어 첫번째 글은 /articles/1)와 같이 구현한다.
      - [x] 게시글 객체에 id 인스턴스 변수를 추가하고 ArrayList에 게시글 객체를 추가할 때 ArrayList.size() + 1을 게시글 객체의 id로 사용한다.
    - [x] Controller에 상세 페이지 접근 method를 추가하고 URL은 /articles/{index}로 매핑한다.
    - [x] ArrayList에서 index - 1 해당하는 데이터를 조회한 후 Model에 저장해 /qna/show.html에 전달한다.
    - [x] /qna/show.html에서는 Controller에서 전달한 데이터를 활용해 html을 생성한다.
  
- # 추가 요구 사항 (선택미션) 회원정보 수정

  - ## 요구사항
    - [x] 회원 목록에서 회원가입한 사용자의 정보를 수정할 수 있어야 한다.
    - [x] 비밀번호, 이름, 이메일만 수정할 수 있으며, 사용자 아이디는 수정할 수 없다.
    - [x] 비밀번호가 일치하는 경우에만 수정 가능하다.
  
  - ## 회원정보 수정 화면
    - [x] 회원가입한 사용자 정보를 수정할 수 있는 수정 화면과 사용자가 수정한 값을 업데이트할 수 있는 기능을 나누어 개발해야 한다.
    - [x] /user/form.html 파일을 /user/updateForm.html로 복사한 후 수정화면을 생성한다.
    - [x] URL 매핑을 할 때 "/users/{id}/form"와 같이 URL을 통해 인자를 전달하는 경우 @PathVariable 애노테이션을 활용해 인자 값을 얻을 수 있다.
    - [x] public String updateForm(@PathVariable String id)와 같이 구현 가능하다.
    - [x] Controller에서 전달한 값을 입력 폼에서 출력하려면 value를 사용하면 된다.
    - [x] \<input type="text" name="name" value="{{name}}" />
  
  - ## 회원정보 수정
    - [x] URL 매핑을 할 때 "/users/{id}"와 같이 URL을 통해 인자를 전달하는 경우 @PathVariable 애노테이션을 활용해 인자 값을 얻을 수 있다.
    - [x] UserController의 사용자가 수정한 정보를 User 클래스에 저장한다.
    - [x] {id}에 해당하는 User를 DB에서 조회한다(UserRepository의 findOne()).
    - [x] DB에서 조회한 User 데이터를 새로 입력받은 데이터로 업데이트한다.
    - [x] UserRepository의 save() 메소드를 사용해 업데이트한다.
  
  - ## PUT method 사용
    - [x] 수정 화면(updateForm.html)에서 수정된 값을 전송할 때 PUT값을 다음과 같이 hidden으로 전송한다.
      - [x] <input type="hidden" name="_method" value="PUT" />
    - [x] 위와 같이 _method로 PUT을 전달하면 UserController에서는 @PutMapping으로 URL을 매핑할 수 있다.

- # 주요 코드 설명

    - ## 회원정보 수정
  
    >![SmartSelectImage_2022-03-09-20-35-22](https://user-images.githubusercontent.com/47964708/157434303-7dcf4eb1-dc76-4d40-9d45-940152134c93.png)  

    - 해당 값들을 ArticleController로 전달하는 ModifiedUserParam 객체를 만들어 사용하고 있습니다.

    ```java
    public class ModifiedUserParam {
  
        ...
  
        public void isValidRequest() {
            if (!nowPassword.equals(password)) {
                throw new UnMatchedPasswordException(UNMATCHED_PASSWORD_MESSAGE);
            }
        }
    }    
    ```
  
    - isValidRequest 메서드로 받아온 기존의 비밀번호(password), 사용자가 입력한 현재 비밀번호(nowPassword) 를 비교하여 올바른 요청인지 확인합니다.

    ```java
    @PutMapping("/{userId}/update")
    public String modifyProfile(ModifiedUserParam modifiedUserParam,
                                HttpServletRequest request) {

        modifiedUserParam.isValidRequest();
  
        ...
    }
    ```

    - Repository 인터페이스에 ENTITY_STATUS(TRANSIENT, DETACHED) 상태 값을 추가했습니다.
    - 해당 값에 따라 save 메서드는 insert 와 update 작업을 각각 수행합니다.
    
    ```java
    public interface Repository<T, V> {

        enum ENTITY_STATUS {
            TRANSIENT, DETACHED
        }

        List<T> findAll();
        Optional<T> save(T obj);
        Optional<T> findOne(V primaryKey);
        void clear();
    }
    ```

    - Vector 를 자료구조로 사용하고 있는 추상 클래스를 정의했습니다.
    - ENTITY_STATUS 값이 TRANSIENT 이면 persist 메서드를 수행할 수 있도록
    - DETACHED 이면 merge 메서드를 수행할 수 있도록 추상 클래스에서 정의해놓았습니다.
    - merge 메서드는 사용자가 업데이트한 User 객체를 해당하는 인덱스에 다시 세팅합니다. (update)
    
    ```java
    public abstract class VolatilityUserRepository implements Repository<User, String> {

        protected final Vector<User> users = new Vector<>();

        protected User persist(User user) {
            user.setIndex(users.size() + 1);
            return users.add(user) ? user : null;
        }

        protected User merge(int index, User user) {
            users.set(index - 1, user);
            return user;
        }
    }
    ```
  
    - VolatilityUserRepositoryImpl 클래스에서 save 메서드를 정의했습니다.
    - findOne 메서드를 사용해 현재 save 하고자하는 User 객체가 리스트에 존재하는 지 확인한 뒤
    - 해당 결과 값에 따라 ENTITY_STATUS 를 결정합니다.
    - switch 문으로 상태에 따라 알맞은 메서드를 호출하도록 구성하였습니다.
    
    ```java
    @Repository
    public class VolatilityUserRepositoryImpl extends VolatilityUserRepository {

        ...
  
        @Override
        public synchronized Optional<User> save(User user) {
            Optional<User> other = findOne(user.getUserId());
            ENTITY_STATUS status = other.isEmpty() ? TRANSIENT : DETACHED;
            User result = null;
            switch (status) {
                case TRANSIENT:
                    result = persist(user);
                    break;
                case DETACHED:
                    result = merge(other.get().getIndex(), user);
            }
            return Optional.ofNullable(result);
        }
    
        ...
  
    }  
    ```
  
    - 글쓰기 기능의 경우 같은 Repository 인터페이스를 구현했기 때문에 방식은 동일하지만
    - id 값이 곧 index 이기 때문에 findOne 메서드를 호출하지 않고 바로 연산을 수행하도록 구현했습니다.

    ```java
    public abstract class VolatilityArticleRepository implements Repository<Article, Integer> {

        protected final Vector<Article> articles = new Vector<>();

        protected Article persist(Article article) {
            article.setId(Integer.toString(articles.size() + 1));
            return articles.add(article) ? article : null;
        }
    }
    ```

    ```java
    @Repository
        public class VolatilityArticleRepositoryImpl extends VolatilityArticleRepository {

        ...

        @Override
        public synchronized Optional<Article> save(Article article) {
            Article result = persist(article);
            return Optional.ofNullable(result);
        }

        @Override
        public Optional<Article> findOne(Integer index) {
            return Optional.ofNullable(articles.get(index - 1));
        }

        ...
  
    }
    ```

- # 실행결과

  ![SmartSelectImage_2022-03-09-20-55-17](https://user-images.githubusercontent.com/47964708/157437422-86a07d58-a589-4ac0-b3ba-df66805b4135.png)  
  ![SmartSelectImage_2022-03-09-20-55-30](https://user-images.githubusercontent.com/47964708/157437425-09f2ff32-3fa7-4ba9-8476-86d9bfe37ef6.png)  
  ![SmartSelectImage_2022-03-09-20-55-40](https://user-images.githubusercontent.com/47964708/157437426-c3e3d282-adcf-4d6f-81db-ced661d04ca4.png)  
  ![SmartSelectImage_2022-03-09-20-56-07](https://user-images.githubusercontent.com/47964708/157437427-bc8316f0-b093-40df-a240-f5195eebe9ed.png)  
  ![SmartSelectImage_2022-03-09-20-56-40](https://user-images.githubusercontent.com/47964708/157437428-db18f446-dad2-47c2-8ca1-ea8ab45bc55d.png)  
  ![SmartSelectImage_2022-03-09-20-56-47](https://user-images.githubusercontent.com/47964708/157437429-c0d80e1a-4002-4e65-9376-15379c3eb0df.png)  

# 스프링 카페 3단계 - DB에 저장하기

- # 프로그래밍 요구사항
  - build.gradle 설정 파일에서 H2 데이터베이스, spring-jdbc 의존성 추가
  - application.properties 파일에서 org.h2.Driver 에 접속하기 위한 값 설정
  - 모든 기능을 데이터베이스와 연동하여 구현한다.
  - Heroku 배포 URL: <https://naneun-spring-cafe.herokuapp.com>

- # 특이사항
  - 로컬환경에서는 H2 데이터베이스를, Heroku 배포 시에는 postgreSQL과 연동하도록 구현했습니다.
  - 도메인 클래스와는 별도로 데이터베이스 테이블과 1:1로 매핑되는 Entity 클래스들을 추가했습니다.
    - 무분별한 setter 메서드의 사용을 방지하고자 위와 같이 클래스들의 역할을 나눠봤습니다.

- # 변경사항
  - Service 패키지에서 불필요한 인터페이스를 제거했습니다.
    - Repository 레이어에서 이미 인터페이스를 사용하여 다형성을 구현했기 때문에 Service 레이어에서는 인터페이스가 필요하지 않다 판단했습니다.
    - Controller 와 Service 레이어는 세트로 '어떠한 특정 도메인을 담당하고 있다' 정도로 표현했습니다.
  - Repository 패키지에서 불필요한 추상화 클래스를 제거했습니다.
    - 추상화 클래스에서 해당 Repository가 사용하고 있는 저장소를 필드로 가지고 있는 용도로 사용했었지만 jdbc를 사용하는 레포지토리와의 통일성을 위해 삭제했습니다.

# 스프링 카페 4단계 - 로그인 구현

- ## 요구사항
  - [x] HttpSession 을 사용하여 로그인, 로그아웃 구현하기
    - [x] 비밀번호가 일치하지 않아 로그인 실패시 login_failed.html 로 이동하기
  - [x] 로그인, 로그아웃 상태에 따라 상단 메뉴 다르게 표시하기
  - [x] (선택) 개인정보 수정 기능 추가하기
    - [x] 다른 사용자의 정보를 수정하려할 시 error/4xx.html 로 이동하기 (에러 페이지를 만든 후 에러 메시지를 출력한다.)
  
- ## 해야할 일
  - HttpServletRequest, log 정리하기 (ControllerAdvice 사용 X)
  - Interceptor 학습하고 적용하기
  - 예외 처리 정리하기
  - 아직 해결하지 못한 3단계 리뷰 내용 반영하고 코멘트 달기

- ## 실행결과

  #### 로그인하지 않은 상태
  ![SmartSelectImage_2022-03-14-21-29-37](https://user-images.githubusercontent.com/47964708/158173149-e8d67c77-e08b-433b-b550-c5d734e0d2be.png)  
  #### 로그인 후 개인정보수정 메뉴를 선택
  ![SmartSelectImage_2022-03-14-21-31-52](https://user-images.githubusercontent.com/47964708/158173151-eefe8edc-19d8-42fe-9fd1-58bf9e065de4.png)  
  ![SmartSelectImage_2022-03-14-21-32-28](https://user-images.githubusercontent.com/47964708/158173152-e785024d-3da7-4ff9-979c-b5dd4aece4cc.png)  
  #### 개인정보 수정에 성공
  ![SmartSelectImage_2022-03-14-21-32-45](https://user-images.githubusercontent.com/47964708/158173154-8706d4ed-6c4e-43ee-b1a2-eebc709402df.png)  
  #### 다른 사용자의 정보를 수정하려할 시
  ![SmartSelectImage_2022-03-14-21-32-57](https://user-images.githubusercontent.com/47964708/158173156-77c5414c-b319-46ec-9556-2cdbd5694c5f.png)  
  #### 로그아웃
  ![SmartSelectImage_2022-03-14-21-33-09](https://user-images.githubusercontent.com/47964708/158173157-b34ea0a2-3310-49af-b0fb-d78d463f383f.png)  
  #### 비밀번호가 일치하지 않았을 시
  ![SmartSelectImage_2022-03-14-21-33-27](https://user-images.githubusercontent.com/47964708/158173160-52e42fb8-0501-449b-9e13-da120db7e6bf.png)  
