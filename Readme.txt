Day 1:
Error
1:plugin-org-springframework-bootspring-boot-maven-plugin-not-found

old:
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
</plugin>

New:
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <version>${project.parent.version}</version>
</plugin>

1) Unable to determine Dialect without JDBC metadata (please set 'jakarta.persistence.jdbc.url' for
common cases or 'hibernate.dialect' when a custom Dialect implementation must be provided)Unable to
determine Dialect without JDBC metadata (please set 'jakarta.persistence.jdbc.url' for common cases or
'hibernate.dialect' when a custom Dialect implementation must be provided)
=>Add this
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL8Dialect

3)Caused by: org.hibernate.exception.SQLGrammarException: Unable to open JDBC Connection for DDL
execution [Unknown database 'cafe-backend'] [n/a]
==>
CREATE SCHEMA `cafe-backend` ;


4)17:48:07.149: [cafe] git -c credential.helper= -c core.quotepath=false -c log.showSignature=false push --progress --porcelain origin refs/heads/master:master
  fatal: unable to access 'https://github.com/mylearningd2021/cafe-backend.git/': Recv failure: Connection was reset
==> use personal n/w

6



5) Now signup is working -- created new branch branch1
    branch1 -added dependencies
        guava
        gson
        android-json
        spring-boot-starter-security
        spring-boot-starter-test
        spring-boot-starter-mail
        jjwt
        itext
        pdfbox

7)While configuring security we are not getting WebSecurityConfigurerAdapater class that why spring
boot version changed from <version>3.3.1</version> to <version>2.6.3</version>

8)2024-07-17 20:07:17.081 ERROR 10116 --- [nio-8081-exec-2] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is java.lang.RuntimeException: org.springframework.dao.InvalidDataAccessApiUsageException: org.hibernate.hql.internal.QueryExecutionRequestException: Not supported for DML operations [update com.dg.cafe.model.User set status=:status where id=:id]; nested exception is java.lang.IllegalStateException: org.hibernate.hql.internal.QueryExecutionRequestException: Not supported for DML operations [update com.dg.cafe.model.User set status=:status where id=:id]] with root cause

9) java.lang.IllegalArgumentException: Modifying queries can only use void or int/Integer as return type!
 Offending method: public abstract com.dg.cafe.model.User com.dg.cafe.repo.UserRepository.updateStatus(java.
 lang.String,java.lang.Integer)] with root cause
