# 잠깐 시간 될까 - Walk Holic
<img src="https://github.com/Walking-Holic/WalkHolic-Backend/assets/96743351/c320003e-a195-4021-865b-6ea2cdcc83e9" width="50%">

### 📖 프로젝트 소개

**잠깐 시간 될까 - Walk Holic**은 `Spring Boot`와  `Flutter`를 사용해서 크로스 플랫폼으로 현재 위치 기반으로 주변 산책로 정보를 제공하고, 사용자 경로 공유 커뮤니티와 같은 여러 편의 기능을 제공해주는 오픈 API 활용 프로젝트입니다.

[<img src="https://github.com/Walking-Holic/WalkHolic-Backend/assets/96743351/2250266d-15d3-4e19-8140-65fd2af1c6b1" width="100">](https://github.com/Walking-Holic/WalkHolic_Flutter)

--------


### ⛰️ 프로젝트 목표

- **오픈 API**와 **공공 데이터**를 활용합니다.
    - Kakao Login API
    - 공공 산책로 데이터
    - 기상청 API
    - Kakao Map API
 
- 역할을 분담하여 서비스를 코드로 **구현**하고 **배포**해봅니다.
    - 구현하는 과정에서 Front-end와 Back-end가 **협업**하는 과정을 이해합니다.
    - RESTful API를 직접 설계하고 API를 통한 HTTP 통신을 겪으며 협업 능력을 기릅니다.
    - 자신이 맡은 부분을 남에게 설명할 수 있는 의사소통 능력을 기릅니다.
    - 단순히 구현 후 끝나는 것이 아닌 **코드 리뷰**와 **피드백**을 통해 함께 성장합니다.
    - 기존의 코드를 지속적으로 개선하기 위해 **리팩토링**을 진행합니다.
      
- **Spring Boot**와  **Flutter**, 를 기반으로 다양한 기술 스택을 **학습**하고 적용합니다.

--------

### 👨🏻‍💻 기술스택
|Category|Stack|
|------|---|
|Tool|Intellij, CloudType|
|FrameWork|Spring Boot (3.1.2)|
|Language|Java (17)|
|Database|MariaDB|
|Collaboration|Git, Github, Discord, Notion|

--------

### 👨‍👦‍👦 Collaborator

| [<img src="https://github.com/Walking-Holic/WalkHolic-Backend/assets/96743351/e28da099-b59f-4844-8d78-104bf5f6ce65" width="100">](https://github.com/kimgunwooo)| [<img src="https://avatars.githubusercontent.com/u/103187357?v=4" width="100">](https://github.com/lunghyun) 
| :-----------------------------------: | :---------------------------------------: |

--------

### ⚙️ Database ERD

![image](https://github.com/Walking-Holic/WalkHolic-Backend/assets/96743351/94a8a6f6-037f-46b9-bb5f-34b6f76573d8)



---

### 🖥 ️주요 기능
<details>
<summary>로그인</summary>
<div markdown="1">

  - 소셜로그인(Kakao) 구현
  - 회원가입, 로그인, 로그아웃
    - JWT & Spring Security

</div>
</details>

<details>
<summary>경로 작성 게시판</summary>
<div markdown="1">
  
  - 게시판 CRUD
  - 경로 저장
  - 댓글 CRUD
  
</div>
</details>

<details>
<summary>주변 산책로 찾기</summary>
<div markdown="1">
  
  - Spring Batch를 통해 .csv 파일 DB 저장
  - 하버사인 공식(Haversine Formula)을 사용해 북동, 남서의 좌표를 구한 뒤, MySQL의 MBR 공간 좌표계를 통해 범위 내 산책로 검색 [참고](https://wooody92.github.io/project/JPA%EC%99%80-MySQL%EB%A1%9C-%EC%9C%84%EC%B9%98-%EB%8D%B0%EC%9D%B4%ED%84%B0-%EB%8B%A4%EB%A3%A8%EA%B8%B0/)

</div>
</details>

<details>
<summary>운동 기록 저장 & 통계 출력</summary>
<div markdown="1">
  
  - 운동 기록(걸은 시간, 걸음 수, 칼로리 소모량) 저장
  - 주, 월, 년, 전체 별로 통계 정보 출력

  
</div>
</details>


----

### ✍🏻 느낀점 (깨달은 점..)

[<img src="https://github.com/Walking-Holic/WalkHolic-Backend/assets/96743351/e28da099-b59f-4844-8d78-104bf5f6ce65" width="100">](https://github.com/kimgunwooo)

- JWT와 Spring Security를 사용해서 회원가입/로그인 기능을 구현해보면서 인증/인가 처리에 대한 이해도가 향상되었고, 보안의 중요성을 다시금 알게 되었다.
- JPA 양방향 매핑시에 무한재귀 문제가 생겼었다. 게시판 목록에서 해당 게시판의 주인인 멤버 정보도 출력하려는 과정에서 `Entity` 자체를 반환하려 했었고, 이는 무한 재귀 문제로 이어졌다. 그래서 이를 `DTO`로 바꿔서 해보았지만 해결되지 않았다. 그래서 `@JsonIdentityInfo`을 통해 순환참조될 대상의 식별키로 구분해 더이상 순환참조되지 않게 해 문제를 해결하였다.

  그리고 멤버와 게시글의 스크랩(저장) 기능으로 N:M 관계 매핑시에 `@ManyToMany`로 서로의 id를 가지고 있는 새로운 테이블(`collection`)을 생성하였다. 처음엔 무작정 게시글에서 `@ManyToMany`로 선언해서 해당 게시글을 저장한 멤버를 가져오는 방식을 사용했었는데, 이는 반대에서 즉 멤버쪽에서는 `collection` 테이블에 접근할 방법이 없기 때문에 접근 방식이 틀렸구나를 깨닫고 멤버에서 `@ManyToMany`를 사용해서 해당 사용자가 저장한 게시글을 가지고 있는 방식으로 해결했다.

  또한 자주 생긴 문제가 테이블을 추가하면서 연관 관계를 매핑할 때마다 `remove`동작이 제대로 작동하지 않는 문제가 발생했다. 이는 댓글 테이블에서 부모가 멤버와 게시판 2개로 `CascadeType.ALL`을 사용하면 `PERSIST`와 `REMOVE`를 모두 사용하게 되어 lazy 로딩시킬 경우 영속성 컨텍스트에 존재하게되어 삭제 동작이 이루어지지 않았다. 이 문제는 배포중에 일어난 일이라 배포한 DB의 댓글 테이블에서 `Cascade` 설정을 `remove`로 바꿔서 해결하였다.

  이를 통해 JPA로 개발 시에 양방향관계를 가진 객체를 직렬화하는 과정에서 문제가 생기거나, 관계 설정에서 CascadeType을 남발하거나, 연관 관계 자체를 잘못사용하는 다양한 실패를 겪어보았다. 개발 당시에는 정말 까다롭고 머리 아픈 문제였지만, 해결하고 돌아보니 한층 성장한 기분을 느꼈다!
- 프론트엔드와 협업을 통해 서로의 의견을 수립하고, 더 좋은 방향으로 갈 수 있었다.

  예로 게시판 상세 정보 페이지에서 게시판 정보, 해당 게시판의 댓글, 댓글의 멤버 등등이 필요했다. 나는 게시판을 담당했고 팀원은 댓글을 담당했는데 프론트 쪽에서 API 호출을 2번(게시판 정보, 댓글 정보) 해야하는게 불필요하다고 생각해 1개의 API로 모든 정보를 보내줬으면 좋겠다고 요청했다. 나는 API 라는 것 자체가 한쪽에 국한되지 않고, 만약 댓글 목록이 2군데 이상에서 필요하다면 재사용 가능하게 함이 좋다 생각했다. 하지만 프론트쪽 인원도 부족할 뿐더러, Flutter를 처음 사용해본 점, 프로젝트 규모가 작은 점들을 고려해서 프론트에서의 웬만한 요청을 모두 수용하려고 노력했다.

  이를 통해 다른 프로젝트에서 프론트와의 작은 갈등이 생기더라도, 현재 상황을 모두 고려해보며 가장 합리적인 방안으로 선택해 프로젝트를 진행할 것 같다.
