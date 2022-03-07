# 2022 Java Spring Cafe

2022년도 마스터즈 멤버스 백엔드 스프링 카페 프로젝트

## 미션 1 - 회원 가입 및 목록 기능

### 요구사항

- [ ] 회원 가입 기능 구현
  - 회원 가입 양식: /users/form.html
  - 요청 메서드: POST /users
  - 회원 가입 후: 유저 목록으로 리다이렉트 (redirect:/users)
- [ ] 회원 조회 기능 구현
  - 요청 메서드: GET /users
  - 회원 목록 뷰 템플릿: /users/list.html
- [ ] 회원 프로필 조회 기능 구현
  - 요청 메서드: GET /users/{userId}
  - 회원 프로필 뷰 템플릿: /users/profile.html
- [ ] 템플릿을 이용하여 HTML 파일들의 중복 제거

### 구현과정 

- LocalRMIServerSocketFactory 클래스에서 IOException 발생
  - 상황
    - 초기 상태의 스프링 부트 프로젝트를 실행시켰을 때
    - WARN 로그를 통해 `sun.rmi.transport.tcp` 패키지에서 IOExcetion 예외가 보고된다.
  - 예외 메시지 
    - The server sockets created using the LocalRMIServerSocketFactory only accept connections from clients running on the host where the RMI remote objects have been exported.
  - 해결방법
    - CLI argument(인텔리제이 실행 설정에서는 VM options 항목)로 `-Dcom.sun.management.jmxremote.local.only=false`를 넘겨주면 예외가 발생하지 않늗다.