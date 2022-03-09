# 2022 Java Spring Cafe

2022년도 마스터즈 멤버스 백엔드 스프링 카페 프로젝트

## Step04 ToDo-List
- [ ] LoginServce 만들기
    - [x] 아이디로 찾아온 User의 password가 사용자가 넘긴 password와 동일하다면 user 반환
    - [x] 동일하지 않다면 null 반환하기
    - [ ] ~~해당 id의 유저가 있는지 확인: bool값 반환하기~~ 생각해보니 아이디로 찾았을때 없으면 그걸로 끝이다... 회원 가입도 아닌데, 중복 아이디 거증을 할 필요는 없는것 같다.
    - [x] 로그아웃 구현하기

## Step03 ToDo-List
- [x] user form 검증 로직 작성하기 (BeanValidation 사용하지 말것!)
- [x] article form 검증 로직 작성하기 (BeanValidation 사용하지 말것!)
- [x] 검증 로직을 Controller가 아닌, 따로 분리하기
- [x] @Validated 에노테이션 적용하기
- [x] User DB 연결
- [x] Article DB 연결
- [x] Dto를 전달받아 전달하는 메서드 Service에 구현하기
- [x] ArticleService Unit Test 만들기
- [x] UserService Unit Test 만들기
- [x] UserController Unit Test 만들기
