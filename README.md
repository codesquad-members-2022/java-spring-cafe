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


> 기능 목록
- 회원가입  
- 회원목록 
- 회원 프로필 정보 보

> HTML의 중복 제거
- header, navigation bar, footer 부분에 많은 중복 코드가 있다. 중복 코드를 제거한다.
___

## STEP2

> 기능 목록
- 사용자는 게시글을 작성할 수 있어야 한다.
- 모든 사용자는 게시글 목록을 볼 수 있어야 한다.
- 모든 사용자는 게시글 상세 내용을 볼 수 있어야 한다.
- (선택) 사용자 정보를 수정할 수 있어야 한다.

> 기능별 url과 메소드 convention

| method | url                   | 기능        |
|--------|-----------------------|-----------|
| GET    | /articles             | 게시글작성 페이지 |
| POST   | /articles             | 게시글작성 기능  |
| GET    | /                     | 게시글목록     |
| GET    | /articles/{articleId} | 게시글 상세내용  |
| POST   | /users/{userId}       | 회원프로필 수정  |

