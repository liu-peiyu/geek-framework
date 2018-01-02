import com.geekcattle.Application;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import redis.clients.jedis.JedisPool;

/**
 * author geekcattle
 * date 2016/10/21 0021 下午 16:54
 */
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = Application.class)
@WebAppConfiguration // 由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
public class TestApp extends TestCase {

    private static Logger logger = LoggerFactory.getLogger(TestApp.class);

    @Autowired
    JedisPool jedisPool;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Test
    public void testRedis(){
        redisTemplate.boundValueOps("geekcattle").set("df1111111111111");
        System.out.println(redisTemplate.opsForValue().get("geekcattle"));
    }


    public static void main(String[] args) {
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
    }


}
