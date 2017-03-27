package util;

import java.util.HashMap;
import java.util.Map.Entry;

/**
 * confirm the type of file by file heading bytes.
 */
public class FileTypeDetector {

    public static final HashMap<String, String> mFileTypes = new HashMap<String, String>();

    static {
        // images
        mFileTypes.put("FFD8FF", "jpg");
        mFileTypes.put("89504E47", "png");
        mFileTypes.put("47494638", "gif");
        mFileTypes.put("49492A00", "tif");
        mFileTypes.put("424D", "bmp");
        //
        mFileTypes.put("41433130", "dwg"); // CAD
        mFileTypes.put("38425053", "psd");
        mFileTypes.put("7B5C727466", "rtf"); // 日记本
        mFileTypes.put("3C3F786D6C", FileTypeDetector.XML);
        mFileTypes.put("68746D6C3E", FileTypeDetector.HTML);
        mFileTypes.put("44656C69766572792D646174653A", "eml"); // 邮件
        mFileTypes.put("D0CF11E0", "doc");
        mFileTypes.put("5374616E64617264204A", "mdb");
        mFileTypes.put("252150532D41646F6265", "ps");
        mFileTypes.put("255044462D312E", FileTypeDetector.PDF);
        mFileTypes.put("504B03040A00000000008", "docx");
        mFileTypes.put("504B0304", "zip");// zip 压缩文件
        mFileTypes.put("52617221", "rar");
        mFileTypes.put("57415645", "wav");
        mFileTypes.put("41564920", "avi");
        mFileTypes.put("2E524D46", "rm");
        mFileTypes.put("000001BA", "mpg");
        mFileTypes.put("000001B3", "mpg");
        mFileTypes.put("6D6F6F76", "mov");
        mFileTypes.put("3026B2758E66CF11", "asf");
        mFileTypes.put("4D546864", "mid");
        mFileTypes.put("1F8B08", "gz");
    }

    /**
     * through file heading bytes
     *
     * @param bytes
     *
     * @return string of file type
     */
    public static String getFileType(byte[] bytes) {
        String value = bytesToHexString(bytes);
        String result = "";
        for (Entry<String, String> entry : mFileTypes.entrySet()) {
            if (value.startsWith(entry.getKey())) {
                result = entry.getValue();
            }
        }
        return result;
    }

    /**
     * convert byte array into hex string
     *
     * @param bytes
     *
     * @return string of byte
     */
    private static String bytesToHexString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String hv;
        for (int i = 0; i < bytes.length; i++) {
            // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
            hv = Integer.toHexString(bytes[i] & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        // System.out.println("HexString: " + builder.toString());
        return builder.toString();
    }



    public static final String PDF = "pdf";
    public static final String XML = "xml";
    public static final String HTML = "html";
}
