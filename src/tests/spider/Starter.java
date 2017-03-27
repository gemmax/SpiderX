package spider;

import util.Timer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by kalexjune on 17/3/21.
 */
public class Starter {
    public void setup() throws MalformedURLException {
        ArrayList<URL> seedset = new ArrayList<URL>();
        seedset.add(new URL("http://web.stanford.edu/class/cs246/"));
        seedset.add(new URL("http://blog.csdn.net/qiaqia609/article/details/11048463"));
        seedset.add(new URL("http://web.stanford.edu/class/cs224n/syllabus.html"));
        seedset.add(new URL("http://cs229.stanford.edu/section/cs229-linalg.pdf"));
        seedset.add(new URL("http://www.baidu.com"));
        //seedset.add(new URL("http://www.cnblogs.com/xinz/archive/2012/01/08/2316717.html"));
        URLPool.getInstance(seedset);
    }

    public static void main(String[] args) {
        Timer.start();

        PrintStream ps;
        try {

            // redirect output stream to file
            ps = new PrintStream(new FileOutputStream("spider-ver0.2.1.txt"));
            System.setOut(ps);

            // init url pool
            (new Starter()).setup();

            // start to crawl web
            for (int i = 0; i < 10; i++) {
                Thread thread = new Thread(new Crawler());

				/*synchronized (thread) {
					thread.wait(1000);
				}
				synchronized (thread) {
					thread.notify();
				}*/
                thread.start();

            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        Timer.end();
        System.out.println("#main thread : " + (double) Timer.getRunTime() / 1000 + " s.#");
    }

}
