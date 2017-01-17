package Email.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Created by v_frankyfu on 2017/1/16.
 * Base64编码相关处理函数
 * 不需要编码为Base64
 */
public class Base64Util {
    public static byte[] EncodeBase64FromFile(String fileName) throws Exception{
        File file = new File(fileName);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return buffer;
        //return new BASE64Encoder().encode(buffer).getBytes();
    }
}
