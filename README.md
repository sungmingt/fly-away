# 🍀 FLYAWAY

<img width="927" alt="화면 캡처 2022-10-23 163901" src="https://user-images.githubusercontent.com/95558880/197380263-f787f673-050c-4913-8a0d-f72feb03086d.png">

### 🏋️‍♀️ 소개
> FLYAWAY는 ‘운동’과 ‘기록’을 결합한 통합 웹 서비스 입니다.

<br>

### 주요 기능 요약

📅 운동 영상을 시청하면서 홈트레이닝을 진행해보세요. 운동시간은 Flyaway에서 기록 해드립니다!

🏆 쌓여가는 기록과 채워지는 캘린더를 보면서 성취감을 느껴보세요!

📄 자신의 운동 노하우, 식단, 일상들을 공유하며 함께 운동해보세요!


### 🔗 [FLY-AWAY 바로가기](https://flyaway.main024.shop)  

<br>


### ⏰ 개발 기간
22-09-11 ~ 22-10-11

<br>

### 백엔드 사용 기술
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/JPA-59666C?style=for-the-badge&logo=hibernate&logoColor=white">
![AWS](https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)
<img src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=white">


<br>

## Commit Message Convention
- FEAT : 새로운 기능의 추가
- REFACTOR : 코드 리팩토링
- TEST : 테스트 코드, 리팩토링 테스트 코드 추가

<br>


## 👩‍💻Team

<table>
  <tr>
     <td align="center"><a href="https://github.com/slevme"><img src="https://avatars.githubusercontent.com/u/46449090?v=4?s=100" width="100px;" alt=""/><br /><sub><b>@slevme</b></sub></a><br /></td>
     <td align="center"><a href="https://github.com/Iandayy"><img src="https://avatars.githubusercontent.com/u/104152583?v=4?s=100" width="100px;" alt=""/><br /><sub><b>@Iandayy</b></sub></a><br /></td>
    <td align="center"><a href="https://github.com/sungmingt"><img src="https://avatars.githubusercontent.com/u/95558880?v=4?s=100" width="100px;" alt=""/><br /><sub><b>@sungmingt</b></sub></a><br /></td>
    <td align="center"><a href="https://github.com/Damgom"><img src="https://avatars.githubusercontent.com/u/104135990?v=4?s=100" width="100px;" alt=""/><br /><sub><b>@Damgom</b></sub></a><br /></td>
</tr>
      <tr>
            <td align="center">Front-end</td>
            <td align="center">Front-end</td>
            <td align="center">Back-end</td>
            <td align="center">Back-end</td>
      <tr>
      <td align="center">황보영</td>
      <td align="center">박수연</td>
      <td align="center">배성민</td>
      <td align="center">진종국</td>
      </tr>
    
</table>


- Github Project의 칸반보드를 이용한 일정 관리

<br><br>



## 📌담당한 역할

- ERD 설계
- 회원 관리
- 인증/인가
- 운동 시간 기록 및 조회 기능
- 영상 시청 기록 관리
- AWS 배포 서버 구축

<br><br>


## 📌ERD 설계

<img width="1033" alt="화면 캡처 2022-10-23 170217" src="https://user-images.githubusercontent.com/95558880/197381245-44a3dd02-32c7-4e8c-8b5c-75a9dd31e448.png">

- 게시판 - 카테고리의 경우 '하나의 게시글은 하나의 카테고리에만 포함될 수 있다' 라는 요구사항 -> N:1 매핑

<br><br>


## 📌테스트 (JUnit)

### 1. 단위 테스트

<img width="494" alt="화면 캡처 2022-10-23 171017" src="https://user-images.githubusercontent.com/95558880/197381522-a7c629ed-183c-41dd-826b-61dea2ccf6c9.png">

<br>

### 2. 시나리오 테스트
<img width="437" alt="main project member, record large test 2" src="https://user-images.githubusercontent.com/95558880/196962581-fb3ad9b0-9b85-4ead-bb4b-bbe1d713ace7.png">
<img width="544" alt="main auth test 성공 2" src="https://user-images.githubusercontent.com/95558880/201518352-cd651312-cd69-41f3-b7db-a6259a87c5ce.png">

- 추가적으로 사용자의 유스케이스를 테스트하기 위해 ```회원 가입 - 운동 기록``` 까지 이어지는 시나리오 테스트를 진행했습니다.

<br><br>



## 📌API Docs 

