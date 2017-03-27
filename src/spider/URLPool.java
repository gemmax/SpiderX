package spider;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by kalexjune on 17/3/21.
 * todo provide one url of a website once time.
 * todo consider priority of url
 * TODO Consecutive crawling
 * todo data saved by l2cache
 */
public class URLPool {

    private static URLPool urlpool;
    private ArrayList<URL> priorityQueue;
    private ArrayList<URL> seeds;
    // private ArrayList<URL> websiteQueue;

    private URLPool(ArrayList<URL> seedset){
        seeds = seedset;
        priorityQueue = new ArrayList<URL>();
        //	websiteQueue = new ArrayList<URL>();
        if(seedset != null)
            priorityQueue.addAll(seedset);
    }


    /**
     * singleton mode with synchronized,when it is null, return urlpool directly.
     *
     * @param seedset the seed set of url to collect defined web pag.
     *
     * @ThreadSafe
     */
    public static URLPool getInstance(ArrayList<URL> seedset){
        if(urlpool == null) {
            synchronized (URLPool.class) {
                urlpool = new URLPool(seedset);
            }
        }
        return urlpool;
    }

    /**
     * crawler get url from url pool.
     * non-Consecutive crawling
     *
     * @ThreadSafe
     * */
    public synchronized URL getUrl(){
        //print queue info
        //printPriorityQueue();

        while (priorityQueue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        notify();
        URL url = priorityQueue.get(0);
        priorityQueue.remove(0);
        // consecutive
        //addUrl(url);

        // print gotten url
        printURLgot(url);
        return url;
    }

    /**
     * add new url into url pool by URLAnalysis analysed
     * @ThreadSafe
     * */
    public synchronized void addUrl(URL url) {
        priorityQueue.add(url);
    }

	/*private synchronized void printPriorityQueue() {
		System.out.println("...........url pool urlset...........");
		for(URL it: priorityQueue){
			System.out.println(it.toString());
		}
		System.out.println("..............end...............");
	}*/

	public ArrayList<URL> getSeeds() {
	    return seeds;
    }
    private synchronized void printURLgot(URL url) {
        System.out.println("#...........url got...........#");

        System.out.println("#url number in url pool: " + priorityQueue.size());
        System.out.println(url.toString());

        System.out.println("#..............end...............");
    }

	/*private synchronized void printURLadded(ArrayList<URL> urls) {
		System.out.println("...........url added into pool...........");
		if (urls == null)
			return;
		for(URL it: urls) {
			System.out.println(it.toString());
		}


		System.out.println("..............end...............");
	}*/

}
