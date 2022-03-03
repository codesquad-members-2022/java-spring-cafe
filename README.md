# 2022 Java Spring Cafe

2022년도 마스터즈 멤버스 백엔드 스프링 카페 프로젝트


* 궁금점
  1. view에서 보여주지 않는다면 패스워드와 같이 숨겨야하는 정보까지 model에 넘겨줘도 될까?
     1. 안된다면 다른 dto를 생성하는 것이 맞을까? 기존 dto에서 메서드를 통해서 패스워드 정보는 저장하지 않은 채로 보내는 것이 맞을까?

```
├── main
│   ├── java
│   │   └── com
│   │       └── kakao
│   │           └── cafe
│   │               ├── CafeApplication.java
│   │               ├── domain
│   │               │   └── user
│   │               │       ├── MemoryUserRepository.java
│   │               │       ├── User.java
│   │               │       └── UserRepository.java
│   │               ├── service
│   │               │   └── UserService.java
│   │               └── web
│   │                   ├── HomeController.java
│   │                   ├── UserController.java
│   │                   └── dto
│   │                       ├── UserDto.java
│   │                       └── UserResponseDto.java
│   └── resources
│       ├── application.properties
│       ├── static
│       │   ├── css
│       │   │   ├── bootstrap.min.css
│       │   │   └── styles.css
│       │   ├── favicon.ico
│       │   ├── fonts
│       │   │   ├── glyphicons-halflings-regular.eot
│       │   │   ├── glyphicons-halflings-regular.svg
│       │   │   ├── glyphicons-halflings-regular.ttf
│       │   │   ├── glyphicons-halflings-regular.woff
│       │   │   └── glyphicons-halflings-regular.woff2
│       │   ├── images
│       │   │   └── 80-text.png
│       │   ├── js
│       │   │   ├── bootstrap.min.js
│       │   │   ├── jquery-2.2.0.min.js
│       │   │   └── scripts.js
│       │   └── qna
│       │       ├── form.html
│       │       └── show.html
│       └── templates
│           ├── form.html
│           ├── index.html
│           ├── layout
│           │   ├── footer.html
│           │   ├── header.html
│           │   └── navbar.html
│           ├── list.html
│           ├── login.html
│           ├── login_failed.html
│           └── profile.html
└── test
    └── java
        └── com
            └── kakao
                └── cafe
                    ├── CafeApplicationTests.java
                    ├── domain
                    │   └── user
                    │       └── MemoryUserRepositoryTest.java
                    ├── service
                    │   └── UserServiceTest.java
                    └── web
                        ├── HomeControllerTest.java
                        └── UserControllerTest.java

```
