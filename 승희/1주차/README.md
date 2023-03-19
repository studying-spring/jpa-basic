### SQL 중심적으로 개발했을 때의 문제점

- 쿼리를 반복적으로 작성해야 한다.
- 수정을 과도하게 해야 한다.
- SQL에 의존적인 개발을 하게 된다.
- 객체와 SQL 로 매핑하는 역할을 개발자가 해야 한다.

### 객체모델링 저장

```java
class Member{
	Long id;
	Team team; // 객체모델링을 하지 않으면 Long teamId 를 저장해야 함 
	String username;
}
```

FK → 참조를 통해서 연관관계를 맺을 수 있음

처음에 어떤 객체로 시작해서 탐색을 했는지에 따라 범위가 제한된다.

조회해서 가져온 객체가 sql 에 의존하게 되면 다른 객체

그러나 자바 컬렉션을 통해서 조회하게 되면 같은 객체

객체지향적으로 설계를 하지만, 매핑 작업에 의한 비용이 너무 크다

### 그래서 jpa 를 사용하기 시작

- ORM
- 자바 애플리케이션과 JDBC API 사이에서 동작
1. 엔티티 분석
2. insert 쿼리 생성
3. jdbc api 사용
4. 패러다임 불일치 해결

### 성능최적화

1. 1차 캐시와 동일성 보장
    1. 동일한 엔티티를 반환하기 때문에 (기존에는 다른 엔티티로 인식) → 캐싱 상의 이점이 있다.
2. 트랜잭션 커밋 이전까지 insert sql 을 모아둔다
3. 지연 로딩과 즉시 로딩

   지연 로딩 : 객체가 실제로 사용되는 시점에 끌어온다.

   즉시 로딩 : 처음부터 연관된 테이블을 다 끌어온다.

```xml
<persistence-unit name="hello">
    <properties>
        <!-- 필수 속성 -->
        <property name="javax.persistence.jdbc.driver" value="org.h2.Driver"/>
        <property name="javax.persistence.jdbc.user" value="sa"/>
        <property name="javax.persistence.jdbc.password" value=""/>
        <property name="javax.persistence.jdbc.url" value="jdbc:h2:tcp://localhost/~/test"/>
        <property name="hibernate.dialect" value="org.hibernate.dialect.H2Dialect"/>
        <!-- 옵션 -->
        <property name="hibernate.show_sql" value="true"/>
        <property name="hibernate.format_sql" value="true"/>
        <property name="hibernate.use_sql_comments" value="true"/>
        <!--<property name="hibernate.hbm2ddl.auto" value="create" />-->
    </properties>
</persistence-unit>
```

hibernate 는 jpa 의 `구현체` 따라서 다른 구현체로 갈아끼우면 적용이 안됨!

`persistence.xml`

persistence 라는 클래스가 persistence.xml 설정 정보를 읽고 동작 → EntityManagerFactory 를 생성하고 이가 필요할 때마다 EntityManager 를 생성함

`EntityManager`

- db 와의 커넥션을 여는 용도
- transaction 을 entity manager 를 통해서 얻을 수 있음

`jpa 의 이점`

업데이트 시에, 별도로 저장(em.persist())을 하지 않아도 저장을 한 것과 같이 동작한다.

`주의사항`

1. 엔티티 매니저 팩토리는 1개만 생성해서 프로젝트 전체에서 공유해야 한다.
2. 엔티티 매니저는 쓰레드 간의 공유를 해서는 안된다.

`JPQL`

JPA 는 SQL 을 추상화한 JPQL 이라는 쿼리 언어 제공해준다.

JPQL 은 객체를 대상으로 쿼리 / SQL 은 테이블을 대상으로 쿼리