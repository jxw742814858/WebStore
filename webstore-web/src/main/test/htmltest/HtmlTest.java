package htmltest;

import cn.iamtudou.kit.PropKit;
import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class HtmlTest {

    @Test
    public void sinaEscape() {
        String html = PropKit.file2String("HtmlFormat.txt");
        html = StringEscapeUtils.unescapeHtml4(html);
        try {
            html = URLDecoder.decode(html, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}
