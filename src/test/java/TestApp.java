import com.geekcattle.Application;
import com.geekcattle.service.console.LogService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * author geekcattle
 * date 2016/10/21 0021 下午 16:54
 */
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = Application.class)
@WebAppConfiguration // 由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
public class TestApp extends TestCase {

    @Autowired
    private LogService logService;

    @Test
    public void insertLog() {
        for(int i=1; i<=10000; i++){
            logService.insertLoginLog("flyshy", "127.0.0.1" ,"/junit/test"+i);
        }
    }


}
