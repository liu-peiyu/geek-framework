import com.geekcattle.util.CamelCaseUtil;
import com.geekcattle.util.DateUtil;
import com.geekcattle.util.UuidUtil;
import org.junit.Test;

/**
 * author geekcattle
 * date 2017/1/11 0011 下午 14:08
 */
public class TestUtil{

    @Test
    public void testCamelCaseUtil() {
        System.out.println(CamelCaseUtil.toUnderlineName("ISOCertifiedStaff"));
        System.out.println(CamelCaseUtil.toUnderlineName("CertifiedStaff"));
        System.out.println(CamelCaseUtil.toUnderlineName("UserID"));
        System.out.println(CamelCaseUtil.toCamelCase("iso_certified_staff"));
        System.out.println(CamelCaseUtil.toCamelCase("certified_staff"));
        System.out.println(CamelCaseUtil.toCamelCase("member"));
    }

    @Test
    public void testDateUtil() {
        System.out.println(DateUtil.getCurrentTime());//获取当前时间
        System.out.println(DateUtil.getCurrentDate());//获取当前日期
    }

    @Test
    public void testUuidUtil() {
        String[] ss = UuidUtil.getUUID(10);
        for (int i = 0; i < ss.length; i++) {
            System.out.println(ss[i]);
        }
    }

}
