package spider;

import util.*;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by kalexjune on 17/3/21.
 * TODO 线程关闭前关闭网络连接
 *
 * todo data saved by l2cache
 */
public class Crawler  implements Runnable{
    private static long  file_id = 0;

    private String crawlWeb(URL url) throws IOException {
        //todo only receive stream which type can be analysed. Such as pdf, html.
        //todo this code block maybe cause memory overflow.

        String type = null;
        byte[] bytes = new byte[64];

        URLConnection urlConnection = url.openConnection();
        BufferedInputStream biStream = new BufferedInputStream(
                urlConnection.getInputStream());
        // combine with reset to reuse input stream
        biStream.mark(64 * 2);


        biStream.read(bytes,0, bytes.length);
        type = FileTypeDetector.getFileType(bytes);
        biStream.reset();

        Charset encode = CodeDetector.getInstance()
                .detectEncode(biStream);


        //一些pdf受损，一些不受损 有排版乱的，中间空白。   没有这一段代码，则全部pdf受损
        if (type.equals(FileTypeDetector.PDF)) {
            addRawFile(url,type,biStream);
            return null;
        }

        // when type is html, xml
        //if (type.equals(FileTypeDetector.HTML) || type.equals(FileTypeDetector.XML)) {

            // convert byte stream to character stream
            StringBuffer stringBuffer = new StringBuffer();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(biStream, encode));
            String line = null;
            while((line = br.readLine()) != null) {
                stringBuffer.append(line);
            }

            // add to disk
            addRawFile(url, type, stringBuffer.toString());

            return stringBuffer.toString();
        //}

        // todo test
        //System.out.println("# type : " + type);

        //return null;

    }

    @Override
    public void run() {
        URL url;
		/* when urlpool is empty, crawler sleep.
		   while ((url = URLPool.getInstance(null).getUrl()) != null) {*/
        // start / test condition
        int count = 0;
        while (count++ < 20) { // todo each thread only collect 20 web pages. change to continual crawling.
            url = URLPool.getInstance(null).getUrl();
            // end / test condition

            Analyzer analyzer = new Analyzer();
            try {
                analyzer.analyse(crawlWeb(url));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                // System.out.println("crawler run throw a exception");
            }

        }

		/* 测试运行时间 test for running time */
        Timer.end();
        System.out.println("#crawler thread : " + (double)Timer.getRunTime() / 1000 + " s.#");
    }

    private void addRawFile(URL url, String type, String s) {
        try {
            writeRaw(s, type);
            add2IndexDB(url, type, Compression.md5(s));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void writeRaw(String s, String type) throws IOException {
        File dir = new File(PATH);
        if(!dir.exists()) {
            dir.mkdir();
        }
        synchronized (Crawler.class) {
            BufferedWriter bWriter = new BufferedWriter(new FileWriter(new File(PATH+file_id+'.'+type)));
            bWriter.append(s);
            bWriter.flush();
            bWriter.close();
            file_id++;
        }
    }

    private void add2IndexDB(URL url, String type, String s) throws SQLException {
//        Connection conn = DBUtil.getInstance();

    }


    //  todo 我以为改成字节流，下载的pdf就不会是受损的。但看情况，file id也是乱的，估计是同步没有做好。但打开不同文件，不需要同步。

    private void addRawFile(URL url, String type, InputStream stream) {
        try {
            writeRaw(stream, type);
            //add2IndexDB(url, type, Compression.md5(s));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeRaw(InputStream stream, String type) throws IOException {
        File dir = new File(PATH);
        if(!dir.exists()) {
            dir.mkdir();
        }

        synchronized (Crawler.class) {
            BufferedOutputStream bs = new BufferedOutputStream(new FileOutputStream(new File(PATH+file_id+'.'+type)));

            byte[] b = new byte[1024];
            int ret;
            while ((ret = stream.read(b)) != -1) {
                bs.write(b);
                bs.flush();
            }
            stream.close();
            bs.close();


            file_id++;

        }
    }

    private final static String PATH = "raws" + File.separator;
}
