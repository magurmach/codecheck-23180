package codecheck.util;

import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shakib Ahmed on 12/22/17.
 */
public class Base64 {
    private final static String base64Chars =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    private static int []reverseBase64Chars;

    public static byte[] encode(byte[] targetBytes) {
        List<Byte> result = new ArrayList<>();
        List<Byte> paddingBytes = new ArrayList<>();

        int padding = (3 - (targetBytes.length % 3)) % 3;

        int i;

        for (i = 0; i < padding; i++) {
            paddingBytes.add((byte) '=');
        }

        for (i = 0; i < targetBytes.length; i += 3) {

            int concatenatedNumber = 0;

            if (i + 2 < targetBytes.length) {
                concatenatedNumber += (targetBytes[i + 2] & ((1 << 8) -1));
            }

            if (i + 1 < targetBytes.length) {
                concatenatedNumber += (targetBytes[i + 1] & ((1 << 8) -1)) << 8;
            }

            if (i < targetBytes.length) {
                concatenatedNumber += (targetBytes[i] & ((1 << 8) -1)) << 16;
            }

            int encodedChars[] = new int[4];

            int index = 3;

            while(index >= 0) {
                encodedChars[index] = concatenatedNumber & ((1<<6) - 1);
                concatenatedNumber >>= 6;
                index--;
            }

            index = 0;

            while (index < 4) {
                result.add((byte)base64Chars.charAt(encodedChars[index]));
                index++;
            }
        }

        result = result.subList(0, result.size() - paddingBytes.size());
        result.addAll(paddingBytes);

        return ArrayUtils.toPrimitive(result.toArray(new Byte[0]));
    }

    private static void preProcess() {
        reverseBase64Chars = new int[256];
        int i;
        for (i = 0; i < reverseBase64Chars.length; i++) {
            reverseBase64Chars[i] = -1;
        }

        for (i = 0; i < base64Chars.length(); i++) {
            reverseBase64Chars[base64Chars.charAt(i)] = i;
        }
    }

    public static byte[] decode(byte target[]) {
        if (reverseBase64Chars == null) {
            preProcess();
        }

        List<Byte> result = new ArrayList<>();

        int i;

        for (i = 0; i < target.length; i += 4) {
            int concatenatedNumber = 0;
            if (i + 3 < target.length && reverseBase64Chars[target[i + 3]] != -1) {
                concatenatedNumber += (reverseBase64Chars[target[i + 3]]);
            }
            if (i + 2 < target.length && reverseBase64Chars[target[i + 2]] != -1) {
                concatenatedNumber += (reverseBase64Chars[target[i + 2]]) << 6;
            }
            if (i + 1 < target.length && reverseBase64Chars[target[i + 1]] != -1) {
                concatenatedNumber += (reverseBase64Chars[target[i + 1]]) << 12;
            }
            if (reverseBase64Chars[target[i]] != -1) {
                concatenatedNumber += (reverseBase64Chars[target[i]]) << 18;
            }

            int decodedChars [] = new int[3];

            int index = 2;
            while (index >= 0) {
                decodedChars[index] =  concatenatedNumber & ((1 << 8)- 1);
                concatenatedNumber >>= 8;
                index --;
            }

            index = 0;

            while (index < 3) {
                result.add((byte) decodedChars[index]);
                index ++;
            }
        }

        int index = 0;
        while (result.get(result.size() - 1 - index) == '\0') {
            index++;
        }

        result = result.subList(0, result.size() - index);

        return ArrayUtils.toPrimitive(result.toArray(new Byte[0]));
    }
}
