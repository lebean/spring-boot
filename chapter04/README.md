> SpringBoot这个框架我一直比较喜欢，但迫于没有时间整理，一直延续到现在才来整理框架。这个文章将是一个系列，从基础到复杂，后面将会不断推出系列内容。所有示例都使用Intellij IDEA编写，项目构建方式选择了Gradle

> 本篇文章主要介绍持久层、API文档、基础日志的配制。在数据持久层上我选择的是Spring-data-jpa，使用它非常方便，可少写很多代码，效率也不错，而且还是spring的亲儿子；api文档选择的是Swagger UI2，Swagger可以自动生成API文档，在文档界面上还可以测试，给开发代来很多方便的；日志这一部分的话就用了比较基础的东西了，会把运行日志输出到指定文件中，方便维护时查找问题。

### 第一步，创建项目并配制build.gradle文件
通过Intellij IDEA新建一个Gradle项目，在build.gradle文件中添加项目配制，执行一次Refresh all Gradle Projects
```
apply plugin: 'java'
apply plugin: 'org.springframework.boot'
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:1.5.2.RELEASE")
    }
}
repositories {
    jcenter()
}
dependencies {
    compile "org.springframework.boot:spring-boot-starter"
    compile 'org.springframework.boot:spring-boot-starter-actuator'
    compile 'org.springframework.boot:spring-boot-starter-web'
    compile 'org.springframework.boot:spring-boot-starter-data-jpa'
    compile 'org.springframework.boot:spring-boot-starter-amqp'
    compile 'org.springframework.boot:spring-boot-starter-integration'
    compile 'org.springframework.integration:spring-integration-file:4.3.5.RELEASE'
    compile 'org.springframework.boot:spring-boot-devtools'
    compile 'mysql:mysql-connector-java'
    compile 'com.google.code.gson:gson:2.8.0'
    compile "io.springfox:springfox-swagger-ui:2.2.2"
    compile "io.springfox:springfox-swagger2:2.2.2"
    testCompile("org.springframework.boot:spring-boot-starter-test")
}
```
### 第二步，创建项目文件目录
1. 创建java文件夹，在src文件夹下创建文件夹“/main/java”
2. 创建resources文件夹，在src文件夹下创建文件夹“/main/resources”

### 第三步，配制application.yml文件
在“src/main/resources/”文件夹下新建文件“application.yml”，并修改其中内容。
> server：服务器相关设定，在这晨设定了服务启动后的端口号和服务的根地址
> spring.datasource：配制系统数据源
> spring.jpa：设置jpa的基础信息
> logging：调协日志的信息，logging.file设置日志文件的存放，可以是“D:\test.logging”这种方式

```
server:
  port: 8081
  context-path: /api/

spring:
  datasource:
    driverClassName: com.mysql.jdbc.Driver
    url: jdbc:mysql://220.192.001.001:3306/test
    username: root
    password: 123456
    initialSize: 5
    minIdle: 5
    maxActive: 20

  jpa:
    hibernate:
      ddl-auto: update
      naming:
        strategy: org.hibernate.cfg.ImprovedNamingStrategy
    show-sql: true
    database: mysql

logging:
  file: springboot.log
  level:
    org:
      mybatis: TRACE
      springframework: INFO
    online:
      zhaopei:
        mypoject: TRACE
```
### 第四步，添加Application.java文件
在项目java包中添加Application.java文件，并初始化内容
```
package leix.lebean.sweb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
@SpringBootApplication
public class Application extends SpringBootServletInitializer  {
    @Autowired
    ApplicationContext context;
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
```
**到这里所有的配制就完成了，剩下的就是数据层的使用了，下面介绍一上jpa的使用** 
> 创建数据实体

```
package leix.lebean.sweb.model;
import javax.persistence.*;
import java.io.Serializable;
/**
 * Name:City
 * Description:
 * Author:leix
 * Time: 2017/3/28 14:02
 */
@Entity
@Table(name = "city")
public class City  implements Serializable{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "state")
    private String state;
    @Column(name = "country")
    private String country;
    public int getId() {return id;}

    public void setId(int id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) { this.name = name;}
    public String getState() {return state;}
    public void setState(String state) {this.state = state;}
    public String getCountry() {return country;}
    public void setCountry(String country) {this.country = country;}
}
```
> 创建数据处理仓库，jpa的具体语法在这里不详细讲述了，后面会专门写一篇文章来介绍

```
package leix.lebean.sweb.repository;
import City;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Name:CityRepository
 * Description:
 * Author:leix
 * Time: 2017/3/28 14:13
 */
public interface CityRepository extends JpaRepository<City, Integer> {
    City findById(int id);
}
```
> 数据处理仓库的调用

```
package leix.lebean.sweb.service.impl;

import City;
import CityRepository;
import CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CityServiceImpl implements CityService {
    @Autowired
    CityRepository cityRepository;
    @Override
    public City findById(int id) {
        return cityRepository.findById(id);
    }
}
```