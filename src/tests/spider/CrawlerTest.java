package spider;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by kalexjune on 17/3/21.
 */
public class CrawlerTest {
    @BeforeClass
    public static void setup() throws MalformedURLException {
        // TODO Auto-generated method stub
        ArrayList<URL> seedset = new ArrayList<URL>();
        seedset.add(new URL("http://www.163.com"));
        seedset.add(new URL("http://www.sina.com"));
        seedset.add(new URL("http://edu.sina.com.cn/"));
        seedset.add(new URL("http://edu.163.com/"));
        seedset.add(new URL("http://ast.nlsde.buaa.edu.cn/"));
        URLPool.getInstance(seedset);

    }

    @Test
    public void test() throws IOException, InterruptedException {

        Crawler crawler1 = new Crawler();

		/*
		   boolean isCrawl = crawler1.crawlWeb(url);
		   assertEquals(true, isCrawl);
		*/
        //url = URLPool.getInstance(null).getUrl();

		/*   write document into file named raws

		  BufferedWriter bWriter = new BufferedWriter(new FileWriter(new File("raws//crawlertest2.txt")));
		  bWriter.append(crawler1.getWebPage());
		  bWriter.flush();
		*/
    }

}