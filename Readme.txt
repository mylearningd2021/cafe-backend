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