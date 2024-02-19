# BlogSphere (블로그 관리 및 인증 서비스 API)

- 사용자 인증 후 블로그 관련 정보(회원 정보, 블로그 게시물 등)를 관리할 수 있는 API 서비스. 
- Tistory API와 유사하게 Authentication Code 방식을 사용해 인증을 처리하며, RESTful API를 통해 데이터를 제공
- https://tistory.github.io/document-tistory-apis/apis/ 참고

## 핵심 기능
1. 인증 서비스
   - OAuth 2.0 기반의 Authentication Code 방식을 구현하여 안전한 인증 프로세스 제공
   - 사용자 로그인 및 인증 코드 발급, 액세스 토큰 및 리프레시 토큰 관리 포함
2. 회원 정보 관리
   - 사용자의 회원 가입, 로그인, 프로필 조회 및 수정 기능 제공
   - 사용자 인증 후에만 접근 가능한 회원 정보 API를 구현
3. 블로그 관리
   - 블로그 게시물의 생성, 조회, 수정, 삭제 기능을 제공
   - 게시물에 대한 댓글 및 좋아요 기능 추가
4. 데이터 관리 및 저장
   - MariaDB를 사용하여 사용자 정보, 블로그 게시물, 댓글 등의 데이터 관리
   - 트랜잭션 관리와 데이터 무결성을 보장

## 학습 포인트 및 기술 스택
- Spring Boot: API 서버 개발 및 관리.
- Spring Security와 OAuth 2.0: 인증 및 권한 부여.
- MariaDB: 데이터 저장 및 관리.
- JPA/Hibernate: ORM을 통한 데이터베이스 작업 간소화.
- RESTful API 디자인: 효율적인 클라이언트-서버 통신.
- JUnit 및 Mockito: 단위 테스트 및 통합 테스트.

## 프로젝트 구조
```
com.example.blogsphere
├── config                 # 보안 설정, 데이터베이스 설정 등의 구성 관련
│   ├── SecurityConfig.java
│   └── CorsConfig.java
├── controller             # HTTP 요청을 처리하고 응답을 반환하는 컨트롤러
│   ├── AuthController.java
│   ├── BlogController.java
│   ├── PostController.java
│   ├── UserController.java
│   ├── CommentController.java
│   └── CategoryController.java
├── service                # 비즈니스 로직을 수행하는 서비스
│   ├── AuthService.java
│   ├── BlogService.java
│   ├── PostService.java
│   ├── UserService.java
│   ├── CommentService.java
│   └── CategoryService.java
├── security               # JWT 토큰 생성, 인증 필터, API 키 인증 로직 등 보안 관련
│   ├── JwtTokenProvider.java
│   ├── JwtAuthenticationFilter.java
│   └── ApiKeyAuthFilter.java
├── repository             # JPA 및 MyBatis를 이용한 데이터 접근 로직
│   ├── jpa
│   │   ├── UserRepository.java
│   │   ├── BlogRepository.java
│   │   ├── PostRepository.java
│   │   ├── CommentRepository.java
│   │   └── CategoryRepository.java
│   └── mybatis
│       ├── AuthMapper.java
│       ├── BlogMapper.java
│       ├── PostMapper.java
│       ├── CommentMapper.java
│       └── CategoryMapper.java
├── model                  # 데이터베이스 테이블과 매핑되는 엔티티
│   ├── enum
│   │   └── Visibility.java
│   ├── Auth.java
│   ├── User.java
│   ├── Blog.java
│   ├── Post.java
│   ├── Comment.java
│   └── Category.java
└── exception              # 사용자 정의 예외 및 전역 예외 처리 클래스
    ├── CustomException.java
    └── GlobalExceptionHandler.java

```
```
src/main/resources
├── application.properties  # 스프링 부트 설정 파일
├── env.properties          # 환경 변수 파일
├── config                  # 설정 파일
│   ├── logback-spring.xml
│   └── mybatis-config.xml
└── mappers                 # MyBatis 매퍼 XML 파일들
    ├── AuthMapper.xml
    ├── UserMapper.xml
    ├── BlogMapper.xml
    ├── PostMapper.xml
    ├── CommentMapper.xml
    └── CategoryMapper.xml
```

## 프로젝트 개발 단계
1. 데이터베이스 모델링 및 엔티티 클래스 구현
2. JPA의 Repository interface와 MyBatis의 Mapper interface 정의
3. 비즈니스 로직 및 서비스 계층 구현
4. REST 컨트롤러 구현 (RESTful API 엔드포인트를 구현)
5. 보안 설정 및 인증 구현
6. 예외 처리 로직 구현
7. 최종 검증 및 리팩토링

