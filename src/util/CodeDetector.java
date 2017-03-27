package util;

import info.monitorenter.cpdetector.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Created by kalexjune on 17/3/23.
 */
public class CodeDetector {
    private CodepageDetectorProxy detectorProxy;
    private static CodeDetector codeDetector;

    private CodeDetector(){
        detectorProxy = CodepageDetectorProxy.getInstance();
        detectorProxy.add(new ByteOrderMarkDetector());
        detectorProxy.add(new ParsingDetector());
        detectorProxy.add(JChardetFacade.getInstance());
        detectorProxy.add(ASCIIDetector.getInstance());

    }

    public static CodeDetector getInstance(){
        if (codeDetector == null) {
            synchronized (CodeDetector.class) {
                codeDetector = new CodeDetector();
            }
        }
        return codeDetector;
    }



    public Charset detectEncode(InputStream stream) throws IOException {
        synchronized (codeDetector) {
            Charset charset = detectorProxy.detectCodepage(stream, 1024);
            return charset;
        }
    }

}
