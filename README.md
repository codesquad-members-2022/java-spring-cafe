# Java Spring Cafe

백엔드 스프링 카페 프로젝트

## STEP1

> 요구사항
- 각 기능에 따른 url과 메소드 convention

| method | url             | 기능     |
|--------|-----------------|--------|
| GET    | /users/signup   | 회원가입 페이지 |
| POST   | /users/signup   | 회원가입 기능 |
| GET    | /users/list     | 회원목록   |
| GET    | /users/{userId} | 회원프로필 보기 |


> 회원가입 기능 구현
- 사용자가 전달한 값을 User 클래스를 생성해 저장한다.
- 사용자 목록을 관리하는 ArrayList를 생성한 후 앞에서 생성한 User 인스턴스를 ArrayList에 저장한다.
- 사용자 추가를 완료한 후 사용자 목록 페이지("redirect:/users")로 이동한다.

> HTML의 중복 제거
- header, navigation bar, footer 부분에 많은 중복 코드가 있다. 중복 코드를 제거한다.