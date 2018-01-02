package cn.iamtudou.kit;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import name.iaceob.kit.httphelper.entity.ProxyEntity;
import name.iaceob.kit.httphelper.kit.HttpKit;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

public class ReqKit {
    private static Properties prop = PropKit.getProp("config.properties");
    private static Logger log = LoggerFactory.getLogger(ReqKit.class);
    private static Map<String, String> header = new HashMap<>();
    private static ProxyEntity proxyEn = new ProxyEntity(prop.getProperty("proxy.host"),
            Integer.valueOf(prop.getProperty("proxy.port")),
            prop.getProperty("proxy.user"),
            prop.getProperty("proxy.pwd"));

    /**
     * 请求重试封装
     *
     * @param url      请求url
     * @param useProxy 是否使用代理
     * @param charset  网页编码，“DEF” 为默认编码
     * @param reqType  "HTML" 为源码请求 "HTMLUNIT" 为模拟浏览器请求
     * @return 网页源码
     * @throws Exception
     */
    public static String reqRetry(String url, boolean useProxy, String charset, String reqType) throws Exception {
        String html = null;
        String errMsg = null;

        Charset chs = charset.equals("DEF") ? Charset.defaultCharset() : Charset.forName(charset);
        ProxyEntity proxy = useProxy ? proxyEn : null;
        int i = 0;
        while (i < 3) {
            i++;

            try {
                if (reqType.equals("HTML"))
                    html = HttpKit.get(url, null, header, proxy, chs).getHtml();
                else if (reqType.equals("HTMLUNIT"))
                    html = reqHtmlUnit(url, useProxy);
                return html;
            } catch (Exception e) {
                errMsg = e.toString();
                try {
                    Thread.sleep(3 * 1000);
                } catch (InterruptedException e1) {
                }
            }
        }

        if (StringUtil.isBlank(html)) {
            if (!errMsg.contains(url))
                errMsg = errMsg + ", url:" + url;
            throw new Exception("url requests 3 times failed, error message:" + errMsg);
        }

        return null;
    }

    /**
     * 使用HtmlUnit请求
     *
     * @param url
     * @param useProxy
     * @return
     */
    private static String reqHtmlUnit(String url, boolean useProxy) {
        String html = null;
        try (WebClient webClient = useProxy ? new WebClient(BrowserVersion.FIREFOX_52, prop.getProperty("proxy.host"),
                Integer.valueOf(prop.getProperty("proxy.port"))) : new WebClient(BrowserVersion.FIREFOX_52)) {

            //使用代理时设置代理用户名，密码
            if (useProxy) {
                DefaultCredentialsProvider cp = (DefaultCredentialsProvider) webClient.getCredentialsProvider();
                cp.addCredentials(prop.getProperty("proxy.user"), prop.getProperty("proxy.passwd"));
            }
            //规避script报错
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getOptions().setPrintContentOnFailingStatusCode(false);

            java.util.logging.Logger.getLogger("net.sourceforge.htmlunit").setLevel(Level.OFF);
            java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
            HtmlPage page = webClient.getPage(url);
            html = Jsoup.parse(page.asXml()).html();
            return html;
        } catch (Exception e) {
            log.error("htmlunit request error, msg:", e);
            return null;
        }
    }
}
