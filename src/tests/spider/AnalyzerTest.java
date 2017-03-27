package spider;

import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by kalexjune on 17/3/21.
 */
public class AnalyzerTest {
    //private String webpage;

    @Before
    public void setup() throws MalformedURLException {
        ArrayList<URL> seedset = new ArrayList<URL>();
        seedset.add(new URL("http://www.163.com"));
        seedset.add(new URL("http://www.sina.com"));
        seedset.add(new URL("http://edu.sina.com.cn/"));
        seedset.add(new URL("http://edu.163.com/"));
        seedset.add(new URL("http://ast.nlsde.buaa.edu.cn/"));
        //URL url = URLPool.getInstance(seedset).getUrl();
        //Crawler crawler = new Crawler(url);
        //webpage = crawler.crawlWeb();
    }

    @Test
    public void extractURL() {
/*
		ArrayList<URL> urls;
		urls = (new URLAnalysis()).extractURL(webpage);
		URLPool.getInstance(null).addUrls(urls);
		for(URL itemUrl : urls) {
			System.out.println(itemUrl.toString());
		}
*/
    }



}