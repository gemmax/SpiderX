package spider;

import org.junit.BeforeClass;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by kalexjune on 17/3/21.
 */
public class URLPoolTest {

    private static URLPool urlPool;

    @BeforeClass
    public static void setup() throws MalformedURLException {
        // TODO Auto-generated method stub
        ArrayList<URL> seedset = new ArrayList<URL>();
        seedset.add(new URL("http://baidu.com"));
        urlPool = URLPool.getInstance(seedset);
    }

    @Test
    public void test() throws MalformedURLException {
        //	fail("Not yet implemented");

        URL url = urlPool.getUrl();
        urlPool.addUrl(new URL("http://www.jianshu.com/p/7088822e21a3"));

        assertEquals(url.toString(), "http://baidu.com");
        url = urlPool.getUrl();
        url = urlPool.getUrl();
        assertEquals(url.toString(), "http://www.jianshu.com/p/7088822e21a3");

    }


}