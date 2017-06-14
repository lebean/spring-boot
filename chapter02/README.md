> 这是篇文章是建立在[上一篇文章](../Chapter02)基础之上的，前面讲了使用SpringBoot并集成了jpa、swagger2、logging，这篇文章将讲述在此基础上实现缓存与API权限难



## Redis缓存

​	Redis是很好的缓存框架，支持集群。在很多系统中为了提升高并发业务的体验，都会使用缓存框架，例如各式各样的秒杀系统。

### 第1步，在服务器上安装redis

​	因为我当前使用的电脑是windows的，所以我就只讲讲在windows上redis的安装了，

1.   到[redis-windows-x64.3.2.100](https://github.com/MSOpenTech/redis/releases/tag/win-3.2.100)下载zip包到电脑上并解压7

2. 使用cmd配置服务，cd到redis-windows-x64.3.2.100目录下，执行以下代码，在系统服务列表中就可以看到redis服务了

   ```
   redis-server --service-install redis.windows-service.conf --loglevel verbose
   ```

3. 可以在系统服务列表中启动或停止redis服务，也可以cd到redis-windows-x64.3.2.100目录下执行以下命令

   ```
   redis-server --service-start//启动
   redis-server --service-stop//停止
   redis-server --service-uninstall//卸载
   ```

### 第2步，在application.yml中配制redis服务器信息

​	在application.yml中配制redis服务器的地址、端口号乖参数

```
spring:
  datasource:
    driverClassName: com.mysql.jdbc.Driver
    url: jdbc:mysql://220.192.168.231:3306/test
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
  redis:
    database: 0
    host: localhost
    port: 6379
    pool:
      max-idle: 8
      max-active: 8
      max-wait: -1
      min-idle: 0
```

### 第3步，在java文件配制缓存

​	在config包下新建RedisCacheConfig.java，继承CachingConfigurerSupport，具体内容如下：

```
/**
 * Name:RedisCacheConfig
 * Description:
 * Author:leix
 * Time: 2017/4/10 13:48
 */
@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    @Bean
    public RedisTemplate redisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate(jedisConnectionFactory);
        //定义key序列化方式
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        //定义value的序列化方式
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setKeySerializer(redisSerializer);
        template.setHashKeySerializer(redisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
        RedisSerializer keySerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setHashKeySerializer(keySerializer);
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        return cacheManager;
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, objects) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(":" + method.getName() + ":");
            for (Object obj : objects) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

}
```

​	jedisConnectionFactory会提示“could not autowire.no beans of JedisConnectionFactory”，我测试了这个不影响运行。

### 第4步，在需要缓存的方法上添加缓存注解

```
@Service
@Transactional
public class CityServiceImpl implements CityService {

    @Autowired
    CityRepository cityRepository;

    @Override
    @Cacheable(value = "city")
    public City findById(int id) {
        return cityRepository.findById(id);
    }
}
```

> 这里可能有个小问题，我怎么知道缓存是否配制成功了呢？答案是可以通过日志来判定，在swagger-ui.html界面上，对一个服务测试，如果没有缓存的话，会一直看到sql执行的日志，如果有缓存的话只会在第一次执行时有sql执行操作



## REST API权限验证

​	RESTful资源是无状态的，但是我们写的API可能也不会是让人随意调，所以要给API加上调用权限验证，为了能更好的适用移动端、h5、或其它终端调用，我选择jwt配合spring-boot-starter-security来验证。

- 添加引用

在*.gradle文件中添加jjwt和security的引用

```
compile ("org.springframework.boot:spring-boot-starter-security")
compile ("io.jsonwebtoken:jjwt:${jjwtVersion}")
```

- 在application.yml中配置jwt的一些值

```
#jwt
jwt:
  header: Authorization
  secret: yoursecret
  expiration: 604800
  tokenHead: "Bearer "
```

- 配制security适配器

```
package leix.lebean.sweb.common.config;

import leix.lebean.sweb.auth.secruity.AuthenticationEntryPoint;
import leix.lebean.sweb.auth.secruity.AuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Name:WebSecurityConfig
 * Description:
 * Author:leix
 * Time: 2017/6/12 10:06
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new AuthenticationTokenFilter();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.POST, "/users")
                .antMatchers("/", "/auth/**", "/resources/**", "/static/**", "/public/**", "/webui/**", "/h2-console/**"
                        , "/configuration/**", "/swagger-ui/**", "/swagger-resources/**", "/api-docs", "/api-docs/**", "/v2/api-docs/**"
                        , "/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.gif", "/**/*.svg", "/**/*.ico", "/**/*.ttf", "/**/*.woff");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // 由于使用的是JWT，我们这里不需要csrf
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                // 允许对于网站静态资源的无授权访问
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();

        // 添加JWT filter
        httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        // 禁用缓存
        httpSecurity.headers().cacheControl();
    }
}

```

- 新建一个auth的业务模块

创建与验证业务相关的模块auth，并在auth中实现用户的验证、token的刷新。

