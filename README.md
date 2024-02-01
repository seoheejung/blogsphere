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
│   ├── CorsConfig.java
│   └── DatabaseConfig.java
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
│       ├── UserMapper.java
│       ├── BlogMapper.java
│       ├── PostMapper.java
│       ├── CommentMapper.java
│       └── CategoryMapper.java
├── model                  # 데이터베이스 테이블과 매핑되는 엔티티
│   ├── enum
│   │   └── Visibility.java
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
└── mappers                 # MyBatis 매퍼 XML 파일들
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
  - created_at: 계정 생성 시간 (TIMESTAMP)
  - updated_at: 계정 정보 수정 시간 (TIMESTAMP)
2. 블로그(Blog) 테이블
  - blog_id: 블로그 ID (Primary Key, Auto Increment)
  - url: 티스토리 기본 URL
  - user_id: 사용자 ID 
  - title: 블로그 타이틀
  - description: 블로그 설명
  - is_default: 대표블로그 여부 (Y/N)
  - nickname: 블로그에서의 닉네임
  - role: 블로그 권한
  - statistics: 블로그 컨텐츠 개수
3. 글(Post) 테이블
  - id: 글 ID (Primary Key)
  - blog_id: 블로그 ID
  - title: 글 제목
  - content: 글 내용
  - visibility: 발행상태 (ENUM: PRIVATE, PROTECTED, PUBLIC)
  - category_id: 카테고리 ID (Foreign Key)
  - published: 발행시간 (TIMESTAMP)
  - tag: 태그
  - accept_comment: 댓글 허용 여부 (BOOLEAN)
  - password: 보호글 비밀번호
4. 댓글(Comment) 테이블
  - id: 댓글 ID (Primary Key, Auto Increment)
  - post_id: 글 ID
  - author: 작성자 (VARCHAR)
  - parent_id: 부모 댓글 ID (대댓글인 경우 사용)
  - content: 댓글 내용
  - is_secret: 비밀 댓글 여부 (BOOLEAN)
  - is_approved: 승인 여부 (BOOLEAN)
  - created_at: 댓글 작성시간 (TIMESTAMP)
5. 카테고리(Category) 테이블
  - id: 카테고리 ID (Primary Key, Auto Increment)
  - name: 카테고리 이름
  - parent_id: 부모 카테고리 ID
  - user_id: 사용자 ID
  - blog_id: 블로그 ID
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

### 2. JPA의 Repository interface와 MyBatis의 Mapper interface 정의
1. 단위 테스트 작성: JPA 리포지토리를 위한 단위 테스트를 작성하여 CRUD 작업이 올바르게 작동하는지 확인
2. MyBatis 매퍼 테스트: MyBatis 매퍼 XML 파일들이 제대로 작동하는지 테스트

### 3. 비즈니스 로직 및 서비스 계층 구현