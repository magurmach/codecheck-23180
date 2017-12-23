package codecheck.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Shakib Ahmed on 12/22/17.
 */
public class FileUtil {
    public static byte[] readFile(String fileName) throws IOException {
        File file = new File(fileName);
        InputStream fileInputStream = new FileInputStream(file);

        long length = file.length();
        byte[] bytes = new byte[(int)length];

        fileInputStream.read(bytes);

        fileInputStream.close();
        return bytes;
    }

    public static void writeFile(String fileName, byte[] bytes) throws IOException {
        File outputFile = new File(fileName);

        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        fileOutputStream.write(bytes);
        fileOutputStream.close();
    }
}
