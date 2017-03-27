package spider;

import util.Compression;

import java.io.*;
import java.util.HashMap;

/**
 * Created by kalexjune on 17/3/21.
 */
public class URLSameDetection {

    private HashMap<String, Integer> urls;
    private URLSameDetection() {
        urls = new HashMap<String, Integer>();
    }

    private void countURLsNum(File file) {

        try {
            BufferedReader bReader = new BufferedReader(new FileReader(file));
            String line;
            int count;

            while ((line = bReader.readLine()) != null) {
                if (line.charAt(0) != '#') {
                    if (urls.containsKey(line)) {
                        count = urls.get(line);
                        count++;
                        urls.replace(line, count);
                    } else {
                        urls.put(line, 1);
                    }
                }
            }
            bReader.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        URLSameDetection detection = new URLSameDetection();
        detection.countURLsNum(new File("spider-ver0.2.1.txt"));
        try {
            detection.print();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void print() throws FileNotFoundException {
        int sum = 0;
        PrintStream ps;
        ps = new PrintStream(new FileOutputStream("urloverlap-ver0.2.1.txt"));
        System.setOut(ps);

        for(String key: urls.keySet()) {
            sum += urls.get(key);
            if (urls.get(key) > 1) {
                System.out.println("the url of follow occurs : " + urls.get(key));
                System.out.println(key);
                System.out.println();
            }
        }
        System.out.println("how much different url gotten " + urls.size());
        System.out.println("when stop total url gotten " + sum);

    }

}
