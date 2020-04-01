package study.memoforward.community;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import net.minidev.json.JSONUtil;
import org.apache.commons.lang3.StringUtils;
import org.mockito.internal.util.StringUtil;

public class TestBasicMethod {
    public static void main(String[] args) {
        //String s = "access_token=3b10fcdd7cc2b8c6863e0dc9337af5056b3cb86e&scope=user&token_type=bearer";
        //String access_token = s.split("&")[0].split("=")[1];
        //System.out.println(access_token);
        String tag = "111,222，333，444";
//        tag = StringUtils.replace(tag,",","|");
        String[] tags = tag.split(",|，");
        tag = StringUtils.joinWith("|",tags);
        System.out.println(tag);
    }
}
