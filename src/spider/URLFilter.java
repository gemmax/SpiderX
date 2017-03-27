package spider;

import util.Compression;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kalexjune on 17/3/21.
 * TODO 检查内容重复 check repetitive content
 * TODO URL过滤器 robot.txt
 *
 * todo data saved by l2cache
 *
 */
public class URLFilter {
    // threading safe
    private static HashMap<String,Boolean> urlmd5Set = new HashMap();
    private static boolean isSetUp = false;

    public URLFilter(ArrayList<URL> seeds) {
        synchronized (URLFilter.class) {
            if(isSetUp == false) {
                setUp(seeds);
            }
        }
    }
    /**
     * filter the url overlapped, ensure the same url wouldn't be added into pool twice.
     * @param urls the url set which are extracted from the crawling web pag.
     * @throws MalformedURLException from {@code handle4req(URL item)}
     * @threadsafe
     */
    public void filter(ArrayList<URL> urls) {
        hitOverlap(urls);
    }

    private void setUp(ArrayList<URL> seeds) {
        hitOverlap(seeds);
        isSetUp = true;
    }

    // reduce the possibility of requiring repetitive content
    private URL handle4req(URL item) {
        URL url = item;
        String urlString = item.toString();
        // if url string contains #, handle it!
        if (urlString.contains("#")) {
            // if url string contains #!
            if(urlString.contains("#!")) {
                urlString = urlString.replaceFirst("#!", "?_escaped_fragment_=");
            } else {
                urlString = urlString.substring(0, urlString.indexOf('#'));
            }
        }
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private void hitOverlap(ArrayList<URL> urls) {
        String urlmd5;
        for (URL item: urls) {
            item = handle4req(item);
            urlmd5 = Compression.md5(item.toString());
            synchronized (URLFilter.class) {
                if(! urlmd5Set.containsKey(urlmd5)) {
                    urlmd5Set.put(urlmd5, false);
                    URLPool.getInstance(null).addUrl(item);
                }
            }
        }
    }
}
