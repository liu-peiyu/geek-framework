import com.geekcattle.Application;
import com.geekcattle.mapper.member.MemberMapper;
import com.geekcattle.model.member.Member;
import com.geekcattle.service.member.MemberService;
import junit.framework.TestCase;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.J2Cache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * author geekcattle
 * date 2016/10/21 0021 下午 16:54
 */
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = Application.class)
public class TestApp extends TestCase {

    private static Logger logger = LoggerFactory.getLogger(TestApp.class);

    @Autowired
    JedisPool jedisPool;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    MemberMapper memberMapper;

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

    @Test
    public void testJ2Cache(){
        CacheChannel cache = J2Cache.getChannel();
        //缓存操作
        System.out.println(cache.get("default", "1"));
        cache.set("default", "1", "Hello J2Cache");
        System.out.println(cache.get("default", "1"));
        cache.evict("default", "1");
        cache.close();
    }

    @Test
    public void modifyPassword(){
        List<Member> lists = memberMapper.selectAll();
        for (Member member : lists) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String newPassword = passwordEncoder.encode("hao123");
            System.out.printf(newPassword);
            member.setPassword(newPassword);
            memberMapper.updateByPrimaryKey(member);
        }
    }


}
