package com.payplus.api.security;

import jodd.typeconverter.Convert;

/**
 * 字节数组和16进制转换工具
 * 
 * @author KAI.YANG
 *
 */
public class ByteArraysTo16Utils {

    public static final String _16_CHARS = "0123456789ABCDEF";

    /**
     * Convert char to byte
     * 
     * @param c
     *            char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) _16_CHARS.indexOf(c);
    }

    /**
     * 16进制转换为字节数组
     * 
     * @param hexString
     *            the hex string
     * @return byte[]
     */
    public static byte[] _16StringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    // / <summary>
    // / 十六进制字符串转换成字节数组
    // / </summary>
    // / <param name="hexString">要转换的字符串</param>
    // / <returns></returns>
    public static byte[] HexStrToByteArray(String hexString) {
        hexString = hexString.replace(" ", "");
        if ((hexString.length() % 2) != 0)
            throw new RuntimeException("十六进制字符串长度不对");
        byte[] buffer = new byte[hexString.length() / 2];
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = Convert.toByte(hexString.substring(i * 2, 2).trim(), (byte) 0x10);
        }
        return buffer;
    }

    // 将指定byte数组转换为16进制的形式
    public static String printHexString(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex);
        }
        return sb.toString().toUpperCase();

    }

    public static void main(String[] args) {
        byte[] _16StringToBytes = _16StringToBytes("70FA");
        for (byte b : _16StringToBytes) {
            System.out.println(b);

        }
    }
}