### 1. 데이터베이스 모델링 및 엔티티 클래스 구현
```
1. 사용자(User) 테이블
   - id: 사용자 ID (Primary Key, Auto Increment)
   - username: 사용자 이름 혹은 사용자명
   - email: 이메일 주소
   - password: 비밀번호 (암호화 저장)
   - role: 권한
   - created_at: 계정 생성 시간
   - updated_at: 계정 정보 수정 시간
2. 블로그(Blog) 테이블
   - id: 블로그 ID (Primary Key, Auto Increment)
   - url: 블로그 URL
   - user_id: 사용자 ID 
   - title: 블로그 타이틀
   - description: 블로그 설명
   - is_default: 대표블로그 여부 (Y/N)
   - nickname: 블로그에서의 닉네임
   - created_at: 블로그 생성시간
3. 글(Post) 테이블
   - id: 글 ID (Primary Key, Auto Increment)
   - blog_id: 블로그 ID
   - user_id: 작성자 ID
   - title: 글 제목
   - content: 글 내용
   - visibility: 발행상태 (ENUM: PRIVATE, PROTECTED, PUBLIC)
   - category_id: 카테고리 ID
   - tag: 태그
   - accept_comment: 댓글 허용 여부 (BOOLEAN)
   - is_secret: 보호글 여부 (BOOLEAN)
   - password: 보호글 비밀번호
   - created_at: 발행시간
   - updated_at: 수정시간
4. 댓글(Comment) 테이블
   - id: 댓글 ID (Primary Key, Auto Increment)
   - post_id: 글 ID
   - author: 작성자
   - parent_id: 부모 댓글 ID (대댓글인 경우 사용)
   - content: 댓글 내용
   - is_secret: 비밀 댓글 여부 (BOOLEAN)
   - password: 비밀 댓글 비밀번호
   - created_at: 댓글 작성시간
5. 카테고리(Category) 테이블
   - id: 카테고리 ID (Primary Key, Auto Increment)
   - name: 카테고리 이름
   - parent_id: 부모 카테고리 ID
   - user_id: 사용자 ID
   - blog_id: 블로그 ID
   - created_at: 생성시간
   - updated_at: 수정시간
6. 인증(Auth) 테이블
   - id : code id (Primary Key, Auto Increment)
   - code : 인증코드
   - client_email : 인증에 사용할 사용자 이메일
   - expiration_time : 만료시간

* 관계 설정
   - User와 Blog: 하나의 사용자는 여러 블로그를 가질 수 있습니다 (One-to-Many).
   - Blog와 Post: 하나의 블로그는 여러 게시글을 가질 수 있습니다 (One-to-Many).
   - Post와 Comment: 하나의 게시글은 여러 댓글을 가질 수 있습니다 (One-to-Many).
   - Category와 Post: 하나의 카테고리는 여러 게시글을 가질 수 있습니다 (One-to-Many).
```
#### JPA를 사용하여 SQL 쿼리를 사용하지 않고 테이블 생성
   - 패키지: com.example.blogsphere.model
   - @Entity: 이 클래스가 JPA 엔티티임을 표시
   - @Table(name = "users"): 이 엔티티가 매핑될 데이터베이스 테이블의 이름을 지정
   - @Id 및 @GeneratedValue(strategy = GenerationType.IDENTITY): id 필드가 테이블의 기본 키임을 나타내며, 자동으로 생성되는 값임을 지정
   - @Column: 데이터베이스 테이블의 각 컬럼에 매핑. nullable, length, unique와 같은 속성 정의 가능.
   - getters, setters, constructors: JPA 엔티티의 필드에 대한 접근 메서드와 생성자
   - @PrePersist 및 @PreUpdate 어노테이션을 사용하여 생성 및 업데이트 시점에 자동으로 날짜/시간을 설정
   
### 2. JPA의 Repository interface와 MyBatis의 Mapper interface 정의
1. JpaRepository 인터페이스 활용 (Create, Update, Delete)
2. MyBatis Mapper 인터페이스 활용 (Read)
```
* AOP @Transactional  : 선언적 방식의 트랜잭션 관리
		- commit : 변경 사항을 실제 DB에 저장
		- rollback : 변경 사항을 취소, 원상태로 복귀
```
### 3. 비즈니스 로직 및 서비스 계층 구현
1. JpaRepository 인터페이스에 정의된 메소드를 사용하여 비즈니스 로직을 구현
2. MyBatis Mapper 인터페이스에 정의된 메소드를 사용하여 비즈니스 로직을 구현

### 4. REST 컨트롤러 구현 
1. @RestController 어노테이션을 사용하여 REST API를 구현
2. 각 메소드는 UserService의 메소드를 호출하여 필요한 작업을 수행
3. post 요청이 안될 때는 csrf를 설정해
```
@GetMapping : HTTP GET 요청을 처리하는 메소드에 사용하는 어노테이션
@PostMapping : HTTP POST 요청을 처리하는 메소드에 사용하는 어노테이션
@DeleteMapping : HTTP DELETE 요청을 처리하는 메소드에 사용하는 어노테이션
@PathVariable : URL 경로의 일부가 메소드의 매개변수로 전달되어야 할 때 사용
@RequestBody : 클라이언트가 보내는 데이터를 HTTP 요청의 본문(Body)에서 추출하여 자바 객체로 변환하는 데 사용
@RequiredArgsConstructor : 생성자 주입
```
#### ResponseEntity
 - HTTP 상태 코드, 응답 헤더, 응답 본문 등을 포함하는 완전한 HTTP 응답을 구성
 - 데이터와 함께 200 OK 응답 보내기 : ResponseEntity.ok(user);
 - 본문이 없는 HTTP 상태 코드 200 (OK) 응답 : ResponseEntity.ok().build();
 - 404 Not Found 응답 보내기 : ResponseEntity.notFound().build();
 - 헤더 정보 추가 : ResponseEntity.ok().headers(headers);
 - 조건부 응답 (값이 존재하면 map, 존재하지 않으면 orElse): userService.getUserById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