[Swagger](https://server.main024.shop/swagger-ui.html)

<img width="1109" alt="main project swagger - login, member" src="https://user-images.githubusercontent.com/95558880/196963723-1f7f7b3a-e8fb-484b-a45e-dcdc923f9c8a.png">

<img width="1085" alt="main project swagger - record, s3, video" src="https://user-images.githubusercontent.com/95558880/196963726-a8a7c9a7-f4dc-42e0-a64b-4c8d25e6e0ce.png">

**API 문서로 Swagger를 사용한 이유**

>Swagger는 코드가 애플리케이션에 섞인다는 단점이 존재하지만 크게 영향을 받지 않는다고 생각했고,
 수시로 변경될 가능성이 있는 API를 동적으로 빠르게 제공하기 위해 Swagger를 사용했습니다. 

<br><br>


## 📌인증/인가

<br>

> 인증 수단으로써 jwt를 사용했으며, spring interceptor로 사용자 권한을 확인하였습니다.

- refresh token은 만료 시간이 길기 때문에 탈취 당했을 경우 치명적일 수 있습니다.
따라서 refresh token은 별도의 DB(Redis)에 보관하고, access token 재발급 시 refresh token도 함께 재발급하도록 했습니다.

- access token의 만료시간을 짧게 설정하고, Blacklist를 도입함으로써 '서버가 제어권을 가질 수 없다' 라는 토큰 방식의 단점으로 발생할 수 있는 보안 문제 최소화를 시도했습니다.

이로써 세션의 장점 (서버의 인증 수단 제어권 소유) 과 토큰의 장점 (다중 서버 환경에 적합)을 최대한 살리려고 노력했습니다.

<br><br>



## 📌AWS를 이용한 배포

- 프론트엔드 서버는 Vercel에 배포하고, 백엔드 서버는 EC2에 jar 파일을 올려 실행시킵니다.
- 도메인을 별도 구입 후, Route 53을 통해 도메인에 대한 Name Server(NS)를 생성했습니다. 
- Route 53 -> ELB를 통해 백엔드 API 서버로 요청이 연결되도록 했습니다.
- RDS(MySQL)로 DB를 구성했습니다. 
- 이미지 저장소로 S3 bucket을 사용했습니다.

<img width="925" alt="main 배포 구조 그림 2" src="https://user-images.githubusercontent.com/95558880/198229349-73ab8237-d600-4869-b6a0-57a96c4525e7.png">


<br><br>


## 📌패키지 구조

<img width="510" alt="image" src="https://user-images.githubusercontent.com/95558880/201260668-7c3a4756-66cf-4d60-b655-ad44d3ba15f7.png">

- 패키지는 ```domain```과 ```web```으로 디렉토리를 세분화하고, 하위에 도메인 단위로 분리하여 관리하는 방식을 사용했습니다.
  - web 영역은 domain 영역에 비해 상대적으로 변화가 잦습니다. 
  - 따라서 web은 domain을 알되 domain은 web을 모르도록 하는 **단방향 의존관계**를 구현해야하기 때문에, domain과 web을 분리하여 관리하는 것이 효율적이라고 생각해 이 방식을 선택했습니다.

- 전역으로 사용되는 entityListener, exception 등은 ```global``` 패키지로 분리했습니다.


<br><br>


## 📌 예외 처리


- ```커스텀 exception code, errorResponse```를 정의하고 @RestControllerAdvice를 통해 exception handling을 구현했습니다.
- web 영역의 DTO 검증에는 ```Bean Validation```을 적용했습니다.

````http response
HTTP/1.1 400
Content-Type: application/json

{
    "status": 400,
    "message": null,
    "fieldErrors": [
        {
            "field": "email",
            "rejectedValue": "memb1gmail.com",
            "reason": "올바른 형식의 이메일 주소여야 합니다"
        },
        {
            "field": "password",
            "rejectedValue": "",
            "reason": "비어 있을 수 없습니다"
        }
    ],
    "violationErrors": null
}
````

<br><br>


## 📌 동시성 문제와 해결

- 회원의 누적 운동 기록을 member의 필드(totalRecord)로 가지도록 했을 때 발생할 수 있는 **동시성 문제**를 알게 되었습니다.
- 해결 방법으로 ```ThreadLocal```을 사용하거나, **```인스턴스의 필드가 상태값을 갖지 않도록```** 수정하는 방법 등이 있었습니다.
- 누적 기록이 필요한 경우 record 테이블에서 조회한 후 누적 기록을 추출하도록 변경함으로써 해결하였습니다.

<img width="575" alt="image" src="https://user-images.githubusercontent.com/95558880/203056858-ca586c81-5820-4f1d-98a5-19d853630b58.png">

<br><br>


## 📌 Scale-Out을 고려한 개발

개발 초기에 Session과 파일 업로드를 구현하던 도중, 데이터가 특정 서버에 저장(종속)될 경우 생기는 문제점과 이를 해결하는 방법에 대해 알게 되었습니다.

- AWS S3에 파일 업로드
- session clustering -> token 방식

서버 개수가 늘어나 다중 서버 환경이 되더라도 정상적으로 서비스가 유지될 수 있는 환경을 만들었습니다.

<br><br>


## 📌 프로젝트에서 특별히 집중한 부분 / 개선한 부분

- 제 코드가 ```nullsafe``` 하도록 특별히 주의를 기울였습니다.
  - 이를 위해 자바의 **Optional을 최대한 활용**했습니다.

- ```컨벤션```을 지키려고 노력했습니다.
  - commit 메시지, 네이밍 규칙 등 사소하지만 **협업**에 필요한 규칙들을 준수하였습니다.

- ```가독성 증진```
  - 남의 코드를 보는 건 힘든 일입니다. 팀원이 제 코드를 볼때 쉽게 이해할 수 있도록 지속적인 **리팩토링** 과정을 거쳐 개선했습니다.
  

<br><br>



