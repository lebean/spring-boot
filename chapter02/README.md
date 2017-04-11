> 这是篇文章是建立在[上一篇文章](../Chapter02)基础之上的，前面讲了使用SpringBoot并集成了jpa、swagger2、logging，这篇文章将讲述在此基础上集成redis缓存

## Redis缓存

​	Redis是很好的缓存框架，支持集群。在很多系统中为了提升高并发业务的体验，都会使用缓存框架，例如各式各样的秒杀系统。

### 第1步，在服务器上安装redis

​	因为我当前使用的电脑是windows的，所以我就只讲讲在windows上redis的安装了，

1.   到[redis-windows-x64.3.2.100](https://github.com/MSOpenTech/redis/releases/tag/win-3.2.100)下载zip包到电脑上并解压7

2.  使用cmd配置服务，cd到redis-windows-x64.3.2.100目录下，执行以下代码，在系统服务列表中就可以看到redis服务了

   ```
   redis-server --service-install redis.windows-service.conf --loglevel verbose
   ```

3.  可以在系统服务列表中启动或停止redis服务，也可以cd到redis-windows-x64.3.2.100目录下执行以下命令

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

