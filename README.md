# 2022 Java Spring Cafe

2022년도 마스터즈 멤버스 백엔드 스프링 카페 프로젝트

## 미션 1 - 회원 가입 및 목록 기능

### 요구사항

- [x] 회원 가입 기능 구현
  - 회원 가입 양식: /users/form.html
  - 요청 메서드: POST /users
  - 회원 가입 후: 유저 목록으로 리다이렉트 (redirect:/users)
- [x] 회원 조회 기능 구현
  - 요청 메서드: GET /users
  - 회원 목록 뷰 템플릿: /users/list.html
- [x] 회원 프로필 조회 기능 구현
  - 요청 메서드: GET /users/{userId}
  - 회원 프로필 뷰 템플릿: /users/profile.html
- [x] 템플릿을 이용하여 HTML 파일들의 중복 제거
- [x] DB는 사용하지 않는다.
  - 단, 이후 DB를 사용하도록 변경할 수 있어야 한다.

### 구현과정 

- LocalRMIServerSocketFactory 클래스에서 IOException 발생
  - 상황
    - 초기 상태의 스프링 부트 프로젝트를 실행시켰을 때
    - WARN 로그를 통해 `sun.rmi.transport.tcp` 패키지에서 IOExcetion 예외가 보고된다.
  - 예외 메시지 
    - The server sockets created using the LocalRMIServerSocketFactory only accept connections from clients running on the host where the RMI remote objects have been exported.
  - 해결방법
    - CLI argument(인텔리제이 실행 설정에서는 VM options 항목)로 `-Dcom.sun.management.jmxremote.local.only=false`를 넘겨주면 예외가 발생하지 않늗다.
- `@EnabaleWebMvc`
  - 인터넷 상의 스프링 예제에는 `WebMvcConfigurer`의 구현체 클래스에 이 애너테이션이 붙은 경우가 자주 있다. 
  - 이 애너테이션을 사용하면 뷰 생성시 스태틱 리소스들을 제대로 불러오지 못하는 현상이 생겼다. 
  - 스프링 부트의 스프링 MVC 자동 설정을 비활성화시키기 때문이라고 하는데, 정확한 내용은 확인이 필요하다. 