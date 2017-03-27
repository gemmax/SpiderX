package spider;

import indexer.ContentExtractor;
import info.monitorenter.cpdetector.io.*;
import util.CodeDetector;
import util.Compression;
import util.FileTypeDetector;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kalexjune on 17/3/21.
 * todo save data by l2cache.
 */
public class Analyzer {

    public void analyse(String docString) throws IOException {
        if(docString != null) {
            ArrayList<URL> urls = null;

            urls = extractURL(docString);

            // ver0.2 url filter
            (new URLFilter(URLPool.getInstance(null).getSeeds())).filter(urls);
        }
    }

    private ArrayList<URL> extractURL(String docString) {
        // TODO copy from others
        final String patternString = "<[a|A]\\s+href=([^>]*\\s*>)";
        Pattern pattern = Pattern.compile(patternString,Pattern.CASE_INSENSITIVE);

        ArrayList<URL> allURLs = new ArrayList<URL>();

        Matcher matcher = pattern.matcher(docString);
        String tempURL;
        while(matcher.find())
        {
            try {

                tempURL = matcher.group();
                tempURL = tempURL.substring(tempURL.indexOf("\"")+1);
                if(!tempURL.contains("\""))
                    continue;

                tempURL = tempURL.substring(0, tempURL.indexOf("\""));
                if(tempURL.startsWith("http"))
                    allURLs.add(new URL(tempURL));


            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return allURLs;
    }
}
