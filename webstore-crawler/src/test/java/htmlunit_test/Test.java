package htmlunit_test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

public class Test {

    public static void main(String[] args) {
        WebClient wc = new WebClient(BrowserVersion.CHROME, "192.168.155.155", 25);
        DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider) wc.getCredentialsProvider();
        credentialsProvider.addCredentials("yproxyq", "zproxyx");
        wc.getOptions().setThrowExceptionOnScriptError(false);

        HtmlPage page = null;
        try {
            page = (HtmlPage) wc.getPage("https://www.facebook.com/login");
        } catch (IOException e) {
            e.printStackTrace();
        }

        HtmlInput uname = (HtmlInput) page.getElementById("email");
        uname.setValueAttribute("742814858@qq.com");
        HtmlInput upass = (HtmlInput) page.getElementById("pass");
        upass.setValueAttribute("Wei123122");
        HtmlButton logIn = (HtmlButton) page.getElementById("loginbutton");
        HtmlPage homePage = null;
        CookieManager bcs = new CookieManager();
        Set<Cookie> ckSet = null;
        try {
            homePage = logIn.click();
            bcs = wc.getCookieManager();
            ckSet = bcs.getCookies();
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuffer buffer = new StringBuffer();
        for (Cookie cookie : ckSet)
            buffer.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");

        System.out.println(buffer.toString());
    }


}
