package study.memoforward.community;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommunityApplicationTests {

    @Test
    void contextLoads() {
        String tag = "111,222,333,444";
        tag = StringUtils.replace(tag,",","|");
        System.out.println(tag);
    }

}