### 5. 보안 설정 및 인증 구현
1. @Configuration, @EnableWebSecurity 어노테이션을 사용하여 스프링 시큐리티 설정
2. Spring Security 6.1 버전부터 csrf() 메서드를 사용하는 대신 csrf(Customizer) 또는 csrf(Customizer.withDefaults())를 사용하는 것을 권장
3. Spring Security 6.1 버전부터 httpBasic() 메서드가 더 이상 사용되지 않으며, 대신 httpBasic(Customizer) 또는 httpBasic(Customizer.withDefaults())를 사용하도록 권장
4. CORS 설정 
   - corsConfig 클래스에 CorsConfigurationSource를 정의하고, SecurityConfig 클래스에서 CORS를 활성화
   - Cors 에러 확인 명령어 
```
* PowerShell에서 Basic Authentication을 사용한 요청을 보내는 방법
1. 사용자 이름과 비밀번호 설정
$springAPIuser = "admin"
$springAPIpass = "pass"

2. Basic 인증 헤더 값 생성
$base64AuthInfo = [Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes(("{0}:{1}" -f $springAPIuser, $springAPIpass)))

\3.# 웹 요청 수행
Invoke-WebRequest -Uri "http://172.30.1.48:8090/users" -Headers @{"Origin"="http://localhost:3000"; "Authorization"="Basic $base64AuthInfo"} -Method GET

```
5. OAuth 2.0
   - AuthController : 인증 요청 및 Authentication code 발급 후 Access Token 발급
6. JWT
   - JWT 토큰을 사용하여 Access Token을 생성하고, 이를 사용해 만든 API에 접근
   - io.jsonwebtoken.security.Keys.secretKeyFor 메서드를 사용하여 HS512 알고리즘에 적합한 안전한 키를 생성
   - Access Toket 발급 및 로그인 후 해당 API를 접근할 수 있게 작업
   - 
7. 비밀번호 암호화 및 저장
   - 사용자 생성 시 Spring Security는 BCryptPasswordEncoder 사용
   -  BCryptPasswordEncoder를 SecurityConfig에서 스프링 빈으로 등록한 후 UserService에 추가
   - 로그인 프로세스에 비밀번호를 검증하는 방식 추가
   - PasswordEncoder의 matches 메서드를 사용하여 입력된 비밀번호가 데이터베이스에 저장된 암호화된 비밀번호와 일치하는지 확인
   - passwordEncoder.encode
8. Basic, Bearer Authorization 둘 다 적용
   - Spring Security의 필터 체인에 커스텀 필터를 추가하여 Bearer 토큰과 Basic 인증 헤더를 모두 확인
   - JWT 인증 로직 구현
   - Basic 인증 로직 구현
9. Spring Security 환경에서 JWT 인증과 Basic 인증을 별도의 필터로 분리하여 처리
   - JwtAuthenticationFilter: JWT 토큰을 처리하는 필터.
   - CustomBasicAuthenticationFilter: Basic 인증 헤더를 처리하는 커스텀 필터.
   - SecurityConfig 설정: 위의 두 필터를 HttpSecurity 설정에 포함시키고, 각각의 인증 방식에 따라 적절히 요청을 처리하는 방법을 구성.
### 6. 예외 처리 로직 구현
1. try-catch문 사용
2. postMan으로 API 예외 확인
3. 확인 순서 (User -> Blog -> Category -> Post -> Common)

### 7. 최종 검증 및 리팩토링
1. postman으로 API 문서화 작성
```
https://documenter.postman.com/preview/29183426-6b3b1625-f3d3-4b10-8ba9-5c6ab52f2f7c?environment=&versionTag=latest&apiName=CURRENT&version=latest&documentationLayout=classic-double-column&documentationTheme=light&logo=https%3A%2F%2Fres.cloudinary.com%2Fpostman%2Fimage%2Fupload%2Ft_team_logo%2Fv1%2Fteam%2Fanonymous_team&logoDark=https%3A%2F%2Fres.cloudinary.com%2Fpostman%2Fimage%2Fupload%2Ft_team_logo%2Fv1%2Fteam%2Fanonymous_team&right-sidebar=303030&top-bar=FFFFFF&highlight=FF6C37&right-sidebar-dark=303030&top-bar-dark=212121&highlight-dark=FF6C37
```
2. JUnit 및 Mockito를 사용하여 단위 테스트와 통합 테스트를 완성
3. 입력값 검증, SQL 인젝션 방지, 크로스 사이트 스크립팅(XSS) 방어 등 보안 강화