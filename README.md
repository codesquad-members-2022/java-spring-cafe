# 2022 Java Spring Cafe

2022년도 마스터즈 멤버스 백엔드 스프링 카페 프로젝트

### 글쓰기

- [x] 게시글 기능 구현을 담당할 ArticleController를 추가하고 애노테이션 매핑한다.
- [x] 게시글 작성 요청(POST 요청)을 처리할 메소드를 추가하고 매핑한다.

### 글 목록 조회하기

- [x] 사용자가 전달한 값을 Article 클래스를 생성해 저장한다.
- [x] 게시글 목록을 관리하는 ArrayList를 생성한 후 앞에서 생성한 Article 인스턴스를 ArrayList에 저장한다.
- [x] 게시글 추가를 완료한 후 메인 페이지(“redirect:/”)로 이동한다.

- [x] 메인 페이지(요청 URL이 “/”)를 담당하는 Controller의 method에서 게시글 목록을 조회한다.

- [x] 조회한 게시글 목록을 Model에 저장한 후 View에 전달한다. 게시글 목록은 앞의 게시글 작성 단계에서 생성한 ArrayList를 그대로 전달한다.

- [x] View에서 Model을 통해 전달한 게시글 목록을 출력한다.

- [x] 게시글 상세보기 게시글 목록(qna/list.html)의 제목을 클릭했을 때 게시글 상세 페이지에 접속할 수 있도록 한다.

- [x] 게시글 상세 페이지 접근 URL은 "/articles/{index}"(예를 들어 첫번째 글은 /articles/1)와 같이 구현한다.

- [x] 게시글 객체에 id 인스턴스 변수를 추가하고 ArrayList에 게시글 객체를 추가할 때 ArrayList.size() + 1을 게시글 객체의 id로 사용한다.

- [x] Controller에 상세 페이지 접근 method를 추가하고 URL은 /articles/{index}로 매핑한다.

- [x] ArrayList에서 index - 1 해당하는 데이터를 조회한 후 Model에 저장해 /qna/show.html에 전달한다.

/qna/show.html에서는 Controller에서 전달한 데이터를 활용해 html을 생성한다.
- 
