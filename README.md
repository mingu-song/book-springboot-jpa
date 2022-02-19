# 스프링 부트 쇼핑몰 프로젝트 with JPA

* 일부 예제를 따라 해봄 (maven -> gradle)
* 원본 github :: https://github.com/roadbook2/shop
* 로컬 mysql 은 도커로 실행
```dockerfile
version: "3.7"
services:
  db:
    image: mysql:5.7
    restart: always
    command: --lower_case_table_names=1
    container_name: mysql57
    ports:
      - "3306:3306"
    environment:
      - MYSQL_DATABASE=shop
      - MYSQL_ROOT_PASSWORD=root
      - TZ=Asia/Seoul
    command: # 명령어 실행
      - --character-set-server=utf8mb4
      - --collation-server=utf8mb4_unicode_ci
    volumes:
      - C:\dev\docker\mysql\data:/var/lib/mysql
```