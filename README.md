## 게시판

간단한 Q&A를 작성할 수 있는 게시판을 구현한다.

<br/><br/>

<details>
<summary>📚 문제 설명 및 요구사항</summary>
<div markdown="1">
<br/>

### ✍🏻 기능요구사항

- 간단한 Q&A를 작성할 수 있는 게시판을 구현한다.  
    - 질문을 작성할 수 있어야 한다.
    - 게시글에 담기는 정보는 어느정도 자유롭게 구성한다.

- 비밀번호가 일치하는 경우 회원정보를 수정할 수 있어야 한다. 

<br/><br/><br/><br/>

### 💻 프로그래밍 요구사항

프로그래밍 요구사항은 아래 규칙을 따른다.

- 게시글 객체 id 인스턴스 변수를 추가하고 ArrayList에 게시글 객체를 추가할 때 ArrayList.size() + 1을 게시글 객체의 id로 사용한다.

<br/><br/><br/><br/>

### 각 기능에 따른 url과 메소드 convention

- 먼저 각 기능에 대한 대표 url을 결정한다.

    - 예를 들어 회원관리와 관련한 기능의 대표 URL은 "/users"와 같이 설계한다.

- 기능의 세부 기능에 대해 일반적으로 구현하는 기능(목록, 상세보기, 수정, 삭제)에 대해서는 URL convention을 결정한 후 사용할 것을 추천한다.

    - 예를 들어 todo 앱에 대한 URL convention을 다음과 같은 기준으로 잡을 수 있다.

<br/>

|  No |    <center>url</center>       | <center>기능</center>   |
|:---:|:------------------------------|:-----------------------|
|  1  |&nbsp; GET/todos               |&nbsp; List all todos   |
|  2  |&nbsp; POST/todos              |&nbsp; Create a new todo|
|  3  |&nbsp; GET/todos/:id           |&nbsp; Get a todo       |
|  4  |&nbsp; PUT/todos/:id           |&nbsp; Update a todo    |
|  5  |&nbsp; DELETE/todos/:id        |&nbsp; Delete a todo and its items|
|  6  |&nbsp; GET/todos/:id/items     |&nbsp; Get a todo item  |
|  7  |&nbsp; PUT/todos/:id/items     |&nbsp; Update a todo item |
|  8  |&nbsp; DELETE/todos/:id/items  |&nbsp; Delete a todo item |

<br/><br/><br/><br/>


</div>
</details>

<details>
<summary>📌 코딩 컨벤션</summary>
<div markdown="1">
<br/>

## 📌 코딩 컨벤션

- `기능 단위로 커밋`하며, 구현의 의미가 명확하게 전달되도록 커밋 메시지를 작성한다.<br/>
- 커밋은 -m 사용을 `지양`하며, 구체적 내용을 기록한다.

- `readme를 상세히 작성`한다.<br/>
    - `전체 프로젝트의 구조를 설명`한다.
    - 각 `패키지`와 `클래스, 메서드의 기능을 상세히 설명`한다.
    - (가능하다면) 패키지/클래스의 `역할과 책임을 명확하게 분리`한다.
    - 변수명은 문맥에 맞게 가장 보편적으로, 메서드명은 `무엇을 하는지를 명확히` 나타낸다.
    - 필요에 따라 그림과 PPT, 학습내용을 첨부해 `알기 쉽게 작성`한다.
    - 테스트 케이스를 기록하며 석연치 않은 부분을 매번 체크한다.

- 함수나 메소드의 들여쓰기를 가능하면 적게하도록 노력한다.<br/>
    - 한 메서드에는 가급적 `두 단계 이내`의 들여쓰기를 한다.
- 함수나 메소드는 한 번에 한 가지 일을 하고 가능하면 20줄이 넘지 않도록 구현한다. <br/>
- 무분별한 static의 사용을 최대한 `지양`한다.
- else 예약어를 `지양`한다.
- 함수나 메소드의 들여쓰기를 가능하면 적게(3단계까지만) 할 수 있도록 노력한다.

```javascript
 function main() {
    for (i = 0; i < 10; i++) { // 들여쓰기 1단계
        if (i == 2) { // 들여쓰기 2단계
            return; // 들여쓰기 3단계
        }
    }
}
```

<br/>

</div>
</details>


<br/><br/>
